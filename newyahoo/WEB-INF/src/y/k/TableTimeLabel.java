// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.util.Hashtable;

import y.utils.StringLookuper;

import common.yutils.Game;

// Referenced classes of package y.k:
// _cls81, _cls11

public class TableTimeLabel {

	public static int getIncrementTime(Hashtable<String, String> hashtable) {
		int i = Game.getInRange("it", hashtable, 0, 0x7fffffff, 0);
		i -= i % 1000;
		return i;
	}

	public static String getLabel(Hashtable<String, String> hashtable,
			StringLookuper _pcls11) {
		StringBuffer stringbuffer = new StringBuffer();
		int i = getTotalTime(hashtable);
		int j = getIncrementTime(hashtable);
		if (i != -1)
			stringbuffer.append(i / 60000 + _pcls11.lookupString(0x66500747)
					+ j / 1000 + _pcls11.lookupString(0x66500748));
		else
			stringbuffer.append(_pcls11.lookupString(0x6650074a));
		return new String(stringbuffer);
	}

	public static int getTotalTime(Hashtable<String, String> hashtable) {
		int i = Game.getInRange("tt", hashtable, -1, 0x7fffffff, -1);
		if (i >= 0 && i < 60000)
			return -1;
		if (i != -1)
			i -= i % 60000;
		return i;
	}

	public TableTimeLabel() {
	}
}
