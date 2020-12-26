// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import java.util.Vector;

import common.io.YData;
import common.yutils.GameHandler;

// Referenced classes of package y.po:
// _cls12, _cls48, _cls33, _cls111,
// _cls124

public interface PoolHandler extends GameHandler {

	public abstract void Ad();

	public abstract void Bd(int i);

	public abstract Vector<IBall> getBallInPlayArea();

	public abstract YRectangle getBounceArea();

	public abstract YIPoint getCenterPoint();

	public abstract YRectangle getInArea();

	public abstract int getLinearFriction();

	public abstract YRectangle getPlayArea();

	public abstract YRectangle getPocketArea();

	public abstract int getRotationFriction();

	public abstract int getSideRotationFriction();

	public abstract void handleColl(int i);

	public abstract void handleFirtsColl(IBall ball);

	public abstract void handleIterate();

	public abstract void handleSetPos(int i, int j, int k, int l,
			YIVector _pcls48, Vel _pcls33);

	public abstract void handleShiftFromIntersect();

	public abstract void handleStopMoving();

	public abstract void handleUpdateCue(int i);

	public abstract void handleUpdateEnglish(int i);

	public abstract void kd(int i);

	public abstract void logState(String s);

	public abstract void nd(YData _pcls111);

	public abstract void qd(IBall _pcls124);

	public abstract void rd();

	public abstract void Sc();

	public abstract void selectSlot(int i);

	public abstract void td();

	public abstract String Xc(int i);

	public abstract void xd(int i);

	public abstract void zd(int i, boolean flag);
}
