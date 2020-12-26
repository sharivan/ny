// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Color;
import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.dialogs.YahooDialog;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls27, _cls56, _cls22, _cls168,
// _cls35, _cls133, _cls78, _cls79,
// _cls87, _cls119

public class OptionsDialog extends YahooDialog {

	YahooButton					btnDone;
	AbstractYahooGamesApplet	applet;

	public OptionsDialog(YahooControl _pcls79, AbstractYahooGamesApplet _pcls56) {
		super(_pcls79, _pcls56.lookupString(0x66500d35));// Options
		applet = _pcls56;
		btnDone = new YahooButton(_pcls56.lookupString(0x66500d36));// Done
		addChildObject(new YahooLabel("Yahoo! Messenger Preferences:"), 17, 0,
				0, 2, 1, 0, 0);
		addChildObject(_pcls56.chkShowLinkToYahooMessenger, 17, 0, 0, 1, 1, 0,
				1, 0, 10, 0, 0);
		addChildObject(_pcls56.btnHelp, 17, 0, 0, 1, 1, 1, 1);
		YahooComponent _lcls78 = new YahooComponent(1, 1);
		_lcls78.setBackColor(new Color(0x666666));
		addChildObject(_lcls78, 2, 1, 0, 2, false, 3, 10, 3, 10);
		int i = 2;
		if (_pcls56.getParameter("yourastar") != null
				&& "yahoo".equals(_pcls56.getParameter("league_id")))
			addChildObject(_pcls56.chkShowAllStarMemberShip, 17, 0, 0, 1, 1, 0,
					++i);
		addChildObject(_pcls56.chkDeclineAllInvites, 17, 0, 0, 1, 1, 0, ++i);
		YahooControl _lcls79 = new YahooControl();
		_lcls79.addChildObject(
				new YahooLabel(_pcls56.lookupString(0x66500de8)), 3, 1, 0, 0,
				true);// Profanity Filter:
		_lcls79.addChildObject(_pcls56.chkProfanityNone, 1, 1, 0, 1);
		_lcls79.addChildObject(_pcls56.chkProfanityWeak, 1, 1, 1, 1);
		_lcls79.addChildObject(_pcls56.chkProfanityStrong, 1, 1, 2, 1);
		addChildObject(_lcls79, 17, 0, 0, 1, 1, 0, ++i);
		addChildObject(_pcls56.w, 18, 0, 0, 1, 1, 0, ++i);
		addChildObject(_pcls56.C, 17, 1, 1, 2, 1, 0, ++i);
		YahooControl _lcls79_1 = new YahooControl();
		addChildObject(_lcls79_1, 1, i - 3, 1, 3);
		_lcls79_1.addChildObject(new YahooLabel(_pcls56
				.lookupString(0x66500dde)), 17, 0, 0, 1, 1, 0, 0);// Avatars:
		_lcls79_1.addChildObject(_pcls56.avatarList, 17, 2, 0, 1, 1, 0, 1);
		addChildObject(btnDone, 2, 1, 0, ++i);
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == btnDone) {
			Rw();
			close();
			applet.changeAvatar(applet.avatarList.Ku());
			return true;
		}
		return false;
	}
}
