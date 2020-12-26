// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import y.controls.YahooComponent;

// Referenced classes of package y.po:
// _cls75, _cls78, _cls79

public class ComponentSort extends Sort<YahooComponent> {

	public ComponentSort() {
	}

	@Override
	public int compare(YahooComponent obj, YahooComponent obj1) {
		if (obj.rowCount < obj1.rowCount)
			return -1;
		return obj.rowCount != obj1.rowCount ? 1 : 0;
	}
}
