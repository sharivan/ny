// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;
import java.awt.FontMetrics;

// Referenced classes of package y.po:
// _cls79, _cls103, _cls78, _cls1,
// _cls116

public abstract class YahooList extends YahooControl {

	public static String getText(StringBuffer stringbuffer,
			FontMetrics fontmetrics, int i) {
		if (stringbuffer == null)
			throw new IllegalArgumentException("null list");
		if (fontmetrics == null)
			throw new IllegalArgumentException("null fm");
		String s = new String(stringbuffer);
		while (fontmetrics.stringWidth(s) > i - 2 && stringbuffer.length() > 0) {
			stringbuffer.setLength(stringbuffer.length() - 1);
			s = new String(String.valueOf(stringbuffer) + "..");
		}
		return s;
	}

	public _cls1	a123;
	public _cls1	b123;
	public _cls103	c123;
	public int		d123;
	public int		e123;
	public int		f123;
	public int		g123;

	public int		h;

	public YahooList() {
		this(true);
	}

	public YahooList(boolean flag) {
		this(false, flag);
	}

	public YahooList(boolean flag, boolean flag1) {
		d123 = 0;
		e123 = 0;
		f123 = 0;
		g123 = 0;
		h = 0;
		c123 = new _cls103(this);
		addChildObject(c123, 1, 1, 0, 1, true);
		a123 = new _cls1(1);
		if (flag1)
			addChildObject(a123, 1, 1, 1, 1, false);
		if (flag) {
			b123 = new _cls1(0);
			addChildObject(b123, 1, 1, 0, 2, false);
		}
	}

	public boolean _v() {
		if (e123 < d123 + g123) {
			d123 = Math.max(0, e123 - g123);
			return true;
		}
		return false;
	}

	public int av() {
		return e123;
	}

	public abstract void canvasMouseDown(Event event, int i, int j);

	public abstract void canvasMouseUp(Event event, int i, int j);

	public void cv(int i, int j) {
		e123 += j;
		if (i < d123 + g123)
			invalidate();
		Yu();
	}

	public abstract int Ds();

	public abstract int Es();

	public final void fv(int i, int j) {
		if (i + j > d123 && i < d123 + g123)
			invalidate();
	}

	@Override
	public int getHeight() {
		return g123;
	}

	public final void gv(int i) {
		a123.q(i);
	}

	public void jv() {
		d123 = Math.max(0, e123 - g123);
		Yu();
		invalidate();
	}

	@Override
	public abstract void paint(YahooGraphics graphics, int i, int j, int k,
			int l);

	@Override
	public boolean processEvent(Event event) {
		if (event.target == a123 && event.id == 605) {
			d123 = ((Integer) event.arg).intValue();
			Vs(d123);
			invalidate();
		}
		else if (event.target == b123 && event.id == 605) {
			f123 = ((Integer) event.arg).intValue();
			invalidate();
		}
		else {
			return false;
		}
		return true;
	}

	public void Us(int i, int j) {
	}

	public void Vs(int i) {
	}

	public void Xu() {
		Zu(0, e123);
	}

	public void Yu() {
		a123.r(d123, ((YahooComponent) c123).height, 0, e123);
	}

	public void Zu(int i, int j) {
		e123 -= j;
		if (_v() || i < d123 + g123)
			invalidate();
		Yu();
	}
}
