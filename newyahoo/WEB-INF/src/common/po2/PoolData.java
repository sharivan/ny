// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;
import common.utils.Buffer;

// Referenced classes of package y.po:
// _cls111, _cls160, _cls16

public class PoolData implements YData {

	public boolean	turnCollided;
	public boolean	c;
	public int		firstCollidedBall;
	public Buffer	turnPocketed;
	public Buffer	turnInArea;
	public boolean	cleared;

	public PoolData() {
		cleared = true;
		firstCollidedBall = -1;
		turnPocketed = new Buffer();
		turnInArea = new Buffer();
	}

	public String clientTs() {
		String s = " turnCollided=" + turnCollided + " firstCollidedBall="
				+ firstCollidedBall + " turnPocketed=" + turnPocketed + "\n";
		for (int i = 0; i < turnInArea.getCount();) {
			int j = turnInArea.getInteger(i++);
			s = s + "[" + j;
			float k = turnInArea.getFloat(i++);
			s = s + " " + k;
			float l = turnInArea.getFloat(i++);
			s = s + " " + l;
			int i1 = turnInArea.getInteger(i++);
			s = s + " " + i1 + "], ";
		}

		return s;
	}

	public void Kw(PoolData _pcls131) {
		_pcls131.turnCollided = turnCollided;
		_pcls131.c = c;
		_pcls131.firstCollidedBall = firstCollidedBall;
		_pcls131.turnPocketed.reset();
		_pcls131.turnInArea.reset();
		for (int i = 0; i < turnPocketed.getCount(); i++)
			_pcls131.turnPocketed.writeInt(turnPocketed.getInteger(i));

		for (int j = 0; j < turnInArea.getCount(); j++)
			_pcls131.turnInArea.writeInt(turnInArea.getInteger(j));

		_pcls131.cleared = false;
	}

	public void read(DataInput input) throws IOException {
		turnCollided = input.readBoolean();
		c = input.readBoolean();
		firstCollidedBall = input.readInt();
		int i = input.readInt();
		for (int j = 0; j < i; j++)
			turnPocketed.writeInt(input.readInt());

		int k = input.readInt();
		for (int l = 0; l < k; l++) {
			int i1 = input.readInt();
			turnInArea.writeInt(i1);
		}

		cleared = false;
	}

	public void read(YPoint[] points) {
		turnCollided = true;
		c = false;
		firstCollidedBall = -1;

		turnPocketed.reset();
		/*
		 * for(int i = 0; i < i; i++) turnPocketed.writeInt();
		 */

		for (YPoint point : points) {
			turnInArea.writeFloat(point.x);
			turnInArea.writeFloat(point.y);
		}
		cleared = false;
	}

	public void reset() {
		c = false;
		turnCollided = false;
		firstCollidedBall = -1;
		turnPocketed.reset();
		turnInArea.reset();
		cleared = true;
	}

	public void write(DataOutput output) throws IOException {
		output.writeBoolean(turnCollided);
		output.writeBoolean(c);
		output.writeInt(firstCollidedBall);
		output.writeInt(turnPocketed.getCount());
		for (int i = 0; i < turnPocketed.getCount(); i++)
			output.writeInt(turnPocketed.getInteger(i));

		output.writeInt(turnInArea.getCount());
		for (int j = 0; j < turnInArea.getCount(); j++)
			output.writeInt(turnInArea.getInteger(j));

	}
}
