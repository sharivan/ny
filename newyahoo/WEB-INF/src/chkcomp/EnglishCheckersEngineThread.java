/**
 * 
 */
package chkcomp;

import y.utils.Square;

import common.k.LinkedList;

/**
 * @author saddam
 * 
 */
public class EnglishCheckersEngineThread extends Thread {

	EnglishCheckersEngineThreadHandler	handler;
	int[][]								b;
	int									turn;
	long								maxTime;
	String[]							reply;

	public EnglishCheckersEngineThread(
			EnglishCheckersEngineThreadHandler handler, int[][] b, int turn,
			long maxTime) {
		super(EnglishCheckersEngineThreadHandler.class.getName());
		this.handler = handler;
		this.b = b;
		this.turn = turn;
		this.maxTime = maxTime;
		reply = new String[1];
		start();
	}

	public String getReply() {
		return reply[0];
	}

	@Override
	public void run() {
		EnglishCheckersEngine.playNow = false;
		LinkedList<Square> best = EnglishCheckersEngine.bestMove(b, turn,
				maxTime, reply);
		handler.handleFinish(best);
	}

}
