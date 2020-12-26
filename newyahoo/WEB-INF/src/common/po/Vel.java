// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls48, _cls16

public class Vel extends YIVector {

	private int	vel;

	public Vel() {
		setVel(0);
	}

	public Vel(int i, int j, int k) {
		set(i, j);
		setVel(k);
	}

	@Override
	public void add(int i) {
		setVel(vel + i);
	}

	public int he() {
		return Math.abs(vel);
	}

	@Override
	public YIVector je() {
		return new Vel(super.a, super.b, vel);
	}

	public int ke() {
		return vel;
	}

	public void ne(int i) {
		byte byte0 = (byte) (vel < 0 ? -1 : 1);
		int j = he() - Math.abs(i);
		j = j < 0 ? 0 : byte0 * j;
		setVel(j);
	}

	public Vel setVel(int i) {
		vel = i;
		return this;
	}

	@Override
	public String toString() {
		String s = "vel=(" + PoolMath.yintToFloat(super.a) + ","
				+ PoolMath.yintToFloat(super.b) + ")";
		return s;
	}
}
