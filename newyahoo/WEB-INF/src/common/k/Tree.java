/**
 * 
 */
package common.k;

import java.util.Vector;

/**
 * @author saddam
 * 
 */
public class Tree<T> {

	protected Vector<Tree<T>>	childs;
	protected Tree<T>			pattern;
	protected T					value;
	int							currIndex;

	public Tree() {
		this(null, null);
	}

	public Tree(T value) {
		this(null, value);
	}

	public Tree(Tree<T> pattern, T value) {
		this.pattern = pattern;
		this.value = value;
		setPattern(pattern);
		childs = new Vector<Tree<T>>();
		reset();
	}

	public void addChild(Tree<T> item) {
		if (item == null)
			throw new NullPointerException("Child canot be null.");
		if (!childs.contains(item)) {
			childs.add(item);
			item.setPattern(this);
		}
	}

	public void addChildsFrom(Tree<T> node) {
		while (node.childs.size() > 0)
			addChild(node.childs.elementAt(0));
	}

	public Tree<T> childAt(int index) {
		return childs.elementAt(index);
	}

	public int childCount() {
		return childCount(false);
	}

	public int childCount(boolean recursive) {
		int result = childs.size();
		if (recursive) {
			for (int i = 0; i < childs.size(); i++) {
				Tree<T> child = childs.elementAt(i);
				result += child.childCount(true);
			}
		}
		return result;
	}

	public void clear() {
		clear(false);
	}

	public void clear(boolean recursive) {
		if (recursive)
			for (int i = 0; i < childs.size(); i++) {
				Tree<T> child = childs.elementAt(i);
				child.clear(true);
			}
		childs.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		Tree<T> result = new Tree<T>(pattern, value);
		result.childs = (Vector<Tree<T>>) childs.clone();
		return result;
	}

	public boolean containsChild(Tree<T> item) {
		return hasChild(item, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tree))
			return false;
		final Tree<T> other = (Tree<T>) obj;
		if (childs == null) {
			if (other.childs != null)
				return false;
		}
		else if (!childs.equals(other.childs))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}

	public LinkedList<T> first() {
		LinkedList<T> curr = new LinkedList<T>(value);
		if (hasMoreElements()) {
			curr.setNext(childs.elementAt(0).first());
			return curr;
		}
		return curr;
	}

	public Tree<T> getPattern() {
		return pattern;
	}

	public T getValue() {
		return value;
	}

	public boolean hasChild(Tree<T> item) {
		return hasChild(item, true);
	}

	public boolean hasChild(Tree<T> item, boolean recursive) {
		boolean flag = childs.contains(item);
		if (flag)
			return true;
		if (recursive)
			for (int i = 0; i < childs.size(); i++) {
				Tree<T> child = childs.elementAt(i);
				if (child.hasChild(item, true))
					return true;
			}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (childs == null ? 0 : childs.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	public boolean hasMoreElements() {
		return currIndex < childs.size();
	}

	public boolean hasValue(T value) {
		return hasValue(value, true);
	}

	public boolean hasValue(T value, boolean recursive) {
		for (int i = 0; i < childs.size(); i++) {
			Tree<T> child = childs.elementAt(i);
			if (value == null && child.value == null)
				return true;
			if (child.value.equals(value))
				return true;
			if (recursive && child.hasValue(value, true))
				return true;
		}
		return false;
	}

	public int indexOf(Tree<T> item) {
		return childs.indexOf(item);
	}

	public boolean isRoot() {
		return pattern == null;
	}

	public LinkedList<T> next() {
		LinkedList<T> curr = new LinkedList<T>(value);
		if (hasMoreElements()) {
			curr.setNext(childs.elementAt(currIndex).next());
			if (!childs.elementAt(currIndex).hasMoreElements())
				currIndex++;
			return curr;
		}
		return curr;
	}

	public void removeChild(int index) {
		childs.remove(index);
	}

	public void removeChild(Tree<T> item) {
		if (childs.contains(item)) {
			childs.remove(item);
			item.pattern = null;
		}
	}

	public void reset() {
		currIndex = 0;
	}

	public void setPattern(Tree<T> pattern) {
		if (this.pattern != pattern) {
			if (this.pattern != null)
				this.pattern.removeChild(this);
			this.pattern = pattern;
			if (pattern != null)
				pattern.addChild(this);
		}
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String result = value != null ? value.toString() : "null";
		if (childs.size() > 0) {
			result += "[" + childs.elementAt(0);
			for (int i = 1; i < childs.size(); i++)
				result += ", " + childs.elementAt(i);
			result += "]";
		}
		return result;
	}

}
