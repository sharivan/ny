// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;

// Referenced classes of package y.po:
// _cls78, _cls35, _cls116

public class _cls1 extends YahooComponent {

	int	e;
	int	f1;
	int	g1;
	int	h;
	int	i;
	int	j;
	int	k;
	int	l;
	int	m;
	int	n1;
	int	o1;
	int	p1;
	int	q1;
	int	r1;

	public _cls1(int i1) {
		p1 = 1;
		q1 = -1;
		r1 = -1;
		j = i1;
		setForeColor(Color.lightGray);
	}

	void a() {
		if (e > i - g1)
			e = i - g1;
		if (e < 0)
			e = 0;
	}

	Event b() {
		return new Event(this, 605, new Integer(e));
	}

	void c(YahooGraphics _pcls116) {
		e(_pcls116, 0, 15, q1 == 0);
		e(_pcls116, k - 15, 15, q1 == 1);
		for (int i1 = 0; i1 < 2; i1++) {
			_pcls116.setColor(Color.black);
			for (int j1 = 0; j1 < 4; j1++) {
				int k1 = 5 + j1;
				_pcls116.ou(0, 0, k1, 7 - j1, k1, 7 + j1, k, 15, j + 2 * i1);
			}

		}

	}

	public void d(YahooGraphics _pcls116, int i1, int j1, int k1, int l1,
			boolean flag) {
		_pcls116.setColor(Color.black);
		_pcls116.drawRect(i1, j1, k1 - 1, l1 - 1);
		for (int i2 = 0; i2 < 2; i2++) {
			_pcls116.setColor(flag ^ i2 % 2 == 0 ? Color.white : ln());
			_pcls116.ou(i1, j1, 1, 1, k1 - 2, 1, k1, l1, i2 * 2);
			_pcls116.ou(i1, j1, 1, 1, 1, l1 - 2, k1, l1, i2 * 2);
		}

	}

	void e(YahooGraphics _pcls116, int i1, int j1, boolean flag) {
		_pcls116.setColor(in());
		if (j == 0) {
			_pcls116.fillRect(i1, 0, j1, 15);
			d(_pcls116, i1, 0, j1, 15, flag);
		}
		else {
			_pcls116.fillRect(0, i1, 15, j1);
			d(_pcls116, 0, i1, 15, j1, flag);
		}
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		return h((j != 0 ? j1 : i1) - 15);
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		return j((j != 0 ? j1 : i1) - 15);
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		if (q1 != -1) {
			q1 = -1;
			invalidate();
		}
		else if (r1 != -1) {
			r1 = -1;
			invalidate();
		}
		return true;
	}

	void f(YahooGraphics _pcls116) {
		_pcls116.setColor(getForeColor());
		if (j == 1) {
			YahooButton.setGraphic(_pcls116, 0, 14, 14, l + 1);
			_pcls116.setColor(Color.black);
			_pcls116.drawRect(0, 14, 14, l + 1);
		}
		else {
			YahooButton.setGraphic(_pcls116, 14, 0, l + 1, 14);
			_pcls116.setColor(Color.black);
			_pcls116.drawRect(14, 0, l + 1, 14);
		}
		int i1;
		if (i == 0) {
			m = l;
			i1 = 0;
		}
		else {
			m = Math.min(l, g1 * l / i);
			if (r1 >= 0)
				i1 = Math.min(n1 + o1, l - m);
			else
				i1 = e * l / i;
		}
		if (m > l - i1)
			m = l - i1;
		if (m < 8)
			m = 8;
		if (e + g1 == i)
			i1 = l - m;
		if (i1 < 0)
			i1 = 0;
		if (m + i1 > l)
			i1 = l - m;
		e(_pcls116, 15 + i1, m, false);
		if (r1 == -1)
			n1 = i1;
	}

	public int g() {
		return e + h;
	}

	@Override
	public int getHeight1() {
		return j != 0 ? 38 : 15;
	}

	@Override
	public int getWidth1() {
		return j != 1 ? 38 : 15;
	}

	public boolean h(int i1) {
		Event event = null;
		if (i1 > n1 && i1 < n1 + m) {
			r1 = i1;
			f1 = n1;
		}
		else {
			r1 = -1;
			if (i1 < 0) {
				q1 = 0;
				e -= p1;
			}
			else if (i1 > l) {
				q1 = 1;
				e += p1;
			}
			else if (i1 < n1)
				e -= g1;
			else
				e += g1;
			a();
			event = b();
			invalidate();
		}
		if (event != null)
			doEvent(event);
		return true;
	}

	public boolean j(int i1) {
		Event event = null;
		if (r1 != -1 && l != 0) {
			o1 = i1 - r1;
			e = (f1 + o1) * i / l;
			a();
			event = b();
			invalidate();
		}
		if (event != null)
			doEvent(event);
		return true;
	}

	@Override
	public void m() {
		k = j != 0 ? super.height : super.width;
		l = k - 30;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		yahooGraphics.setColor(Color.black);
		f(yahooGraphics);
		c(yahooGraphics);
	}

	public void q(int i1) {
		p1 = i1;
	}

	public void r(int i1, int j1, int k1, int l1) {
		e = i1 + k1;
		g1 = j1;
		h = k1;
		i = l1 - k1;
		invalidate();
	}
}
