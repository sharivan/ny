// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls108, _cls16, _cls20, _cls46

public class YIVector {

	public int		a;
	public int		b;
	protected int	cos;
	protected int	sin;
	protected int	e;
	private boolean	angleChanged;
	private boolean	absChanged;

	public YIVector() {
		this(0, 0);
	}

	public YIVector(float x, float y) {
		this(PoolMath.floatToYInt(x), PoolMath.floatToYInt(y));
	}

	public YIVector(int i, int j) {
		set(i, j);
	}

	public YIVector(YIPoint _pcls46, YIPoint _pcls46_1) {
		set(_pcls46_1.a - _pcls46.a, _pcls46_1.b - _pcls46.b);
	}

	public int abs() {
		if (absChanged) {
			e = abs2();
			absChanged = false;
		}
		return e;
	}

	protected int abs2() {
		int i = (int) PoolMath2.sqrt(PoolMath2.mul(a, a) + PoolMath2.mul(b, b));
		return i;
	}

	public void absMul(int i) {
		if (abs() == 0 || i < PoolMath.div(PoolMath.n_1, abs())) {
			versor();
		}
		else {
			a = PoolMath.mul(a, i);
			b = PoolMath.mul(b, i);
		}
		angleChanged = true;
		absChanged = true;
	}

	public void add(int i) {
		if (angleChanged) {
			int j = abs();
			if (j == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, j);
				sin = PoolMath.div(b, j);
			}
			angleChanged = false;
		}
		a += PoolMath.mul(cos, i);
		b += PoolMath.mul(sin, i);
		angleChanged = true;
		absChanged = true;
	}

	public void add(YIVector _pcls48) {
		a += _pcls48.a;
		b += _pcls48.b;
		angleChanged = true;
		absChanged = true;
	}

	public int angleFrom(YIVector _pcls48) {
		int i = PoolMath.arctan(b, a);
		int j = PoolMath.arctan(_pcls48.b, _pcls48.a);
		int k = j - i;
		if (Math.abs(k) > PoolMath.pi)
			if (k > 0)
				k = -(PoolMath.pi * 2 - k);
			else
				k = PoolMath.pi * 2 - Math.abs(k);
		return k;
	}

	protected void computeCosSin() {
		if (angleChanged) {
			int i = abs();
			if (i == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, i);
				sin = PoolMath.div(b, i);
			}
			angleChanged = false;
		}
	}

	public boolean Ef(YIVector _pcls48) {
		int i = PoolMath.Ha(a);
		int j = PoolMath.Ha(b);
		int k = PoolMath.Ha(_pcls48.a);
		int l = PoolMath.Ha(_pcls48.b);
		return i == k && j == l;
	}

	public YIVector je() {
		return new YIVector(a, b);
	}

	public void mul(int i) {
		a = PoolMath.mul(a, i);
		b = PoolMath.mul(b, i);
		angleChanged = true;
		absChanged = true;
	}

	public long mul(YIVector _pcls48) {
		return PoolMath2.mul(a, _pcls48.a) + PoolMath2.mul(b, _pcls48.b);
	}

	public void neg() {
		a = -a;
		b = -b;
		angleChanged = true;
		absChanged = true;
	}

	public void neg2() {
		a = -a;
		b = -b;
		angleChanged = true;
	}

	public boolean Nf(YIVector _pcls48) {
		if (angleChanged) {
			int i = abs();
			if (i == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, i);
				sin = PoolMath.div(b, i);
			}
			angleChanged = false;
		}
		_pcls48.computeCosSin();
		long l = PoolMath.mul(cos, _pcls48.cos)
				+ PoolMath.mul(sin, _pcls48.sin);
		return Math.abs(PoolMath.n_1 + l) < 1000L;
	}

	public boolean noIsNull() {
		return a != 0 || b != 0;
	}

	public long norm() {
		long l = PoolMath2.mul(a, a) + PoolMath2.mul(b, b);
		return l;
	}

	public boolean Of(YIVector _pcls48) {
		if (angleChanged) {
			int i = abs();
			if (i == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, i);
				sin = PoolMath.div(b, i);
			}
			angleChanged = false;
		}
		_pcls48.computeCosSin();
		long l = PoolMath.mul(cos, _pcls48.cos)
				+ PoolMath.mul(sin, _pcls48.sin);
		return Math.abs(PoolMath.n_1 - l) < 1000L;
	}

	public int proj(YIVector _pcls48) {
		_pcls48.computeCosSin();
		return PoolMath.mul(a, _pcls48.cos) + PoolMath.mul(b, _pcls48.sin);
	}

	public void rotate(int teta) {
		if (abs() == 0) {
			return;
		}
		int cos_teta = PoolMath.cos(teta);
		int sin_teta = PoolMath.sin(teta);
		int cos_2teta = PoolMath.mul(a, cos_teta) - PoolMath.mul(b, sin_teta);
		int sin_2teta = PoolMath.mul(a, sin_teta) + PoolMath.mul(b, cos_teta);
		a = cos_2teta;
		b = sin_2teta;
		angleChanged = true;
		return;
	}

	public void set(int i, int j) {
		a = i;
		b = j;
		angleChanged = true;
		absChanged = true;
	}

	public void setFrom(YIPoint _pcls46) {
		set(_pcls46.a, _pcls46.b);
	}

	public void setFrom(YIPoint _pcls46, YIPoint _pcls46_1) {
		set(_pcls46_1.a - _pcls46.a, _pcls46_1.b - _pcls46.b);
	}

	public void setFrom(YIVector _pcls48) {
		set(_pcls48.a, _pcls48.b);
	}

	public YIVector setTo(YIVector _pcls48) {
		_pcls48.set(a, b);
		return _pcls48;
	}

	public YIVector setVersor(YIVector _pcls48) {
		if (angleChanged) {
			int i = abs();
			if (i == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, i);
				sin = PoolMath.div(b, i);
			}
			angleChanged = false;
		}
		_pcls48.set(cos, sin);
		return _pcls48;
	}

	public void sub(int i)// abs<--abs-i
	{
		int j = abs();
		if (j == 0) {
			set(0, 0);
			return;
		}
		int k = j - i;
		if (k <= 0) {
			set(0, 0);
		}
		else {
			versor();
			mul(k);
		}
		absChanged = true;
	}

	public void subFrom(YIVector _pcls48) {
		a -= _pcls48.a;
		b -= _pcls48.b;
		angleChanged = true;
		absChanged = true;
	}

	@Override
	public String toString() {
		String s = "(";
		s = s + PoolMath.yintToFloat(a) + " " + PoolMath.yintToFloat(b) + ")";
		return s;
	}

	public YIPoint toYIPoint() {
		return new YIPoint(a, b);
	}

	public YVector toYPoint() {
		return new YVector(PoolMath.yintToFloat(a), PoolMath.yintToFloat(b));
	}

	public void versor() {
		if (angleChanged) {
			int i = abs();
			if (i == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, i);
				sin = PoolMath.div(b, i);
			}
			angleChanged = false;
		}
		a = cos;
		b = sin;
		absChanged = true;
	}

	public int zf(YIVector _pcls48) {
		if (angleChanged) {
			int i = abs();
			if (i == 0) {
				cos = 0;
				sin = 0;
			}
			else {
				cos = PoolMath.div(a, i);
				sin = PoolMath.div(b, i);
			}
			angleChanged = false;
		}
		_pcls48.computeCosSin();
		int j = PoolMath.mul(cos, _pcls48.cos) + PoolMath.mul(sin, _pcls48.sin);
		if (j > PoolMath.n_1)
			j = PoolMath.n_1;
		if (j < -PoolMath.n_1)
			j = -PoolMath.n_1;
		return PoolMath.arctan(j);
	}
}
