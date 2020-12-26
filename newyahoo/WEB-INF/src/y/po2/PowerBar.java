// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Color;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;

// Referenced classes of package y.po:
// _cls78, _cls174, _cls89, _cls168,
// _cls116, _cls29

public class PowerBar extends YahooComponent {

	PoolAreaHandler	pb_a;
	int				pb_b;
	int				pb_c;
	int				pb_d;
	int				e;
	int				pb_f;
	int				pb_g;
	int				h;
	Color			i;
	Color			j;
	Color			k;
	Color			l;
	Color			m;

	public PowerBar(PoolAreaHandler _pcls29) {
		super(50, 220);
		pb_b = 20;
		pb_c = 160;
		pb_d = 20;
		e = 7;
		pb_f = 30;
		pb_g = 0;
		i = new Color(0, 51, 0);
		j = new Color(119, 162, 79);
		k = new Color(205, 234, 75);
		l = new Color(198, 151, 44);
		m = new Color(191, 69, 50);
		pb_a = _pcls29;
		h = 120;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		yahooGraphics.setColor(i);
		yahooGraphics.drawRect(e, pb_f, pb_d, pb_c);
		for (int i1 = 0; i1 < 9; i1++)
			yahooGraphics.drawLine(24 + e, i1 * pb_b + pb_f, 34 + e, i1 * pb_b
					+ pb_f);

		if (pb_g > 0) {
			yahooGraphics.setColor(j);
			yahooGraphics.fillRect(e + 1, pb_f + pb_c - pb_g, pb_d - 1, pb_g);
			if (pb_g > pb_b) {
				yahooGraphics.setColor(k);
				yahooGraphics.fillRect(e + 1, pb_f + pb_c - pb_g, pb_d - 1,
						pb_g - pb_b);
			}
			if (pb_g > pb_b * 3) {
				yahooGraphics.setColor(l);
				yahooGraphics.fillRect(e + 1, pb_f + pb_c - pb_g, pb_d - 1,
						pb_g - pb_b * 3);
			}
			if (pb_g > pb_b * 6) {
				yahooGraphics.setColor(m);
				yahooGraphics.fillRect(e + 1, pb_f + pb_c - pb_g, pb_d - 1,
						pb_g - pb_b * 6);
			}
		}
		yahooGraphics.setColor(Color.black);
		yahooGraphics.drawString(pb_a.lookupString(0x6650136c), 5, 210);
	}

	public void reset() {
		pb_g = 0;
		invalidate();
	}

	public void setPower(int i1) {
		pb_g = i1 * pb_c / h;
		invalidate();
	}
}
