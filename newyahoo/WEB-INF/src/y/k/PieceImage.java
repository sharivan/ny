// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Image;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;

// Referenced classes of package y.k:
// _cls62, _cls84, _cls100

class PieceImage extends YahooComponent {

	Image	pi_a;
	int		pi_b;
	int		pi_c;

	PieceImage(Image image, int i, int j) {
		super(i, j);
		pi_a = image;
		pi_b = i;
		pi_c = j;
	}

	@Override
	public void paint(YahooGraphics _pcls100) {
		if (pi_a != null)
			_pcls100.drawImage(pi_a, (pi_b - pi_a.getWidth(null)) / 2,
					(pi_c - pi_a.getHeight(null)) / 2, null);
	}
}
