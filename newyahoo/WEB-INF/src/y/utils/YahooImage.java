// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.Image;
import java.awt.image.ImageObserver;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;

// Referenced classes of package y.po:
// _cls78, _cls116

public class YahooImage extends YahooComponent implements ImageObserver {

	private Image	image;
	private boolean	update;
	private boolean	stretch;

	public YahooImage(Image image) {
		this(image, image.getWidth(null), image.getHeight(null), false);
	}

	public YahooImage(Image image, double proportion) {
		this(image, (int) (image.getWidth(null) * proportion), (int) (image
				.getHeight(null) * proportion), false, true);
	}

	public YahooImage(Image image, double proportion, boolean update) {
		this(image, (int) (image.getWidth(null) * proportion), (int) (image
				.getHeight(null) * proportion), update, true);
	}

	public YahooImage(Image image, int width, int height) {
		this(image, width, height, false, false);
	}

	public YahooImage(Image image, int width, int height, boolean stretch) {
		this(image, width, height, false, stretch);
	}

	public YahooImage(Image image, int width, int height, boolean update,
			boolean stretch) {
		super(true, width, height);
		this.image = image;
		this.update = update;
		this.stretch = stretch;
	}

	public boolean imageUpdate(Image image, int infoflags, int x, int y,
			int width, int height) {
		if ((infoflags & 0x20) != 0 || update)
			invalidate();
		return (infoflags & 0xa0) == 0 || update;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		if (image != null)
			if (stretch)
				yahooGraphics.drawImage(image, 0, 0, super.width1,
						super.height1, this);
			else
				yahooGraphics.drawImage(image, 0, 0, this);
	}

	public void setImage(Image image) {
		this.image = image;
		invalidate();
	}
}
