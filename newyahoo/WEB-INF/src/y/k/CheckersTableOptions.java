// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.util.Hashtable;

import y.utils.StringLookuper;
import y.ycontrols.TableCreator;
import y.yutils.YahooTable;

import common.k.BrazilianCheckers;
import common.k.Checkers;
import common.k.EnglishCheckers;
import common.k.InternationalCheckers;
import common.k.ItalianCheckers;
import common.k.RussianCheckers;
import common.k.SpanishCheckers;
import common.k.TurkishCheckers;
import common.yutils.Game;

// Referenced classes of package y.k:
// _cls23, _cls82, _cls137, _cls86,
// _cls11, _cls151, _cls81

public class CheckersTableOptions extends BoardTableOptions {

	int	a[]	= { 0x665008db, 0x665008da };

	public CheckersTableOptions() {
	}

	@Override
	public Game createGame(Hashtable<String, String> properties) {
		String variant = properties.get("variant");
		if (variant != null) {
			if (variant.equals("english"))
				return new EnglishCheckers();
			if (variant.equals("brazilian"))
				return new BrazilianCheckers();
			if (variant.equals("spanish"))
				return new SpanishCheckers();
			if (variant.equals("italian"))
				return new ItalianCheckers();
			if (variant.equals("russian"))
				return new RussianCheckers();
			if (variant.equals("international"))
				return new InternationalCheckers();
			if (variant.equals("turkish"))
				return new TurkishCheckers();
		}
		return new EnglishCheckers();
	}

	@Override
	public TableCreator createTableCreator() {
		return new CheckersTableCreator();
	}

	@Override
	public String getOptions(Hashtable<String, String> hashtable,
			StringLookuper _pcls14) {
		StringBuffer stringbuffer = new StringBuffer();
		String variant = Checkers.getVariant(hashtable);
		if (variant == null)
			variant = "english";
		stringbuffer.append(variant);
		stringbuffer.append(" ");
		stringbuffer.append(super.getOptions(hashtable, _pcls14));
		return new String(stringbuffer);
	}

	@Override
	public String getVersion() {
		return "4";
	}

	@Override
	public YahooTable newTable() {
		return new YahooCheckersTable();
	}

	@Override
	public String sa(StringLookuper _pcls11, int i) {
		return _pcls11.lookupString(a[i]);
	}
}
