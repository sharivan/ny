// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import y.controls.YahooControl;

// Referenced classes of package y.po:
// _cls79, _cls78

public class _cls114 extends YahooControl {

	public _cls114() {
	}

	@Override
	public void m() {
		super.m();
		setCoords((getParent().getWidth() - getWidth1()) / 2, (getParent()
				.getHeight() - getHeight1()) / 2);
	}
}
