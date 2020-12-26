// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;

// Referenced classes of package y.po:
// _cls78, _cls77, _cls92, _cls116,
// _cls136

public class _cls113 extends YahooComponent {

	public YahooComboBox	b113;

	public _cls113(YahooComboBox _pcls77) {
		b113 = _pcls77;
	}

	@Override
	public int getHeight1() {
		return b113.e.getHeight1();
	}

	@Override
	public int getWidth1() {
		return 15;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.backColor = in();
		paintTo(yahooGraphics);
		for (int i = 0; i < 2; i++) {
			yahooGraphics.setColor(b113.j ^ i % 2 == 0 ? Color.white : b113
					.ln());
			yahooGraphics.ou(0, 0, 0, 0, getWidth() - 1, 0, getWidth(),
					getHeight(), i * 2);
			yahooGraphics.ou(0, 0, 0, 0, 0, getHeight() - 1, getWidth(),
					getHeight(), i * 2);
		}

		yahooGraphics.setColor(b113.i ? Color.gray : Color.black);
		for (int j = 0; j < 4; j++) {
			int k = getHeight1() / 2 + 2 - j;
			yahooGraphics.drawLine(7 - j, k, 7 + j, k);
		}

	}
}
