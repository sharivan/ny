package server.k;

import server.yutils.YahooRoom;
import server.yutils.YahooRoomHandler;
import server.yutils.YahooTable;
import data.MySQLTable;

public class YahooCheckersRoom extends YahooRoom {

	public YahooCheckersRoom(YahooRoomHandler handler, int index, String yport) {
		super(handler, index, yport);
	}

	@Override
	public YahooTable createTable(int number) {
		return new YahooCheckersTable(this, number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooRoom#getGameLogTable()
	 */
	@Override
	public MySQLTable getGameLogTable() {
		return handler.getTable("checkers_games");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.common.YahooRoom#getIgnoredsTable()
	 */
	@Override
	protected MySQLTable getIgnoredsTable() {
		return handler.getTable("checkers_ignoreds");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.common.YahooRoom#getProfileTable()
	 */
	@Override
	public MySQLTable getProfileTable() {
		return handler.getTable("checkers_profiles");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.common.YahooRoom#getRooms()
	 */
	@Override
	protected MySQLTable getRooms() {
		return handler.getTable("checkers_rooms");
	}

}
