/**
 * 
 */
package core;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import process.ProcessPool;
import data.MySQLConnectionPool;
import data.MySQLTable;

/**
 * @author Saddam
 * 
 */
public class Initializer extends HttpServlet {
	
	public static Initializer selfInstance = null;

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1573278850881282759L;
	
	private static String				dbHost				= "saddam.virtuaserver.com.br";

	private static int					dbPort				= 3306;

	private static String				dbName				= "newyahoo";

	private static String				dbUsername			= "newyahoo";
	private static String				dbPassword			= "123456";

	public MySQLTable			ids;
	public MySQLTable			games;
	public MySQLTable			checkers_rooms;

	public ProcessPool			processPool;
	public MySQLConnectionPool connectionPool;
	
	public static int			initialPoolLen		= 13;
	
	public Connection[]							mySqlConnections;
	public boolean[]							using;

	@Override
	public void destroy() {
		selfInstance = null;
		
		for (int i = 0; i < mySqlConnections.length; i++) {
			try {
				if (mySqlConnections[i] != null
						&& !mySqlConnections[i].isClosed()) {
					mySqlConnections[i].close();
					using[i] = false;
					mySqlConnections[i] = null;
				}
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
		mySqlConnections = null;
		using = null;
		
		ids = null;
		games = null;
		checkers_rooms = null;
		
		connectionPool.destroy();
		connectionPool = null;
		
		processPool.destroy();
		processPool = null;
	}

	@Override
	public void init() {
		processPool = new ProcessPool();
		connectionPool = new MySQLConnectionPool(processPool, dbHost, dbPort, dbName, dbUsername, dbPassword);
		
		mySqlConnections = new Connection[initialPoolLen];
		using = new boolean[initialPoolLen];			
		
		ids = new MySQLTable(connectionPool, "ids");
		games = new MySQLTable(connectionPool, "games");
		checkers_rooms = new MySQLTable(connectionPool, "checkers_rooms");
		selfInstance = this;
	}

}