// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

// Referenced classes of package y.po:
// _cls171, _cls130, _cls16, _cls46,
// _cls48

public class Obstacle {

	static int			m	= 1;
	public LineSegment	segment;
	public YVector		c;
	public YVector		d;
	public boolean		isBall;
	public PoolBall		ball;
	public LineSegment	segment1;
	private float		h;
	private YPoint		interceptPoint;
	public int			num;
	public float		ai[];

	public Obstacle(float _ai[]) {
		d = new YVector(0, -1);
		isBall = false;
		segment1 = new LineSegment();
		h = 1000;
		interceptPoint = new YPoint();
		ai = new float[_ai.length];
		for (int i1 = 0; i1 < _ai.length; i1++)
			ai[i1] = _ai[i1];
		if (_ai[0] == 0) {
			isBall = true;
			ball = new PoolBall(_ai[1], _ai[2], _ai[3]);
		}
		else {
			segment = new LineSegment(_ai[1], _ai[2], _ai[3], _ai[4]);
			YVector _lcls48 = new YVector(segment.x2 - segment.x1, segment.y2
					- segment.y1);
			_lcls48.rotate(Math.PI / 2);
			YVector _lcls48_1 = new YVector(_ai[5], _ai[6]);
			if (_lcls48.angle(_lcls48_1) > Math.PI / 2)
				_lcls48.neg();
			c = _lcls48;
			c.versor();
		}
		num = m++;
	}

	public Obstacle(Obstacle obstacle) {
		this(obstacle.ai);
	}

	public YVector Rl(IBall _pcls171) {
		return isBall ? Sl(_pcls171) : Tl(_pcls171);
	}

	public YVector Sl(IBall _pcls171) {
		return new YVector(ball, (YPoint) _pcls171);
	}

	public float timeFromBallToBall(IBall _pcls171) {
		float i1 = _pcls171.timeToBall(ball);
		// if (i1 < 1)
		// System.out.println(String.valueOf(ball) + " " + c);
		return i1;
	}

	public float timeFromSegmentToBall(IBall _pcls171) {
		float i1 = h;
		float x = _pcls171.getX();
		float y = _pcls171.getY();
		YVector vel = _pcls171.getVel();
		segment1.setCoords(x, y, x + vel.x, y + vel.y);
		if (vel.mul(c) > 0L)
			return i1;
		i1 /= 10;
		if (segment.intercept(segment1, interceptPoint)) {
			float d = _pcls171.distance(interceptPoint);
			float t = d / vel.abs();
			if (t == 1F / 65536F)
				t -= 2F / 65536F;
			i1 = d / vel.abs();
		}
		return i1;
	}

	public float timeToBall(IBall _pcls171) {
		return isBall ? timeFromBallToBall(_pcls171)
				: timeFromSegmentToBall(_pcls171);
	}

	public YVector Tl(IBall _pcls171) {
		return c;
	}

	@Override
	public String toString() {
		String s = "num " + num + " (";
		if (isBall)
			s = s + "(" + ball.x + "," + ball.y + ") " + ball.radius + ")";
		else
			s = s + "(" + segment.x1 + "," + segment.y1 + ") (" + segment.x2
					+ "," + segment.y2 + "))";
		return s;
	}

}
