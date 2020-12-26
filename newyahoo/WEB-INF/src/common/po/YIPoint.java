// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111, _cls169, _cls16, _cls20,
// _cls48

public class YIPoint implements YData {

	public int	a;
	public int	b;

	public YIPoint() {
		setCoords(0, 0);
	}

	public YIPoint(float f, float f1) {
		setCoords(PoolMath.floatToYInt(f), PoolMath.floatToYInt(f1));
	}

	public YIPoint(int i, int j) {
		setCoords(i, j);
	}

	public YIPoint(YIPoint point) {
		setCoords(point);
	}

	public int abs() {
		return distance(0, 0);
	}

	public void add(YIVector _pcls48) {
		setCoords(a + _pcls48.a, b + _pcls48.b);
	}

	public int distance(int i, int j) {
		long l = i - a;
		long l1 = j - b;
		long l2 = PoolMath2.sqrt(PoolMath2.mul(l, l) + PoolMath2.mul(l1, l1));
		return (int) l2;
	}

	public int distance(YIPoint _pcls46) {
		return distance(_pcls46.a, _pcls46.b);
	}

	public YIPoint newCopy() {
		return new YIPoint(a, b);
	}

	public void read(DataInput input) throws IOException {
		a = input.readInt();
		b = input.readInt();
	}

	public boolean same(int i, int j) {
		return i == a && j == b;
	}

	public boolean same(YIPoint _pcls46) {
		return _pcls46.a == a && _pcls46.b == b;
	}

	public void setCoords(int i, int j) {
		a = i;
		b = j;
	}

	public void setCoords(YIPoint point) {
		setCoords(point.a, point.b);
	}

	@Override
	public String toString() {
		String s = "x,y=(";
		s = s + PoolMath.yintToFloat(a) + "," + PoolMath.yintToFloat(b) + ")";
		return s;
	}

	public YVector toYPoint() {
		return new YVector(PoolMath.yintToFloat(a), PoolMath.yintToFloat(b));
	}

	public YPoint toYVector() {
		return new YPoint(PoolMath.yintToFloat(a), PoolMath.yintToFloat(b));
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(a);
		output.writeInt(b);
	}
}
