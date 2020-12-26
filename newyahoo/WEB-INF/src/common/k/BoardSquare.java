/**
 * 
 */
package common.k;

import y.utils.Square;

/**
 * @author saddam
 * 
 */
public class BoardSquare extends Square {

	int	value;

	public BoardSquare(int x, int y, int value) {
		super(x, y);
		this.value = value;
	}

	@Override
	public Object clone() {
		return new BoardSquare(super.a, super.b, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof BoardSquare))
			return false;
		final BoardSquare other = (BoardSquare) obj;
		if (value != other.value)
			return false;
		return true;
	}

	/**
	 * @return the newValue
	 */
	public int getValue() {
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
		int result = super.hashCode();
		result = prime * result + value;
		return result;
	}

	/**
	 * @param value
	 *            the newValue to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return super.toString() + " " + value;
	}

}
