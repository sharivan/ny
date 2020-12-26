// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooControl;
import y.controls.YahooFrame;
import y.controls.YahooLabel;
import y.controls.YahooTextBox;
import y.utils.Id;
import y.yutils.AbstractYahooGamesApplet;
import y.yutils.CustomYahooGamesApplet;
import y.yutils.Table;

// Referenced classes of package y.po:
// _cls82, _cls56, _cls74, _cls49,
// _cls118, _cls168, _cls35, _cls80,
// _cls79, _cls87, _cls136

public class InvitedFrame extends YahooFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -504370345374614594L;
	YahooButton					a;
	YahooCheckBox				b;
	YahooTextBox				c;
	CustomYahooGamesApplet		d;
	int							e;
	String						f;

	public InvitedFrame(Table _pcls118, Id _pcls49,
			CustomYahooGamesApplet _pcls74) {
		super(_pcls74.lookupString(0x6650007c), true, _pcls74.processor);
		c = new YahooTextBox(_pcls74.getTimerHandler(), 240);
		a = new YahooButton(_pcls74.lookupString(0x66500079));
		b = new YahooCheckBox(_pcls74.lookupString(0x6650007b));
		e = _pcls118.number;
		f = _pcls49.name;
		d = _pcls74;
		super.container.addChildObject(new YahooLabel(_pcls49.caption
				+ (_pcls49.str.equals("") ? "" : "(" + _pcls49.str + ")")
				+ _pcls74.lookupString(0x66500080)
				+ e
				+ "."
				+ (_pcls118.strProperties != null ? _pcls74
						.lookupString(0x6650007d)
						+ _pcls118.strProperties : "")
				+ _pcls74.lookupString(0x66500081)), 2, 1, 0, 0);
		super.container.addChildObject(new YahooLabel(_pcls74
				.lookupString(0x6650007e), 2), 1, 1, 0, 2);
		super.container.addChildObject(new YahooLabel(_pcls74
				.lookupString(0x6650007f)), 17, 0, 0, 1, 1, 1, 3);
		super.container.addChildObject(c, 10, 2, 0, 1, 1, 1, 2, 0, 0, 0, 8);
		super.container.addChildObject(b, 17, 0, 0, 1, 1, 1, 4, 6, 0, 6, 0);
		YahooControl _lcls79 = new YahooControl(1);
		super.container.addChildObject(_lcls79, 2, 1, 0, 5);
		_lcls79.addChildObject(a, 0, 0, 2);
		YahooButton _lcls35 = new YahooButton(_pcls74.lookupString(0x66500082));
		setDefaultAction(_lcls35);
		_lcls79.addChildObject(_lcls35, 1, 0, 2);
		pack();
		setVisible(true);
	}

	@Override
	public boolean action(Event event, Object obj) {
		if (event.target == a) {
			d.joinTable(e);
			super.close();
			return true;
		}
		if (event.target == b) {
			d.declineAllInvites(b.isChecked());
			((AbstractYahooGamesApplet) d).chkDeclineAllInvites.setChecked(b
					.isChecked());
			return true;
		}
		return false;
	}

	@Override
	protected void close() {
		String s = c.getText().trim();
		d.declineInvite(f, s.equals("") ? "None" : s);
		super.close();
	}
}
