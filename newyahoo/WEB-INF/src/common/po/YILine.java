package common.po;

public class YILine {

	public YIPoint	x	= new YIPoint();
	public YIPoint	y	= new YIPoint();
	private int		a;
	private int		b;
	private int		c;

	public YILine() {
		setCoords(0, 0, 0, 0);
	}

	public YILine(YIPoint x, YIPoint y) {
		setCoords(x, y);
	}

	public int distance(YIPoint z) {
		int d = 0;
		a = x.distance(y);
		b = z.distance(y);
		c = z.distance(x);
		if (a > 0) {
			int p = PoolMath.div(a + b + c, PoolMath.n_2);
			int A = (int) PoolMath2.sqrt(PoolMath.mul(p, PoolMath.mul(p - a,
					PoolMath.mul(p - b, p - c))));
			d = PoolMath.div(PoolMath.mul(PoolMath.n_2, A), a);
		}
		else
			d = b;
		return d;
	}

	public void extendX(int d) {
		int l = lenght();
		if (l > 0) {
			int c = PoolMath.div(y.a - x.a, l);
			int s = PoolMath.div(y.b - x.b, l);
			x.setCoords(x.a - PoolMath.mul(l, c), x.b - PoolMath.mul(l, s));
		}
	}

	public void extendY(int d) {
		int l = lenght();
		if (l > 0) {
			int c = PoolMath.div(y.a - x.a, l);
			int s = PoolMath.div(y.b - x.b, l);
			x.setCoords(x.a + PoolMath.mul(l, c), x.b + PoolMath.mul(l, s));
		}
	}

	public boolean isNear() {
		return PoolMath.mul(b, b) <= PoolMath.mul(a, a) + PoolMath.mul(c, c)
				&& PoolMath.mul(c, c) <= PoolMath.mul(a, a)
						+ PoolMath.mul(b, b);
	}

	public int lenght() {
		return x.distance(y);
	}

	public void setCoords(int i, int j, int k, int l) {
		x.setCoords(i, j);
		y.setCoords(k, l);
	}

	public void setCoords(YIPoint x, YIPoint y) {
		setCoords(x.a, x.b, y.a, y.b);
	}

	@Override
	public String toString() {
		String s = x + " - " + y;
		return s;
	}

}
