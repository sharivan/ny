// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Image;

import y.controls.YahooGraphics;
import y.utils.YahooImage;

// Referenced classes of package y.po:
// _cls13, _cls31, _cls45, _cls78,
// _cls116

class YahooBallImage extends YahooImage {

	BallSprite	ybi_a;
	Image		ybi_c;

	YahooBallImage(BallSprite _pcls31) {
		super(null, 20, 21);
		ybi_a = _pcls31;
		ybi_c = YahooPoolImageList.loadImages().r;
	}

	public void dp() {
		Tn(10);
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		if (!ybi_a.getInSlot() && ybi_a.active)
			yahooGraphics.drawImage(ybi_c, 0, 0, this);
	}
}
