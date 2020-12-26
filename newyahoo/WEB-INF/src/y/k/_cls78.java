// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;

import y.controls.YahooGraphics;

// Referenced classes of package y.k:
// _cls62, _cls100

public class _cls78 {

	static final Color	a	= new Color(104, 104, 104);
	static final Color	b	= new Color(184, 184, 184);

	public static void hi(YahooGraphics _pcls100, int i, int j, int k, int l) {
		_pcls100.setColor(a);
		_pcls100.drawRect(i, j, k, l);
		_pcls100.drawRect(i + 3, j + 3, k - 6, l - 6);
		_pcls100.setColor(Color.white);
		_pcls100.drawRect(i + 1, j + 1, k - 2, l - 2);
		_pcls100.setColor(b);
		_pcls100.drawRect(i + 2, j + 2, k - 4, l - 4);
	}

	public _cls78() {
	}

}
