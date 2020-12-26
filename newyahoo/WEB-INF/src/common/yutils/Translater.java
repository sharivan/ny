// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

public class Translater {

	int	key;

	public Translater(int i) {
		// System.out.println("Translater.<init>(" + i + ")");
		key = i;
	}

	public synchronized void translate(byte abyte0[], int i, int j) {
		for (int k = i; k < j; k++) {
			key *= 83;
			abyte0[k] ^= key;
		}
	}
}
