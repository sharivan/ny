// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;
import java.awt.Event;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

import y.controls.YahooButton;
import y.controls.YahooComponent;
import y.controls.YahooLabel;
import y.dialogs.OkDialog;
import y.utils.Id;
import y.ycontrols.YahooGamesTableListener;
import y.ycontrols._cls31;
import y.yutils.AbstractYahooGamesApplet;
import y.yutils.YahooGamesTable;

import common.io.YData;
import common.k.TimeredGame;
import common.yutils.Game;

// Referenced classes of package y.k:
// _cls48, _cls151, _cls85, _cls42,
// _cls131, _cls31, _cls81, _cls150,
// _cls108, _cls37, _cls145, _cls27,
// _cls113, _cls62, _cls75, _cls96,
// _cls38

public class TimeParser implements YahooGamesTableListener {

	private TimeDialog	timeDialog;
	private int			currTime[];
	private boolean		d;
	private YahooButton	btnSetTimer;
	private YahooLabel	lblTimer;
	private YahooLabel	lblTimePerMove;
	Game				game;
	TimeredGame			timeredGame;
	_cls31				j;
	YahooGamesTable		table;
	boolean				canSetIncrement;
	int					m;

	public TimeParser(YahooGamesTable _pcls85) {
		this(_pcls85, true);
	}

	public TimeParser(YahooGamesTable _pcls85, boolean flag) {
		lblTimer = new YahooLabel();
		lblTimePerMove = new YahooLabel();
		m = 0;
		table = _pcls85;
		j = (_cls31) _pcls85;
		table = _pcls85;
		canSetIncrement = flag;
	}

	private void doChangeCurrTime(int i1) {
		j.ub(i1, d ? currTime[i1] : -1);
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
		if (event.id == 1001 && event.target == btnSetTimer) {
			if (table.imHost()) {
				if (timeDialog == null)
					timeDialog = new TimeDialog(table.getTimerHandler(), table
							.getTableControlContainer(), this);
				timeDialog.show();
			}
			else {
				new OkDialog(table.getTableControlContainer(), table
						.getApplet().lookupString(0x66500754), table
						.getApplet());
			}
			return true;
		}
		return false;
	}

	public void handleIterate() {
		m += 15;
		if (m >= 1000) {
			if (game.isRunning() && timeredGame.getCurrentTurn() >= 0 && d) {
				currTime[timeredGame.getCurrentTurn()] = Math.max(0,
						currTime[timeredGame.getCurrentTurn()] - 1000);
				if (currTime[timeredGame.getCurrentTurn()] == 0
						&& table.getMySitIndex() >= 0)
					notifyTimeEmpty();
				doChangeCurrTime(timeredGame.getCurrentTurn());
			}
			m = 0;
		}
	}

	public boolean handleParseData(byte byte0, DataInputStream datainputstream)
			throws IOException {
		switch (byte0) {
		case 105: // 'i': tempo esgotado
			timeredGame.handleTimeEmpty(datainputstream.readByte());
			return true;

		case 108: // 'l': tempo corrente
			byte byte1 = datainputstream.readByte();
			long l1 = datainputstream.readLong();
			currTime[byte1] = (int) l1;
			doChangeCurrTime(byte1);
			return true;

		case 106: // 'j'
		case 107: // 'k'
		default:
			return false;
		}
	}

	public void handleSetProperties(Hashtable<String, String> properties) {
		Yh();
	}

	public void handleStand(int i1) {
	}

	public void handleStart() {
		btnSetTimer.setEnabled(false);
	}

	public void handleStop(YData data) {
		btnSetTimer.setEnabled(true);
	}

	public void handleUpdateTitle(String text) {
	}

	public void im() {
		game = table.getGame();
		timeredGame = (TimeredGame) game;
		btnSetTimer = new YahooButton(table.getApplet()
				.lookupString(0x66500753));
		// game = table.getGame();
		currTime = new int[table.getAutomat()];
		table.addObject(lblTimer, -1);
		if (!((AbstractYahooGamesApplet) table.getYahooGamesApplet()).noPopupTables) {
			YahooComponent _lcls62 = new YahooComponent(1, 10);
			_lcls62.setBackColor(new Color(0x666666));
			table.addObject(_lcls62, -1);
		}
		table.addObject(lblTimePerMove, -1);
		table.addObject(btnSetTimer, -1);
		Yh();
	}

	public void nm(int i1) {
	}

	void notifyTimeEmpty() {
		table.send('I', (byte) timeredGame.getCurrentTurn());
	}

	public boolean tg() {
		return true;
	}

	public boolean ug() {
		return true;
	}

	void Yh() {
		int i1 = TableTimeLabel.getTotalTime(table.getPropertyes());
		d = i1 != -1;
		lblTimer.setCaption(table.getApplet().lookupString(0x66500755)
				+ (i1 != -1 ? i1
						/ 60000
						+ (canSetIncrement ? "/"
								+ TableTimeLabel.getIncrementTime(table
										.getPropertyes()) / 1000 : "") : table
						.getApplet().lookupString(0x66500751)));
		lblTimePerMove
				.setCaption(Game.haveTimePerMove(table.getPropertyes()) ? timeredGame
						.getTimePerMove()
						+ table.getApplet().lookupString(0x66500752)
						: "");
	}
}
