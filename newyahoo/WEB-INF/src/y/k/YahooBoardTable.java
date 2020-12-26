// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;
import java.awt.Event;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.dialogs.YesNoDialog;
import y.utils.Square;
import y.ycontrols.SaveCancel;
import y.yutils.AbstractYahooGamesApplet;
import y.yutils.YahooGamesTable;

import common.k.BoardGame;
import common.k.BoardGameHandler;
import common.k.LinkedList;
import common.k.Move;
import common.k.Tree;
import common.yutils.Game;

// Referenced classes of package y.k:
// _cls85, _cls147, _cls4, _cls128,
// _cls48, _cls151, _cls17, _cls117,
// _cls64, _cls146, _cls81, _cls127,
// _cls66, _cls19, _cls145, _cls27,
// _cls67, _cls62, _cls65, _cls75,
// _cls138

public abstract class YahooBoardTable extends YahooGamesTable implements
		BoardGameHandler {

	public YahooBoard	board;
	BoardGame			boardGame;
	BoardContainer		ybt_c;
	_cls17				d;
	YahooButton			btnOfferDraw;
	YahooButton			btnUndoTurn;
	YahooCheckBox		chkRated;
	YahooCheckBox		chkLastMove;
	YesNoDialog			dlgDraw;
	YesNoDialog			dlgUndoTurn;
	boolean				m;
	private int			moveCounter;
	protected boolean	random;

	public YahooBoardTable() {
		m = false;
		moveCounter = 0;
		random = false;
		addSitParser(new SaveCancel(this));
	}

	@Override
	public void activateControls(int i1) {
		btnOfferDraw.setEnabled(false);
		if (i1 == 1 && Kc())
			swap();
		board.setMyTurn(-1);
		btnUndoTurn.setEnabled(false);
		super.activateControls(i1);
	}

	@Override
	public boolean canSave() {
		return true;
	}

	public boolean canUndo() {
		return false;
	}

	@Override
	public void close() {
		if (ybt_c != null) {
			ybt_c.table = null;
			ybt_c = null;
		}
		if (d != null) {
			d.table = null;
			d = null;
		}
		if (boardGame != null) {
			boardGame.close();
			boardGame = null;
		}
		board = null;

		super.close();
	}

	@Override
	public void createArea() {
		// btnResign = new YahooButton(getApplet().lookupString(uc()));
		btnOfferDraw = new YahooButton(getApplet().lookupString(0x6650073e));
		boardGame = (BoardGame) getGame();
		btnUndoTurn = new YahooButton(getApplet().lookupString(0x66500740));
		chkRated = new YahooCheckBox(getApplet().lookupString(0x6650073d));
		chkLastMove = new YahooCheckBox(getApplet().lookupString(0x6650073b));
		if (boardGame.isRated()
				&& !getApplet().isKids()
				&& ((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
			addObject(chkRated, 0);
		if (canUndo())
			addYahooObject(btnUndoTurn);
		if (boardGame.drawExist())
			addYahooObject(btnOfferDraw);
		chkLastMove.setChecked(vc());
		addObject(chkLastMove, -1);
		btnOfferDraw.setEnabled(false);
		btnUndoTurn.setEnabled(false);
		board = lc();
		d = rc();
		board.Io(vc());
		ybt_c = new BoardContainer(this, getBoardArea(), wc());

		Vc();
		super.createArea();
	}

	@Override
	public void deactivateControls(int i1) {
		boolean flag = boardGame.isRunning() && boardGame.getCurrentTurn() >= 0
				&& (boardGame.getCurrentTurn() + 1) % 2 == getMySitIndex();
		btnOfferDraw.setEnabled(flag);
		btnUndoTurn.setEnabled(flag);
		if (i1 == 1 && Kc())
			swap();
		board.setMyTurn(i1);
		super.deactivateControls(i1);
	}

	@Override
	protected void doChangeHost(String s) {
		super.doChangeHost(s);
		chkRated.setEnabled(imHost());
	}

	/**
	 * 
	 */
	protected void doChangeTurn() {

	}

	public abstract YahooComponent getBoardArea();

	@Override
	public YahooControl getGameArea() {
		return ybt_c;
	}

	public YahooLabel getSitLabel(int index) {
		return ybt_c.c64[getRealSitIndex(index)];
	}

	public void handleChangeMoveList(int i1, int j1, int k1,
			Tree<Square> moveList) {
		board.setMoveList(i1, j1, k1, moveList);
	}

	public void handleChangeTurn(int i1) {
		if (i1 == getMySitIndex() && i1 >= 0) {
			beep();
			Tree<Square> moveList = boardGame.getMoveList(i1);
			if (moveList.childCount() == 1)
				makeUniqueMove(moveList.childAt(0));
			else
				doChangeTurn();
			// else if(random)
			// moveCounter = 30;
		}
		for (int j1 = 0; j1 < getAutomat(); j1++)
			ybt_c.arrows[getRealSitIndex(j1)].setVisible(i1 == -2 || i1 >= 0
					&& j1 == i1);

		boolean flag = i1 >= 0 && (i1 + 1) % 2 == getMySitIndex();
		btnOfferDraw.setEnabled(flag);
		btnUndoTurn.setEnabled(flag);
		board.Jo(i1);
	}

	public void handleDeclineDraw(int i1) {
		logMessage(getApplet().lookupString(0x66500741), Color.blue);
	}

	public void handleLastMove(int i1, int j1) {
		board.setLastMove(i1, j1);
	}

	@Override
	public synchronized void handleNo(YesNoDialog _pcls4) {
		if (_pcls4 == dlgDraw)
			dlgDraw = null;
		else if (_pcls4 == dlgUndoTurn)
			dlgUndoTurn = null;
		else
			super.handleNo(_pcls4);
	}

	public void handleOfferDraw(int i1) {
		if ((i1 + 1) % 2 == getMySitIndex() && dlgDraw == null)
			dlgDraw = new YesNoDialog(getApplet(), getTableControlContainer(),
					getApplet().lookupString(0x6650073c), this);
	}

	public void handleSetBoard(int i1, int j1, int k1) {
		board.setBoard(i1, j1, k1);
	}

	@Override
	public void handleStart() {
		super.handleStart();
		chkRated.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.BoardGameHandler#handleUndo()
	 */
	@Override
	public void handleUndo() {
		synchronized (gameHistory) {
			try {
				gameHistory.back();
				byte byte0 = gameHistory.readByte();
				if (byte0 != 'm')
					throw new IOException("invalid byte0 (byte=" + byte0 + ")");
				gameHistory.readLong();
				int x1 = gameHistory.readByte();
				int y1 = gameHistory.readByte();
				int oldPiece1 = gameHistory.readByte();
				gameHistory.readByte();
				int x2 = gameHistory.readByte();
				int y2 = gameHistory.readByte();
				int oldPiece2 = gameHistory.readByte();
				gameHistory.readByte();
				boardGame.setBoard(x1, y1, oldPiece1);
				boardGame.setBoard(x2, y2, oldPiece2);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				gameHistory.endWrite();
			}
		}
	}

	public void handleWon(int wonTurn, boolean flag) {
		if (wonTurn == -2) // Draw
			showQuickMessage(getApplet().lookupString(0x66500745));
		else if (wonTurn >= 0)
			showQuickMessage((wonTurn != 0 ? player1Won() : player0Won())
					+ getApplet().lookupString(0x66500743));
		chkRated.setEnabled(true);
		btnResign.setEnabled(false);
	}

	@Override
	public synchronized void handleYes(YesNoDialog _pcls4) {
		if (_pcls4 == dlgDraw) {
			dlgDraw = null;
			send('W');
		}
		else if (_pcls4 == dlgUndoTurn) {
			dlgUndoTurn = null;
			send('N');
		}
		else
			super.handleYes(_pcls4);
	}

	public abstract YahooBoard lc();

	public void Lc(int i1, int j1) {
		if (d != null)
			d.Ha(i1, j1);
	}

	public synchronized void makeMove(int i1, int j1, int i2, int j2) {
		boardGame.saveState(i1, j1, i2, j2, getMySitIndex());
		send('M', new Move(i1, j1, i2, j2));
	}

	public synchronized void makeMove(Vector<Integer> vector) {
		if (getMySitIndex() >= 0) {
			int i1 = vector.elementAt(0).intValue();
			int j1 = vector.elementAt(1).intValue();
			int k1 = vector.elementAt(2).intValue();
			int l1 = vector.elementAt(3).intValue();
			makeMove((byte) i1, (byte) j1, (byte) k1, (byte) l1);
		}
	}

	/**
	 * 
	 */
	private void makeRandomMove() {
		Vector<Square> moves = boardGame.randomMove(mySitIndex);
		if (moves.size() < 2)
			return;
		Square s0 = moves.elementAt(0);
		for (int i = 1; i < moves.size(); i++) {
			Square s = moves.elementAt(i);
			makeMove(s0.getX(), s0.getY(), s.getX(), s.getY());
			s0 = s;
		}
	}

	public void makeUniqueMove(LinkedList<Square> move) {
		int x1 = move.getValue().getX();
		int y1 = move.getValue().getY();
		LinkedList<Square> next = move.getNext();
		if (next != null) {
			int x2 = next.getValue().getX();
			int y2 = next.getValue().getY();
			makeMove(x1, y1, x2, y2);
			makeUniqueMove(next);
		}
	}

	public void makeUniqueMove(Tree<Square> node) {
		int x1 = node.getValue().getX();
		int y1 = node.getValue().getY();
		if (node.childCount() == 1) {
			Tree<Square> node1 = node.childAt(0);
			int x2 = node1.getValue().getX();
			int y2 = node1.getValue().getY();
			makeMove(x1, y1, x2, y2);
			makeUniqueMove(node1);
		}
		else if (random)
			moveCounter = 30;
	}

	@Override
	public void notifyChangeProperties(Hashtable<String, String> hashtable) {
		super.notifyChangeProperties(hashtable);
		Vc();
	}

	@Override
	public synchronized void parseData(byte byte0,
			DataInputStream datainputstream) throws IOException {
		switch ((char) byte0) {
		case 'm': // move
			byte turn = datainputstream.readByte();
			byte x1 = datainputstream.readByte();
			byte y1 = datainputstream.readByte();
			byte x2 = datainputstream.readByte();
			byte y2 = datainputstream.readByte();

			int oldPiece1 = boardGame.getBoard(x1, y1);
			int oldPiece2 = boardGame.getBoard(x2, y2);

			boardGame.makeMove(x1, y1, x2, y2, turn);
			boardGame.restoreStateIfChanged();

			int newPiece1 = boardGame.getBoard(x1, y1);
			int newPiece2 = boardGame.getBoard(x2, y2);

			synchronized (gameHistory) {
				gameHistory.beginWrite('m');
				try {
					gameHistory.write(x1);
					gameHistory.write(y1);
					gameHistory.write(oldPiece1);
					gameHistory.write(newPiece1);
					gameHistory.write(x2);
					gameHistory.write(y2);
					gameHistory.write(oldPiece2);
					gameHistory.write(newPiece2);
				}
				finally {
					gameHistory.endWrite();
				}
			}

			break;

		case 'n': // undo turn
			byte byte2 = datainputstream.readByte();
			boardGame.undo(byte2);
			boardGame.restoreStateIfChanged();
			break;

		case 'f': // offer draw
			byte byte3 = datainputstream.readByte();
			boardGame.offerDraw(byte3);
			boardGame.restoreStateIfChanged();
			break;

		case 'w':
			byte byte4 = datainputstream.readByte();
			boardGame.acceptDraw(byte4);
			boardGame.restoreStateIfChanged();
			break;

		case 'g':
			boardGame.up(datainputstream.readByte());
			boardGame.restoreStateIfChanged();
			break;

		default:
			super.parseData(byte0, datainputstream);
			break;
		}
	}

	public abstract String player0Won();

	public abstract String player1Won();

	@Override
	public synchronized void processEvent(Event event) {
		if (event.id == Event.ACTION_EVENT) {
			if (event.target == chkLastMove) {
				board.Io(((Boolean) event.arg).booleanValue());
				return;
			}
			if (event.target == btnOfferDraw)
				send('F');
			else if (event.target == btnUndoTurn)
				send('N');
			else if (event.target == chkRated)
				send('K', (byte) (chkRated.isChecked() ? 1 : 0));
		}
		else if (event.id == Event.KEY_PRESS) {
			if (event.key == Event.F8)
				makeRandomMove();
		}
		super.processEvent(event);
	}

	@Override
	public void processMessages() {
		super.processMessages();
		if (moveCounter > 0) {
			moveCounter--;
			if (moveCounter == 0)
				makeRandomMove();
		}
	}

	public _cls17 rc() {
		return null;
	}

	void swap() {
		String s = ybt_c.b64[0].getFirstLine();
		ybt_c.b64[0].setCaption(ybt_c.b64[1].getFirstLine());
		ybt_c.b64[1].setCaption(s);
		s = ybt_c.c64[0].getFirstLine();
		ybt_c.c64[0].setCaption(ybt_c.c64[1].getFirstLine());
		ybt_c.c64[1].setCaption(s);
	}

	public Color tc() {
		return lc().getForeColor();
	}

	/**
	 * 
	 */
	public void toggleRandom() {
		random = !random;
	}

	public abstract int uc();

	public synchronized void Uc(int i1) {
		boardGame.vp(i1);
	}

	public boolean vc() {
		return false;
	}

	public void Vc() {
		chkRated.setChecked(Game.isRated(getPropertyes()));
	}

	public YahooComponent wc() {
		return new YahooComponent();
	}

	public Color yc() {
		return lc().getForeColor();
	}

}
