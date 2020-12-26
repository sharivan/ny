// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

public class ObjectPool {

	Object		items[];
	int			b;
	Class<?>	objClass;
	public int	d;

	public ObjectPool(Object obj, int i) {
		d = 0;
		objClass = obj.getClass();
		items = new Object[i];
		items[0] = obj;
		for (int j = 1; j < i; j++)
			try {
				items[j] = objClass.newInstance();
			}
			catch (Throwable exception) {
				exception.printStackTrace();
				System.exit(1);
			}

		b = i;
	}

	public synchronized Object aqcuire() {
		d++;
		if (b == 0) {
			try {
				return objClass.newInstance();
			}
			catch (Throwable exception) {
				exception.printStackTrace();
			}
			System.exit(1);
			return null;
		}
		b--;
		return items[b];
	}

	public synchronized void release(Object obj) {
		d--;
		if (d < 0) {
			System.err.println("assertion failed deallocate twice");
			Thread.dumpStack();
			System.exit(1);
		}
		if (b == items.length) {
			Object aobj[] = new Object[items.length * 2];
			System.arraycopy(items, 0, aobj, 0, b);
			items = aobj;
		}
		items[b] = obj;
		b++;
	}
}
