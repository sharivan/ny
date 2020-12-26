/**
 * 
 */
package server.po2;

import java.sql.ResultSet;
import java.sql.SQLException;

import server.yutils.YahooProfileId;
import data.MySQLTable;

/**
 * @author Saddam Hussein
 * 
 */
public class YahooPoolProfileId extends YahooProfileId {

	/**
	 * @param ids_table
	 * @param profile_table
	 * @param ignoreds_table
	 * @param name
	 */
	public YahooPoolProfileId(MySQLTable ids_table, MySQLTable profile_table,
			MySQLTable ignoreds_table, String name) {
		super(ids_table, profile_table, ignoreds_table, name);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooProfileId#getInitialRating()
	 */
	@Override
	public int getInitialRating() {
		return 1200;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooProfileId#getProfile()
	 */
	@Override
	public String getProfile() {
		ResultSet rs = null;
		try {
			rs = profile_table.getAllValues(new String[] { "name" },
					new Object[] { getName() });
			if (!rs.next())
				return "";
			String result = "Rating: " + rs.getInt("rating") + "\r\n";
			result += "\r\n";
			int wins = rs.getInt("wins");
			result += "Wins: " + wins + "\r\n";
			int losses = rs.getInt("losses");
			result += "Losses: " + losses + "\r\n";
			int aborteds = rs.getInt("aborteds");
			result += "Aborted Games: " + aborteds + "\r\n";
			int total = wins + losses + aborteds;
			result += "Total: " + total + "\r\n";
			result += "\r\n";
			int streak = rs.getInt("streak");
			result += "Streak: " + streak;
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
		finally {
			if (rs != null) {
				profile_table.closeResultSet(rs);
				rs = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooProfileId#getTotalGames(java.sql.ResultSet)
	 */
	@Override
	protected int getTotalGames(ResultSet rs) throws SQLException {
		int total = rs.getInt("wins");
		total += rs.getInt("losses");
		total += rs.getInt("aborteds");
		return total;
	}

}
