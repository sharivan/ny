/**
 * 
 */
package room;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.MySQLTable;

/**
 * @author Saddam
 * 
 */
public class Room {

	public static int getCount(MySQLTable rooms, String game) {
		if (game.equals("checkers")) {
			ResultSet rs = null;
			try {
				rs = rooms.executeQuery("SELECT count(*) FROM " + rooms.name);
				if (!rs.next())
					return -1;
				int count = rs.getInt("count(*)");
				return count;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
			finally {
				if (rs != null) {
					rooms.closeResultSet(rs);
					rs = null;
				}
			}
		}
		return -1;
	}

}
