// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import y.ycontrols.CustomTableOptions;
import y.yutils.CustomYahooGamesApplet;

/*
 * O que acabei de fazer agora é executar o servidor do yahoo pool. Agora vou
 * rodar o applet cliente do yahoo pool
 */

public class YahooPool extends CustomYahooGamesApplet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -497994604629940264L;
	public int					update;
	public boolean				yahooadmin;
	public boolean				show_9ball_option;
	public boolean				yp_d;

	public YahooPool() {
		update = 1;
	}

	public CustomTableOptions createTableOptions() {
		return new PoolTableOptions(this);
	}

	@Override
	public void initProperties() {
		try {
			update = Integer.parseInt(getParameter("update"));
			if (getParameter("isyahooadmin") != null)
				yahooadmin = true;
			if (getParameter("show_9ball_option") != null)
				show_9ball_option = true;
			getParameter("allow_9ball_all");
		}
		catch (Throwable _ex) {
			_ex.printStackTrace();
		}
		super.tableSettings = createTableOptions();
		super.initProperties();
		YahooPoolImageList.loadImages();
	}
}
