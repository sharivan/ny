package common.po;

import java.util.Vector;

public interface PoolParams {

	public Vector<IBall> getBallInPlayArea();

	public int getIntProperty(String name);

	public Object getProperty(String name);

}
