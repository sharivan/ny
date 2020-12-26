// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import y.yutils.CustomYahooGamesApplet;

// Referenced classes of package y.k:
// _cls59, _cls48, _cls109

public class YahooCheckers extends CustomYahooGamesApplet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7425616548007021454L;

	public YahooCheckers() {
		// YahooButton yahooButton = new YahooButton("Botão de teste");
	}

	@Override
	public void initProperties() {
		super.tableSettings = new CheckersTableOptions();
		super.initProperties();
	}
}
