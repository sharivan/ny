// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;

import y.utils.TimerEngine;

// Referenced classes of package y.po:
// _cls136, PoolTableCreator, _cls5

public class YahooNumericTextBox extends YahooTextBox {

	public YahooNumericTextBox(TimerEngine timerHandler, String s, int i) {
		super(timerHandler, s, i);
	}

	@Override
	public boolean processEvent(Event event) {
		String s = getText();
		boolean flag = super.processEvent(event);
		if (event.id == 401) {
			String s1 = getText();
			if (s1.length() > 2)
				setText(s);
		}
		return flag;
	}
}
