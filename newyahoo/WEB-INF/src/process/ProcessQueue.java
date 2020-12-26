/**
 * 
 */
package process;

import java.util.Vector;

/**
 * @author Saddam
 * 
 */
public class ProcessQueue extends Thread {

	static int			processQueueCreateds	= 0;

	Vector<ProcessItem>	items;

	public ProcessQueue() {
		this(ProcessQueue.class.getName() + "[" + processQueueCreateds++ + "]");
	}

	public ProcessQueue(String name) {
		super(name);
		items = new Vector<ProcessItem>();
		start();
	}

	public synchronized void close() {
		items.add(null);
		notifyAll();
		processQueueCreateds--;
	}

	public synchronized void put(ProcessItem item) {
		items.add(item);
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

	/**
	 * @return quantidade de elementos a serem processados
	 */
	public int size() {
		return items.size();
	}

}
