// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.io;

import java.io.IOException;

// Referenced classes of package y.po:
// _cls34, _cls37

public class YPort {

	public int					index;
	public String				yport;
	public YahooOutputStream	d;
	public Object				e;
	YahooBufferedOutputStream	output;

	public YPort(int i, String s, YahooOutputStream _pcls37,
			YahooBufferedOutputStream _pcls34, Object obj) {
		index = i;
		yport = s;
		d = _pcls37;
		output = _pcls34;
		e = obj;
	}

	public void flush() throws IOException {
		output.flush(index);
	}

	public void flush(byte[] buff, int index, int count) throws IOException {
		output.flush(buff, index, count);
	}
}
