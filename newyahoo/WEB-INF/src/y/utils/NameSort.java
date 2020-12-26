// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import y.controls.ListItem;

// Referenced classes of package y.po:
// _cls32, _cls30, _cls155, _cls44

public class NameSort extends ListItemSort {

	public NameSort() {
	}

	@Override
	public int compare(ListItem item, ListItem item1) {
		int i = item.ay();
		int j = item.colls[i].compareTo(item1.colls[i]);
		if (j == 0) {
			if (item.index == item1.index)
				return 0;
			return item.index <= item1.index ? -1 : 1;
		}
		return j;
	}

	@Override
	public void setValue(ListItem item, int value, Object obj) {
	}
}
