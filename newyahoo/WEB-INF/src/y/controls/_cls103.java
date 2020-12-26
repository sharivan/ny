// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;

// Referenced classes of package y.po:
// _cls78, _cls116, _cls123

public class _cls103 extends YahooComponent {

	public YahooList	a103;

	public _cls103(YahooList _pcls123) {
		super(true);
		a103 = _pcls123;
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		a103.canvasMouseDown(event, j, k + a103.d123);
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int j, int k) {
		a103.canvasMouseUp(event, j, k + a103.d123);
		return true;
	}

	@Override
	public int getHeight1() {
		return a103.Ds();
	}

	@Override
	public int getWidth1() {
		return a103.Es();
	}

	@Override
	public void m() {
		super.m();
		a103.Us(super.width, super.height);
		a103.g123 = super.height;
		a103.h = super.width;
		a103._v();
		a103.Yu();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		yahooGraphics.left -= a103.f123;
		yahooGraphics.top -= a103.d123;
		a103.paint(yahooGraphics, a103.f123, a103.f123 + a103.h, a103.d123,
				a103.d123 + a103.g123);
	}
}
