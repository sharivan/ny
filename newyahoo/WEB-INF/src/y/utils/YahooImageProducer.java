// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.util.Hashtable;
import java.util.Vector;

public class YahooImageProducer implements ImageProducer {

	int						a;
	int						b;
	ColorModel				c;
	Object					d;
	int						e;
	int						f;
	Hashtable<?, ?>			g;
	Vector<ImageConsumer>	h;
	boolean					i;
	boolean					j;

	public YahooImageProducer(int k, int l, int ai[], int i1, int j1) {
		h = new Vector<ImageConsumer>();
		Yl(k, l, ColorModel.getRGBdefault(), ai, i1, j1, null);
	}

	public synchronized void addConsumer(ImageConsumer imageconsumer) {
		if (h.contains(imageconsumer))
			return;
		h.add(imageconsumer);
		try {
			Xl(imageconsumer);
			dm(imageconsumer, 0, 0, a, b);
			if (isConsumer(imageconsumer)) {
				imageconsumer.imageComplete(i ? 2 : 3);
				if (!i && isConsumer(imageconsumer)) {
					imageconsumer.imageComplete(1);
					removeConsumer(imageconsumer);
				}
			}
		}
		catch (Throwable _ex) {
			if (isConsumer(imageconsumer))
				imageconsumer.imageComplete(1);
		}
	}

	private void dm(ImageConsumer imageconsumer, int k, int l, int i1, int j1) {
		int k1 = e + f * l + k;
		if (isConsumer(imageconsumer))
			if (d instanceof byte[])
				imageconsumer.setPixels(k, l, i1, j1, c, (byte[]) d, k1, f);
			else
				imageconsumer.setPixels(k, l, i1, j1, c, (int[]) d, k1, f);
	}

	public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
		return h.contains(imageconsumer);
	}

	public synchronized void removeConsumer(ImageConsumer imageconsumer) {
		h.removeElement(imageconsumer);
	}

	public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
	}

	public void startProduction(ImageConsumer imageconsumer) {
		addConsumer(imageconsumer);
	}

	private void Xl(ImageConsumer imageconsumer) {
		if (isConsumer(imageconsumer))
			imageconsumer.setDimensions(a, b);
		if (isConsumer(imageconsumer))
			imageconsumer.setProperties(g);
		if (isConsumer(imageconsumer))
			imageconsumer.setColorModel(c);
		if (isConsumer(imageconsumer))
			imageconsumer.setHints(i ? j ? 6 : 1 : 30);
	}

	private void Yl(int k, int l, ColorModel colormodel, Object obj, int i1,
			int j1, Hashtable<?, ?> hashtable) {
		a = k;
		b = l;
		c = colormodel;
		d = obj;
		e = i1;
		f = j1;
		if (hashtable == null)
			hashtable = new Hashtable<Object, Object>();
		g = hashtable;
	}
}
