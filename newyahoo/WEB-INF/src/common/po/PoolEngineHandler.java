// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls124

public interface PoolEngineHandler {

	public abstract void handleCollBalls(IBall _pcls124, IBall _pcls124_1);

	public abstract void handleIterate();

	public abstract void handlePocket(IBall _pcls124);

	public abstract void handleShiftFromIntersect();

	public abstract void handleSideColl(IBall _pcls124);

	public abstract void handleStop();

}
