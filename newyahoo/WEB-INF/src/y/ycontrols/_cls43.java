// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;

// Referenced classes of package y.po:
// _cls78, _cls116

public class _cls43 extends YahooComponent {

	static final Color	a43;
	static final Color	b43	= new Color(0xb0b4b9);
	static final Color	c43	= new Color(0x56616e);
	static {
		a43 = Color.white;
	}

	public static void cf(YahooGraphics _pcls116, Color color, int i, int j,
			int k, int l) {
		if (color != null) {
			_pcls116.setColor(color);
			_pcls116.fillRect(i, j, k, l);
		}
		_pcls116.setColor(c43);
		_pcls116.drawRect(i, j, k, l);
		_pcls116.setColor(a43);
		_pcls116.drawLine(i + 1, j + 1, i + k - 1, j + 1);
		_pcls116.drawLine(i + 1, j + 1, i + 1, j + l - 1);
		_pcls116.setColor(b43);
		_pcls116.drawLine(i + 2, j + l - 1, i + k - 1, j + l - 1);
		_pcls116.drawLine(i + k - 1, j + 2, i + k - 1, j + l - 1);
	}

	Color	d43;

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		cf(yahooGraphics, d43, 0, 0, getWidth(), getHeight());
	}
}
