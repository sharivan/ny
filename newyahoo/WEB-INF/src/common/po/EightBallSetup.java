// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import java.util.Vector;

// Referenced classes of package y.po:
// _cls91, _cls57, _cls89, _cls124,
// _cls171, _cls100, _cls120, _cls16,
// _cls46

public final class EightBallSetup extends PoolSetup implements PoolConsts {

	protected static YIPoint	initPos[];
	static {
		initPos = new YIPoint[16];
		initPos[0] = new YIPoint(PoolMath.intToYInt(154), PoolMath
				.intToYInt(148));
		initPos[1] = new YIPoint(PoolMath.intToYInt(393), PoolMath
				.intToYInt(149));
		initPos[2] = new YIPoint(PoolMath.intToYInt(412), PoolMath
				.intToYInt(160));
		initPos[3] = new YIPoint(PoolMath.intToYInt(412), PoolMath
				.intToYInt(138));
		initPos[4] = new YIPoint(PoolMath.intToYInt(431), PoolMath
				.intToYInt(126));
		initPos[5] = new YIPoint(PoolMath.intToYInt(431), PoolMath
				.intToYInt(172));
		initPos[6] = new YIPoint(PoolMath.intToYInt(431), PoolMath
				.intToYInt(149));
		initPos[7] = new YIPoint(PoolMath.intToYInt(450), PoolMath
				.intToYInt(137));
		initPos[8] = new YIPoint(PoolMath.intToYInt(450), PoolMath
				.intToYInt(161));
		initPos[9] = new YIPoint(PoolMath.intToYInt(450), PoolMath
				.intToYInt(115));
		initPos[10] = new YIPoint(PoolMath.intToYInt(450), PoolMath
				.intToYInt(183));
		initPos[11] = new YIPoint(PoolMath.intToYInt(469), PoolMath
				.intToYInt(126));
		initPos[12] = new YIPoint(PoolMath.intToYInt(469), PoolMath
				.intToYInt(172));
		initPos[13] = new YIPoint(PoolMath.intToYInt(469), PoolMath
				.intToYInt(103));
		initPos[14] = new YIPoint(PoolMath.intToYInt(469), PoolMath
				.intToYInt(195));
		initPos[15] = new YIPoint(PoolMath.intToYInt(469), PoolMath
				.intToYInt(149));
	}
	private IBall				whiteBall;
	private int					whiteBallIndex;
	private IBall				blackBall;
	private int					blackBallIndex;

	int							h;

	public EightBallSetup(Pool _pcls57) {
		super(_pcls57);
		whiteBallIndex = 0;
		blackBallIndex = 6;
		h = 0;
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
	public YIPoint getInitPos(int i) {
		if (i >= initPos.length)
			return new YIPoint(0, 0);
		return initPos[i];
	}

	@Override
	public int getSlotIndex() {
		int i = pool.m_turn != 0 ? pool.type1 : pool.type0;
		if (i == 0)
			return -1;
		Vector<IBall> vector = pool.getBallInPlayArea();
		if (!vector.contains(blackBall))
			return -1;
		for (int j = 0; j < vector.size(); j++) {
			IBall _lcls124 = vector.elementAt(j);
			if (_lcls124.getType() == i)
				return -1;
		}

		return 1;
	}

	@Override
	public int getState() {
		boolean flag = true;
		boolean flag1 = true;
		boolean flag2 = false;
		boolean flag3 = false;
		if (isFaul() || whiteBallPocketed())
			flag3 = true;
		int i = pool.m_turn != 0 ? pool.type1 : pool.type0;
		if (pool.pocketed)
			if (pool.turnPocketed.size() == 1) {
				if (pool.turnPocketed.firstElement().equals(whiteBall)) {
					super.turnChanged = flag1;
					pool.Sj(pool.getBall(0), initPos[0].a, initPos[0].b);
					pool.Wj(true);
					return 0;
				}
			}
			else if (whiteBall.inSlot())
				pool.turnPocketed.removeElement(whiteBall);
		if (pool.pocketed)
			if (pool.type0 != 0) {
				if (pool.turnPocketed.contains(blackBall)) {
					if (!typePocketed(i) && pool.turnCollided
							&& pool.firstCollidedBall.getType() == 0) {
						boolean flag4 = false;
						for (int j = 0; j < pool.turnPocketed.size(); j++) {
							IBall _lcls124 = pool.turnPocketed.elementAt(j);
							if (_lcls124.getType() != i)
								continue;
							flag4 = true;
							break;
						}

						flag2 = true;
						if (pool.selectedSlotIndex != blackBall.getSlot())
							pool.Vj();
						flag = !flag4
								&& pool.isInSlot(whiteBall)
								&& pool.selectedSlotIndex == blackBall
										.getSlot();
					}
					else {
						flag2 = true;
						flag = pool.firstStrike;
					}
				}
				else if (pool.turnCollided
						&& pool.firstCollidedBall.getType() == i) {
					boolean flag5 = false;
					for (int k = 0; k < pool.turnPocketed.size(); k++) {
						IBall _lcls124_1 = pool.turnPocketed.elementAt(k);
						if (_lcls124_1.getType() != i)
							continue;
						flag5 = true;
						break;
					}

					if (flag5)
						flag1 = !pool.isInSlot(whiteBall);
				}
			}
			else if (pool.turnPocketed.contains(blackBall)) {
				flag2 = true;
				flag = false;
			}
			else {
				boolean flag6 = true;
				int l = pool.turnPocketed.firstElement().getType();
				for (int i1 = 0; i1 < pool.turnPocketed.size(); i1++) {
					IBall _lcls124_2 = pool.turnPocketed.elementAt(i1);
					int j1 = _lcls124_2.getType();
					if (j1 == 0 || j1 == l)
						continue;
					flag6 = false;
					break;
				}

				if (flag6) {
					char c1 = l != 1024 ? '\u0400' : '\u0800';
					if (!pool.firstStrike && pool.firstCollidedBall != null
							&& !pool.firstCollidedBall.equals(blackBall))
						pool.Xj(pool.m_turn != 0 ? (int) c1 : l);
				}
				else {
					return 2;
				}
				flag1 = !pool.isInSlot(whiteBall)
						|| pool.firstCollidedBall.equals(blackBall);
			}
		super.turnChanged = flag1;
		super.b = flag;
		if (flag2)
			return 1;
		if (flag3) {
			if (whiteBall.inSlot() || pool.yj())
				pool.Sj(pool.getBall(0), initPos[0].a, initPos[0].b);
			pool.Wj(true);
		}
		else {
			pool.Wj(false);
		}
		return 0;
	}

	@Override
	public IBall getWhiteBall() {
		return whiteBall;
	}

	@Override
	public void initializeWB() {
		whiteBall = pool.getBall(whiteBallIndex);
		blackBall = pool.getBall(blackBallIndex);
	}

	@Override
	public boolean isFaul() {
		if (!pool.turnCollided)
			return true;
		int i = pool.m_turn != 0 ? pool.type1 : pool.type0;
		if (pool.firstCollidedBall.equals(blackBall))
			return typePocketed(i);
		if (i == 0 || pool.x)
			return false;
		return pool.firstCollidedBall.getType() != i;
	}

	@Override
	public boolean isWhiteBall(IBall _pcls124) {
		return _pcls124.equals(whiteBall);
	}

	@Override
	public boolean nl() {
		return true;
	}

	@Override
	public boolean ql(IBall _pcls124) {
		return isWhiteBall(_pcls124) && pool.getAimStateInit();
	}

	private boolean typePocketed(int type) {
		IBall a_lcls124[] = pool.getBall();
		for (int j = 0; j < a_lcls124.length; j++)
			if (!a_lcls124[j].inSlot() && a_lcls124[j].getType() == type)
				return true;

		return false;
	}

	@Override
	public boolean whiteBallPocketed() {
		return pool.turnPocketed.contains(whiteBall);
	}
}
