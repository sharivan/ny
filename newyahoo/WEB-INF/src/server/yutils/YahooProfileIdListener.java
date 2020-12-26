/**
 * 
 */
package server.yutils;

import java.sql.Timestamp;

/**
 * @author saddam
 * 
 */
public interface YahooProfileIdListener {

	public void actionBan(YahooProfileId sender, Timestamp ban_date, int time,
			String reason);

	public void actionMute(YahooProfileId sender, Timestamp mute_date,
			int time, String reason);

	public void actionSetAvatar(YahooProfileId sender, byte avatar);

	public void actionSetFlags(YahooProfileId sender, long value);

	/**
	 * @param sender
	 * @param flags
	 */
	public void actionSetPublicFlags(YahooProfileId sender, int flags);

	public void actionSetRating(YahooProfileId sender, int totalGames,
			int rating);

}
