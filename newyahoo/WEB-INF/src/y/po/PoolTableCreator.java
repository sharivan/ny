// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import y.dialogs.YahooDialog;
import y.ycontrols.TableCreator;

// Referenced classes of package y.po:
// _cls97, _cls5, _cls137, _cls133,
// _cls27

public class PoolTableCreator extends TableCreator {

	YahooDialog	a;

	public PoolTableCreator() {
	}

	@Override
	public void cancel() {
		a.close();
		super.cancel();
	}

	@Override
	public void createDialog() {
		a = new PoolTableCreatorDialog(this, super.container);
	}
}
