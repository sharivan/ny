// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

public class ObjectManager {

	Object		objects[];
	int			maxAllocCount;
	Class<?>	objClass;
	public int	allocCount;

	public ObjectManager(Object obj, int i) {
		allocCount = 0;
		objClass = obj.getClass();
		objects = new Object[i];
		objects[0] = obj;
		for (int j = 1; j < i; j++)
			try {
				objects[j] = objClass.newInstance();
			}
			catch (Throwable exception) {
				exception.printStackTrace();
				System.exit(1);
			}

		maxAllocCount = i;
	}

	public synchronized Object allocate() {
		allocCount++;
		if (maxAllocCount == 0) {
			try {
				return objClass.newInstance();
			}
			catch (Throwable exception) {
				exception.printStackTrace();
			}
			System.exit(1);
			return null;
		}
		maxAllocCount--;
		return objects[maxAllocCount];
	}

	public synchronized void deallocate(Object obj) {
		allocCount--;
		if (allocCount < 0) {
			System.err.println("assertion failed deallocate twice");
			Thread.dumpStack();
			System.exit(1);
		}
		if (maxAllocCount == objects.length) {
			Object aobj[] = new Object[objects.length * 2];
			System.arraycopy(objects, 0, aobj, 0, maxAllocCount);
			objects = aobj;
		}
		objects[maxAllocCount] = obj;
		maxAllocCount++;
	}
}
