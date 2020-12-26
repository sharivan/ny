// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.util.Vector;

public abstract class Sort<T> {

	public Sort() {
	}

	public int binarySearch(Vector<T> items, T item) {
		int i = 0;
		int j = items.size();
		if (items.size() == 0)
			return 0;
		while (i < j) {
			int k = (i + j) / 2;
			T item1 = items.elementAt(k);
			if (item1 == item)
				return k;
			if (compare(item, item1) > 0)
				i = k + 1;
			else
				j = k;
		}
		return i;
	}

	public abstract int compare(T item, T item1);

	public void quickSort(Vector<T> items) {
		quickSort(items, 0, items.size() - 1);
	}

	public void quickSort(Vector<T> items, int i, int j) {
		if (j > i) {
			int k = i;
			int l = j;
			T item = items.elementAt((i + j) / 2);
			while (k <= l) {
				while (k < j && compare(item, items.elementAt(k)) > 0)
					k++;
				for (; l > i && compare(item, items.elementAt(l)) < 0; l--) {
				}
				if (k <= l) {
					T obj1 = items.elementAt(k);
					items.setElementAt(items.elementAt(l), k);
					items.setElementAt(obj1, l);
					k++;
					l--;
				}
			}
			quickSort(items, i, l);
			quickSort(items, k, j);
		}
	}
}
