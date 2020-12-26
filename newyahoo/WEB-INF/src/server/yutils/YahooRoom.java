package server.yutils;

import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Vector;

import server.io.YahooConnectionId;

import common.utils.SynchronizedVector;
import common.yutils.YahooUtils;

import data.MySQLTable;

public abstract class YahooRoom {

	private int										index;
	private String									yport;
	private SynchronizedVector<YahooConnectionId>	ids;
	private Hashtable<String, YahooConnectionId>	idTable;
	private YahooTable[]							tables;
	protected YahooRoomHandler						handler;

	public YahooRoom(YahooRoomHandler handler, int index, String yport) {
		this.handler = handler;
		this.index = index;
		this.yport = yport;
		ids = new SynchronizedVector<YahooConnectionId>();
		idTable = new Hashtable<String, YahooConnectionId>();
		tables = new YahooTable[256];
		for (int i = 0; i < tables.length; i++)
			tables[i] = createTable(i);
		Hashtable<String, String> properties = new Hashtable<String, String>();
		tables[0].open(null, properties);
	}

	public void addId(final YahooConnectionId id) {
		String name = id.getName();
		YahooConnectionId id1 = idTable.get(name);
		if (id1 != null) {
			alert(id1,
			"A new connection has established with your login on the room");
			removeId(id1);
			alert(id, "Your previous connection has been closed");
		}

		String welcomeMsg = "";

		ResultSet rs = null;
		try {
			rs = getRooms().getAllValues(new String[] { "name" },
					new Object[] { yport });
			if (rs.next()) {
				welcomeMsg = "Welcome to room " + rs.getString("label");
				String s1 = rs.getString("welcome_msg");
				if (s1 != null && !s1.equals(""))
					welcomeMsg += "\r\n" + s1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				getRooms().closeResultSet(rs);
				rs = null;
			}
		}

		ids.readLock();
		try {
			for (YahooConnectionId id2 : ids) {
				String name2 = id2.getName();
				enterId(id, name2, name2);
				if (id2.isAllStarMemberShip())
					changePublicFlags(id, name2, id2.getMoreFlags());
				changeAvatar(id, id2.getAvatar(), name2);
			}
		}
		finally {
			ids.readUnlock();
		}

		ids.writeLock();
		try {
			ids.add(id);
			idTable.put(name, id);
		}
		finally {
			ids.writeUnlock();
		}

		ids.readLock();
		try {
			getRooms().assyncUpdate(new String[] { "name" },
					new Object[] { yport }, new String[] { "id_count" },
					new Object[] { ids.size() });

			byte avatar = id.getAvatar();

			for (YahooConnectionId id2 : ids) {
				enterId(id2, name, name);
				if (id.isAllStarMemberShip())
					changePublicFlags(id2, name, id.getMoreFlags());
				changeAvatar(id2, avatar, name);
			}
		}
		finally {
			ids.readUnlock();
		}

		for (int i = 1; i < tables.length; i++) {
			if (tables[i].isFree())
				continue;

			int number = tables[i].getNumber();

			makeTable(id, number, tables[i].getProperties(), false);

			SynchronizedVector<YahooConnectionId> tableIds = tables[i].getIds();
			tableIds.readLock();
			try {
				for (YahooConnectionId id2 : tableIds)
					joinTable(id, id2.getName(), number);
			}
			finally {
				tableIds.readUnlock();
			}

			YahooConnectionId[] sits = tables[i].getSits();
			for (int j = 0; j < sits.length; j++) {
				if (sits[j] == null)
					continue;
				sit(id, number, j, sits[j].getName());
			}
		}

		// blue message
		blueMessage(id, welcomeMsg);

		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++)
				chageRating(ids.elementAt(i), id.getName(), id.getRating());
		}
		finally {
			ids.readUnlock();
		}

		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++)
				chageRating(id, ids.elementAt(i).getName(), ids.elementAt(i)
						.getRating());
		}
		finally {
			ids.readUnlock();
		}

		// amigos
		synchronized (id) {
			id.write('1');
			id.writeVector(id.getFriends());
			id.flush();
		}

		// ignorados
		synchronized (id) {
			id.write('4');
			id.writeVector(id.getIgnoreds());
			id.flush();
		}

		id.loadValues(new String[] { "games_common_sound", "prowler_g",
				"games_common_profanity", "games_common_hidestar",
				"games_common_smallwindows", "games_common_automove" },
				new ActionLoadValue() {
			public void onLoad(String name, String value) {
				changeIdProperty(id, name, value);
			}
		});

		// id flags
		changeFlags(id, id.getFlags());
	}

	public void alert(YahooConnectionId id, String message) {
		synchronized (id) {
			id.write('a');
			id.writeUTF(message);
			id.flush();
		}
	}

	public void ban(String name, int ban_type, Timestamp ban_date,
			int ban_time, String reason, String admin) {
		MySQLTable ignoreds_table = getIgnoredsTable();

		ResultSet rs = null;
		boolean flag = false;
		String ip = null;
		try {
			rs = ignoreds_table.executeQuery("SELECT * FROM "
					+ ignoreds_table.name + " WHERE name="
					+ MySQLTable.formatValue(name),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			if (rs.next()) {
				ip = rs.getString("ip");
				rs.updateTimestamp("ban_date", ban_date);
				rs.updateInt("ban_type", ban_type);
				rs.updateLong("ban_time", ban_time);
				rs.updateString("reason", reason);
				rs.updateString("admin", admin);
				rs.updateRow();
				flag = true;
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
		if (ip == null) {
			MySQLTable profile_table = getProfileTable();
			try {
				rs = profile_table.getValue(new String[] { "name" },
						new Object[] { name }, new String[] { "ip" });
				if (rs.next())
					ip = rs.getString("ip");
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

			if (ip == null) {
				MySQLTable ids_table = getIdsTable();
				try {
					rs = ids_table.getValue(new String[] { "name" },
							new Object[] { name }, new String[] { "ip" });
					if (rs.next())
						ip = rs.getString("ip");
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
		}
		if (ip == null)
			ip = "0.0.0.0";
		if (!flag)
			ignoreds_table.assyncInsert(new Object[] { name, ban_date,
					ban_type, ban_time, reason, admin, ip });
		ignoreds_table.assyncUpdate(new String[] { "ip" }, new Object[] { ip },
				new String[] { "ban_date", "ban_type", "ban_time", "reason",
		"admin" }, new Object[] { ban_date, ban_type, ban_time,
				reason, admin });
	}

	/**
	 * @param id
	 * @param message
	 */
	public void blueMessage(YahooConnectionId id, String message) {
		synchronized (id) {
			id.write('m');
			id.writeUTF(message);
			id.flush();
		}
	}

	public void boot(YahooConnectionId id, int number, String name) {
		synchronized (id) {
			id.write('q');
			id.write(number);
			id.writeUTF(name);
			id.flush();
		}
	}

	public void chageRating(YahooConnectionId id, String name, int rating) {
		synchronized (id) {
			id.write('t');
			id.writeUTF(name);
			id.writeShort(rating);
			id.flush();
		}
	}

	public void changeAvatar(YahooConnectionId id, byte avatar, String name) {
		synchronized (id) {
			id.write('o');
			id.write(avatar);
			id.writeUTF(name);
			id.flush();
		}
	}

	public void changeFlags(YahooConnectionId id, long value) {
		synchronized (id) {
			id.write('f');
			id.writeLong(value);
			id.flush();
		}
	}

	public void changeIdProperty(YahooConnectionId id, String key, String value) {
		synchronized (id) {
			id.write(20);
			id.writeUTF(key);
			id.writeUTF(value);
			id.flush();
		}
	}

	/**
	 * @param id
	 * @param name
	 * @param flags
	 */
	public void changePublicFlags(YahooConnectionId id, String name, int flags) {
		synchronized (id) {
			id.write('w');
			id.writeUTF(name);
			id.writeInt(flags);
			id.flush();
		}
	}

	public void changeTablePrivacy(YahooConnectionId id, int number, int privacy) {
		synchronized (id) {
			id.write('p');
			id.write(number);
			id.write(privacy);
			id.flush();
		}
	}

	public void changeTableProperties(YahooConnectionId id, int table,
			Hashtable<String, String> properties) {
		synchronized (id) {
			id.write('0');
			id.writeByte(table);
			id.writeHashtable(properties);
			id.flush();
		}
	}

	public void chat(YahooConnectionId id, String name, String message) {
		synchronized (id) {
			id.write('c');
			id.writeUTF(name);
			id.writeUTF(message);
			id.flush();
		}
	}

	public void close() {
		if (ids != null) {
			ids.clear();
			ids = null;
		}
		if (idTable != null) {
			idTable.clear();
			idTable = null;
		}
		if (tables != null) {
			for (YahooTable table : tables)
				table.close();
			tables = null;
		}
		handler = null;
	}

	public abstract YahooTable createTable(int number);

	public void declineInvite(YahooConnectionId id, String name, String reason) {
		synchronized (id) {
			id.write('v');
			id.writeUTF(name);
			id.writeUTF(reason);
			id.flush();
		}
	}

	public void doBan(YahooConnectionId id, String name, int time, String reason) {
		if (!id.isAdmin())
			return;
		Timestamp ban_date = new Timestamp(System.currentTimeMillis());
		YahooConnectionId id1 = idTable.get(name);
		if (id1 != null)
			id1.notifyBan(ban_date, time, reason, id.getName());
		else
			ban(name, IgnoredEntry.BAN, ban_date, time, reason, id.getName());
	}

	public void doBoot(YahooConnectionId id, int number, String name) {
		ids.readLock();
		try {
			for (YahooConnectionId id1 : ids) {
				if (!name.equals(id1.getName()))
					continue;
				boot(id1, number, id.getName());
				break;
			}
		}
		finally {
			ids.readUnlock();
		}
	}

	public void doChangeIdProperty(YahooConnectionId id, String key,
			String value) {
		FloodRecord changeIdPropertyFloodRecord = id
		.getChangeIdPropertyFloodRecord();
		long time = changeIdPropertyFloodRecord.log();
		if (changeIdPropertyFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"change id property flood", "auto");
			return;
		}

		id.setProperty(key, value);
		changeIdProperty(id, key, value);
	}

	/**
	 * @param number
	 * @param privacy
	 */
	public void doChangeTablePrivacy(YahooConnectionId id, int number,
			int privacy) {
		ids.readLock();
		try {
			for (YahooConnectionId id1 : ids)
				changeTablePrivacy(id1, number, privacy);
		}
		finally {
			ids.readUnlock();
		}
	}

	public void doChangeTableProperties(int number, int table,
			Hashtable<String, String> properties) {
		ids.readLock();
		try {
			for (YahooConnectionId id1 : ids)
				changeTableProperties(id1, table, properties);
		}
		finally {
			ids.readUnlock();
		}
	}

	public void doChat(YahooConnectionId id, String message) {
		IgnoredEntry entry = id.getIgnoredEntry();
		if (entry != null && entry.type == IgnoredEntry.MUTE)
			return;

		FloodRecord chatFloodRecord = id.getChatFloodRecord();
		long time = chatFloodRecord.log();
		if (chatFloodRecord.isFlood()) {
			id.notifyMute(new Timestamp(time), 30, "chat flood",
			"auto");
			return;
		}

		String idName = id.getName();
		ids.readLock();
		try {
			for (YahooConnectionId id1 : ids) {
				if (id1.isIgnored(idName))
					continue;
				chat(id1, idName, message);
			}
		}
		finally {
			ids.readUnlock();
		}
	}

	public void doClientStatus(YahooConnectionId id, String value) {
		// System.out.println("Client status sent by " + id.getName() + ": "
		// + value);
	}

	public void doDeclineAllInviations(YahooConnectionId id, boolean flag) {
		id.setDeclineAllInviations(flag);
	}

	public void doDeclineInvite(YahooConnectionId id, String name, String reason) {
		FloodRecord declineInviteFloodRecord = id.getDeclineInviteFloodRecord();
		long time = declineInviteFloodRecord.log();
		if (declineInviteFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"decline invite flood", "auto");
			return;
		}

		YahooConnectionId id1 = idTable.get(name);
		if (id1 == null)
			return;
		declineInvite(id1, id.getName(), reason);
	}

	@SuppressWarnings("unchecked")
	public void doExitId(YahooConnectionId id) {
		ids.writeLock();
		try {
			if (!ids.remove(id))
				return;
		}
		finally {
			ids.writeUnlock();
		}
		String name = id.getName();
		idTable.remove(name);
		Vector<YahooTable> clone = (Vector<YahooTable>) id.getTables().clone();
		for (YahooTable table : clone)
			doLeaveTable(id, table.getNumber());
		ids.readLock();
		try {
			for (YahooConnectionId id1 : ids)
				exitId(id1, name);
			getRooms().assyncUpdate(new String[] { "name" },
					new Object[] { yport }, new String[] { "id_count" },
					new Object[] { ids.size() });
		}
		finally {
			ids.readUnlock();
		}
	}

	public void doIgnore(YahooConnectionId id, String name) {
		id.ignore(name);
	}

	public void doImFromFriendsOnly(YahooConnectionId id, boolean flag) {
		id.setImFromFriendsOnly(flag);
		imFromFriendsOnly(id, flag);
	}

	public void doInformation(YahooConnectionId id, String name) {
		YahooConnectionId id1 = idTable.get(name);
		if (id1 == null)
			return;
		information(id, name, id1.getProfile(), (int) (System
				.currentTimeMillis() - id.getLastRequestTime()));
	}

	public boolean doInvite(YahooConnectionId id, String name, int table) {
		if (table < 1 || table > 255)
			return false;

		FloodRecord inviteFloodRecord = id.getInviteFloodRecord();
		long time = inviteFloodRecord.log();
		if (inviteFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"invite flood", "auto");
			return false;
		}		

		String idName = id.getName();
		YahooConnectionId id1 = idTable.get(name);
		if (id1 == null) {
			alert(id, name + " não foi encontrado no servidor");
			return false;
		}
		if (id1.isDeclineAllInviations()) {
			alert(id, name + " não está aceitando convites");
			return false;
		}
		if (id1.isIgnored(idName))
			return false;
		invite(id1, idName, table);
		return true;
	}

	public boolean doJoinTable(YahooConnectionId id, int table) {
		if (table < 1 || table > 255)
			return false;

		return tables[table].joinId(id);
	}

	public boolean doLeaveTable(YahooConnectionId id, int table) {
		if (table < 1 || table > 255)
			return false;

		return tables[table].leaveId(id);
	}

	public boolean doMakeTable(YahooConnectionId id,
			Hashtable<String, String> hashtable) {
		for (int i = 1; i < tables.length; i++)
			if (tables[i].open(id, hashtable))
				return true;
		alert(id, "O número máximo de mesas já foi atingido");
		return false;
	}

	public void doMute(YahooConnectionId id, String name, int time,
			String reason) {
		if (!id.isAdmin())
			return;
		Timestamp ban_date = new Timestamp(System.currentTimeMillis());
		YahooConnectionId id1 = idTable.get(name);
		if (id1 != null)
			id1.notifyMute(ban_date, time, reason, id.getName());
		else
			ban(name, IgnoredEntry.MUTE, ban_date, time, reason, id.getName());
	}

	public void doneTable(YahooConnectionId id, int table) {
		synchronized (id) {
			id.write('d');
			id.write(table);
			id.flush();
		}
	}

	public void doNoIgnore(YahooConnectionId id, String name) {
		id.noIgnore(name);
	}

	public void doPing(YahooConnectionId id, String name) {
		FloodRecord pingFloodRecord = id.getPingFloodRecord();
		long time = pingFloodRecord.log();
		if (pingFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"ping flood", "auto");
			return;
		}

		YahooConnectionId id1 = idTable.get(name);
		if (id1 == null)
			return;
		pingDispatched(id, name);
		ping(id1, id.getName());
	}

	public void doPlayNow(YahooConnectionId id) {
		for (int i = 1; i < tables.length; i++) {
			if (!doJoinTable(id, i))
				continue;
			return;
		}
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("rd", "");
		doMakeTable(id, hashtable);
	}

	public void doPong(YahooConnectionId id, String name) {
		FloodRecord pongFloodRecord = id.getPongFloodRecord();
		long time = pongFloodRecord.log();
		if (pongFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"pong flood", "auto");
			return;
		}

		YahooConnectionId id1 = idTable.get(name);
		if (id1 == null)
			return;
		pong(id1, id.getName());
	}

	public void doPrivateChat(YahooConnectionId id, String name, String msg) {
		IgnoredEntry entry = id.getIgnoredEntry();
		if (entry != null && entry.type == IgnoredEntry.MUTE)
			return;

		FloodRecord privateChatFloodRecord = id.getPrivateChatFloodRecord();
		long time = privateChatFloodRecord.log();
		if (privateChatFloodRecord.isFlood()) {
			id.notifyMute(new Timestamp(time), 30,
					"private chat flood", "auto");
			return;
		}

		String idName = id.getName();
		YahooConnectionId id1 = idTable.get(name);
		if (id1 == null)
			return;
		if (id1.isImFromFriendsOnly() && !id1.isFriend(idName))
			return;
		if (id1.isIgnored(idName))
			return;
		privateChat(id1, idName, msg);
	}

	public void doSetAvatar(YahooConnectionId id, byte avatar) {
		FloodRecord setAvatarFloodRecord = id.getSetAvatarFloodRecord();
		long time = setAvatarFloodRecord.log();
		if (setAvatarFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"set avatar flood", "auto");
			return;
		}

		if (!id.isAllStarMemberShip() && avatar > 34) {
			id.close();
			return;
		}
		id.setAvatar(avatar);
	}

	public void doWatch(YahooConnectionId id, String name) {
		ids.readLock();
		try {
			if (!ids.contains(name)) {
				alert(id, name + " não está na sala");
				return;
			}
			YahooConnectionId id1 = ids.elementAt(ids.indexOf(name));
			Vector<YahooTable> idTables = id1.getTables();
			if (idTables.size() == 0) {
				alert(id, name + " está na sala");
				return;
			}
			doJoinTable(id, idTables.elementAt(0).getNumber());
		}
		finally {
			ids.readUnlock();
		}
	}

	public void enterId(YahooConnectionId id, String name, String caption) {
		synchronized (id) {
			id.write('e');
			id.writeUTF(name);
			id.writeUTF(caption);
			id.flush();
		}
	}

	public void exitId(YahooConnectionId id, String name) {
		synchronized (id) {
			id.write('x');
			id.writeUTF(name);
			id.flush();
		}
	}

	/**
	 * @return tabela de log de jogos
	 */
	public abstract MySQLTable getGameLogTable();

	/**
	 * @return the ids
	 */
	public SynchronizedVector<YahooConnectionId> getIds() {
		return ids;
	}

	public MySQLTable getIdsTable() {
		return handler.getTable("ids");
	}

	protected abstract MySQLTable getIgnoredsTable();

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	public abstract MySQLTable getProfileTable();

	protected abstract MySQLTable getRooms();

	/**
	 * @return the tables
	 */
	public YahooTable[] getTables() {
		return tables;
	}

	/**
	 * @return the yport
	 */
	public String getYport() {
		return yport;
	}

	/**
	 * @param id
	 * @param flag
	 */
	public void imFromFriendsOnly(YahooConnectionId id, boolean flag) {
		synchronized (id) {
			id.write('6');
			id.writeBoolean(flag);
			id.flush();
		}
	}

	public void information(YahooConnectionId id, String name, String profile,
			int idle) {
		synchronized (id) {
			id.write('y');
			id.writeUTF(name);
			id.writeUTF(profile);
			id.writeInt(idle);
			id.flush();
		}
	}

	public void invite(YahooConnectionId id, String name, int table) {
		synchronized (id) {
			id.write('i');
			id.write(table);
			id.writeUTF(name);
			id.flush();
		}
	}

	public void joinTable(YahooConnectionId id, String name, int table) {
		synchronized (id) {
			id.write('j');
			id.writeUTF(name);
			id.write(table);
			id.flush();
		}
	}

	public void leaveTable(YahooConnectionId id, String name, int table) {
		synchronized (id) {
			id.write('l');
			id.writeUTF(name);
			id.write(table);
			id.flush();
		}
	}

	public void makeTable(YahooConnectionId id, int number,
			Hashtable<String, String> properties, boolean flag) {
		synchronized (id) {
			id.write('n');
			id.write(number);
			id.writeHashtable(properties);
			id.writeBoolean(flag);
			id.flush();
		}
	}

	public void parseData(YahooConnectionId id, byte byte0,
			DataInputStream input) throws IOException {
		if (byte0 != 'H')
			id.setLastRequestTime(System.currentTimeMillis());
		int table;
		String name;
		String message;
		String key;
		String value;
		String reason;
		boolean flag;
		int time;
		switch (byte0) {
		case '!': // keep-alive
			break;
		case 'C': // chat
			message = input.readUTF();
			doChat(id, message);
			break;
		case 'E': // y error
			String error = "";
			do {
				char c = (char) input.read();
				if (c == 0)
					break;
				error += c;
			}
			while (true);
			System.err.println("Client error sent by " + id.getName() + ";\n\n"
					+ error);
			break;
		case 'F': // change avatar
			byte avatar = input.readByte();
			doSetAvatar(id, avatar);
			break;
		case 'G': // ping
			name = input.readUTF();
			doPing(id, name);
			break;
		case 'H': // response ping
			name = input.readUTF();
			doPong(id, name);
			break;
		case 'I': // information
			name = input.readUTF();
			doInformation(id, name);
			break;
		case 'J': // join table
			table = YahooUtils.readUnsignedByte(input);
			doJoinTable(id, table);
			break;
		case 'L': // leave table
			table = YahooUtils.readUnsignedByte(input);
			doLeaveTable(id, table);
			break;
		case 'N': // new table
			Hashtable<String, String> hashtable = YahooUtils
			.readHashtable(input);
			doMakeTable(id, hashtable);
			break;
		case 'P': // watch
			name = input.readUTF();
			doWatch(id, name);
			break;
		case 'Q': // play now
			doPlayNow(id);
			break;
		case 'V': // decline invite
			name = input.readUTF();
			reason = input.readUTF();
			doDeclineInvite(id, name, reason);
			break;
		case 'X': // exit id
			removeId(id);
			break;
		case '+': // table command
			table = YahooUtils.readUnsignedByte(input);
			if (tables[table].isFree())
				break;
			SynchronizedVector<YahooConnectionId> tableIds = tables[table]
			                                                        .getIds();
			tableIds.readLock();
			try {
				flag = tableIds.contains(id);
			}
			finally {
				tableIds.readUnlock();
			}
			if (flag)
				tables[table].parseData(id, input.readByte(), input);
			else
				tables[0].parseData(id, input.readByte(), input);
			break;
		case '#': // private chat
			name = input.readUTF();
			message = input.readUTF();
			doPrivateChat(id, name, message);
			break;
		case '~': // y status
			value = input.readUTF();
			doClientStatus(id, value);
			break;
		case '_': // ban
			name = input.readUTF();
			time = input.readInt();
			reason = input.readUTF();
			doBan(id, name, time, reason);
			break;
		case '&': // decline all inviations
			flag = input.readBoolean();
			doDeclineAllInviations(id, flag);
			break;
		case '$': // ignore
			name = input.readUTF();
			doIgnore(id, name);
			break;
		case '%': // no ignore
			name = input.readUTF();
			doNoIgnore(id, name);
			break;
		case '(': // mute
			name = input.readUTF();
			time = input.readInt();
			reason = input.readUTF();
			doMute(id, name, time, reason);
			break;
		case '^': // im from friends only
			flag = input.readBoolean();
			doImFromFriendsOnly(id, flag);
			break;
		case 20: // change id property
			key = input.readUTF();
			value = input.readUTF();
			doChangeIdProperty(id, key, value);
			break;
		default:
			throw new IllegalArgumentException("Illegal command: " + byte0
					+ "(" + (char) byte0 + ")");
		}
	}

	public void ping(YahooConnectionId id, String name) {
		synchronized (id) {
			id.write('g');
			id.writeUTF(name);
			id.flush();
		}
	}

	public void pingDispatched(YahooConnectionId id, String name) {
		synchronized (id) {
			id.write('k');
			id.writeUTF(name);
			id.flush();
		}
	}

	public void pong(YahooConnectionId id, String name) {
		synchronized (id) {
			id.write('h');
			id.writeUTF(name);
			id.flush();
		}
	}

	public void privateChat(YahooConnectionId id, String name, String msg) {
		synchronized (id) {
			id.write('3');
			id.writeUTF(name);
			id.writeUTF(msg);
			id.flush();
		}
	}

	public void removeId(YahooConnectionId id) {
		id.close();
	}

	public void sit(YahooConnectionId id, int number, int sitIndex, String name) {
		synchronized (id) {
			id.write('s');
			id.write(number);
			id.write(sitIndex);
			id.writeUTF(name);
			id.flush();
		}
	}

}
