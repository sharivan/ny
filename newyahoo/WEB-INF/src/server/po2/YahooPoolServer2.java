package server.po2;

import java.net.Socket;
import java.util.Vector;

import server.net.YahooServer;
import server.net.YahooServerConnection;
import server.yutils.YahooProfileId;
import server.yutils.YahooRoom;
import server.yutils.YahooRoomHandler;

public class YahooPoolServer2 extends YahooServer {

	public YahooPoolServer2(YahooRoomHandler handler, int port,
			Vector<String> yports) {
		super(handler, port, yports, YahooPoolServer2.class.getName());
	}

	@Override
	public YahooServerConnection createConnection(Socket socket) {
		return new YahooPoolServerConnection(this, socket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.net.YahooServer#createProfileId(java.lang.String)
	 */
	@Override
	protected YahooProfileId createProfileId(String name) {
		return new YahooPoolProfileId(getTable("ids"),
				getTable("pool_profiles"), getTable("pool_ignoreds"), name);
	}

	@Override
	public YahooRoom createRoom(int index, String yport) {
		return new YahooPoolRoom(handler, index, yport);
	}

}
