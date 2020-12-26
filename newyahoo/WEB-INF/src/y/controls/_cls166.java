// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;

// Referenced classes of package y.po:
// _cls79, _cls78, _cls119

public class _cls166 extends YahooControl {

	AvatarList	a166;
	Color		b166;
	Color		c166;
	Color		d166;
	Color		e;
	Color		f166;
	Color		g166;
	boolean		h;

	public _cls166(AvatarList _pcls119, YahooComponent _pcls78) {
		super(0);
		b166 = null;
		c166 = null;
		d166 = null;
		e = null;
		f166 = null;
		g166 = null;
		h = false;
		a166 = _pcls119;
		addChildObject(_pcls78, 1, 1, 0, 0);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		a166.Pu(this);
		return true;
	}

	public void Sy() {
		if (b166 != null && c166 != null && d166 != null && e != null
				&& f166 != null && g166 != null) {
			if (h)
				nc(c166, b166, g166, f166, e, d166);
			else
				nc(b166, c166, d166, e, f166, g166);
			invalidate();
		}
	}

	public void Ty(boolean flag) {
		h = flag;
		Sy();
	}

	public void Uy(Color color, Color color1, Color color2, Color color3,
			Color color4, Color color5) {
		if (color != null && color1 != null && color2 != null && color3 != null
				&& color4 != null && color5 != null) {
			b166 = color;
			c166 = color1;
			d166 = color2;
			e = color3;
			f166 = color4;
			g166 = color5;
			Sy();
		}
	}
}
