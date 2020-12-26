// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

// Referenced classes of package y.po:
// _cls81, _cls66, _cls65

public class _cls83 {

	public static ObjectManager	manager	= new ObjectManager(new _cls83(), 8);

	public static _cls83 Fo(_cls65 _pcls65, int i, Object obj) {
		_cls83 _lcls83 = (_cls83) manager.allocate();
		_lcls83.b = _pcls65;
		_lcls83.c = i;
		_lcls83.d = obj;
		return _lcls83;
	}

	public _cls65	b;
	public int		c;

	public Object	d;

	public _cls83() {
	}

}
