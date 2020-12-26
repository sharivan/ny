// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

// Referenced classes of package y.po:
// _cls171, _cls130, _cls16, _cls46,
// _cls48

public class Obstacle {

	static int			m	= 1;
	public PoolPocket	pocket;
	public YIVector		c;
	public YIVector		d;
	public boolean		isBall;
	public PoolBall		ball;
	public PoolPocket	g;
	private int			h;
	private YIPoint		i;
	public int			num;
	public int			ai[];

	public Obstacle(int _ai[]) {
		d = new YIVector(0, -1);
		isBall = false;
		g = new PoolPocket();
		h = PoolMath.intToYInt(1000);
		i = new YIPoint();
		ai = new int[_ai.length];
		for (int i1 = 0; i1 < _ai.length; i1++)
			ai[i1] = _ai[i1];
		if (_ai[0] == 0) {
			isBall = true;
			ball = new PoolBall(_ai[1], _ai[2], _ai[3]);
		}
		else {
			pocket = new PoolPocket(PoolMath.intToYInt(_ai[1]), PoolMath
					.intToYInt(_ai[2]), PoolMath.intToYInt(_ai[3]), PoolMath
					.intToYInt(_ai[4]));
			YIVector _lcls48 = new YIVector(pocket.c - pocket.a, pocket.d
					- pocket.b);
			_lcls48.rotate(PoolMath.c);
			YIVector _lcls48_1 = new YIVector(PoolMath.intToYInt(_ai[5]),
					PoolMath.intToYInt(_ai[6]));
			if (_lcls48.zf(_lcls48_1) > PoolMath.c)
				_lcls48.neg2();
			c = _lcls48;
			c.versor();
		}
		num = m++;
	}

	public Obstacle(Obstacle obstacle) {
		this(obstacle.ai);
	}

	public YIVector Rl(IBall _pcls171) {
		return isBall ? Sl(_pcls171) : Tl(_pcls171);
	}

	public YIVector Sl(IBall _pcls171) {
		return new YIVector(ball, (YIPoint) _pcls171);
	}

	public YIVector Tl(IBall _pcls171) {
		return c;
	}

	@Override
	public String toString() {
		String s = "num " + num + " (";
		if (isBall)
			s = s + "(" + PoolMath.yintToFloat(((YIPoint) ball).a) + ","
					+ PoolMath.yintToFloat(((YIPoint) ball).b) + ") "
					+ PoolMath.yintToFloat(ball.radius) + ")";
		else
			s = s + "(" + PoolMath.yintToFloat(pocket.a) + ","
					+ PoolMath.yintToFloat(pocket.b) + ") ("
					+ PoolMath.yintToFloat(pocket.c) + ","
					+ PoolMath.yintToFloat(pocket.d) + "))";
		return s;
	}

	public int Ul(IBall _pcls171) {
		return isBall ? Vl(_pcls171) : Wl(_pcls171);
	}

	public int Vl(IBall _pcls171) {
		int i1 = _pcls171.timeToBall(ball);
		// if (i1 < PoolMath.n_1)
		// System.out.println(String.valueOf(ball) + " " + c);
		return i1;
	}

	public int Wl(IBall _pcls171) {
		int i1 = h;
		int x = _pcls171.getX();
		int y = _pcls171.getY();
		YIVector vel = _pcls171.getVel();
		g.Iw(x, y, x + vel.a, y + vel.b);
		if (vel.mul(c) > 0L)
			return i1;
		i1 /= 10;
		if (pocket.sw(g, i)) {
			int j1 = _pcls171.distance(i);
			int k1 = PoolMath.div(j1, vel.abs());
			if (k1 == PoolMath.n_1)
				k1 -= 2;
			i1 = PoolMath.div(j1, vel.abs());
		}
		return i1;
	}

}
