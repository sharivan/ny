// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.utils;

public class Square {

	protected int	a;
	protected int	b;

	public Square() {
		this(0, 0);
	}

	public Square(int i, int j) {
		a = i;
		b = j;
	}

	@Override
	public Object clone() {
		return new Square(a, b);
	}

	public boolean equals(int x, int y) {
		return a == x && b == y;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Square
				&& equals(((Square) obj).a, ((Square) obj).b);
	}

	public int getX() {
		return a;
	}

	public int getY() {
		return b;
	}

	@Override
	public int hashCode() {
		return a * 0x10000 + b;
	}

	public void setCoords(int x, int y) {
		a = x;
		b = y;
	}

	@Override
	public String toString() {
		return "(" + a + "," + b + ")";
	}
}
