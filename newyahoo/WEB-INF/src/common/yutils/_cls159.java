// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111

public final class _cls159 implements YData {

	private long	a;

	public _cls159() {
		this(System.currentTimeMillis());
	}

	public _cls159(long l) {
		hy(l);
	}

	public int cy(int i) {
		return (fy() & 0x7fffffff) % i;
	}

	protected synchronized int dy(int i) {
		long l = a * 0x5deece66dL + 11L & 0xffffffffffffL;
		a = l;
		return (int) (l >>> 48 - i);
	}

	public int fy() {
		return dy(32);
	}

	public synchronized void hy(long l) {
		a = (l ^ 0x5deece66dL) & 0xffffffffffffL;
	}

	public void read(DataInput input) throws IOException {
		a = input.readLong();
	}

	public void write(DataOutput output) throws IOException {
		output.writeLong(a);
	}
}
