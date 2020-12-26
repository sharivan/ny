// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

public class PoolMath2 {

	public static long	b	= 0x3243fL;
	public static long	n_2	= 0x20000L;
	public static long	d	= 0x10000L;
	public static long	n_4	= 0x40000L;
	public static long	f	= 0x100000000L;

	public static long div(long l, long l1) {
		if (l1 == 0L) {
			System.err.println("MFPL devision on 0 called");
			return 0x7fffffffffffffffL;
		}
		int i = (l >= 0L || l1 <= 0L) && (l <= 0L || l1 >= 0L) ? 1 : -1;
		long l3 = Math.abs((l << 16) / l1) * i;
		return l3;
	}

	public static long mul(long l, long l1) {
		long l2 = l * l1;
		long l3 = l2;
		l2 >>= 16;
		if (l3 < 0 && l2 > 0 || l3 > 0 && l2 < 0)
			System.err.println("l3=" + l3 + "\nl2=" + l2);
		return l2;
	}

	public static long sqrt(long l) {
		if (l == 0L)
			return 0L;
		long l1 = 0L;
		long l2 = 0x100000L;
		do {
			l1 ^= l2;
			if (l1 * l1 > l)
				l1 ^= l2;
		}
		while ((l2 >>= 1) != 0L);
		return l1 << 8;
	}

	public static int toInt(long l) {
		return (int) l;
	}

	public PoolMath2() {
	}

}
