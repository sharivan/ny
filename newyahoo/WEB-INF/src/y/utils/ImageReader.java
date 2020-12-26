// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class ImageReader {

	public static int getHeight(String data, int i, int j) {
		return (data.charAt(0) * i + j - 1) / j;
	}

	public static Image getImage(String data) {
		return getImage(data, false);
	}

	public static Image getImage(String data, boolean flag) {
		if (data.equals("X"))
			data = "\001\001\001\370\uFCF8\0";
		return getImage(data, flag ? 2 : 1, flag ? 3 : 1);
	}

	public static Image getImage(String data, int i, int j) {
		int width = getWidth(data, i, j);
		int height = getHeight(data, i, j);
		MemoryImageSource source = new MemoryImageSource(width, height,
				getImagePixels(data, i, j), 0, width);
		return Toolkit.getDefaultToolkit().createImage(source);
	}

	public static int[] getImagePixels(String data, int i, int j) {
		int k = -1;
		char c = data.charAt(++k);
		char c1 = data.charAt(++k);
		char c2 = data.charAt(++k);
		int ai[] = new int[c2];
		for (int l = 0; l < c2; l++) {
			char c3 = data.charAt(++k);
			char c4 = data.charAt(++k);
			ai[l] = (c3 << 16) + c4;
			if ((ai[l] & 0xff000000) == 0)
				ai[l] = 0xffffff;
		}

		int i1 = 1;
		for (int j1 = 2; j1 < ai.length; j1 *= 2)
			i1++;

		int k1 = (c1 * i + j - 1) / j;
		int l1 = (c * i + j - 1) / j;
		int result[] = new int[k1 * l1];
		k++;
		for (int i2 = 0; i2 < c1 * c; i2++) {
			int j2 = i2 % c1;
			int k2 = i2 / c1;
			j2 = j2 * i / j;
			k2 = k2 * i / j;
			int l2 = i2 / 7;
			long l3 = 1L << i2 % 7;
			int i3 = 0;
			int j3 = 1;
			for (int k3 = 0; k3 < i1; k3++) {
				if ((data.charAt(k + k3 * ((c1 * c + 6) / 7) + l2) & l3) != 0L)
					i3 += j3;
				j3 *= 2;
			}

			int i4 = j2 + k2 * k1;
			int j4 = result[i4];
			int k4 = ai[i3];
			if (j4 != 0) {
				result[i4] = 0;
				for (int l4 = 0; l4 < 4; l4++) {
					int i5 = ((j4 >> 8 * l4 & 0xff) + (k4 >> 8 * l4 & 0xff)) / 2;
					result[i4] |= i5 << 8 * l4;
				}

			}
			else {
				result[i4] = k4;
			}
		}

		return result;
	}

	public static int getWidth(String data, int i, int j) {
		return (data.charAt(1) * i + j - 1) / j;
	}

	private ImageReader() {
	}
}
