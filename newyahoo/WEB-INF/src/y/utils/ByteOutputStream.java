// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.io.OutputStream;

public class ByteOutputStream extends OutputStream {

	byte	a[];
	int		b;

	@Override
	public void write(byte abyte0[], int i, int j) {
		System.arraycopy(abyte0, i, a, b, j);
		b += j;
	}

	@Override
	public void write(int i) {
		a[b] = (byte) i;
		b++;
	}
}
