// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls169

public class YVector {

	public float	x;
	public float	y;
	protected float	c;
	protected float	d;
	protected float	e;
	private boolean	f;
	private boolean	g;

	public YVector() {
		this(0.0F, 0.0F);
	}

	public YVector(float _x, float _y) {
		f = true;
		g = true;
		x = _x;
		y = _y;
	}

	protected void _versor() {
		if (f) {
			float f1 = abs();
			if (f1 == 0.0F) {
				c = 0.0F;
				d = 0.0F;
			}
			else {
				c = x / f1;
				d = y / f1;
			}
			f = false;
		}
	}

	public float abs() {
		if (g) {
			e = It();
			g = false;
		}
		return e;
	}

	public void add(YVector point) {
		x += point.x;
		y += point.y;
		f = true;
	}

	public double angle(YVector _pcls108) {
		_versor();
		_pcls108._versor();
		float f1 = c * _pcls108.c + d * _pcls108.d;
		if (f1 > 1.0F)
			f1 = 1.0F;
		if (f1 < -1F)
			f1 = -1F;
		return Math.acos(f1);
	}

	public float Dt(YVector point) {
		point._versor();
		return x * point.c + y * point.d;
	}

	protected float It() {
		float f1 = (float) Math.sqrt(x * x + y * y);
		return f1;
	}

	public void mul(float f1) {
		x *= f1;
		y *= f1;
		f = true;
		g = true;
	}

	public void neg() {
		x = -x;
		y = -y;
		f = true;
	}

	public void rotate(double d1) {
		if (abs() == 0.0F) {
			return;
		}
		double d2 = Math.cos(d1);
		double d3 = Math.sin(d1);
		float f1 = (float) (x * d2 - y * d3);
		float f2 = (float) (x * d3 + y * d2);
		x = f1;
		y = f2;
		f = true;
		return;
	}

	public void setAbs(float f1) {
		float f2 = abs();
		if (f2 == 0.0F) {
			setCoords(0.0F, 0.0F);
			return;
		}
		float f3 = f2 - f1;
		if (f3 <= 0.0F) {
			setCoords(0.0F, 0.0F);
		}
		else {
			versor();
			mul(f3);
		}
		g = true;
	}

	public void setCoords(float f1, float f2) {
		x = f1;
		y = f2;
		f = true;
		g = true;
	}

	public void setCoords(YPoint _pcls169, YPoint _pcls169_1) {
		setCoords(_pcls169_1.x - _pcls169.x, _pcls169_1.y - _pcls169.y);
	}

	public YVector setCoordsTo(YVector _pcls108) {
		_pcls108.setCoords(x, y);
		return _pcls108;
	}

	public void sub(YVector point) {
		x -= point.x;
		y -= point.y;
		f = true;
	}

	@Override
	public String toString() {
		String s = "(";
		s = s + x + " " + y + ")";
		return s;
	}

	public YIPoint toYIPoint() {
		return new YIPoint(PoolMath.floatToYInt(x), PoolMath.floatToYInt(y));
	}

	public YIVector toYIVector() {
		return new YIVector(PoolMath.floatToYInt(x), PoolMath.floatToYInt(y));
	}

	public double tt(YVector _pcls108) {
		double d1 = Math.atan2(y, x);
		double d2 = Math.atan2(_pcls108.y, _pcls108.x);
		double d3 = d2 - d1;
		if (Math.abs(d3) > 3.1415926535897931D)
			if (d3 > 0.0D)
				d3 = -(6.2831853071795862D - d3);
			else
				d3 = 6.2831853071795862D - Math.abs(d3);
		return d3;
	}

	public void versor() {
		if (f) {
			float f1 = abs();
			if (f1 == 0.0F) {
				c = 0.0F;
				d = 0.0F;
			}
			else {
				c = x / f1;
				d = y / f1;
			}
			f = false;
		}
		x = c;
		y = d;
		g = true;
	}
}
