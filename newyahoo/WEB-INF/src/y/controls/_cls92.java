// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;

// Referenced classes of package y.po:
// _cls78, _cls77, _cls113, _cls116

public class _cls92 extends YahooComponent {

	public YahooComboBox	b92;
	public int				c92;
	public int				d92;
	public FontMetrics		e92;

	public _cls92(YahooComboBox _pcls77, int j, int k) {
		b92 = _pcls77;
		c92 = j;
		d92 = k;
		setBackColor(((YahooComponent) _pcls77).backColor);
	}

	void cp(int j) {
		b92.ycc_d = -1;
		if (j >= 0) {
			int k = j / e92.getHeight();
			if (k < b92.ycc_a.size())
				b92.ycc_d = k;
		}
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		cp(k);
		Event event1 = b92.Zm();
		if (event1 != null)
			b92.doEvent(event1);
		return true;
	}

	@Override
	public boolean eventMouseMove(Event event, int j, int k) {
		cp(k);
		invalidate();
		return true;
	}

	@Override
	public int getHeight1() {
		return d92;
	}

	@Override
	public int getWidth1() {
		return c92;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		yahooGraphics.setColor(Color.black);
		yahooGraphics.drawRect(0, 0, c92, d92);
		yahooGraphics.setFont(YahooComponent.defaultFont);
		int j = 0;
		for (int k = 0; j < b92.ycc_a.size(); k += b92.ycc_g.getHeight()) {
			yahooGraphics.setColor(Color.black);
			if (j == b92.ycc_d) {
				yahooGraphics.setColor(YahooComponent.defaultColor);
				yahooGraphics.fillRect(0, k, c92, b92.ycc_g.getHeight() + 2);
				yahooGraphics.setColor(Color.white);
			}
			String s = b92.ycc_a.elementAt(j);
			yahooGraphics.drawString(s, 2, j * b92.ycc_g.getHeight()
					+ b92.ycc_g.getAscent() + 2);
			j++;
		}

	}

	@Override
	public boolean processEvent(Event event) {
		if (event.target == this && event.id == Event.LOST_FOCUS) {
			if (b92.h != null)
				b92._n();
			return true;
		}
		return super.processEvent(event);
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		e92 = getFontMetrics(YahooComponent.defaultFont);
	}
}
