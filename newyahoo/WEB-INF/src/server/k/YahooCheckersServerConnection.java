package server.k;

import java.net.Socket;

import server.net.YahooServer;
import server.net.YahooServerConnection;
import data.MySQLTable;

public class YahooCheckersServerConnection extends YahooServerConnection {

	public YahooCheckersServerConnection(YahooServer handler, Socket socket) {
		super(handler, socket, YahooCheckersServerConnection.class.getName());
		roomVersion = "t";
		tableVersion = "4";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.net.YahooServerConnection#getIgnoredsTable()
	 */
	@Override
	protected MySQLTable getIgnoredsTable() {
		return server.getTable("checkers_ignoreds");
	}

}
