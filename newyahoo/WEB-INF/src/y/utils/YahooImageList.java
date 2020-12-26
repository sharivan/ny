// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.Image;

// Referenced classes of package y.po:
// _cls55

public class YahooImageList {

	public Image	image[];

	public YahooImageList(String as[]) {
		image = new Image[as.length];
		for (int i = 0; i < as.length; i++)
			image[i] = ImageReader.getImage(as[i]);

	}

	public void flush() {
		for (Image element : image)
			element.flush();

	}
}
