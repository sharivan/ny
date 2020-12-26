// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.util.Vector;

import y.controls.ProcessHandler;
import y.controls.YahooContainer;
import y.controls.YahooVector;

// Referenced classes of package y.po:
// _cls63, _cls65, _cls81, _cls86,
// _cls177, _cls21, _cls173

public class TimerEngine implements Runnable, ProcessHandler {

	private boolean					enabled;
	private boolean					finished;
	private long					atomicInterval;
	private boolean					waiting;
	private long					time;
	private Vector<TimerEntry>		currList;
	private Processor				processor;
	private YahooVector<TimerEntry>	list;
	private Thread					thisThread;

	public TimerEngine(long atomicInterval) {
		enabled = false;
		finished = false;
		waiting = false;
		currList = new Vector<TimerEntry>();
		processor = null;
		list = new YahooVector<TimerEntry>();
		this.atomicInterval = atomicInterval;
		thisThread = new Thread(this, TimerEngine.class.getName());
	}

	public final TimerEntry add(TimerHandler _pcls86) {
		return add(_pcls86, -1);
	}

	public final TimerEntry add(TimerHandler _pcls86, long interval) {
		return add(_pcls86, interval, true);
	}

	public final synchronized TimerEntry add(TimerHandler _pcls86,
			long interval, boolean enabled) {
		TimerEntry timer = new TimerEntry(this, _pcls86, interval, enabled);
		currList.add(timer);
		return timer;
	}

	void doTimer(TimerEntry entry) {
		try {
			if (entry.interval == -1L)
				entry.handler.handleTimer(entry.processedTime);
			else
				while (entry.processedTime > entry.tempInterval) {
					entry.tempInterval += entry.interval;
					if (entry.handler != null)
						entry.handler.handleTimer(entry.processedTime);
				}
		}
		catch (Throwable throwable) {
			if (processor != null)
				processor.handleError(throwable);
			else
				throwable.printStackTrace();
		}
	}

	public boolean loop() {
		return true;
	}

	void process() {
		synchronized (this) {
			list.assign(currList);
		}
		int i = list.size();
		for (int j = 0; j < i; j++) {
			TimerEntry entry = list.elementAt(j);
			entry.processedTime += atomicInterval;
			if (entry.handler instanceof YahooContainer) {
				doTimer(entry);
			}
			else {
				processor.pool.add(this, 0, entry);
				if (entry.processedTime + atomicInterval > entry.tempInterval
						&& processor.isActive())
					processor.getContainer().Bb();
			}
		}

	}

	public void process(int code, Object obj) {
		doTimer((TimerEntry) obj);
	}

	public synchronized void refresh() {
		time = System.currentTimeMillis();
		if (waiting)
			notify();
	}

	public final synchronized void remove(TimerEntry _pcls177) {
		currList.removeElement(_pcls177);
	}

	@Override
	public void run() {
		time = System.currentTimeMillis() + atomicInterval;
		while (!enabled) {
			synchronized (this) {
				long l = System.currentTimeMillis();
				long l1 = time - l;
				if (l1 > 0L) {
					waiting = true;
					try {
						wait(l1);
					}
					catch (InterruptedException _ex) {
					}
					finally {
						waiting = false;
					}
				}
				time += atomicInterval;
			}
			if (processor == null)
				simplyProcess();
			else
				process();
			if (!loop())
				break;
		}
		synchronized (this) {
			finished = true;
			notify();
		}
	}

	public final void setProcessor(Processor processor) {
		this.processor = processor;
	}

	void simplyProcess() {
		synchronized (this) {
			list.assign(currList);
		}
		int i = list.size();
		for (int j = 0; j < i; j++) {
			TimerEntry entry = list.elementAt(j);
			entry.processedTime += atomicInterval;
			doTimer(entry);
		}

	}

	/**
	 * 
	 */
	public void start() {
		thisThread.start();
	}

	public synchronized void terminate() {
		enabled = true;
		while (!finished) {
			refresh();
			try {
				wait();
			}
			catch (InterruptedException _ex) {
			}
		}
	}
}
