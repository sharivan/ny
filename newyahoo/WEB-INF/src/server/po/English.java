package server.po;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;
import common.po.YIPoint;

public class English implements YData {

	YIPoint	pos;

	public English() {
		pos = new YIPoint();
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
