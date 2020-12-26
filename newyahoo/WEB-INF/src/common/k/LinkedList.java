/**
 * 
 */
package common.k;

/**
 * @author saddam
 * 
 */
public class LinkedList<T> {

	private LinkedList<T>	next;
	private T				value;

	public LinkedList() {
		this(null, null);
	}

	public LinkedList(T value) {
		this(value, null);
	}

	public LinkedList(T value, LinkedList<T> next) {
		this.value = value;
		this.next = next;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		LinkedList<T> result = new LinkedList<T>(value);
		if (next != null)
			result.next = (LinkedList<T>) next.clone();
		return result;
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
		if (!(obj instanceof LinkedList))
			return false;
		final LinkedList<T> other = (LinkedList<T>) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		}
		else if (!next.equals(other.next))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * @return the next
	 */
	public LinkedList<T> getNext() {
		return next;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
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
		result = prime * result + (next == null ? 0 : next.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(LinkedList<T> next) {
		this.next = next;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value + (next != null ? " -> " + next : "");
	}

}
