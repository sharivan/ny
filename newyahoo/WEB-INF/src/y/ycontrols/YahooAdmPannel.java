// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooComboBox;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooTextBox;
import y.ydialogs.InformationFrame;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls79, _cls56, _cls36, _cls168,
// _cls35, _cls77, _cls87

public class YahooAdmPannel extends YahooControl {

	static final int			yap_g[]	= { 0x6650162b, 0x66501630, 0x6650162c,
			0x6650162d, 0x6650162f, 0x6650162a, 0x6650162e, 0x66501629 };
	static final int			time[]	= { 60, 300, 600, 900, 1800, 6000,
			10800, 0x15180				};
	YahooButton					btnKick;
	YahooButton					btnMute;
	YahooButton					btnBan;
	YahooComboBox				cmbTime;
	AbstractYahooGamesApplet	applet;
	InformationFrame			informationFrame;
	// Hashtable i;
	YahooTextBox				txtReason;

	public YahooAdmPannel(AbstractYahooGamesApplet _pcls56,
			InformationFrame _pcls36) {
		cmbTime = new YahooComboBox(_pcls56.getTimerHandler());
		// i = new Hashtable();
		applet = _pcls56;
		informationFrame = _pcls36;
		btnKick = new YahooButton(_pcls56.lookupString(0x66501632));
		btnMute = new YahooButton(_pcls56.lookupString(0x66501633));
		btnBan = new YahooButton(_pcls56.lookupString(0x66501631));
		txtReason = new YahooTextBox(_pcls56.getTimerHandler(), "", 100);
		for (int element : yap_g)
			cmbTime.setText(_pcls56.lookupString(element));

		YahooLabel _lcls87 = new YahooLabel(_pcls56.lookupString(0x66501634));
		addChildObject(_lcls87, 11, 0, 1, 1, 1, 0, 0);
		addChildObject(btnKick, 17, 0, 0, 1, 1, 0, 1);
		addChildObject(cmbTime, 17, 15, 3, 1, 1, 0, 2, 32, 0, 0, 0);
		YahooControl _lcls79 = new YahooControl(1);
		_lcls79.addChildObject(btnMute, 0, 0, 0);
		_lcls79.addChildObject(btnBan, 1, 0, 0);
		addChildObject(_lcls79, 17, 0, 0, 1, 1, 0, 3);
		addChildObject(txtReason, 17, 0, 0, 1, 1, 0, 4);
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		String reason;
		if (event.target == btnBan) {
			reason = txtReason.getText().trim();
			applet.ban(informationFrame.idName, time[cmbTime.getItemIndex()],
					!reason.equals("") ? reason : "none");
		}
		else if (event.target == btnMute) {
			reason = txtReason.getText().trim();
			applet.mute(informationFrame.idName, time[cmbTime.getItemIndex()],
					!reason.equals("") ? reason : "none");
		}
		else if (event.target == btnKick) {
			reason = txtReason.getText().trim();
			applet.ban(informationFrame.idName, 10, !reason.equals("") ? reason
					: "kick!");
		}
		else
			return false;
		return true;
	}

}
