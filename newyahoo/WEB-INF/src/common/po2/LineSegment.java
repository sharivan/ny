// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

// Referenced classes of package y.po:
// _cls16, _cls20, _cls46

public class LineSegment {

	public float	x1;
	public float	y1;
	public float	x2;
	public float	y2;

	public LineSegment() {
		setCoords(0, 0, 0, 0);
	}

	public LineSegment(float i, float j, float k, float l) {
		setCoords(i, j, k, l);
	}

	public boolean intercept(LineSegment _pcls130, YPoint _pcls46) {
		float i = x2 - x1;
		float j = _pcls130.x2 - _pcls130.x1;
		if (j != 0 && i != 0) {
			float k = (y2 - y1) / i;
			float j1 = (_pcls130.y2 - _pcls130.y1) / j;
			float l2 = y1 - k * x1;
			float l5 = _pcls130.y2 - j1 * _pcls130.x2;
			float i2 = k - j1;
			if (i2 == 0)
				return false;
			float k2 = (l5 - l2) / i2;
			if (k2 < x1 || k2 > x2 || k2 < _pcls130.x1 || k2 > _pcls130.x2) {
				return false;
			}
			_pcls46.x = k2;
			_pcls46.y = k * k2 + l2;
			return true;
		}
		if (j == 0) {
			if (i == 0)
				return false;
			float l = _pcls130.x1;
			float k1 = (y2 - y1) * i;
			float l3 = y1 - k1 * x1;
			float l6 = k1 * l + l3;
			float j2 = _pcls130.y2 < _pcls130.y1 ? _pcls130.y2 : _pcls130.y1;
			float i3 = _pcls130.y2 < _pcls130.y1 ? _pcls130.y1 : _pcls130.y2;
			if (l6 < j2 || l6 > i3 || l < x1 || l > x2) {
				return false;
			}
			_pcls46.x = l;
			_pcls46.y = l6;
			return true;
		}
		float i1 = x1;
		float l1 = (_pcls130.y2 - _pcls130.y1) / j;
		float l4 = _pcls130.y1 - l1 * _pcls130.x1;
		float l7 = l1 * i1 + l4;
		float j3 = y2 < y1 ? y2 : y1;
		float k3 = y2 < y1 ? y1 : y2;
		if (l7 < j3 || l7 > k3 || i1 < _pcls130.x1 || i1 > _pcls130.x2) {
			return false;
		}
		_pcls46.x = i1;
		_pcls46.y = l7;
		return true;
	}

	public void setCoords(float i, float j, float k, float l) {
		if (i <= k) {
			x1 = i;
			y1 = j;
			x2 = k;
			y2 = l;
		}
		else {
			x1 = k;
			y1 = l;
			x2 = i;
			y2 = j;
		}
	}

	@Override
	public String toString() {
		String s = "(";
		s = s + x1 + "," + y1 + "  " + x2 + "," + y2 + ")";
		return s;
	}
}
