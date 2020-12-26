// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooTextBox;
import y.utils.Formater;
import y.utils.TimerEngine;
import y.utils.TimerEntry;
import y.utils.TimerHandler;
import y.ycontrols.SaveCancel;
import y.ycontrols.TableControlContainer;
import y.ydialogs.AllStarDialog;
import y.yutils.YahooGamesTable;

import common.io.YData;
import common.po.EightBallSetup;
import common.po.IBall;
import common.po.NineBallSetup;
import common.po.Obstacle;
import common.po.Pool;
import common.po.PoolBall;
import common.po.PoolData;
import common.po.PoolHandler;
import common.po.PoolMath;
import common.po.Slot;
import common.po.Vel;
import common.po.YIPoint;
import common.po.YIVector;
import common.po.YRectangle;
import common.po.YVector;
import common.utils.ByteArrayData;

// Referenced classes of package y.po:
// _cls99, _cls145, _cls86, _cls50,
// _cls56, _cls174, _cls3, _cls170,
// _cls58, _cls175, _cls23, _cls0,
// _cls24, _cls106, _cls40, _cls105,
// _cls57, _cls89, _cls121, _cls153,
// _cls124, _cls138, _cls129, _cls100,
// _cls6, _cls131, _cls169, _cls108,
// _cls16, _cls46, _cls48, _cls168,
// _cls35, _cls78, _cls79, _cls87,
// _cls177, _cls33, _cls111

public class YahooPoolTable extends YahooGamesTable implements PoolHandler,
		TimerHandler, PoolAreaHandler {

	public Pool		pool;
	PoolArea		poolArea;
	PoolAimer		poolAimer;
	TimerEngine		timerHandler;
	TimerEntry		poolTimer;
	TimerEntry		poolAimerTimer;
	TimerEntry		thisTimer;
	private long	cueTime;
	private long	updateTime;
	boolean			k;
	boolean			ypt_l;
	boolean			m;
	boolean			n;
	boolean			ypt_o;
	boolean			p;
	boolean			ypt_q;
	int				ypt_r;
	int				ypt_s;
	int				t;
	int				u;
	int				v;
	String			cueSound;
	String			ballSound;
	String			break_longSound;
	String			break_shortSound;
	String			pocketSound;
	String			cushionSound;
	String			ball_softSound;
	private int		update;
	public boolean	ypt_E;
	YIPoint			cueDist;
	YIPoint			englishDist;
	YIPoint			firstColl;
	YVector			ypt_I;
	PoolData		J;
	String			state;
	YahooLabel		lblTime;
	YahooTextBox	txtTime;

	public YahooPoolTable() {
		cueTime = 0L;
		updateTime = 0L;
		k = true;
		ypt_l = true;
		m = false;
		n = true;
		ypt_o = false;
		p = false;
		ypt_q = false;
		ypt_r = 0;
		ypt_s = 0;
		t = 0;
		u = 0;
		v = 0;
		cueSound = "yog/resource/pool/cue.au";
		ballSound = "yog/resource/pool/ball.au";
		break_longSound = "yog/resource/pool/break-long.au";
		break_shortSound = "yog/resource/pool/break-short.au";
		pocketSound = "yog/resource/pool/pocket.au";
		cushionSound = "yog/resource/pool/cushion.au";
		ball_softSound = "yog/resource/pool/ball-soft.au";
		update = 1;
		cueDist = new YIPoint();
		englishDist = new YIPoint();
		firstColl = new YIPoint();
		ypt_I = new YVector();
		J = new PoolData();
		state = "";
		addSitParser(new SaveCancel(this));
	}

	public void actionChangeCue(byte byte1, DataInputStream datainputstream)
			throws IOException {
		poolArea.cueSprite.read(datainputstream);
		pool.doUpdateCue(byte1);
	}

	public void actionChangeCue(byte byte1, int i1, float f1, float f2,
			float f3, float f4, int power) {
		poolArea.cueSprite.cueTip.setCoords(f1, f2);
		poolArea.cueSprite.cueTipOffset.setCoords(f3, f4);
		poolArea.cueSprite.power = power;
	}

	public void actionChangeEnglish(byte byte2, DataInputStream datainputstream)
			throws IOException {
		poolArea.english.read(datainputstream);
		pool.doUpdateEnglish(byte2);
	}

	public void actionChangePoolBall(int k1, int j2, int l2) {
		logState("updatePB " + k1 + " " + j2 + " " + l2);
		pool.doUpdatePB(pool.m_turn, k1, j2, l2);
	}

	public void actionStrike() {

	}

	@Override
	public void activateControls(int i1) {
		super.activateControls(i1);
		if (pool.training)
			poolArea._c();
		if (pool.isRunning()) {
			poolArea.hideCue();
			poolArea.deactivate();
		}
	}

	public void Ad() {
		Hc();
		if (poolAimer != null)
			poolArea.poolAimer.ls();
		poolArea.lblTime.setCaption(Formater.formatTimer(0L));
		poolArea.hideCue();
		poolArea.deactivate();
		if (poolAimer != null)
			poolAimer.wb();
		poolArea.releasePowerbar();
		if (poolAimer != null)
			poolAimer.wb();
	}

	public void Bd(int i1) {
		poolArea.vc(i1);
	}

	@Override
	public void cd(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 18, 0, 0, 1, 1, 0, 0);
	}

	@Override
	public void close() {
		if (poolTimer != null) {
			timerHandler.remove(poolTimer);
			poolTimer = null;
		}
		if (poolAimerTimer != null) {
			timerHandler.remove(poolAimerTimer);
			poolAimerTimer = null;
		}
		if (thisTimer != null) {
			timerHandler.remove(thisTimer);
			thisTimer = null;
		}
		pool = null;

		super.close();
	}

	@Override
	public void createArea() {
		update = ((YahooPool) getApplet()).update;
		pool = (Pool) getGame();
		poolArea = new PoolArea(this);
		poolAimer = poolArea.getPoolAimer();
		if (isSmallWidows()) {
			addYahooObject(poolArea.english);
			poolArea.english.setVisible(true);
		}
		if (pool.getSetup() instanceof EightBallSetup)
			addObject(new YahooLabel(getApplet().lookupString(0x665016b5)), -1);
		else if (pool.getSetup() instanceof NineBallSetup)
			addObject(new YahooLabel(getApplet().lookupString(0x665016b6)), -1);
		else
			addObject(new YahooLabel(getApplet().lookupString(0x665016b7)), -1);
		super.createArea();
		poolArea.setBackColor();
		pool.getSetup().getBallCount();
		timerHandler = getTimerHandler();
		poolTimer = timerHandler.add(pool.getPoolEngine(), 15); // 15
		poolAimerTimer = timerHandler.add(poolAimer, 200);
		thisTimer = timerHandler.add(this, 15); // 15
		long l1 = 0xfffffffffe386f04L;
		long l2 = 0xfffffe386f040000L;
		l1 <<= 16;
		long bshift = l1;
		int j1 = 0xfffe9b97;
		long l4 = 0xfffffffffffe9b97L;
		long l5 = l1 / j1;
		long l6 = l1 / l4;
		long l7 = l2 / j1;
		long l8 = l2 / l4;
		String s1 = "";
		s1 = s1 + l5;
		s1 = s1 + ",bshift=" + bshift + " LMAX=" + 0x7fffffffffffffffL
				+ " LMIN=" + 0x8000000000000000L + " long/long=" + l6 + " "
				+ l7 + " " + l8;
		s1 = s1 + System.getProperty("java.version") + " "
				+ System.getProperty("java.vendor");
		send('\uFF9F', s1);
	}

	@Override
	public TableControlContainer createTableControlContainer() {
		return new PoolControlContainer(this);
	}

	public boolean cueActive() {
		if (poolArea.cueSprite == null)
			return false;
		return poolArea.cueSprite.wk();
	}

	@Override
	public void dd(YahooControl _pcls79, YahooComponent _pcls78) {
		if (!isSmallWidows())
			super.dd(_pcls79, _pcls78);
	}

	@Override
	public void deactivateControls(int i1) {
		super.deactivateControls(i1);
		if (pool.training)
			poolArea.zc();
		if (pool.isRunning() && isMyTurn())
			Jd();
	}

	public void doChangeEnglish(byte byte2, int i1, float x, float y) {
		poolArea.english.e_n.x = x;
		poolArea.english.e_n.y = y;
	}

	public void doUpdateState() {
		if (ypt_E) {
			String s1 = "";
			s1 = s1 + System.getProperty("java.version") + " ";
			s1 = s1 + System.getProperty("java.vendor") + "\n";
			logState("turnStat sent turn=" + pool.m_turnNum);
			PoolData _lcls131 = pool.tj();
			send('\uFF85', pool.m_turnNum, _lcls131);
		}
	}

	private void Fc() {
		ypt_q = false;
		ypt_r = 0;
		ypt_s = 0;
		t = 0;
		v = 0;
		u = 0;
	}

	public void Fd(String s1) {
		Lq(getMySitIndex(), s1);
	}

	public boolean get_n() {
		return n;
	}

	public Vector<IBall> getBallInPlayArea() {
		return pool.getBallInPlayArea();
	}

	public YRectangle getBounceArea() {
		return (YRectangle) pool.getProperty("OUT_OF_BOUNCE_AREA");
	}

	public YIPoint getCenterPoint() {
		return (YIPoint) pool.getProperty("CENTER_POINT");
	}

	@Override
	public YahooControl getGameArea() {
		return poolArea;
	}

	public YRectangle getInArea() {
		return (YRectangle) pool.getProperty("IN_AREA");
	}

	public int getLinearFriction() {
		return pool.getIntProperty("linearFriction");
	}

	public Obstacle[] getObstacles() {
		return (Obstacle[]) pool.getProperty("OBSTACLES");
	}

	public YRectangle getPlayArea() {
		return (YRectangle) pool.getProperty("PLAY_AREA_BALLS");
	}

	public YRectangle getPocketArea() {
		return (YRectangle) pool.getProperty("OUT_OF_POCKET_AREA");
	}

	public Pool getPool() {
		return pool;
	}

	public Object getProperty(String s1) {
		return pool.getProperty(s1);
	}

	public int getRotationFriction() {
		return pool.getIntProperty("rotationFriction");
	}

	public int getSideRotationFriction() {
		return pool.getIntProperty("sideRotationFriction");
	}

	public int getSitCount() {
		return !pool.training ? 2 : 1;
	}

	public Slot[] getSlots() {
		return (Slot[]) pool.getProperty("SLOTS");
	}

	public void handleColl(int i1) {
		ypt_q = true;
		switch (i1) {
		case 0: // '\0'
			ypt_r++;
			break;

		case 1: // '\001'
			ypt_s++;
			break;

		case 2: // '\002'
			t++;
			break;

		case 3: // '\003'
			v++;
			break;

		case 4: // '\004'
			u++;
			break;
		}
	}

	public void handleFirtsColl(IBall ball) {

	}

	public void handleIterate() {
		if (poolAimer != null)
			poolAimer.wb();
	}

	public void handleSetPos(int i1, int j1, int k1, int l1, YIVector _pcls48,
			Vel _pcls33) {
		if (_pcls48 != null)
			ypt_I.setCoords(PoolMath.yintToFloat(_pcls48.a), PoolMath
					.yintToFloat(_pcls48.b));
		else
			ypt_I.setCoords(0.0F, 0.0F);
		poolArea.fc(i1, j1, k1, l1, ypt_I, _pcls33);
	}

	public void handleShiftFromIntersect() {
		doUpdateState();
	}

	@Override
	public void handleStart() {
		super.handleStart();
		poolArea.xc();
		poolArea.yc();
		if (isMyTurn()) {
			Jd();
			if (k) {
				Lq(getMySitIndex(), getApplet().lookupString(0x66501217));
				k = false;
			}
		}
		else {
			Id();
		}
		poolArea.swapArrow(pool.m_turn);
		poolArea.vc(pool.nj());
		int i1;
		if ((i1 = pool.getSetup().Uo()) != -1) {
			poolArea.vc(-1);
			poolArea.qc(pool.m_turn, i1);
		}
	}

	@Override
	public void handleStop(YData data) {
		super.handleStop(data);
	}

	public void handleStopMoving() {

	}

	public synchronized void handleTimer(long l1) {
		try {
			long l3 = System.currentTimeMillis();
			if (l3 - cueTime >= 10L) {
				poolArea.cueSprite.el(l1);
				cueTime = l3;
			}
			if (isMyTurn() && pool.getCurrentState() == 0
					&& l1 - updateTime >= update * 1000) {
				if (poolArea.cueSprite.isChanged()) {
					send('\uFF80', poolArea.cueSprite);
					poolArea.cueSprite.setChanged(false);

				}
				if (poolArea.english.isChanged()) {
					send('\uFF81', poolArea.english);
					poolArea.english.setChanged(false);
				}
				updateTime = l1;
			}
			if (ypt_q)
				playPoolSound();
			Fc();
		}
		catch (NullPointerException nullpointerexception) {
			nullpointerexception.printStackTrace();
		}
	}

	public void handleUpdateCue(int i1) {
		if (getMySitIndex() != i1) {
			poolArea.cueSprite.setVisible(true);
			poolArea.cueSprite.update();
			poolArea.pullCue(poolArea.cueSprite.power1);
		}
	}

	public void handleUpdateEnglish(int i1) {
		if (getMySitIndex() != i1)
			poolArea.english.update();
	}

	public boolean haveInitTimePorMove() {
		return pool.getIntProperty("INIT_TIME_PER_MOVE") > 0;
	}

	public void Hc() {
		Lq(-3, "");
	}

	public void Hd() {
		if (ypt_o) {
			Lq(getMySitIndex(), getApplet().lookupString(0x66501214));
			ypt_o = false;
			return;
		}
		if (k) {
			Lq(getMySitIndex(), getApplet().lookupString(0x66501217));
			k = false;
			return;
		}
		if (m) {
			Lq(getMySitIndex(), getApplet().lookupString(0x66501259));
			m = false;
			return;
		}
		if (pool.getAimStateInit()) {
			Lq(getMySitIndex(), getApplet().lookupString(0x6650125d));
			return;
		}
		if (p) {
			Lq(getMySitIndex(), getApplet().lookupString(0x66501253));
			return;
		}
		Lq(getMySitIndex(), getApplet().lookupString(0x66501254));
		p = true;
		return;
	}

	public void Id() {
		poolArea.deactivate();
		p = false;
	}

	public boolean isMyTurn() {
		return pool.isSameTurn(getMySitIndex());
	}

	public void Jd() {
		poolArea.oc();
		if (pool.isRunning()) {
			poolArea.activate();
			poolArea.yc();
			int i1 = pool.getSetup().Uo();
			if (i1 != -1)
				poolArea.cueSprite.Tk(((YIPoint) pool.getBall(i1)).toYVector());
		}
		Hd();
		beep();
	}

	@Override
	public boolean Kc() {
		return false;
	}

	public void kd(int i1) {
		if (getMySitIndex() == i1) {
			poolArea.hideCue();
			new SelectTypeDialog(this, poolArea.getContainer());
		}
		else {
			Lq(getMySitIndex(), getSitIdCaption(i1)
					+ getApplet().lookupString(0x665011c5));
		}
	}

	@Override
	public void Kd(int i1) {
		super.Kd(i1);
		if (i1 >= 0 && pool.getCurrentState() == 0 && pool.useInitTimePorMove()) {
			poolArea.lblTime.setCaption(Formater.formatTimer(i1 + 999));
			if (i1 == 0 && isMyTurn())
				send('\uFF91', pool.m_turnNum);
		}
	}

	public void logState(String s1) {
		if (state.length() > 200)
			state = state.substring(state.length() - 180);
		state += "\n" + s1;
		// System.out.println(s1);
	}

	public String lookupString(int i) {
		return applet.lookupString(i);
	}

	public void nd(YData _pcls111) {
		String s1 = "YOU";
		poolArea.hideCue();
		if (!pool.training)
			s1 = ((ByteArrayData) _pcls111).byteAt(0) != 1 ? getSitIdCaption(1)
					: getSitIdCaption(0);
		if (!pool.g)
			showQuickMessage(s1 + getApplet().lookupString(0x66501413));
		if (pool.g)
			startgame();
	}

	@Override
	public synchronized void parseData(byte byte0,
			DataInputStream datainputstream) throws IOException {
		switch (byte0) {
		case -128: // 80: change cue
			byte byte1 = datainputstream.readByte();
			actionChangeCue(byte1, datainputstream);
			break;

		case -113: // 8F
			break;

		case -127: // 81: change english
			byte byte2 = datainputstream.readByte();
			actionChangeEnglish(byte2, datainputstream);
			break;

		case -125: // 83: reset
			byte byte3 = datainputstream.readByte();
			pool.actionReset(byte3);
			break;

		case -122: // 86: select type
			int i1 = datainputstream.readInt();
			pool.selectType(pool.m_turn, i1);
			break;

		case -115: // 8D: change slot
			int j1 = datainputstream.readInt();
			pool.doSetSlot(pool.m_turn, j1);
			break;

		case -102: // 9A: change pool ball
			int k1 = datainputstream.readInt();
			int j2 = datainputstream.readInt();
			int l2 = datainputstream.readInt();
			actionChangePoolBall(k1, j2, l2);
			break;

		case -126: // 82: strike
			byte byte4 = datainputstream.readByte();
			int k2 = datainputstream.readInt();
			byte byte5 = datainputstream.readByte();
			cueDist.read(datainputstream);
			englishDist.read(datainputstream);
			firstColl.read(datainputstream);
			logState("strike recvd " + byte4 + " " + k2);
			if (byte4 != getMySitIndex())
				pool
						.doStrike(byte4, k2, cueDist, englishDist, firstColl,
								byte5);
			break;

		case -112: // 90: time empty
			J.reset();
			J.read(datainputstream);
			logState("notifyTELAPS");
			pool.doNotifyTELAPS(J);
			break;

		case -101: // 9B: change table state
			ypt_E = false;
			J.reset();
			J.read(datainputstream);
			Xc(0);
			Xc(1);
			logState("notifyTStat.");
			pool.doNotifyTStat(J, false);
			logMessage("***moving off", Color.green);
			break;

		case -105: // 97: change time
			int l1 = datainputstream.readInt();
			if (pool.isRunning()) {
				poolArea.lblTime.setCaption(Formater.formatTimer(l1 / 1000));
				handleStartTick(l1);
			}
			break;

		case -96: // A0
			datainputstream.readInt();
			break;
		case -95: // A1
			datainputstream.readInt();
			// a_y_em_fld.e(i2);
			return;

		case -94: // A2
			j2 = datainputstream.readInt();
			datainputstream.readInt();
			// a_y_em_fld.a_y_cw_fld.c(j2, i3);
			// a_y_if_fld.b("Got aimballdiameter=" + j2);
			// a_y_if_fld.b("Got aimballcolor=" + i3);
			return;

		case -124: // 84
		case -123: // 85
		case -121: // 87
		case -120: // 88
		case -119: // 89
		case -118: // 8A
		case -117: // 8B
		case -116: // 8C
		case -114: // 8E
		case -111: // 91
		case -110: // 92
		case -109: // 93
		case -108: // 94
		case -107: // 95
		case -106: // 96
		case -104: // 98
		case -103: // 99
		case -100: // 9C
		case -99: // 9D
		case -98: // 9E
		case -97: // 9F
		default:
			super.parseData(byte0, datainputstream);
			break;
		}
	}

	public void pd() {
		new AllStarDialog(getApplet(), poolArea.getContainer(), getApplet()
				.lookupString(0x6650169c));
	}

	private void playPoolSound() {
		if (!soundEnabled())
			return;
		String s2 = null;
		if (u > 0)
			s2 = pocketSound;
		else if (ypt_r > 5)
			s2 = ballSound;
		else if (ypt_r > 2)
			s2 = ballSound;
		else if (ypt_r > 0)
			s2 = ballSound;
		else if (v > 0)
			s2 = cueSound;
		else if (ypt_s > 0)
			s2 = ball_softSound;
		else if (t > 0)
			s2 = cushionSound;
		if (s2 != null) {
			String s1 = s2;
			getApplet().playSound(s1);
		}
	}

	public void qd(IBall _pcls124) {
		poolArea.hc(_pcls124);
	}

	public void rd() {
		poolArea.gc();
	}

	public void reset() {
		if (pool.isRunning()) {
			send('\uFF83');
		}
	}

	@Override
	public void rq() {
		lblTime = new YahooLabel("Time");
		txtTime = new YahooTextBox(getTimerHandler());
		txtTime.setText("4000");
		super.rq();
	}

	public void Sc() {
		poolArea.xc();
		poolArea.yc();
		poolArea.activate();
		poolArea.swapArrow(pool.m_turn);
		poolArea.vc(pool.nj());
		if (pool.getSetup().ap())
			poolArea.wc();
		int i1;
		if ((i1 = pool.getSetup().Uo()) != -1) {
			poolArea.vc(-1);
			poolArea.qc(pool.m_turn, i1);
		}
		if (!pool.training)
			if (isMyTurn()) {
				Jd();
				if (k) {
					Lq(getMySitIndex(), getApplet().lookupString(0x66501217));
					k = false;
				}
			}
			else {
				Id();
			}
	}

	public void selectMyType(int i1) {
		if (isMyTurn() && pool.getCurrentState() == 3) {
			if (getMySitIndex() == 1)
				if (i1 == 1024)
					i1 = 2048;
				else
					i1 = 1024;
			send('\uFF95', i1);
		}
	}

	public void selectSlot(int i1) {
		poolArea.sc(i1);
	}

	public void set_n(boolean value) {
		n = value;
	}

	public void setSlot(int i1) {
		if (isMyTurn() && pool.isRunning() && pool.getCurrentState() == 0
				&& pool.getSelectedSlotIndex() != -1)
			send('\uFF8E', i1);
	}

	@Override
	public void sit(int i1) {
		if (pool.getSetup() instanceof NineBallSetup) {
			if (getApplet().idPropertyContains(8L)
					|| ((YahooPool) getApplet()).yp_d)
				super.sit(i1);
			else
				pd();
			return;
		}
		super.sit(i1);
		return;
	}

	public void strike() {
		if (isMyTurn() && pool.isRunning() && pool.getCurrentState() == 0) {
			IBall selectedBall = poolArea.cueSprite.getSelectedBall();
			YIPoint cueDist = poolArea.cueSprite.getPos(true).copy();
			YIPoint englishDist = poolArea.english.getPos().copy();
			YIPoint firstColl = poolAimer.getFirstColl();
			int collBall = poolAimer.getIndex();
			logMessage("***moving on", Color.green);
			boolean flag = pool.Bj(selectedBall);
			if (flag) {
				YIVector u = new YIVector((YIPoint) selectedBall, cueDist);
				float f1 = 5F;
				if (collBall == 1)
					f1 *= 1.3F;
				int l1 = PoolMath.floatToYInt(((float) Math.random() - 0.5F)
						* f1);
				u.mul(PoolMath.floatToYInt(1.4F));
				u.b = PoolMath.add(u.b, l1);
				cueDist = ((PoolBall) selectedBall).newCopy();
				cueDist.add(u);
				firstColl = new YIPoint(0, 0);
			}
			if (collBall != -1) {
				IBall _ball[] = pool.getBall();
				for (IBall item : _ball) {
					if (item.getIndex() != collBall
							&& item.distance(firstColl) < PoolMath
									.intToYInt(21))
						collBall = -1;
				}

			}
			// System.out.println("cueDist=" + cueDist + "; englishDist="
			// + englishDist + "; firstColl=" + firstColl + "; collBall="
			// + collBall);
			strike(selectedBall.getIndex(), cueDist, englishDist, firstColl,
					collBall);
		}
	}

	public void strike(int index, YIPoint cueDist, YIPoint englishDist,
			YIPoint firstColl, int collBall) {
		logState("strike done and sent turn=" + pool.m_turnNum + " seat="
				+ getMySitIndex());
		send('\uFF82', pool.m_turnNum, index, (byte) collBall, cueDist,
				englishDist, firstColl);
		pool.poolEngine.active = false;
		pool.doStrike(getMySitIndex(), index, cueDist, englishDist, firstColl,
				collBall);
		pool.poolEngine.resetLog();
		pool.poolEngine.active = true;
	}

	public void td() {
		poolArea.xc();
	}

	public void updatePB(int i1, int j1, int k1) {
		if (isMyTurn() && pool.isRunning() && pool.getCurrentState() == 0) {
			logMessage("***ball index=" + i1, Color.blue);
			send('\uFF9A', i1, j1, k1);
		}
	}

	public String Xc(int i1) {
		if (i1 >= getSitCount())
			return "X";
		return getSitIdCaption(i1);
	}

	public void xd(int i1) {
		poolArea.hideCue();
		if (poolAimer != null)
			poolArea.poolAimer.ls();
		poolArea.cueSprite.power1 = 0;
		poolArea.releasePowerbar();
		poolArea.Yb();
		ypt_E = true;
	}

	public void zd(int i1, boolean flag) {
		poolArea.swapArrow(i1);
		if (pool.getSetup().ap())
			poolArea.wc();
		int j1;
		if ((j1 = pool.getSetup().Uo()) != -1) {
			poolArea.vc(-1);
			poolArea.qc(i1, j1);
		}
		Lq(-3, "");
		if (pool.getAimStateInit())
			Lq(-3, getSitIdCaption(i1) + getApplet().lookupString(0x665011c6));
		if (isMyTurn())
			Jd();
		else
			Id();
		if (!pool.training) {
			int k1 = pool.getIntProperty("INIT_TIME_PER_MOVE");
			if (k1 > 0) {
				if (pool.getAimStateInit())
					k1 = 180;
				poolArea.lblTime.setCaption(Formater.formatTimer(k1));
				handleStartTick(k1 * 1000);
			}
			else {
				handleStartTick(180000);
			}
		}
	}

}
