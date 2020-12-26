// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111, _cls164, _cls68, _cls48,
// _cls46, _cls33, _cls57, _cls121

public interface IBall extends YData {

	public abstract Vel Bv();

	public abstract void checkSlots(Slot a_pcls164[]);

	public abstract IBall Copy();

	public abstract float distance(float i, float j);

	public abstract float distance(YPoint _pcls46);

	public abstract int getIndex();

	public abstract float getRadius();

	public abstract byte getSlot();

	public abstract int getType();

	public abstract YVector getVel();

	public abstract float getX();

	public abstract float getY();

	public abstract boolean inSlot();

	public abstract boolean isInitPos();

	public abstract boolean isMoving();

	public abstract boolean Jv();

	public abstract boolean Kv(float i);

	public abstract void nextPosition();

	public abstract void ov(IBall _pcls124, YVector _pcls48);

	public abstract void putOnSlot(Slot _pcls164);

	public abstract void pv();

	public abstract void Pv(boolean flag);

	public abstract void reset();

	public abstract void resetBall();

	public abstract void resetMsgList();

	public abstract void setCoords(float i, float j);

	public abstract void setHandler(PoolHandler _pcls121);

	public abstract void setNull();

	public abstract void setPool(PoolParams _pcls57);

	public abstract void setPos(float i, float j);

	public abstract void setRadius(float i);

	public abstract void setSlot(byte byte0);

	public abstract void start(YPoint _pcls46, YPoint _pcls46_1,
			YPoint _pcls46_2, int i);

	public abstract void stop();

	public abstract float timeToBall(IBall _pcls124);

	public abstract float timeToObstacle(Obstacle a_pcls68[]);

	public abstract String toString();

	public abstract void uncolide();

	public abstract int vv();
}
