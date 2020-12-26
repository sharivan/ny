// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Color;
import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.dialogs.YahooDialog;
import y.utils.YahooImage;

// Referenced classes of package y.po:
// _cls27, _cls174, _cls29, _cls89,
// _cls45, _cls168, _cls35, _cls133,
// _cls78, _cls79, _cls13, _cls87

public class SelectTypeDialog extends YahooDialog {

	YahooPoolTable	table;
	YahooLabel		std_b;
	YahooControl	e;
	YahooControl	std_f;
	YahooImage		std_g;
	YahooImage		h;
	YahooButton		i;
	YahooButton		j;
	YahooButton		k;

	public SelectTypeDialog(YahooPoolTable _pcls29, YahooControl _pcls79) {
		super(_pcls79, _pcls29.getApplet().lookupString(0x665011bf));
		table = _pcls29;
		java.awt.Image image = YahooPoolImageList.loadImages().s;
		java.awt.Image image1 = YahooPoolImageList.loadImages().t;
		h = new YahooImage(null, 20, 20);
		h.setImage(image);
		std_g = new YahooImage(null, 20, 20);
		std_g.setImage(image1);
		j = new YahooButton(table.getApplet().lookupString(0x665011c1));
		i = new YahooButton(table.getApplet().lookupString(0x665011c2));
		k = new YahooButton(table.getApplet().lookupString(0x665011c0));
		e = new YahooControl(1);
		std_f = new YahooControl(1);
		e.setBackColor(new Color(45, 109, 43));
		std_f.setBackColor(new Color(45, 109, 43));
		e.addChildObject(h, 10, 0, 0, 1, 1, 0, 0);
		std_f.addChildObject(std_g, 10, 0, 0, 1, 1, 1, 0);
		std_b = new YahooLabel(table.getApplet().lookupString(0x6650125c));
		addChildObject(std_b, 10, 2, 2, 2, 1, 0, 0);
		addChildObject(e, 10, 2, 2, 1, 1, 0, 1);
		addChildObject(std_f, 10, 2, 2, 1, 1, 1, 1);
		addChildObject(j, 10, 2, 2, 1, 1, 0, 2);
		addChildObject(i, 10, 2, 2, 1, 1, 1, 2);
		show();
	}

	@Override
	public void close() {
		table = null;

		super.close();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == j) {
			table.selectMyType(1024);
			close();
			return true;
		}
		if (event.target == i) {
			table.selectMyType(2048);
			close();
			return true;
		}
		return false;
	}

}
