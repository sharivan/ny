// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111

public interface GameHandler {

	public abstract void handleStart();

	public abstract void handleStartTick(int time);

	public abstract void handleStop(YData data);

	public abstract void handleStopTick();

	public abstract void handleUpdateStatus(boolean flag);
}
