// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111

public class ByteArrayData implements YData {

	byte	item[];

	public ByteArrayData() {
	}

	public ByteArrayData(int i) {
		item = new byte[i];
	}

	public int byteAt(int i) {
		return item[i];
	}

	public void clear() {
		for (int i = 0; i < item.length; i++)
			item[i] = 0;

	}

	public void read(DataInput input) throws IOException {
		byte byte0 = input.readByte();
		item = new byte[byte0];
		for (int i = 0; i < byte0; i++)
			item[i] = input.readByte();

	}

	public void setByte(int index, int value) {
		item[index] = (byte) value;
	}

	public void write(DataOutput output) throws IOException {
		output.writeByte(item.length);
		for (byte element : item)
			output.writeByte(element);

	}

}
