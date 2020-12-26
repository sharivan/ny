// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.net;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import y.io.YPort;
import y.io.YPortHandler;
import y.io.YahooBufferedOutputStream;
import y.io.YahooInputStream;
import y.io.YahooOutputStream;

// Referenced classes of package y.po:
// _cls37, _cls104, _cls34, _cls157,
// _cls125, _cls176

public class YahooSocket implements YPortHandler {
	Vector<YPort>				yportList;
	public Socket				socket;
	YahooInputStream			input;
	DataInputStream				dataInput;
	YahooBufferedOutputStream	output;
	YahooSocketHandler			handler;
	YahooOutputStream			dataOutput;
	int							j;
	private boolean				closed;

	public YahooSocket(YahooSocketHandler _pcls157, String host, int port,
			int l, boolean flag) throws IOException {
		closed = false;
		yportList = new Vector<YPort>();
		handler = _pcls157;
		j = l;
		// System.out.println("Conectando-se a " + host);
		socket = new Socket(host, port);
		try {
			input = new YahooInputStream(_pcls157, socket.getInputStream(), l);
			// input.useHttp = useHttp;
			dataInput = new DataInputStream(input);
			BufferedOutputStream bufOut = new BufferedOutputStream(socket
					.getOutputStream());
			output = new YahooBufferedOutputStream(bufOut, l);
			output.host = host;
			dataOutput = new YahooOutputStream(output);
			input.checkProxyHeader();
			if (flag) {
				synchronized (this) {
					output.responseProxyHeader();
				}
				output.setEncoder(input.getEncoder());
			}
		}
		catch (IOException ioexception) {
			socket.close();
			throw ioexception;
		}
	}

	public void close() {
		if (closed)
			return;
		try {
			if (output != null) {
				output.close();
				output = null;
			}
			if (input != null) {
				input.close();
				input = null;
			}
			if (socket != null)
				try {
					socket.close();
				}
				catch (IOException _ex) {
				}
				finally {
					socket = null;
				}
		}
		finally {
			closed = true;
		}
	}

	public void exit() throws IOException {
		output.exit();
	}

	YPort getYPort(int k) {
		for (int l = 0; l < yportList.size(); l++) {
			YPort _lcls176 = yportList.elementAt(l);
			if (_lcls176.index == k)
				return _lcls176;
		}

		return null;
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isValidCommand(YPort _pcls176) throws IOException {
		return input != null && input.isValidCommand(_pcls176.index);
	}

	public void openYPort(String s) throws IOException {
		output.open(s);
	}

	public void processMessages() throws IOException {
		int k = input.readByte();
		if (k == 111) // 'o'
		{
			String s = input.readUTF();
			int k1 = input.readInt();
			YPort _lcls176_3 = new YPort(k1, s, dataOutput, output, this);
			yportList.add(_lcls176_3);
			handler.handleOpen(_lcls176_3);
		}
		else if (k == 102) // 'f'
		{
			String s1 = input.readUTF();
			handler.handleFail(s1);
		}
		else if (k == 99) // 'c'
		{
			int l = input.readInt();
			YPort _lcls176 = getYPort(l);
			handler.handleClose(_lcls176);
			yportList.removeElement(_lcls176);
		}
		else if (k == 100) // 'd'
		{
			int i1 = input.readInt();
			YPort _lcls176_1 = getYPort(i1);
			int len = input.readShortLength();
			int p = input.p1;
			handler.processMessage(_lcls176_1, dataInput, len);
			int delta = p + len - input.p1;
			if (delta > 0) {
				input.Wr(delta);
				input.p1 += delta;
			}
			if (!input.isExpectedPacketLength())
				throw new IOException("Unexpected packet length");
		}
		else if (k == 101) // 'e'
		{
			int j1 = input.readInt();
			YPort yport = getYPort(j1);
			int len = input.readIntLength();
			int p = input.p1;
			handler.processMessage(yport, dataInput, len);
			int delta = p + len - input.p1;
			if (delta > 0) {
				input.Wr(delta);
				input.p1 += delta;
			}
			if (!input.isExpectedPacketLength())
				throw new IOException("Unexpected packet length");
		}
		else {
			throw new IOException("Illegal connection proxy command: " + k);
		}
		// if(useHttp)
		// dataInput.skip(2);
	}
}
