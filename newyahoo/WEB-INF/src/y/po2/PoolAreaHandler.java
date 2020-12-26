package y.po2;

import java.util.Vector;

import y.ycontrols.SitContainer;

import common.po2.IBall;
import common.po2.Obstacle;
import common.po2.Pool;
import common.po2.Slot;
import common.po2.YPoint;
import common.po2.YRectangle;

public interface PoolAreaHandler {

	public boolean cueActive();

	public void Fd(String s);

	public boolean get_n();

	public Vector<IBall> getBallInPlayArea();

	public YRectangle getBounceArea();

	public YPoint getCenterPoint();

	public YRectangle getInArea();

	public float getLinearFriction();

	public int getMySitIndex();

	public Obstacle[] getObstacles();

	public YRectangle getPlayArea();

	public YRectangle getPocketArea();

	public Pool getPool();

	public Object getProperty(String name);

	public float getRotationFriction();

	public float getSideRotationFriction();

	public int getSitCount();

	public Slot[] getSlots();

	public void handleColl(int i);

	public boolean haveInitTimePorMove();

	public void Hd();

	public boolean isMyTurn();

	public boolean isSmallWidows();

	public String lookupString(int i);

	public void reset();

	public void set_n(boolean value);

	public void setSlot(int l1);

	public void strike();

	public SitContainer Sz(int i);

	public void updatePB(int index, float x, float y);

}
