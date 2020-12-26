// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.io;

import java.io.IOException;
import java.io.OutputStream;

import common.yutils.Translater;
import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls41, _cls102

public class YahooBufferedOutputStream extends OutputStream {

	public static char	hexa_digits[]	= { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static boolean charInRange(char c, char low, char high) {
		return (byte) low <= (byte) c && (byte) c <= (byte) high;
	}

	public static String intToHex(int i, int digits) {
		String result = "";
		for (int j = 0; j < digits; j++) {
			result = hexa_digits[i & 0x0f] + result;
			i >>= 4;
		}
		return result;
	}

	public static String retString(String s) {
		boolean B = false;
		boolean B1 = false;
		String Result = "";
		for (int i = 0; i < s.length(); i++)
			if (charInRange(s.charAt(i), '\u0020', '\u007E')) {
				if (B) {
					if (s.charAt(i) == '\'')
						Result = Result + "'" + s.charAt(i);
					else
						Result = Result + s.charAt(i);
				}
				else {
					if (s.charAt(i) == '\'')
						Result = Result + '\'' + '\'' + s.charAt(i) + '\'';
					else
						Result = Result + '\'' + s.charAt(i);
					B = true;
					B1 = false;
				}
			}
			else {
				if (B) {
					Result = Result + "\'#$" + intToHex((byte) s.charAt(i), 2);
					B = false;
					B1 = true;
				}
				else if (!B1) {
					Result = Result + "#$" + intToHex((byte) s.charAt(i), 2);
					B1 = true;
					B = false;
				}
				else
					Result = Result + "#$" + intToHex((byte) s.charAt(i), 2);
			}
		if (B)
			Result = Result + '\'';
		return Result;
	}

	byte				a[];
	public OutputStream	output;

	int					e;

	boolean				trouble;

	public Translater	encoder;

	public YahooBufferedOutputStream(OutputStream outputstream, int i) {
		e = 9;
		encoder = null;
		a = new byte[i];
		output = outputstream;
	}

	public int Be(String s, int index, int i) {
		synchronized (a) {
			int j = s.length();
			YahooUtils.writeShort(j, a, i);
			i += 2;
			for (int k = 0; k < j; k++)
				a[i++] = (byte) s.charAt(k);

			a[i++] = (byte) (index >> 24);
			a[i++] = (byte) (index >> 16);
			a[i++] = (byte) (index >> 8);
			a[i++] = (byte) index;

			return i;
		}
	}

	@Override
	public void close() {
		a = null;
		if (output != null) {
			try {
				output.close();
			}
			catch (IOException e) {
			}
			output = null;
		}
		encoder = null;
	}

	public void close(int index) throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					a[0] = 'c';
					int i = writeInt(index, 1);
					send(a, 0, i);
				}
				catch (IOException ioexception) {
					trouble = true;
					throw ioexception;
				}
		}
	}

	/**
	 * 
	 */
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

	public void fail(String message) throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					a[0] = 'f';
					int i = writeUTF(message, 1);
					send(a, 0, i);
				}
				catch (IOException ioexception) {
					trouble = true;
					throw ioexception;
				}
		}
	}

	@Override
	public void flush() throws IOException {
		send(a, 0, e);
	}

	public void open(String s, int index) throws IOException {
		if (!trouble)
			try {
				a[0] = 'o';
				int i = Be(s, index, 1);
				send(a, 0, i);
			}
			catch (IOException ioexception) {
				trouble = true;
				throw ioexception;
			}
	}

	public void send(byte abyte0[], int i, int j) throws IOException {
		String s = "";
		for (int i1 = i; i1 < i + j; i1++)
			s += (char) abyte0[i1];
		if (encoder != null)
			encoder.translate(abyte0, i, i + j);
		synchronized (output) {
			output.write(abyte0, i, j);
			output.flush();
		}
	}

	public void send(int i) throws IOException {
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

	public void sendAlgorithm(int encryptKey, int decryptKey)
			throws IOException {
		synchronized (a) {
			a[0] = (byte) (encryptKey >> 24);
			a[1] = (byte) (encryptKey >> 16);
			a[2] = (byte) (encryptKey >> 8);
			a[3] = (byte) encryptKey;
			a[4] = (byte) (decryptKey >> 24);
			a[5] = (byte) (decryptKey >> 16);
			a[6] = (byte) (decryptKey >> 8);
			a[7] = (byte) decryptKey;
			send(a, 0, 8);
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

	public int writeInt(int index, int i) {
		synchronized (a) {
			YahooUtils.writeShort(4, a, i);
			i += 2;

			a[i++] = (byte) (index >> 24);
			a[i++] = (byte) (index >> 16);
			a[i++] = (byte) (index >> 8);
			a[i++] = (byte) index;

			return i;
		}
	}

	public int writeUTF(String s, int i) {
		synchronized (a) {
			int len = s.length();

			YahooUtils.writeShort(len + 2, a, i);
			i += 2;

			a[i++] = (byte) (len >> 8);
			a[i++] = (byte) len;

			for (int j = 0; j < len; j++)
				a[i++] = (byte) s.charAt(j);

			return i;
		}
	}

	public void yahoo() throws IOException {
		synchronized (a) {
			if (!trouble)
				try {
					a[0] = 'Y';
					a[1] = 'A';
					a[2] = 'H';
					a[3] = 'O';
					a[4] = 'O';
					a[5] = '!';
					send(a, 0, 6);
				}
				catch (IOException ioexception) {
					trouble = true;
					throw ioexception;
				}
		}
	}

}
