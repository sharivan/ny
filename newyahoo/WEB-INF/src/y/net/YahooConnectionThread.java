// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.net;

import java.io.DataInputStream;
import java.io.IOException;

import y.controls.ProcessHandler;
import y.io.YPort;
import y.io.YahooOutputStream;

// Referenced classes of package y.po:
// _cls65, _cls157, _cls162, _cls176,
// _cls56, _cls168, _cls173, _cls37

public class YahooConnectionThread extends Thread implements ProcessHandler,
		YahooSocketHandler {

	public long						lastRequestTime;
	public YahooSocket				yahooSocket;
	public YPort					yport;
	public YahooOutputStream		output;
	public String					host;
	public int						port;
	public String					yPort;
	public YahooConnectionHandler	handler;
	public boolean					processing;
	public IOException				exception;
	public int						state;
	public DataInputStream			input;
	public boolean					m;

	public YahooConnectionThread(String s, int i1, String s1,
			YahooConnectionHandler _pcls56) {
		super(YahooConnectionThread.class.getName());
		// System.out.println(MacAddress.macToString(MacAddress.getMacAddress()));
		lastRequestTime = System.currentTimeMillis();
		exception = null;
		state = 0;
		m = false;
		host = s;
		port = i1;
		yPort = s1;
		handler = _pcls56;
		start();
	}

	public void close() {
		yahooSocket.close();
	}

	public void exit() {
		try {
			yahooSocket.exit();
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			close();
		}
	}

	public void flush() {
		lastRequestTime = System.currentTimeMillis();
		try {
			yport.flush();
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			close();
		}
	}

	public YahooConnectionHandler getHandler() {
		return handler;
	}

	public void handleClose(YPort _pcls176) {
		handler.handleProcess(this, -1, null);
	}

	public void handleFail(String s) {
		handler.handleProcess(this, yport == null ? -2 : -1, null);
	}

	public void handleOpen(YPort _pcls176) {
		yport = _pcls176;
		output = _pcls176.d;
	}

	public void process(int code, Object obj) {
		if (code >= 0) {
			try {
				do {
					handler.processMessages(input, state);
					state++;
				}
				while (yahooSocket != null && yahooSocket.isValidCommand(yport));
			}
			catch (IOException ioexception) {
				exception = ioexception;
			}
			synchronized (this) {
				processing = false;
				notify();
			}
		}
		else if (!m) {
			if (yahooSocket != null)
				close();
			handler.handleDisconnect(this, code == -1);
			m = true;
		}
	}

	public synchronized void processMessage(YPort _pcls176,
			DataInputStream datainputstream, int i1) {
		processing = true;
		input = datainputstream;
		handler.handleProcess(this, 0, null);
		while (processing)
			try {
				wait();
			}
			catch (InterruptedException _ex) {
			}
	}

	@Override
	public void run() {
		try {
			yahooSocket = new YahooSocket(this, host, port, 65536, true/*
																		 * handler.
																		 * getParameter
																		 * (
																		 * "milestones"
																		 * ) !=
																		 * null
																		 */);
			yahooSocket.openYPort(yPort);
			do
				yahooSocket.processMessages();
			while (!yahooSocket.isClosed() || exception == null);
			if (exception != null)
				throw exception;
		}
		catch (IOException ioexception) {
			// ioexception.printStackTrace();
			handler.handleError(ioexception);
		}
		handler.handleProcess(this, yport == null ? -2 : -1, null);
		if (yahooSocket != null)
			yahooSocket.close();
	}
}
