// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.dialogs;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.utils.StringLookuper;

// Referenced classes of package y.po:
// _cls27, _cls14, _cls35, _cls133,
// _cls78, _cls79, _cls87

public class OkDialog extends YahooDialog {

	YahooButton	btnOk;

	public OkDialog(YahooControl _pcls79, String s1, StringLookuper _pcls14) {
		super(_pcls79, _pcls14.lookupString(0x66500164));
		addChildObject(new YahooLabel(s1), 1, 1, 0, 0);
		addChildObject(
				btnOk = new YahooButton(_pcls14.lookupString(0x66500165)), 1,
				1, 0, 1);
		show();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == btnOk) {
			close();
			return true;
		}
		return super.eventActionEvent(event, obj);
	}
}
