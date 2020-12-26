// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.util.Hashtable;

import y.controls.YahooControl;
import y.dialogs.YahooDialog;
import y.yutils.CustomYahooGamesApplet;

// Referenced classes of package y.po:
// _cls56, _cls74, _cls54, _cls105,
// _cls133, _cls79, _cls27

public class TableCreator {

	public CustomYahooGamesApplet	applet;
	Hashtable<String, String>		properties;
	public YahooControl				container;
	YahooDialog						yahooDialog;

	public TableCreator() {
		properties = new Hashtable<String, String>(7);
	}

	public final void addProperty(String s, String s1) {
		properties.put(s, s1);
	}

	public void cancel() {
		if (yahooDialog != null)
			yahooDialog.close();
	}

	public void createDialog() {
		if (getApplet().idPropertyContains(32L))
			yahooDialog = new TableLabelDialog(this, container);
		makeTable();
	}

	public final CustomYahooGamesApplet getApplet() {
		return applet;
	}

	public final void makeTable() {
		applet.makeTable(properties);
	}
}
