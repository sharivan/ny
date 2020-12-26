/**
 * 
 */
package server.yutils;

/**
 * @author Saddam Hussein
 * 
 */
public class FloodRecord {

	long[]	times;
	long	interval;
	int		pos;
	boolean	returned;

	public FloodRecord(int count, long interval) {
		times = new long[count];
		this.interval = interval;
		reset();
	}

	public boolean isFlood() {
		return returned
				&& times[(times.length + pos - 1) % times.length] - times[pos] < interval;
	}

	public long log() {
		long time = System.currentTimeMillis();
		times[pos++] = time;
		if (!returned && pos == times.length)
			returned = true;
		pos %= times.length;
		return time;
	}

	public void reset() {
		pos = 0;
		returned = false;
	}

}
