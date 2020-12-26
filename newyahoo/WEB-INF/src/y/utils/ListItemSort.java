// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import y.controls.ListItem;

// Referenced classes of package y.po:
// _cls75, _cls155

public abstract class ListItemSort extends Sort<ListItem> {

	public ListItemSort() {
	}

	public abstract void setValue(ListItem item, int value, Object obj);
}
