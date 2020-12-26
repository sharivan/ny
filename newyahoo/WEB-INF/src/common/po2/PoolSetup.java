// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;

// Referenced classes of package y.po:
// _cls89, _cls100, _cls68, _cls164,
// _cls169, _cls154, _cls120, _cls16,
// _cls46, _cls57, _cls124

public abstract class PoolSetup implements Setup {

	protected static Hashtable<String, Object>	propertyes;
	static {
		propertyes = new Hashtable<String, Object>();
		float byte1 = 10;
		float width = 478;
		float height = 228;
		YPoint initPoint = new YPoint(PoolConsts.initPoint[0],
				PoolConsts.initPoint[1]);
		YRectangle playArea = new YRectangle(initPoint.x, initPoint.y, width,
				height);
		propertyes.put("PLAY_AREA", playArea);
		YPoint centerPoint = new YPoint(initPoint.x + width / 2F, initPoint.y
				+ height / 2F);
		propertyes.put("CENTER_POINT", centerPoint);
		YRectangle inArea = new YRectangle(initPoint.x + 9, initPoint.y + 9,
				width - 18, height - 18);
		propertyes.put("IN_AREA", inArea);
		YRectangle playAreaBalls = new YRectangle(initPoint.x + 10, initPoint.y
				+ byte1, width - byte1 * 2, height - byte1 * 2);
		propertyes.put("PLAY_AREA_BALLS", playAreaBalls);
		playAreaBalls = new YRectangle(initPoint.x + 10, initPoint.y + 10,
				width - 20, height - 20);
		propertyes.put("PLAY_AREA_AIM", playAreaBalls);
		YPoint _lcls169 = new YPoint(initPoint.x + byte1 + 1, initPoint.y
				+ byte1 + 1);
		playArea = new YRectangle((int) _lcls169.x, (int) _lcls169.y, width
				- (2 * byte1 + 2), height - (2 * byte1 + 2));
		propertyes.put("OUT_OF_BOUNCE_AREA", playArea);
		_lcls169.setCoords(initPoint.x + 6, initPoint.y + 6);
		playArea = new YRectangle((int) _lcls169.x, (int) _lcls169.y,
				width - 12, height - 12);
		propertyes.put("OUT_OF_POCKET_AREA", playArea);
		float i = 0.191F * 0.157F * 9.8F;
		propertyes.put("linearFriction", i);
		i = 0.008F * 0.157F * 9.8F;
		propertyes.put("rotationFriction", i);
		i = 0.0195F * 0.157F * 9.8F;
		propertyes.put("sideRotationFriction", i);
		i = 6F * 0.157F;
		propertyes.put("cushionFriction", i);
		i = 8F * 0.157F;
		propertyes.put("cushionSideFriction", i);
		i = 0.001F * 0.157F * 9.8F;
		propertyes.put("momentumFriction", i);
		YPoint aimBallInitPoint = new YPoint(PoolConsts.aimBallInitPoint[0],
				PoolConsts.aimBallInitPoint[1]);
		propertyes.put("AIM_BALL_INIT_POINT", aimBallInitPoint);
		YPoint aimBallInitial = new YPoint(PoolConsts.aimBallInitial[0],
				PoolConsts.aimBallInitial[1]);
		propertyes.put("AIM_BALL_INITIAL", aimBallInitial);
		YPoint aimBallFinalPoint = new YPoint(PoolConsts.aimBallFinalPoint[0],
				PoolConsts.aimBallFinalPoint[1]);
		propertyes.put("AIM_BALL_FINAL_POINT", aimBallFinalPoint);
		YRectangle aimBallInitArea = new YRectangle(initPoint.x, initPoint.y,
				119, 228);
		propertyes.put("AIM_BALL_INIT_AREA", aimBallInitArea);
		Obstacle obstacle[] = new Obstacle[22];
		obstacle[0] = new Obstacle(PoolConsts.obstacle1);
		obstacle[1] = new Obstacle(PoolConsts.obstacle2);
		obstacle[2] = new Obstacle(PoolConsts.obstacle3);
		obstacle[3] = new Obstacle(PoolConsts.obstacle4);
		obstacle[4] = new Obstacle(PoolConsts.obstacle5);
		obstacle[5] = new Obstacle(PoolConsts.obstacle6);
		obstacle[6] = new Obstacle(PoolConsts.obstacle7);
		obstacle[7] = new Obstacle(PoolConsts.obstacle8);
		obstacle[8] = new Obstacle(PoolConsts.obstacle9);
		obstacle[9] = new Obstacle(PoolConsts.obstacle10);
		obstacle[10] = new Obstacle(PoolConsts.obstacle11);
		obstacle[11] = new Obstacle(PoolConsts.obstacle12);
		obstacle[12] = new Obstacle(PoolConsts.obstacle13);
		obstacle[13] = new Obstacle(PoolConsts.obstacle14);
		obstacle[14] = new Obstacle(PoolConsts.obstacle15);
		obstacle[15] = new Obstacle(PoolConsts.obstacle16);
		obstacle[16] = new Obstacle(PoolConsts.obstacle17);
		obstacle[17] = new Obstacle(PoolConsts.obstacle18);
		obstacle[18] = new Obstacle(PoolConsts.obstacle19);
		obstacle[19] = new Obstacle(PoolConsts.obstacle20);
		obstacle[20] = new Obstacle(PoolConsts.obstacle21);
		obstacle[21] = new Obstacle(PoolConsts.obstacle22);
		propertyes.put("OBSTACLES", obstacle);
		Slot slot[] = new Slot[6];
		slot[0] = new Slot(0, PoolConsts.slot1);
		slot[1] = new Slot(1, PoolConsts.slot2);
		slot[2] = new Slot(2, PoolConsts.slot3);
		slot[3] = new Slot(3, PoolConsts.slot4);
		slot[4] = new Slot(4, PoolConsts.slot5);
		slot[5] = new Slot(5, PoolConsts.slot6);
		propertyes.put("SLOTS", slot);
	}

	public static Object getProperty(String s) {
		Object obj = propertyes.get(s);
		return obj;
	}

	protected Pool		pool;

	protected boolean	b;

	protected boolean	turnChanged;

	public PoolSetup(Pool _pcls57) {
		pool = _pcls57;
	}

	protected void _p() {
	}

	public boolean ap() {
		return false;
	}

	public Object get(String s) {
		return propertyes.get(s);
	}

	public YRectangle getAimBallInitArea() {
		return new YRectangle(0, 0, 0, 0);
	}

	public int getBallCount() {
		return 8;
	}

	public YPoint getInitPos(int i) {
		return new YPoint(0, 0);
	}

	public int getSlotIndex() {
		return -1;
	}

	public int getState() {
		return 0;
	}

	public IBall getWhiteBall() {
		return null;
	}

	public void initializeWB() {
	}

	public boolean isFaul() {
		return false;
	}

	public boolean isTurnChanged() {
		return turnChanged;
	}

	public abstract boolean isWhiteBall(IBall _pcls124);

	public boolean nl() {
		return false;
	}

	public abstract boolean ql(IBall _pcls124);

	public void read(DataInput input) throws IOException {
	}

	public void So() {
	}

	public int Uo() {
		return -1;
	}

	public boolean whiteBallPocketed() {
		return false;
	}

	public void write(DataOutput output) throws IOException {
	}

	public boolean Zo() {
		return b;
	}
}
