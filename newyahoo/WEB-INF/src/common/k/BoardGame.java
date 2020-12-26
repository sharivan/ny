// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package common.k;

import java.util.Hashtable;
import java.util.Vector;

import y.utils.Square;

import common.utils.ByteArrayData;
import common.yutils.Game;
import common.yutils.YahooUtils;

// Referenced classes of package y.k:
// _cls81, _cls99, Buffer, _cls149,
// _cls53, _cls32, _cls9, _cls150,
// _cls66

public abstract class BoardGame extends Game implements TimeredGame {

	public static boolean boardEquals(int[][] b1, int[][] b2) {
		if (b1.length != b2.length)
			return false;
		for (int j = 0; j < b1.length; j++) {
			if (b1[j].length != b2[j].length)
				return false;
			for (int i = 0; i < b1[j].length; i++)
				if (b1[j][i] != b2[j][i])
					return false;
		}
		return true;
	}

	public static String boardToString(int[][] b) {
		String result = printRow(b, 0);
		for (int i = 1; i < b.length; i++)
			result += "\r\n" + printRow(b, i);
		return result;
	}

	public static int[][] cloneBoard(int[][] b) {
		int[][] result = new int[b.length][];
		for (int j = 0; j < b.length; j++) {
			result[j] = new int[b[j].length];
			for (int i = 0; i < b[j].length; i++)
				result[j][i] = b[j][i];
		}
		return result;
	}

	public static int getIncrementTime(Hashtable<String, String> hashtable) {
		int i = Game.getInRange("it", hashtable, 0, 0x7fffffff, 0);
		i -= i % 1000;
		return i;
	}

	public static int getTotalTime(Hashtable<String, String> hashtable) {
		int i = Game.getInRange("tt", hashtable, -1, 0x7fffffff, -1);
		if (i >= 0 && i < 60000)
			return -1;
		if (i != -1)
			i -= i % 60000;
		return i;
	}

	public static boolean haveAutoSwap(Hashtable<String, String> properties) {
		return properties != null && properties.containsKey("autoSwap")
				&& !properties.get("autoSwap").equals("0");
	}

	public static boolean isAutomat(Hashtable<String, String> properties) {
		return properties != null && properties.containsKey("automat")
				&& !properties.get("automat").equals("0");
	}

	public static boolean isTraining(Hashtable<String, String> properties) {
		return properties != null && properties.containsKey("training")
				&& !properties.get("training").equals("0");
	}

	public static boolean moveListContains(Tree<Square> moveList, int x, int y) {
		return moveList != null && moveList.hasValue(new Square(x, y), false);
	}

	public static String printRow(int[][] b, int row) {
		if (b[row].length == 0)
			return null;
		String result = Integer.toString(b[row][0]);
		for (int i = 1; i < b[row].length; i++)
			result += " " + b[row][i];
		return result;
	}

	protected int				rowCount;
	protected int				colCount;
	int							currTurn;
	protected BoardGameHandler	handler;
	protected int				board[][];
	protected Tree<Square>[]	moveList;

	private int					sitCount;

	int							sitWaitingForDraw;

	boolean						training;

	Vector<BoardSquare>			changedSquares;

	ByteArrayData				m;

	public BoardGame() {
		super();
		currTurn = -3;
		training = false;
		m = new ByteArrayData(2);
	}

	public boolean acceptDraw(int i1) {
		if (super.running && i1 >= 0 && currTurn == i1
				&& sitWaitingForDraw == (i1 + 1) % 2) {
			doWon(-2, false);
			return true;
		}
		return false;
	}

	protected void addMoveTree(int turn, Tree<Square> moveTree) {
		if (moveList[turn].hasChild(moveTree, false))
			return;
		moveList[turn].addChild(moveTree);
	}

	@Override
	public void close() {
		deallocate();
		handler = null;
		board = null;
		moveList = null;
		changedSquares = null;
		m = null;
	}

	public void deallocate() {
		for (int i = 0; i < sitCount; i++) {
			// Maker.hashMovesManager.deallocate(hashMoves[j1]);
			// for (int k1 = 0; k1 < colCount; k1++)
			// for (int l1 = 0; l1 < rowCount; l1++)
			Maker.moveListManager.deallocate(moveList[i]);
		}
	}

	protected abstract void doMove(int x1, int y1, int x2, int y2, int turn);

	@Override
	public boolean doResign(int turn) {
		if (isRunning()) {
			doWon((turn + 1) % 2, true);
			return true;
		}
		return false;
	}

	@Override
	public boolean doStop() {
		if (super.running) {
			setTurn(-3);
			doStop(null);
			return true;
		}
		return false;
	}

	protected void doWon(int turn, boolean flag) {
		setTurn(-3);
		handler.handleWon(turn, flag);
		if (turn == -3)
			doStop(null);
		else {
			if (turn == -2) {
				m.clear();
			}
			else {
				m.setByte(turn, 1);
				m.setByte((turn + 1) % 2, 2);
			}
			doStop(m);
		}
		super.running = false;
	}

	public boolean drawExist() {
		return false;
	}

	protected void flushMoveList(int turn) {
		if (turn == -2) {
			for (int j1 = 0; j1 < sitCount; j1++)
				flushMoveList(j1);

		}
		else {
			handler.handleResetMoveList(turn);
			for (int k1 = 0; k1 < moveList[turn].childCount(); k1++) {
				Tree<Square> tree = moveList[turn].childAt(k1);
				int x = tree.getValue().getX();
				int y = tree.getValue().getY();
				handler.handleChangeMoveList(x, y, turn, tree);
			}
			// hashMoves[turn].clear();
		}
	}

	public final int getBoard(int x, int y) {
		return board[y][x];
	}

	public final int getColCount() {
		return colCount;
	}

	public final int getCurrentTurn() {
		return currTurn;
	}

	/**
	 * @return the moveList
	 */
	public Tree<Square> getMoveList(int turn) {
		return moveList[turn];
	}

	public final Tree<Square> getMoveTree(int turn, int x, int y) {
		for (int i = 0; i < moveList[turn].childCount(); i++) {
			Tree<Square> tree = moveList[turn].childAt(i);
			Square value = tree.getValue();
			if (value != null && value.equals(x, y))
				return tree;
		}
		return null;
	}

	public final int getRowCount() {
		return rowCount;
	}

	/*
	 * void putHashMove(int turn, int x, int y) { Square square = new Square(x,
	 * y); if(!hashMoves[turn].contains(square)) hashMoves[turn].add(square); }
	 */

	/*
	 * void addPosition(Vector<Square> move, int x, int y) { move.add(new
	 * Square(x, y)); }
	 */

	public abstract int getTimePerMove();

	public boolean handleTimeEmpty(int i1) {
		if (super.running) {
			doWon((i1 + 1) % 2, true);
			return true;
		}
		return false;
	}

	@Override
	public boolean isCurrentTurn(int turn) {
		if (super.running)
			return turn == currTurn || currTurn == -2 || training;
		return false;
	}

	public boolean isRated() {
		return false;
	}

	public final boolean isValidMove(int x1, int y1, int x2, int y2, int turn) {
		return (turn == currTurn || currTurn == -2)
				&& moveListContains(getMoveTree(turn, x1, y1), x2, y2);
	}

	public boolean isValidPos(int x, int y) {
		return x >= 0 && y >= 0 && x < rowCount && y < colCount;
	}

	@SuppressWarnings("unchecked")
	public void makeBoard(int rowCount, int colCount, int sitCount,
			BoardGameHandler handler, boolean training) {
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.training = training;
		board = new int[colCount][rowCount];
		changedSquares = new Vector<BoardSquare>();
		moveList = new Tree[sitCount];
		// hashMoves = new Vector[sitCount];
		for (int i2 = 0; i2 < sitCount; i2++) {
			// hashMoves[i2] = Maker.allocateHashMoves();
			// for (int j2 = 0; j2 < colCount; j2++)
			// for (int k2 = 0; k2 < rowCount; k2++)
			moveList[i2] = Maker.allocateMoveList();
		}

		this.sitCount = sitCount;
		this.handler = handler;
	}

	public boolean makeMove(int x1, int y1, int x2, int y2, int turn) {
		if (super.running
				&& (getCurrentTurn() == -2 || getCurrentTurn() == turn)
				&& isValidMove(x1, y1, x2, y2, turn)) {
			doMove(x1, y1, x2, y2, turn);
			return true;
		}
		return false;
	}

	/**
	 * @param turn
	 * @param move
	 */
	public boolean makeMove(Move move, int turn) {
		return makeMove(move.x1, move.y1, move.x2, move.y2, turn);
	}

	public boolean offerDraw(int turn) {
		if (super.running && drawExist() && turn >= 0 && currTurn != turn
				&& sitWaitingForDraw == -3) {
			sitWaitingForDraw = turn;
			handler.handleOfferDraw(turn);
			return true;
		}
		return false;
	}

	public String printRow(int row) {
		if (rowCount == 0)
			return null;
		String result = Integer.toString(board[row][0]);
		for (int i = 1; i < rowCount; i++)
			result += " " + board[row][i];
		return result;
	}

	/**
	 * @return um movimento aleatório
	 */
	public Vector<Square> randomMove(int turn) {
		Vector<Square> result = new Vector<Square>();
		randomMove1(result, moveList[turn]);
		return result;
	}

	private void randomMove1(Vector<Square> result, Tree<Square> node) {
		if (node.childCount() == 0)
			return;
		int i = YahooUtils.randomRange(0, node.childCount() - 1);
		Tree<Square> node1 = node.childAt(i);
		result.add(node1.value);
		randomMove1(result, node1);
	}

	@Override
	public void refresh() {
		super.refresh();
		handler.handleChangeTurn(currTurn);
		handler.Lc(0, 0);
		if (super.running && sitWaitingForDraw >= 0)
			handler.handleOfferDraw(sitWaitingForDraw);
	}

	protected void reset() {
		for (int i1 = 0; i1 < colCount; i1++) {
			for (int j1 = 0; j1 < rowCount; j1++) {
				setBoard(j1, i1, 0);
			}
		}

		for (int i = 0; i < sitCount; i++) {
			for (int j = 0; j < moveList[i].childCount(); j++) {
				Tree<Square> tree = moveList[i].childAt(j);
				handler.handleChangeMoveList(tree.getValue().getX(), tree
						.getValue().getY(), i, null);
			}
			moveList[i].clear();
		}

		/*
		 * for (int k1 = 0; k1 < sitCount; k1++) { hashMoves[k1].clear(); }
		 */

		currTurn = sitWaitingForDraw = -3;
		handler.handleChangeTurn(-3);
		handler.handleLastMove(-1, -1);
		handler.Lc(0, 0);
	}

	public void restoreStateIfChanged() {
	}

	protected void saveSquare(int x, int y) {
		changedSquares.add(new BoardSquare(x, y, getBoard(x, y)));
	}

	public void saveState(int x1, int y1, int x2, int y2, int turn) {
	}

	public void saveState(Move move, int turn) {
		this.saveState(move.x1, move.y1, move.x2, move.y2, turn);
	}

	public void setBoard(int x, int y, int value) {
		if (value != board[y][x]) {
			board[y][x] = value;
			handler.handleSetBoard(x, y, value);
		}
	}

	public void setBoard(int[][] board) {
		for (int i = 0; i < rowCount; i++)
			for (int j = 0; j < colCount; j++)
				setBoard(i, j, board[j][i]);
	}

	protected void setTurn(int turn) {
		if (turn == sitWaitingForDraw && turn >= 0) {
			sitWaitingForDraw = -3;
			handler.handleDeclineDraw(turn);
		}
		currTurn = turn;
		handler.handleChangeTurn(turn);
		if (turn == -3)
			handler.handleStopTick();
		else
			handler.handleStartTick(getTimePerMove() * 60000);
	}

	@Override
	public String toString() {
		if (colCount == 0)
			return null;
		String result = "********************The Board**********************";
		result += "\r\n";
		result += printRow(0);
		for (int i = 1; i < colCount; i++)
			result += "\r\n" + printRow(i);
		result += "\r\n";
		result += "********************Move List**********************";
		result += "\r\n";
		for (int i = 0; i < sitCount; i++) {
			result += "\r\n";
			result += "********************[" + i + "]**********************";
			result += "\r\n";
			result += moveList[i];
			result += "\r\n";
		}
		result += "******************************************";
		return result;
	}

	public boolean undo(int sitIndex) {
		if (isRunning() || isCurrentTurn(sitIndex)) {
			handler.handleUndo();
			return true;
		}
		return false;
	}

	protected void undoMove2(int turn) {
		for (int i = 0; i < changedSquares.size(); i++) {
			BoardSquare square = changedSquares.elementAt(i);
			setBoard(square.getX(), square.getY(), square.getValue());
		}
		changedSquares.clear();
	}

	public boolean up(int i1) {
		return false;
	}

	public void vp(int i1) {
	}

}
