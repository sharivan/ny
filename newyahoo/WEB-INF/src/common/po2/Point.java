// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

// Referenced classes of package y.po:
// _cls108

public class Point {

	public int	a;
	public int	b;

	public Point() {
		Zx(0, 0);
	}

	public Point(int i, int j) {
		Zx(i, j);
	}

	@Override
	public String toString() {
		String s = "x,y=(";
		s = s + a + "," + b + ")";
		return s;
	}

	public void Zx(int i, int j) {
		a = i;
		b = j;
	}
}
