// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.dialogs;

import y.controls.YahooButton;
import y.controls.YahooFrame;
import y.controls.YahooLabel;
import y.utils.Processor;
import y.utils.StringLookuper;

// Referenced classes of package y.po:
// _cls82, _cls168, _cls35, _cls79,
// _cls87

public class OkFrame extends YahooFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5159012974509478499L;

	public OkFrame(String s, Processor _pcls173, StringLookuper lookuper) {
		super(lookuper.lookupString(0x66500000), true, _pcls173);
		super.container.addChildObject(new YahooLabel(s), 1, 1, 0, 0);
		YahooButton _lcls35 = new YahooButton(lookuper.lookupString(0x66500001));
		setDefaultAction(_lcls35);
		super.container.addChildObject(_lcls35, 1, 1, 0, 1);
		pack();
		setVisible(true);
	}
}
