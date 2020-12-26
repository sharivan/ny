// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls169, _cls154

public class YLine {

	public int	a;
	public int	b;
	public int	c;
	public int	d;

	public YLine() {
		setCoords(0, 0, 0, 0);
	}

	public YLine(int i, int j, int k, int l) {
		setCoords(i, j, k, l);
	}

	public void setCoords(int i, int j, int k, int l) {
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

	public void setCoords(YPoint _pcls169, YPoint _pcls169_1) {
		setCoords((int) Math.rint(_pcls169.x), (int) Math.rint(_pcls169.y),
				(int) Math.rint(_pcls169_1.x), (int) Math.rint(_pcls169_1.y));
	}

	@Override
	public String toString() {
		String s = "(";
		s = s + a + "," + b + "  " + c + "," + d + ")";
		return s;
	}
}
