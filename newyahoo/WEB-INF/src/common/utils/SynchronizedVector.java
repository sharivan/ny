/**
 * 
 */
package common.utils;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @author Saddam Hussein
 * 
 */
public class SynchronizedVector<E> extends Vector<E> {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 3784087319156362596L;

	private ReentrantReadWriteLock	lock;

	private ReadLock				r;

	private WriteLock				w;

	public SynchronizedVector() {
		super();
		initlock();
	}

	public SynchronizedVector(Collection<? extends E> c) {
		super(c);
		initlock();
	}

	public SynchronizedVector(int initialCapacity) {
		super(initialCapacity);
		initlock();
	}

	public SynchronizedVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		initlock();
	}

	private void initlock() {
		lock = new ReentrantReadWriteLock(true);
		r = lock.readLock();
		w = lock.writeLock();
	}

	public void readLock() {
		r.lock();
	}

	public void readUnlock() {
		r.unlock();
	}

	public void writeLock() {
		w.lock();
	}

	public void writeUnlock() {
		w.unlock();
	}

}
