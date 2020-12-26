/**
 * 
 */
package common.k;

import y.utils.Square;

/**
 * @author saddam
 * 
 */
public class RussianCheckers extends Checkers {

	static int[][]	testBoard	= { { 0, 4, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } };

	public RussianCheckers() {
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
		setBoard(x2, y2, getBoard(x1, y1));
		setBoard(x1, y1, 0);

		if ((turn == 1 && y2 == colCount - 1 || turn == 0 && y2 == 0)
				&& getBoard(x2, y2) > 0 && getBoard(x2, y2) <= 2)
			setBoard(x2, y2, getBoard(x2, y2) + 2);

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
		// setBoard(initialBoard);
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

	private boolean generateCaptureList(int value, Tree<Square> node,
			int ex_dx, int ex_dy) {
		// captura
		int x = node.getValue().getX();
		int y = node.getValue().getY();
		boolean flag = false;
		for (int dx = -1; dx < 2; dx += 2) {
			for (int dy = -1; dy < 2; dy += 2) {
				if (dx == ex_dx && dy == ex_dy)
					continue;
				if (!isValidPos(x + 2 * dx, y + 2 * dy))
					continue;
				if (value == 1 || value == 2) {
					if (isValidPos(x + dx * 2, y + dy * 2)
							&& getBoard(x + dx, y + dy) != 0
							&& getBoard(x + dx, y + dy) % 2 != value % 2
							&& getBoard(x + dx * 2, y + dy * 2) == 0) {

						move[value % 2] = true;
						capture[value % 2] = true;
						flag = true;

						int oldx = x + dx;
						int oldy = y + dy;
						int oldValue = board[oldy][oldx];
						board[oldy][oldx] = 0;
						Tree<Square> node1 = new Tree<Square>(new Square(x + dx
								* 2, y + dy * 2));
						int value1 = value;
						if (value1 % 2 == 1 && y + dy * 2 == colCount - 1
								|| value1 % 2 == 0 && y + dy * 2 == 0)
							value1 += 2;
						generateCaptureList(value1, node1);
						board[oldy][oldx] = oldValue;
						node.addChild(node1);
					}
				}
				else if (value == 3 || value == 4) {
					int i = 1;
					int x1 = x + dx;
					int y1 = y + dy;
					while (isValidPos(x1, y1) && getBoard(x1, y1) == 0) {
						i++;
						x1 = x + i * dx;
						y1 = y + i * dy;
					}

					int oldx1 = x1;
					int oldy1 = y1;
					if (isValidPos(x1, y1) && getBoard(x1, y1) % 2 != value % 2) {
						i++;
						x1 = x + i * dx;
						y1 = y + i * dy;

						if (isValidPos(x1, y1) && getBoard(x1, y1) == 0) {
							do {
								move[value % 2] = true;
								capture[value % 2] = true;
								flag = true;

								int oldValue = board[oldy1][oldx1];
								board[oldy1][oldx1] = 0;
								Tree<Square> node1 = new Tree<Square>(
										new Square(x1, y1));
								generateCaptureList(value, node1, -dx, -dy);
								board[oldy1][oldx1] = oldValue;

								node.addChild(node1);

								i++;
								x1 = x + i * dx;
								y1 = y + i * dy;
							}
							while (isValidPos(x1, y1) && getBoard(x1, y1) == 0);
						}
					}
				}
			}
		}
		return flag;
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
				// addHashMove(x, y, -2);
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
				boolean flag1 = generateCaptureList(value, node, dx, dy);
				board[y][x] = value;

				if (flag1) {
					if (!flag[value % 2]) {
						flag[value % 2] = true;
						moveList[value % 2].clear();
					}
					moveList[value % 2].addChild(node);
				}
				else if (!capture[value % 2])
					generateMoveList(moveList[value % 2], value, x, y);
			}
		}
	}

	public boolean generateMoveList(Tree<Square> result, int value, int x, int y) {
		Tree<Square> node = new Tree<Square>(new Square(x, y));
		if (lastX2 != -1)
			return false;

		boolean flag = false;
		if (value == 2 || value > 2) {
			flag |= generateMoveList(node, value, x, y, 1, -1);
			flag |= generateMoveList(node, value, x, y, -1, -1);
		}
		if (value == 1 || value > 2) {
			flag |= generateMoveList(node, value, x, y, 1, 1);
			flag |= generateMoveList(node, value, x, y, -1, 1);
		}

		if (flag)
			result.addChild(node);
		return flag;
	}

	public boolean generateMoveList(Tree<Square> result, int value, int x,
			int y, int dx, int dy) {
		// movimento simples
		if (!isValidPos(x + dx, y + dy))
			return false;
		if (value == 1 || value == 2) {
			if (value == 1 && dy == -1)
				return false;
			if (value == 2 && dy == 1)
				return false;
			if (getBoard(x + dx, y + dy) == 0) {
				result.addChild(new Tree<Square>(new Square(x + dx, y + dy)));
				move[value % 2] = true;
				return true;
			}
		}
		else if (value == 3 || value == 4) {
			int i = 1;
			int x1 = x + dx;
			int y1 = y + dy;
			boolean flag = false;
			while (isValidPos(x1, y1) && getBoard(x1, y1) == 0) {
				result.addChild(new Tree<Square>(new Square(x1, y1)));
				move[value % 2] = true;
				flag = true;
				i++;
				x1 = x + i * dx;
				y1 = y + i * dy;
			}
			return flag;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.Checkers#getInitialTurn()
	 */
	@Override
	public int getInitialTurn() {
		return 0;
	}

	@Override
	public boolean isValidPos(int x, int y) {
		return super.isValidPos(x, y) && (x + y) % 2 == 1;
	}

}
