// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

public class YRectangle {

	public int	left;
	public int	top;
	public int	width;
	public int	height;

	public YRectangle() {
		this(0, 0, 0, 0);
	}

	public YRectangle(int i, int j, int k, int l) {
		left = i;
		top = j;
		width = k;
		height = l;
	}

	public YRectangle(YRectangle rectangle) {
		this(rectangle.left, rectangle.top, rectangle.width, rectangle.height);
	}

	public boolean containsPoint(int i, int j) {
		return i >= left && i - left < width && j >= top && j - top < height;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof YRectangle) {
			YRectangle _lcls120 = (YRectangle) obj;
			return left == _lcls120.left && top == _lcls120.top
					&& width == _lcls120.width && height == _lcls120.height;
		}
		return false;
	}

	@Override
	public String toString() {
		String s = "(" + left + "," + top + " " + width + " " + height;
		return s;
	}
}
