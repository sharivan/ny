// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Event;
import java.util.Vector;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;
import y.controls.YahooVector;
import y.utils.Square;

import common.k.Tree;

// Referenced classes of package y.k:
// _cls62, _cls51, _cls99, _cls138,
// _cls127, _cls100

public abstract class YahooBoard extends YahooComponent {

	protected int				yb_a[][];
	protected boolean			availableMoves[][][];
	protected boolean			cyanSquares[][];
	protected boolean			yb_c[][];
	protected Vector<Square>	yellowSquares[][][];
	protected Tree<Square>		moveList[][][];
	protected int				myTurn;
	int							currTurn;
	protected int				currY;
	protected int				currX;
	int							i;
	int							j;
	protected int				k;
	protected int				l;
	protected int				lastX;
	protected int				lastY;
	protected boolean			yb_o;
	protected int				boardRowCount;
	protected int				boardColCount;
	String						yb_r;
	boolean						yb_s;
	boolean						changed[][];
	protected boolean			yb_u;
	protected boolean			yb_v;
	protected int				bpX;
	protected int				bpY;

	@SuppressWarnings("unchecked")
	public YahooBoard(int rowCount, int colCount, int sitCount, boolean flag) {
		myTurn = -1;
		currTurn = -3;
		currY = -1;
		currX = -1;
		i = -1;
		j = -1;
		k = -1;
		l = -1;
		lastX = -1;
		lastY = -1;
		yb_r = "";
		yb_v = false;
		yb_s = flag;
		boardRowCount = rowCount;
		boardColCount = colCount;
		yb_a = new int[colCount][rowCount];
		yb_c = new boolean[colCount][rowCount];
		availableMoves = new boolean[sitCount][colCount][rowCount];
		cyanSquares = new boolean[colCount][rowCount];
		yellowSquares = new Vector[sitCount][colCount][rowCount];
		moveList = new Tree[sitCount][colCount][rowCount];
		changed = new boolean[colCount][rowCount];
		Eo();
		for (int l1 = 0; l1 < sitCount; l1++)
			for (int i2 = 0; i2 < colCount; i2++)
				for (int j2 = 0; j2 < rowCount; j2++)
					yellowSquares[l1][i2][j2] = new Vector(1);
	}

	/**
	 * @return
	 */
	protected abstract boolean canDraw();

	protected abstract void computeBoardPosition(int i1, int j1);

	@Override
	public void Dn() {
		super.Dn();
		Eo();
	}

	public void Do() {
	}

	protected abstract void drawSquare(YahooGraphics _pcls100, int x, int y);

	public void Eo() {
		for (int i1 = 0; i1 < boardColCount; i1++) {
			for (int j1 = 0; j1 < boardRowCount; j1++)
				changed[i1][j1] = true;
		}
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		if (currX != -1 && myTurn >= 0)
			if (yb_s) {
				if (k == -1 && hasAvailableMoves(currX, currY)
						&& yellowSquares[myTurn][currY][currX].size() > 0) {
					k = currX;
					l = currY;
					yb_u = false;
					Wi(currX, currY);
					if (bpX != -1)
						Ri(event, bpX, bpY);
					for (int k1 = 0; k1 < boardRowCount; k1++) {
						for (int l1 = 0; l1 < boardColCount; l1++)
							yb_c[l1][k1] = false;

					}

					for (int i2 = 0; i2 < yellowSquares[myTurn][currY][currX]
							.size(); i2++) {
						Square _lcls99 = yellowSquares[myTurn][currY][currX]
								.elementAt(i2);
						yb_c[_lcls99.getY()][_lcls99.getX()] = true;
					}

					setChanged(k, l);
					invalidate();
				}
			}
			else if (hasAvailableMoves(currX, currY)) {
				i = currX;
				j = currY;
			}
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		if (k != -1) {
			yb_u = true;
			if (event.arg != null) {
				computeBoardPosition(i1
						+ ((YahooComponent) event.arg).getWidth() / 2, j1
						+ ((YahooComponent) event.arg).getHeight() / 2);
				if (bpX != -1) {
					xo(bpX, bpY);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean eventMouseMove(Event event, int i1, int j1) {
		computeBoardPosition(i1, j1);
		if (bpX != -1) {
			fillCyanSquares(bpX, bpY);
			xo(bpX, bpY);
			return true;
		}
		return false;
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		Event event1 = null;
		if (yb_s) {
			if (yb_v) {
				yb_v = false;
				yb_u = true;
			}
			if (k != -1) {
				setChanged(k, l);
				if (yb_u && currX != -1) {
					if (yb_c[currY][currX])
						event1 = new Event(this, 1001,
								new YahooVector<Integer>(new Integer(k),
										new Integer(l), new Integer(currX),
										new Integer(currY)));
					k = -1;
				}
				else {
					yb_v = true;
				}
				Do();
				invalidate();
			}
		}
		else if (currX >= 0 && hasAvailableMoves(currX, currY) && currX == i
				&& currY == j)
			event1 = new Event(this, 1001, new YahooVector<Integer>(
					new Integer(currX), new Integer(currY), new Integer(currX),
					new Integer(currY)));
		if (event1 != null)
			doEvent(event1);
		return false;
	}

	private void fillCyanSquares(int x, int y) {
		for (int i = 0; i < boardRowCount; i++)
			for (int j = 0; j < boardColCount; j++)
				if (cyanSquares[j][i]) {
					changed[j][i] = true;
					cyanSquares[j][i] = false;
				}
		if (myTurn < 0 || moveList[myTurn][y][x] == null)
			return;
		Tree<Square> node = moveList[myTurn][y][x];
		fillCyanSquares1(myTurn, node);
	}

	public void fillCyanSquares1(int turn, Tree<Square> node) {
		int x = node.getValue().getX();
		int y = node.getValue().getY();
		cyanSquares[y][x] = true;
		changed[y][x] = true;
		for (int i = 0; i < node.childCount(); i++) {
			Tree<Square> node1 = node.childAt(i);
			fillCyanSquares1(turn, node1);
		}
	}

	boolean hasAvailableMoves(int x, int y) {
		return myTurn >= 0 && (currTurn == myTurn || currTurn == -2)
				&& availableMoves[myTurn][y][x];
	}

	public void Io(boolean flag) {
		yb_o = flag;
		setChanged(lastX, lastY);
		invalidate();
	}

	public void Jo(int i1) {
		currTurn = i1;
		refresh();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		for (int i1 = 0; i1 < boardRowCount; i1++) {
			for (int j1 = 0; j1 < boardColCount; j1++)
				if (changed[j1][i1]) {
					drawSquare(yahooGraphics, i1, j1);
					changed[j1][i1] = false;
				}
		}
	}

	void refresh() {
		if (k != -1) {
			k = -1;
		}
		Eo();
		invalidate();
	}

	/**
	 * @param turn
	 */
	public void resetMoveList(int turn) {
		for (int x = 0; x < boardRowCount; x++)
			for (int y = 0; y < boardColCount; y++) {
				availableMoves[turn][y][x] = false;
				yellowSquares[turn][y][x].clear();
				moveList[turn][y][x] = null;
			}
	}

	protected abstract void Ri(Event event, int i1, int j1);

	public void setBoard(int i1, int j1, int k1) {
		yb_a[j1][i1] = k1;
		setChanged(i1, j1);
		invalidate();
	}

	protected void setChanged(int x, int y) {
		if (x >= 0)
			changed[y][x] = true;
	}

	public void setLastMove(int x, int y) {
		setChanged(lastX, lastY);
		lastX = x;
		lastY = y;
		setChanged(lastX, lastY);
		invalidate();
	}

	public void setMoveList(int x, int y, int turn, Tree<Square> moveList) {
		availableMoves[turn][y][x] = moveList != null
				&& moveList.childCount() > 0;
		yellowSquares[turn][y][x].clear();
		if (moveList != null) {
			this.moveList[turn][y][x] = moveList;
			for (int l1 = 0; l1 < moveList.childCount(); l1++) {
				Tree<Square> child = moveList.childAt(l1);
				Square square = (Square) child.getValue().clone();
				yellowSquares[turn][y][x].add(square);
			}
		}
		refresh();
	}

	public void setMyTurn(int value) {
		myTurn = value;
		refresh();
	}

	protected abstract void Wi(int i1, int j1);

	void xo(int i1, int j1) {
		if (currX != i1 || currY != j1) {
			setChanged(currX, currY);
			currX = i1;
			currY = j1;
			setChanged(currX, currY);
			invalidate();
		}
	}

	protected boolean zo(int i1, int j1) {
		return currX == i1
				&& currY == j1
				&& (k == -1 && hasAvailableMoves(i1, j1) || k != -1
						&& yb_c[j1][i1]) || i1 == k && j1 == l;
	}
}
