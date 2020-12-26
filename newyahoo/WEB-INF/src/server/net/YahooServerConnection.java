// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.net;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import login.Login;
import server.io.YahooConnectionId;
import server.yutils.IgnoredEntry;
import server.yutils.YahooRoom;
import data.MySQLTable;

// Referenced classes of package y.po:
// _cls65, _cls157, _cls162, _cls176,
// _cls56, _cls168, _cls173, _cls37

public abstract class YahooServerConnection extends Thread implements
		YahooSocketHandler {

	public long				a;
	public YahooSocket		yahooSocket;
	public boolean			m;
	Socket					socket;
	protected String		roomVersion;
	protected String		tableVersion;
	protected YahooServer	server;
	boolean					closing;

	public YahooServerConnection(YahooServer server, Socket socket) {
		this(server, socket, YahooServerConnection.class.getName());
	}

	public YahooServerConnection(YahooServer server, Socket socket, String name) {
		super(name);
		a = System.currentTimeMillis();
		m = false;
		this.server = server;
		this.socket = socket;
		closing = false;
		start();
	}

	/**
	 * 
	 */
	public void close() {
		if (closing)
			return;

		closing = true;
		try {
			interrupt();
			if (socket != null) {
				try {
					socket.close();
				}
				catch (IOException e) {
				}
				socket = null;
			}
		}
		finally {
			closing = false;
		}
	}

	public void close(YahooSocket sender) {
		if (yahooSocket != null) {
			yahooSocket.close();
			yahooSocket = null;
		}
		server.getConnections().remove(this);
	}

	private boolean doProcess(YahooConnectionId id,
			DataInputStream datainputstream) {
		try {
			YahooRoom room = id.getRoom();
			if (id.getState() != 0)
				room.parseData(id, datainputstream.readByte(), datainputstream);
			else {
				datainputstream.read();
				id.setCookie(datainputstream.readUTF());
				String ycookie = datainputstream.readUTF();
				id.setYcookie(ycookie);
				id.setAgent(datainputstream.readUTF());
				id.setIntl_code(datainputstream.readUTF());
				if (!id.getCookie().startsWith("id=")) {
					room.alert(id, "Invalid cookie");
					id.close();
					return false;
				}

				MySQLTable ids = server.getTable("ids");

				String idname = id.getCookie().substring(3);
				if (!(Login.isValidName(idname) && Login
						.loginExist(ids, idname))) {
					room.alert(id, "Login invalid or not exist");
					id.close();
					return false;
				}
				if (!Login.isValidCookie(ids, idname, ycookie)) {
					room.alert(id, "Invalid login");
					id.close();
					return false;
				}

				server.aquireProfileId(id, idname);

				IgnoredEntry entry = id.getIgnoredEntry();
				if (entry != null && entry.type == IgnoredEntry.BAN) {
					room.alert(id, "Your are banned from the game "
							+ (entry.time == -1 ? "forever" : "until "
									+ new Date(entry.date.getTime()
											+ entry.time * 60 * 1000))
							+ ".\r\nReason: " + entry.reason);
					id.close();
					return false;
				}

				synchronized (id) {
					id.write(0);
					id.writeUTF(idname);
					id.flush();
				}

				room.addId(id);
				id.setState(1);
			}
			return true;
		}
		catch (SocketException e) {
		}
		catch (EOFException e) {
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		if (id != null) {
			id.close();
			id = null;
		}
		return false;
	}

	protected abstract MySQLTable getIgnoredsTable();

	public String getRemoteHost(YahooSocket sender) {
		return socket.getInetAddress().getHostAddress();
	}

	public void handleClose(YahooSocket sender, YahooConnectionId id) {
		YahooRoom room = id.getRoom();
		if (room != null)
			room.removeId(id);
	}

	public void handleOpen(YahooSocket sender, String yport,
			YahooConnectionId id) throws IOException {

		YahooRoom room = server.getRoom(yport);
		if (room == null) {

			return;
		}

		id.setRoom(room);
		synchronized (id) {
			id.writeUTF("GAMES");
			id.writeUTF(roomVersion);
			id.writeUTF(tableVersion);
			id.flush();
		}
	}

	public void handleProcess(YahooSocket sender, YahooConnectionId id,
			DataInputStream datainputstream, boolean flag) throws IOException {
		boolean flag1;
		do {
			flag1 = doProcess(id, datainputstream);
		}
		while (flag1 && yahooSocket.isValidCommand(id, flag));
	}

	public boolean isClosed(YahooSocket sender) {
		return socket.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * server.net.YahooSocketHandler#releaseProfileId(server.io.YahooConnectionId
	 * , java.lang.String)
	 */
	@Override
	public void releaseProfileId(YahooConnectionId id, String name) {
		server.releaseProfileId(id, name);
	}

	@Override
	public void run() {
		try {
			yahooSocket = new YahooSocket(this, socket.getInputStream(), socket
					.getOutputStream(), 65536);
			do
				yahooSocket.processMessages();
			while (!closing && yahooSocket != null && !yahooSocket.isClosed());
		}
		catch (EOFException e) {
		}
		catch (SocketException e) {
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		finally {
			close();
		}
	}

}
