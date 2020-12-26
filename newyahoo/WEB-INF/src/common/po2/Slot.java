// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

// Referenced classes of package y.po:
// _cls46, _cls16, _cls48, _cls171

public class Slot extends YPoint {

	public static float	e	= 6F / 65536F;
	public static float	f	= 2F / 65536F;
	public int			index;
	public float		radius;
	public boolean		d;

	public Slot(int i, float ai[]) {
		index = -1;
		d = false;
		float j = ai[0];
		float k = ai[1];
		float l = ai[2];
		x = j;
		y = k;
		radius = l;
		index = i;
	}

	public Slot(int i, int j) {
		super(i, j);
		index = -1;
		d = false;
	}

	public Slot(Slot slot) {
		super(((YPoint) slot).x, ((YPoint) slot).y);
		index = slot.index;
		d = false;
	}

	public boolean contains(float i, float j) {
		return distance(i, j) < radius - 1F / 65536F;
	}

	public boolean contains(YPoint _pcls46) {
		return contains(_pcls46.x, _pcls46.y);
	}

	public boolean containsIncludingTheBorder(YPoint _pcls46) {
		return distance(_pcls46) <= radius + f;
	}

	public byte getIndex() {
		return (byte) index;
	}

	public YVector getVelToMySelf(YPoint point) {
		if (!containsIncludingTheBorder(point))
			return new YVector();
		YVector _lcls48 = new YVector(point, this);
		_lcls48.versor();
		_lcls48.mul(e);
		return _lcls48;
	}

}
