/**
 * 
 */
package common.k;

import y.utils.Square;

/**
 * @author saddam
 * 
 */
public final class EnglishCheckers extends Checkers {

	/*
	 * (white) 00 37 00 38 00 39 00 40 07 32 00 33 00 34 00 35 00 06 00 28 00 29
	 * 00 30 00 31 05 23 00 24 00 25 00 26 00 04 00 19 00 20 00 21 00 22 03 14
	 * 00 15 00 16 00 17 00 02 00 10 00 11 00 12 00 13 01 05 00 06 00 07 00 08
	 * 00 00
	 * 
	 * 07 06 05 04 03 02 01 00 (black)
	 */

	static int[][]	testBoard	= { { 0, 4, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } };

	public EnglishCheckers() {
		super();
		rowCount = 8;
		colCount = 8;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.BoardGame#doMove(int, int, int, int, int)
	 */
	@Override
	protected void doMove(int x1, int y1, int x2, int y2, int turn) {
		saveSquare(x1, y1);
		saveSquare(x2, y2);
		setBoard(x2, y2, getBoard(x1, y1));
		setBoard(x1, y1, 0);
		if (Math.abs(x1 - x2) >= 2) {
			int dx;
			int dy;
			if (x1 < x2)
				dx = 1;
			else
				dx = -1;
			if (y1 < y2)
				dy = 1;
			else
				dy = -1;
			int x = x1 + dx;
			int y = y1 + dy;
			boolean flag = false;
			do {
				if (getBoard(x, y) != 0) {
					saveSquare(x, y);
					setBoard(x, y, 0);
					flag = true;
				}
				x += dx;
				y += dy;
			}
			while (x != x2 && !flag);
			if (flag) {
				lastX1 = x1;
				lastY1 = y1;
				lastX2 = x2;
				lastY2 = y2;
				generateMoveList();
				if (capture[turn % 2])
					return;
			}
		}
		if ((turn == 1 && y2 == colCount - 1 || turn == 0 && y2 == 0)
				&& (getBoard(x2, y2) == 1 || getBoard(x2, y2) == 2))
			setBoard(x2, y2, getBoard(x2, y2) + 2);
		lastX2 = -1;
		generateMoveList();
		super.handler.handleLastMove(x2, y2);
		setTurn((turn + 1) % 2);
		int j2 = getCurrentTurn();
		if (!move[j2])
			doWon(turn, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.Checkers#fillBoard()
	 */
	@Override
	protected void fillBoard() {
		// setBoard(testBoard);
		int i1 = rowCount / 2 - 1;
		int i2 = rowCount - i1;
		int parity = 1;
		for (int i = 0; i < i1; i++) {
			fill(i, parity, 1);
			parity = 1 - parity;
		}
		for (int i = i1; i < i2; i++) {
			fill(i, parity, 0);
			parity = 1 - parity;
		}
		for (int i = i2; i < rowCount; i++) {
			fill(i, parity, 2);
			parity = 1 - parity;
		}
	}

	private void generateCaptureList(int value, Tree<Square> node) {
		generateCaptureList(value, node, 0, 0);
	}

	private void generateCaptureList(int value, Tree<Square> node, int ex_dx,
			int ex_dy) {
		// captura
		int x = node.value.getX();
		int y = node.value.getY();
		for (int dx = -1; dx < 2; dx += 2) {
			for (int dy = -1; dy < 2; dy += 2) {
				if (dx == ex_dx && dy == ex_dy)
					continue;
				if (!isValidPos(x + 2 * dx, y + 2 * dy))
					continue;
				if (value == 1 && dy == -1)
					continue;
				if (value == 2 && dy == 1)
					continue;
				if (isValidPos(x + dx * 2, y + dy * 2)
						&& getBoard(x + dx, y + dy) != 0
						&& getBoard(x + dx, y + dy) % 2 != value % 2
						&& getBoard(x + dx * 2, y + dy * 2) == 0) {

					move[value % 2] = true;
					capture[value % 2] = true;

					int oldx = x + dx;
					int oldy = y + dy;
					int oldValue = board[oldy][oldx];
					board[oldy][oldx] = 0;
					Tree<Square> node1 = new Tree<Square>(new Square(
							x + dx * 2, y + dy * 2));
					generateCaptureList(value, node1);
					board[oldy][oldx] = oldValue;
					node.addChild(node1);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.Checkers#generateMoveList1()
	 */
	@Override
	protected void generateHashMoves() {
		boolean[] flag = new boolean[] { false, false };
		for (int x = 0; x < rowCount; x++) {
			for (int y = 0; y < colCount; y++) {
				if (!isValidPos(x, y))
					continue;
				int value = getBoard(x, y);
				if (value == 0)
					continue;

				int dx = 0;
				int dy = 0;
				if (lastX2 != -1) {
					if (x != lastX2 || y != lastY2)
						continue;
					dx = lastX2 > lastX1 ? -1 : 1;
					dy = lastY2 > lastY1 ? -1 : 1;
				}
				board[y][x] = 0;
				Tree<Square> node = new Tree<Square>(new Square(x, y));
				generateCaptureList(value, node, dx, dy);
				board[y][x] = value;

				if (node.childCount() > 0) {
					if (!flag[value % 2]) {
						moveList[value % 2].clear();
						flag[value % 2] = true;
					}
					moveList[value % 2].addChild(node);
				}
				else if (!capture[value % 2])
					generateMoveList(moveList[value % 2], value, x, y);
			}
		}
	}

	public void generateMoveList(Tree<Square> result, int value, int x, int y) {
		Tree<Square> node = new Tree<Square>(new Square(x, y));
		if (lastX2 != -1)
			return;

		if (value == 2 || value > 2) {
			generateMoveList(node, value, x, y, 1, -1);
			generateMoveList(node, value, x, y, -1, -1);
		}
		if (value == 1 || value > 2) {
			generateMoveList(node, value, x, y, 1, 1);
			generateMoveList(node, value, x, y, -1, 1);
		}

		if (node.childCount() > 0)
			result.addChild(node);
	}

	public void generateMoveList(Tree<Square> result, int value, int x, int y,
			int dx, int dy) {
		// movimento simples
		if (!isValidPos(x + dx, y + dy))
			return;
		if (value == 1 && dy == -1)
			return;
		if (value == 2 && dy == 1)
			return;
		if (getBoard(x + dx, y + dy) == 0) {
			result.addChild(new Tree<Square>(new Square(x + dx, y + dy)));
			move[value % 2] = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.Checkers#getInitialTurn()
	 */
	@Override
	public int getInitialTurn() {
		return 1;
	}

	@Override
	public boolean isValidPos(int x, int y) {
		return super.isValidPos(x, y) && (x + y) % 2 == 1;
	}

}
