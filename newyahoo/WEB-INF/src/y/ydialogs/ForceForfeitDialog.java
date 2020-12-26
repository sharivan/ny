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
// _cls27, _cls174, _cls99, _cls170,
// _cls163, _cls168, _cls35, _cls133,
// _cls79, _cls87

public class ForceForfeitDialog extends YahooDialog {

	public YahooButton		btnForceForfeit;
	public YahooButton		btnSave;
	public YahooButton		btnCancel;
	public YahooButton		btnWait;
	public YahooGamesTable	table;
	public YahooLabel		lblPlayerAbandoned;
	public YahooLabel		lblWaitFor;
	public YahooLabel		lblForceForfeit;
	public int				currTurn;

	public ForceForfeitDialog(YahooGamesTable table, String text, int currTurn) {
		super(table.getTableControlContainer());
		btnForceForfeit = new YahooButton(table.getApplet().lookupString(
				0x66500136));
		btnCancel = new YahooButton(table.getApplet().lookupString(0x6650012f));
		btnWait = new YahooButton(table.getApplet().lookupString(0x66500131));
		this.table = table;
		this.currTurn = currTurn;
		lblPlayerAbandoned = new YahooLabel(table.getApplet().lookupString(
				0x66500c5b)
				+ text + table.getApplet().lookupString(0x66500133));
		addChildObject(lblPlayerAbandoned, 2, 1, 0, 0);
		YahooLabel _lcls87 = new YahooLabel(table.getApplet().lookupString(
				0x66500130), 2);
		addChildObject(_lcls87, 12, 2, 1, 1, 1, 0, 1);
		lblForceForfeit = new YahooLabel(table.getApplet().lookupString(
				0x6650012c)
				+ text + table.getApplet().lookupString(0x66500129));
		addChildObject(lblForceForfeit, 18, 2, 2, 1, 1, 1, 1);
		_lcls87 = new YahooLabel(table.getApplet().lookupString(0x6650012e), 2);
		addChildObject(_lcls87, 12, 2, 1, 1, 1, 0, 2);
		_lcls87 = new YahooLabel(table.getApplet().lookupString(0x66500134));
		addChildObject(_lcls87, 18, 1, 2, 1, 1, 1, 2);
		if (table.canSave()) {
			_lcls87 = new YahooLabel(
					table.getApplet().lookupString(0x66500135), 2);
			addChildObject(_lcls87, 12, 2, 1, 1, 1, 0, 3);
			_lcls87 = new YahooLabel(table.getApplet().lookupString(0x66500132));
			addChildObject(_lcls87, 18, 1, 2, 1, 1, 1, 3);
		}
		_lcls87 = new YahooLabel(table.getApplet().lookupString(0x66500137), 2);
		addChildObject(_lcls87, 12, 2, 1, 1, 1, 0, 4);
		lblWaitFor = new YahooLabel(table.getApplet().lookupString(0x6650012b)
				+ text + table.getApplet().lookupString(0x6650012a));
		new YahooLabel();
		addChildObject(lblWaitFor, 18, 1, 2, 1, 1, 1, 4);
		YahooControl _lcls79 = new YahooControl(1);
		addChildObject(_lcls79, 3, 1, 0, 5);
		_lcls79.addChildObject(btnForceForfeit, 0, 0, 2);
		_lcls79.addChildObject(btnCancel, 1, 0, 2);
		int k = 1;
		if (table.canSave()) {
			btnSave = new YahooButton(table.getApplet()
					.lookupString(0x6650012d));
			_lcls79.addChildObject(btnSave, ++k, 0, 2);
		}
		_lcls79.addChildObject(btnWait, ++k, 0, 2);
		show();
	}

	@Override
	public void close() {
		table = null;

		super.close();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == btnForceForfeit)
			table.resign(currTurn);
		else if (event.target == btnCancel)
			table.send('X', (byte) 1, (byte) 0);
		else if (event.target == btnSave)
			table.send('X', (byte) 1, (byte) 1);
		else if (event.target != btnWait)
			return false;
		close();
		return true;
	}

}
