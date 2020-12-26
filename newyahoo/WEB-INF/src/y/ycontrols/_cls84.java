// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;
import y.yutils.YahooTable;

// Referenced classes of package y.po:
// _cls78, _cls174, _cls116

public class _cls84 extends YahooComponent {

	String		a84[];
	int			b84;
	int			c84;
	boolean		d84;
	Color		e;
	Font		f84;
	FontMetrics	fontMetrics;

	public _cls84(Color color, int j, int k, YahooTable _pcls174, int i1) {
		super(true);
		e = Color.red;
		b84 = j;
		f84 = _pcls174.Oz(i1);
		c84 = k;
		a84 = new String[k];
		for (int j1 = 0; j1 < k; j1++)
			a84[j1] = "";

	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		if (Go(j, k)) {
			d84 = true;
			invalidate();
		}
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int j, int k) {
		if (d84 && Go(j, k))
			doEvent(new Event(this, 1001, null));
		d84 = false;
		invalidate();
		return true;
	}

	@Override
	public int getHeight1() {
		return c84 * fontMetrics.getHeight();
	}

	@Override
	public int getWidth1() {
		return b84;
	}

	boolean Go(int j, int k) {
		return fontMetrics != null && j >= 0 && k >= 0 && j < getWidth()
				&& k < getHeight();
	}

	public void Io(int j, String s) {
		a84[j] = s;
		invalidate();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		yahooGraphics.setFont(f84);
		yahooGraphics.setColor(d84 ? e : getForeColor());
		for (int j = 0; j < c84; j++)
			yahooGraphics.drawString(a84[j], 0, fontMetrics.getHeight() * j
					+ fontMetrics.getAscent());

	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		fontMetrics = getFontMetrics(f84);
	}
}
