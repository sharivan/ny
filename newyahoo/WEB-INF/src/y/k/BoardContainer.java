// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Event;
import java.util.Vector;

import y.controls.ArrowControl;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;

// Referenced classes of package y.k:
// _cls65, _cls147, _cls151, _cls45,
// _cls62, _cls75

class BoardContainer extends YahooControl {

	YahooBoardTable	table;
	YahooLabel		b64[];
	YahooLabel		c64[];
	ArrowControl	arrows[];
	YahooComponent	sits[];

	BoardContainer(YahooBoardTable table, YahooComponent component,
			YahooComponent componen1) {
		b64 = new YahooLabel[2];
		c64 = new YahooLabel[2];
		arrows = new ArrowControl[2];
		sits = new YahooComponent[2];
		this.table = table;
		addChildObject(component, 1, 9, 0, 0);
		for (int i = 0; i < table.sits.length; i++) {
			sits[i] = table.Sz(i);
			addChildObject(sits[i], 10, 2, 0, 1, 1, 1, i != 0 ? 0 : 6);
			arrows[i] = new ArrowControl(3, 21, 21);
			addChildObject(arrows[i], 10, 2, 0, 1, 1, 2, i != 0 ? 0 : 6);
			b64[i] = new YahooLabel(i != 0 ? table.player1Won() : table
					.player0Won());
			b64[i].setForeColor(i != 0 ? table.tc() : table.yc());
			addChildObject(b64[i], 10, 2, 2, 2, 1, 1, i != 0 ? 1 : 5);
			c64[i] = new YahooLabel();
			addChildObject(c64[i], 10, 2, 2, 2, 1, 1, i != 0 ? 2 : 4);
		}

		addChildObject(componen1, 2, 1, 1, 3, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == table.board) {
			table.makeMove((Vector<Integer>) obj);
			return true;
		}
		return false;
	}
}
