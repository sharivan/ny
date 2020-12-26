// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.util.Vector;

// Referenced classes of package y.po:
// _cls159, _cls61

public final class YahooVector<T> extends Vector<T> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 438417310939959861L;

	public YahooVector() {
	}

	public YahooVector(T obj) {
		this();
		add(obj);
	}

	public YahooVector(T obj, T obj1) {
		this(obj);
		add(obj1);
	}

	public YahooVector(T obj, T obj1, T obj2) {
		this(obj, obj1);
		add(obj2);
	}

	public YahooVector(T obj, T obj1, T obj2, T obj3) {
		this(obj, obj1, obj2);
		add(obj3);
	}

	public void assign(Vector<T> vector) {
		clear();
		for (int i = 0; i < vector.size(); i++)
			add(vector.elementAt(i));

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector))
			return false;
		Vector<T> vector = (Vector<T>) obj;
		if (size() != vector.size())
			return false;
		for (int i = 0; i < size(); i++)
			if (!elementAt(i).equals(vector.elementAt(i)))
				return false;

		return true;
	}

	@Override
	public int hashCode() {
		int i = 0;
		for (int j = 0; j < size(); j++)
			i += elementAt(j).hashCode() + j;

		return i;
	}
}
