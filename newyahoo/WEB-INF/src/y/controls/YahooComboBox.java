// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;
import java.awt.FontMetrics;
import java.util.Vector;

import y.utils.TimerEngine;

// Referenced classes of package y.po:
// _cls79, _cls113, _cls92, _cls78,
// _cls116, _cls136

public class YahooComboBox extends YahooControl {

	public Vector<String>	ycc_a;
	public int				ycc_b;
	public int				ycc_c;
	public int				ycc_d;
	public YahooTextBox		e;
	public _cls113			ycc_f;
	public FontMetrics		ycc_g;
	public _cls92			h;
	public boolean			i;
	public boolean			j;
	public int				k;
	public YahooComponent	m;
	public YahooControl		ycc_n;

	public YahooComboBox(TimerEngine timerHandler) {
		ycc_a = new Vector<String>();
		ycc_b = -1;
		ycc_c = 0;
		ycc_d = -1;
		e = new YahooTextBox(timerHandler);
		ycc_f = new _cls113(this);
		h = null;
		i = false;
		m = new YahooComponent(1, 1);
		ycc_n = new YahooControl();
		addChildObject(ycc_n, 10, 1, 1, 1, 1, 0, 0, 2, 2, 2, 2);
		ycc_n.addChildObject(e, 17, 2, 2, 1, 1, 0, 0);
		e.setEnabled(false);
		ycc_n.addChildObject(m, 10, 3, 3, 1, 1, 1, 0);
		ycc_n.addChildObject(ycc_f, 17, 0, 0, 1, 1, 2, 0);
	}

	public void _n() {
		getContainer().removeChildObject(h);
		h = null;
	}

	protected void an(int i1, int j1) {
		h = new _cls92(this, i1, j1);
	}

	public int cn() {
		return ycc_a.size();
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		if (event.target == ycc_f && !i) {
			ycc_d = -1;
			j = true;
			ycc_f.invalidate();
			if (h == null) {
				int k1 = getWidth1();
				int l1 = ycc_g.getHeight() * ycc_a.size();
				int i2 = getWorldLeft(getContainer());
				int j2 = getWorldTop(getContainer()) + getHeight1();
				if (j2 + l1 > getContainer().getHeight()) {
					k = j2 - (getContainer().getHeight() - l1);
					j2 -= k;
				}
				else {
					k = 0;
				}
				an(k1, l1);
				getContainer().addChildObject(h, i2, j2, true);
				xb(h, true);
			}
			else {
				_n();
			}
		}
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		if (h != null) {
			j1 -= getHeight1() + 2 - k;
			ycc_d = -1;
			if (j1 >= 0) {
				int k1 = j1 / ycc_g.getHeight();
				if (k1 < ycc_a.size())
					ycc_d = k1;
			}
			h.invalidate();
		}
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		Event event1 = null;
		if (event.target == ycc_f) {
			j = false;
			ycc_f.invalidate();
			event1 = Zm();
		}
		if (event1 != null)
			doEvent(event1);
		return true;
	}

	public void fn(int i1) {
		e.setText(ycc_a.elementAt(i1));
		ycc_c = i1;
	}

	public int getItemIndex() {
		return ycc_c;
	}

	@Override
	public int getWidth1() {
		int i1 = 0;
		for (int j1 = 0; j1 < ycc_a.size(); j1++) {
			String s = ycc_a.elementAt(j1);
			int k1 = ycc_g.stringWidth(s);
			if (k1 > i1)
				i1 = k1;
		}

		return i1 + super.getWidth1();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		e.backColor = in();
		super.paint(yahooGraphics);
		yahooGraphics.du(0, 0, getWidth(), getHeight(), kn(), jn());
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		ycc_g = getFontMetrics(YahooComponent.defaultFont);
		m.setBackColor(jn());
	}

	public void setEnabled(boolean flag) {
		i = flag ^ true;
		ycc_f.invalidate();
	}

	public void setText(String s) {
		ycc_a.add(s);
		if (ycc_b == -1) {
			ycc_b = 0;
			e.setText(s);
		}
		if (h != null)
			h.invalidate();
	}

	public Event Zm() {
		if (h != null && ycc_d != -1) {
			_n();
			ycc_b = ycc_d;
			String s = ycc_a.elementAt(ycc_d);
			e.setText(s);
			ycc_c = ycc_d;
			return new Event(this, 1001, s);
		}
		return null;
	}
}
