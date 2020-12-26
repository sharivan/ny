/**
 * 
 */
package server.yutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import data.MySQLTable;

/**
 * @author saddam
 * 
 */
public abstract class YahooProfileId {

	private String								name;
	private int									rating;
	private int									total;

	private byte								avatar;

	private long								flags;
	private int									moreFlags;
	String										ip;
	protected MySQLTable						ids_table;

	protected MySQLTable						profile_table;
	protected MySQLTable						ignoreds_table;
	protected Vector<YahooProfileIdListener>	listeners;
	private int									ban_type;
	private Timestamp							ban_date;
	private int									ban_time;
	private String								ban_reason;

	public YahooProfileId(MySQLTable ids_table, MySQLTable profile_table,
			MySQLTable ignoreds_table, String name) {
		this.ids_table = ids_table;
		this.profile_table = profile_table;
		this.ignoreds_table = ignoreds_table;
		this.name = name;

		updateValues();

		listeners = new Vector<YahooProfileIdListener>();
	}

	public void addListener(YahooProfileIdListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/**
	 * 
	 */
	public void clearListeners() {
		listeners.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof YahooProfileId))
			return false;
		YahooProfileId other = (YahooProfileId) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * @return the avatar
	 */
	public byte getAvatar() {
		return avatar;
	}

	/**
	 * @return the ban_reason
	 */
	public String getBan_reason() {
		return ban_reason;
	}

	/**
	 * @return the flags
	 */
	public long getFlags() {
		return flags;
	}

	public IgnoredEntry getIgnoredEntry() {
		IgnoredEntry result = getIgnoredEntryByIp();
		if (result == null)
			return getIgnoredEntryByName();
		return result;
	}

	public IgnoredEntry getIgnoredEntryByIp() {
		if (ban_type == 0)
			return null;
		if (ban_time != -1
				&& System.currentTimeMillis() >= ban_date.getTime() + ban_time
						* 60 * 1000) {
			ignoreds_table.assyncDelete("ip", ip);
			return null;
		}
		return new IgnoredEntry(name, ban_type, ban_date, ban_time, ban_reason);
	}

	public IgnoredEntry getIgnoredEntryByName() {
		if (ban_type == 0)
			return null;
		if (ban_time != -1
				&& System.currentTimeMillis() >= ban_date.getTime() + ban_time
						* 60 * 1000) {
			ignoreds_table.assyncDelete("name", name);
			return null;
		}
		return new IgnoredEntry(name, ban_type, ban_date, ban_time, ban_reason);
	}

	public abstract int getInitialRating();

	public String getIP() {
		return ip;
	}

	public Vector<YahooProfileIdListener> getListeners() {
		return listeners;
	}

	/**
	 * @return the moreFlags
	 */
	public int getMoreFlags() {
		return moreFlags;
	}

	public String getName() {
		return name;
	}

	public abstract String getProfile();

	/**
	 * 
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	protected abstract int getTotalGames(ResultSet rs) throws SQLException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	public boolean hidingStar() {
		return (moreFlags & 1) != 0;
	}

	public void incrementDraws(int rating) {
		profile_table.assyncExecute("UPDATE " + profile_table.name
				+ " SET rating = " + rating
				+ ", draws = draws + 1, streak = 0 WHERE name='" + getName()
				+ "';");
		this.rating = rating;
		total++;
		for (int i = 0; i < listeners.size(); i++)
			listeners.elementAt(i).actionSetRating(this, total, rating);
	}

	public void incrementLosses(int rating) {
		profile_table
				.assyncExecute("UPDATE "
						+ profile_table.name
						+ " SET rating = "
						+ rating
						+ ", losses = losses + 1, streak = IF(streak >= 0, -1, streak - 1) WHERE name='"
						+ getName() + "';");
		this.rating = rating;
		total++;
		for (int i = 0; i < listeners.size(); i++)
			listeners.elementAt(i).actionSetRating(this, total, rating);
	}

	public void incrementWins(int rating) {
		profile_table
				.assyncExecute("UPDATE "
						+ profile_table.name
						+ " SET rating = "
						+ rating
						+ ", wins = wins + 1, streak = IF(streak <= 0, 1, streak + 1) WHERE name='"
						+ getName() + "';");
		this.rating = rating;
		total++;
		for (int i = 0; i < listeners.size(); i++)
			listeners.elementAt(i).actionSetRating(this, total, rating);
	}

	public boolean isAllStarMemberShip() {
		return flags != 192;
	}

	/**
	 * @return true if have a name in room ignore list
	 */
	public boolean isIgnoredByName() {
		boolean flag = false;
		if (ban_type != 1)
			return false;
		if (ban_time == -1)
			return true;
		flag = System.currentTimeMillis() < ban_date.getTime() + ban_time
				* 60 * 1000;
		if (!flag)
			ignoreds_table.assyncDelete("name", name);
		return flag;
	}

	public void loadValues(String[] names, ActionLoadValue action) {
		ResultSet rs = null;
		try {
			rs = ids_table.getAllValues(new String[] { "name" },
					new Object[] { getName() });
			if (rs.next())
				for (String name2 : names)
					action.onLoad(name2, rs.getString(name2));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				ids_table.closeResultSet(rs);
				rs = null;
			}
		}
	}

	public void notifyBan(Timestamp ban_date, int time, String reason,
			String admin) {
		if (ban_type != IgnoredEntry.BAN)
			ignoreds_table.assyncInsert(new Object[] { name, ban_date,
					IgnoredEntry.BAN, time, reason, admin, ip });
		else
			ignoreds_table.assyncUpdate(new String[] { "name" },
					new Object[] { name }, new String[] { "ban_date",
							"ban_type", "ban_time", "reason", "admin", "ip" },
					new Object[] { ban_date, IgnoredEntry.BAN, time, reason,
							admin, ip });

		ignoreds_table.assyncUpdate(new String[] { "ip" }, new Object[] { ip },
				new String[] { "ban_date", "ban_type", "ban_time", "reason",
						"admin" }, new Object[] { ban_date, IgnoredEntry.BAN,
						time, reason, admin });

		ban_type = IgnoredEntry.BAN;
		this.ban_date = ban_date;
		ban_time = time;
		ban_reason = reason;

		for (int i = 0; i < listeners.size(); i++)
			listeners.elementAt(i).actionBan(this, ban_date, time, reason);
	}

	/**
	 * @param value
	 */
	public void notifyFlags(long value) {
		if (flags != value) {
			flags = value;
			profile_table.assyncUpdate(new String[] { "name" },
					new Object[] { getName() }, new String[] { "flags" },
					new Object[] { flags });
			for (int i = 0; i < listeners.size(); i++)
				listeners.elementAt(i).actionSetFlags(this, value);
		}
	}

	public void notifyMute(Timestamp mute_date, int time, String reason,
			String admin) {
		if (ban_type != IgnoredEntry.MUTE)
			ignoreds_table.assyncInsert(new Object[] { name, ban_date,
					IgnoredEntry.MUTE, time, reason, admin, ip });
		else
			ignoreds_table.assyncUpdate(new String[] { "name" },
					new Object[] { name }, new String[] { "ban_date",
							"ban_type", "ban_time", "reason", "admin", "ip" },
					new Object[] { ban_date, IgnoredEntry.MUTE, time, reason,
							admin, ip });

		ignoreds_table.assyncUpdate(new String[] { "ip" }, new Object[] { ip },
				new String[] { "ban_date", "ban_type", "ban_time", "reason",
						"admin" }, new Object[] { ban_date, IgnoredEntry.MUTE,
						time, reason, admin });

		ban_type = IgnoredEntry.MUTE;
		ban_date = mute_date;
		ban_time = time;
		ban_reason = reason;

		for (int i = 0; i < listeners.size(); i++)
			listeners.elementAt(i).actionMute(this, mute_date, time, reason);
	}

	/**
	 * @param value
	 */
	public void notifyPublicFlags(int value) {
		if (moreFlags != value) {
			moreFlags = value;
			profile_table.assyncUpdate(new String[] { "name" },
					new Object[] { getName() }, new String[] { "more_flags" },
					new Object[] { moreFlags });
			for (int i = 0; i < listeners.size(); i++)
				listeners.elementAt(i).actionSetPublicFlags(this, value);
		}
	}

	public void removeListener(YahooProfileIdListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	/**
	 * @param value
	 */
	public void setAvatar(byte value) {
		if (avatar != value) {
			avatar = value;
			ids_table.assyncUpdate(new String[] { "name" },
					new Object[] { getName() }, new String[] { "avatar" },
					new Object[] { value });
			for (int i = 0; i < listeners.size(); i++)
				listeners.elementAt(i).actionSetAvatar(this, avatar);
		}
	}

	/**
	 * @param value
	 */
	public void setIp(String value) {
		if (value != null && !value.equals(ip)) {
			ip = value;
			profile_table.assyncUpdate(new String[] { "name" },
					new Object[] { name }, new String[] { "ip" },
					new Object[] { ip });
		}
	}

	public void setProperty(String name, String value) {
		ids_table.assyncUpdate(new String[] { "name" },
				new Object[] { getName() }, new String[] { name },
				new Object[] { value });

		if (name.equals("games_common_hidestar")) {
			if (value.equals("1") && !hidingStar())
				notifyPublicFlags(moreFlags | 1);
		}
	}

	public void setRating(int value) {
		if (rating != value) {
			rating = value;
			profile_table.assyncUpdate(new String[] { "name" },
					new Object[] { getName() }, new String[] { "rating" },
					new Object[] { value });
			for (int i = 0; i < listeners.size(); i++)
				listeners.elementAt(i).actionSetRating(this, total, value);
		}
	}

	@Override
	public String toString() {
		return getProfile();
	}

	protected void updateValues() {
		ResultSet rs = null;
		try {
			rs = ids_table.getAllValues(new String[] { "name" },
					new Object[] { getName() });
			if (rs.next()) {
				avatar = rs.getByte("avatar");
				flags = rs.getLong("flags");
				moreFlags = rs.getInt("more_flags");
				ip = rs.getString("ip");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				ids_table.closeResultSet(rs);
				rs = null;
			}
		}

		rating = getInitialRating();
		total = 0;
		try {
			rs = profile_table.getAllValues(new String[] { "name" },
					new Object[] { getName() });
			if (rs.next()) {
				rating = rs.getInt("rating");
				total = getTotalGames(rs);
			}
			else
				profile_table.assyncInsert(new Object[] { name,
						getInitialRating(), 0, 0, 0, 0, 0, 0, ip });
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				profile_table.closeResultSet(rs);
				rs = null;
			}
		}

		ban_type = 0;
		try {
			rs = ignoreds_table.getAllValues(new String[] { "name" },
					new Object[] { getName() });
			if (rs.next()) {
				ban_type = rs.getInt("ban_type");
				ban_date = rs.getTimestamp("ban_date");
				ban_time = rs.getInt("ban_time");
				ban_reason = rs.getString("reason");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				ignoreds_table.closeResultSet(rs);
				rs = null;
			}
		}
	}

}
