// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class StringEnum implements Enumeration<String> {

	private int		pos;
	private String	text;
	private char	separator;

	public StringEnum(String s, char c1) {
		pos = -1;
		text = s;
		separator = c1;
	}

	public boolean hasMoreElements() {
		return notEof();
	}

	public String next() throws NoSuchElementException {
		if (!notEof())
			throw new NoSuchElementException();
		pos++;
		int i = pos;
		for (; pos < text.length() && text.charAt(pos) != separator; pos++) {
		}
		return text.substring(i, pos);
	}

	public String nextElement() {
		return next();
	}

	public boolean notEof() {
		return pos < text.length();
	}

	public int separatorCount() {
		int i = 1;
		for (int j = 0; j < text.length(); j++)
			if (text.charAt(j) == separator)
				i++;

		return i;
	}
}
