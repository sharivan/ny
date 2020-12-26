// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Event;
import java.awt.Image;

import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.utils.YahooImage;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls79, _cls168, _cls78, _cls13,
// _cls87, _cls56

public class YahooGameBrought extends YahooControl {

	String						ygb_a;
	AbstractYahooGamesApplet	ygb_b;
	YahooImage					ygb_c;

	public YahooGameBrought(AbstractYahooGamesApplet _pcls56, Image image,
			String s) {
		ygb_c = new YahooImage(image, 88, 31);
		addChildObject(ygb_c, 1, 1, 1, 0, 4, 4, 4, 4);
		YahooLabel _lcls87 = new YahooLabel(_pcls56.lookupString(0x66500031),
				YahooLabel.yl_a);
		_lcls87.Oo(0, 0, 10);
		addChildObject(_lcls87, 1, 1, 0, 0);
		ygb_a = s;
		ygb_b = _pcls56;
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		if (event.target == ygb_c) {
			ygb_b.showDocument(ygb_a, "_blank");
			return true;
		}
		return super.eventMouseDown(event, j, k);
	}
}
