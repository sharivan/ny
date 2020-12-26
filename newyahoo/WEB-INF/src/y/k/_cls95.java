// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Image;
import java.awt.image.ImageObserver;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooGraphics;
import y.utils.TimerEngine;
import y.utils.TimerEntry;
import y.utils.TimerHandler;

// Referenced classes of package y.k:
// _cls62, _cls74, _cls39, _cls65,
// _cls100, _cls153

public class _cls95 extends YahooComponent implements ImageObserver,
		TimerHandler {

	Image			b95;
	YahooControl	c95;
	TimerEngine		d95;
	TimerEntry		e;

	public _cls95(YahooControl _pcls65, Image image, TimerEngine _pcls39) {
		super(100, 100);
		_pcls65.addChildObject(this, 0, 0, true);
		b95 = image;
		c95 = _pcls65;
		d95 = _pcls39;
		e = _pcls39.add(this, 8000);
	}

	public void handleTimer(long l) {
		c95.removeChildObject(this);
		d95.remove(e);
	}

	public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
		boolean flag = (i & 0x20) != 0;
		if (flag)
			c95.removeChildObject(this);
		return flag ^ true;
	}

	@Override
	public void paint(YahooGraphics _pcls100) {
		_pcls100.drawImage(b95, 0, 0, this);
	}
}
