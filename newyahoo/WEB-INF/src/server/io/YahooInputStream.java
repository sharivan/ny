// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import common.yutils.Translater;
import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls34, _cls117, _cls41, _cls102

public class YahooInputStream extends InputStream {

	byte					a[];
	public int				p1;
	int						c;
	int						pend;
	public InputStream		input;
	public Translater		decoder;
	YahooInputStreamHandler	handler;

	public YahooInputStream(YahooInputStreamHandler handler,
			InputStream inputstream, int i) {
		// System.out.println("YahooInputStream.process(" + handler + ", "
		// + inputstream + ", " + i + ")");
		decoder = null;
		input = inputstream;
		a = new byte[i];
		this.handler = handler;
	}

	public void checkProxyHeader() throws IOException {
		// System.out.println("YahooInputStream.checkProxyHeader()");
		copyPacketBytes("Y".length());
		for (int i = 0; i < "Y".length(); i++)
			if (a[p1 + i] != "Y".charAt(i))
				throw new IOException("Illegal connection proxy header");

		p1 += "Y".length();
		pend = p1;
	}

	@Override
	public void close() {
		a = null;
		if (input != null) {
			try {
				input.close();
			}
			catch (IOException e) {
			}
			input = null;
		}
		decoder = null;
		handler = null;
	}

	public void copyPacketBytes(int size) throws IOException {
		if (c == p1) {
			pend -= p1;
			c = p1 = 0;
		}
		if (p1 + size > a.length) {
			System.arraycopy(a, p1, a, 0, c - p1);
			pend -= p1;
			c -= p1;
			p1 = 0;
		}
		int j = p1 + size;
		if (j > a.length) {
			int newLen = a.length == 0 ? 1 : 2 * a.length;
			while (j > newLen)
				newLen *= 2;
			expandBuffer(newLen);
		}
		// if (j > a.length)
		// throw new IOException("CPInputStream Buffer full size=" + size
		// + " p1=" + p1 + " pend=" + pend);
		int k;
		for (; c < j; c += k) {
			k = input.read(a, c, a.length - c);
			if (k == -1)
				throw new EOFException("Illegal cp protocol");
			if (decoder != null)
				decoder.translate(a, c, c + k);
		}

	}

	/**
	 * @param newLen
	 */
	private void expandBuffer(int len) {
		a = YahooUtils.redimArray(a, len);
		System.gc();
	}

	public boolean isExpectedPacketLength() {
		if (p1 == pend) {
			return true;
		}
		p1 = pend;
		return false;
	}

	public boolean isValidIntCommand(int index) throws IOException {
		if (!isExpectedPacketLength())
			throw new IOException("Unexepected packet length");
		if (c - p1 < 9)
			return false;
		if (a[p1] != 101)
			return false;
		int j = YahooUtils.readInt(a, p1 + 1);
		if (j != index)
			return false;
		int k = YahooUtils.readInt(a, p1 + 5);
		if (c - p1 >= k + 9) {
			p1 += 9;
			pend = p1 + k;
			return true;
		}
		return false;
	}

	public boolean isValidShortCommand(int index) throws IOException {
		if (!isExpectedPacketLength())
			throw new IOException("Unexepected packet length");
		if (c - p1 < 7)
			return false;
		if (a[p1] != 100)
			return false;
		int j = YahooUtils.readInt(a, p1 + 1);
		if (j != index)
			return false;
		int k = YahooUtils.readShort(a, p1 + 5);
		if (c - p1 >= k + 7) {
			p1 += 7;
			pend = p1 + k;
			return true;
		}
		return false;
	}

	@Override
	public int read() throws IOException {
		if (pend == p1) {
			throw new IOException("Illegal CP Read pend=" + pend + " p1=" + p1);
		}
		int i = a[p1] & 0xff;
		p1++;
		return i;
	}

	@Override
	public int read(byte abyte0[], int i, int j) throws IOException {
		if (j > pend - p1) {
			throw new IOException("Illegal CP Read");
		}
		System.arraycopy(a, p1, abyte0, i, j);
		p1 += j;
		return j;
	}

	public int readByte() throws IOException {
		// System.out.println("YahooInputStream.readByte()");
		copyPacketBytes(1);
		byte byte0 = a[p1];
		p1++;
		pend = p1;
		return byte0;
	}

	public int readInt() throws IOException {
		copyPacketBytes(4);
		int i = YahooUtils.readInt(a, p1);
		p1 += 4;
		return i;
	}

	public int readIntLength() throws IOException {
		copyPacketBytes(4);
		int i = YahooUtils.readInt(a, p1);
		if (i < 0) {
			throw new IOException("illegal packet length");
		}
		p1 += 4;
		pend = p1 + i;
		copyPacketBytes(i);
		return i;
	}

	public int readShortLength() throws IOException {
		copyPacketBytes(2);
		int i = YahooUtils.readShort(a, p1);
		if (i < 0) {
			throw new IOException("illegal packet length");
		}
		p1 += 2;
		pend = p1 + i;
		copyPacketBytes(i);
		return i;
	}

	public String readUTF() throws IOException {
		int i = 0;
		copyPacketBytes(2);
		i = YahooUtils.readShort(a, p1);
		p1 += 2;
		copyPacketBytes(i);
		StringBuffer stringbuffer = new StringBuffer();
		for (int j = 0; j < i; j++) {
			stringbuffer.append((char) a[p1]);
			p1++;
		}

		return new String(stringbuffer);
	}

	/*
	 * public int skipBytes(int arg0) { return 0; }
	 */

	public void setDecoder(Translater decoder) {
		// System.out.println("YahooInputStream.setDecoder(" + decoder + ")");
		this.decoder = decoder;
	}

}
