// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import y.controls.YahooComponent;
import y.controls.YahooControl;

// Referenced classes of package y.po:
// _cls79, _cls78

public class _cls139 extends YahooControl {

	YahooControl	control;

	public _cls139(int i, int j, int k, int l) {
		control = new YahooControl();
		super.addChildObject(control, 10, 1, 1, 1, 1, 0, 1, i, j, k, l, false,
				false);
	}

	@Override
	public void addChildObject(YahooComponent component, int i, int j, int k,
			int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2,
			boolean flag, boolean flag1) {
		control.addChildObject(component, i, j, k, l, i1, j1, k1, l1, i2, j2,
				k2, flag, flag1);
	}
}
