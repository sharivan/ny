// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Image;

import y.controls.YahooGraphics;
import y.utils.YahooImage;

// Referenced classes of package y.po:
// _cls13, _cls78, _cls116

public class BallImage extends YahooImage {

	Image	bi_a;
	int		bi_b;
	int		bi_c;

	public BallImage() {
		super(null);
	}

	public BallImage(Image image, int i, int j) {
		super(image, i, j);
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		if (bi_a != null)
			yahooGraphics.drawImage(bi_a, bi_b, bi_c, this);
	}

	@Override
	public void setImage(Image image) {
		bi_a = null;
		super.setImage(image);
	}

	public void xl(Image image, int i, int j) {
		bi_a = image;
		bi_b = i;
		bi_c = j;
		invalidate();
	}
}
