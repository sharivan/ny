// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.io;

import java.io.DataOutput;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Vector;

import server.net.YahooSocket;
import server.yutils.ActionLoadValue;
import server.yutils.FloodRecord;
import server.yutils.IgnoredEntry;
import server.yutils.YahooProfileId;
import server.yutils.YahooProfileIdListener;
import server.yutils.YahooRoom;
import server.yutils.YahooTable;

import common.io.YData;
import common.utils.SynchronizedVector;
import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls34, _cls37

public class YahooConnectionId implements YahooProfileIdListener, DataOutput {

	private YahooSocket					socket;
	private YahooRoom					room;
	private Vector<YahooTable>			tables;
	private YahooOutputStream			out;
	private YahooBufferedOutputStream	bufOut;
	private String						cookie;
	private String						ycookie;
	private String						agent;
	private String						intl_code;
	private boolean						imFromFriendsOnly		= false;
	private Vector<String>				friends;
	private Vector<String>				ignoreds;
	private long						lastRequestTime;
	private boolean						declineAllInviations	= false;
	private YahooProfileId				profileId;
	private String						ip;
	private long						flags;
	private int							state;
	private FloodRecord					chatFloodRecord;
	private FloodRecord					privateChatFloodRecord;
	private FloodRecord					pingFloodRecord;
	private FloodRecord					pongFloodRecord;
	private FloodRecord					joinTableFloodRecord;
	private FloodRecord					sitFloodRecord;
	private FloodRecord					changeTablePrivacyFloodRecord;
	private FloodRecord					declineInviteFloodRecord;
	private FloodRecord					inviteFloodRecord;
	private FloodRecord					setAvatarFloodRecord;
	private FloodRecord					changeIdPropertyFloodRecord;
	private FloodRecord					cancelRequestFloodRecord;
	private FloodRecord					cancelResponseFloodRecord;
	private FloodRecord					tableChatFloodRecord;
	private FloodRecord					focusFloodRecord;
	private FloodRecord					startFloodRecord;

	public YahooConnectionId(YahooSocket yahooSocket, YahooOutputStream out,
			YahooBufferedOutputStream bufOut) {
		lastRequestTime = System.currentTimeMillis();
		friends = new Vector<String>();
		ignoreds = new Vector<String>();
		socket = yahooSocket;
		this.out = out;
		this.bufOut = bufOut;
		flags = 0;
		setState(0);
		tables = new Vector<YahooTable>();

		chatFloodRecord = new FloodRecord(3, 2000);
		privateChatFloodRecord = new FloodRecord(3, 2000);
		pingFloodRecord = new FloodRecord(9, 1000);
		pongFloodRecord = new FloodRecord(9, 1000);
		joinTableFloodRecord = new FloodRecord(3, 1000);
		sitFloodRecord = new FloodRecord(9, 1000);
		changeTablePrivacyFloodRecord = new FloodRecord(9, 1000);
		declineInviteFloodRecord = new FloodRecord(9, 1000);
		inviteFloodRecord = new FloodRecord(9, 1000);
		setAvatarFloodRecord = new FloodRecord(9, 1000);
		changeIdPropertyFloodRecord = new FloodRecord(3, 1000);
		cancelRequestFloodRecord = new FloodRecord(9, 1000);
		cancelResponseFloodRecord = new FloodRecord(9, 1000);
		tableChatFloodRecord = new FloodRecord(3, 2000);
		focusFloodRecord = new FloodRecord(9, 1000);
		startFloodRecord = new FloodRecord(9, 1000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * server.yutils.YahooProfileIdListener#actionBan(server.yutils.YahooProfileId
	 * , int, java.lang.String)
	 */
	@Override
	public void actionBan(YahooProfileId sender, Timestamp ban_date, int time,
			String reason) {
		room.alert(this, "You are banned from the room "
				+ (time == -1 ? "forever" : " per " + time / 60 + " minutes")
				+ " (" + reason + ")");
		close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * server.yutils.YahooProfileIdListener#actionMute(server.yutils.YahooProfileId
	 * , int, java.lang.String)
	 */
	@Override
	public void actionMute(YahooProfileId sender, Timestamp mute_date,
			int time, String reason) {
		room.blueMessage(this, "Your chat are blocked "
				+ (time == -1 ? "forever" : " per " + time / 60 + " minutes")
				+ " (" + reason + ")");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeserver.yutils.YahooProfileIdListener#actionSetAvatar(server.yutils.
	 * YahooProfileId, byte)
	 */
	@Override
	public void actionSetAvatar(YahooProfileId sender, byte avatar) {
		String name = getName();
		SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
		roomIds.readLock();
		try {
			for (YahooConnectionId id : roomIds)
				room.changeAvatar(id, avatar, name);
		}
		finally {
			roomIds.readUnlock();
		}
		for (YahooTable table : tables) {
			YahooConnectionId[] sits = table.getSits();
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(this)) {
					table.doChangeAvatar(i, avatar);
					break;
				}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeserver.yutils.YahooProfileIdListener#actionSetFlags(server.yutils.
	 * YahooProfileId, long)
	 */
	@Override
	public void actionSetFlags(YahooProfileId sender, long value) {
		room.changeFlags(this, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * server.yutils.YahooProfileIdListener#actionSetPublicFlags(server.yutils
	 * .YahooProfileId, int)
	 */
	@Override
	public void actionSetPublicFlags(YahooProfileId sender, int flags) {
		String name = getName();
		SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
		roomIds.readLock();
		try {
			for (YahooConnectionId id1 : roomIds)
				room.changePublicFlags(id1, name, flags);
		}
		finally {
			roomIds.readUnlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeserver.yutils.YahooProfileIdListener#actionSetRating(server.yutils.
	 * YahooProfileId, int)
	 */
	@Override
	public void actionSetRating(YahooProfileId sender, int totalGames,
			int rating) {
		if (totalGames >= 20) {
			String name = getName();
			SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
			roomIds.readLock();
			try {
				for (YahooConnectionId id : roomIds)
					room.chageRating(id, name, rating);
			}
			finally {
				roomIds.readUnlock();
			}
		}
	}

	public void close() {
		if (room != null) {
			room.doExitId(this);
			if (bufOut != null) {
				try {
					bufOut.close(room.getIndex());
				}
				catch (IOException e1) {
				}
				bufOut = null;
			}
			room = null;
		}
		if (profileId != null) {
			socket.releaseProfileId(this, profileId.getName());
			profileId = null;
		}
		if (tables != null) {
			tables.clear();
			tables = null;
		}
		if (friends != null) {
			friends.clear();
			friends = null;
		}
		if (ignoreds != null) {
			ignoreds.clear();
			ignoreds = null;
		}
		if (socket != null) {
			socket.close(this);
			socket = null;
		}
		out = null;
		setState(0);
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
		if (!(obj instanceof YahooConnectionId))
			return false;
		YahooConnectionId other = (YahooConnectionId) obj;
		if (profileId == null) {
			if (other.profileId != null)
				return false;
		}
		else if (!profileId.equals(other.profileId))
			return false;
		return true;
	}

	/**
	 * @param string
	 * @throws IOException
	 */
	public void fail(String string) throws IOException {
		bufOut.fail(string);
	}

	public void flush() {
		try {
			bufOut.send(room.getIndex());
		}
		catch (IOException e) {
			close();
		}
	}

	public void friend(String name) {
		friends.add(name);
	}

	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @return avatar
	 */
	public byte getAvatar() {
		return profileId.getAvatar();
	}

	public FloodRecord getCancelRequestFloodRecord() {
		return cancelRequestFloodRecord;
	}

	public FloodRecord getCancelResponseFloodRecord() {
		return cancelResponseFloodRecord;
	}

	public FloodRecord getChangeIdPropertyFloodRecord() {
		return changeIdPropertyFloodRecord;
	}

	/**
	 * @return the changeTablePrivacyFloodRecord
	 */
	public FloodRecord getChangeTablePrivacyFloodRecord() {
		return changeTablePrivacyFloodRecord;
	}

	/**
	 * @return the chatFloodRecord
	 */
	public FloodRecord getChatFloodRecord() {
		return chatFloodRecord;
	}

	/**
	 * @return the cookie
	 */
	public String getCookie() {
		return cookie;
	}

	public FloodRecord getDeclineInviteFloodRecord() {
		return declineInviteFloodRecord;
	}

	/**
	 * @return flags
	 */
	public long getFlags() {
		return flags | profileId.getFlags();
	}

	public FloodRecord getFocusFloodRecord() {
		return focusFloodRecord;
	}

	public Vector<String> getFriends() {
		return friends;
	}

	public long getGlobalFlags() {
		return profileId.getFlags();
	}

	/**
	 * @return true se estiver sem chat
	 */
	public IgnoredEntry getIgnoredEntry() {
		return profileId.getIgnoredEntry();
	}

	public Vector<String> getIgnoreds() {
		return ignoreds;
	}

	/**
	 * @return the intl_code
	 */
	public String getIntl_code() {
		return intl_code;
	}

	public FloodRecord getInviteFloodRecord() {
		return inviteFloodRecord;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return the joinTableFloodRecord
	 */
	public FloodRecord getJoinTableFloodRecord() {
		return joinTableFloodRecord;
	}

	/**
	 * @return the lastRequestTime
	 */
	public long getLastRequestTime() {
		return lastRequestTime;
	}

	public long getLocalFlags() {
		return flags;
	}

	/**
	 * @return moreflags
	 */
	public int getMoreFlags() {
		return profileId.getMoreFlags();
	}

	public String getName() {
		if (profileId != null)
			return profileId.getName();
		return null;
	}

	/**
	 * @return the pingFloodRecord
	 */
	public FloodRecord getPingFloodRecord() {
		return pingFloodRecord;
	}

	/**
	 * @return the pongFloodRecord
	 */
	public FloodRecord getPongFloodRecord() {
		return pongFloodRecord;
	}

	/**
	 * @return the privateChatFloodRecord
	 */
	public FloodRecord getPrivateChatFloodRecord() {
		return privateChatFloodRecord;
	}

	public String getProfile() {
		return profileId.getProfile();
	}

	public YahooProfileId getProfileId() {
		return profileId;
	}

	/**
	 * @return rating verdadeiro (usado quando for provisório)
	 */
	public int getRating() {
		if (profileId != null && profileId.getTotal() >= 20)
			return profileId.getRating();
		return YahooUtils.provisional;
	}

	/**
	 * @return a pontuação verdadeira (sem verificar se é provisório ou não)
	 */
	public int getRealRating() {
		if (profileId != null)
			return profileId.getRating();
		return YahooUtils.provisional;
	}

	/**
	 * @return the room
	 */
	public YahooRoom getRoom() {
		return room;
	}

	public FloodRecord getSetAvatarFloodRecord() {
		return setAvatarFloodRecord;
	}

	/**
	 * @return the sitFloodRecord
	 */
	public FloodRecord getSitFloodRecord() {
		return sitFloodRecord;
	}

	public FloodRecord getStartFloodRecord() {
		return startFloodRecord;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	public FloodRecord getTableChatFloodRecord() {
		return tableChatFloodRecord;
	}

	/**
	 * @return the tables
	 */
	public Vector<YahooTable> getTables() {
		return tables;
	}

	/**
	 * @return the ycookie
	 */
	public String getYcookie() {
		return ycookie;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (profileId == null ? 0 : profileId.hashCode());
		return result;
	}

	public void ignore(String name) {
		ignoreds.add(name);
	}

	public boolean isAdmin() {
		return (getFlags() & 4) != 0;
	}

	public boolean isAllStarMemberShip() {
		return profileId.isAllStarMemberShip();
	}

	/**
	 * @return the declineAllInviations
	 */
	public boolean isDeclineAllInviations() {
		return declineAllInviations;
	}

	public boolean isFriend(String name) {
		return friends.contains(name);
	}

	public boolean isIgnored(String name) {
		return ignoreds.contains(name);
	}

	/**
	 * @return the imFromFriendsOnly
	 */
	public boolean isImFromFriendsOnly() {
		return imFromFriendsOnly;
	}

	public void loadValues(String[] names, ActionLoadValue action) {
		profileId.loadValues(names, action);
	}

	public void noFriend(String name) {
		friends.remove(name);
	}

	public void noIgnore(String name) {
		ignoreds.remove(name);
	}

	public void notifyBan(Timestamp ban_date, int time, String reason,
			String admin) {
		profileId.notifyBan(ban_date, time, reason, admin);
	}

	public void notifyMute(Timestamp mute_date, int time, String reason,
			String admin) {
		profileId.notifyMute(mute_date, time, reason, admin);
	}

	/**
	 * @param agent
	 *            the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @param avatar
	 */
	public void setAvatar(byte avatar) {
		profileId.setAvatar(avatar);
	}

	/**
	 * @param cookie
	 *            the cookie to set
	 */
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	/**
	 * @param declineAllInviations
	 *            the declineAllInviations to set
	 */
	public void setDeclineAllInviations(boolean declineAllInviations) {
		this.declineAllInviations = declineAllInviations;
	}

	public void setGlobalFlags(long value) {
		profileId.notifyFlags(value);
	}

	/**
	 * @param imFromFriendsOnly
	 *            the imFromFriendsOnly to set
	 */
	public void setImFromFriendsOnly(boolean imFromFriendsOnly) {
		this.imFromFriendsOnly = imFromFriendsOnly;
	}

	/**
	 * @param intl_code
	 *            the intl_code to set
	 */
	public void setIntl_code(String intl_code) {
		this.intl_code = intl_code;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @param lastRequestTime
	 *            the lastRequestTime to set
	 */
	public void setLastRequestTime(long lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}

	public void setLocalFlags(long value) {
		flags = value;
	}

	public void setProfileId(YahooProfileId value) {
		if (profileId != value) {
			if (profileId != null)
				profileId.removeListener(this);
			profileId = value;
			if (profileId != null)
				profileId.addListener(this);
		}
	}

	public void setProperty(String name, String value) {
		profileId.setProperty(name, value);
	}

	/**
	 * @param value
	 */
	public void setRating(int value) {
		profileId.setRating(value);
	}

	/**
	 * @param value
	 * @throws IOException
	 */
	public void setRoom(YahooRoom value) throws IOException {
		if (room != null)
			room.removeId(this);
		room = value;
		bufOut.open(room.getYport(), room.getIndex());
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @param ycookie
	 *            the ycookie to set
	 */
	public void setYcookie(String ycookie) {
		this.ycookie = ycookie;
	}

	/**
	 * @param abyte0
	 * @see server.io.YahooOutputStream#write(byte[])
	 */
	public void write(byte[] abyte0) {
		if (out != null)
			out.write(abyte0);
	}

	/**
	 * @param abyte0
	 * @param i
	 * @param j
	 * @see server.io.YahooOutputStream#write(byte[], int, int)
	 */
	public void write(byte[] abyte0, int i, int j) {
		if (out != null)
			out.write(abyte0, i, j);
	}

	/**
	 * @param i
	 * @see server.io.YahooOutputStream#write(int)
	 */
	public void write(int i) {
		if (out != null)
			out.write(i);
	}

	/**
	 * @param flag
	 * @see server.io.YahooOutputStream#writeBoolean(boolean)
	 */
	public void writeBoolean(boolean flag) {
		if (out != null)
			out.writeBoolean(flag);
	}

	/**
	 * @param i
	 * @see server.io.YahooOutputStream#writeByte(int)
	 */
	public void writeByte(int i) {
		if (out != null)
			out.writeByte(i);
	}

	/**
	 * @param s
	 * @see server.io.YahooOutputStream#writeBytes(java.lang.String)
	 */
	public void writeBytes(String s) {
		if (out != null)
			out.writeBytes(s);
	}

	/**
	 * @param i
	 * @see server.io.YahooOutputStream#writeChar(int)
	 */
	public void writeChar(int i) {
		if (out != null)
			out.writeChar(i);
	}

	/**
	 * @param s
	 * @see server.io.YahooOutputStream#writeChars(java.lang.String)
	 */
	public void writeChars(String s) {
		if (out != null)
			out.writeChars(s);
	}

	/**
	 * @param _pcls111
	 * @see server.io.YahooOutputStream#writeData(common.io.YData)
	 */
	public void writeData(YData _pcls111) {
		if (out != null)
			out.writeData(_pcls111);
	}

	/**
	 * @param d
	 * @see server.io.YahooOutputStream#writeDouble(double)
	 */
	public void writeDouble(double d) {
		if (out != null)
			out.writeDouble(d);
	}

	/**
	 * @param f
	 * @see server.io.YahooOutputStream#writeFloat(float)
	 */
	public void writeFloat(float f) {
		if (out != null)
			out.writeFloat(f);
	}

	/**
	 * @param hashtable
	 * @see server.io.YahooOutputStream#writeHashtable(java.util.Hashtable)
	 */
	public void writeHashtable(Hashtable<String, String> hashtable) {
		if (out != null)
			out.writeHashtable(hashtable);
	}

	/**
	 * @param i
	 * @see server.io.YahooOutputStream#writeInt(int)
	 */
	public void writeInt(int i) {
		if (out != null)
			out.writeInt(i);
	}

	/**
	 * @param l
	 * @see server.io.YahooOutputStream#writeLong(long)
	 */
	public void writeLong(long l) {
		if (out != null)
			out.writeLong(l);
	}

	/**
	 * @param i
	 * @see server.io.YahooOutputStream#writeShort(int)
	 */
	public void writeShort(int i) {
		if (out != null)
			out.writeShort(i);
	}

	/**
	 * @param s
	 * @see server.io.YahooOutputStream#writeUTF(java.lang.String)
	 */
	public void writeUTF(String s) {
		if (out != null)
			out.writeUTF(s);
	}

	/**
	 * @param vector
	 * @see server.io.YahooOutputStream#writeVector(java.util.Vector)
	 */
	public void writeVector(Vector<String> vector) {
		if (out != null)
			out.writeVector(vector);
	}

}
