// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111, _cls164, _cls68, _cls48,
// _cls46, _cls33, _cls57, _cls121

public interface IBall extends YData {

	public abstract Vel Bv();

	public abstract void checkSlots(Slot a_pcls164[]);

	public abstract IBall Copy();

	public abstract int distance(int i, int j);

	public abstract int distance(YIPoint _pcls46);

	public abstract int getIndex();

	public abstract int getRadius();

	public abstract byte getSlot();

	public abstract int getType();

	public abstract YIVector getVel();

	public abstract int getX();

	public abstract int getY();

	public abstract float getYIntX();

	public abstract float getYIntY();

	public abstract boolean inSlot();

	public abstract boolean isInitPos();

	public abstract boolean isMoving();

	public abstract boolean Jv();

	public abstract boolean Kv(int i);

	public abstract void Mv(Slot _pcls164);

	public abstract void nextPosition();

	public abstract void nv();

	public abstract void ov(IBall _pcls124, YIVector _pcls48);

	public abstract void pv();

	public abstract void Pv(boolean flag);

	public abstract void reset();

	public abstract void resetBall();

	public abstract void setCoords(int i, int j);

	public abstract void setHandler(PoolHandler _pcls121);

	public abstract void setNull();

	public abstract void setPool(PoolParams _pcls57);

	public abstract void setPos(int i, int j);

	public abstract void setRadius(int i);

	public abstract void setSlot(byte byte0);

	public abstract void start(YIPoint _pcls46, YIPoint _pcls46_1,
			YIPoint _pcls46_2, int i);

	public abstract void stop();

	public abstract int timeToBall(IBall _pcls124);

	public abstract int timeToObstacle(Obstacle a_pcls68[]);

	public abstract String toString();

	public abstract void uncolide();

	public abstract int vv();
}
