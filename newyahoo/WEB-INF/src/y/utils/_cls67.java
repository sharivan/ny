// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

public class _cls67 {

	int	a[];
	int	b;
	int	c;
	int	d;

	public _cls67(int i) {
		c = 0;
		d = 0;
		a = new int[i];
	}

	public void Nl(int i) {
		b -= a[c];
		a[c] = i;
		b += i;
		c = (c + 1) % a.length;
		d++;
	}

	public int Ol() {
		return b / Math.max(1, Math.min(d, a.length));
	}
}
