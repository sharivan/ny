/**
 * 
 */
package chkcomp;

import java.util.Vector;

import y.utils.Square;

import common.k.BoardGame;
import common.k.BoardSquare;
import common.k.LinkedList;

/**
 * @author saddam
 * 
 */
public class EnglishCheckersEngineTest implements
		EnglishCheckersEngineThreadHandler {

	EnglishCheckersEngineThread			engine;
	static int[][]						testboard	= {
			{ 0, 1, 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 2, 0, 2, 0, 2, 0, 2, 0 },
			{ 0, 2, 0, 2, 0, 2, 0, 2 }, { 2, 0, 2, 0, 2, 0, 2, 0 } };

	boolean								finished;
	int									currTurn;

	static EnglishCheckersEngineTest	instance;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance = new EnglishCheckersEngineTest();
	}

	public EnglishCheckersEngineTest() {
		currTurn = 1;
		for (int i = 0; i < 20; i++) {
			System.out.println(BoardGame.boardToString(testboard));
			System.out.println("evaluating...");
			finished = false;
			engine = new EnglishCheckersEngineThread(this, testboard, currTurn,
					1000L);
			String reply = "";
			while (!finished) {
				String reply1 = engine.getReply();
				if (reply1 != null && !reply1.equals(reply)) {
					reply = new String(reply1);
					System.out.println(reply);
				}
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("evaluate complete");
		}
		System.out.println(BoardGame.boardToString(testboard));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * chkcomp.EnglishCheckersEngineThreadHandler#handleFinish(common.k.LinkedList
	 * )
	 */
	@Override
	public synchronized void handleFinish(LinkedList<Square> best) {
		finished = true;
		Vector<BoardSquare> result = new Vector<BoardSquare>();
		EnglishCheckersEngine.domove(result, testboard, best, currTurn);
		currTurn = 1 - currTurn;
	}

}
