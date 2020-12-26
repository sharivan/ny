package server.po2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;
import common.po2.YPoint;

public class Cue implements YData {

	YPoint	cueTip;
	YPoint	cueTipOffset;
	int		power	= 0;

	public Cue() {
		cueTip = new YPoint();
		cueTipOffset = new YPoint();
	}

	public void read(DataInput input) throws IOException {
		input.readInt();
		cueTip.read(input);
		cueTipOffset.read(input);
		power = input.readInt();
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(20);
		cueTip.write(output);
		cueTipOffset.write(output);
		output.writeInt(power);
	}

}
