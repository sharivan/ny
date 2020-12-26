// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.awt.Image;
import java.io.IOException;

// Referenced classes of package y.po:
// _cls174

public class AdEntry {

	public String		location;
	public String		content;
	public String		imageUrl;
	public Image		image;
	public YahooTable	table;
	public IOException	exception;
	public long			time;
	public int			width;
	public int			height;
	public boolean		processed;

	public AdEntry(YahooTable table, String location) {
		width = -1;
		height = -1;
		processed = false;
		this.table = table;
		this.location = location;
		time = System.currentTimeMillis();
	}

	public int getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public String Y() {
		return content;
	}

}
