// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Event;
import java.awt.Image;

import y.controls.YahooButton;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.dialogs.YahooDialog;
import y.ycontrols.YahooGameBrought;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls27, _cls144, _cls99, _cls2,
// _cls168, _cls35, _cls133, _cls78,
// _cls79, _cls56

public class GameOverDialog extends YahooDialog {

	YahooButton					god_a;
	AbstractYahooGamesApplet	god_b;

	public GameOverDialog(YahooControl control,
			AbstractYahooGamesApplet _pcls56, YahooComponent _pcls78,
			Image image, String s1) {
		super(control, _pcls56.lookupString(0x66500032));
		god_a = new YahooButton(_pcls56.lookupString(0x66500140));
		god_b = _pcls56;
		if (image != null) {
			YahooGameBrought _lcls144 = new YahooGameBrought(_pcls56, image, s1);
			addChildObject(_lcls144, 1, 1, 0, 1);
		}
		addChildObject(_pcls78, 1, 1, 0, 0);
		addChildObject(god_a, 1, 1, 0, 2);
		show();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == god_a) {
			close();
			return true;
		}
		return super.eventActionEvent(event, obj);
	}
}
