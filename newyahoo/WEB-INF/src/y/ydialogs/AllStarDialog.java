// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.dialogs.YahooDialog;
import y.yutils.AbstractYahooApplet;

// Referenced classes of package y.po:
// _cls27, _cls168, _cls35, _cls133,
// _cls79, _cls87

public class AllStarDialog extends YahooDialog {

	YahooLabel			asd_a;
	YahooButton			asd_b;
	YahooButton			asd_c;
	String				allstar_order_url;
	AbstractYahooApplet	applet;

	public AllStarDialog(AbstractYahooApplet _pcls168, YahooControl _pcls79,
			String s1) {
		super(_pcls79, "");
		applet = _pcls168;
		asd_b = new YahooButton(_pcls168.lookupString(0x66501676));
		asd_c = new YahooButton(_pcls168.lookupString(0x66501677));
		asd_a = new YahooLabel(s1);
		allstar_order_url = _pcls168.getParameter("allstar_order_url");
		addChildObject(asd_a, 10, 2, 2, 2, 1, 0, 0);
		YahooControl _lcls79 = new YahooControl(1);
		addChildObject(_lcls79, 10, 0, 0, 2, 1, 0, 1);
		_lcls79.addChildObject(asd_b, 0, 0, 2);
		_lcls79.addChildObject(asd_c, 1, 0, 2);
		show();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == asd_b) {
			close();
			return true;
		}
		if (event.target == asd_c) {
			applet.showDocument(allstar_order_url, "_blank");
			close();
			return true;
		}
		return false;
	}
}
