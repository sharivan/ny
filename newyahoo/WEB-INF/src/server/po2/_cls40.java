// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.po2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111

public class _cls40 implements YData {

	byte	a[];

	public _cls40() {
	}

	public _cls40(int i) {
		a = new byte[i];
	}

	public void _f(int i, int j) {
		a[i] = (byte) j;
	}

	public void read(DataInput input) throws IOException {
		byte byte0 = input.readByte();
		a = new byte[byte0];
		for (int i = 0; i < byte0; i++)
			a[i] = input.readByte();

	}

	public void write(DataOutput output) throws IOException {
		output.writeByte(a.length);
		for (byte element : a)
			output.writeByte(element);

	}

	public int Ye(int i) {
		return a[i];
	}
}
