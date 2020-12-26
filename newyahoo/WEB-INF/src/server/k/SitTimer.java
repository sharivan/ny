/**
 * 
 */
package server.k;

import common.utils.ClockHandler;
import common.utils.ReverseClock;

/**
 * @author Saddam
 * 
 */
public class SitTimer extends ReverseClock {

	protected int	sitIndex;

	public SitTimer(ClockHandler handler, int sitIndex, long totalTime) {
		this(handler, sitIndex, totalTime, true);
	}

	public SitTimer(ClockHandler handler, int sitIndex, long totalTime,
			boolean enabled) {
		super(SitTimer.class.getName() + "[" + sitIndex + "]", handler,
				totalTime, enabled);
		this.sitIndex = sitIndex;
	}

	/**
	 * @return the sitIndex
	 */
	public int getSitIndex() {
		return sitIndex;
	}

}
