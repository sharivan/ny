// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;

// Referenced classes of package y.po:
// _cls79, _cls78, _cls116, _cls87

public class YahooButton extends YahooControl {

	public static void setGraphic(YahooGraphics _pcls116, int j, int i1,
			int j1, int k1) {
		for (int l1 = 0; l1 < j1; l1 += 2) {
			int i2 = Math.min(k1, j1 - l1) - 1;
			_pcls116.drawLine(j + l1, i1, j + l1 + i2, i1 + i2);
		}

		for (int j2 = 0; j2 < k1; j2 += 2) {
			int k2 = Math.min(j1, k1 - j2) - 1;
			_pcls116.drawLine(j, i1 + j2, j + k2, i1 + k2 + j2);
		}

	}

	protected boolean			yb_c;
	protected boolean			yb_d;
	protected boolean			enabled;
	int							yb_f;
	int							yb_g;
	protected YahooComponent	caption;

	YahooControl				i;

	public YahooButton(String s) {
		this(new YahooLabel(s));
	}

	public YahooButton(YahooComponent _pcls78) {
		yb_c = false;
		yb_d = false;
		enabled = true;
		i = new YahooControl();
		caption = _pcls78;
		addChildObject(i, 10, 1, 1, 1, 1, 0, 0, 3, 3, 3, 3);
		i.addChildObject(_pcls78, 10, 0, 1, 1, 1, 0, 0);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int i1) {
		if (enabled) {
			yb_d = true;
			yb_f = j;
			yb_g = i1;
			Qe();
		}
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int j, int i1) {
		yb_f = j;
		yb_g = i1;
		Qe();
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int j, int i1) {
		if (yb_c) {
			super.parent.doEvent(new Event(this, 502, null));
			doEvent(new Event(this, 1001, null));
			yb_d = false;
			Qe();
		}
		return true;
	}

	protected void Je(YahooGraphics _pcls116, int j) {
		setGraphic(_pcls116, j, j, getWidth() - 2 * j, getHeight() - 2 * j);
	}

	protected void Oe() {
		if (yb_c) {
			caption.left++;
			caption.top++;
		}
		else {
			caption.left--;
			caption.top--;
		}
		invalidate();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		((YahooComponent) i).backColor = in();
		super.paint(yahooGraphics);
		yahooGraphics.du(0, 0, getWidth(), getHeight(), kn(), jn());
		for (int j = 0; j < 2; j++) {
			yahooGraphics.setColor(jn());
			yahooGraphics.ou(0, 0, 3, 1, getWidth() - 4, 1, getWidth(),
					getHeight(), j * 2);
			yahooGraphics.ou(0, 0, 1, 3, 1, getHeight() - 4, getWidth(),
					getHeight(), j * 2);
			yahooGraphics.setColor(yb_c ^ j % 2 == 0 ? Color.white : ln());
			yahooGraphics.ou(0, 0, 3, 2, getWidth() - 4, 2, getWidth(),
					getHeight(), j * 2);
			yahooGraphics.ou(0, 0, 2, 3, 2, getHeight() - 4, getWidth(),
					getHeight(), j * 2);
		}

		if (!enabled) {
			yahooGraphics.setColor(getBackColor());
			Je(yahooGraphics, 3);
		}
	}

	protected void Qe() {
		boolean flag = yb_c;
		yb_c = yb_d && yb_f >= 0 && yb_f < getWidth() && yb_g >= 0
				&& yb_g < getHeight();
		if (flag != yb_c)
			Oe();
	}

	public void setCaption(String s) {
		if (caption instanceof YahooLabel)
			((YahooLabel) caption).setCaption(s);
	}

	public void setEnabled(boolean flag) {
		enabled = flag;
		invalidate();
	}
}
