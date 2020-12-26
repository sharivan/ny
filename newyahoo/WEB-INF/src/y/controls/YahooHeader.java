// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;

// Referenced classes of package y.po:
// _cls78, _cls116, _cls107, _cls158,
// _cls123

public class YahooHeader extends YahooComponent {

	static Color	defaultHeaderColor;
	static Color	defaultDarkerHeaderColor;
	static {
		defaultHeaderColor = new Color(0xffffcc);
		defaultDarkerHeaderColor = defaultHeaderColor.darker();
	}
	YahooListBox	list;

	FontMetrics		fontMetrics;

	public YahooHeader(YahooListBox list) {
		this.list = list;
		setBackColor(defaultHeaderColor);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		if (list.ylb_s != -1) {
			for (int l = 0; l < list.listColCount; l++) {
				if (j >= list.ylb_n[l + 1])
					continue;
				list.kt(l);
				break;
			}

		}
		invalidate();
		return true;
	}

	@Override
	public int getHeight1() {
		return fontMetrics.getHeight() + 8;
	}

	@Override
	public int getWidth1() {
		return list.i;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		yahooGraphics.setFont(YahooComponent.defaultFont);
		paintTo(yahooGraphics);
		for (int j = 0; j < list.listColCount; j++) {
			int k = fontMetrics.getHeight() - fontMetrics.getDescent() + 2;
			if (j == list.ylb_s) {
				int l = list.ylb_n[j + 1] - 9;
				int j1 = k - fontMetrics.getAscent() + 2;
				yahooGraphics.setColor(defaultDarkerHeaderColor);
				yahooGraphics.drawLine(l, j1, l + 8, j1);
				yahooGraphics.drawLine(l, j1 + 1, l + 6, j1 + 1);
				yahooGraphics.drawLine(l + 1, j1 + 2, l + 3, j1 + 7);
				yahooGraphics.drawLine(l + 2, j1 + 2, l + 3, j1 + 5);
				yahooGraphics.setColor(Color.white);
				yahooGraphics.drawLine(l + 5, j1 + 7, l + 7, j1 + 2);
				yahooGraphics.drawLine(l + 5, j1 + 5, l + 4, j1 + 2);
				yahooGraphics.drawLine(l + 7, j1 + 1, l + 8, j1 + 1);
			}
			int i1 = list.ylb_n[j];
			if (j > 0) {
				yahooGraphics.setColor(list.color);
				yahooGraphics.drawLine(i1, 0, i1, getHeight() - 1);
			}
			String s = YahooList.getText(new StringBuffer(list.cells[j]),
					list.fontMetrics, list.ylb_n[j + 1] - list.ylb_n[j]);
			yahooGraphics.setColor(getForeColor());
			yahooGraphics.drawString(s, i1 + 2, k);
		}

	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		fontMetrics = getFontMetrics(YahooComponent.defaultFont);
	}
}
