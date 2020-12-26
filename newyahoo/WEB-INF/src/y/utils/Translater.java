// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

public class Translater {

	int				a;
	public boolean	logAfter;
	public boolean	logBefore;
	private char	hexa_digits[]	= { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public Translater(int i, boolean _logAfter, boolean _logBefore) {
		a = i;
		logAfter = _logAfter;
		logBefore = _logBefore;
	}

	private boolean charInRange(char c, char low, char high) {
		return (byte) low <= (byte) c && (byte) c <= (byte) high;
	}

	private String intToHex(int i, int digits) {
		String result = "";
		for (int j = 0; j < digits; j++) {
			result = hexa_digits[i & 0x0f] + result;
			i >>= 4;
		}
		return result;
	}

	private String retString(String s) {
		boolean B = false;
		boolean B1 = false;
		String Result = "";
		for (int i = 0; i < s.length(); i++)
			if (charInRange(s.charAt(i), '\u0020', '\u007E')) {
				if (B) {
					if (s.charAt(i) == '\'')
						Result = Result + "'" + s.charAt(i);
					else
						Result = Result + s.charAt(i);
				}
				else {
					if (s.charAt(i) == '\'')
						Result = Result + '\'' + '\'' + s.charAt(i) + '\'';
					else
						Result = Result + '\'' + s.charAt(i);
					B = true;
					B1 = false;
				}
			}
			else {
				if (B) {
					Result = Result + "\'#$" + intToHex((byte) s.charAt(i), 2);
					B = false;
					B1 = true;
				}
				else if (!B1) {
					Result = Result + "#$" + intToHex((byte) s.charAt(i), 2);
					B1 = true;
					B = false;
				}
				else
					Result = Result + "#$" + intToHex((byte) s.charAt(i), 2);
			}
		if (B)
			Result = Result + '\'';
		return Result;
	}

	public void translate(byte abyte0[], int i, int j) {
		String s = "";
		String s1 = "";
		for (int k = i; k < j; k++) {
			if (logAfter)
				s += (char) abyte0[k];
			a *= 83;
			abyte0[k] ^= a;
			if (logBefore)
				s1 += (char) abyte0[k];
		}
		if (logAfter)
			System.out.println(">>: " + retString(s));
		if (logBefore)
			System.out.println("<<: " + retString(s1));
	}
}
