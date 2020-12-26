// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;

import y.controls.YahooGraphics;
import y.controls.YahooLabel;

// Referenced classes of package y.po:
// _cls114, _cls43, _cls139, _cls78,
// _cls79, _cls116, _cls87

public class _cls88 extends _cls114 {

	static final Color	f88	= new Color(0x94a7be);
	YahooLabel			a88;
	boolean				e;

	public _cls88() {
		this(false);
	}

	public _cls88(boolean flag) {
		this("", flag);
	}

	public _cls88(String s, boolean flag) {
		e = false;
		_cls139 _lcls139 = new _cls139(flag ? 4 : 8, flag ? 4 : 8,
				flag ? 4 : 8, flag ? 4 : 8);
		addChildObject(_lcls139, 1, 1, 0, 0);
		Sn(true);
		_lcls139.Sn(true);
		a88 = new YahooLabel("", YahooLabel.yl_a);
		if (!flag)
			a88.setBackColor(getBackColor());
		else
			setBackColor(f88);
		_lcls139.addChildObject(a88, 1, 1, 0, 0, true);
		Ro(s);
		e = flag;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		if (!a88.getFirstLine().equals("")) {
			if (e) {
				_cls43.cf(yahooGraphics, f88, 0, 0, getWidth() - 1,
						getHeight() - 1);
			}
			else {
				yahooGraphics.setColor(getBackColor());
				yahooGraphics.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
				yahooGraphics.setColor(getForeColor());
				yahooGraphics.qu(0, 0, getWidth(), getHeight(), 2);
			}
			super.paint(yahooGraphics);
		}
	}

	@Override
	public boolean processEvent(Event event) {
		event.target = this;
		return false;
	}

	public void Ro(String s) {
		if (a88.setCaption(s))
			a88.rb();
	}

}
