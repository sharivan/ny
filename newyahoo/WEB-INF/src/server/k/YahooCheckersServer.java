package server.k;

import java.net.Socket;
import java.util.Vector;

import server.net.YahooServer;
import server.net.YahooServerConnection;
import server.yutils.YahooProfileId;
import server.yutils.YahooRoom;
import server.yutils.YahooRoomHandler;

public class YahooCheckersServer extends YahooServer {

	public YahooCheckersServer(YahooRoomHandler handler, int port,
			Vector<String> yports) {
		super(handler, port, yports, YahooCheckersServer.class.getName());
	}

	@Override
	public YahooServerConnection createConnection(Socket socket) {
		return new YahooCheckersServerConnection(this, socket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.net.YahooServer#createProfileId(java.lang.String)
	 */
	@Override
	protected YahooProfileId createProfileId(String name) {
		return new YahooCheckersProfileId(getTable("ids"),
				getTable("checkers_profiles"), getTable("checkers_ignoreds"),
				name);
	}

	@Override
	public YahooRoom createRoom(int index, String yport) {
		return new YahooCheckersRoom(handler, index, yport);
	}

}
