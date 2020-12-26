// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.Polygon;

public class Arrow {

	public static Polygon Jy(int i, int j, int k, int l, int i1) {
		int j1 = k / 6;
		int k1 = l / 2;
		Polygon polygon = new Polygon();
		polygon.addPoint(k / 2, 0);
		polygon.addPoint(k - 1, k1 - 1);
		polygon.addPoint(k - j1 - 1, k1 - 1);
		polygon.addPoint(k - j1 - 1, l - 1);
		polygon.addPoint(j1, l - 1);
		polygon.addPoint(j1, k1 - 1);
		polygon.addPoint(0, k1 - 1);
		polygon.addPoint(k / 2, 0);
		Polygon polygon1 = new Polygon();
		for (int l1 = 0; l1 < polygon.npoints; l1++)
			switch (i1) {
			case 0: // '\0'
				polygon1.addPoint(i + polygon.xpoints[l1], j
						+ polygon.ypoints[l1]);
				break;

			case 2: // '\002'
				polygon1.addPoint(i + polygon.xpoints[l1], j + l
						- polygon.ypoints[l1] - 1);
				break;

			case 3: // '\003'
				polygon1.addPoint(i + polygon.ypoints[l1], j + k
						- polygon.xpoints[l1] - 1);
				break;

			case 1: // '\001'
			default:
				polygon1.addPoint(i + l - polygon.ypoints[l1] - 1, j
						+ polygon.xpoints[l1]);
				break;
			}

		return polygon1;
	}

	public Arrow() {
	}
}
