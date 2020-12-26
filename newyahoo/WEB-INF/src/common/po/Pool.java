// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import common.utils.ByteArrayData;
import common.yutils.Game;
import common.yutils.GameHandler;
import common.yutils._cls159;

// Referenced classes of package y.po:
// _cls95, _cls159, _cls111, _cls160,
// _cls40, _cls89, _cls121, _cls124,
// _cls171, _cls138, _cls129, _cls100,
// _cls60, _cls147, _cls6, _cls131,
// _cls120, _cls16, _cls46, _cls48,
// _cls164, _cls12

public class Pool extends Game implements PoolConsts, PoolEngineHandler,
		PoolParams {

	public static boolean	O;
	public static boolean	logMessages;
	static int[]			_d	= { 0x0001, 0x0010, 0x0020, 0x0008, 0x0004,
			0x0040, 0x0002, 0x0020, 0x0080, 0x0010, 0x0008, 0x0004, 0x0100,
			0x0100, 0x0080, 0x0040 };
	static int[]			_f	= { 0x0000, 0x0400, 0x0800, 0x0400, 0x0800,
			0x0400, 0x0000, 0x0400, 0x0400, 0x0800, 0x0800, 0x0400, 0x0800,
			0x0400, 0x0800, 0x0800 };

	public static int getTimer(Hashtable<String, String> hashtable) {
		int i1 = -1;
		if (hashtable.containsKey("timer")) {
			String s1 = hashtable.get("timer");
			try {
				int j1 = Integer.parseInt(s1);
				if (j1 > 0)
					i1 = j1 >= 5 ? j1 : 5;
			}
			catch (NumberFormatException _ex) {
			}
		}
		return i1;
	}

	public static boolean isAutomat(Hashtable<String, String> hashtable) {
		return hashtable.containsKey("automat");
	}

	public static boolean isNineBallGame(Hashtable<String, String> hashtable) {
		return hashtable.containsKey("nineBallGame");
	}

	public static boolean isTraining(Hashtable<String, String> hashtable) {
		return hashtable.containsKey("training");
	}

	PoolHandler							handler;
	public IBall						ball[];
	public Setup						setup;
	public PoolEngine					poolEngine;
	public boolean						training;
	public boolean						g;
	private Hashtable<String, Integer>	propertyes;
	long								i;
	public int							m_turn;
	public int							m_turnNum;
	public int							type0;
	public int							type1;
	boolean								n;
	int									o;
	boolean								m_playedBefore;
	int									q;
	public Vector<IBall>				turnPocketed;
	public IBall						firstCollidedBall;
	public boolean						turnCollided;
	public boolean						pocketed;
	public boolean						sideCollided;
	public boolean						firstStrike;
	public boolean						x;
	public boolean						m_aimStateInit;
	private boolean						z;
	public int							selectedSlotIndex;
	public int							m_currentState;

	private PoolData					G;

	private int							index;

	private YIPoint						cueDist;

	private YIPoint						englishDist;

	private YIPoint						firstColl;

	private int							collBall;

	public Pool() {
		super();
		training = false;
		g = false;
		i = System.currentTimeMillis();
		m_turnNum = 0;
		type0 = 0;
		type1 = 0;
		n = false;
		o = 0;
		m_playedBefore = false;
		q = -1;
		turnPocketed = new Vector<IBall>();
		turnCollided = false;
		pocketed = false;
		sideCollided = false;
		x = false;
		m_aimStateInit = true;
		selectedSlotIndex = -1;
	}

	public boolean actionReset(int turn) {
		if (m_turn == turn && training) {
			reset();
			handler.td();
			if (logMessages)
				handler.logState("Pool.actionReset");
			handler.zd(m_turn, true);
			return true;
		}
		return false;
	}

	public boolean Aj(IBall _pcls124) {
		return setup.ql(_pcls124);
	}

	@Override
	public boolean allSitsStarted() {
		byte byte0 = (byte) (training ? 1 : 2);
		return super.sitStarteds >= byte0 && !super.running;
	}

	public void assign(Pool pool) {
		running = pool.running;
		i = pool.i;
		m_turn = pool.m_turn;
		q = pool.q;
		m_currentState = pool.m_currentState;
		m_turnNum = pool.m_turnNum;
		type0 = pool.type0;
		type1 = pool.type1;
		m_aimStateInit = pool.m_aimStateInit;
		selectedSlotIndex = pool.selectedSlotIndex;
		int i1 = pool.ball.length;
		for (int j1 = 0; j1 < i1; j1++) {
			PoolBall _lcls171 = new PoolBall();
			_lcls171.setPool(this);
			_lcls171.setHandler(handler);
			_lcls171.assign((PoolBall) pool.ball[j1]);
			ball[_lcls171.getIndex()] = _lcls171;
		}

		initializeEngine();
		// d.read(datainputstream);
		setup.initializeWB();
		handler.rd();
	}

	public boolean Bj(IBall _pcls124) {
		boolean flag = false;
		int i1 = 0;
		for (IBall _lcls124 : ball) {
			if (!_lcls124.equals(_pcls124) && _lcls124.isInitPos())
				i1++;
		}

		if (i1 > 5)
			flag = true;
		return flag;
	}

	public void changeTurn(boolean change, boolean ballInHand) {
		if (ballInHand)
			m_aimStateInit = true;
		if (change)
			m_turn = 1 - m_turn;
		selectedSlotIndex = setup.getSlotIndex();
		if (selectedSlotIndex != -1)
			handler.selectSlot(selectedSlotIndex);
		m_turnNum++;
		m_currentState = 0;
		if (logMessages)
			handler.logState("Pool.changeTurn.");
		handler.zd(m_turn, m_aimStateInit);
	}

	@Override
	public void close() {
		handler = null;
		ball = null;
		setup = null;
		if (poolEngine != null) {
			poolEngine.close();
			poolEngine = null;
		}
		if (propertyes != null) {
			propertyes.clear();
			propertyes = null;
		}
		if (turnPocketed != null) {
			turnPocketed.clear();
			turnPocketed = null;
		}
		firstCollidedBall = null;
	}

	@Override
	public void configDoStart() {
		if (q == -1) {
			_cls159 _lcls159 = new _cls159(i);
			m_turn = _lcls159.cy(2);
		}
		else {
			m_turn = 1 - q;
		}
		if (training)
			m_turn = 0;
		q = m_turn;
		m_turnNum = 0;
		reset();
		handler.Sc();
		if (logMessages)
			handler.logState("Pool.configDoStart.");
		handler.zd(m_turn, m_aimStateInit);
	}

	public boolean doNotifyTELAPS(PoolData _pcls131) {
		if (_pcls131 != null) {
			poolEngine.stop();
			int index;
			int x;
			int y;
			int slot;
			for (int i1 = 0; i1 < _pcls131.turnInArea.getCount(); setPos(index,
					x, y, slot)) {
				index = _pcls131.turnInArea.getInteger(i1++);
				x = _pcls131.turnInArea.getInteger(i1++);
				y = _pcls131.turnInArea.getInteger(i1++);
				slot = _pcls131.turnInArea.getInteger(i1++);
			}

		}
		handler.Ad();
		changeTurn(true, true);
		return true;
	}

	public boolean doNotifyTStat(PoolData _pcls131, boolean flag) {
		if (m_currentState != 1 && m_currentState != 2)
			return false;
		if (O && z && flag) {
			boolean flag1 = Bj(getBall(index));
			for (IBall element : ball)
				element.Pv(flag1);

			firstStrike = isFirstStrike();
			ball[index].start(cueDist, englishDist, firstColl, collBall);
			int turnStat = 0;
			System.err.println(m_turn + " Calculate new. cleared="
					+ _pcls131.cleared + "\n");
			for (; poolEngine.movingExist() && turnStat < 400; turnStat++) {
				poolEngine.handleStop();
				if (logMessages && turnStat % 10 == 1)
					System.err.println("nt=" + turnStat);
			}

			PoolData _lcls131 = tj();
			if (turnStat < 400) {
				System.err.println("\nTurnStat Server Calculated in "
						+ turnStat + "\n");
				_lcls131.Kw(_pcls131);
			}
			else {
				System.err.println("Can't calculate server nt=400 y ts="
						+ _pcls131.clientTs());
			}
		}
		poolEngine.stop();
		x = false;
		firstCollidedBall = null;
		turnPocketed.clear();
		turnCollided = _pcls131.turnCollided;
		sideCollided = _pcls131.c;
		if (turnCollided)
			firstCollidedBall = ball[_pcls131.firstCollidedBall];
		pocketed = _pcls131.turnPocketed.getCount() != 0;
		if (pocketed) {
			for (int i1 = 0; i1 < _pcls131.turnPocketed.getCount(); i1++)
				turnPocketed.add(ball[_pcls131.turnPocketed.getInteger(i1)]);

		}
		int index;
		int x;
		int y;
		int slot;
		for (int j1 = 0; j1 < _pcls131.turnInArea.getCount(); setPos(index, x,
				y, slot)) {
			index = _pcls131.turnInArea.getInteger(j1++);
			x = _pcls131.turnInArea.getInteger(j1++);
			y = _pcls131.turnInArea.getInteger(j1++);
			slot = _pcls131.turnInArea.getInteger(j1++);
		}

		int i2 = setup.getState();
		switch (i2) {
		case 0: // '\0'
			changeTurn(setup.isTurnChanged(), false);
			break;

		case 1: // '\001'
			Yj(setup.Zo());
			break;

		case 2: // '\002'
			m_currentState = 3;
			handler.kd(m_turn);
			break;
		}
		return true;
	}

	@Override
	public boolean doResign(int i1) {
		if (super.running) {
			Yj(i1 != m_turn);
			return true;
		}
		return false;
	}

	public boolean doSetSlot(int turn, int slot) {
		if (m_turn == turn && m_currentState == 0 && setup.getSlotIndex() != -1) {
			selectedSlotIndex = slot;
			handler.selectSlot(slot);
			return true;
		}
		return false;
	}

	@Override
	public boolean doStop() {
		if (isRunning()) {
			doStop(null);
			return true;
		}
		return false;
	}

	public boolean doStrike(int turn, int _index, YIPoint _cueDist,
			YIPoint _englishDist, YIPoint _firstColl, int _collBall) {
		if (m_turn != turn)
			return false;
		if (m_currentState != 0)
			return false;
		pocketed = false;
		turnCollided = false;
		sideCollided = false;
		firstCollidedBall = null;
		turnPocketed.clear();
		setup.So();
		for (IBall _lcls124 : ball) {
			_lcls124.setNull();
		}

		index = _index;
		cueDist = _cueDist;
		englishDist = _englishDist;
		firstColl = _firstColl;
		collBall = _collBall;
		firstStrike = isFirstStrike();
		synchronized (poolEngine) {
			m_currentState = 1;
			if (!z) {
				boolean flag = Bj(getBall(index));
				for (IBall element : ball)
					element.Pv(flag);

				handler.xd(turn);
				ball[_index].start(_cueDist, _englishDist, _firstColl,
						_collBall);
			}
		}
		return true;
	}

	public boolean doUpdateCue(int turn) {
		if (m_turn == turn && m_currentState == 0) {
			handler.handleUpdateCue(turn);
			return true;
		}
		return false;
	}

	public boolean doUpdateEnglish(int i1) {
		if (m_turn == i1 && m_currentState == 0) {
			handler.handleUpdateEnglish(i1);
			return true;
		}
		return false;
	}

	public boolean doUpdatePB(int turn, int index, int x, int y) {
		if (Fj(turn, index, x, y)) {
			poolEngine.setPos(index, x, y, -1);
			handler.qd(ball[index]);
			return true;
		}
		return false;
	}

	public boolean Fj(int turn, int index, int x, int y) {
		IBall _lcls124 = getBall(index);
		if (m_turn != turn || !super.running || m_currentState != 0
				|| !setup.ql(_lcls124))
			return false;
		if (!poolEngine.pointInPlayArea(x, y) || !poolEngine.J(index, x, y))
			return false;
		return !setup.nl() || !_lcls124.equals(setup.getWhiteBall())
				|| !isFirstStrike()
				|| setup.getAimBallInitArea().containsPoint(x, y);
	}

	public boolean getAimStateInit() {
		if (training)
			return true;
		return m_aimStateInit;
	}

	public IBall[] getBall() {
		return ball;
	}

	public IBall getBall(int i1) {
		return ball[i1];
	}

	@SuppressWarnings("unchecked")
	public Vector<IBall> getBallInPlayArea() {
		return (Vector<IBall>) poolEngine.ballInPlayArea.clone();
	}

	public int getCurrentState() {
		return m_currentState;
	}

	public int getIntProperty(String s1) {
		Object obj = getProperty(s1);
		if (obj instanceof Integer)
			return ((Integer) obj).intValue();
		return -1;
	}

	public PoolEngine getPoolEngine() {
		return poolEngine;
	}

	public Object getProperty(String s1) {
		Object obj = setup.get(s1);
		if (obj == null)
			obj = propertyes.get(s1);
		return obj;
	}

	public Slot getSelectedSlot() {
		if (selectedSlotIndex == -1)
			return null;
		return ((Slot[]) getProperty("SLOTS"))[selectedSlotIndex];
	}

	public int getSelectedSlotIndex() {
		return selectedSlotIndex;
	}

	public Setup getSetup() {
		return setup;
	}

	public void handleCollBalls(IBall _pcls124, IBall _pcls124_1) {
		if (!turnCollided) {
			firstCollidedBall = _pcls124.isMoving() ? _pcls124_1 : _pcls124;
			turnCollided = true;
			if (handler != null)
				handler.handleFirtsColl(firstCollidedBall);
		}
		if (handler != null)
			if (_pcls124.getVel().abs() > PoolMath.n_5
					|| _pcls124_1.getVel().abs() > PoolMath.n_5)
				handler.handleColl(0);
			else
				handler.handleColl(1);
	}

	public void handleIterate() {
		handler.handleIterate();
	}

	public void handlePocket(IBall _pcls124) {
		turnPocketed.add(_pcls124);
		pocketed = true;
	}

	public void handleShiftFromIntersect() {
		handler.handleShiftFromIntersect();
	}

	public void handleSideColl(IBall _pcls124) {
		sideCollided = true;
	}

	public void handleStop() {
		handler.handleStopMoving();
	}

	public void initializeEngine() {
		poolEngine.initialize((YRectangle) getProperty("PLAY_AREA_BALLS"),
				(Obstacle[]) getProperty("OBSTACLES"),
				(Slot[]) getProperty("SLOTS"), ball);
	}

	@Override
	public void initializeProperties(Hashtable<String, String> hashtable,
			GameHandler handler) {
		this.handler = (PoolHandler) handler;
		propertyes = new Hashtable<String, Integer>();
		G = new PoolData();
		if (isTraining(hashtable))
			training = true;
		if (isAutomat(hashtable)) {
			g = true;
			training = true;
		}
		int i1 = getTimer(hashtable);
		propertyes.put("INIT_TIME_PER_MOVE", new Integer(i1));
		if (training)
			Zj(new TrainingSetup(this));
		else if (isNineBallGame(hashtable))
			Zj(new NineBallSetup(this));
		else
			Zj(new EightBallSetup(this));
		poolEngine = new PoolEngine(this);
		ball = new IBall[setup.getBallCount()];
		m_turnNum = 0;
	}

	@Override
	public boolean isCurrentTurn(int i1) {
		return m_turn == i1 && (m_aimStateInit || !useInitTimePorMove());
	}

	public boolean isFirstStrike() {
		boolean flag = true;
		for (IBall _lcls124 : ball) {
			if (_lcls124.equals(setup.getWhiteBall())
					|| setup.getInitPos(_lcls124.getIndex()).same(
							(YIPoint) _lcls124))
				continue;
			flag = false;
			break;
		}

		return flag;
	}

	public boolean isInSlot(IBall ball) {
		return !poolEngine.ballInSlot.contains(ball);
	}

	public boolean isSameTurn(int i1) {
		return m_turn == i1;
	}

	public int nj() {
		return type0;
	}

	@Override
	public void read(DataInput input) throws IOException {
		running = input.readBoolean();
		i = input.readLong();
		m_turn = input.readInt();
		q = input.readInt();
		m_currentState = input.readInt();
		m_turnNum = input.readInt();
		type0 = input.readInt();
		type1 = input.readInt();
		m_aimStateInit = input.readBoolean();
		selectedSlotIndex = input.readInt();
		int i1 = input.readInt();
		for (int j1 = 0; j1 < i1; j1++) {
			PoolBall _lcls171 = new PoolBall();
			_lcls171.setPool(this);
			_lcls171.setHandler(handler);
			_lcls171.read(input);
			ball[_lcls171.getIndex()] = _lcls171;
		}

		initializeEngine();
		setup.read(input);
		setup.initializeWB();
		handler.rd();
	}

	@Override
	public void refresh() {
		super.refresh();
	}

	public void reset() {
		poolEngine.reset();
		m_aimStateInit = true;
		o = 0;
		n = false;
		type0 = 0;
		type1 = 0;
		turnPocketed.clear();
		firstCollidedBall = null;
		turnCollided = false;
		pocketed = false;
		sideCollided = false;
		selectedSlotIndex = -1;
		m_currentState = 0;
		setup.initializeWB();
	}

	public boolean selectType(int i1, int j1) {
		if (m_turn == i1 && m_currentState == 3) {
			Xj(j1);
			m_currentState = 0;
			changeTurn(false, false);
			return true;
		}
		return false;
	}

	public boolean setPos(int index, int x, int y, int slot) {
		return poolEngine.setPos(index, x, y, slot);
	}

	@Override
	public void setup() {
		running = false;
		i = 0x00000110F1E74C51L;
		m_turn = 0;
		q = -1;
		m_currentState = 0;
		m_turnNum = 0;
		type0 = 0;
		type1 = 0;
		m_aimStateInit = true;
		selectedSlotIndex = -1;
		int i1 = 16;
		for (int j1 = 0; j1 < i1; j1++) {
			PoolSetup poolSetup = (PoolSetup) setup;
			YIPoint p = poolSetup.getInitPos(j1);
			PoolBall _lcls171 = new PoolBall();
			_lcls171.index = j1;
			_lcls171.setCoords(p.a, p.b);
			_lcls171.x0 = p.a;
			_lcls171.y0 = p.b;
			_lcls171.radius = 0x000A0000;
			_lcls171.m_inSlot = -1;
			_lcls171.d = _d[j1];
			_lcls171.type = _f[j1];
			_lcls171.setPool(this);
			_lcls171.setHandler(handler);
			ball[j1] = _lcls171;
		}

		initializeEngine();
		// d.read(datainputstream);
		setup.initializeWB();
		handler.rd();
	}

	public void Sj(IBall _pcls124, int i1, int j1) {
		poolEngine.M(_pcls124, i1, j1);
	}

	public PoolData tj() {
		G.reset();
		G.turnCollided = turnCollided;
		G.c = sideCollided;
		if (turnCollided)
			G.firstCollidedBall = firstCollidedBall.getIndex();
		if (pocketed) {
			for (int i1 = 0; i1 < turnPocketed.size(); i1++) {
				IBall _lcls124 = turnPocketed.elementAt(i1);
				G.turnPocketed.writeInt(_lcls124.getIndex());
			}

		}
		for (IBall element : ball) {
			G.turnInArea.writeInt(element.getIndex());
			G.turnInArea.writeInt(element.getX());
			G.turnInArea.writeInt(element.getY());
			G.turnInArea.writeInt(element.getSlot());
		}

		return G;
	}

	@Override
	public String toString() {
		String s1 = "Pool \n";
		s1 = s1 + "m_turn=" + m_turn + "\n";
		s1 = s1 + "m_playedBefore=" + m_playedBefore + "\n";
		s1 = s1 + "m_aimStateInit=" + m_aimStateInit + "\n";
		s1 = s1 + "m_currentState=" + m_currentState + "\n";
		s1 = s1 + "m_turnNum=" + m_turnNum + "\n";
		for (IBall element : ball)
			s1 = s1 + element + "\n";

		s1 = s1 + "last strike data.\n";
		s1 = s1 + "cueDist=" + cueDist;
		s1 = s1 + "englishDist=" + englishDist;
		s1 = s1 + "firstColl=" + firstColl;
		s1 = s1 + "collBall=" + collBall;
		return s1;
	}

	public boolean useInitTimePorMove() {
		return getIntProperty("INIT_TIME_PER_MOVE") != -1;
	}

	public void Vj() {
	}

	public void Wj(boolean flag) {
		m_aimStateInit = flag;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeBoolean(super.running);
		output.writeLong(i);
		output.writeInt(m_turn);
		output.writeInt(q);
		output.writeInt(m_currentState);
		output.writeInt(m_turnNum);
		output.writeInt(type0);
		output.writeInt(type1);
		output.writeBoolean(m_aimStateInit);
		output.writeInt(selectedSlotIndex);
		output.writeInt(ball.length);
		for (IBall _lcls124 : ball) {
			_lcls124.write(output);
		}

		setup.write(output);
	}

	public boolean xj(IBall _pcls124) {
		return setup.isWhiteBall(_pcls124);
	}

	public void Xj(int i1) {
		int j1 = i1 != 1024 ? 1024 : 2048;
		type0 = i1;
		type1 = j1;
		handler.Bd(type0);
		x = true;
	}

	public boolean yj() {
		IBall _lcls124 = setup.getWhiteBall();
		boolean flag = true;
		for (IBall _lcls124_1 : ball) {
			if (_lcls124_1.equals(_lcls124)
					|| getSetup().getInitPos(_lcls124_1.getIndex()).same(
							(YIPoint) _lcls124_1))
				continue;
			flag = false;
			break;
		}

		return flag;
	}

	public void Yj(boolean flag) {
		if (!isRunning())
			return;
		ByteArrayData _lcls40 = new ByteArrayData(2);
		byte byte0 = (byte) (flag ? 1 : 2);
		byte byte1 = (byte) (flag ? 2 : 1);
		_lcls40.setByte(m_turn, byte0);
		_lcls40.setByte(1 - m_turn, byte1);
		String s1 = handler.Xc(flag ? 1 - m_turn : m_turn);
		if (s1 != null)
			m_playedBefore = true;
		handler.nd(_lcls40);
		doStop(_lcls40);
		selectedSlotIndex = -1;
	}

	public void Zj(Setup _pcls100) {
		setup = _pcls100;
	}

}
