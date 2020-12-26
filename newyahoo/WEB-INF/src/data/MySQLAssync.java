/**
 * 
 */
package data;

import process.ProcessItem;
import process.ProcessQueue;

/**
 * @author Saddam
 * 
 */
public class MySQLAssync implements ProcessItem {

	MySQLTable	table;
	String		command;

	public MySQLAssync(ProcessQueue processQeue, MySQLTable table,
			String command) {
		this.table = table;
		this.command = command;
		processQeue.put(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.ProcessItem#process()
	 */
	@Override
	public void process() {
		table.execute(command);
	}

}
