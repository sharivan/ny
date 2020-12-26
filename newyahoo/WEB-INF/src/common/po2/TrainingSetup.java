// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

import java.util.Vector;

// Referenced classes of package y.po:
// _cls91, _cls57, _cls89, _cls124,
// _cls171, _cls100, _cls120, _cls16,
// _cls46

public final class TrainingSetup extends PoolSetup implements PoolConsts {

	protected static YPoint	initPos[];
	static {
		initPos = new YPoint[16];
		initPos[0] = new YPoint(154, 148);
		initPos[1] = new YPoint(393, 149);
		initPos[2] = new YPoint(412, 160);
		initPos[3] = new YPoint(412, 138);
		initPos[4] = new YPoint(431, 126);
		initPos[5] = new YPoint(431, 172);
		initPos[6] = new YPoint(431, 149);
		initPos[7] = new YPoint(450, 137);
		initPos[8] = new YPoint(450, 161);
		initPos[9] = new YPoint(450, 115);
		initPos[10] = new YPoint(450, 183);
		initPos[11] = new YPoint(469, 126);
		initPos[12] = new YPoint(469, 172);
		initPos[13] = new YPoint(469, 103);
		initPos[14] = new YPoint(469, 195);
		initPos[15] = new YPoint(469, 149);
	}

	private IBall			whiteBall;

	public TrainingSetup(Pool _pcls57) {
		super(_pcls57);
		_p();
	}

	@Override
	public YRectangle getAimBallInitArea() {
		return (YRectangle) PoolSetup.getProperty("AIM_BALL_INIT_AREA");
	}

	@Override
	public int getBallCount() {
		return 16;
	}

	@Override
	public YPoint getInitPos(int i) {
		if (i >= initPos.length)
			return new YPoint(0, 0);
		return initPos[i];
	}

	@Override
	public int getSlotIndex() {
		int i = pool.m_turn != 0 ? pool.type1 : pool.type0;
		if (i == 0)
			return -1;
		Vector<IBall> vector = pool.getBallInPlayArea();
		for (int j = 0; j < vector.size(); j++) {
			IBall _lcls124 = vector.elementAt(j);
			if (_lcls124.getType() == i)
				return -1;
		}

		int k = (int) Math.random() * 6 + 1;
		return k;
	}

	@Override
	public int getState() {
		super.turnChanged = false;
		return 0;
	}

	@Override
	public IBall getWhiteBall() {
		return whiteBall;
	}

	@Override
	public boolean isFaul() {
		if (pool.firstCollidedBall == null)
			return true;
		int i = pool.m_turn != 0 ? pool.type1 : pool.type0;
		if (i == 0)
			return false;
		return pool.firstCollidedBall.getType() != i;
	}

	@Override
	public boolean isWhiteBall(IBall _pcls124) {
		return true;
	}

	@Override
	public boolean nl() {
		return false;
	}

	@Override
	public boolean ql(IBall _pcls124) {
		return true;
	}

	@Override
	public boolean whiteBallPocketed() {
		return pool.turnPocketed.contains(whiteBall);
	}
}
