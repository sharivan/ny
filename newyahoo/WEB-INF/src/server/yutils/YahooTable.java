package server.yutils;

import java.io.DataInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Vector;

import javax.sql.rowset.serial.SerialBlob;

import server.io.YahooConnectionId;

import common.io.YData;
import common.utils.ByteArrayData;
import common.utils.IOBuffer;
import common.utils.SynchronizedVector;
import common.yutils.Game;
import common.yutils.GameHandler;
import common.yutils.GameHistory;

import data.MySQLTable;

/*
 * Continuando...
 */

public abstract class YahooTable implements GameHandler {

	public static int[] computeRating(int[] oldRating, int[] turnResult) {
		int[] newRating = new int[oldRating.length];
		System.arraycopy(oldRating, 0, newRating, 0, oldRating.length);

		int winnerSum = 0;
		int winnerCount = 0;
		int loserSum = 0;
		int loserCount = 0;
		int drawAvg = 0;
		int drawCount = 0;
		for (int i = 0; i < turnResult.length; i++)
			if (turnResult[i] == 1) {
				winnerSum += oldRating[i];
				winnerCount++;
			}
			else if (turnResult[i] == 2) {
				loserSum += oldRating[i];
				loserCount++;
			}
			else {
				drawAvg += oldRating[i];
				drawCount++;
			}
		int noDrawCount = winnerCount + loserCount;
		int noDrawAvg;
		if (noDrawCount > 0)
			noDrawAvg = (winnerSum + loserSum) / noDrawCount;
		else
			noDrawAvg = 0;
		if (drawCount > 0)
			drawAvg /= drawCount;
		long drawSd = 0;
		long noDrawSd = 0;
		for (int i = 0; i < turnResult.length; i++)
			if (turnResult[i] == 1 || turnResult[i] == 2) {
				int delta = oldRating[i] - noDrawAvg;
				noDrawSd += delta * delta;
			}
			else {
				int delta = oldRating[i] - drawAvg;
				drawSd += delta * delta;
			}
		if (noDrawCount > 0) {
			noDrawSd /= noDrawCount;
			noDrawSd = (int) Math.sqrt(noDrawSd);
		}
		if (drawCount > 0) {
			drawSd /= drawCount;
			drawSd = (int) Math.sqrt(drawSd);
		}
		byte signal1 = (byte) (winnerSum >= loserSum ? 1 : -1);
		int delta1 = (int) (20 - 40 * Math.atan(signal1 * noDrawSd / 300D)
				/ Math.PI);
		int delta2 = (int) (5 - 10 * Math.atan(drawSd / 300D) / Math.PI);
		for (int i = 0; i < oldRating.length; i++) {
			if (turnResult[i] == 1)
				newRating[i] += delta1;
			else if (turnResult[i] == 2)
				newRating[i] -= delta1;
			else if (newRating[i] > drawAvg)
				newRating[i] -= delta2;
			else
				newRating[i] += delta2;
		}

		return newRating;
	}

	public static byte[] int2bytes(int[] value) {
		if (value == null)
			return null;
		byte[] result = new byte[value.length * 4];
		int j = 0;
		for (int element : value) {
			result[j++] = (byte) ((element & 0xff000000) >> 24);
			result[j++] = (byte) ((element & 0xff0000) >> 16);
			result[j++] = (byte) ((element & 0xff00) >> 8);
			result[j++] = (byte) (element & 0xff);
		}
		return result;
	}

	protected YahooRoom								room;
	protected int									number;
	protected Hashtable<String, String>				properties;
	private boolean									free;
	protected SynchronizedVector<YahooConnectionId>	ids;
	protected YahooConnectionId[]					sits;
	private long									sitTime[];
	private boolean[]								sitState;
	private Game									game;
	private Hashtable<String, String>				booteds;
	private Hashtable<String, String>				inviteds;
	private Vector<GameHistory>						gameLog;
	protected GameHistory							currGameLogEntry;
	protected YahooConnectionId[]					startedPlayers;
	private String									host;
	private int										privacy;
	private int										finishedGameCount;

	public YahooTable(YahooRoom room, int number) {
		this.room = room;
		this.number = number;
		finishedGameCount = 1;
		free = true;
		privacy = 0;
		ids = new SynchronizedVector<YahooConnectionId>();
		booteds = new Hashtable<String, String>();
		inviteds = new Hashtable<String, String>();
		gameLog = new Vector<GameHistory>();
		currGameLogEntry = null;
		startedPlayers = new YahooConnectionId[getSitCount()];
		host = null;
	}

	public void begin(YahooConnectionId id) {
		synchronized (id) {
			writeHeader(id);
			id.write('b');
			id.flush();
		}
	}

	public void cancelRequest(YahooConnectionId id, int sitIndex, boolean save) {
		synchronized (id) {
			writeHeader(id);
			id.write('=');
			id.writeByte(sitIndex);
			id.writeBoolean(save);
			id.flush();
		}
	}

	public void cancelResponse(YahooConnectionId id, boolean save, String name) {
		synchronized (id) {
			writeHeader(id);
			id.write('x');
			id.writeBoolean(save);
			id.writeUTF(name);
			id.flush();
		}
	}

	public void changeAvatar(YahooConnectionId id, int sitIndex, byte avatar) {
		synchronized (id) {
			writeHeader(id);
			id.write('8');
			id.write(sitIndex);
			id.write(avatar);
			id.flush();
		}
	}

	public void changeHost(YahooConnectionId id, String name) {
		synchronized (id) {
			writeHeader(id);
			id.write('5');
			id.writeUTF(name);
			id.flush();
		}
	}

	public void chat(YahooConnectionId id, String name, String message) {
		synchronized (id) {
			writeHeader(id);
			id.write('c');
			id.writeUTF(name);
			id.writeUTF(message);
			id.flush();
		}
	}

	public void close() {
		room = null;

		if (properties != null) {
			properties.clear();
			properties = null;
		}
		if (ids != null) {
			ids.clear();
			ids = null;
		}

		sits = null;
		sitTime = null;
		sitState = null;
		if (game != null) {
			game.close();
			game = null;
		}
		if (booteds != null) {
			booteds.clear();
			booteds = null;
		}
		if (inviteds != null) {
			inviteds.clear();
			inviteds = null;
		}
		if (gameLog != null) {
			gameLog.clear();
			gameLog = null;
		}
		if (currGameLogEntry != null) {
			currGameLogEntry.close();
			currGameLogEntry = null;
		}
		startedPlayers = null;
	}

	protected abstract Game createGame();

	public void doAuto(YahooConnectionId id) {
		room.alert(id, "Bots not allowed for this game.");
	}

	public boolean doBoot(YahooConnectionId id, String name) {
		if (!isHost(id)) {
			room.alert(id, "You are not the host of table");
			return false;
		}
		booteds.put(name, name);
		doTableLog(name + " foi expulso da mesa por " + id.getName());
		room.doBoot(id, number, name);
		return true;
	}

	public boolean doCancelRequest(YahooConnectionId id, int sitIndex,
			boolean save) {
		FloodRecord cancelRequestFloodRecord = id.getCancelRequestFloodRecord();
		long time = cancelRequestFloodRecord.log();
		if (cancelRequestFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"cancel request flood", "auto");
			return false;
		}

		synchronized (game) {
			if (!game.isRunning())
				return false;

			ids.readLock();
			try {
				for (int i = 0; i < ids.size(); i++)
					cancelRequest(ids.elementAt(i), sitIndex, save);
			}
			finally {
				ids.readUnlock();
			}
			return true;
		}
	}

	public boolean doCancelRespose(YahooConnectionId id, boolean accept,
			boolean save, String name) {
		FloodRecord cancelResponseFloodRecord = id
		.getCancelResponseFloodRecord();
		long time = cancelResponseFloodRecord.log();
		if (cancelResponseFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"cancel response flood", "auto");
			return false;
		}

		synchronized (game) {
			if (!game.isRunning())
				return false;
			if (accept) {
				ids.readLock();
				try {
					for (int i = 0; i < ids.size(); i++)
						cancelResponse(ids.elementAt(i), save, name);
				}
				finally {
					ids.readUnlock();
				}
			}
			else {
				// TODO implementar
			}
			return true;
		}
	}

	/**
	 * 
	 */
	public void doChangeAvatar(int sitIndex, byte avatar) {
		ids.readLock();
		try {
			for (YahooConnectionId id : ids)
				changeAvatar(id, sitIndex, avatar);
		}
		finally {
			ids.readUnlock();
		}
	}

	public boolean doChangeTablePrivacy(YahooConnectionId id, int privacy) {
		if (!isHost(id)) {
			room.alert(id, "You are not the host");
			return false;
		}

		FloodRecord changeTablePrivacyFloodRecord = id
		.getChangeTablePrivacyFloodRecord();
		long time = changeTablePrivacyFloodRecord.log();
		if (changeTablePrivacyFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"change table privacy flood", "auto");
			return false;
		}		

		this.privacy = privacy;
		room.doChangeTablePrivacy(id, number, privacy);
		return true;
	}

	public boolean doChat(YahooConnectionId id, String message) {
		IgnoredEntry entry = id.getIgnoredEntry();
		if (entry != null && entry.type == IgnoredEntry.MUTE)
			return false;

		FloodRecord tableChatFloodRecord = id.getTableChatFloodRecord();
		long time = tableChatFloodRecord.log();
		if (tableChatFloodRecord.isFlood()) {
			id.notifyMute(new Timestamp(time), 30,
					"table chat flood", "auto");
			return false;
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

		return true;
	}

	public void doFocus(YahooConnectionId id, int sitIndex, boolean flag) {
		FloodRecord focusFloodRecord = id.getFocusFloodRecord();
		long time = focusFloodRecord.log();
		if (focusFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"focus flood", "auto");
			return;
		}

		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++)
				focus(ids.elementAt(i), sitIndex, flag);
		}
		finally {
			ids.readUnlock();
		}
	}

	public boolean doInvite(YahooConnectionId id, String name) {
		if (!isHost(id)) {
			room.alert(id, "You are not the host");
			return false;
		}
		if (room.doInvite(id, name, number)) {
			inviteds.put(name, name);
			booteds.remove(name);
			return true;
		}
		return false;
	}

	public boolean doResign(YahooConnectionId id, byte sitIndex) {
		synchronized (game) {
			if (!game.isRunning())
				return false;
			game.doResign(sitIndex);

			ids.readLock();
			try {
				for (int i = 0; i < ids.size(); i++)
					resign(ids.elementAt(i), sitIndex);
			}
			finally {
				ids.readUnlock();
			}

			return true;
		}
	}

	public boolean doSit(YahooConnectionId id, int sitIndex) {
		if (sitIndex < 0 || sitIndex > sits.length)
			return false;
		String name = id.getName();
		if (privacy != 0 && !name.equals(host) && !inviteds.contains(name))
			return false;
		for (YahooConnectionId sit : sits)
			if (sit != null && sit.equals(id))
				return false;
		synchronized (sits) {
			if (sits[sitIndex] == null) {
				
				FloodRecord sitFloodRecord = id.getSitFloodRecord();
				long time = sitFloodRecord.log();
				if (sitFloodRecord.isFlood()) {
					id.notifyBan(new Timestamp(time), 1 * 24 * 60,
							"sit flood", "auto");
					return false;
				}				
				
				sits[sitIndex] = id;
				SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
				roomIds.readLock();
				try {
					for (YahooConnectionId id1 : roomIds)
						room.sit(id1, number, sitIndex, name);
				}
				finally {
					roomIds.readUnlock();
				}

				ids.readLock();
				try {
					for (YahooConnectionId id1 : ids)
						sit(id1, sitIndex, id.getAvatar(), name);
				}
				finally {
					ids.readUnlock();
				}

				return true;
			}
		}
		return false;
	}

	public boolean doStand(YahooConnectionId id) {
		for (int j = 0; j < sits.length; j++)
			synchronized (sits) {
				if (sits[j] != null && sits[j].equals(id)) {
					sits[j] = null;

					ids.readLock();
					try {
						for (YahooConnectionId id1 : ids)
							stand(id1, j);
					}
					finally {
						ids.readUnlock();
					}

					SynchronizedVector<YahooConnectionId> roomIds = room
					.getIds();
					roomIds.readLock();
					try {
						for (YahooConnectionId id1 : roomIds)
							room.sit(id1, number, j, "");
					}
					finally {
						roomIds.readUnlock();
					}
					return true;
				}
			}
		return false;
	}

	/**
	 * 
	 */
	protected abstract void doStart();

	public boolean doStartGame(YahooConnectionId id) {
		FloodRecord startFloodRecord = id.getStartFloodRecord();
		long time = startFloodRecord.log();
		if (startFloodRecord.isFlood()) {
			id.notifyBan(new Timestamp(time), 1 * 24 * 60,
					"start flood", "auto");
			return false;
		}

		synchronized (game) {
			if (game.isRunning())
				return false;
			boolean flag = true;
			boolean flag1 = true;
			int sitIndex = -1;
			synchronized (sits) {
				for (int i = 0; i < sits.length; i++) {
					if (sits[i] == null || !sits[i].equals(id)) {
						flag &= sits[i] != null;
						flag1 &= sitState[i] == true;
						continue;
					}
					sitState[i] = true;
					flag &= true;
					flag1 &= true;
					sitIndex = i;

					ids.readLock();
					try {
						for (int j = 0; j < ids.size(); j++)
							sitBegin(ids.elementAt(j), sitIndex);
					}
					finally {
						ids.readUnlock();
					}
				}
			}
			if (flag && sitIndex != -1) {
				if (flag1 || !Game.isRated(properties)) {
					doStart();
					game.setRunning(true);
					for (int i = 0; i < sitState.length; i++)
						sitState[i] = false;

					ids.readLock();
					try {
						for (int i = 0; i < ids.size(); i++) {
							begin(ids.elementAt(i));
							sitBegin(ids.elementAt(i), -1);
						}
					}
					finally {
						ids.readUnlock();
					}
					return true;
				}
				return false;
			}

			ids.readLock();
			try {
				for (int j = 0; j < ids.size(); j++)
					sitBegin(ids.elementAt(j), -1);
			}
			finally {
				ids.readUnlock();
			}

			return false;
		}
	}

	public void doTableLog(String message) {
		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++)
				tableLog(ids.elementAt(i), message);
		}
		finally {
			ids.readUnlock();
		}
	}

	protected void doUpdateGame(YahooConnectionId id) {
		synchronized (game) {
			updateGame(id, game);
			game.refresh();
		}
	}

	/**
	 * @param id
	 * @param sitIndex
	 * @param flag
	 */
	public void focus(YahooConnectionId id, int sitIndex, boolean flag) {
		synchronized (id) {
			writeHeader(id);
			id.write('2');
			id.write(sitIndex);
			id.writeBoolean(flag);
			id.flush();
		}
	}

	public abstract int getGameId();

	/**
	 * @return the ids
	 */
	public SynchronizedVector<YahooConnectionId> getIds() {
		return ids;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the properties
	 */
	public Hashtable<String, String> getProperties() {
		return properties;
	}

	public abstract int getSitCount();

	/**
	 * @return the sits
	 */
	public YahooConnectionId[] getSits() {
		return sits;
	}

	/**
	 * @param data
	 * @return
	 */
	protected int[] getWonTurn(YData data) {
		if (data == null)
			return null;
		ByteArrayData buf = (ByteArrayData) data;
		int[] result = new int[getSitCount()];
		for (int i = 0; i < result.length; i++)
			result[i] = buf.byteAt(i);
		return result;
	}

	public void handleStart() {
		String[] players = new String[startedPlayers.length];
		for (int i = 0; i < startedPlayers.length; i++)
			if (startedPlayers[i] != null)
				players[i] = startedPlayers[i].getName();
			else
				players[i] = null;
		currGameLogEntry = new GameHistory(System.currentTimeMillis(), players,
				1 << getGameId());
	}

	public void handleStartTick(int time) {

	}

	public void handleStop(YData data) {
		finishedGameCount++;

		int[] wonTurn = getWonTurn(data);
		currGameLogEntry.setResult(wonTurn);

		if (!Game.isRated(properties))
			return;
		if (wonTurn == null)
			return;
		if (System.currentTimeMillis() - currGameLogEntry.getTime() < 30000) {
			doTableLog("O tempo deste jogo foi muito curto para a pontuação");
			return;
		}

		int[] oldRating = new int[startedPlayers.length];
		for (int i = 0; i < startedPlayers.length; i++)
			oldRating[i] = startedPlayers[i].getRealRating();
		int[] newRating = computeRating(oldRating, wonTurn);

		for (int i = 0; i < startedPlayers.length; i++) {
			if (wonTurn[i] == 1)
				startedPlayers[i].getProfileId().incrementWins(newRating[i]);
			else if (wonTurn[i] == 2)
				startedPlayers[i].getProfileId().incrementLosses(newRating[i]);
			else
				startedPlayers[i].getProfileId().incrementDraws(newRating[i]);
			int delta = newRating[i] - oldRating[i];
			currGameLogEntry.setOldRating(i, newRating[i]);
			currGameLogEntry.setNewRating(i, newRating[i]);
			doTableLog(startedPlayers[i].getName()
					+ " has old rating "
					+ oldRating[i]
					            + " and now have the new rating "
					            + newRating[i]
					                        + (delta != 0 ? " (" + (delta > 0 ? "+" + delta : delta)
					                        		+ ")" : ""));
		}

		MySQLTable table = room.getGameLogTable();
		PreparedStatement ps = null;
		try {
			ps = table.prepareStatement("INSERT INTO "
					+ table.name
					+ " VALUES (null, "
					+ MySQLTable.formatValue(new Timestamp(currGameLogEntry
							.getTime())) + ", ?, ?, ?, ?, "
							+ currGameLogEntry.getFlags() + ", ?)");

			IOBuffer buf = new IOBuffer();
			String[] players = currGameLogEntry.getPlayers();
			buf.writeShort(players.length);
			for (String player : players)
				buf.writeUTF(player);
			ps.setBlob(1, new SerialBlob(buf.getBytes()));

			buf = new IOBuffer();
			int[] oldRatings = currGameLogEntry.getOldRatings();
			buf.writeShort(oldRatings.length);
			for (int oldRating1 : oldRatings)
				buf.writeInt(oldRating1);
			ps.setBlob(2, new SerialBlob(buf.getBytes()));

			buf = new IOBuffer();
			int[] newRatings = currGameLogEntry.getNewRatings();
			buf.writeShort(newRatings.length);
			for (int newRating1 : newRatings)
				buf.writeInt(newRating1);
			ps.setBlob(3, new SerialBlob(buf.getBytes()));
			byte[] bytes = currGameLogEntry.getBytes();
			ps.setBlob(4, bytes != null && bytes.length > 0 ? new SerialBlob(
					bytes) : null);

			int[] result = currGameLogEntry.getResult();
			bytes = int2bytes(result);
			ps.setBlob(5, bytes != null && bytes.length > 0 ? new SerialBlob(
					bytes) : null);
			ps.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (ps != null) {
				table.closePreparedStatement(ps);
				ps = null;
			}
		}
		gameLog.add(currGameLogEntry);
	}

	public void handleStopTick() {
	}

	public void handleUpdateStatus(boolean flag) {

	}

	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}

	public boolean isHost(YahooConnectionId id) {
		return id.getName().equals(host);
	}

	public boolean joinId(YahooConnectionId id) {
		if (free)
			return false;
		String name = id.getName();
		if (privacy == 2 && !name.equals(host) && !inviteds.containsKey(name)) {
			room
			.alert(id,
			"Você não pode entrar em uma mesa no qual não foi convidado.");
			return false;
		}
		if (booteds.containsKey(id.getName())) {
			room.alert(id,
			"Você não pode entrar em uma mesa na qual foi expulso!");
			return false;
		}

		ids.writeLock();
		try {
			if (ids.contains(id))
				return false;

			FloodRecord joinTableFloodRecord = id.getJoinTableFloodRecord();
			long time = joinTableFloodRecord.log();
			if (joinTableFloodRecord.isFlood()) {
				id.notifyBan(new Timestamp(time), 1 * 24 * 60,
						"join table flood", "auto");
				return false;
			}			

			ids.add(id);
		}
		finally {
			ids.writeUnlock();
		}

		id.getTables().add(this);
		SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
		roomIds.readLock();
		try {
			for (YahooConnectionId id1 : roomIds)
				room.joinTable(id1, name, number);
		}
		finally {
			roomIds.readUnlock();
		}
		inviteds.remove(name);
		if (host == null)
			host = name;
		synchronized (sits) {
			for (int i = 0; i < sits.length; i++) {
				if (sits[i] == null)
					continue;
				sit(id, i, sits[i].getAvatar(), sits[i].getName());
			}
		}
		changeHost(id, host);
		doUpdateGame(id);
		return true;
	}

	public boolean leaveId(YahooConnectionId id) {
		if (free)
			return false;

		ids.writeLock();
		try {
			if (!ids.contains(id))
				return false;

			doStand(id);

			ids.remove(id);
			id.getTables().remove(this);
		}
		finally {
			ids.writeUnlock();
		}

		SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
		roomIds.readLock();
		try {
			for (YahooConnectionId id1 : roomIds)
				room.leaveTable(id1, id.getName(), number);
		}
		finally {
			roomIds.readUnlock();
		}

		if (ids.isEmpty())
			release();
		else if (isHost(id)) {
			host = ids.firstElement().getName();
			for (int i = 0; i < ids.size(); i++)
				changeHost(ids.elementAt(i), host);
		}
		return true;
	}

	public boolean open(YahooConnectionId host,
			Hashtable<String, String> hashtable) {
		if (!free)
			return false;
		free = false;
		properties = hashtable;
		game = createGame();
		game.initialize(hashtable, this, hashtable.containsKey("training")
				|| hashtable.containsKey("automat") ? 1 : 2);
		game.setup();
		int count = getSitCount();
		sits = new YahooConnectionId[count];
		sitTime = new long[count];
		sitState = new boolean[count];
		for (int i = 0; i < count; i++) {
			sits[i] = null;
			sitTime[i] = 0;
			sitState[i] = false;
		}
		if (host == null)
			return true;

		SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
		roomIds.readLock();
		try {
			for (YahooConnectionId id1 : roomIds)
				room.makeTable(id1, number, hashtable, false);
		}
		finally {
			roomIds.readUnlock();
		}

		joinId(host);
		return true;
	}

	public void parseData(YahooConnectionId id, int byte0, DataInputStream input)
	throws IOException {
		String name;
		String message;
		byte sitIndex;
		boolean flag;
		boolean flag1;
		switch (byte0) {
		case '0': // lost focus
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doFocus(id, i, false);
					break;
				}
			break;
		case '1': // got focus
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doFocus(id, i, true);
					break;
				}
			break;
		case 'A': // auto
			doAuto(id);
			break;
		case 'B': // start game
			doStartGame(id);
			break;
		case 'C': // chat
			message = input.readUTF();
			doChat(id, message);
			break;
		case 'D': // stand
			doStand(id);
			break;
		case 'Q': // boot
			name = input.readUTF();
			doBoot(id, name);
			break;
		case 'T': // sit
			sitIndex = input.readByte();
			doSit(id, sitIndex);
			break;
		case 'X': // cancel response
			flag = input.readBoolean();
			flag1 = input.readBoolean();
			for (YahooConnectionId sit : sits)
				if (sit != null && sit.equals(id)) {
					doCancelRespose(id, flag, flag1, id.getName());
					break;
				}
			break;
		case '^': // invite
			name = input.readUTF();
			doInvite(id, name);
			break;
		case '{': // resign
			sitIndex = input.readByte();
			doResign(id, sitIndex);
			break;
		case '%': // change privacy
			int privacy = input.readByte();
			doChangeTablePrivacy(id, privacy);
			break;
		case '(': // notify empty time per move
			break;
		case '!': // cancel request
			flag = input.readBoolean();
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doCancelRequest(id, i, flag);
					break;
				}
			break;
		default:
			throw new IllegalArgumentException("Illegal command: " + byte0
					+ "(" + (char) byte0 + ")");
		}
	}

	public void release() {
		if (!free) {
			free = true;

			SynchronizedVector<YahooConnectionId> roomIds = room.getIds();
			roomIds.readLock();
			try {
				for (YahooConnectionId id : roomIds)
					room.doneTable(id, number);
			}
			finally {
				roomIds.readUnlock();
			}

			host = null;
			booteds.clear();
			inviteds.clear();

			if (currGameLogEntry != null) {
				currGameLogEntry.close();
				currGameLogEntry = null;
			}

			properties.clear();
			booteds.clear();
			inviteds.clear();
			gameLog.clear();
		}
	}

	public void resign(YahooConnectionId id, byte sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('[');
			id.write(sitIndex);
			id.flush();
		}
	}

	public void sit(YahooConnectionId id, int sitIndex, byte avatar, String name) {
		synchronized (id) {
			writeHeader(id);
			id.write('t');
			id.write(sitIndex);
			id.write(avatar);
			id.writeUTF(name);
			id.flush();
		}
	}

	public void sitBegin(YahooConnectionId id, int sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('9');
			id.write(sitIndex);
			id.flush();
		}
	}

	public void stand(YahooConnectionId id, int sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('d');
			id.write(sitIndex);
			id.flush();
		}
	}

	public void tableLog(YahooConnectionId id, String message) {
		synchronized (id) {
			writeHeader(id);
			id.write('1');
			id.writeUTF(message);
			id.flush();
		}
	}

	public void updateGame(YahooConnectionId id, Game game) {
		try {
			synchronized (id) {
				writeHeader(id);
				id.write('a');
				game.write(id);
				id.writeInt(finishedGameCount);
				if (currGameLogEntry != null) {
					id.writeBoolean(true);
					currGameLogEntry.write(id);
				}
				else
					id.writeBoolean(false);
				id.flush();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			id.close();
		}
	}

	protected void writeHeader(YahooConnectionId id) {
		id.write('=');
		id.write(number);
	}

}
