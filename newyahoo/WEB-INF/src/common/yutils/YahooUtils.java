// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package y.po:
// _cls26, _cls37

public final class YahooUtils {

	public static int			a			= 0x10000;
	public static short			provisional	= -32768;
	public static String		c			= "      ";
	static byte					d[]			= new byte[Long.toString(
													0x8000000000000000L)
													.length()];
	static int					e			= 95;

	public static final char[]	hexdigits	= { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static double random() {
		double result = Math.random();
		while (result == 1)
			result = Math.random();
		return result;
	}

	public static boolean randomArray(boolean[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static boolean randomArray(boolean[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static byte randomArray(byte[] b) {
		return randomArray(b, 0, b.length);
	}

	public static byte randomArray(byte[] b, int off, int len) {
		return b[randomRange(off, off + len - 1)];
	}

	public static char randomArray(char[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static char randomArray(char[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static double randomArray(double[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static double randomArray(double[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static float randomArray(float[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static float randomArray(float[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static int randomArray(int[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static int randomArray(int[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static long randomArray(long[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static long randomArray(long[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static Object randomArray(Object[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static Object randomArray(Object[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static short randomArray(short[] ai) {
		return randomArray(ai, 0, ai.length);
	}

	public static short randomArray(short[] ai, int off, int len) {
		return ai[randomRange(off, off + len - 1)];
	}

	public static char randomASCIIChar() {
		return (char) randomRange(0, 255);
	}

	public static char randomASCIIChar(char low, char high) {
		if (low > 255 || high > 255)
			throw new IndexOutOfBoundsException();
		return (char) randomRange(low, high);
	}

	public static boolean randomBoolean() {
		return randomArray(new boolean[] { false, true });
	}

	public static char randomChar() {
		return (char) randomRange(0, 65536);
	}

	public static char randomChar(char low, char high) {
		return (char) randomRange(low, high);
	}

	public static char randomDigit() {
		return randomArray(hexdigits, 0, 9);
	}

	public static char randomHexDigit() {
		return randomArray(hexdigits);
	}

	public static char randomHexDigit(int low, int high) {
		return randomArray(hexdigits, low, high);
	}

	public static String randomIP() {
		return randomDigit() + randomDigit() + randomDigit() + "."
				+ randomDigit() + randomDigit() + randomDigit() + "."
				+ randomDigit() + randomDigit() + randomDigit() + "."
				+ randomDigit() + randomDigit() + randomDigit();
	}

	public static String randomName(int minSize, int maxSize) {
		return randomString(minSize, maxSize, '0', 'z');
	}

	public static int randomRange(int lowRange, int highRange) {
		int delta = highRange - lowRange + 1;
		return (int) (random() * delta + lowRange);
	}

	public static String randomString(int minSize, int maxSize) {
		int size = randomRange(minSize, maxSize);
		String result = "";
		for (int i = 0; i < size; i++)
			result += randomASCIIChar();
		return result;
	}

	public static String randomString(int minSize, int maxSize, char minChar,
			char maxChar) {
		int size = randomRange(minSize, maxSize);
		String result = "";
		for (int i = 0; i < size; i++)
			result += randomASCIIChar(minChar, maxChar);
		return result;
	}

	public static Object randomVector(Vector<?> vector) {
		if (vector.size() == 0)
			return null;
		int index = randomRange(0, vector.size() - 1);
		return vector.elementAt(index);
	}

	public static Object randomVectorWithRemove(Vector<?> vector) {
		if (vector.size() == 0)
			return null;
		int index = randomRange(0, vector.size() - 1);
		Object obj = vector.elementAt(index);
		vector.remove(index);
		return obj;
	}

	public static Hashtable<String, String> readHashtable(
			DataInputStream datainputstream) throws IOException {
		short length = datainputstream.readShort();
		Hashtable<String, String> result = new Hashtable<String, String>(length);
		for (int i = 0; i < length; i++) {
			String key = datainputstream.readUTF();
			String value = datainputstream.readUTF();
			result.put(key, value);
		}
		return result;
	}

	public static int readInt(byte abyte0[], int i) {
		int j = 0;
		for (int k = 0; k < 4;) {
			j = (j << 8) + (abyte0[i] & 0xff);
			k++;
			i++;
		}

		return j;
	}

	public static int readShort(byte abyte0[], int i) {
		int j = 0;
		for (int k = 0; k < 2;) {
			j = (j << 8) + (abyte0[i] & 0xff);
			k++;
			i++;
		}

		return j;
	}

	public static int readUnsignedByte(DataInputStream datainputstream)
			throws IOException {
		int i = datainputstream.readByte();
		if (i < 0)
			i += 256;
		return i;
	}

	public static byte[] redimArray(byte[] b, int len) {
		if (b == null)
			throw new NullPointerException("redim array fail with b == null");
		if (len < 0)
			throw new ArrayIndexOutOfBoundsException(
					"redim array fail with len < 0 (len=" + len + ")");
		byte[] b1 = new byte[len];
		System.arraycopy(b, 0, b1, 0, Math.min(len, b.length));
		return b1;
	}

	public static String translateMessage(String message) {
		String result = "";
		int p = 0;
		while (p < message.length()) {
			char c = message.charAt(p);
			if (c == '#') {
				p++;
				if (p >= message.length()) {
					result += '?';
					break;
				}
				c = message.charAt(p);
				if (c == '#')
					result += '#';
				else {
					result += '?';
					result += c;
				}
			}
			else
				result += c;
			p++;
		}
		return result;
	}

	public static void writeInt(int i, byte abyte0[], int j) {
		abyte0[j] = (byte) (i >> 24);
		abyte0[j + 1] = (byte) (i >> 16);
		abyte0[j + 2] = (byte) (i >> 8);
		abyte0[j + 3] = (byte) i;
	}

	public static void writeShort(int i, byte abyte0[], int j) {
		abyte0[j] = (byte) (i >> 8);
		abyte0[j + 1] = (byte) i;
	}

	public YahooUtils() {
	}

}
