package y.po;

import java.util.Vector;

import y.ycontrols.SitContainer;

import common.po.IBall;
import common.po.Obstacle;
import common.po.Pool;
import common.po.Slot;
import common.po.YIPoint;
import common.po.YRectangle;

public interface PoolAreaHandler {

	public boolean cueActive();

	public void Fd(String s);

	public boolean get_n();

	public Vector<IBall> getBallInPlayArea();

	public YRectangle getBounceArea();

	public YIPoint getCenterPoint();

	public YRectangle getInArea();

	public int getLinearFriction();

	public int getMySitIndex();

	public Obstacle[] getObstacles();

	public YRectangle getPlayArea();

	public YRectangle getPocketArea();

	public Pool getPool();

	public Object getProperty(String name);

	public int getRotationFriction();

	public int getSideRotationFriction();

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

	public void updatePB(int index, int x, int y);

}
