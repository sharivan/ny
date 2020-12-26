package server.po2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;
import common.po2.YPoint;

public class English implements YData {

	YPoint	pos;

	public English() {
		pos = new YPoint();
	}

	public void read(DataInput input) throws IOException {
		input.readInt();
		pos.read(input);
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(8);
		pos.write(output);
	}

}
