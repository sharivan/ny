/**
 * 
 */
package common.yutils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.utils.IOBuffer;

/**
 * @author Saddam Hussein
 * 
 */
public class GameHistory extends IOBuffer {

	private long		time;

	private String[]	players;

	private int[]		result;

	long				flags;

	private int[]		oldRating;

	private int[]		newRating;

	private int			mark;

	public GameHistory() {
		this(0, null, 0, 0);
	}

	public GameHistory(long time, String[] players, long flags) {
		this(time, players, flags, 16);
	}

	public GameHistory(long time, String[] players, long flags, byte[] buf) {
		super(buf);

		this.time = time;
		this.players = players;
		this.flags = flags;

		if (players != null) {
			oldRating = new int[players.length];
			newRating = new int[players.length];
		}
		else {
			oldRating = null;
			newRating = null;
		}

		mark = -1;
	}

	public GameHistory(long time, String[] players, long flags, int size) {
		this(time, players, flags, new byte[size]);
	}

	public void back() throws IOException {
		setReadPos(getWritePos() - 4);
		setReadPos(readInt());
		setWritePos(getReadPos());
	}

	public void beginWrite(int byte0) {
		beginWrite(byte0, System.currentTimeMillis());
	}

	public void beginWrite(int byte0, long time) {
		mark = getWritePos();
		write(byte0);
		writeLong(time);
	}

	/**
	 * 
	 */
	public void close() {
		players = null;
		oldRating = null;
		newRating = null;
	}

	public void endWrite() {
		if (mark != -1) {
			writeInt(mark);
			mark = -1;
		}
	}

	/**
	 * @return the flags
	 */
	public long getFlags() {
		return flags;
	}

	/**
	 * @return the newRating
	 */
	public int[] getNewRating() {
		return newRating;
	}

	/**
	 * @return array das pontuações posteriores ao jogo
	 */
	public int[] getNewRatings() {
		return newRating;
	}

	/**
	 * @return the oldRating
	 */
	public int[] getOldRating() {
		return oldRating;
	}

	/**
	 * @return array das pontuações anteriores ao jogo
	 */
	public int[] getOldRatings() {
		return oldRating;
	}

	/**
	 * @return the player at position index
	 */
	public String getPlayer(int index) {
		return players[index];
	}

	/**
	 * @return the players
	 */
	public String[] getPlayers() {
		return players;
	}

	/**
	 * @return the result
	 */
	public int[] getResult() {
		return result;
	}

	/**
	 * @return the date
	 */
	public long getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.io.YData#read(java.io.DataInput)
	 */
	@Override
	public void read(DataInput input) throws IOException {
		time = input.readLong();
		flags = input.readLong();
		int len = input.readInt();
		if (len >= 0) {
			players = new String[len];
			oldRating = new int[len];
			newRating = new int[len];
			for (int i = 0; i < len; i++) {
				players[i] = input.readUTF();
				oldRating[i] = input.readInt();
				newRating[i] = input.readInt();
				result[i] = input.readInt();
			}
		}
		super.read(input);
	}

	/**
	 * @param i
	 * @param j
	 */
	public void setNewRating(int i, int j) {
		newRating[i] = j;
	}

	/**
	 * @param i
	 * @param j
	 */
	public void setOldRating(int i, int j) {
		oldRating[i] = j;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(int[] result) {
		this.result = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.io.YData#write(java.io.DataOutput)
	 */
	@Override
	public void write(DataOutput output) throws IOException {
		output.writeLong(time);
		output.writeLong(flags);
		if (players != null) {
			output.writeInt(players.length);
			for (int i = 0; i < players.length; i++) {
				output.writeUTF(players[i]);
				output.writeInt(oldRating[i]);
				output.writeInt(newRating[i]);
				output.writeInt(result[i]);
			}
		}
		else
			output.writeInt(-1);
		super.write(output);
	}

}
