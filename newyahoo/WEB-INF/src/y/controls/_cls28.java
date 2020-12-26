// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Font;
import java.awt.FontMetrics;

// Referenced classes of package y.po:
// _cls78, _cls38, _cls116

public class _cls28 extends YahooComponent {

	Font		c28;
	FontMetrics	d28;
	YahooPannel	e28;
	int			f28;
	int			g28;

	public _cls28(YahooPannel _pcls38) {
		super(true);
		c28 = new Font(YahooComponent.defaultFont.getName(), 0, 8);
		e28 = _pcls38;
	}

	@Override
	public int getHeight1() {
		g28 = d28.getHeight();
		return g28;
	}

	@Override
	public int getWidth1() {
		f28 = Math.max(d28.stringWidth(e28.yp_c), d28
				.stringWidth("Audio Alert")) + 8;
		return f28;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		yahooGraphics.setFont(c28);
		yahooGraphics.setColor(e28.yp_a);
		yahooGraphics.fillRoundRect(0, 0, f28, g28 * 2, g28, g28);
		yahooGraphics.setColor(e28.yp_b);
		yahooGraphics.drawString(e28.yp_c, 4, g28 - d28.getDescent());
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		d28 = getFontMetrics(c28);
		f28 = Math.max(d28.stringWidth(e28.yp_c), d28
				.stringWidth("Audio Alert")) + 8;
		g28 = d28.getHeight();
	}
}
