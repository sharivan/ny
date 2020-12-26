// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls16, _cls20, _cls46

public class PoolPocket {

	public int	a;
	public int	b;
	public int	c;
	public int	d;

	public PoolPocket() {
		Iw(0, 0, 0, 0);
	}

	public PoolPocket(int i, int j, int k, int l) {
		Iw(i, j, k, l);
	}

	public void Iw(int i, int j, int k, int l) {
		if (i <= k) {
			a = i;
			b = j;
			c = k;
			d = l;
		}
		else {
			a = k;
			b = l;
			c = i;
			d = j;
		}
	}

	public boolean sw(PoolPocket _pcls130, YIPoint _pcls46) {
		int i = c - a;
		int j = _pcls130.c - _pcls130.a;
		if (j != 0 && i != 0) {
			int k = PoolMath.div(d - b, i);
			int j1 = PoolMath.div(_pcls130.d - _pcls130.b, j);
			long l2 = b - PoolMath2.mul(k, a);
			long l5 = _pcls130.d - PoolMath2.mul(j1, _pcls130.c);
			int i2 = k - j1;
			if (i2 == 0)
				return false;
			int k2 = (int) PoolMath2.div(l5 - l2, i2);
			if (k2 < a || k2 > c || k2 < _pcls130.a || k2 > _pcls130.c) {
				return false;
			}
			_pcls46.a = k2;
			_pcls46.b = (int) (PoolMath2.mul(k, k2) + l2);
			return true;
		}
		if (j == 0) {
			if (i == 0)
				return false;
			int l = _pcls130.a;
			int k1 = PoolMath.div(d - b, i);
			long l3 = b - PoolMath2.mul(k1, a);
			long l6 = PoolMath2.mul(k1, l) + l3;
			int j2 = _pcls130.d < _pcls130.b ? _pcls130.d : _pcls130.b;
			int i3 = _pcls130.d < _pcls130.b ? _pcls130.b : _pcls130.d;
			if (l6 < j2 || l6 > i3 || l < a || l > c) {
				return false;
			}
			_pcls46.a = l;
			_pcls46.b = (int) l6;
			return true;
		}
		int i1 = a;
		long l1 = PoolMath.div(_pcls130.d - _pcls130.b, j);
		long l4 = _pcls130.b - PoolMath2.mul(l1, _pcls130.a);
		long l7 = PoolMath2.mul(l1, i1) + l4;
		int j3 = d < b ? d : b;
		int k3 = d < b ? b : d;
		if (l7 < j3 || l7 > k3 || i1 < _pcls130.a || i1 > _pcls130.c) {
			return false;
		}
		_pcls46.a = i1;
		_pcls46.b = (int) l7;
		return true;
	}

	@Override
	public String toString() {
		String s = "(";
		s = s + PoolMath.yintToFloat(a) + "," + PoolMath.yintToFloat(b) + "  "
				+ PoolMath.yintToFloat(c) + "," + PoolMath.yintToFloat(d) + ")";
		return s;
	}
}
