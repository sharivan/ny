package y.k;

import java.awt.Color;

import y.utils.Square;
import y.utils.TimerEntry;
import y.utils.TimerHandler;
import y.ycontrols.TableControlContainer;
import y.yutils.YahooGamesTable;

import common.k.LinkedList;

public class CheckersControlContainer extends TableControlContainer implements
		TimerHandler {

	YahooCheckersTable	checkersTable;
	String[]			reply;

	public CheckersControlContainer(YahooGamesTable _pcls99) {
		super(_pcls99);
		reply = new String[2];
		checkersTable = (YahooCheckersTable) _pcls99;
	}

	@Override
	public void close() {
		checkersTable = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see y.utils.TimerEvent#handleTimer(long)
	 */
	@Override
	public void handleTimer(long l) {
		if (reply[0] != null && !reply[0].equals(reply[1])) {
			reply[1] = reply[0];
			tcc_f.append("***" + reply[1], Color.blue);
		}
	}

	@Override
	public boolean parseCommand(String command) {
		if (command.equals("/toggle random") || command.equals("/tr")) {
			checkersTable.toggleRandom();
			if (checkersTable.isRandom())
				tcc_f.append("***random enabled", Color.blue);
			else
				tcc_f.append("***random disabled", Color.blue);
			return true;
		}
		else if (command.equals("/best move") || command.equals("/bm")) {
			reply[1] = "evaluating...";
			tcc_f.append("***" + reply[1], Color.blue);
			final TimerEntry selfTimer = checkersTable.getTimerHandler().add(
					this, 100);
			Thread thread = new Thread("Evaluator") {
				@Override
				public void run() {
					LinkedList<Square> move = checkersTable.checkers.bestMove(
							checkersTable.mySitIndex, 1200000L, reply);
					selfTimer.stop();
					reply[0] = null;
					reply[1] = null;
					tcc_f.append("***evaluate complete", Color.blue);
					checkersTable.makeUniqueMove(move);
				}
			};
			thread.start();
			return true;
		}
		else if (command.equals("/play now") || command.equals("/pn")) {
			checkersTable.checkers.playNow();
			return true;
		}
		else if (command.equals("/toggle computer") || command.equals("/tc")) {
			checkersTable.computer = !checkersTable.computer;
			return true;
		}
		return super.parseCommand(command);
	}

}
