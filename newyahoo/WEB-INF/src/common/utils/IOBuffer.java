/**
 * 
 */
package common.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;

import common.io.YData;
import common.yutils.YahooUtils;

/**
 * @author Saddam Hussein
 * 
 */
public class IOBuffer implements YData, DataInput, DataOutput {

	private byte[]	buf;
	private int		readPos;
	private int		writePos;

	public IOBuffer() {
		this(16);
	}

	public IOBuffer(byte[] buf) {
		this.buf = buf;

		readPos = 0;
		writePos = 0;
	}

	public IOBuffer(int size) {
		this(new byte[size]);
	}

	private void checkReadPos(int size) throws EOFException {
		if (readPos + size > buf.length)
			throw new EOFException("readPos=" + readPos + "; size=" + size
					+ "; buf.length=" + buf.length);
	}

	/**
	 * @param size
	 */
	private void checkWriteLength(int size) {
		if (writePos + size <= buf.length)
			return;
		int newLen = buf.length == 0 ? 1 : 2 * buf.length;
		while (writePos + size > newLen)
			newLen *= 2;
		buf = YahooUtils.redimArray(buf, newLen);
	}

	/**
	 * @return the buf
	 */
	public byte[] getBuf() {
		return buf;
	}

	public byte getByte0() throws IOException {
		if (buf == null)
			throw new EOFException("buf is null");
		return buf[0];
	}

	public byte[] getBytes() {
		byte[] result = new byte[writePos];
		System.arraycopy(buf, 0, result, 0, writePos);
		return result;
	}

	/**
	 * @return the readPos
	 */
	public int getReadPos() {
		return readPos;
	}

	/**
	 * @return the writePos
	 */
	public int getWritePos() {
		return writePos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.io.YData#read(java.io.DataInput)
	 */
	@Override
	public void read(DataInput input) throws IOException {
		int len = input.readInt();
		if (len >= 0) {
			buf = new byte[len];
			readPos = input.readInt();
			writePos = input.readInt();
			input.readFully(buf);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readBoolean()
	 */
	@Override
	public boolean readBoolean() throws IOException {
		checkReadPos(1);
		return buf[readPos++] != 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readByte()
	 */
	@Override
	public byte readByte() throws IOException {
		checkReadPos(1);
		return buf[readPos++];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readChar()
	 */
	@Override
	public char readChar() throws IOException {
		checkReadPos(2);
		short s = buf[readPos++];
		s <<= 8;
		s |= buf[readPos++];
		return (char) s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readDouble()
	 */
	@Override
	public double readDouble() throws IOException {
		long l = readLong();
		return Double.longBitsToDouble(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readFloat()
	 */
	@Override
	public float readFloat() throws IOException {
		int i = readInt();
		return Float.intBitsToFloat(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readFully(byte[])
	 */
	@Override
	public void readFully(byte[] b) throws IOException {
		if (buf.length < b.length)
			throw new EOFException();
		System.arraycopy(buf, 0, b, 0, b.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readFully(byte[], int, int)
	 */
	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		if (buf.length < off + len)
			throw new EOFException();
		System.arraycopy(buf, 0, b, off, Math.min(buf.length, off + len));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readInt()
	 */
	@Override
	public int readInt() throws IOException {
		checkReadPos(4);
		int i = buf[readPos++];
		i <<= 8;
		i |= buf[readPos++];
		i <<= 8;
		i |= buf[readPos++];
		i <<= 8;
		i |= buf[readPos++];
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readLine()
	 */
	@Override
	public String readLine() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readLong()
	 */
	@Override
	public long readLong() throws IOException {
		checkReadPos(8);
		long l = buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		l <<= 8;
		l |= buf[readPos++];
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readShort()
	 */
	@Override
	public short readShort() throws IOException {
		checkReadPos(2);
		short s = buf[readPos++];
		s <<= 8;
		s |= buf[readPos++];
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readUnsignedByte()
	 */
	@Override
	public int readUnsignedByte() throws IOException {
		int i = readByte();
		return i >= 0 ? i : 256 + i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readUnsignedShort()
	 */
	@Override
	public int readUnsignedShort() throws IOException {
		int i = readShort();
		return i >= 0 ? i : 65536 + i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#readUTF()
	 */
	@Override
	public String readUTF() throws IOException {
		int utflen = readUnsignedShort();
		byte[] bytearr = new byte[utflen];
		char[] chararr = new char[utflen];

		int c, char2, char3;
		int count = 0;
		int chararr_count = 0;

		readFully(bytearr, 0, utflen);

		while (count < utflen) {
			c = bytearr[count] & 0xff;
			if (c > 127)
				break;
			count++;
			chararr[chararr_count++] = (char) c;
		}

		while (count < utflen) {
			c = bytearr[count] & 0xff;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				/* 0xxxxxxx */
				count++;
				chararr[chararr_count++] = (char) c;
				break;
			case 12:
			case 13:
				/* 110x xxxx 10xx xxxx */
				count += 2;
				if (count > utflen)
					throw new UTFDataFormatException(
							"malformed input: partial character at end");
				char2 = bytearr[count - 1];
				if ((char2 & 0xC0) != 0x80)
					throw new UTFDataFormatException(
							"malformed input around byte " + count);
				chararr[chararr_count++] = (char) ((c & 0x1F) << 6 | char2 & 0x3F);
				break;
			case 14:
				/* 1110 xxxx 10xx xxxx 10xx xxxx */
				count += 3;
				if (count > utflen)
					throw new UTFDataFormatException(
							"malformed input: partial character at end");
				char2 = bytearr[count - 2];
				char3 = bytearr[count - 1];
				if ((char2 & 0xC0) != 0x80 || (char3 & 0xC0) != 0x80)
					throw new UTFDataFormatException(
							"malformed input around byte " + (count - 1));
				chararr[chararr_count++] = (char) ((c & 0x0F) << 12
						| (char2 & 0x3F) << 6 | (char3 & 0x3F) << 0);
				break;
			default:
				/* 10xx xxxx, 1111 xxxx */
				throw new UTFDataFormatException("malformed input around byte "
						+ count);
			}
		}
		// The number of chars produced may be less than utflen
		return new String(chararr, 0, chararr_count);
	}

	public void reset() {
		readPos = 0;
		writePos = 0;
	}

	/**
	 * @param buf
	 *            the buf to set
	 */
	public void setBuf(byte[] buf) {
		this.buf = buf;
	}

	/**
	 * @param readPos
	 *            the readPos to set
	 * @throws IOException
	 */
	public void setReadPos(int readPos) throws IOException {
		if (buf == null || readPos < 0 || readPos >= buf.length)
			throw new IOException("readPos out of bounds: readPos=" + readPos
					+ ", "
					+ (buf != null ? "buf.length=" + buf.length : "buf=null"));
		this.readPos = readPos;
	}

	public void setState(byte[] buf, int readPos, int writePos) {
		this.buf = buf;
		this.readPos = readPos;
		this.writePos = writePos;
	}

	/**
	 * @param writePos
	 *            the writePos to set
	 * @throws IOException
	 */
	public void setWritePos(int writePos) throws IOException {
		if (buf == null || writePos < 0 || writePos >= buf.length)
			throw new IOException("writePos out of bounds: writePos="
					+ writePos + ", "
					+ (buf != null ? "buf.length=" + buf.length : "buf=null"));
		this.writePos = writePos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataInput#skipBytes(int)
	 */
	@Override
	public int skipBytes(int n) throws IOException {
		checkReadPos(n);
		readPos += n;
		return readPos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#write(byte[])
	 */
	@Override
	public void write(byte[] b) {
		checkWriteLength(b.length);
		System.arraycopy(b, 0, buf, writePos, b.length);
		writePos += b.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) {
		checkWriteLength(len);
		System.arraycopy(b, off, buf, writePos, len);
		writePos += len;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.io.YData#write(java.io.DataOutput)
	 */
	@Override
	public void write(DataOutput output) throws IOException {
		if (buf != null) {
			output.writeInt(buf.length);
			output.writeInt(readPos);
			output.writeInt(writePos);
			output.write(buf);
		}
		else
			output.writeInt(-1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#write(int)
	 */
	@Override
	public void write(int b) {
		checkWriteLength(1);
		buf[writePos++] = (byte) b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean v) {
		checkWriteLength(1);
		buf[writePos++] = (byte) (v ? 1 : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeByte(int)
	 */
	@Override
	public void writeByte(int v) {
		checkWriteLength(1);
		buf[writePos++] = (byte) v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeBytes(java.lang.String)
	 */
	@Override
	public void writeBytes(String s) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeChar(int)
	 */
	@Override
	public void writeChar(int v) {
		checkWriteLength(2);
		buf[writePos++] = (byte) ((v & 0xff00) >>> 8);
		buf[writePos++] = (byte) (v & 0xff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeChars(java.lang.String)
	 */
	@Override
	public void writeChars(String s) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeDouble(double)
	 */
	@Override
	public void writeDouble(double v) {
		long l = Double.doubleToLongBits(v);
		writeLong(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeFloat(float)
	 */
	@Override
	public void writeFloat(float v) {
		int i = Float.floatToIntBits(v);
		writeInt(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeInt(int)
	 */
	@Override
	public void writeInt(int v) {
		checkWriteLength(4);
		buf[writePos++] = (byte) ((v & 0xff000000) >>> 24);
		buf[writePos++] = (byte) ((v & 0xff0000) >>> 16);
		buf[writePos++] = (byte) ((v & 0xff00) >>> 8);
		buf[writePos++] = (byte) (v & 0xff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeLong(long)
	 */
	@Override
	public void writeLong(long v) {
		checkWriteLength(8);
		buf[writePos++] = (byte) ((v & 0xff00000000000000L) >>> 56);
		buf[writePos++] = (byte) ((v & 0xff000000000000L) >>> 48);
		buf[writePos++] = (byte) ((v & 0xff0000000000L) >>> 40);
		buf[writePos++] = (byte) ((v & 0xff00000000L) >>> 32);
		buf[writePos++] = (byte) ((v & 0xff000000) >>> 24);
		buf[writePos++] = (byte) ((v & 0xff0000) >>> 16);
		buf[writePos++] = (byte) ((v & 0xff00) >>> 8);
		buf[writePos++] = (byte) (v & 0xff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeShort(int)
	 */
	@Override
	public void writeShort(int v) {
		checkWriteLength(2);
		buf[writePos++] = (byte) ((v & 0xff00) >>> 8);
		buf[writePos++] = (byte) (v & 0xff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.DataOutput#writeUTF(java.lang.String)
	 */
	@Override
	public void writeUTF(String str) {
		int strlen = str.length();
		int utflen = 0;
		int c, count = 0;

		/* use charAt instead of copying String to char array */
		for (int i = 0; i < strlen; i++) {
			c = str.charAt(i);
			if (c >= 0x0001 && c <= 0x007F) {
				utflen++;
			}
			else if (c > 0x07FF) {
				utflen += 3;
			}
			else {
				utflen += 2;
			}
		}

		if (utflen > 65535) {
			System.err.println("encoded string too long: " + utflen + " bytes");
			return;
		}

		byte[] bytearr = new byte[utflen + 2];

		bytearr[count++] = (byte) (utflen >>> 8 & 0xFF);
		bytearr[count++] = (byte) (utflen >>> 0 & 0xFF);

		int i = 0;
		for (i = 0; i < strlen; i++) {
			c = str.charAt(i);
			if (!(c >= 0x0001 && c <= 0x007F))
				break;
			bytearr[count++] = (byte) c;
		}

		for (; i < strlen; i++) {
			c = str.charAt(i);
			if (c >= 0x0001 && c <= 0x007F) {
				bytearr[count++] = (byte) c;

			}
			else if (c > 0x07FF) {
				bytearr[count++] = (byte) (0xE0 | c >> 12 & 0x0F);
				bytearr[count++] = (byte) (0x80 | c >> 6 & 0x3F);
				bytearr[count++] = (byte) (0x80 | c >> 0 & 0x3F);
			}
			else {
				bytearr[count++] = (byte) (0xC0 | c >> 6 & 0x1F);
				bytearr[count++] = (byte) (0x80 | c >> 0 & 0x3F);
			}
		}
		write(bytearr, 0, utflen + 2);
	}

}
