// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

public class Formater {

	public static String formatFloat(int value, int digits) {
		int k = (int) (value * 100L / digits % 100L);
		return value / digits + "." + (k >= 10 ? "" : "0") + k;
	}

	public static String formatInt(int value, int digits) {
		int k = 1;
		for (int l = 1; l < digits; l++)
			k *= 10;
		StringBuffer stringbuffer = new StringBuffer();
		for (; k > 0; k /= 10) {
			stringbuffer.append((char) (48 + value / k));
			value %= k;
		}
		return new String(stringbuffer);
	}

	public static String formatTimer(long l) {
		int i = (int) l / 1000;
		int j = i % 60;
		return i / 60 + ":" + formatInt(j, 2);
	}

	public Formater() {
	}
}
