// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;

// Referenced classes of package y.po:
// _cls78, _cls80, _cls116

public class YahooRadioGroup extends YahooComponent {

	public YahooCheckBox	yrg_a;

	public YahooRadioGroup(YahooCheckBox _pcls80) {
		super(15, 15);
		yrg_a = _pcls80;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		if (yrg_a.nextCheckBox != yrg_a) {
			Color color = Color.white;
			if (yrg_a.ycb_f || !yrg_a.isEnabled())
				color = Color.lightGray;
			yahooGraphics.setColor(color);
			yahooGraphics.fillRect(3, 3, 8, 8);
			yahooGraphics.drawImage(yrg_a.h, 1, 1, null);
		}
		else {
			yahooGraphics.setColor(yrg_a.ycb_f ? getBackColor() : kn());
			yahooGraphics.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
			yahooGraphics.du(0, 0, getWidth(), getHeight(), kn(), jn());
		}
		if (yrg_a.checked) {
			yahooGraphics.setColor(Color.black);
			if (yrg_a.nextCheckBox == yrg_a) {
				for (int i = 0; i < 3; i++) {
					yahooGraphics.drawLine(4, 6 + i, 6, 8 + i);
					yahooGraphics.drawLine(6, 8 + i, 10, 4 + i);
				}

			}
			else {
				yahooGraphics.fillRect(5, 5, 4, 4);
			}
		}
	}
}
