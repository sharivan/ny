package common.po2;

import java.util.Vector;

public interface PoolParams {

	public Vector<IBall> getBallInPlayArea();

	public float getFloatProperty(String name);

	public Object getProperty(String name);

}
