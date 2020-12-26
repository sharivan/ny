/**
 * 
 */
package y.k;

import y.dialogs.YahooDialog;
import y.ycontrols.TableCreator;

/**
 * @author saddam
 * 
 */
public class CheckersTableCreator extends TableCreator {

	YahooDialog	yahooDialog;

	public CheckersTableCreator() {
	}

	@Override
	public void cancel() {
		yahooDialog.close();
		super.cancel();
	}

	@Override
	public void createDialog() {
		yahooDialog = new CheckersTableCreatorDialog(this, super.container);
	}

}
