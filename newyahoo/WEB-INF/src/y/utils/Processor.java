// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.Container;

import y.controls.ProcessHandler;
import y.controls.YahooContainer;

// Referenced classes of package y.po:
// _cls81, _cls50, _cls21, _cls143,
// _cls65

public class Processor {

	public ProcessPool				pool;
	public TimerEngine				timerHandler;
	public Object					lock;
	public YahooContainer			defaultContainer;
	public YahooContainer			container;
	public YahooFrameDisposeQueue	framesToClose;
	public boolean					stoped;

	public Processor(Container container) {
		this(container, 15);
	}

	public Processor(Container container, int i) {
		pool = new ProcessPool();
		lock = new Object();
		this.container = defaultContainer;
		framesToClose = new YahooFrameDisposeQueue();
		timerHandler = new TimerEngine(i);
		timerHandler.setProcessor(this);
		timerHandler.start();
		defaultContainer = new YahooContainer(container, this);
	}

	public final void addProcess(ProcessHandler handler, int code, Object obj) {
		pool.add(handler, code, obj);
		getContainer().Bb();
	}

	public YahooContainer getContainer() {
		YahooContainer container1 = container;
		if (container1 == null)
			container1 = defaultContainer;
		return container1;
	}

	public void handleError(Throwable error) {
		error.printStackTrace();
	}

	public boolean isActive() {
		return true;
	}
}
