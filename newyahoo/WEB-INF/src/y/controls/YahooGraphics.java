// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.ImageObserver;

public class YahooGraphics {

	public Graphics	graphics;
	public int		left;
	public int		top;

	public YahooGraphics() {
	}

	public YahooGraphics create(int x, int y, int width, int height) {
		YahooGraphics result = new YahooGraphics();
		result.graphics = graphics.create(x + left, y + top, width, height);
		return result;
	}

	public void dispose() {
		graphics.dispose();
	}

	public void drawChars(char data[], int offset, int length, int x, int y,
			int width, FontMetrics fontmetrics) {
		for (; fontmetrics.charsWidth(data, offset, length) > width
				&& length > 0; length--) {
		}
		graphics.drawChars(data, offset, length, x + left, y + top);
	}

	public void drawCursor(int x, int y, FontMetrics fontmetrics) {
		int k = y - fontmetrics.getAscent();
		int l = y + fontmetrics.getDescent();
		drawLine(x, l, x, k);
		drawLine(x - 2, l, x + 2, l);
		drawLine(x - 2, k, x + 2, k);
	}

	public void drawImage(Image image, int x, int y, ImageObserver imageobserver) {
		graphics.drawImage(image, x + left, y + top, imageobserver);
	}

	public void drawImage(Image image, int x, int y, int width, int height,
			ImageObserver imageobserver) {
		graphics.drawImage(image, x + left, y + top, width, height,
				imageobserver);
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		graphics.drawLine(x1 + left, y1 + top, x2 + left, y2 + top);
	}

	public void drawOval(int x, int y, int width, int height) {
		graphics.drawOval(x + left, y + top, width, height);
	}

	public void drawPolygon(Polygon polygon) {
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] += left;
			polygon.ypoints[i] += top;
		}

		graphics.drawPolygon(polygon);
		for (int j = 0; j < polygon.npoints; j++) {
			polygon.xpoints[j] -= left;
			polygon.ypoints[j] -= top;
		}

	}

	public void drawRect(int x, int y, int width, int height) {
		graphics.drawRect(x + left, y + top, width, height);
	}

	public void drawString(String str, int x, int y) {
		graphics.drawString(str, x + left, y + top);
	}

	public void du(int i, int j, int k, int l, Color color, Color color1) {
		setColor(color);
		for (int i1 = 0; i1 < 2; i1++) {
			ou(i, j, 2, 0, k - 3, 0, k, l, i1 * 2);
			ou(i, j, 0, 2, 0, l - 3, k, l, i1 * 2);
		}

		iu(k, l, i + 2, j, i, j + 2);
		iu(k, l, i + 3, j, i, j + 3);
		setColor(color1);
		iu(k, l, i + 1, j + 3, i + 3, j + 1);
		for (int j1 = 0; j1 < 2; j1++) {
			ou(i, j, 3, 1, k - 4, 1, k, l, j1 * 2);
			ou(i, j, 1, 3, 1, l - 4, k, l, j1 * 2);
		}

	}

	public void eu(int i, int j, int k, int l, boolean flag) {
		if (flag) {
			setColor(Color.darkGray);
			drawRect(i, j, k - 1, l - 1);
		}
		else {
			setColor(Color.lightGray);
			ou(i, j, 0, 0, k - 2, 0, 0, 0, 0);
			ou(i, j, 0, 1, 0, l - 2, 0, 0, 0);
			setColor(Color.white);
			ou(i, j, 1, 1, k - 3, 1, 0, 0, 0);
			ou(i, j, 1, 2, 1, l - 3, 0, 0, 0);
			setColor(Color.darkGray);
			ou(i, j, 1, l - 2, k - 2, l - 2, 0, 0, 0);
			ou(i, j, k - 2, l - 3, k - 2, 1, 0, 0, 0);
			setColor(Color.black);
			ou(i, j, 0, l - 1, k - 1, l - 1, 0, 0, 0);
			ou(i, j, k - 1, l - 2, k - 1, 0, 0, 0, 0);
		}
	}

	public void fill3DRect(int x, int y, int width, int height, boolean raised) {
		graphics.fill3DRect(x + left, y + top, width, height, raised);
	}

	public void fillOval(int x, int y, int width, int height) {
		graphics.fillOval(x + left, y + top, width, height);
	}

	public void fillPolygon(Polygon polygon) {
		fillPolygon(polygon, left, top);
	}

	void fillPolygon(Polygon polygon, int x, int y) {
		for (int k = 0; k < polygon.npoints; k++) {
			polygon.xpoints[k] += x;
			polygon.ypoints[k] += y;
		}

		graphics.fillPolygon(polygon);
		for (int l = 0; l < polygon.npoints; l++) {
			polygon.xpoints[l] -= x;
			polygon.ypoints[l] -= y;
		}

	}

	public void fillRect(int x, int y, int width, int height) {
		graphics.fillRect(x + left, y + top, width, height);
	}

	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		graphics.fillRoundRect(x + left, y + top, width, height, arcWidth,
				arcHeight);
	}

	public void iu(int i, int j, int k, int l, int i1, int j1) {
		for (int k1 = 0; k1 < 2; k1++) {
			ou(0, 0, k, l, i1, j1, i, j, k1 * 2);
			ou(0, 0, k, l, j1, i1, i, j, k1 * 2);
		}

		for (int l1 = 0; l1 < 2; l1++) {
			ou(0, 0, k, l, i1, j1, j, i, l1 * 2 + 1);
			ou(0, 0, k, l, j1, i1, j, i, l1 * 2 + 1);
		}

	}

	public void ou(int i, int j, int k, int l, int i1, int j1, int k1, int l1,
			int i2) {
		switch (i2) {
		case 0: // '\0'
			drawLine(i + k, j + l, i + i1, j + j1);
			break;

		case 1: // '\001'
			drawLine(i + l1 - 1 - l, j + k, i + l1 - 1 - j1, j + i1);
			break;

		case 2: // '\002'
			drawLine(i + k1 - 1 - k, j + l1 - 1 - l, i + k1 - 1 - i1, j + l1
					- 1 - j1);
			break;

		case 3: // '\003'
			drawLine(i + l, j + k1 - 1 - k, i + j1, j + k1 - 1 - i1);
			break;
		}
	}

	public void qu(int i, int j, int k, int l, int i1) {
		drawLine(i + i1, j, i + k - i1 - 1, j);
		drawLine(i + k - i1 - 1, j, i + k - 1, j + i1);
		drawLine(i + k - 1, j + i1, i + k - 1, j + l - 1 - i1);
		drawLine(i + k - 1, j + l - 1 - i1, i + k - 1 - i1, j + l - 1);
		drawLine(i + k - 1 - i1, j + l - 1, i + i1, j + l - 1);
		drawLine(i + i1, j + l - 1, i, j + l - 1 - i1);
		drawLine(i, j + l - 1 - i1, i, j + i1);
		drawLine(i, j + i1, i + i1, j);
	}

	public void setColor(Color color) {
		graphics.setColor(color);
	}

	public void setFont(Font font) {
		graphics.setFont(font);
	}
}
