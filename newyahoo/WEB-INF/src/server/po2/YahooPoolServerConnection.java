package server.po2;

import java.net.Socket;

import server.net.YahooServer;
import server.net.YahooServerConnection;
import data.MySQLTable;

public class YahooPoolServerConnection extends YahooServerConnection {

	public YahooPoolServerConnection(YahooServer handler, Socket socket) {
		super(handler, socket, YahooPoolServerConnection.class.getName());
		roomVersion = "t";
		tableVersion = "i";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.net.YahooServerConnection#getIgnoredsTable()
	 */
	@Override
	protected MySQLTable getIgnoredsTable() {
		return server.getTable("pool_ignoreds");
	}

}
