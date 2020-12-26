// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.io.InputStream;

public class YahooStringBuffer extends InputStream {

	byte	a[];
	int		b;
	int		c;

	public YahooStringBuffer(byte abyte0[]) {
		a = abyte0;
		c = abyte0.length;
		b = 0;
	}

	@Override
	public int read() {
		if (b >= a.length || c >= 0 && b >= c) {
			return -1;
		}
		int i = a[b] & 0xff;
		b++;
		return i;
	}

	@Override
	public int read(byte abyte0[], int i, int j) {
		int k = c < 0 ? a.length : c;
		int l = j + b <= k ? j : k - b;
		if (l < 1) {
			return -1;
		}
		System.arraycopy(a, b, abyte0, i, l);
		b += l;
		return l;
	}
}
