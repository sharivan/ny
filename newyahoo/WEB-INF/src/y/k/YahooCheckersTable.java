// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.utils.Formater;
import y.utils.Square;
import y.utils.TimerEntry;
import y.utils.TimerHandler;
import y.utils.YahooImage;
import y.ycontrols.TableControlContainer;
import y.ycontrols._cls31;

import common.k.Checkers;
import common.k.LinkedList;

// Referenced classes of package y.k:
// _cls45, _cls123, _cls48, _cls151,
// _cls63, _cls31, _cls73, _cls86,
// _cls145, _cls62, _cls65, _cls10,
// _cls75, _cls117

public class YahooCheckersTable extends YahooBoardTable implements _cls31,
		TimerHandler {

	static final Color	yct_d	= new Color(0x162d46);
	Checkers			checkers;
	BoardArea			boardArea;
	YahooComponent		yct_c;
	String[]			reply;
	boolean				computer;

	public YahooCheckersTable() {
		reply = new String[2];
		computer = false;
		addSitParser(new TimeParser(this));
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void close() {
		if (boardArea != null) {
			for (int i = 1; i < boardArea.piece.length; i++) {
				boardArea.piece[i].flush();
			}
			boardArea = null;
		}
		if (checkers != null) {
			checkers.close();
			checkers = null;
		}

		super.close();
	}

	@Override
	public void createArea() {
		checkers = (Checkers) getGame();
		boardArea = new BoardArea(this);
		String s = getApplet().getParameter("yahoo.games.table_right_image");
		String s1 = getApplet().getParameter(
				"yahoo.games.table_right_image_width");
		String s2 = getApplet().getParameter(
				"yahoo.games.table_right_image_height");
		if (s != null && s1 != null && s2 != null)
			try {
				int i = Integer.parseInt(s1);
				int j = Integer.parseInt(s2);
				yct_c = new YahooControl();
				((YahooControl) yct_c).addChildObject(new YahooImage(
						getApplet().getYahooImage(s), i, j), 1, 1, 0, 0);
			}
			catch (NumberFormatException _ex) {
			}
		if (yct_c == null)
			yct_c = new YahooComponent();
		super.createArea();
	}

	@Override
	public TableControlContainer createTableControlContainer() {
		return new CheckersControlContainer(this);
	}

	/**
	 * 
	 */
	@Override
	protected void doChangeTurn() {
		if (!computer)
			return;
		final TimerEntry selfTimer = getTimerHandler().add(this, 100);
		Thread thread = new Thread() {
			@Override
			public void run() {
				LinkedList<Square> move = checkers.bestMove(mySitIndex,
						120000L, reply);
				selfTimer.stop();
				reply[0] = null;
				reply[1] = null;
				tableControlContainer.tcc_f.append("***evaluate complete",
						Color.blue);
				makeUniqueMove(move);
			}
		};
		thread.start();
	}

	@Override
	public YahooComponent getBoardArea() {
		return boardArea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.BoardGameHandler#handleResetMoveList(int)
	 */
	@Override
	public void handleResetMoveList(int turn) {
		board.resetMoveList(turn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see y.utils.TimerEvent#handleTimer(long)
	 */
	@Override
	public void handleTimer(long l) {
		if (reply[0] != null && !reply[0].equals(reply[1])) {
			reply[1] = reply[0];
			tableControlContainer.tcc_f.append("***" + reply[1], Color.blue);
		}
	}

	/**
	 * @return true se random for verdadeiro
	 */
	public boolean isRandom() {
		return random;
	}

	@Override
	public boolean isRobot() {
		return getApplet().getParameter("yahoo.games.hasrobot") != null;
	}

	@Override
	public YahooBoard lc() {
		return boardArea.ba_a;
	}

	@Override
	public String player0Won() {
		return getApplet().lookupString(0x665008de);
	}

	@Override
	public String player1Won() {
		return getApplet().lookupString(0x665008dd);
	}

	@Override
	public Color tc() {
		return yct_d;
	}

	public void ub(int i, int j) {
		getSitLabel(i).setCaption(
				j != -1 ? getApplet().lookupString(0x66500c60)
						+ Formater.formatTimer(j) : "");
	}

	@Override
	public int uc() {
		return 0x66501143;
	}

	@Override
	public YahooComponent wc() {
		return yct_c;
	}

	@Override
	public Color yc() {
		return yct_d;
	}

}
