// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import y.utils.Processor;

// Referenced classes of package y.po:
// _cls173, _cls85, _cls168

public class AppletProcessor extends Processor {

	AbstractYahooApplet	applet;

	public AppletProcessor(AbstractYahooApplet applet) {
		super(applet, 15);
		this.applet = applet;
	}

	@Override
	public void handleError(Throwable error) {
		applet.doError(error);
	}

	@Override
	public boolean isActive() {
		return applet.isActive();
	}
}
