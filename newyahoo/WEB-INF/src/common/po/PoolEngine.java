package common.po;

import java.util.Vector;

import y.list.StringList;
import y.utils.TimerHandler;

import common.utils.ClockHandler;
import common.utils.ReverseClock;

public class PoolEngine implements ClockHandler, TimerHandler {
	public static Obstacle		obstacles[];
	static int					n_317		= PoolMath.intToYInt(317);
	static int					n_60		= PoolMath.intToYInt(60);
	static int					n_470		= PoolMath.intToYInt(470);
	static int					n_5			= PoolMath.intToYInt(5);
	static int					n_0			= PoolMath.intToYInt(0);
	public PoolEngineHandler	handler;
	public IBall				ball[];
	public Vector<IBall>		ballInPlayArea;
	public Vector<IBall>		ballInSlot;
	public Slot					slots[];
	public boolean				active;
	boolean						currFlag;
	public Vector<IBall>		q;
	public Vector<IBall>		r;
	public Vector<IBall>		s;
	int							t;
	int							u;
	YRectangle					playArea;
	YIVector					v2;
	YIVector					v1;
	public StringList			physicsLog;
	public boolean				logMessages	= true;
	public int					iterateAreaCounter;
	private YIVector			B;
	public boolean				moving		= false;

	public PoolEngine(PoolEngineHandler _pcls147) {
		physicsLog = new StringList();
		resetLog();
		ballInPlayArea = new Vector<IBall>();
		ballInSlot = new Vector<IBall>();
		q = new Vector<IBall>();
		r = new Vector<IBall>();
		s = new Vector<IBall>();
		u = 40;
		v2 = new YIVector();
		v1 = new YIVector();
		B = new YIVector(0, PoolMath.n_5);
		handler = _pcls147;
	}

	private void addPhysicsLog(String s) {
		physicsLog.add(s);
	}

	/**
	 * 
	 */
	public void close() {
		handler = null;
		ball = null;
		if (ballInPlayArea != null) {
			ballInPlayArea.clear();
			ballInPlayArea = null;
		}
		if (ballInSlot != null) {
			ballInSlot.clear();
			ballInSlot = null;
		}
		slots = null;
		if (q != null) {
			q.clear();
			q = null;
		}
		if (r != null) {
			r.clear();
			r = null;
		}
		if (s != null) {
			s.clear();
			s = null;
		}
		playArea = null;
		v2 = null;
		v1 = null;
		if (physicsLog != null) {
			physicsLog.clear();
			physicsLog = null;
		}
		B = null;
	}

	private void E() {
		if (movingExist() || !r.isEmpty()) {
			return;
		}
		if (handler != null)
			handler.handleShiftFromIntersect();
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.utils.ClockHandler#handlePause(common.utils.ReverseClock)
	 */
	@Override
	public void handlePause(ReverseClock reverseClock) {
		// TODO Auto-generated method stub

	}

	public void handleStop() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see y.utils.TimerEvent#handleTimer(long)
	 */
	@Override
	public void handleTimer(long l) {
		if (active) {
			if (iteratePlayArea() && handler != null)
				handler.handleIterate();
			if (iterateBallInSlot() && handler != null)
				handler.handleIterate();
			if (t++ == u) {
				N();
				t = 0;
			}
			boolean moving1 = movingExist();
			if (!moving1 && moving && handler != null)
				handler.handleStop();
			moving = moving1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.utils.ClockHandler#handleStop(common.utils.ReverseClock)
	 */
	@Override
	public void handleTimer(ReverseClock reverseClock) {
		handleTimer(reverseClock.getInterval());
	}

	public void initialize(YRectangle _playArea, Obstacle _obstacles[],
			Slot _slots[], IBall _balls[]) {
		playArea = _playArea;
		obstacles = _obstacles;
		slots = _slots;
		ball = _balls;
		for (IBall element : ball)
			if (element.inSlot()) {
				ballInSlot.add(element);
				q.add(element);
			}
			else {
				ballInPlayArea.add(element);
			}

		active = true;
	}

	public boolean iterateBallInSlot() {
		if (s.isEmpty())
			return false;
		Vector<IBall> vector = new Vector<IBall>();
		for (int i1 = 0; i1 < s.size(); i1++)
			vector.add(s.elementAt(i1));

		for (int j1 = 0; j1 < vector.size(); j1++) {
			IBall _lcls124 = vector.elementAt(j1);
			if (_lcls124.getX() >= n_470) {
				_lcls124.stop();
				s.removeElement(_lcls124);
				q.add(_lcls124);
				P();
			}
			else {
				for (int k1 = 0; k1 < q.size(); k1++) {
					IBall _lcls124_1 = q.elementAt(k1);
					int l1 = _lcls124.timeToBall(_lcls124_1);
					if (l1 >= PoolMath.n_1)
						continue;
					_lcls124.Kv(l1);
					_lcls124.stop();
					s.removeElement(_lcls124);
					q.add(_lcls124);
					P();
					break;
				}

			}
			if (s.contains(_lcls124))
				_lcls124.Kv(PoolMath.n_1);
		}

		return true;
	}

	public synchronized boolean iteratePlayArea() {
		int minTimeToCollBall = PoolMath.n_1;
		int minTimeToObstacle = PoolMath.n_1;
		int tnTick = PoolMath.n_1;
		iterateAreaCounter++;
		while (tnTick > 0) {
			if (logMessages && tnTick < PoolMath.n_1)
				addPhysicsLog(iterateAreaCounter + " tnTick=" + tnTick);
			for (int l1 = 0; l1 < ballInPlayArea.size(); l1++) {
				IBall currBall = ballInPlayArea.elementAt(l1);
				currBall.checkSlots(slots);
				if (currBall.inSlot()) {
					ballInPlayArea.removeElement(currBall);
					ballInSlot.add(currBall);
					L(currBall);
					if (handler != null)
						handler.handlePocket(currBall);
				}
			}

			IBall clS = null;
			IBall cb2 = null;
			IBall cb1 = null;
			for (int j2 = 0; j2 < ballInPlayArea.size(); j2++) {
				IBall ballj2 = ballInPlayArea.elementAt(j2);
				int currTime1 = ballj2.timeToObstacle(obstacles);
				if (currTime1 < minTimeToObstacle && currTime1 < tnTick
						&& currTime1 >= 0) {
					minTimeToObstacle = currTime1;
					clS = ballj2;
					if (handler != null)
						handler.handleSideColl(ballj2);
				}
				for (int i3 = 0; i3 < j2; i3++) {
					IBall balli3 = ballInPlayArea.elementAt(i3);
					int currTime2 = ballj2.timeToBall(balli3);
					if (logMessages && currTime2 < PoolMath.n_1
							&& currTime2 > 0)
						addPhysicsLog("ttc " + ballj2.getIndex() + " "
								+ balli3.getIndex() + " " + currTime2);
					if (currTime2 <= minTimeToCollBall && currTime2 > 0
							&& currTime2 <= tnTick) {
						if (logMessages)
							addPhysicsLog("2bs pI " + ballj2.getIndex() + " "
									+ balli3.getIndex());
						minTimeToCollBall = currTime2;
						cb1 = ballj2;
						cb2 = balli3;
						if (handler != null)
							handler.handleCollBalls(cb2, cb1);
					}
				}

			}

			int cE = Math.min(Math.min(minTimeToCollBall, tnTick),
					minTimeToObstacle);
			boolean flag = false;
			if (logMessages && cE < PoolMath.n_1)
				addPhysicsLog(iterateAreaCounter + " cE=" + cE);
			for (int j3 = 0; j3 < ballInPlayArea.size(); j3++)
				if (ballInPlayArea.elementAt(j3).Kv(cE))
					flag = true;

			if (currFlag && !flag) {
				for (int l3 = 0; l3 < ballInPlayArea.size() - 1; l3++) {
					IBall currBall = ballInPlayArea.elementAt(l3);
					for (int i4 = l3 + 1; i4 < ballInPlayArea.size(); i4++) {
						IBall _lcls124_8 = ballInPlayArea.elementAt(i4);
						if (currBall.distance((YIPoint) _lcls124_8) < PoolMath
								.intToYInt(20)) {
							addPhysicsLog("shiftFromIntersect " + currBall);
							shiftFromIntersect(currBall);
						}
					}

				}

				E();
			}
			currFlag = flag;
			if (minTimeToObstacle == cE && clS != null) {
				if (logMessages)
					addPhysicsLog("clS " + clS + " "
							+ ((PoolBall) clS).obstacle);
				clS.pv();
			}
			if (minTimeToCollBall == cE && cb2 != null && cb1 != null) {
				if (logMessages)
					addPhysicsLog("cb1" + cb1 + "\n cb2" + cb2);
				v2.setFrom(cb2.getVel());
				v1.setFrom(cb1.getVel());
				if (cb2.getIndex() < cb1.getIndex()) {
					cb2.ov(cb1, v1);
					cb1.ov(cb2, v2);
				}
				else {
					cb1.ov(cb2, v2);
					cb2.ov(cb1, v1);
				}
			}
			minTimeToObstacle = minTimeToCollBall = tnTick -= cE;
		}
		for (int i2 = 0; i2 < ballInPlayArea.size(); i2++) {
			IBall currBall = ballInPlayArea.elementAt(i2);
			currBall.nextPosition();
		}
		return currFlag;
	}

	public boolean J(int index, int x, int y) {
		for (IBall _lcls124 : ball) {
			if (index != _lcls124.getIndex()
					&& _lcls124.distance(x, y) < PoolMath.mul(_lcls124
							.getRadius(), PoolMath.n_2))
				return false;
		}
		return true;
	}

	public void L(IBall _pcls124) {
		r.add(_pcls124);
	}

	public void M(IBall _pcls124, int i1, int j1) {
		boolean flag = true;
		YIPoint _lcls46 = new YIPoint(i1, j1);
		YIPoint _lcls46_1 = new YIPoint(i1, PoolMath.intToYInt(11));
		YIPoint _lcls46_2 = new YIPoint(i1, PoolMath.intToYInt(217));
		_pcls124.setCoords(_lcls46.a, _lcls46.b);
		while (flag) {
			boolean flag1 = false;
			for (IBall _lcls124 : ball) {
				if (_pcls124.equals(_lcls124)
						|| _pcls124.distance((YIPoint) _lcls124) > _pcls124
								.getRadius() * 2)
					continue;
				flag1 = true;
				break;
			}

			if (flag1) {
				((YIPoint) _pcls124).add(B);
				if (_pcls124.getY() >= _lcls46_2.b)
					_pcls124.setCoords(_pcls124.getX() - PoolMath.n_5,
							_lcls46_1.b);
			}
			else {
				flag = false;
			}
		}
		setPos(_pcls124.getIndex(), _pcls124.getX(), _pcls124.getY(), -1);
	}

	public boolean movingExist() {
		boolean flag = false;
		for (int i1 = 0; i1 < ball.length; i1++) {
			if (!ball[i1].isMoving())
				continue;
			flag = true;
			// if(logMessages)
			// addPhysicsLog("moving " + ball[i1] + "\n");
			break;
		}

		if (!r.isEmpty())
			flag = true;
		return flag;
	}

	public void N() {
		if (!r.isEmpty()) {
			IBall _lcls124 = r.lastElement();
			r.removeElement(_lcls124);
			_lcls124.setCoords(n_60, n_317);
			s.add(_lcls124);
			_lcls124.getVel().set(n_5, n_0);
			_lcls124.Bv().setVel(PoolMath.floatToYInt(-0.5F));
		}
	}

	private void P() {
		E();
	}

	public boolean pointInPlayArea(int i1, int j1) {
		return playArea.containsPoint(i1, j1);
	}

	public void reset() {
		stop();
		q.clear();
		r.clear();
		s.clear();
		ballInPlayArea.clear();
		ballInSlot.clear();
		for (IBall element : ball) {
			element.resetBall();
			ballInPlayArea.add(element);
		}

	}

	public void resetLog() {
		physicsLog.clear();
		physicsLog.add("PhysicsLog");
		iterateAreaCounter = 0;
	}

	private void S() {
		int i1 = q.size();
		if (i1 > 0) {
			IBall _lcls124 = q.elementAt(0);
			if (_lcls124.getX() < n_470)
				_lcls124.setPos(n_470, n_317);
		}
		for (int j1 = 1; j1 < q.size(); j1++) {
			PoolBall _lcls171 = (PoolBall) q.elementAt(j1);
			PoolBall _lcls171_1 = (PoolBall) q.elementAt(j1 - 1);
			if (_lcls171_1.getX() - _lcls171.getX() > _lcls171.getRadius() * 2)
				_lcls171.setPos(_lcls171_1.getX() - _lcls171.getRadius() * 2,
						n_317);
		}

	}

	public boolean setPos(int index, int x, int y, int slot) {
		IBall _lcls124 = ball[index];
		if (slot >= 0 && !_lcls124.inSlot()) {
			ballInPlayArea.removeElement(_lcls124);
			s.removeElement(_lcls124);
			r.removeElement(_lcls124);
			ballInSlot.add(_lcls124);
			q.add(_lcls124);
		}
		if (slot == -1 && _lcls124.inSlot()) {
			/*
			 * if(!pointInPlayArea(x, y) || !J(index, x, y)) return false;
			 */
			ballInSlot.removeElement(_lcls124);
			q.removeElement(_lcls124);
			s.removeElement(_lcls124);
			r.removeElement(_lcls124);
			ballInPlayArea.add(_lcls124);
			S();
		}
		_lcls124.setSlot((byte) slot);
		_lcls124.setPos(x, y);
		return true;
	}

	private void shiftFromIntersect(IBall _pcls124) {
		YIVector _lcls48 = new YIVector((YIPoint) _pcls124, new YIPoint(
				playArea.left + playArea.width / 2, playArea.top
						+ playArea.height / 2));
		_lcls48.versor();
		boolean flag = true;
		while (flag) {
			boolean flag1 = false;
			for (int i1 = 0; i1 < ballInPlayArea.size(); i1++) {
				IBall _lcls124 = ballInPlayArea.elementAt(i1);
				if (!_pcls124.equals(_lcls124)
						&& _pcls124.distance((YIPoint) _lcls124) < PoolMath
								.intToYInt(20))
					flag1 = true;
			}

			if (flag1) {
				((YIPoint) _pcls124).add(_lcls48);
				flag = true;
			}
			else {
				flag = false;
			}
		}
	}

	public void stop() {
		for (IBall element : ball)
			element.stop();

	}
}
