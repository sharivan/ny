// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

import y.utils.YahooImageProducer;

// Referenced classes of package y.po:
// _cls141, _cls69

public class ColoredBallProducer implements BallProducer {

	float				a;
	Image				b;
	// Hashtable c;
	int					d[];
	int					e[];
	float				f[];
	YahooImageProducer	g;

	public ColoredBallProducer(float f1) {
		// c = new Hashtable();
		d = new int[400];
		e = new int[400];
		f = new float[3];
		a = f1;
		g = new YahooImageProducer(20, 20, e, 0, 20);
	}

	public Image Wm() {
		PixelGrabber pixelgrabber = new PixelGrabber(b, 0, 0, 20, 20, d, 0, 20);
		int ai[] = new int[400];
		try {
			pixelgrabber.grabPixels();
		}
		catch (InterruptedException _ex) {
			System.err.println("interrupted waiting for pixels!");
			_ex.printStackTrace();
		}
		for (int i = 0; i < 400; i++) {
			int j = d[i];
			int k = j;
			int l = 0xff000000 & j;
			int i1 = (0xff0000 & j) >> 16;
			int j1 = (0xff00 & j) >> 8;
			int k1 = 0xff & j;
			if (l != 0) {
				f = Color.RGBtoHSB(i1, j1, k1, f);
				f[0] += a;
				k = Color.HSBtoRGB(f[0], f[1], f[2]);
			}
			ai[i] = k;
		}
		// YahooMemoryImageSource.saveToFile(20, 20, ai, "c:/y/po/coloredball["
		// + a + "].img");
		g = new YahooImageProducer(20, 20, ai, 0, 20);
		return Toolkit.getDefaultToolkit().createImage(g);
	}

	public void Xm(Image image) {
		b = image;
	}
}
