// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

import y.utils.YahooImageProducer;

// Referenced classes of package y.po:
// _cls141, _cls69

public class BlackProducer implements BallProducer {

	Image				a;
	int					b[];
	int					c[];
	float				d[];
	YahooImageProducer	e;

	public BlackProducer() {
		b = new int[400];
		c = new int[400];
		d = new float[3];
		e = new YahooImageProducer(20, 20, c, 0, 20);
	}

	public Image Wm() {
		PixelGrabber pixelgrabber = new PixelGrabber(a, 0, 0, 20, 20, b, 0, 20);
		try {
			pixelgrabber.grabPixels();
		}
		catch (InterruptedException _ex) {
			System.err.println("interrupted waiting for pixels!");
			_ex.printStackTrace();
		}
		int ai[] = new int[400];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				int k = b[i * 20 + j];
				int l = 0xff000000 & k;
				int i1 = (0xff0000 & k) >> 16;
				int j1 = (0xff00 & k) >> 8;
				int k1 = 0xff & k;
				if (i1 > 70)
					i1 = j1;
				int l1 = l | i1 << 16 | j1 << 8 | k1;
				ai[i * 20 + j] = l1;
			}

		}
		// YahooMemoryImageSource.saveToFile(20, 20, ai,
		// "c:/y/po/blackball.img");
		e = new YahooImageProducer(20, 20, ai, 0, 20);
		return Toolkit.getDefaultToolkit().createImage(e);
	}

	public void Xm(Image image) {
		a = image;
	}
}
