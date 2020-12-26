// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.util.Vector;

import common.utils.Buffer;

// Referenced classes of package y.po:
// _cls160, _cls78, _cls107

public class ListItem {

	public Color			color;
	public YahooComponent	componentTable[][];
	public String			colls[];
	public Object			obj;
	public int				rating;
	Buffer					g;
	Buffer					h;
	Vector<Color>			colors;
	public int				index;
	YahooListBox			list;

	ListItem(YahooListBox list, Object obj) {
		g = new Buffer();
		h = new Buffer();
		colors = new Vector<Color>();
		this.list = list;
		componentTable = new YahooComponent[list.listColCount][];
		colls = new String[list.listColCount];
		for (int l = 0; l < list.listColCount; l++) {
			colls[l] = "";
			componentTable[l] = new YahooComponent[6];
		}

		index = list.count;
		list.count++;
		this.obj = obj;
	}

	public int ay() {
		return list.ylb_s;
	}
}
