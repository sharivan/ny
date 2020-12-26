// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

// Referenced classes of package y.po:
// _cls50, _cls86

public class TimerEntry {

	public long			processedTime;
	public long			interval;
	public long			tempInterval;
	public TimerHandler	handler;
	public TimerEngine	engine;
	public boolean		enabled;

	public TimerEntry(TimerEngine engine, TimerHandler handler, long interval,
			boolean enabled) {
		this.engine = engine;
		this.handler = handler;
		this.interval = interval;
		this.enabled = enabled;
		start();
	}

	public void setInterval(long i) {
		interval = i;
	}

	public void start() {
		processedTime = 0;
		if (interval >= 0)
			tempInterval = interval;
	}

	public void stop() {
		engine.remove(this);
	}
}
