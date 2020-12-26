// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.dialogs;

import y.controls.YahooControl;

// Referenced classes of package y.po:
// _cls79, _cls78

public class YahooCustomDialog extends YahooControl {

	YahooControl	container;
	int				ycd_b;
	int				ycd_c;
	boolean			ycd_d;
	boolean			e;

	public YahooCustomDialog(YahooControl control) {
		super(true);
		e = false;
		container = control;
	}

	public void close() {
		container.removeChildObject(this);
	}

	@Override
	public void m() {
		super.m();
		if (!e) {
			int i = (container.getWidth() - getWidth()) / 2;
			int j = (container.getHeight() - getHeight()) / 2;
			if (i != super.left || j != super.top)
				super.setBounds(i, j, getWidth(), getHeight());
		}
	}

	public void Rw() {
		e = false;
	}

	public void show() {
		container.addChildObject(this, 0, 0, true);
	}
}
