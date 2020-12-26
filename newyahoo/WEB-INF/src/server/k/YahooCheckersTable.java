package server.k;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

import server.io.YahooConnectionId;
import server.yutils.YahooRoom;
import server.yutils.YahooTable;
import y.utils.Square;

import common.io.YData;
import common.k.BoardGame;
import common.k.BoardGameHandler;
import common.k.BrazilianCheckers;
import common.k.Checkers;
import common.k.EnglishCheckers;
import common.k.InternationalCheckers;
import common.k.ItalianCheckers;
import common.k.Move;
import common.k.RussianCheckers;
import common.k.SpanishCheckers;
import common.k.Tree;
import common.k.TurkishCheckers;
import common.utils.ClockHandler;
import common.utils.ReverseClock;
import common.yutils.Game;

public class YahooCheckersTable extends YahooTable implements BoardGameHandler,
		ClockHandler {

	Checkers	checkers;
	SitTimer[]	sitTimer;
	SitTimer	activeTimer;
	long		totalTime;
	long		incrementTime;

	boolean		swapNext;

	public YahooCheckersTable(YahooRoom room, int number) {
		super(room, number);
		sitTimer = new SitTimer[getSitCount()];
		activeTimer = null;
		totalTime = -1;
		incrementTime = 0;
		swapNext = false;
	}

	public void acceptDraw(YahooConnectionId id, int sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('w');
			id.writeByte(sitIndex);
			id.flush();
		}
	}

	private void changeTime(YahooConnectionId id, int sitIndex, long time) {
		synchronized (id) {
			writeHeader(id);
			id.write('l');
			id.writeByte(sitIndex);
			id.writeLong(time);
			id.flush();
		}
	}

	@Override
	public void close() {
		if (checkers != null) {
			checkers.close();
			checkers = null;
		}
		if (sitTimer != null) {
			for (int i = 0; i < sitTimer.length; i++)
				if (sitTimer[i] != null) {
					sitTimer[i].close();
					sitTimer[i] = null;
				}
			sitTimer = null;
		}
		activeTimer = null;
		super.close();
	}

	@Override
	protected Game createGame() {
		String variant = Checkers.getVariant(properties);
		if (variant != null) {
			if (variant.equals("english"))
				checkers = new EnglishCheckers();
			else if (variant.equals("brazilian"))
				checkers = new BrazilianCheckers();
			else if (variant.equals("spanish"))
				checkers = new SpanishCheckers();
			else if (variant.equals("italian"))
				checkers = new ItalianCheckers();
			else if (variant.equals("russian"))
				checkers = new RussianCheckers();
			else if (variant.equals("international"))
				checkers = new InternationalCheckers();
			else if (variant.equals("turkish"))
				checkers = new TurkishCheckers();
			else
				checkers = new EnglishCheckers();
		}
		else
			checkers = new EnglishCheckers();
		return checkers;
	}

	private void doAcceptDraw(int index) {
		if (checkers.acceptDraw(index)) {
			ids.readLock();
			try {
				for (int j = 0; j < ids.size(); j++)
					acceptDraw(ids.elementAt(j), index);
			}
			finally {
				ids.readUnlock();
			}
		}
	}

	private void doChangeTime(boolean useTimePerMove, int totalTime,
			int incrementTime) {
		this.totalTime = totalTime;
		this.incrementTime = incrementTime;
		properties.put("tt", Integer.toString(totalTime));
		properties.put("it", Integer.toString(incrementTime));
		boolean flag = !properties.containsKey("pl");
		if (useTimePerMove && !flag)
			properties.put("pl", "");
		else if (!useTimePerMove && flag)
			properties.remove("pl");
		room.doChangeTableProperties(number, number, properties);
		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++) {
				changeTime(ids.elementAt(i), 0, totalTime);
				changeTime(ids.elementAt(i), 1, totalTime);
			}
		}
		finally {
			ids.readUnlock();
		}
	}

	private void doMove(int turn, Move move) throws IOException {
		int x1 = move.x1;
		int y1 = move.y1;
		int x2 = move.x2;
		int y2 = move.y2;

		int oldPiece1 = checkers.getBoard(x1, y1);
		int oldPiece2 = checkers.getBoard(x2, y2);
		checkers.saveState(move, turn);
		if (checkers.makeMove(move, turn)) {
			int newPiece1 = checkers.getBoard(x1, y1);
			int newPiece2 = checkers.getBoard(x2, y2);

			ids.readLock();
			try {
				for (int i = 0; i < ids.size(); i++)
					move(ids.elementAt(i), turn, move);
			}
			finally {
				ids.readUnlock();
			}
			synchronized (currGameLogEntry) {
				currGameLogEntry.beginWrite('m');
				try {
					currGameLogEntry.write(x1);
					currGameLogEntry.write(y1);
					currGameLogEntry.write(oldPiece1);
					currGameLogEntry.write(newPiece1);
					currGameLogEntry.write(x2);
					currGameLogEntry.write(y2);
					currGameLogEntry.write(oldPiece2);
					currGameLogEntry.write(newPiece2);
				}
				finally {
					currGameLogEntry.endWrite();
				}
			}
		}
		checkers.restoreStateIfChanged();
	}

	private void doOfferDraw(int sitIndex) {
		checkers.offerDraw(sitIndex);
		checkers.restoreStateIfChanged();
	}

	private void doRated(boolean rated) {
		if (rated && !properties.containsKey("rd"))
			properties.put("rd", "");
		else if (!rated && properties.containsKey("rd"))
			properties.remove("rd");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooTable#doStart()
	 */
	@Override
	protected void doStart() {
		if (BoardGame.haveAutoSwap(properties))
			doSwap();
		for (int i = 0; i < sits.length; i++)
			startedPlayers[i] = sits[i];
	}

	/**
	 * 
	 */
	private void doSwap() {
		/*
		 * if(!swapNext) { swapNext = true; return; }
		 */

		YahooConnectionId sit0 = sits[0];
		YahooConnectionId sit1 = sits[1];
		doStand(sit0);
		doStand(sit1);
		doSit(sit0, 1);
		doSit(sit1, 0);
	}

	private void doTimeEmpty(int sitIndex) {
		if (checkers.isRunning()) {
			ids.readLock();
			try {
				for (int i = 0; i < ids.size(); i++)
					timeEmpty(ids.elementAt(i), sitIndex);
			}
			finally {
				ids.readUnlock();
			}
		}
		checkers.handleTimeEmpty(sitIndex);
	}

	private void doUndoTurn(int i) {
		checkers.undo(i);
		checkers.restoreStateIfChanged();
		ids.readLock();
		try {
			for (int j = 0; j < ids.size(); j++)
				undoTurn(ids.elementAt(j), i);
		}
		finally {
			ids.readUnlock();
		}
	}

	@Override
	protected void doUpdateGame(YahooConnectionId id) {
		super.doUpdateGame(id);

		if (totalTime == -1)
			return;

		for (int i = 0; i < sitTimer.length; i++)
			if (sitTimer[i] == null)
				changeTime(id, i, totalTime);
			else
				changeTime(id, i, sitTimer[i].getRemaningTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.yutils.YahooTable#getGameId()
	 */
	@Override
	public int getGameId() {
		String variant = Checkers.getVariant(properties);
		if (variant != null) {
			if (variant.equals("english"))
				return 0;
			else if (variant.equals("brazilian"))
				return 1;
			else if (variant.equals("spanish"))
				return 2;
			else if (variant.equals("italian"))
				return 3;
			else if (variant.equals("russian"))
				return 4;
			else if (variant.equals("international"))
				return 5;
			else if (variant.equals("turkish"))
				return 6;
		}
		return 0;
	}

	@Override
	public int getSitCount() {
		return BoardGame.isTraining(properties)
				|| BoardGame.isAutomat(properties) ? 1 : 2;
	}

	public void handleChangeMoveList(int x, int y, int turn,
			Tree<Square> moveList) {
		// TODO Auto-generated method stub

	}

	public void handleChangeTurn(int turn) {
		// if(turn == currTurn)
		// return;
		if (totalTime != -1 && activeTimer != null) {
			activeTimer.pause();
			activeTimer = null;
		}
		if (turn >= 0) {
			activeTimer = sitTimer[turn];
			if (totalTime != -1 && activeTimer != null)
				activeTimer.go();
		}
	}

	public void handleDeclineDraw(int i) {
		// TODO Auto-generated method stub

	}

	public void handleLastMove(int x, int y) {

	}

	public void handleOfferDraw(int turn) {
		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++)
				offerDraw(ids.elementAt(i), turn);
		}
		finally {
			ids.readUnlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.utils.ClockHandler#handlePause()
	 */
	@Override
	public void handlePause(ReverseClock reverseClock) {
		reverseClock.incrementTime(incrementTime);
		ids.readLock();
		try {
			for (int i = 0; i < ids.size(); i++)
				changeTime(ids.elementAt(i), ((SitTimer) reverseClock)
						.getSitIndex(), reverseClock.getRemaningTime());
		}
		finally {
			ids.readUnlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.BoardGameHandler#handleResetMoveList(int)
	 */
	@Override
	public void handleResetMoveList(int turn) {
		// TODO Auto-generated method stub

	}

	public void handleSetBoard(int i, int j, int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleStart() {
		super.handleStart();
		if (totalTime == -1)
			return;
		for (int i = 0; i < sitTimer.length; i++) {
			ids.readLock();
			try {
				for (int j = 0; j < ids.size(); j++)
					changeTime(ids.elementAt(j), i, totalTime);
			}
			finally {
				ids.readUnlock();
			}
			sitTimer[i] = new SitTimer(this, i, totalTime, false);
		}
	}

	@Override
	public void handleStop(YData ydata) {
		if (totalTime != -1) {
			for (int i = 0; i < sitTimer.length; i++) {
				if (sitTimer[i] != null) {
					sitTimer[i].close();

					ids.readLock();
					try {
						for (int j = 0; j < ids.size(); j++)
							changeTime(ids.elementAt(j), i, sitTimer[i]
									.getRemaningTime());
					}
					finally {
						ids.readUnlock();
					}
					sitTimer[i] = null;
				}
			}
			activeTimer = null;
		}
		super.handleStop(ydata);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.utils.ClockHandler#handleTick(long)
	 */
	@Override
	public void handleTimer(ReverseClock reverseClock) {
		doTimeEmpty(((SitTimer) reverseClock).getSitIndex());
		reverseClock.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.k.BoardGameHandler#handleUndo()
	 */
	@Override
	public void handleUndo() {
		synchronized (currGameLogEntry) {
			try {
				currGameLogEntry.back();
				byte byte0 = currGameLogEntry.readByte();
				if (byte0 != 'm')
					throw new IOException("invalid byte0 (byte=" + byte0 + ")");
				currGameLogEntry.readLong();
				int x1 = currGameLogEntry.readByte();
				int y1 = currGameLogEntry.readByte();
				int oldPiece1 = currGameLogEntry.readByte();
				currGameLogEntry.readByte();
				int x2 = currGameLogEntry.readByte();
				int y2 = currGameLogEntry.readByte();
				int oldPiece2 = currGameLogEntry.readByte();
				currGameLogEntry.readByte();
				checkers.setBoard(x1, y1, oldPiece1);
				checkers.setBoard(x2, y2, oldPiece2);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				currGameLogEntry.endWrite();
			}
		}
	}

	public void handleWon(int wonTurn, boolean flag) {

	}

	public void Lc(int i, int j) {
		// TODO Auto-generated method stub

	}

	private void move(YahooConnectionId id, int turn, Move move)
			throws IOException {
		synchronized (id) {
			writeHeader(id);
			id.write('m');
			id.write(turn);
			move.write(id);
			id.flush();
		}
	}

	public void offerDraw(YahooConnectionId id, int sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('f');
			id.writeByte(sitIndex);
			id.flush();
		}
	}

	@Override
	public boolean open(YahooConnectionId host,
			Hashtable<String, String> hashtable) {
		if (!BoardGame.haveAutoSwap(hashtable))
			hashtable.put("autoSwap", "1");
		if (Checkers.getVariant(hashtable) == null)
			hashtable.put("variant", "english");
		return super.open(host, hashtable);
	}

	@Override
	public void parseData(YahooConnectionId id, int byte0, DataInputStream input)
			throws IOException {
		switch (byte0) {
		case 'F': // offer draw
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doOfferDraw(i);
					break;
				}
			break;
		case 'I': // time empty
			/* byte sitIndex = */
			input.readByte();
			// doTimeEmpty(sitIndex);
			break;
		case 'K': // rated game
			boolean rated = input.readBoolean();
			if (isHost(id))
				doRated(rated);
			else
				room.alert(id, "Você não é o host da mesa!");
			break;
		case 'L': // set time
			boolean useTimePerMove = input.readBoolean();
			int initialTime = input.readInt();
			int incrementTime = input.readInt();
			if (isHost(id))
				doChangeTime(useTimePerMove, initialTime, incrementTime);
			else
				room.alert(id, "Você não é o host da mesa!");
			break;
		case 'M': // move
			Move move = new Move();
			move.read(input);
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doMove(i, move);
					break;
				}
			break;
		case 'N': // undo turn
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doUndoTurn(i);
					break;
				}
			break;
		case 'W': // accept draw
			for (int i = 0; i < sits.length; i++)
				if (sits[i] != null && sits[i].equals(id)) {
					doAcceptDraw(i);
					break;
				}
			break;
		default:
			super.parseData(id, byte0, input);
		}
	}

	private void timeEmpty(YahooConnectionId id, int sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('i');
			id.writeByte(sitIndex);
			id.flush();
		}
	}

	public void undoTurn(YahooConnectionId id, int sitIndex) {
		synchronized (id) {
			writeHeader(id);
			id.write('n');
			id.writeByte(sitIndex);
			id.flush();
		}
	}

}
