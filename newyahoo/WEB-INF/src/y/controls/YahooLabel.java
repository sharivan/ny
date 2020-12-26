// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;

import y.utils.StringEnum;

// Referenced classes of package y.po:
// _cls78, _cls140, _cls35, _cls116

public class YahooLabel extends YahooComponent {

	public static int		yl_a	= 0;
	public static int		yl_b	= 1;
	public static int		yl_c	= 2;
	public static int		yl_d	= 2;

	protected FontMetrics	fontMetrics;
	protected String		lines[];
	String					caption;
	int						h;
	int						i;
	protected Font			font;
	protected boolean		k;

	public YahooLabel() {
		this("");
	}

	public YahooLabel(String s) {
		this(s, yl_b);
	}

	public YahooLabel(String s, FontMetrics fontMetrics) {
		this(s, yl_b, fontMetrics);
	}

	public YahooLabel(String s, int l) {
		this(s, l, -1, null);
	}

	public YahooLabel(String s, int l, FontMetrics fontMetrics) {
		this(s, l, -1, fontMetrics);
	}

	public YahooLabel(String s, int l, int i1) {
		this(s, l, i1, null);
	}

	public YahooLabel(String s, int l, int i1, FontMetrics fontMetrics) {
		super(true);
		font = YahooComponent.defaultFont;
		k = true;
		i = l;
		h = i1;
		this.fontMetrics = fontMetrics;
		setCaption(s);
	}

	protected void drawString(YahooGraphics _pcls116, String s, int l, int i1) {
		_pcls116.drawString(s, l, i1);
	}

	public String getFirstLine() {
		return lines[0];
	}

	@Override
	public int getHeight1() {
		return fontMetrics.getHeight() * lines.length + 2 * yl_d;
	}

	@Override
	public int getWidth1() {
		if (h != -1)
			return h;
		int l = 0;
		for (String line : lines) {
			int j1 = fontMetrics.stringWidth(line);
			if (j1 > l)
				l = j1;
		}

		return l + yl_d * 2;
	}

	@SuppressWarnings("deprecation")
	public void Oo(int l, int i1, int j1) {
		font = new Font(Toolkit.getDefaultToolkit().getFontList()[l], i1, j1);
		invalidate();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		yahooGraphics.setFont(font);
		yahooGraphics.setColor(getForeColor());
		fontMetrics = getFontMetrics(font);
		int l = (getHeight() - getHeight1()) / 2 + fontMetrics.getAscent()
				+ yl_d;
		for (String line : lines) {
			int j1;
			if (i == yl_a)
				j1 = (getWidth() - fontMetrics.stringWidth(line)) / 2;
			else if (i == yl_b)
				j1 = yl_d;
			else
				j1 = getWidth() - yl_d - fontMetrics.stringWidth(line);
			drawString(yahooGraphics, line, j1, l);
			l += fontMetrics.getHeight();
		}

		if (!k) {
			yahooGraphics.setColor(getBackColor());
			YahooButton.setGraphic(yahooGraphics, yl_d, yl_d, getWidth() - 2
					* yl_d, getHeight() - 2 * yl_d);
		}
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		if (fontMetrics == null)
			fontMetrics = getFontMetrics(font);
	}

	public boolean setCaption(String s) {
		if (!s.equals(caption)) {
			caption = s;
			StringEnum stringEnum = new StringEnum(s, '\n');
			lines = new String[stringEnum.separatorCount()];
			for (int l = 0; l < stringEnum.separatorCount(); l++)
				lines[l] = stringEnum.next();

			invalidate();
			return true;
		}
		return false;
	}

}
