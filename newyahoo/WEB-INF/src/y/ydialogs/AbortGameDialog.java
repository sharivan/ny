// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.dialogs.YahooDialog;
import y.yutils.YahooGamesTable;

// Referenced classes of package y.po:
// _cls27, _cls174, _cls99, _cls10,
// _cls170, _cls168, _cls35, _cls133,
// _cls79, _cls87

public class AbortGameDialog extends YahooDialog {

	YahooButton		btnQuitAndForfeit;
	YahooButton		btnQuitAndSave;
	YahooButton		btnQuitAndCancel;
	YahooButton		btnNeverMind;
	YahooGamesTable	table;

	public AbortGameDialog(YahooGamesTable _pcls99) {
		super(_pcls99.getTableControlContainer(), _pcls99.getApplet()
				.lookupString(0x6650004a));
		btnNeverMind = new YahooButton(_pcls99.getApplet().lookupString(
				0x66500049));
		btnQuitAndForfeit = new YahooButton(_pcls99.getApplet().lookupString(
				0x6650004d));
		btnQuitAndCancel = new YahooButton(_pcls99.getApplet().lookupString(
				0x6650004c));
		table = _pcls99;
		addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
				0x66500052)), 2, 1, 0, 0);
		addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
				0x6650004b), 2), 12, 2, 1, 1, 1, 0, 1);
		addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
				0x6650004e)), 18, 2, 2, 1, 1, 1, 1);
		addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
				0x66500050), 2), 12, 2, 1, 1, 1, 0, 2);
		addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
				0x66500048)), 18, 1, 2, 1, 1, 1, 2);
		if (_pcls99.canSave()) {
			addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
					0x6650004f), 2), 12, 2, 1, 1, 1, 0, 3);
			addChildObject(new YahooLabel(_pcls99.getApplet().lookupString(
					0x66500051)), 18, 1, 2, 1, 1, 1, 3);
		}
		YahooControl _lcls79 = new YahooControl(1);
		addChildObject(_lcls79, 3, 1, 0, 4);
		_lcls79.addChildObject(btnQuitAndForfeit, 0, 0, 2);
		_lcls79.addChildObject(btnQuitAndCancel, 1, 0, 2);
		int i = 1;
		if (_pcls99.canSave()) {
			btnQuitAndSave = new YahooButton(_pcls99.getApplet().lookupString(
					0x66500053));
			_lcls79.addChildObject(btnQuitAndSave, ++i, 0, 2);
		}
		_lcls79.addChildObject(btnNeverMind, ++i, 0, 2);
		show();
	}

	@Override
	public void close() {
		table = null;

		super.close();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == btnQuitAndForfeit) {
			table.resign();
			table.hide();
			close();
		}
		else if (event.target == btnQuitAndCancel) {
			table.send('!', (byte) 0);
			close();
		}
		else if (event.target == btnQuitAndSave) {
			table.send('!', (byte) 1);
			close();
		}
		else if (event.target == btnNeverMind)
			close();
		else
			return false;
		return true;
	}

}
