// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;

// Referenced classes of package y.po:
// _cls79, _cls28, _cls78

public class YahooPannel extends YahooControl {

	public Color	yp_a;
	public Color	yp_b;
	public String	yp_c;

	public YahooPannel(String s, YahooComponent _pcls78, Color color,
			Color color1) {
		yp_c = s;
		yp_a = color;
		yp_b = color1;
		_cls28 _lcls28 = new _cls28(this);
		addChildObject(_lcls28, 17, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0);
		addChildObject(_pcls78, 1, 1, 0, 1, true);
	}
}
