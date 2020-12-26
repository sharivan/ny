package common.k;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;

public class Move implements YData {

	public int	x1;
	public int	y1;
	public int	x2;
	public int	y2;

	public Move() {
		this((byte) 0, (byte) 0, (byte) 0, (byte) 0);
	}

	public Move(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void read(DataInput input) throws IOException {
		x1 = input.readByte();
		y1 = input.readByte();
		x2 = input.readByte();
		y2 = input.readByte();
	}

	public void write(DataOutput output) throws IOException {
		output.writeByte(x1);
		output.writeByte(y1);
		output.writeByte(x2);
		output.writeByte(y2);
	}

}
