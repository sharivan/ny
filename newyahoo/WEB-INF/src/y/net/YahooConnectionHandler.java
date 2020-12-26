package y.net;

import java.io.DataInputStream;
import java.io.IOException;

import y.controls.ProcessHandler;

public interface YahooConnectionHandler {

	public String getParameter(String name);

	public void handleDisconnect(YahooConnectionThread connection,
			boolean success);

	public void handleError(Throwable throwable);

	public void handleProcess(ProcessHandler handler, int i, Object obj);

	public void processMessages(DataInputStream datainputstream, int state)
			throws IOException;

}
