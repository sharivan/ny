package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Hashtable;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;

public class MySQLTable {

	public static String formatValue(Object value) {
		if (value instanceof String) {
			String result = (String) value;
			result = result.replace("\\", "\\\\");
			result = result.replace("'", "\\'");
			return "'" + result + "'";
		}
		else if (value instanceof java.util.Date) {
			long t = ((java.util.Date) value).getTime();
			Date date = new Date(t);
			Time time = new Time(t);
			return "'" + date + " " + time + "'";
		}
		else if (value == null)
			return "NULL";
		return value.toString();
	}

	public static String formatValues(Object[] values) {
		String result = formatValue(values[0]);
		for (int i = 1; i < values.length; i++)
			result += ", " + formatValue(values[i]);
		return result;
	}

	public String										name;

	protected MySQLConnectionPool						pool;

	protected Hashtable<PreparedStatement, Connection>	preparedStatements;
	protected Hashtable<ResultSet, Object[]>			resultSets;

	public MySQLTable(MySQLConnectionPool pool, String table) {
		this.pool = pool;
		name = table;
		preparedStatements = new Hashtable<PreparedStatement, Connection>();
		resultSets = new Hashtable<ResultSet, Object[]>();
	}

	public void assyncDelete(String keyName, Object keyValue) {
		String command = "DELETE FROM " + name + " WHERE " + keyName + " = "
				+ MySQLTable.formatValue(keyValue);

		new MySQLAssync(pool.getProcessQueue(), this, command);
	}

	public void assyncExecute(String command) {
		new MySQLAssync(pool.getProcessQueue(), this, command);
	}

	public void assyncInsert(Object[] values) {
		String command = "INSERT INTO " + name + " VALUES ("
				+ MySQLTable.formatValues(values) + ")";

		new MySQLAssync(pool.getProcessQueue(), this, command);
	}

	public void assyncUpdate(String[] keyName, Object[] keyValue,
			String[] fieldName, Object[] fieldValue) {
		String sets = fieldName[0] + " = "
				+ MySQLTable.formatValue(fieldValue[0]);
		for (int i = 1; i < fieldName.length; i++)
			sets += ", " + fieldName[i] + " = "
					+ MySQLTable.formatValue(fieldValue[i]);

		String command = "UPDATE " + name + " SET " + sets + " WHERE "
				+ keyName[0] + " = " + MySQLTable.formatValue(keyValue[0]);
		for (int i = 1; i < keyName.length; i++)
			command += " AND " + keyName[i] + " = "
					+ MySQLTable.formatValue(keyValue[i]);

		new MySQLAssync(pool.getProcessQueue(), this, command);
	}

	public void closePreparedStatement(PreparedStatement ps) {
		Connection connection = preparedStatements.get(ps);
		if (connection == null) {
			System.err.println("PreparedStatement sem conexão associada!");
			return;
		}
		try {
			ps.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			preparedStatements.remove(ps);
			ps = null;
			pool.releaseConnection(connection);
		}
	}

	public void closeResultSet(ResultSet rs) {
		Object[] obj = resultSets.get(rs);
		if (obj == null) {
			System.err.println("ResultSet sem objeto associado!");
			return;
		}
		Connection connection = (Connection) obj[0];
		Statement statement = (Statement) obj[1];
		try {
			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			resultSets.remove(rs);
			rs = null;
			if (statement != null) {
				try {
					statement.close();
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				statement = null;
			}
			if (connection != null)
				pool.releaseConnection(connection);
		}
	}

	public boolean contains(String keyName, Object keyValue) {
		ResultSet rs = null;
		boolean result = false;
		try {
			rs = executeQuery("SELECT * FROM " + name + " WHERE " + keyName
					+ " = " + formatValue(keyValue));
			if (rs != null) {
				result = rs.next();
				closeResultSet(rs);
			}
		}
		catch (SQLException e) {
			// ignore
		}
		rs = null;
		return result;
	}

	public void delete(String keyName, Object keyValue) {
		execute("DELETE FROM " + name + " WHERE " + keyName + " = "
				+ formatValue(keyValue));
	}

	public void execute(String command) {
		// long currTime = System.currentTimeMillis();
		boolean success = false;
		// boolean success1 = false;
		for (int counter = 0; counter < 10; counter++) {
			Connection connection = pool.getFreeConnection();
			if (connection == null)
				return;
			Statement statement = null;
			try {
				statement = connection.createStatement();
				statement.execute(command);
				success = true;
				// success1 = true;
				break;
			}
			catch (MySQLNonTransientConnectionException e) {
				// System.out
				// .println("Erro durante a execução do comando SQL (Servidor MySQL está off-line). Tentando uma nova conexão com o pool... (Tentativa "
				// + (counter + 1) + ")");
			}
			catch (CommunicationsException e) {
				// System.out
				// .println("Erro durante a execução do comando SQL (Resposta do servidor MySQL inválida). Tentando uma nova conexão com o pool... (Tentativa "
				// + (counter + 1) + ")");
			}
			catch (SQLException e) {
				success = true;
				e.printStackTrace();
			}
			finally {
				if (statement != null) {
					try {
						statement.close();
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				pool.releaseConnection(connection);
				if (!success) {
					synchronized (this) {
						try {
							wait(1000);
						}
						catch (InterruptedException e1) {
						}
					}
				}
				// else
				// System.out.println(command + "("
				// + (System.currentTimeMillis() - currTime) + " ms) "
				// + (success1 ? "" : "(falha!)"));
			}
		}
	}

	public ResultSet executeQuery(String query) {
		return executeQuery(query, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
	}

	public ResultSet executeQuery(String query, int resultSetType,
			int resultSetConcurrency) {
		// long currTime = System.currentTimeMillis();
		boolean success = false;
		// boolean success1 = false;
		for (int counter = 0; counter < 10; counter++) {
			Connection connection = pool.getFreeConnection();
			if (connection == null)
				return null;
			Statement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.createStatement(resultSetType,
						resultSetConcurrency);
				rs = statement.executeQuery(query);
				resultSets.put(rs, new Object[] { connection, statement });
				success = true;
				// success1 = true;
				return rs;
			}
			catch (MySQLNonTransientConnectionException e) {
				// System.out
				// .println("Erro durante a execução do comando SQL (Servidor MySQL está off-line). Tentando uma nova conexão com o pool... (Tentativa "
				// + (counter + 1) + ")");
			}
			catch (CommunicationsException e) {
				// System.out
				// .println("Erro durante a execução do comando SQL (Resposta do servidor MySQL inválida). Tentando uma nova conexão com o pool... (Tentativa "
				// + (counter + 1) + ")");
			}
			catch (SQLException e) {
				success = true;
				e.printStackTrace();
			}
			finally {
				if (!success) {
					pool.releaseConnection(connection);
					synchronized (this) {
						try {
							wait(1000);
						}
						catch (InterruptedException e1) {
						}
					}
				}
				// else
				// System.out.println(query + "("
				// + (System.currentTimeMillis() - currTime) + " ms) "
				// + (success1 ? "" : "(falha!)"));
			}
		}
		return null;
	}

	public ResultSet getAllValues() {
		return executeQuery("SELECT * FROM " + name);
	}

	public ResultSet getAllValues(int resultSetType, int resultSetConcurrency) {
		return executeQuery("SELECT * FROM " + name, resultSetType,
				resultSetConcurrency);
	}

	public ResultSet getAllValues(String[] keyName, Object[] keyValue) {
		String command = "SELECT * FROM " + name + " WHERE " + keyName[0]
				+ " = " + formatValue(keyValue[0]);
		for (int i = 1; i < keyName.length; i++)
			command += " AND " + keyName[i] + " = " + formatValue(keyValue[i]);
		ResultSet rs = executeQuery(command);
		return rs;
	}

	public ResultSet getAllValues(String[] keyName, Object[] keyValue,
			int resultSetType, int resultSetConcurrency) {
		String command = "SELECT * FROM " + name + " WHERE " + keyName[0]
				+ " = " + formatValue(keyValue[0]);
		for (int i = 1; i < keyName.length; i++)
			command += " AND " + keyName[i] + " = " + formatValue(keyValue[i]);
		ResultSet rs = executeQuery(command, resultSetType,
				resultSetConcurrency);
		return rs;
	}

	public ResultSet getValue(String[] keyName, Object[] keyValue,
			String[] valueNames) {
		String vns = valueNames[0];
		for (int i = 1; i < valueNames.length; i++)
			vns += ", " + valueNames[i];
		String command = "SELECT " + vns + " FROM " + name + " WHERE "
				+ keyName[0] + " = " + formatValue(keyValue[0]);
		for (int i = 1; i < keyName.length; i++)
			command += " AND " + keyName[i] + " = " + formatValue(keyValue[i]);
		ResultSet rs = executeQuery(command);
		return rs;
	}

	public ResultSet getValue(String[] keyName, Object[] keyValue,
			String[] valueNames, int resultSetType, int resultSetConcurrency) {
		String vns = valueNames[0];
		for (int i = 1; i < valueNames.length; i++)
			vns += ", " + valueNames[i];
		String command = "SELECT " + vns + " FROM " + name + " WHERE "
				+ keyName[0] + " = " + formatValue(keyValue[0]);
		for (int i = 1; i < keyName.length; i++)
			command += " AND " + keyName[i] + " = " + formatValue(keyValue[i]);
		ResultSet rs = executeQuery(command, resultSetType,
				resultSetConcurrency);
		return rs;
	}

	public void insert(Object[] values) {
		execute("INSERT INTO " + name + " VALUES (" + formatValues(values)
				+ ")");
	}

	public PreparedStatement prepareStatement(String query) {
		return prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
	}

	public PreparedStatement prepareStatement(String query, int resultSetType,
			int resultSetConcurrency) {
		Connection connection = pool.getFreeConnection();
		if (connection == null)
			return null;
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(query, resultSetType,
					resultSetConcurrency);
			preparedStatements.put(ps, connection);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}

	public void update(String keyName, Object keyValue, String[] fieldName,
			Object[] fieldValue) {
		String sets = fieldName[0] + " = " + formatValue(fieldValue[0]);
		for (int i = 1; i < fieldName.length; i++)
			sets += ", " + fieldName[i] + " = " + formatValue(fieldValue[i]);
		execute("UPDATE " + name + " SET " + sets + " WHERE " + keyName + " = "
				+ formatValue(keyValue));
	}

}
