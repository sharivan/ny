// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Polygon;

import y.utils.Arrow;

// Referenced classes of package y.po:
// _cls78, _cls161, _cls116

public class ArrowControl extends YahooComponent {

	Polygon	arrow_a;
	int		arrow_left;
	int		arrow_top;
	boolean	arrow_visible;

	public ArrowControl(int i, int j, int k) {
		if (i % 2 == 0) {
			arrow_left = j;
			arrow_top = k;
		}
		else {
			arrow_left = k;
			arrow_top = j;
		}
		arrow_a = Arrow.Jy(0, 0, j, k, i);
		setForeColor(Color.cyan.darker());
	}

	@Override
	public int getHeight1() {
		return arrow_top;
	}

	@Override
	public int getWidth1() {
		return arrow_left;
	}

	@Override
	public synchronized void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		if (arrow_visible) {
			yahooGraphics.setColor(getForeColor());
			yahooGraphics.fillPolygon(arrow_a);
			yahooGraphics.setColor(Color.black);
			yahooGraphics.drawPolygon(arrow_a);
		}
	}

	public synchronized void setVisible(boolean flag) {
		arrow_visible = flag;
		invalidate();
	}
}
