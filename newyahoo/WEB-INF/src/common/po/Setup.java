// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111, _cls124, _cls120, _cls46

public interface Setup extends YData {

	public abstract boolean ap();

	public abstract Object get(String s);

	public abstract YRectangle getAimBallInitArea();

	public abstract int getBallCount();

	public abstract YIPoint getInitPos(int i);

	public abstract int getSlotIndex();

	public abstract int getState();

	public abstract IBall getWhiteBall();

	public abstract void initializeWB();

	public abstract boolean isFaul();

	public abstract boolean isTurnChanged();

	public abstract boolean isWhiteBall(IBall _pcls124);

	public abstract boolean nl();

	public abstract boolean ql(IBall _pcls124);

	public abstract void So();

	public abstract int Uo();

	public abstract boolean whiteBallPocketed();

	public abstract boolean Zo();
}
