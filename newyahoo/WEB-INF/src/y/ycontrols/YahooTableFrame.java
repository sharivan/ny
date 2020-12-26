// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import y.controls.YahooFrame;
import y.yutils.YahooGamesTable;

// Referenced classes of package y.po:
// _cls82, _cls174, _cls99, _cls101

public class YahooTableFrame extends YahooFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6122644314206905121L;
	YahooGamesTable				table;

	public YahooTableFrame(YahooGamesTable _pcls99) {
		super(_pcls99.getApplet().processor);
		table = _pcls99;
	}

	@Override
	protected void close() {
		if (table != null) {
			table.exit();
			table = null;
		}
	}

	/**
	 * 
	 */
	@Override
	protected void doGotFocus() {
		if (table != null)
			table.send('0');
	}

	/**
	 * 
	 */
	@Override
	protected void doLostFocus() {
		if (table != null)
			table.send('1');
	}
}
