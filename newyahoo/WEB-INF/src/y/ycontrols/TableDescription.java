// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooTextBox;
import y.utils.StringLookuper;
import y.utils.TimerEngine;

// Referenced classes of package y.po:
// _cls79, _cls97, _cls105, _cls14,
// _cls87, _cls136

public class TableDescription extends YahooControl {

	public YahooTextBox	td_a;

	public TableDescription(TimerEngine timerHandler, StringLookuper _pcls14) {
		td_a = new YahooTextBox(timerHandler, 180);
		YahooLabel _lcls87 = new YahooLabel(_pcls14.lookupString(0x665016b8));
		addChildObject(_lcls87, 1, 1, 0, 0);
		addChildObject(td_a, 11, 0, 0, 1, 1, 1, 0);
	}

	public void Qa(TableCreator _pcls97) {
		String s = td_a.getText().trim();
		if (s.length() > 0)
			_pcls97.addProperty("dc", s);
	}
}
