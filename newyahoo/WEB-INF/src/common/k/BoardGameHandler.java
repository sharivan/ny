// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package common.k;

import y.utils.Square;

import common.yutils.GameHandler;

// Referenced classes of package y.k:
// _cls9, _cls138

public interface BoardGameHandler extends GameHandler {

	public abstract void handleChangeMoveList(int x, int y, int turn,
			Tree<Square> moveList);

	public abstract void handleChangeTurn(int i);

	public abstract void handleDeclineDraw(int i);

	public abstract void handleLastMove(int x, int y);

	public abstract void handleOfferDraw(int turn);

	/**
	 * @param turn
	 */
	public abstract void handleResetMoveList(int turn);

	public abstract void handleSetBoard(int i, int j, int k);

	/**
	 * 
	 */
	public abstract void handleUndo();

	public abstract void handleWon(int wonTurn, boolean flag);

	public abstract void Lc(int i, int j);
}
