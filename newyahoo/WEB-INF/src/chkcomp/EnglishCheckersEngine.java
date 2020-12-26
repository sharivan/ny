/**
 * 
 */
package chkcomp;

import java.util.Vector;

import y.utils.Square;

import common.k.BoardSquare;
import common.k.LinkedList;
import common.k.Tree;

/**
 * @author saddam
 * 
 */
public class EnglishCheckersEngine {

	static final int		MAXDEPTH			= 50;
	static final int		rowCount			= 8;
	static final int		colCount			= 8;
	static int				alphabetas;
	public static boolean	playNow;

	static final int[]		value				= { 0, 16, 1, 4096, 256 };

	static final int[]		edgex				= { 7, 5, 3, 1, 0, 7, 0, 7, 0,
			7, 6, 4, 2, 0						};

	static final int[]		edgey				= { 0, 0, 0, 0, 1, 2, 3, 4, 5,
			6, 7, 7, 7, 7						};

	static final int[]		centerx				= { 5, 3, 4, 2, 5, 3, 4, 2 };

	static final int[]		centery				= { 2, 2, 3, 3, 4, 4, 5, 5 };

	static final int[]		safeedgex			= { 1, 0, 7, 6 };

	static final int[]		safeedgey			= { 0, 1, 6, 7 };

	static final int		turn				= 2;							// color

	// to
	// move
	// gets
	// +turn
	static final int		brv					= 3;							// multiplier

	// for
	// back
	// rank
	static final int		kcv					= 5;							// multiplier

	// for
	// kings
	// in
	// center
	static final int		mcv					= 1;							// multiplier
	// for
	// men
	// in
	// center

	static final int		mev					= 1;							// multiplier
	// for
	// men
	// on
	// edge
	static final int		kev					= 5;							// multiplier
	// for
	// kings
	// on
	// edge
	static final int		cramp				= 5;							// multiplier
	// for
	// cramp
	static final int		opening				= -2;							// multipliers
	// for
	// tempo
	static final int		midgame				= -1;
	static final int		endgame				= 2;
	static final int		intactdoublecorner	= 3;

	private static int alphabeta(int[][] b, int depth, int alpha, int beta,
			int turn) {
		boolean capture;
		Tree<Square> movelist;

		// System.out.println("alphabeta(" + depth + ", " + alpha + ", " + beta
		// + ", " + turn + ")");
		// System.out.println("b=");
		// System.out.println(BoardGame.boardToString(b));

		if (playNow)
			return 0;

		alphabetas++;

		/* ----------> test if captures are possible */
		capture = testcapture(b, turn);

		/* ----------> recursion termination if no captures and depth=0 */
		if (depth == 0) {
			if (!capture)
				return evaluation(b, turn);
			depth = 1;
		}

		/* ----------> generate all possible moves in the position */
		if (!capture) {
			movelist = generatemovelist(b, turn);
			/* ----------> if there are no possible moves, we lose: */
			if (movelist.childCount() == 0) {
				if (turn == 1)
					return -5000;
				return 5000;
			}
		}
		else
			movelist = generatecapturelist(b, turn);

		for (int i = 0; i < movelist.childCount(); i++) {
			Tree<Square> node = movelist.childAt(i);
			for (LinkedList<Square> move = node.first(); node.hasMoreElements(); move = node
					.next()) {
				int x = move.getValue().getX();
				int y = move.getValue().getY();
				int v = b[y][x];
				Vector<BoardSquare> changedSquare = new Vector<BoardSquare>();
				changedSquare.add(new BoardSquare(x, y, v));
				// int[][] b1 = BoardGame.cloneBoard(b);
				domove(changedSquare, b, move, turn);
				int value = alphabeta(b, depth - 1, alpha, beta, 1 - turn);
				undomove(b, changedSquare);
				b[y][x] = v;
				/*
				 * if(!BoardGame.boardEquals(b, b1)) {
				 * System.out.println("**********ERROR ON RESTORE BOARD*********"
				 * ); System.out.println(BoardGame.boardToString(b1));
				 * System.out
				 * .println("*****************************************");
				 * System.out.println(BoardGame.boardToString(b));
				 * System.out.println
				 * ("*****************************************"); }
				 */
				if (turn == 1) {
					if (value >= beta)
						return value;
					if (value > alpha)
						alpha = value;
				}
				else if (turn == 0) {
					if (value <= alpha)
						return value;
					if (value < beta)
						beta = value;
				}
			}
		}
		if (turn == 1)
			return alpha;
		return beta;
	}

	@SuppressWarnings("unchecked")
	public static LinkedList<Square> bestMove(int[][] b, int turn,
			long maxtime, String[] str) {
		LinkedList<Square>[] best = new LinkedList[1];
		EnglishCheckersEngine.checkers(b, turn, maxtime, str, best);
		return best[0];
	}

	private static int checkers(int b[][], int turn, long maxtime,
			String[] str, LinkedList<Square>[] best)
	/*
	 * ----------> purpose: entry point to checkers. find a move on board b for
	 * color ----------> in the time specified by maxtime, write the best move
	 * in ----------> board, returns information on the search in str
	 * ----------> returns 1 if a move is found & executed, 0, if there is no
	 * legal ----------> move in this position. ----------> version: 1.1
	 * ----------> date: 9th october 98
	 */
	{
		int i;
		long start;
		int eval;
		LinkedList<Square> lastbest = null;
		Tree<Square> movelist;

		alphabetas = 0;

		/* --------> check if there is only one move */
		movelist = generatecapturelist(b, turn);
		if (movelist.childCount() == 0)
			movelist = generatemovelist(b, turn);
		/*
		 * set the best move to default movelist[0] - if it's a forced move we
		 * will return immediately
		 */
		// setbestmove(movelist[0]);
		/*
		 * if(movelist.childCount()==1) { int x1 = movelist
		 * domove(b,movelist[0]); str = "forced move";return(1); }
		 */
		if (movelist.childCount() == 0) {
			str[0] = "no legal moves in this position";
			return 0;
		}

		start = System.currentTimeMillis();
		eval = firstalphabeta(b, 1, -10000, 10000, turn, best);
		for (i = 2; i <= MAXDEPTH
				&& System.currentTimeMillis() - start < maxtime; i++) {
			lastbest = best[0];
			eval = firstalphabeta(b, i, -10000, 10000, turn, best);
			str[0] = "best: " + best[0].toString() + " time "
					+ (System.currentTimeMillis() - start) + ", depth " + i
					+ ", value " + eval + ", evals " + alphabetas;
			if (playNow)
				break;
			if (eval == 5000)
				break;
			if (eval == -5000)
				break;
		}
		i--;
		if (playNow) {
			if (lastbest != null)
				str[0] = lastbest.toString();
		}
		else
			str[0] = best[0].toString();

		str[0] = "best: " + str[0] + " time "
				+ (System.currentTimeMillis() - start) + ", depth " + i
				+ ", value " + eval + ", evals " + alphabetas;

		if (playNow)
			best[0] = lastbest;

		// domove(b,best);

		/* set the CBmove */
		// setbestmove(best);
		return eval;
	}

	public static void domove(Vector<BoardSquare> result, int[][] b,
			LinkedList<Square> move, int turn) {
		LinkedList<Square> next = move.getNext();

		Square square = move.getValue();
		int x1 = square.getX();
		int y1 = square.getY();
		int value = b[y1][x1];

		if (next == null) {
			result.add(new BoardSquare(x1, y1, 0));
			if ((turn == 1 && y1 == colCount - 1 || turn == 0 && y1 == 0)
					&& (b[y1][x1] == 1 || b[y1][x1] == 2))
				b[y1][x1] += 2;
			return;
		}

		Square nextSquare = next.getValue();
		int x2 = nextSquare.getX();
		int y2 = nextSquare.getY();

		b[y1][x1] = 0;
		b[y2][x2] = value;
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
			do {
				if (b[y][x] != 0) {
					result.add(new BoardSquare(x, y, b[y][x]));
					b[y][x] = 0;
					break;
				}
				x += dx;
				y += dy;
			}
			while (x != x2);
		}

		domove(result, b, next, turn);
	}

	private static int evaluation(int[][] b, int turn) {
		int eval;
		int v1, v2;
		int nbm = 0;
		int nbk = 0;
		int nwm = 0;
		int nwk = 0;
		int nbmc = 0, nbkc = 0, nwmc = 0, nwkc = 0;
		int nbme = 0, nbke = 0, nwme = 0, nwke = 0;
		int code = 0;

		int tempo = 0;
		int nm;
		// int nk;

		int backrank;

		// int stonesinsystem = 0;

		/*
		 * #ifdef STATISTICS evaluations++; #endif
		 */

		for (int i = 0; i < rowCount; i++)
			for (int j = 0; j < colCount; j++) {
				if ((i + j) % 2 == 0)
					continue;
				// code += value[b[j][i]];
				switch (b[j][i]) {
				case 1:
					nbm++;
					break;
				case 2:
					nwm++;
					break;
				case 3:
					nbk++;
					break;
				case 4:
					nwk++;
					break;
				}
			}

		/*
		 * nwm = code % 16; nwk = (code >> 4) % 16; nbm = (code >> 8) % 16; nbk
		 * = (code >> 12) % 16;
		 */

		v1 = 100 * nbm + 130 * nbk;
		v2 = 100 * nwm + 130 * nwk;

		eval = v1 - v2; /* material values */
		eval += 250 * (v1 - v2) / (v1 + v2); /*
											 * favor exchanges if in material
											 * plus
											 */

		nm = nbm + nwm;
		// nk = nbk + nwk;
		/* --------- fine evaluation below ------------- */

		if (turn == 1)
			eval += turn;
		else
			eval -= turn;
		/*
		 * (white) 00 37 00 38 00 39 00 40 07 32 00 33 00 34 00 35 00 06 00 28
		 * 00 29 00 30 00 31 05 23 00 24 00 25 00 26 00 04 00 19 00 20 00 21 00
		 * 22 03 14 00 15 00 16 00 17 00 02 00 10 00 11 00 12 00 13 01 05 00 06
		 * 00 07 00 08 00 00
		 * 
		 * 07 06 05 04 03 02 01 00 (black)
		 */
		/* cramp */
		if (b[4][7] == 1 && b[5][6] == 2)
			eval += cramp;
		if (b[3][0] == 2 && b[2][1] == 1)
			eval -= cramp;

		/* back rank guard */

		code = 0;
		if (b[0][7] == 1)
			code++;
		if (b[0][5] == 1)
			code += 2;
		if (b[0][3] == 1)
			code += 4;
		if (b[0][1] == 1)
			code += 8;
		switch (code) {
		case 0:
			code = 0;
			break;
		case 1:
			code = -1;
			break;
		case 2:
			code = 1;
			break;
		case 3:
			code = 0;
			break;
		case 4:
			code = 1;
			break;
		case 5:
			code = 1;
			break;
		case 6:
			code = 2;
			break;
		case 7:
			code = 1;
			break;
		case 8:
			code = 1;
			break;
		case 9:
			code = 0;
			break;
		case 10:
			code = 7;
			break;
		case 11:
			code = 4;
			break;
		case 12:
			code = 2;
			break;
		case 13:
			code = 2;
			break;
		case 14:
			code = 9;
			break;
		case 15:
			code = 8;
			break;
		}
		backrank = code;

		code = 0;
		if (b[7][6] == 2)
			code += 8;
		if (b[7][4] == 2)
			code += 4;
		if (b[7][2] == 2)
			code += 2;
		if (b[7][0] == 2)
			code++;
		switch (code) {
		case 0:
			code = 0;
			break;
		case 1:
			code = -1;
			break;
		case 2:
			code = 1;
			break;
		case 3:
			code = 0;
			break;
		case 4:
			code = 1;
			break;
		case 5:
			code = 1;
			break;
		case 6:
			code = 2;
			break;
		case 7:
			code = 1;
			break;
		case 8:
			code = 1;
			break;
		case 9:
			code = 0;
			break;
		case 10:
			code = 7;
			break;
		case 11:
			code = 4;
			break;
		case 12:
			code = 2;
			break;
		case 13:
			code = 2;
			break;
		case 14:
			code = 9;
			break;
		case 15:
			code = 8;
			break;
		}
		backrank -= code;
		eval += brv * backrank;

		/*
		 * (white) 00 37 00 38 00 39 00 40 07 32 00 33 00 34 00 35 00 06 00 28
		 * 00 29 00 30 00 31 05 23 00 24 00 25 00 26 00 04 00 19 00 20 00 21 00
		 * 22 03 14 00 15 00 16 00 17 00 02 00 10 00 11 00 12 00 13 01 05 00 06
		 * 00 07 00 08 00 00
		 * 
		 * 07 06 05 04 03 02 01 00 (black)
		 */
		/* intact double corner */
		if (b[0][1] == 1) {
			if (b[1][2] == 1 || b[1][0] == 1)
				eval += intactdoublecorner;
		}

		if (b[7][6] == 2) {
			if (b[6][7] == 2 || b[6][5] == 2)
				eval -= intactdoublecorner;
		}

		/* center control */
		for (int i = 0; i < centerx.length; i++)
			if (b[centery[i]][centerx[i]] != 0) {
				if (b[centery[i]][centerx[i]] == 1)
					nbmc++;
				if (b[centery[i]][centerx[i]] == 3)
					nbkc++;
				if (b[centery[i]][centerx[i]] == 2)
					nwmc++;
				if (b[centery[i]][centerx[i]] == 4)
					nwkc++;
			}
		eval += (nbmc - nwmc) * mcv;
		eval += (nbkc - nwkc) * kcv;

		/* edge */
		for (int i = 0; i < edgex.length; i++)
			if (b[edgey[i]][edgex[i]] != 0) {
				if (b[edgey[i]][edgex[i]] == 1)
					nbme++;
				if (b[edgey[i]][edgex[i]] == 3)
					nbke++;
				if (b[edgey[i]][edgex[i]] == 2)
					nwme++;
				if (b[edgey[i]][edgex[i]] == 4)
					nwke++;
			}
		eval -= (nbme - nwme) * mev;
		eval -= (nbke - nwke) * kev;

		/* tempo */
		for (int i = 0; i < rowCount; i++)
			for (int j = 0; j < colCount; j++) {
				if ((i + j) % 2 == 0)
					continue;
				if (b[j][i] == 1)
					tempo += j;
				if (b[j][i] == 2)
					tempo -= 7 - j;
			}

		if (nm >= 16)
			eval += opening * tempo;
		if (nm <= 15 && nm >= 12)
			eval += midgame * tempo;
		if (nm < 9)
			eval += endgame * tempo;

		for (int i = 0; i < safeedgex.length; i++) {
			if (nbk + nbm > nwk + nwm && nwk < 3) {
				if (b[safeedgey[i]][safeedgex[i]] == 4)
					eval -= 15;
			}
			if (nwk + nwm > nbk + nbm && nbk < 3) {
				if (b[safeedgey[i]][safeedgex[i]] == 3)
					eval += 15;
			}
		}

		return eval;
	}

	private static int firstalphabeta(int[][] b, int depth, int alpha,
			int beta, int turn, LinkedList<Square>[] bestMove) {
		/*
		 * ----------> purpose: search the game tree and find the best move.
		 * ----------> version: 1.0 ----------> date: 25th october 97
		 */
		boolean capture;
		Tree<Square> movelist;

		// System.out.println("firstalphabeta(" + depth + ", " + alpha + ", " +
		// beta + ", " + turn + ")");
		// System.out.println("b=");
		// System.out.println(BoardGame.boardToString(b));

		if (playNow)
			return 0;
		/* ----------> test if captures are possible */
		capture = testcapture(b, turn);

		/* ----------> recursion termination if no captures and depth=0 */
		if (depth == 0) {
			if (!capture)
				return evaluation(b, turn);
			depth = 1;
		}

		/* ----------> generate all possible moves in the position */
		if (!capture) {
			movelist = generatemovelist(b, turn);
			/* ----------> if there are no possible moves, we lose: */
			if (movelist.childCount() == 0) {
				if (turn == 1)
					return -5000;
				return 5000;
			}
		}
		else
			movelist = generatecapturelist(b, turn);

		for (int i = 0; i < movelist.childCount(); i++) {
			Tree<Square> node = movelist.childAt(i);
			for (LinkedList<Square> move = node.first(); node.hasMoreElements(); move = node
					.next()) {
				int x = move.getValue().getX();
				int y = move.getValue().getY();
				int v = b[y][x];
				Vector<BoardSquare> changedSquare = new Vector<BoardSquare>();
				changedSquare.add(new BoardSquare(x, y, v));
				// int[][] b1 = BoardGame.cloneBoard(b);
				domove(changedSquare, b, move, turn);
				int value = alphabeta(b, depth - 1, alpha, beta, 1 - turn);
				undomove(b, changedSquare);
				b[y][x] = v;
				/*
				 * if(!BoardGame.boardEquals(b, b1)) {
				 * System.out.println("**********ERROR ON RESTORE BOARD*********"
				 * ); System.out.println(BoardGame.boardToString(b1));
				 * System.out
				 * .println("*****************************************");
				 * System.out.println(BoardGame.boardToString(b));
				 * System.out.println
				 * ("*****************************************"); }
				 */
				if (turn == 1) {
					if (value >= beta)
						return value;
					if (value > alpha) {
						alpha = value;
						bestMove[0] = move;
					}
				}
				else if (turn == 0) {
					if (value <= alpha)
						return value;
					if (value < beta) {
						beta = value;
						bestMove[0] = move;
					}
				}
			}
		}
		if (turn == 1)
			return alpha;
		return beta;
	}

	public static Tree<Square> generatecapturelist(int[][] b, int turn) {
		Tree<Square> result = new Tree<Square>();
		for (int x = 0; x < rowCount; x++)
			for (int y = 0; y < colCount; y++) {
				if (!isValidPos(x, y))
					continue;
				if (b[y][x] == 0 || b[y][x] % 2 != turn)
					continue;
				Tree<Square> result1 = new Tree<Square>(new Square(x, y));
				generatecapturelist(b, result1, 0, 0, turn);
				if (result1.childCount() > 0)
					result.addChild(result1);
			}
		return result;
	}

	private static void generatecapturelist(int[][] b, Tree<Square> node,
			int ex_dx, int ex_dy, int turn) {
		int x = node.getValue().getX();
		int y = node.getValue().getY();
		int value = b[y][x];
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
						&& b[y + dy][x + dx] != 0
						&& b[y + dy][x + dx] % 2 != value % 2
						&& b[y + 2 * dy][x + 2 * dx] == 0) {

					int oldx = x + dx;
					int oldy = y + dy;
					int oldValue = b[oldy][oldx];
					b[oldy][oldx] = 0;
					Tree<Square> node1 = new Tree<Square>(new Square(
							x + dx * 2, y + dy * 2));
					generatecapturelist(b, node1, 0, 0, turn);
					b[oldy][oldx] = oldValue;
					node.addChild(node1);
				}
			}
		}
	}

	public static Tree<Square> generatemovelist(int[][] b, int turn) {
		Tree<Square> result = new Tree<Square>();
		for (int x = 0; x < rowCount; x++)
			for (int y = 0; y < colCount; y++) {
				if (!isValidPos(x, y))
					continue;
				if (b[y][x] == 0 || b[y][x] % 2 != turn)
					continue;
				Tree<Square> result1 = new Tree<Square>(new Square(x, y));
				generatemovelist(b, result1, turn);
				if (result1.childCount() > 0)
					result.addChild(result1);
			}
		return result;
	}

	private static void generatemovelist(int[][] b, Tree<Square> node, int turn) {
		int x = node.getValue().getX();
		int y = node.getValue().getY();
		int value = b[y][x];
		for (int dx = -1; dx < 2; dx += 2)
			for (int dy = -1; dy < 2; dy += 2) {
				if (!isValidPos(x + dx, y + dy))
					continue;
				if (value == 1 && dy == -1)
					continue;
				if (value == 2 && dy == 1)
					continue;
				if (b[y + dy][x + dx] == 0)
					node.addChild(new Tree<Square>(new Square(x + dx, y + dy)));
			}
	}

	public static boolean isValidPos(int x, int y) {
		return x >= 0 && y >= 0 && x < rowCount && y < colCount;
	}

	private static boolean testcapture(int[][] b, int turn) {
		for (int x = 0; x < rowCount; x++)
			for (int y = 0; y < colCount; y++) {
				if (!isValidPos(x, y))
					continue;
				if (b[y][x] == 0 || b[y][x] % 2 != turn)
					continue;
				if (testcapture(b, x, y, turn))
					return true;
			}
		return false;
	}

	private static boolean testcapture(int[][] b, int x, int y, int turn) {
		int value = b[y][x];
		for (int dx = -1; dx < 2; dx += 2) {
			for (int dy = -1; dy < 2; dy += 2) {
				if (!isValidPos(x + 2 * dx, y + 2 * dy))
					continue;
				if (value == 1 && dy == -1)
					continue;
				if (value == 2 && dy == 1)
					continue;
				if (isValidPos(x + dx * 2, y + dy * 2)
						&& b[y + dy][x + dx] != 0
						&& b[y + dy][x + dx] % 2 != value % 2
						&& b[y + 2 * dy][x + 2 * dx] == 0)
					return true;
			}
		}
		return false;
	}

	private static void undomove(int[][] b, Vector<BoardSquare> changedSquare) {
		for (int i = 0; i < changedSquare.size(); i++) {
			BoardSquare square = changedSquare.elementAt(i);
			int x = square.getX();
			int y = square.getY();
			int value = square.getValue();
			b[y][x] = value;
		}
	}

}
