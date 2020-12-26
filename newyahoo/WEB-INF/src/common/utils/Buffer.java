// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import common.io.YData;

// Referenced classes of package y.po:
// _cls159, _cls111

public class Buffer implements Cloneable, YData {

	public int	item[];
	public int	count;
	public int	pos;

	public Buffer() {
		this(10);
	}

	public Buffer(int count) {
		this(count, 0);
	}

	public Buffer(int count, int initialPos) {
		item = new int[count];
		pos = initialPos;
	}

	public final void checkSpace(int len) {
		int j = item.length;
		if (len > j) {
			int ai[] = item;
			int k = pos <= 0 ? j * 2 : j + pos;
			if (k < len)
				k = len;
			item = new int[k];
			System.arraycopy(ai, 0, item, 0, count);
		}
	}

	@Override
	public Object clone() {
		try {
			Buffer _lcls160 = (Buffer) super.clone();
			_lcls160.item = new int[count];
			System.arraycopy(item, 0, _lcls160.item, 0, count);
			return _lcls160;
		}
		catch (CloneNotSupportedException _ex) {
			throw new InternalError();
		}
	}

	public final void delete(int index) {
		if (index >= count)
			throw new ArrayIndexOutOfBoundsException(index + " >= " + count);
		int j = count - index - 1;
		if (j > 0)
			System.arraycopy(item, index + 1, item, index, j);
		count--;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Buffer) {
			Buffer buffer = (Buffer) obj;
			if (buffer.count == count) {
				for (int i = 0; i < count; i++) {
					if (buffer.item[i] == item[i])
						continue;
					return false;
				}
				return true;
			}
			return false;
		}
		return false;
	}

	public final int getCount() {
		return count;
	}

	public final float getFloat(int index) {
		if (index >= count)
			throw new ArrayIndexOutOfBoundsException("index=" + index);
		return Float.intBitsToFloat(item[index]);
	}

	public final int getInteger(int index) {
		if (index >= count)
			throw new ArrayIndexOutOfBoundsException("index=" + index);
		return item[index];
	}

	public void read(DataInput input) throws IOException {
		reset();
		int i = input.readInt();
		for (int j = 0; j < i; j++)
			writeInt(input.readInt());
	}

	public final void reset() {
		count = 0;
	}

	@Override
	public final String toString() {
		int i = getCount() - 1;
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("[");
		for (int j = 0; j <= i; j++) {
			String s = Integer.toString(item[j]);
			stringbuffer.append(s);
			if (j < i)
				stringbuffer.append(", ");
		}

		stringbuffer.append("]");
		return stringbuffer.toString();
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(getCount());
		for (int i = 0; i < getCount(); i++)
			output.writeInt(getInteger(i));
	}

	/**
	 * @param x
	 */
	public void writeFloat(float x) {
		checkSpace(count + 1);
		item[count++] = Float.floatToIntBits(x);
	}

	public final void writeInt(int i) {
		checkSpace(count + 1);
		item[count++] = i;
	}

}
