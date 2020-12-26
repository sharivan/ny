// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;

// Referenced classes of package y.po:
// _cls78, _cls116, _cls107, _cls44

public class YahooRatingSquare extends YahooComponent {

	public YahooRatingSquare(Color color) {
		super(12, 12);
		setForeColor(color);
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		yahooGraphics.setColor(getForeColor());
		yahooGraphics.fillRect(2, 2, 8, 8);
	}
}
