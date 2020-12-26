// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;
import java.awt.Image;

import y.controls.YahooControl;
import y.utils.ImageReader;

// Referenced classes of package y.k:
// _cls65, _cls47, _cls48, _cls151,
// _cls137, _cls40, _cls84, _cls41,
// _cls62, _cls95, _cls117

class BoardArea extends YahooControl {

	YahooBoard	ba_a;
	Image		piece[];
	Image		images[];

	BoardArea(YahooCheckersTable _pcls137) {
		piece = new Image[5];
		images = new Image[2];
		boolean flag = _pcls137.isSmallWidows();
		if (flag) {
		}
		else {
		}
		String s = _pcls137.getApplet().getParameter(
				"yahoo.games.checkers.images");
		if (s != null) {
			piece[1] = _pcls137.getApplet()
					.getYahooImage(s + "red_checker.gif");
			piece[2] = _pcls137.getApplet().getYahooImage(
					s + "black_checker.gif");
			piece[3] = _pcls137.getApplet().getYahooImage(s + "red_king.gif");
			piece[4] = _pcls137.getApplet().getYahooImage(s + "black_king.gif");
			if (piece[1] != null)
				new _cls95(this, piece[1], _pcls137.getTimerHandler());
			if (piece[2] != null)
				new _cls95(this, piece[2], _pcls137.getTimerHandler());
			if (piece[3] != null)
				new _cls95(this, piece[3], _pcls137.getTimerHandler());
			if (piece[4] != null)
				new _cls95(this, piece[4], _pcls137.getTimerHandler());
		}
		for (int i = 1; i < 5; i++)
			if (piece[i] == null)
				piece[i] = ImageReader.getImage(
						YahooCheckersImageList.data[i - 1], flag);

		for (int j = 0; j < 2; j++)
			images[j] = ImageReader.getImage(
					YahooCheckersImageList.data[j + 4], flag);

		ba_a = new YahooCheckerBoard(_pcls137.checkers, images, true, true,
				true, piece, images[0].getWidth(null)
						* _pcls137.checkers.getRowCount(), images[0]
						.getHeight(null)
						* _pcls137.checkers.getColCount());
		addChildObject(ba_a, 1, 1, 0, 0);
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		getParent()._qo(
				new SquareImage(ImageReader.getImage(
						YahooCheckersImageList.data[6], false)));
		getParent().nc(Color.white, new Color(0xe8edef), new Color(0x8597af),
				new Color(0x3e4e5b), null, new Color(0x3e4e5b));
	}
}
