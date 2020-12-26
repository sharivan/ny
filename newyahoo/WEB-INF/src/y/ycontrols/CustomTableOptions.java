// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.util.Hashtable;

import y.utils.StringLookuper;
import y.yutils.YahooTable;

import common.yutils.Game;
import common.yutils.GameHandler;

// Referenced classes of package y.po:
// _cls105, _cls95, _cls12, _cls174,
// _cls97, _cls14

public abstract class CustomTableOptions {

	public CustomTableOptions() {
	}

	public abstract Game createGame(Hashtable<String, String> properties);

	public abstract TableCreator createTableCreator();

	public abstract String getOptions(Hashtable<String, String> hashtable,
			StringLookuper _pcls14);

	public abstract int getSitCount();

	public abstract String getVersion();

	public abstract int isTraining(Hashtable<String, String> hashtable);

	public final Game makeGame(Hashtable<String, String> hashtable,
			GameHandler _handler) {
		Game _lcls95 = createGame(hashtable);
		_lcls95.initialize(hashtable, _handler, isTraining(hashtable));
		return _lcls95;
	}

	public abstract YahooTable newTable();

	public abstract String sa(StringLookuper _pcls14, int i);

	public String toString(Hashtable<String, String> hashtable,
			StringLookuper _pcls14) {
		String s = getOptions(hashtable, _pcls14);
		if (hashtable.get("dc") != null)
			s = (s == null ? "" : s + " ") + hashtable.get("dc");
		return s;
	}

}
