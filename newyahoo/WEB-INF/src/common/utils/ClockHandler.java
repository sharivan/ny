/**
 * 
 */
package common.utils;

/**
 * @author Saddam
 * 
 */
public interface ClockHandler {

	/**
	 * @param reverseClock
	 */
	public void handlePause(ReverseClock reverseClock);

	public void handleTimer(ReverseClock reverseClock);

}
