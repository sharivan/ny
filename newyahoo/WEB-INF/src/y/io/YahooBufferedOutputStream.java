// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.io;

import java.io.IOException;
import java.io.OutputStream;

import y.utils.Translater;

import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls41, _cls102

public class YahooBufferedOutputStream extends OutputStream {

	byte				a[];
	public OutputStream	output;
	int					e;
	boolean				trouble;
	Translater			encoder;

	public String		host;

	public YahooBufferedOutputStream(OutputStream outputstream, int i) {
		e = 9;
		encoder = null;
		a = new byte[i];
		output = outputstream;
	}

	public int Be(String s, int i) {
		synchronized (a) {
			int j = s.length();
			YahooUtils.writeShort(j, a, i);
			i += 2;
			for (int k = 0; k < j; k++) {
				a[i] = (byte) s.charAt(k);
				i++;
			}

			return i;
		}
	}

	@Override
	public void close() {
		if (output != null) {
			try {
				output.close();
			}
			catch (IOException e) {
			}
			output = null;
		}
	}

	public void exit() throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					a[0] = 88; // 'X'
					send(a, 0, 1);
				}
				catch (IOException ioexception) {
					trouble = true;
					throw ioexception;
				}
		}
	}

	private void expandBuffer() {
		expandBuffer(a.length == 0 ? 1 : 2 * a.length);
	}

	/**
	 * @param newLen
	 */
	private void expandBuffer(int len) {
		a = YahooUtils.redimArray(a, len);
		System.gc();
	}

	public void flush(byte[] buffer, int index, int count) throws IOException {
		send(buffer, index, count - index);
		e = 9;
	}

	public void flush(int i) throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					if (e - 9 > Short.MAX_VALUE) {
						a[0] = 'e';
						YahooUtils.writeInt(i, a, 1);
						YahooUtils.writeInt(e - 9, a, 5);
						send(a, 0, e);
					}
					else {
						a[2] = 'd';
						YahooUtils.writeInt(i, a, 3);
						YahooUtils.writeShort(e - 9, a, 7);
						send(a, 2, e - 2);
					}
				}
				catch (IOException ioexception) {
					trouble = true;
					e = 9;
					throw ioexception;
				}
			e = 9;
		}
	}

	public void open(String s) throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					a[0] = 111; // 'o'
					int i = Be(s, 1);
					send(a, 0, i);
				}
				catch (IOException ioexception) {
					trouble = true;
					throw ioexception;
				}
		}
	}

	public void responseProxyHeader() throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					a[0] = 89; // 'Y'
					send(a, 0, 1);
					// System.out.println(">>: Y");
				}
				catch (IOException ioexception) {
					trouble = true;
					throw ioexception;
				}
		}
	}

	public void send(byte abyte0[], int i, int j) throws IOException {
		if (encoder != null)
			encoder.translate(abyte0, i, i + j);
		synchronized (output) {
			output.write(abyte0, i, j);
			output.flush();
		}
	}

	public void setEncoder(Translater _pcls41) {
		encoder = _pcls41;
	}

	@Override
	public void write(byte abyte0[], int i, int j) {
		synchronized (a) {
			if (e + j > a.length) {
				int newLen = a.length == 0 ? 1 : 2 * a.length;
				while (e + j > newLen)
					newLen *= 2;
				expandBuffer(newLen);
			}
			System.arraycopy(abyte0, i, a, e, j);
			e += j;
		}
	}

	@Override
	public void write(int i) {
		synchronized (a) {
			if (e + 1 > a.length)
				expandBuffer();
			a[e] = (byte) i;
			e++;
		}
	}

}
