// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package common.k;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;

import y.utils.Square;
import chkcomp.EnglishCheckersEngine;

import common.yutils.GameHandler;

// Referenced classes of package y.k:
// _cls127, _cls81, _cls111, _cls66,
// _cls9

public abstract class Checkers extends BoardGame {

	public static String getVariant(Hashtable<String, String> properties) {
		if (properties.containsKey("variant"))
			return properties.get("variant");
		return null;
	}

	boolean				c;
	protected boolean	move[];
	protected boolean	capture[];
	protected int		lastX1;
	protected int		lastY1;
	protected int		lastX2;
	protected int		lastY2;
	int					currX1;
	int					currY1;
	int					currX2;
	int					currY2;
	int					newPiece;
	int					oldPiece;

	public Checkers() {
		super();
		c = false;
		move = new boolean[2];
		capture = new boolean[2];
		currX1 = -1;
	}

	public LinkedList<Square> bestMove(int turn, long maxtime, String[] str) {
		int[][] board1 = board.clone();
		EnglishCheckersEngine.playNow = false;
		return EnglishCheckersEngine.bestMove(board1, turn, maxtime, str);
	}

	@Override
	public void close() {
		move = null;
		capture = null;
	}

	@Override
	public void configDoStart() {
		reset();
		fillBoard();
		setTurn(getInitialTurn());
		lastX2 = -1;
		generateMoveList();
	}

	@Override
	public boolean drawExist() {
		return true;
	}

	protected void fill(int row, int parity, int value) {
		for (int l1 = 0; l1 < colCount / 2; l1++)
			setBoard(parity + l1 * 2, row, value);

	}

	protected abstract void fillBoard();

	protected abstract void generateHashMoves();

	public void generateMoveList() {
		move[0] = move[1] = false;
		capture[0] = capture[1] = false;
		for (Tree<Square> element : moveList)
			element.clear();
		generateHashMoves();
		// System.out.println(this);
		// if (capture[0] || capture[1])
		// generateHashMoves();
		flushMoveList(-2);
	}

	public abstract int getInitialTurn();

	@Override
	public int getTimePerMove() {
		return 5;
	}

	@Override
	public void initializeProperties(Hashtable<String, String> hashtable,
			GameHandler handler) {
		this.handler = (BoardGameHandler) handler;
		makeBoard(rowCount, colCount, 2, this.handler, BoardGame
				.isTraining(hashtable));
	}

	@Override
	public boolean isRated() {
		return true;
	}

	public void playNow() {
		EnglishCheckersEngine.playNow = true;
	}

	@Override
	public void read(DataInput input) throws IOException {
		try {
			boolean flag = input.readBoolean();
			running = false;
			if (flag) {
				byte turn = input.readByte();
				int x = input.readByte();
				int y = input.readByte();
				int sitWaitingForDraw = input.readByte();
				if (x != -1) {
					if (x < 0 || x >= rowCount || y < 0 || y >= colCount)
						throw new IllegalState("Illegal state");
					if ((x + y) % 2 == 0)
						throw new IllegalState("Illegal state");
				}
				if (turn < 0 || turn >= 2)
					throw new IllegalState("Illegal state");
				if ((sitWaitingForDraw < 0 || sitWaitingForDraw >= 2)
						&& sitWaitingForDraw != -3)
					throw new IllegalState("Illegal state");
				reset();
				this.sitWaitingForDraw = sitWaitingForDraw;
				running = true;
				setTurn(turn);
				lastX2 = x;
				lastY2 = y;
				for (int l1 = 0; l1 < colCount; l1++) {
					for (int i2 = 0; i2 < rowCount; i2++) {
						byte byte1 = input.readByte();
						if (byte1 > 0 && (l1 + i2) % 2 == 0)
							throw new IllegalState("Illegal state");
						if (byte1 > 4)
							throw new IllegalState("Illegal state");
						setBoard(i2, l1, byte1);
					}

				}

				generateMoveList();
			}
		}
		catch (IOException ioexception) {
			setTurn(-3);
			running = false;
			throw ioexception;
		}
	}

	@Override
	public void restoreStateIfChanged() {
		if (currX1 != -1) {
			if (getBoard(currX1, currY1) != newPiece)
				super.handler.handleSetBoard(currX1, currY1, getBoard(currX1,
						currY1));
			if (getBoard(currX2, currY2) != oldPiece)
				super.handler.handleSetBoard(currX2, currY2, getBoard(currX2,
						currY2));
		}
		currX1 = -1;
	}

	@Override
	public void saveState(int x1, int y1, int x2, int y2, int turn) {
		restoreStateIfChanged();
		currX1 = x1;
		currY1 = y1;
		currX2 = x2;
		currY2 = y2;
		newPiece = 0;
		oldPiece = getBoard(currX1, currY1);
		super.handler.handleSetBoard(x1, y1, 0);
		super.handler.handleSetBoard(x2, y2, oldPiece);
	}

	@Override
	public void setup() {
		running = false;
		for (int l1 = 0; l1 < rowCount; l1++)
			for (int i2 = 0; i2 < colCount; i2++)
				setBoard(l1, i2, 0);
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeBoolean(running);
		if (running) {
			output.writeByte(currTurn);
			output.writeByte(lastX2);
			output.writeByte(lastY2);
			output.writeByte(super.sitWaitingForDraw);
			for (int l1 = 0; l1 < colCount; l1++)
				for (int i2 = 0; i2 < rowCount; i2++)
					output.writeByte(board[l1][i2]);
		}
	}

}
