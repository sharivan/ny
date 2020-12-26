/**
 * 
 */
package common.utils;

/**
 * @author Saddam
 * 
 */
public class ReverseClock implements Runnable {

	private long			remaningTime;
	private boolean			enabled;
	private ClockHandler	handler;
	private boolean			finishing;
	private Object			lock;
	private long			interval;
	private boolean			living;
	private boolean			initializing;
	private Object			initializingLock;
	private Object			terminatingLock;
	private Thread			thisThead;
	private boolean			waiting;

	public ReverseClock(ClockHandler handler, long interval) {
		this(handler, interval, true);
	}

	public ReverseClock(ClockHandler handler, long interval, boolean enabled) {
		this(ReverseClock.class.getName(), handler, interval, true);
	}

	public ReverseClock(String name, ClockHandler handler, long interval,
			boolean enabled) {
		initializing = true;
		initializingLock = new Object();
		this.handler = handler;
		this.interval = interval;
		remaningTime = interval;
		this.enabled = enabled;
		waiting = false;
		finishing = false;
		lock = new Object();
		terminatingLock = new Object();
		thisThead = new Thread(this, name);
		thisThead.start();
	}

	public void close() {
		close(false);
	}

	/**
	 * 
	 */
	public void close(boolean waitForTerminate) {
		waitForInitialize();
		if (!living || finishing)
			return;
		finishing = true;
		if (waiting)
			synchronized (lock) {
				lock.notifyAll();
			}
		if (!waitForTerminate)
			return;
		try {
			synchronized (terminatingLock) {
				while (living) {
					terminatingLock.wait();
				}
			}
		}
		catch (InterruptedException e) {
		}
	}

	public void decrementTime(long dt) {
		remaningTime -= dt;
	}

	/**
	 * @return the handler
	 */
	public ClockHandler getHandler() {
		return handler;
	}

	/**
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
	}

	/**
	 * @return the interval
	 */
	public long getRemaningTime() {
		return remaningTime;
	}

	public void go() {
		waitForInitialize();
		if (!living || enabled)
			return;
		synchronized (lock) {
			enabled = true;
			lock.notifyAll();
		}
	}

	public void incrementTime(long dt) {
		remaningTime += dt;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	public boolean isFinishing() {
		return finishing;
	}

	public boolean isLiving() {
		return living;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void pause() {
		waitForInitialize();
		if (!living || !enabled)
			return;
		enabled = false;
		if (waiting)
			synchronized (lock) {
				lock.notifyAll();
			}
	}

	public void resetTime() {
		remaningTime = interval;
	}

	@Override
	public void run() {
		synchronized (initializingLock) {
			living = true;
			initializing = false;
			initializingLock.notifyAll();
		}
		try {
			while (!finishing) {
				synchronized (lock) {
					while (!enabled && !finishing) {
						waiting = true;
						try {
							lock.wait();
						}
						finally {
							waiting = false;
						}
					}
					long t = System.currentTimeMillis();
					while (!finishing && enabled && remaningTime > 0) {
						waiting = true;
						try {
							lock.wait(remaningTime);
						}
						finally {
							waiting = false;
						}
						long t1 = System.currentTimeMillis();
						remaningTime -= t1 - t;
						t = t1;
					}
				}
				if (finishing)
					return;
				if (remaningTime <= 0) {
					remaningTime = 0;
					handler.handleTimer(this);
					if (finishing)
						return;
					resetTime();
				}
				else
					handler.handlePause(this);
			}
		}
		catch (InterruptedException e) {
		}
		finally {
			synchronized (terminatingLock) {
				living = false;
				finishing = true;
				thisThead = null;
				handler = null;
				terminatingLock.notifyAll();
			}
		}
	}

	/**
	 * @param value
	 *            the enabled to set
	 */
	public void setEnabled(boolean value) {
		if (value && !enabled)
			go();
		else if (!value && enabled)
			pause();
	}

	public void setInterval(long value) {
		interval = value;
	}

	public void setRemaningTime(long t) {
		remaningTime = t;
	}

	protected void waitForInitialize() {
		try {
			synchronized (initializingLock) {
				while (initializing)
					initializingLock.wait();
			}
		}
		catch (InterruptedException e) {
		}
	}

}
