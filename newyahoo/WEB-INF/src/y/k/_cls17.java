// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;
import java.awt.Event;

import y.controls.YahooControl;
import y.controls.YahooListBox;
import y.controls._cls1;

// Referenced classes of package y.k:
// _cls65, _cls45, _cls62, _cls92,
// _cls1

public class _cls17 extends YahooControl {

	_cls1			a17;
	YahooListBox	b17;
	YahooBoardTable	table;
	int				d17;
	int				e;

	void Ha(int i, int j) {
		if (d17 != -1) {
			b17.um(null, d17, e);
			d17 = -1;
		}
		int k = i / 2;
		int l = i % 2 + 1;
		if (i < j) {
			b17.um(Color.blue, k, l);
			b17.Km(k);
			d17 = k;
			e = l;
		}
		else {
			b17.pt();
		}
		if (j == 0) {
			a17.r(0, 1, 0, 0);
			b17.clear();
		}
		else {
			a17.r(i, (j + 1) / j, 0, j + (j + 1) / j);
		}
	}

	@Override
	public boolean processEvent(Event event) {
		if (event.target == a17 && event.id == 605)
			table.Uc(a17.g());
		else if (event.target == b17 && event.id == 701) {
			int i = ((Integer) event.arg).intValue();
			int j = b17.km();
			if (j >= 1)
				table.Uc(i * 2 + j - 1);
		}
		else {
			return super.processEvent(event);
		}
		return true;
	}
}
