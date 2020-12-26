/**
 * 
 */
package process;

import javax.security.auth.Destroyable;

/**
 * @author saddam
 * 
 */
public class ProcessPool implements Destroyable {

	static int		defaultPoolLen	= 4;

	private boolean	destroyed		= false;

	int				length;
	ProcessQueue[]	process;

	public ProcessPool() {
		this(defaultPoolLen);
	}

	public ProcessPool(int length) {
		if (length <= 0)
			throw new ArrayIndexOutOfBoundsException(
					"Length of process pool canot be <= 0.");
		this.length = length;
		process = new ProcessQueue[length];
		for (int i = 0; i < length; i++)
			process[i] = new ProcessQueue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		for (ProcessQueue proces : process)
			proces.close();
		process = null;

		destroyed = true;
	}

	public synchronized ProcessQueue getProcessQueue() {
		ProcessQueue result = process[0];
		int minSize = result.size();
		if (minSize == 0)
			return result;
		for (int i = 1; i < process.length; i++) {
			ProcessQueue result1 = process[i];
			int size = result.size();
			if (size == 0)
				return result1;
			if (size < minSize) {
				result = result1;
				minSize = size;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.Destroyable#isDestroyed()
	 */
	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

}
