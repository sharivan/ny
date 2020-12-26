// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.dialogs.YahooDialog;

// Referenced classes of package y.po:
// _cls27, _cls97, _cls17, _cls168,
// _cls35, _cls133, _cls79

public class TableLabelDialog extends YahooDialog {

	TableCreator		tld_a;
	TableDescription	tld_b;
	YahooButton			tld_c;
	YahooButton			tld_d;

	public TableLabelDialog(TableCreator _pcls97, YahooControl _pcls79) {
		super(_pcls79, _pcls97.getApplet().lookupString(0x66501762));
		tld_a = _pcls97;
		tld_b = new TableDescription(_pcls97.applet.getTimerHandler(), _pcls97
				.getApplet());
		addChildObject(tld_b, 1, 1, 0, 0);
		YahooControl _lcls79 = new YahooControl(1);
		addChildObject(_lcls79, 1, 1, 0, 1);
		tld_c = new YahooButton(_pcls97.getApplet().lookupString(0x66501763));
		_lcls79.addChildObject(tld_c, 0, 0, 2);
		tld_d = new YahooButton(_pcls97.getApplet().lookupString(0x66501761));
		_lcls79.addChildObject(tld_d, 1, 0, 2);
		show();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == tld_c) {
			tld_b.Qa(tld_a);
			tld_a.makeTable();
			close();
			return true;
		}
		if (event.target == tld_d) {
			tld_a.cancel();
			return true;
		}
		return false;
	}
}
