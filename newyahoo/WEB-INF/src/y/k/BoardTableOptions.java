// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.util.Hashtable;

import y.utils.StringLookuper;
import y.ycontrols.CustomTableOptions;

import common.yutils.Game;

// Referenced classes of package y.k:
// _cls14, _cls81, _cls108, _cls11

public abstract class BoardTableOptions extends CustomTableOptions {

	public BoardTableOptions() {
	}

	@Override
	public String getOptions(Hashtable<String, String> hashtable,
			StringLookuper _pcls11) {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(TableTimeLabel.getLabel(hashtable, _pcls11));
		stringbuffer.append(Game.isRated(hashtable) ? _pcls11
				.lookupString(0x66500760) : _pcls11.lookupString(0x66500761));
		return new String(stringbuffer);
	}

	@Override
	public int getSitCount() {
		return 2;
	}

	@Override
	public int isTraining(Hashtable<String, String> hashtable) {
		return hashtable.containsKey("training")
				|| hashtable.containsKey("automat") ? 1 : 2;
	}
}
