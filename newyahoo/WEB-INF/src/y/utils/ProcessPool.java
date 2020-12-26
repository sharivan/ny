// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.util.Vector;

import y.controls.ProcessHandler;
import y.controls.YahooVector;

// Referenced classes of package y.po:
// _cls63, _cls65, _cls83, _cls66

public class ProcessPool {

	Vector<ProcessEntry>		currentList;
	boolean						stoped;
	YahooVector<ProcessEntry>	list;

	public ProcessPool() {
		currentList = new Vector<ProcessEntry>();
		stoped = false;
		list = new YahooVector<ProcessEntry>();
	}

	public synchronized void add(ProcessHandler handler, int code, Object obj) {
		ProcessEntry entry = ProcessEntry.getInstance(handler, code, obj);
		currentList.add(entry);
	}

	public void process() {
		synchronized (this) {
			if (stoped)
				return;
			list.clear();
			list.assign(currentList);
			currentList.clear();
		}
		for (int i = 0; i < list.size(); i++) {
			ProcessEntry entry = list.elementAt(i);
			entry.handler.process(entry.code, entry.obj);
			entry.obj = null;
			entry.handler = null;
			ProcessEntry.pool.release(entry);
		}
	}

	public synchronized void stop() {
		stoped = true;
	}
}
