// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package server.io;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111

public final class YahooOutputStream implements DataOutput {

	public DataOutputStream	output;

	public YahooOutputStream(OutputStream outputstream) {
		// System.out.println("YahooOutputStream.process(" + outputstream +
		// ")");
		output = new DataOutputStream(outputstream);
	}

	public void flush() throws IOException {
		output.flush();
	}

	public void write(byte abyte0[]) {
		write(abyte0, 0, abyte0.length);
	}

	public void write(byte abyte0[], int i, int j) {
		try {
			output.write(abyte0, i, j);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void write(int i) {
		try {
			output.write(i);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeBoolean(boolean flag) {
		try {
			output.writeBoolean(flag);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeByte(int i) {
		try {
			output.writeByte(i);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeBytes(String s) {
		try {
			output.writeBytes(s);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeChar(int i) {
		try {
			output.writeChar(i);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeChars(String s) {
		try {
			output.writeChars(s);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeData(YData _pcls111) {
		try {
			_pcls111.write(output);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeDouble(double d) {
		try {
			output.writeDouble(d);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeFloat(float f) {
		try {
			output.writeFloat(f);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeHashtable(Hashtable<String, String> hashtable) {
		Enumeration<String> keys = hashtable.keys();
		Collection<String> values = hashtable.values();
		Vector<String> sKey = new Vector<String>();
		int count = 0;
		while (keys.hasMoreElements()) {
			sKey.add(keys.nextElement());
			count++;
		}
		Object[] aValues = values.toArray();
		writeShort(count);
		for (int i = 0; i < count; i++) {
			writeUTF(sKey.elementAt(i));
			writeUTF((String) aValues[i]);
		}
	}

	public void writeInt(int i) {
		try {
			output.writeInt(i);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeLong(long l) {
		try {
			output.writeLong(l);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeShort(int i) {
		try {
			output.writeShort(i);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeUTF(String s) {
		try {
			output.writeUTF(s);
		}
		catch (IOException _ex) {
			_ex.printStackTrace();
			try {
				output.close();
			}
			catch (IOException _ex2) {
			}
		}
	}

	public void writeVector(Vector<String> vector) {
		writeShort(vector.size());
		for (int i = 0; i < vector.size(); i++)
			writeUTF(vector.elementAt(i));
	}
}
