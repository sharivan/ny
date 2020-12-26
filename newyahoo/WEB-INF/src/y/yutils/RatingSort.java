// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import y.controls.ListItem;
import y.utils.Id;
import y.utils.ListItemSort;

// Referenced classes of package y.po:
// _cls32, _cls56, _cls74, _cls49,
// _cls155

public class RatingSort extends ListItemSort {

	boolean					reverse;
	boolean					useProvisionalColor;
	CustomYahooGamesApplet	applet;

	public RatingSort(boolean flag, boolean flag1, CustomYahooGamesApplet applet) {
		reverse = flag;
		useProvisionalColor = flag1;
		this.applet = applet;
	}

	@Override
	public int compare(ListItem item, ListItem item1) {
		Id id = (Id) item.obj;
		Id id1 = (Id) item1.obj;
		int i = item.ay();
		int result;
		if (i == 0) {
			result = AbstractYahooGamesApplet.compareIdsByRating(id, id1, item,
					item1);
		}
		else {
			int k = stringToRating(item.colls[i]);
			int l = stringToRating(item1.colls[i]);
			result = k != l ? (int) (k <= l ? -1 : 1) : 0;
		}
		if (reverse && i == 1 && !useProvisionalColor)
			result *= -1;
		if (result == 0) {
			if (item.index == item1.index)
				return 0;
			return item.index <= item1.index ? -1 : 1;
		}
		return result;
	}

	@Override
	public void setValue(ListItem item, int value, Object obj) {
		item.rating = value;
	}

	int stringToRating(String str) {
		if (str.equals("") || str.equals(applet.provisional))
			return useProvisionalColor ? 0x7fffffff : -1;
		return Integer.parseInt(str);
	}
}
