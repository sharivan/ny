// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

// Referenced classes of package y.po:
// _cls169

public class YVector {

	public float	x;
	public float	y;
	protected float	cos;
	protected float	sin;
	protected float	abs_cache;
	private boolean	angleChanged;
	private boolean	absChanged;

	public YVector() {
		this(0.0F, 0.0F);
	}

	public YVector(float x, float y) {
		angleChanged = true;
		absChanged = true;
		this.x = x;
		this.y = y;
	}

	public YVector(YPoint point1, YPoint point2) {
		this(point2.x - point1.x, point2.y - point1.y);
	}

	/**
	 * @param vector
	 */
	public YVector(YVector vector) {
		this(vector.x, vector.y);
	}

	public float abs() {
		if (absChanged) {
			abs_cache = It();
			absChanged = false;
		}
		return abs_cache;
	}

	public void absMul(float i) {
		if (abs() == 0 || i < 1 / abs())
			versor();
		else {
			x = x * i;
			y = y * i;
		}
		angleChanged = i <= 0;
		absChanged = true;
	}

	/**
	 * @param factor
	 */
	public void add(float factor) {
		computeCosSin();
		x += cos * factor;
		y += sin * factor;
		absChanged = factor != 1;
	}

	public void add(YVector point) {
		x += point.x;
		y += point.y;
		absChanged = true;
		angleChanged = true;
	}

	public double angle(YVector _pcls108) {
		computeCosSin();
		_pcls108.computeCosSin();
		float f1 = cos * _pcls108.cos + sin * _pcls108.sin;
		if (f1 > 1.0F)
			f1 = 1.0F;
		if (f1 < -1F)
			f1 = -1F;
		return Math.acos(f1);
	}

	@Override
	public Object clone() {
		return new YVector(x, y);
	}

	protected void computeCosSin() {
		if (angleChanged) {
			float f1 = abs();
			if (f1 == 0.0F) {
				cos = 0.0F;
				sin = 0.0F;
			}
			else {
				cos = x / f1;
				sin = y / f1;
			}
			angleChanged = false;
			absChanged = true;
		}
	}

	public boolean equalsWithRounding(YVector vector) {
		return equalsWithRounding(vector, 0.001F);
	}

	public boolean equalsWithRounding(YVector vector, float epslon) {
		YVector v = new YVector(vector);
		v.sub(this);
		return v.abs() < epslon;
	}

	protected float It() {
		float f1 = (float) Math.sqrt(x * x + y * y);
		return f1;
	}

	public void mul(float f1) {
		x *= f1;
		y *= f1;
		angleChanged = f1 < 0;
		absChanged = f1 > 1 || f1 < -1;
	}

	public float mul(YVector _pcls48) {
		return x * _pcls48.x + y * _pcls48.y;
	}

	public void neg() {
		x = -x;
		y = -y;
		angleChanged = true;
	}

	public boolean Nf(YVector _pcls48) {
		computeCosSin();
		_pcls48.computeCosSin();
		float l = cos * _pcls48.cos + sin * _pcls48.sin;
		return Math.abs(1 + l) < 1000F / 65536F;
	}

	public boolean noIsNull() {
		return x != 0 || y != 0;
	}

	public boolean Of(YVector _pcls48) {
		computeCosSin();
		_pcls48.computeCosSin();
		float l = cos * _pcls48.cos + sin * _pcls48.sin;
		return Math.abs(1 - l) < 1000F / 65536F;
	}

	public float proj(YVector point) {
		point.computeCosSin();
		return x * point.cos + y * point.sin;
	}

	public void rotate(double angle) {
		if (x == 0 && y == 0)
			return;
		rotate(Math.sin(angle), Math.cos(angle));
	}

	public void rotate(double sin, double cos) {
		float f1 = (float) (x * cos - y * sin);
		float f2 = (float) (x * sin + y * cos);
		x = f1;
		y = f2;
		angleChanged = sin != 0 || cos != 1;
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
		absChanged = f1 != 1 && f1 != -1;
		angleChanged = f1 < 0;
	}

	public void setCoords(float f1, float f2) {
		x = f1;
		y = f2;
		angleChanged = true;
		absChanged = true;
	}

	/**
	 * @param point
	 */
	public void setCoords(YPoint point) {
		setCoords(point.x, point.y);
	}

	public void setCoords(YPoint _pcls169, YPoint _pcls169_1) {
		setCoords(_pcls169_1.x - _pcls169.x, _pcls169_1.y - _pcls169.y);
	}

	public void setCoords(YVector vector) {
		setCoords(vector.x, vector.y);
	}

	public YVector setCoordsTo(YVector _pcls108) {
		_pcls108.setCoords(x, y);
		return _pcls108;
	}

	public YVector setVersor(YVector _pcls48) {
		computeCosSin();
		_pcls48.setCoords(cos, sin);
		return _pcls48;
	}

	public void sub(float i)// abs<--abs-i
	{
		float j = abs();
		if (j == 0) {
			setCoords(0, 0);
			return;
		}
		float k = j - i;
		if (k <= 0)
			setCoords(0, 0);
		else {
			versor();
			mul(k);
		}
		absChanged = true;
	}

	public void sub(YVector point) {
		x -= point.x;
		y -= point.y;
		angleChanged = true;
		absChanged = true;
	}

	@Override
	public String toString() {
		String s = "(";
		s = s + x + " " + y + ")";
		return s;
	}

	public double tt(YVector _pcls108) {
		double d1 = Math.atan2(y, x);
		double d2 = Math.atan2(_pcls108.y, _pcls108.x);
		double d3 = d2 - d1;
		if (Math.abs(d3) > Math.PI)
			if (d3 > 0.0D)
				d3 = -(2 * Math.PI - d3);
			else
				d3 = 2 * Math.PI - Math.abs(d3);
		return d3;
	}

	public void versor() {
		computeCosSin();
		x = cos;
		y = sin;
		absChanged = true;
	}

}
