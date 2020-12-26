package server.net;

import java.io.DataInputStream;
import java.io.IOException;

import server.io.YahooConnectionId;

public interface YahooSocketHandler {

	void close(YahooSocket sender);

	String getRemoteHost(YahooSocket sender);

	void handleClose(YahooSocket sender, YahooConnectionId id);

	void handleOpen(YahooSocket sender, String yport, YahooConnectionId id)
			throws IOException;

	void handleProcess(YahooSocket sender, YahooConnectionId yport,
			DataInputStream datainputstream, boolean flag) throws IOException;

	boolean isClosed(YahooSocket sender);

	/**
	 * @param id
	 * @param name
	 */
	void releaseProfileId(YahooConnectionId id, String name);

}
