// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.util.Vector;

import y.controls.YahooFrame;

// Referenced classes of package y.po:
// _cls82

public class YahooFrameDisposeQueue extends Thread {

	Vector<YahooFrame>	items;

	public YahooFrameDisposeQueue() {
		super(YahooFrameDisposeQueue.class.getName());
		items = new Vector<YahooFrame>();
		start();
	}

	public synchronized void close() {
		items.add(null);
		notifyAll();
	}

	public synchronized void put(YahooFrame _pcls82) {
		items.add(_pcls82);
		notifyAll();
	}

	@Override
	public void run() {
		do {
			YahooFrame item;
			synchronized (this) {
				while (items.size() == 0)
					try {
						wait();
					}
					catch (InterruptedException _ex) {
					}
				item = items.elementAt(0);
				items.remove(0);
			}
			if (item == null)
				return;
			item.process();
		}
		while (true);
	}
}
