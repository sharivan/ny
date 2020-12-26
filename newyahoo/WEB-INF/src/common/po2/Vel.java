// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

// Referenced classes of package y.po:
// _cls48, _cls16

public class Vel extends YVector {

	private float	vel;

	public Vel() {
		setVel(0);
	}

	public Vel(float i, float j, float k) {
		setCoords(i, j);
		setVel(k);
	}

	@Override
	public void add(float factor) {
		setVel(vel + factor);
	}

	@Override
	public YVector clone() {
		return new Vel(super.x, super.y, vel);
	}

	public float getAbsVel() {
		return Math.abs(vel);
	}

	public float getVel() {
		return vel;
	}

	public void ne(float friction) {
		byte signalvel = (byte) (vel < 0 ? -1 : 1);
		float delta = getAbsVel() - Math.abs(friction);
		delta = delta < 0 ? 0 : signalvel * delta;
		setVel(delta);
	}

	public Vel setVel(float i) {
		vel = i;
		return this;
	}

	@Override
	public String toString() {
		String s = "vel=(" + x + "," + y + ")";
		return s;
	}
}
