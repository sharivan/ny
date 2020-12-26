// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls46, _cls16, _cls48, _cls171

public class Slot extends YIPoint {

	public static int	e	= 6;
	public static int	f	= 2;
	public int			index;
	public int			c;
	public boolean		d;

	public Slot(int i, int j) {
		super(i, j);
		index = -1;
		d = false;
	}

	public Slot(int i, int ai[]) {
		index = -1;
		d = false;
		int j = PoolMath.intToYInt(ai[0]);
		int k = PoolMath.intToYInt(ai[1]);
		int l = PoolMath.intToYInt(ai[2]);
		super.a = j;
		super.b = k;
		c = l;
		index = i;
	}

	public Slot(Slot slot) {
		super(((YIPoint) slot).a, ((YIPoint) slot).b);
		index = slot.index;
		d = false;
	}

	public YIVector My(YIPoint point) {
		if (!Oy(point)) {
			return new YIVector();
		}
		YIVector _lcls48 = new YIVector(point, this);
		_lcls48.versor();
		_lcls48.mul(e);
		return _lcls48;
	}

	public byte Ny() {
		return (byte) index;
	}

	public boolean Oy(YIPoint _pcls46) {
		return distance(_pcls46) <= c + f;
	}

	public boolean Py(int i, int j) {
		return distance(i, j) < c - 1;
	}

	public boolean Qy(YIPoint _pcls46) {
		return Py(_pcls46.a, _pcls46.b);
	}

}
