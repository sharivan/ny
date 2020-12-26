// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls108, _cls46

public class YPoint {

	public float	x;
	public float	y;

	public YPoint() {
		setCoords(0.0F, 0.0F);
	}

	public YPoint(float f, float f1) {
		setCoords(f, f1);
	}

	public YPoint(int i, int j) {
		setCoords(i, j);
	}

	public float abs() {
		return distance(0.0F, 0.0F);
	}

	public void add(YVector _pcls108) {
		setCoords(x + _pcls108.x, y + _pcls108.y);
	}

	public float angle(YVector _pcls108) {
		float f = 0.0F;
		if (_pcls108.x == 0.0F)
			f = x;
		else if (_pcls108.y == 0.0F) {
			f = y;
		}
		else {
			double d = _pcls108.y / _pcls108.x;
			double d1 = x * d;
			double d2 = Math.atan(d);
			f = (float) (d1 * Math.cos(d2));
		}
		return f;
	}

	public YIPoint copy() {
		return new YIPoint(x, y);
	}

	public float distance(float f, float f1) {
		float f2 = f - x;
		float f3 = f1 - y;
		return (float) Math.sqrt(f2 * f2 + f3 * f3);
	}

	public float distance(YPoint _pcls169) {
		return distance(_pcls169.x, _pcls169.y);
	}

	public void setCoords(float f, float f1) {
		x = f;
		y = f1;
	}

	public YPoint setCoordsTo(YPoint _pcls169) {
		_pcls169.x = x;
		_pcls169.y = y;
		return _pcls169;
	}

	@Override
	public String toString() {
		String s = "x,y=(";
		s = s + x + "," + y + ")";
		return s;
	}
}
