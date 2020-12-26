// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

// Referenced classes of package y.po:
// _cls91, _cls57, _cls89, _cls124,
// _cls171, _cls100, _cls120, _cls16,
// _cls46

public final class NineBallSetup extends PoolSetup implements PoolConsts {

	protected static YIPoint	initPos[];
	static {
		initPos = new YIPoint[10];
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
		initPos[5] = new YIPoint(PoolMath.intToYInt(469), PoolMath
				.intToYInt(149));
		initPos[6] = new YIPoint(PoolMath.intToYInt(431), PoolMath
				.intToYInt(172));
		initPos[7] = new YIPoint(PoolMath.intToYInt(450), PoolMath
				.intToYInt(137));
		initPos[8] = new YIPoint(PoolMath.intToYInt(450), PoolMath
				.intToYInt(161));
		initPos[9] = new YIPoint(PoolMath.intToYInt(431), PoolMath
				.intToYInt(149));
	}
	private IBall				c;
	private int					d;
	public int					e;
	public int					f;
	public int					g;

	int							i;

	public NineBallSetup(Pool _pcls57) {
		super(_pcls57);
		d = 0;
		e = 1;
		i = 0;
	}

	@Override
	public boolean ap() {
		return true;
	}

	@Override
	public YRectangle getAimBallInitArea() {
		return (YRectangle) PoolSetup.getProperty("AIM_BALL_INIT_AREA");
	}

	@Override
	public int getBallCount() {
		return 10;
	}

	@Override
	public YIPoint getInitPos(int j) {
		if (j >= initPos.length)
			return new YIPoint(0, 0);
		return initPos[j];
	}

	@Override
	public int getSlotIndex() {
		return -1;
	}

	@Override
	public int getState() {
		boolean flag2 = false;
		boolean flag3 = false;
		if (isFaul() || whiteBallPocketed()) {
			if (super.pool.m_turn == 0) {
				if (f == 2) {
					super.b = false;
					flag2 = true;
				}
				else {
					f++;
					super.turnChanged = true;
				}
			}
			else if (g == 2) {
				super.b = false;
				flag2 = true;
			}
			else {
				g++;
				super.turnChanged = true;
			}
			flag3 = true;
		}
		else {
			if (super.pool.m_turn == 0)
				f = 0;
			else
				g = 0;
			if (super.pool.pocketed) {
				if (super.pool.getBall(9).inSlot()) {
					flag2 = true;
					super.b = true;
				}
				super.turnChanged = false;
			}
			else {
				super.turnChanged = true;
			}
		}
		if (flag2)
			return 1;
		if (flag3) {
			if (super.pool.getBall(9).inSlot())
				super.pool
						.Sj(super.pool.getBall(9), initPos[9].a, initPos[9].b);
			if (c.inSlot() || super.pool.yj())
				super.pool
						.Sj(super.pool.getBall(0), initPos[0].a, initPos[0].b);
			super.pool.Wj(true);
		}
		else {
			super.pool.Wj(false);
		}
		return 0;
	}

	@Override
	public IBall getWhiteBall() {
		return c;
	}

	@Override
	public void initializeWB() {
		c = super.pool.getBall(d);
		f = 0;
		g = 0;
	}

	@Override
	public boolean isFaul() {
		if (!pool.turnCollided)
			return true;
		if (!pool.sideCollided && !pool.pocketed)
			return true;
		return pool.firstCollidedBall.getIndex() != e;
	}

	@Override
	public boolean isWhiteBall(IBall _pcls124) {
		return _pcls124.equals(c);
	}

	@Override
	public boolean nl() {
		return true;
	}

	@Override
	public boolean ql(IBall _pcls124) {
		return isWhiteBall(_pcls124) && super.pool.getAimStateInit();
	}

	@Override
	public void read(DataInput input) throws IOException {
		f = input.readInt();
		g = input.readInt();
	}

	@Override
	public void So() {
		e = Uo();
	}

	@Override
	public int Uo() {
		IBall a_lcls124[] = super.pool.getBall();
		for (int j = 0; j < getBallCount(); j++) {
			IBall _lcls124 = a_lcls124[j];
			if (!_lcls124.equals(c) && !_lcls124.inSlot())
				return _lcls124.getIndex();
		}

		return 0;
	}

	@Override
	public boolean whiteBallPocketed() {
		return super.pool.turnPocketed.contains(c);
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeInt(f);
		output.writeInt(g);
	}
}
