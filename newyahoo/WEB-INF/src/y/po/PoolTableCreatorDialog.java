// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooNumericTextBox;
import y.dialogs.YahooDialog;
import y.ycontrols.TableCreator;
import y.ycontrols.TableDescription;
import y.ydialogs.AllStarDialog;

// Referenced classes of package y.po:
// _cls27, _cls56, _cls97, _cls3,
// _cls17, _cls0, PoolTableCreator, _cls137,
// _cls105, _cls168, _cls35, _cls133,
// _cls80, _cls79, _cls87, _cls136

class PoolTableCreatorDialog extends YahooDialog {

	TableCreator		tableCreator;
	YahooCheckBox		chkRated;
	YahooCheckBox		chkTraining;
	YahooCheckBox		chkAutomat;
	YahooCheckBox		chkEightBallGame;
	YahooCheckBox		chkNineBallGame;
	YahooCheckBox		chkTimer;
	YahooCheckBox		chkForceForfeit;
	YahooControl		h;
	YahooControl		i;
	YahooControl		j;
	YahooNumericTextBox	txtTimer;
	YahooButton			l;
	YahooButton			m;
	YahooControl		ptc_n;
	TableDescription	ptc_o;

	PoolTableCreatorDialog(TableCreator _pcls97, YahooControl _pcls79) {
		super(_pcls79, _pcls97.getApplet().lookupString(0x66501402));
		txtTimer = new YahooNumericTextBox(_pcls97.applet.getTimerHandler(),
				"30", 20);
		ptc_n = _pcls79;
		l = new YahooButton(_pcls97.getApplet().lookupString(0x66501404));
		m = new YahooButton(_pcls97.getApplet().lookupString(0x66501400));
		tableCreator = _pcls97;
		String category = _pcls97.getApplet().getParameter("category");
		chkTraining = new YahooCheckBox(_pcls97.getApplet().lookupString(
				0x66501405), null, category != null
				&& !category.equals("social"));
		addChildObject(chkTraining, 17, 0, 0, 2, 1, 0, 0);
		chkTraining.setChecked(false);
		chkEightBallGame = new YahooCheckBox(_pcls97.getApplet().lookupString(
				0x66501401), chkTraining, category != null
				&& !category.equals("social"));
		addChildObject(chkEightBallGame, 17, 0, 0, 2, 1, 0, 1);
		chkEightBallGame.setChecked(true);
		chkNineBallGame = new YahooCheckBox(_pcls97.getApplet().lookupString(
				0x66501694), chkTraining, category != null
				&& !category.equals("social"));
		if ((((YahooPool) _pcls97.getApplet()).show_9ball_option || ((YahooPool) _pcls97
				.getApplet()).yp_d)
				&& _pcls97.getApplet().lookupString(0x6650179d).equals("y"))
			addChildObject(chkNineBallGame, 17, 0, 0, 2, 1, 0, 2);
		chkNineBallGame.setChecked(false);
		chkAutomat = new YahooCheckBox("Automat.", null, category != null
				&& !category.equals("social"));
		chkAutomat.setChecked(false);
		h = new YahooControl(2);
		i = new YahooControl(0);
		chkRated = new YahooCheckBox(_pcls97.getApplet().lookupString(
				0x66501403), null, category != null
				&& !category.equals("social"));
		i.addChildObject(chkRated, 17, 0, 0, 2, 1, 0, 0);
		chkRated.setChecked(true);
		chkTimer = new YahooCheckBox("");
		i.addChildObject(chkTimer, 17, 0, 0, 1, 1, 0, 1);
		chkForceForfeit = new YahooCheckBox("Forçar derrota");
		chkForceForfeit.setChecked(true);
		i.addChildObject(chkForceForfeit, 17, 0, 0, 2, 1, 0, 2);
		j = new YahooControl(2);
		YahooControl _lcls79 = new YahooControl(0);
		_lcls79.addChildObject(new YahooLabel(_pcls97.getApplet().lookupString(
				0x665013fc)), 17, 0, 0, 1, 1, 0, 0);
		_lcls79.addChildObject(txtTimer, 13, 0, 0, 1, 1, 1, 0);
		_lcls79.addChildObject(new YahooLabel(_pcls97.getApplet().lookupString(
				0x6650140a)), 13, 0, 0, 1, 1, 2, 0);
		txtTimer.setEnabled(true);
		j.addChildObject(new YahooLabel(_pcls97.getApplet().lookupString(
				0x665013fd)), 0);
		j.addChildObject(_lcls79, 1);
		j.qo(0);
		i.addChildObject(j, 17, 0, 0, 1, 1, 1, 1);
		h.addChildObject(new YahooControl(), 0);
		h.addChildObject(i, 1);
		addChildObject(h, 17, 0, 0, 2, 1, 0, 3);
		h.qo(1);
		if (_pcls97.getApplet().idPropertyContains(32L)) {
			ptc_o = new TableDescription(_pcls97.getApplet().getTimerHandler(),
					_pcls97.getApplet());
			addChildObject(ptc_o, 2, 1, 0, 6);
		}
		YahooControl _lcls79_1 = new YahooControl(1);
		addChildObject(_lcls79_1, 10, 0, 0, 2, 1, 0, 7);
		_lcls79_1.addChildObject(l, 0, 0, 2);
		_lcls79_1.addChildObject(m, 1, 0, 2);
		show();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == l) {
			boolean flag = false;
			if (chkRated.isChecked())
				tableCreator.addProperty("rd", "");
			if (!chkForceForfeit.isChecked())
				tableCreator.addProperty("ff", "");
			if (chkTraining.isChecked())
				tableCreator.addProperty("training", "");
			if (chkAutomat.isChecked())
				tableCreator.addProperty("automat", "");
			if (chkEightBallGame.isChecked()) {
				tableCreator.addProperty("eightBallGame", "");
				if (chkTimer.isChecked())
					tableCreator.addProperty("timer", txtTimer.getText());
			}
			if (chkNineBallGame.isChecked()) {
				tableCreator.addProperty("nineBallGame", "");
				flag = true;
				if (chkTimer.isChecked())
					tableCreator.addProperty("timer", txtTimer.getText());
			}
			if (flag && !tableCreator.getApplet().idPropertyContains(8L)
					&& !((YahooPool) tableCreator.getApplet()).yp_d) {
				close();
				new AllStarDialog(tableCreator.getApplet(), ptc_n, tableCreator
						.getApplet().lookupString(0x6650169c));
				return true;
			}
			if (ptc_o != null)
				ptc_o.Qa(tableCreator);
			tableCreator.makeTable();
			close();
			return true;
		}
		if (event.target != chkRated)
			if (event.target == chkTimer) {
				if (chkTimer.isChecked())
					j.qo(1);
				else
					j.qo(0);
			}
			else if (event.target instanceof YahooCheckBox) {
				if (chkEightBallGame.isChecked() || chkNineBallGame.isChecked()) {
					h.qo(1);
				}
				else {
					h.qo(0);
					chkRated.setChecked(false);
				}
			}
			else if (event.target == m) {
				tableCreator.cancel();
				return true;
			}
		return false;
	}
}
