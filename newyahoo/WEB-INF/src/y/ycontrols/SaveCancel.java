// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

import y.dialogs.OkDialog;
import y.dialogs.YesNoDialog;
import y.dialogs.YesNoDialogHandler;
import y.utils.Id;
import y.ydialogs.AbortGameDialog;
import y.ydialogs.ForceForfeitDialog;
import y.yutils.YahooGamesTable;

import common.io.YData;
import common.yutils.Game;

// Referenced classes of package y.po:
// _cls7, _cls150, _cls174, _cls99,
// _cls51, _cls10, _cls163, _cls95,
// _cls47, _cls168, _cls111, _cls49

public class SaveCancel implements YahooGamesTableListener, YesNoDialogHandler {

	boolean			enabled;
	YahooGamesTable	table;
	YesNoDialog		acceptAndSaveDialog;
	YesNoDialog		acceptOnlyDialog;

	public SaveCancel(YahooGamesTable _pcls99) {
		enabled = false;
		table = _pcls99;
	}

	private int getCurrentTurn() {
		for (int i = 0; i < table.getAutomat(); i++)
			if (table.getGame().isCurrentTurn(i))
				return i;

		return -1;
	}

	public void handeHide() {
	}

	public void handleAddId(Id id) {

	}

	public void handleClose() {
		table = null;
	}

	public void handleCreateFrame() {
	}

	public boolean handleEvent(Event event) {
		return false;
	}

	public void handleIterate() {
	}

	public void handleNo(YesNoDialog _pcls7) {
		if (_pcls7 == acceptAndSaveDialog) {
			table.send('X', (byte) 0, (byte) 1);
			acceptAndSaveDialog = null;
		}
		else if (_pcls7 == acceptOnlyDialog) {
			table.send('X', (byte) 0, (byte) 0);
			acceptOnlyDialog = null;
		}
	}

	public boolean handleParseData(byte byte0, DataInputStream datainputstream)
			throws IOException {
		if (byte0 == '=') {
			byte sitIndex = datainputstream.readByte();
			boolean flag = datainputstream.readBoolean();
			table.logMessage(flag ? table.getApplet().lookupString(0x6650008f)
					: table.getApplet().lookupString(0x665000ab), Color.blue);
			// ***
			// Save
			// requested,
			// ***
			// Cancel
			// requested
			if (table.getMySitIndex() >= 0 && sitIndex != table.getMySitIndex()) {
				String s = sitTypeToString(sitIndex);
				if (flag) {
					if (acceptAndSaveDialog == null)
						acceptAndSaveDialog = new YesNoDialog(
								table.getApplet(), table
										.getTableControlContainer(), table
										.getApplet().lookupString(0x665000b1)
										+ s
										+ table.getApplet().lookupString(
												0x66500091), this);
				}
				else if (acceptOnlyDialog == null)
					acceptOnlyDialog = new YesNoDialog(table.getApplet(), table
							.getTableControlContainer(), table.getApplet()
							.lookupString(0x665000b1)
							+ s + table.getApplet().lookupString(0x66500095),
							this);
			}
			return true;
		}
		return false;
	}

	public void handleSetProperties(Hashtable<String, String> properties) {
	}

	public void handleStand(int index) {
		if (table.getGame().isRunning() && table.getMySitIndex() >= 0
				&& index != table.getMySitIndex()
				&& Game.isRated(table.getPropertyes())
				&& Game.haveTimePerMove(table.getPropertyes())) {
			String text = sitTypeToString(index);
			if (table.getGame().isCurrentTurn(index) && !table.isTicking())
				showForceForfeitDialog(index);
			else
				new OkDialog(table.getTableControlContainer(),
						table.getApplet().lookupString(0x665000b1)
								+ text
								+ table.getApplet().lookupString(0x665000b4)
								+ (table.getGame().isCurrentTurn(
										table.getMySitIndex()) ? table
										.getApplet().lookupString(0x6650008a)
										: table.getApplet().lookupString(
												0x665000ba))
								+ table.getApplet().lookupString(0x66500093)
								+ text
								+ table.getApplet().lookupString(0x6650008e),
						table.getApplet());
		}
	}

	public void handleStart() {
		enabled = table.getMySitIndex() < 0;
	}

	public void handleStop(YData data) {
		enabled = false;
	}

	public void handleUpdateTitle(String text) {
	}

	public void handleYes(YesNoDialog _pcls7) {
		if (_pcls7 == acceptAndSaveDialog) {
			table.send('X', (byte) 1, (byte) 1);
			acceptAndSaveDialog = null;
		}
		else if (_pcls7 == acceptOnlyDialog) {
			table.send('X', (byte) 1, (byte) 0);
			acceptOnlyDialog = null;
		}
	}

	public void im() {
	}

	public boolean isEnabled() {
		boolean flag = table.getNumber() > 0
				&& table.getFinishedGameCount() == 0;
		if (table.getMySitIndex() >= 0 && flag || table.getGame().isRunning()
				&& Game.isRated(table.getPropertyes())
				&& table.getMySitIndex() >= 0 && !enabled) {
			new AbortGameDialog(table);
			return false;
		}
		return true;
	}

	public void nm(int i) {
		int currTurn = getCurrentTurn();
		if (i == 0 && table.getMySitIndex() >= 0
				&& !table.getGame().isCurrentTurn(table.getMySitIndex())
				&& Game.isRated(table.getPropertyes())
				&& Game.haveTimePerMove(table.getPropertyes()) && currTurn >= 0)
			showForceForfeitDialog(currTurn);
	}

	private final void showForceForfeitDialog(int index) {
		if (Game.canForceForfeit(table.getPropertyes()))
			new ForceForfeitDialog(table, sitTypeToString(index), index);
	}

	String sitTypeToString(int index) {
		return !table.getGame().hasPartner()
				|| table.getMySitIndex() % 2 != index % 2 ? table.getApplet()
				.lookupString(0x66500098) : table.getApplet().lookupString(
				0x665000b8);
	}

	public boolean tg() {
		return isEnabled();
	}

	public boolean ug() {
		return isEnabled();
	}

}
