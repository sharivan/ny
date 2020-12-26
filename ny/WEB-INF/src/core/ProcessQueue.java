/**
 * 
 */
package core;

import java.util.Vector;

/**
 * @author Saddam
 * 
 */
public class ProcessQueue extends Thread {

	Vector<ProcessItem>	items;

	public ProcessQueue() {
		super(ProcessQueue.class.getName());
		items = new Vector<ProcessItem>();
		start();
	}

	public synchronized void close() {
		items.addElement(null);
		notifyAll();
	}

	public synchronized void put(ProcessItem item) {
		items.addElement(item);
		notifyAll();
	}

	@Override
	public void run() {
		do {
			ProcessItem item;
			synchronized (this) {
				while (items.size() == 0)
					try {
						wait();
					}
					catch (InterruptedException _ex) {
						_ex.printStackTrace();
					}
				item = items.elementAt(0);
				items.removeElementAt(0);
			}
			if (item == null)
				return;
			item.process();
		}
		while (true);
	}

}
