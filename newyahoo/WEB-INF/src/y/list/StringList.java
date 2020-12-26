package y.list;

import java.util.Vector;

public class StringList implements Cloneable {

	private Vector<String>	items;

	public StringList() {
		items = new Vector<String>();
	}

	public void add(String s) {
		items.add(s);
	}

	public void clear() {
		items.clear();
	}

	@Override
	public StringList clone() {
		StringList result = new StringList();
		for (int i = 0; i < items.size(); i++)
			result.add(items.elementAt(i));
		return result;
	}

	public String getItem(int index) {
		return items.elementAt(index);
	}

	public void insert(String s, int index) {
		items.insertElementAt(s, index);
	}

	public void remove(int index) {
		items.remove(index);
	}

	public int size() {
		return items.size();
	}

	@Override
	public String toString() {
		if (items.size() == 0)
			return "";
		String result = items.elementAt(0);
		for (int i = 1; i < items.size(); i++)
			result += "\n" + items.elementAt(i);
		return result;
	}
}
