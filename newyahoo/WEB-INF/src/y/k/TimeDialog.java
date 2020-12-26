// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooTextBox;
import y.dialogs.OkDialog;
import y.dialogs.YahooDialog;
import y.utils.TimerEngine;

// Referenced classes of package y.k:
// _cls21, _cls151, _cls85, _cls73,
// _cls150, _cls37, _cls145, _cls27,
// _cls113, _cls67, _cls65, _cls75,
// _cls116

class TimeDialog extends YahooDialog {

	YahooTextBox	txtTotalTime;
	YahooTextBox	txtIncrementTime;
	YahooButton		btnOk;
	YahooButton		btnCancel;
	TimeParser		timeTarser;
	YahooCheckBox	chkNoTimer;
	YahooCheckBox	chk5MinPerMove;

	TimeDialog(TimerEngine timeHandler, YahooControl control,
			TimeParser timeTarser) {
		super(control, timeTarser.table.getApplet().lookupString(0x66500734));
		chkNoTimer = new YahooCheckBox(timeTarser.table.getApplet()
				.lookupString(0x66500736), null, true);
		this.timeTarser = timeTarser;
		addChildObject(chkNoTimer, 3, 1, 0, 0, 16, 0, 0, 0);
		addChildObject(new YahooLabel(timeTarser.table.getApplet()
				.lookupString(0x6650072f), 2), 10, 2, 0, 1, 1, 0, 1);
		txtTotalTime = new YahooTextBox(timeHandler, 32);
		txtTotalTime.setEnabled(false);
		addChildObject(txtTotalTime, 1, 1, 1, 1, 0, 0, 1, 0);
		addChildObject(new YahooLabel(timeTarser.table.getApplet()
				.lookupString(0x6650072e)), 2, 1, 2, 1);
		if (timeTarser.canSetIncrement) {
			addChildObject(new YahooLabel(timeTarser.table.getApplet()
					.lookupString(0x6650072d), 2), 10, 2, 0, 1, 1, 0, 2);
			txtIncrementTime = new YahooTextBox(timeHandler, 32);
			txtIncrementTime.setEnabled(false);
			addChildObject(txtIncrementTime, 1, 1, 1, 2);
			addChildObject(new YahooLabel(timeTarser.table.getApplet()
					.lookupString(0x66500731)), 1, 1, 2, 2);
		}
		chk5MinPerMove = new YahooCheckBox(timeTarser.table.getApplet()
				.lookupString(0x66500733)
				+ timeTarser.timeredGame.getTimePerMove()
				+ timeTarser.table.getApplet().lookupString(0x66500735), null,
				true);
		addChildObject(chk5MinPerMove, 3, 1, 0, 3);
		YahooControl _lcls65 = new YahooControl(1);
		addChildObject(_lcls65, 3, 1, 0, 4, 0, 0, 16, 0);
		btnOk = new YahooButton(timeTarser.table.getApplet().lookupString(
				0x66500732));
		_lcls65.addChildObject(btnOk, 0, 0, 2);
		btnCancel = new YahooButton(timeTarser.table.getApplet().lookupString(
				0x66500730));
		_lcls65.addChildObject(btnCancel, 1, 0, 2);
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == chkNoTimer) {
			boolean flag = ((Boolean) obj).booleanValue() ^ true;
			if (txtIncrementTime != null)
				txtIncrementTime.setEnabled(flag);
			txtTotalTime.setEnabled(flag);
			return true;
		}
		if (event.target == btnOk) {
			int i = -1;
			int j = 0;
			boolean flag = chk5MinPerMove.isChecked();
			if (chkNoTimer.isChecked()) {
				timeTarser.table.send('L', flag, -1, 0);
			}
			else {
				try {
					i = Integer.parseInt(txtTotalTime.getText().trim()) * 60 * 1000;
					if (txtIncrementTime != null)
						j = Integer.parseInt(txtIncrementTime.getText().trim()) * 1000;
					else
						j = 0;
				}
				catch (NumberFormatException _ex) {
				}
				if (i < 1 || j < 0)
					new OkDialog(timeTarser.table.getTableControlContainer(),
							timeTarser.table.getApplet().lookupString(
									0x6650072c), timeTarser.table.getApplet());
				else
					timeTarser.table.send('L', flag, i, j);
			}
			close();
			return true;
		}
		if (event.target == btnCancel) {
			close();
			return true;
		}
		return false;
	}
}
