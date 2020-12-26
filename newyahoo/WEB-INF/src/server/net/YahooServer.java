package server.net;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import server.io.YahooConnectionId;
import server.yutils.YahooProfileId;
import server.yutils.YahooRoom;
import server.yutils.YahooRoomHandler;
import data.MySQLTable;

public abstract class YahooServer extends Thread {

	private int									port;
	private Hashtable<String, YahooRoom>		rooms;
	private Vector<YahooServerConnection>		connections;
	private ServerSocket						server;
	protected YahooRoomHandler					handler;
	protected Hashtable<String, YahooProfileId>	profileIds;
	private boolean								closing;

	public YahooServer(YahooRoomHandler handler, int port, Vector<String> yports) {
		this(handler, port, yports, YahooServer.class.getName());
	}

	public YahooServer(YahooRoomHandler handler, int port,
			Vector<String> yports, String name) {
		super(name);
		this.handler = handler;
		this.port = port;
		connections = new Vector<YahooServerConnection>();
		profileIds = new Hashtable<String, YahooProfileId>();
		rooms = new Hashtable<String, YahooRoom>();
		int index = 0;
		for (String yport : yports)
			rooms.put(yport, createRoom(index++, yport));
		closing = false;
		start();
	}

	/**
	 * @param name
	 */
	public void aquireProfileId(YahooConnectionId id, String name) {
		YahooProfileId profileId;
		if (!profileIds.containsKey(name)) {
			profileId = createProfileId(name);
			profileId.setIp(id.getIp());
			profileIds.put(name, profileId);
		}
		else
			profileId = profileIds.get(name);
		id.setProfileId(profileId);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void close() {
		if (!closing && server != null && !server.isClosed()) {
			closing = true;
			try {
				interrupt();
				if (connections != null) {
					Vector<YahooServerConnection> clone = null;
					try {
						clone = (Vector<YahooServerConnection>) connections
								.clone();
						for (YahooServerConnection connection : clone) {
							try {
								connections.remove(connection);
								connection.close();
							}
							catch (Throwable e) {
								e.printStackTrace();
							}
						}
					}
					finally {
						if (clone != null) {
							clone.clear();
							clone = null;
						}
						if (connections != null) {
							connections.clear();
							connections = null;
						}
					}
				}
				if (rooms != null) {
					Collection<YahooRoom> values = rooms.values();
					for (YahooRoom room : values)
						room.close();
					rooms.clear();
					rooms = null;
				}
				handler = null;
				if (profileIds != null) {
					profileIds.clear();
					profileIds = null;
				}
				try {
					server.close();
				}
				catch (IOException e) {
				}
				server = null;
			}
			finally {
				closing = false;
			}
		}
	}

	public abstract YahooServerConnection createConnection(Socket socket);

	protected abstract YahooProfileId createProfileId(String name);

	public abstract YahooRoom createRoom(int index, String yport);

	/**
	 * @return the connections
	 */
	public Vector<YahooServerConnection> getConnections() {
		return connections;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the profileIds
	 */
	public Hashtable<String, YahooProfileId> getProfileIds() {
		return profileIds;
	}

	public YahooRoom getRoom(String yport) {
		return rooms.get(yport);
	}

	/**
	 * @return the rooms
	 */
	public Hashtable<String, YahooRoom> getRooms() {
		return rooms;
	}

	/**
	 * @param name
	 * @return tabela correspondente
	 */
	public MySQLTable getTable(String name) {
		return handler.getTable(name);
	}

	/**
	 * @return the closing
	 */
	public boolean isClosing() {
		return closing;
	}

	public void releaseProfileId(YahooConnectionId id, String name) {
		YahooProfileId profileId = profileIds.get(name);
		id.setProfileId(null);
		if (profileId.getListeners().size() == 0) {
			profileIds.remove(profileId);
			profileId.clearListeners();
			profileId = null;
		}
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			do {
				Socket socket = server.accept();
				if (closing) {
					socket.close();
					socket = null;
					return;
				}
				YahooServerConnection connection = createConnection(socket);
				connections.add(connection);
			}
			while (true);
		}
		catch (SocketException e) {
		}
		catch (EOFException e) {
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}

}
