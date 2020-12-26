/**
 * 
 */
package server.yutils;

import java.sql.Timestamp;

/**
 * @author Saddam Hussein
 * 
 */
public class IgnoredEntry {

	public static final int	MUTE	= 1;
	public static final int	BAN		= 2;

	public String			name;
	public int				type;
	public Timestamp		date;
	public int				time;
	public String			reason;

	public IgnoredEntry(String name, int type, Timestamp date, int time,
			String reason) {
		this.name = name;
		this.type = type;
		this.date = date;
		this.time = time;
		this.reason = reason;
	}

}
