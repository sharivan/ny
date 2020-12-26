// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

import java.util.Vector;

// Referenced classes of package y.po:
// _cls63, _cls65, _cls83, _cls66

public class _cls81 {

	Vector<_cls83>	a;
	boolean			b;
	YahooVector		yahooVector;

	public _cls81() {
		a = new Vector<_cls83>();
		b = false;
		yahooVector = new YahooVector();
	}

	public synchronized void Ao(_cls65 _pcls65, int i, Object obj) {
		_cls83 _lcls83 = _cls83.Fo(_pcls65, i, obj);
		a.add(_lcls83);
	}

	public void Bo() {
		synchronized (this) {
			if (b)
				return;
			yahooVector.clear();
			yahooVector.assign(a);
			a.clear();
		}
		for (int i = 0; i < yahooVector.size(); i++) {
			_cls83 _lcls83 = (_cls83) yahooVector.elementAt(i);
			_lcls83.b.process(_lcls83.c, _lcls83.d);
			_lcls83.d = null;
			_lcls83.b = null;
			_cls83.manager.deallocate(_lcls83);
		}

	}

	public synchronized void Co() {
		b = true;
	}
}
