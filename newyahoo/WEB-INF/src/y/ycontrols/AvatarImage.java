// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import y.controls.YahooGraphics;
import y.utils.YahooImage;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls13, _cls56, _cls78, _cls116

public class AvatarImage extends YahooImage {

	public Image					ai_a;
	public AbstractYahooGamesApplet	ai_b;

	public AvatarImage(Image image, AbstractYahooGamesApplet _pcls56) {
		super(image);
		ai_a = image;
		ai_b = _pcls56;
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		try {
			ai_b.getAppletContext().showDocument(
					new URL("http://" + ai_b.avatar_host + "/"), "_blank");
		}
		catch (MalformedURLException _ex) {
		}
		return super.eventMouseDown(event, j, k);
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		yahooGraphics.drawImage(ai_a, -40, 0, this);
		yahooGraphics.setColor(Color.black);
		yahooGraphics.drawRect(0, 0, 100, 180);
		yahooGraphics.setColor(getBackColor());
		yahooGraphics.fillRect(-40, 0, 0, 180);
		yahooGraphics.fillRect(101, 0, 141, 180);
	}
}
