// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package common.k;

public interface TimeredGame {

	public abstract int getCurrentTurn();

	public abstract int getTimePerMove();

	public abstract boolean handleTimeEmpty(int turn);
}
