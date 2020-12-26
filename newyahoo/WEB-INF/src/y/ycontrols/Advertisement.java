// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.utils.Formater;
import y.utils.YahooImage;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls79, _cls145, _cls168, _cls78,
// _cls13, _cls87, _cls56

public class Advertisement extends YahooControl {

	public int						ad_b;
	public YahooImage				ad_c;
	public YahooControl				ad_d;
	public YahooComponent			e;
	public String					ad_f;
	public AbstractYahooGamesApplet	ad_g;
	public YahooLabel				h;
	public String					i;

	public Advertisement(YahooComponent _pcls78,
			AbstractYahooGamesApplet _pcls56, String s) {
		super(2);
		ad_b = 0;
		ad_c = new YahooImage(null, 300, 200, true);
		ad_d = new YahooControl();
		ad_f = null;
		h = new YahooLabel("", YahooLabel.yl_a);
		addChildObject(_pcls78, 0);
		addChildObject(ad_d, 1);
		ad_d.addChildObject(new YahooLabel(_pcls56.lookupString(0x66501131)),
				15, 0, 3, 1, 1, 0, 0);
		ad_d.addChildObject(ad_c, 1, 1, 0, 1, 0, 4, 0, 4);
		ad_d.setBackColor(Color.white);
		ad_d.addChildObject(h, 15, 2, 3, 2, 1, 0, 2);
		ad_g = _pcls56;
		i = s;
	}

	public void Ap(String s, Image image) {
		ad_c.setImage(image);
		ad_f = s;
		ad_b = 20000;
	}

	public void Bp(int j) {
		if (j < 0)
			j = 0;
		h.setCaption(i + Formater.formatTimer(j + 999));
	}

	public void Cp() {
		ad_f = null;
		qo(0);
	}

	public void Dp(YahooComponent _pcls78) {
		if (e != null)
			ad_d.removeChildObject(e);
		e = _pcls78;
		ad_d.addChildObject(_pcls78, 1, 1, 1, 1, 0, 4, 0, 4);
		qo(1);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		if (event.target == ad_c && ad_f != null) {
			ad_g.showDocument(ad_f, "_blank");
			return true;
		}
		return false;
	}
}
