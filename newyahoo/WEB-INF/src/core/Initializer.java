/**
 * 
 */
package core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServlet;

import process.ProcessPool;
import server.k.YahooCheckersServer;
import server.po.YahooPoolServer;
import server.po2.YahooPoolServer2;
import server.yutils.YahooRoomHandler;
import data.MySQLConnectionPool;
import data.MySQLTable;

/**
 * @author Saddam
 * 
 */
public class Initializer extends HttpServlet implements YahooRoomHandler {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -4558999460178582604L;

	public static java.sql.Connection		mySqlConnection;
	private static String					dbHost				= "localhost";

	private static int						dbPort				= 3306;

	private static String					dbName				= "newyahoo";

	private static String					dbUserName			= "newyahoo";
	private static String					dbPassword			= "123456";

	public MySQLTable						ids;
	public MySQLTable						games;

	public MySQLTable						pool_rooms;
	public MySQLTable						pool_ignoreds;
	public MySQLTable						pool_profiles;
	public MySQLTable						pool_games;

	public MySQLTable						pool2_rooms;
	public MySQLTable						pool2_ignoreds;
	public MySQLTable						pool2_profiles;
	public MySQLTable						pool2_games;

	public MySQLTable						checkers_rooms;
	public MySQLTable						checkers_ignoreds;
	public MySQLTable						checkers_profiles;
	public MySQLTable						checkers_games;

	private Hashtable<String, MySQLTable>	tables;
	private ProcessPool						processPool;
	private MySQLConnectionPool				connectionPool;
	private YahooPoolServer					poolServer;
	private YahooPoolServer2				poolServer2;
	private YahooCheckersServer				checkersServer;

	@Override
	public void destroy() {
		connectionPool.destroy();
		connectionPool = null;

		processPool.destroy();
		processPool = null;

		ids = null;
		games = null;

		pool_rooms = null;
		pool_ignoreds = null;
		pool_profiles = null;
		pool_games = null;

		pool2_rooms = null;
		pool2_ignoreds = null;
		pool2_profiles = null;
		pool2_games = null;

		checkers_rooms = null;
		checkers_ignoreds = null;
		checkers_profiles = null;
		checkers_games = null;

		tables.clear();
		tables = null;

		poolServer.close();
		poolServer = null;

		poolServer2.close();
		poolServer2 = null;

		checkersServer.close();
		checkersServer = null;

		System.gc();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.TableManager#getTable(java.lang.String)
	 */
	@Override
	public MySQLTable getTable(String name) {
		return tables.get(name);
	}

	@Override
	public void init() {
		processPool = new ProcessPool();

		connectionPool = new MySQLConnectionPool(processPool, dbHost, dbPort,
				dbName, dbUserName, dbPassword);

		tables = new Hashtable<String, MySQLTable>();

		ids = new MySQLTable(connectionPool, "ids");
		tables.put("ids", ids);
		games = new MySQLTable(connectionPool, "games");
		tables.put("games", games);

		pool_rooms = new MySQLTable(connectionPool, "pool_rooms");
		tables.put("pool_rooms", pool_rooms);
		pool_ignoreds = new MySQLTable(connectionPool, "pool_ignoreds");
		tables.put("pool_ignoreds", pool_ignoreds);
		pool_profiles = new MySQLTable(connectionPool, "pool_profiles");
		tables.put("pool_profiles", pool_profiles);
		pool_games = new MySQLTable(connectionPool, "pool_games");
		tables.put("pool_games", pool_games);

		pool2_rooms = new MySQLTable(connectionPool, "pool2_rooms");
		tables.put("pool2_rooms", pool2_rooms);
		pool2_ignoreds = new MySQLTable(connectionPool, "pool2_ignoreds");
		tables.put("pool2_ignoreds", pool2_ignoreds);
		pool2_profiles = new MySQLTable(connectionPool, "pool2_profiles");
		tables.put("pool2_profiles", pool2_profiles);
		pool2_games = new MySQLTable(connectionPool, "pool2_games");
		tables.put("pool2_games", pool2_games);

		checkers_rooms = new MySQLTable(connectionPool, "checkers_rooms");
		tables.put("checkers_rooms", checkers_rooms);
		checkers_ignoreds = new MySQLTable(connectionPool, "checkers_ignoreds");
		tables.put("checkers_ignoreds", checkers_ignoreds);
		checkers_profiles = new MySQLTable(connectionPool, "checkers_profiles");
		tables.put("checkers_profiles", checkers_profiles);
		checkers_games = new MySQLTable(connectionPool, "checkers_games");
		tables.put("checkers_games", checkers_games);

		Vector<String> yports = new Vector<String>();
		ResultSet rs = null;
		try {
			rs = pool_rooms.getAllValues();
			while (rs.next())
				yports.add(rs.getString("name"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				pool_rooms.closeResultSet(rs);
				rs = null;
			}
		}
		poolServer = new YahooPoolServer(this, 11998, yports);

		yports.clear();
		try {
			rs = checkers_rooms.getAllValues();
			while (rs.next())
				yports.add(rs.getString("name"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				checkers_rooms.closeResultSet(rs);
				rs = null;
			}
		}
		checkersServer = new YahooCheckersServer(this, 11999, yports);

		yports.clear();
		try {
			rs = pool2_rooms.getAllValues();
			while (rs.next())
				yports.add(rs.getString("name"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				pool2_rooms.closeResultSet(rs);
				rs = null;
			}
		}
		poolServer2 = new YahooPoolServer2(this, 12002, yports);
	}

}