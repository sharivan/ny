// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.utils.YahooImage;
import y.yutils.YahooTable;

// Referenced classes of package y.po:
// _cls79, _cls56, _cls174, _cls84,
// _cls168, _cls35, _cls78, _cls13

public class SitContainer extends YahooControl {

	public YahooButton	btnSit;
	public _cls84		sc_b;
	public YahooControl	sc_c;
	public YahooImage	image;
	YahooTable			table;
	int					index;

	public SitContainer(Color color, Color color1, int j, int k,
			YahooTable _pcls174, int l, boolean flag) {
		sc_c = new YahooControl(2);
		table = _pcls174;
		index = l;
		btnSit = new YahooButton(_pcls174.getApplet().lookupString(0x66500017));
		sc_b = new _cls84(color1, k, j, _pcls174, l);
		sc_b.setForeColor(color);
		sc_b.setBackColor(_pcls174.getBackColor());
		image = new YahooImage(null, _pcls174.Kz(l), _pcls174.Jz(l));
		if (_pcls174.getApplet().nofaceicons)
			if (flag) {
				addChildObject(sc_c, 1, 1, 0, 0);
				sc_c.addChildObject(btnSit, 0);
				sc_c.addChildObject(image, 1);
			}
			else {
				addChildObject(image, 1, 1, 0, 0);
			}
		addChildObject(sc_b, 1, 1, 1, 0);
	}

	public void close() {
		table = null;
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		if (event.target == image) {
			table.changeAvatar(index, event.modifiers == 4);
			return true;
		}
		return super.eventMouseDown(event, j, k);
	}

	@Override
	public boolean processEvent(Event event) {
		if (event.id == Event.ACTION_EVENT && event.target == sc_b) {
			table.handleInformation(index);
			return true;
		}
		return super.processEvent(event);
	}

	public void Pw(String s) {
		sc_b.Io(1, s);
	}

	public void setText(String s) {
		sc_b.Io(0, s);
	}

}
