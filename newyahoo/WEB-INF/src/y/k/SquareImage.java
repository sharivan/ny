// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Image;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;

// Referenced classes of package y.k:
// _cls62, _cls100

public class SquareImage extends YahooComponent {

	Image	si_a;
	int		si_b;
	int		si_c;

	public SquareImage(Image image) {
		this(image, 0, 0);
	}

	public SquareImage(Image image, int i, int j) {
		super(1, 1);
		si_a = image;
		si_b = i;
		si_c = j;
	}

	@Override
	public void paint(YahooGraphics _pcls100, int i, int j, int k, int l) {
		YahooGraphics _lcls100 = _pcls100.create(i, j, k, l);
		int i1 = si_a.getWidth(null);
		int j1 = si_a.getHeight(null);
		for (int k1 = 0; k1 * j1 + (si_c - j) % j1 < l; k1++) {
			for (int l1 = 0; l1 * i1 + (si_b - i) % i1 < k; l1++)
				_lcls100.drawImage(si_a, l1 * i1 + (si_b - i) % i1, k1 * j1
						+ (si_c - j) % j1, null);

		}

	}
}
