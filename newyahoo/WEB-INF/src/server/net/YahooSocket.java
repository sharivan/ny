// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.net;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import server.io.YahooBufferedOutputStream;
import server.io.YahooConnectionId;
import server.io.YahooInputStream;
import server.io.YahooInputStreamHandler;
import server.io.YahooOutputStream;
import server.yutils.YahooRoom;

import common.io._cls125;
import common.yutils.Translater;
import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls37, _cls104, _cls34, _cls157,
// _cls125, _cls176

public class YahooSocket implements _cls125, YahooInputStreamHandler {
	private static int					counter	= 1;
	public static HttpServletResponse	resp;
	public Vector<YahooConnectionId>	yportList;
	// public Socket socket;
	public YahooInputStream				input;
	public DataInputStream				dataInput;
	public YahooBufferedOutputStream	output;
	public YahooSocketHandler			handler;
	public YahooOutputStream			dataOutput;
	public boolean						closing;
	public boolean						closed;

	public YahooSocket(YahooSocketHandler handler, InputStream in,
			OutputStream out, int l) throws IOException {
		closed = false;
		// System.out.println("YahooSocket.<init>(" + handler + ", " + in + ", "
		// + out + ", " + l + ")");
		yportList = new Vector<YahooConnectionId>();
		this.handler = handler;
		// this.socket = socket;
		closing = false;
		try {
			input = new YahooInputStream(this, in, l);
			dataInput = new DataInputStream(input);
			output = new YahooBufferedOutputStream(
					new BufferedOutputStream(out), l);
			// output.address = handler.getRemoteHost(this) + "("
			// + counter + ")";
			counter++;
			dataOutput = new YahooOutputStream(output);
			output.yahoo();
			// resp.flushBuffer();
			input.checkProxyHeader();
			int encodeKey = YahooUtils.randomRange(0, 255);
			int decodeKey = YahooUtils.randomRange(0, 255);
			Translater encoder = new Translater(encodeKey);
			Translater decoder = new Translater(decodeKey);
			output.sendAlgorithm(decodeKey, encodeKey);
			input.setDecoder(decoder);
			output.setEncoder(encoder);
		}
		catch (IOException ioexception) {
			handler.close(this);
			throw ioexception;
		}
	}

	public void close() {
		if (closing || closed)
			return;
		closing = true;
		try {
			closeAllIds();
			if (output != null) {
				output.close();
				output = null;
			}
			if (input != null) {
				input.close();
				input = null;
			}
			if (handler != null) {
				handler.close(this);
				handler = null;
			}
		}
		finally {
			handler = null;
			closing = false;
			closed = true;
		}
	}

	public void close(YahooConnectionId id) {
		if (yportList.remove(id))
			handler.handleClose(this, id);
	}

	@SuppressWarnings("unchecked")
	public void closeAllIds() {
		Vector<YahooConnectionId> vector = (Vector<YahooConnectionId>) yportList
				.clone();
		for (int i = 0; i < vector.size(); i++)
			close(vector.elementAt(i));
	}

	YahooConnectionId getYPort(int index) {
		for (int l = 0; l < yportList.size(); l++) {
			YahooConnectionId id = yportList.elementAt(l);
			if (id.getRoom().getIndex() == index)
				return id;
		}

		return null;
	}

	public boolean isClosed() {
		return closed || handler != null && handler.isClosed(this);
	}

	public boolean isValidCommand(YahooConnectionId id, boolean flag)
			throws IOException {
		YahooRoom room = id.getRoom();
		if (room == null)
			return false;
		if (flag)
			return input.isValidIntCommand(room.getIndex());
		return input.isValidIntCommand(room.getIndex());
	}

	public synchronized void processMessages() throws IOException {
		// System.out.println("YahooSocket.processMessages()");
		int k = input.readByte();
		if (k == 88) // 'X'
			close();
		else if (k == 111) // 'o'
		{
			String yport = input.readUTF();
			YahooConnectionId id = new YahooConnectionId(this, dataOutput,
					output);
			id.setIp(handler.getRemoteHost(this));
			yportList.add(id);
			handler.handleOpen(this, yport, id);
		}
		else if (k == 100) // 'd'
		{
			int index = input.readInt();
			YahooConnectionId id = getYPort(index);
			input.readShortLength();
			handler.handleProcess(this, id, dataInput, false);
			if (!input.isExpectedPacketLength())
				throw new IOException("Unexpected packet length");
		}
		else if (k == 101) // 'e'
		{
			int index = input.readInt();
			YahooConnectionId id = getYPort(index);
			input.readIntLength();
			handler.handleProcess(this, id, dataInput, true);
			if (!input.isExpectedPacketLength())
				throw new IOException("Unexpected packet length");
		}
		else {
			throw new IOException("Illegal connection proxy command: " + k);
		}
	}

	/**
	 * @param id
	 * @param name
	 */
	public void releaseProfileId(YahooConnectionId id, String name) {
		handler.releaseProfileId(id, name);
	}
}
