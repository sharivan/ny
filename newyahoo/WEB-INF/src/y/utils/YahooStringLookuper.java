// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

// Referenced classes of package y.po:
// _cls14, _cls73

public class YahooStringLookuper implements StringLookuper {

	String	strings[];

	public YahooStringLookuper() {
		strings = new String[8192];
	}

	public String lookupString(int i) {
		String s = strings[i & 0xffff];
		return s != null ? s : "X";
	}

	public void read(byte abyte0[]) throws IOException {
		DataInputStream datainputstream = new DataInputStream(
				new YahooStringBuffer(abyte0));
		read(datainputstream);
	}

	public void read(InputStream inputstream) throws IOException {
		try {
			DataInputStream datainputstream = new DataInputStream(inputstream);
			do {
				short word0;
				String s;
				do {
					word0 = datainputstream.readShort();
					try {
						s = datainputstream.readUTF();
					}
					catch (EOFException _ex) {
						throw new IOException("bad little dict");
					}
				}
				while (word0 < 0);
				strings[word0] = s;
				// System.out.println(Integer.toHexString(word0) + ": " + s);
			}
			while (true);
		}
		catch (EOFException _ex) {
		}
		catch (NoSuchElementException _ex) {
		}
	}

	@SuppressWarnings("deprecation")
	public void read(String s) throws IOException {
		byte abyte0[] = new byte[s.length()];
		s.getBytes(0, abyte0.length, abyte0, 0);
		read(abyte0);
	}
}
