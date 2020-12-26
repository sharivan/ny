package server.po2;

import server.yutils.YahooRoom;
import server.yutils.YahooRoomHandler;
import server.yutils.YahooTable;
import data.MySQLTable;

public class YahooPoolRoom extends YahooRoom {

	public YahooPoolRoom(YahooRoomHandler handler, int index, String yport) {
		super(handler, index, yport);
	}

	@Override
	public YahooTable createTable(int number) {
		return new YahooPoolTable(this, number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooRoom#getGameLogTable()
	 */
	@Override
	public MySQLTable getGameLogTable() {
		return handler.getTable("pool_games");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.common.YahooRoom#getIgnoredsTable()
	 */
	@Override
	protected MySQLTable getIgnoredsTable() {
		return handler.getTable("pool_ignoreds");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.common.YahooRoom#getProfileTable()
	 */
	@Override
	public MySQLTable getProfileTable() {
		return handler.getTable("pool_profiles");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.common.YahooRoom#getRooms()
	 */
	@Override
	protected MySQLTable getRooms() {
		return handler.getTable("pool_rooms");
	}

}
