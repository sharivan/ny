// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;
import java.util.Vector;

// Referenced classes of package y.po:
// _cls123, _cls78, _cls79, _cls116

public final class _cls126 extends YahooList {

	private int				a126;
	private int				b126;
	Vector<YahooComponent>	c126;
	private int				d126;

	public _cls126() {
		this(100);
	}

	public _cls126(int i) {
		super(true);
		a126 = 0;
		b126 = 0;
		c126 = new Vector<YahooComponent>();
		d126 = i;
	}

	@Override
	public void canvasMouseDown(Event event, int i, int j) {
		// System.out.println("canvasMouseDown(" + event + ", " + i + ", " + j
		// + ")");
		event.x = i;
		event.y = j;
	}

	@Override
	public void canvasMouseUp(Event event, int i, int j) {
		// System.out
		// .println("canvasMouseUp(" + event + ", " + i + ", " + j + ")");
		event.x = i;
		event.y = j;
	}

	@Override
	public YahooComponent componentAtPos(int i, int j) {
		if (lw(i, j) == super.c123) {
			j += super.d123;
			for (int k = 0; k < c126.size(); k++) {
				YahooComponent _lcls78 = c126.elementAt(k);
				if (_lcls78.row <= j && _lcls78.row + _lcls78.height >= j) {
					_lcls78.left = 0;
					_lcls78.top = _lcls78.row - super.d123;
					return _lcls78.componentAtPos(i, j - _lcls78.top
							- super.d123);
				}
			}

		}
		return super.componentAtPos(i, j);
	}

	@Override
	public void Dn() {
		for (int i = 0; i < c126.size(); i++) {
			YahooComponent _lcls78 = c126.elementAt(i);
			_lcls78.Dn();
		}

		super.Dn();
	}

	@Override
	public int Ds() {
		return d126;
	}

	@Override
	public void En() {
		for (int i = 0; i < c126.size(); i++) {
			YahooComponent _lcls78 = c126.elementAt(i);
			_lcls78.En();
		}

		Xu();
		super.En();
	}

	@Override
	public int Es() {
		return a126;
	}

	public void ew(YahooComponent _pcls78) {
		fw(_pcls78, c126.size());
	}

	public void fw(YahooComponent _pcls78, int i) {
		if (i > c126.size())
			i = c126.size();
		c126.insertElementAt(_pcls78, i);
		_pcls78.parent = this;
		_pcls78.J = false;
		if (((YahooComponent) this).invalidated) {
			int j = 0;
			for (int k = 0; k < i; k++) {
				YahooComponent _lcls78 = c126.elementAt(k);
				j += _lcls78.getHeight1();
			}

			_pcls78.realingChilds();
			_pcls78.width = _pcls78.getWidth1();
			_pcls78.height = _pcls78.getHeight1();
			_pcls78.m();
			_pcls78.col = 0;
			_pcls78.row = j;
			cv(j, _pcls78.getHeight1());
			b126 += _pcls78.getHeight1();
			if (_pcls78.getWidth1() > a126)
				a126 = _pcls78.getWidth1();
			kw();
		}
	}

	public void jw(int i) {
		if (i > c126.size())
			return;
		YahooComponent _lcls78 = c126.elementAt(i);
		c126.remove(i);
		if (((YahooComponent) this).invalidated) {
			super.Zu(_lcls78.row, _lcls78.height);
			b126 -= _lcls78.getHeight1();
			_lcls78.En();
		}
		invalidate();
	}

	private void kw() {
		int i = 0;
		for (int j = 0; j < c126.size(); j++) {
			YahooComponent _lcls78 = c126.elementAt(j);
			_lcls78.width = a126;
			_lcls78.col = 0;
			_lcls78.row = i;
			_lcls78.m();
			i += _lcls78.height;
		}

	}

	public YahooComponent lw(int i, int j) {
		return super.componentAtPos(i, j);
	}

	@Override
	public void paint(YahooGraphics graphics, int i, int j, int k, int l) {
		int i1 = 0;
		int k1 = graphics.top;
		for (int l1 = 0; l1 < c126.size(); l1++) {
			YahooComponent _lcls78 = c126.elementAt(l1);
			graphics.top = k1 + i1;
			int i2 = _lcls78.getHeight1();
			boolean flag = i1 + i2 >= k;
			boolean flag1 = i1 < l;
			if (flag1 && flag)
				_lcls78.paint(graphics);
			i1 += i2;
		}

	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		int i = 0;
		for (int j = 0; j < c126.size(); j++) {
			YahooComponent _lcls78 = c126.elementAt(j);
			_lcls78.realingChilds();
			_lcls78.width = _lcls78.getWidth1();
			_lcls78.height = _lcls78.getHeight1();
			_lcls78.m();
			_lcls78.col = 0;
			_lcls78.row = i;
			cv(i, _lcls78.getHeight1());
			b126 += _lcls78.getHeight1();
			i += _lcls78.getHeight1();
			if (_lcls78.getWidth1() > a126)
				a126 = _lcls78.getWidth1();
		}

		kw();
	}

	@Override
	public void vb() {
		super.c123.z = true;
		super.vb();
	}
}
