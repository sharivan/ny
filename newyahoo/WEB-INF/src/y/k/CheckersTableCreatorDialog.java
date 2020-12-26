/**
 * 
 */
package y.k;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooControl;
import y.dialogs.YahooDialog;
import y.ycontrols.TableCreator;
import y.ycontrols.TableDescription;

/**
 * @author saddam
 * 
 */
public class CheckersTableCreatorDialog extends YahooDialog {

	TableCreator		tableCreator;
	YahooCheckBox		chkRated;
	YahooCheckBox		chkEnglishCheckers;
	YahooCheckBox		chkBrazilianCheckers;
	YahooCheckBox		chkSpanishCheckers;
	YahooCheckBox		chkItalianCheckers;
	YahooCheckBox		chkRussianCheckers;
	YahooCheckBox		chkInternationalCheckers;
	YahooCheckBox		chkTurkishCheckers;
	// YahooCheckBox chkTraining;
	YahooControl		h;
	YahooControl		i;
	YahooButton			btnStart;
	YahooButton			btnCancel;
	YahooControl		ptc_n;
	TableDescription	tableDescription;

	CheckersTableCreatorDialog(TableCreator _pcls97, YahooControl _pcls79) {
		super(_pcls79, "Variation");
		ptc_n = _pcls79;
		btnStart = new YahooButton("Start");
		btnCancel = new YahooButton(_pcls97.applet.lookupString(0x66500168));
		tableCreator = _pcls97;
		String category = _pcls97.getApplet().getParameter("category");

		int l = 0;

		l++;
		chkEnglishCheckers = new YahooCheckBox("English Checkers", null,
				category != null && !category.equals("social"));
		addChildObject(chkEnglishCheckers, 17, 0, 0, 2, 1, 0, l);
		chkEnglishCheckers.setChecked(true);

		l++;
		chkBrazilianCheckers = new YahooCheckBox("Brazilian Checkers",
				chkEnglishCheckers, category != null
						&& !category.equals("social"));
		addChildObject(chkBrazilianCheckers, 17, 0, 0, 2, 1, 0, l);
		chkBrazilianCheckers.setChecked(false);

		l++;
		chkSpanishCheckers = new YahooCheckBox("Spanish Checkers",
				chkEnglishCheckers, category != null
						&& !category.equals("social"));
		addChildObject(chkSpanishCheckers, 17, 0, 0, 2, 1, 0, l);
		chkSpanishCheckers.setChecked(false);

		l++;
		chkItalianCheckers = new YahooCheckBox("Italian Checkers",
				chkEnglishCheckers, category != null
						&& !category.equals("social"));
		addChildObject(chkItalianCheckers, 17, 0, 0, 2, 1, 0, l);
		chkItalianCheckers.setChecked(false);

		l++;
		chkRussianCheckers = new YahooCheckBox("Russian Checkers",
				chkEnglishCheckers, category != null
						&& !category.equals("social"));
		addChildObject(chkRussianCheckers, 17, 0, 0, 2, 1, 0, l);
		chkRussianCheckers.setChecked(false);

		l++;
		chkInternationalCheckers = new YahooCheckBox("International Checkers",
				chkEnglishCheckers, category != null
						&& !category.equals("social"));
		addChildObject(chkInternationalCheckers, 17, 0, 0, 2, 1, 0, l);
		chkInternationalCheckers.setChecked(false);

		l++;
		chkTurkishCheckers = new YahooCheckBox("Turkish Checkers",
				chkEnglishCheckers, category != null
						&& !category.equals("social"));
		addChildObject(chkTurkishCheckers, 17, 0, 0, 2, 1, 0, l);
		chkTurkishCheckers.setChecked(false);

		h = new YahooControl(2);
		i = new YahooControl(0);

		chkRated = new YahooCheckBox("rated", null, category != null
				&& !category.equals("social"));
		i.addChildObject(chkRated, 17, 0, 0, 2, 1, 0, 0);
		chkRated.setChecked(true);

		// chkTraining = new YahooCheckBox("training", null, category != null
		// && !category.equals("social"));
		// i.addChildObject(chkTraining, 17, 0, 0, 2, 1, 0, 1);
		// chkTraining.setChecked(false);

		h.addChildObject(new YahooControl(), 0);
		h.addChildObject(i, 1);
		l++;
		addChildObject(h, 17, 0, 0, 2, 1, 0, l);
		h.qo(1);
		if (_pcls97.getApplet().idPropertyContains(32L)) {
			tableDescription = new TableDescription(_pcls97.getApplet()
					.getTimerHandler(), _pcls97.getApplet());
			l++;
			addChildObject(tableDescription, 2, 1, 0, l);
		}
		YahooControl _lcls79_1 = new YahooControl(1);
		l++;
		addChildObject(_lcls79_1, 10, 0, 0, 2, 1, 0, l);
		_lcls79_1.addChildObject(btnStart, 0, 0, 2);
		_lcls79_1.addChildObject(btnCancel, 1, 0, 2);
		show();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == btnStart) {
			if (chkRated.isChecked())
				tableCreator.addProperty("rd", "");
			// if (chkTraining.isChecked())
			// tableCreator.addProperty("training", "");
			if (chkEnglishCheckers.isChecked()) {
				tableCreator.addProperty("variant", "english");
			}
			else if (chkBrazilianCheckers.isChecked()) {
				tableCreator.addProperty("variant", "brazilian");
			}
			else if (chkSpanishCheckers.isChecked()) {
				tableCreator.addProperty("variant", "spanish");
			}
			else if (chkItalianCheckers.isChecked()) {
				tableCreator.addProperty("variant", "italian");
			}
			else if (chkRussianCheckers.isChecked()) {
				tableCreator.addProperty("variant", "russian");
			}
			else if (chkInternationalCheckers.isChecked()) {
				tableCreator.addProperty("variant", "international");
			}
			else if (chkTurkishCheckers.isChecked()) {
				tableCreator.addProperty("variant", "turkish");
			}
			if (tableDescription != null)
				tableDescription.Qa(tableCreator);
			tableCreator.makeTable();
			close();
			return true;
		}
		if (event.target != chkRated)
			if (event.target instanceof YahooCheckBox) {
				if (chkBrazilianCheckers.isChecked()
						|| chkEnglishCheckers.isChecked()
						|| chkSpanishCheckers.isChecked()
						|| chkItalianCheckers.isChecked()
						|| chkRussianCheckers.isChecked()
						|| chkInternationalCheckers.isChecked()
						|| chkTurkishCheckers.isChecked()) {
					h.qo(1);
				}
				else {
					h.qo(0);
					chkRated.setChecked(false);
				}
			}
			else if (event.target == btnCancel) {
				tableCreator.cancel();
				return true;
			}
		return false;
	}

}
