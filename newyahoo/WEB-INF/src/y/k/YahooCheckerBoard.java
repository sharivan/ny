// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package y.k;

import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Image;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;

import common.k.BoardGame;

// Referenced classes of package y.k:
// _cls117, _cls78, _cls130, _cls127,
// _cls62, _cls100

public class YahooCheckerBoard extends YahooBoard {

	public static final Color	ycb_c	= new Color(0x162d46);
	Color						pieceColors[];
	Color						ycb_e[];
	Color						ycb_f[];
	boolean						ycb_g;
	protected boolean			ycb_h;
	Image						pieceImages[];
	Image						images[];
	protected int				ycb_k;
	protected int				ycb_l;
	protected int				ycb_m;
	protected int				ycb_n;
	boolean						ycb_o;
	boolean						ycb_p;
	protected int				ycb_q;
	protected int				ycb_r;
	protected int				ycb_s;
	protected int				ycb_t;
	protected int				ycb_u;
	protected int				ycb_v;

	public YahooCheckerBoard(BoardGame game, Color color, Color color1,
			boolean flag, boolean flag1, boolean flag2, Image pieceImages[],
			int i1, int j1) {
		super(game.getRowCount(), game.getColCount(), 2, flag1);
		pieceColors = new Color[2];
		ycb_e = new Color[2];
		ycb_f = new Color[2];
		ycb_o = true;
		ycb_p = true;
		pieceColors[0] = color;
		pieceColors[1] = color1;
		ycb_g = flag;
		ycb_h = flag2;
		this.pieceImages = pieceImages;
		for (int k1 = 0; k1 < 2; k1++) {
			ycb_e[k1] = new Color(Math.max(pieceColors[k1].getRed() - 64, 0),
					Math.max(pieceColors[k1].getGreen() - 64, 0), Math.max(
							pieceColors[k1].getBlue() - 64, 0));
			ycb_f[k1] = new Color(Math.min(pieceColors[k1].getRed() + 32, 255),
					Math.min(pieceColors[k1].getGreen() + 32, 255), Math.min(
							pieceColors[k1].getBlue() + 32, 255));
		}

		ycb_m = i1 - i1 % game.getRowCount();
		ycb_n = j1 - j1 % game.getColCount();
		ycb_k = ycb_m / game.getRowCount();
		ycb_l = ycb_n / game.getColCount();
		ycb_p = true;
	}

	public YahooCheckerBoard(BoardGame game, Image images[], boolean flag,
			boolean flag1, boolean flag2, Image pieceImages[], int i1, int j1) {
		this(game, Color.red, Color.white, flag, flag1, flag2, pieceImages, i1,
				j1);
		this.images = images;
		ycb_p = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see y.k.YahooBoard#canDraw()
	 */
	@Override
	protected boolean canDraw() {
		return !ycb_p || pieceColors[0] != pieceColors[1];
	}

	@Override
	public void computeBoardPosition(int i1, int j1) {
		i1 -= 12;
		j1 -= 12;
		if (!ycb_p) {
			i1 -= 4;
			j1 -= 4;
		}
		if (i1 < 0 || j1 < 0) {
			super.bpX = -1;
			return;
		}
		int k1 = i1 / ycb_k;
		int l1 = j1 / ycb_l;
		if (k1 >= super.boardRowCount || l1 >= super.boardColCount) {
			super.bpX = -1;
			return;
		}
		if (super.myTurn == 1 && ycb_h) {
			k1 = super.boardRowCount - k1 - 1;
			l1 = super.boardColCount - l1 - 1;
		}
		super.bpX = k1;
		super.bpY = l1;
	}

	@Override
	public void Dn() {
		ycb_o = true;
		super.Dn();
	}

	@Override
	protected void drawSquare(YahooGraphics yahooGraphics, int x, int y) {
		int x1 = x;
		int y1 = y;
		if (super.myTurn == 1 && ycb_h) {
			x1 = super.boardRowCount - x - 1;
			y1 = super.boardColCount - y - 1;
		}
		ycb_q = x1 * ycb_k + 12;
		ycb_r = y1 * ycb_l + 12;
		ycb_s = (x1 + 1) * ycb_k - 1 + 12;
		ycb_t = (y1 + 1) * ycb_l - 1 + 12;
		if (!ycb_p) {
			ycb_q += 4;
			ycb_r += 4;
			ycb_s += 4;
			ycb_t += 4;
		}
		int i2 = (x1 + y1) % 2;
		ycb_u = ycb_q + ycb_k / 2;
		ycb_v = ycb_r + ycb_l / 2;
		if (ycb_p) {
			if (zo(x, y) && pieceColors[0] == pieceColors[1])
				yahooGraphics.setColor(ycb_f[i2]);
			else
				yahooGraphics.setColor(pieceColors[i2]);
			yahooGraphics.fillRect(ycb_q, ycb_r, ycb_k, ycb_l);
		}
		else {
			yahooGraphics.drawImage(images[i2], ycb_q, ycb_r, null);
		}
		if (ycb_p && pieceColors[0] == pieceColors[1])
			if (ycb_g) {
				yahooGraphics.setColor(ycb_f[0]);
				yahooGraphics.drawLine(ycb_q, ycb_r, ycb_s, ycb_r);
				yahooGraphics.drawLine(ycb_q, ycb_r, ycb_q, ycb_t);
				yahooGraphics.setColor(ycb_e[0]);
				yahooGraphics.drawLine(ycb_s, ycb_t, ycb_q, ycb_t);
				yahooGraphics.drawLine(ycb_s, ycb_t, ycb_s, ycb_r);
			}
			else {
				yahooGraphics.setColor(ycb_e[0]);
				if (x1 > 0)
					yahooGraphics.drawLine(ycb_u, y1 != 0 ? ycb_r : ycb_v,
							ycb_u, y1 != super.boardColCount - 1 ? ycb_t
									: ycb_v + 1);
				if (y1 > 0)
					yahooGraphics.drawLine(x1 != 0 ? ycb_q : ycb_u, ycb_v,
							x1 != super.boardRowCount - 1 ? ycb_s : ycb_u + 1,
							ycb_v);
				yahooGraphics.setColor(ycb_f[0]);
				if (x1 < super.boardRowCount - 1)
					yahooGraphics.drawLine(ycb_u + 1, y1 != 0 ? ycb_r : ycb_v,
							ycb_u + 1, y1 != super.boardColCount - 1 ? ycb_t
									: ycb_v + 1);
				if (y1 < super.boardColCount - 1)
					yahooGraphics.drawLine(x1 != 0 ? ycb_q : ycb_u, ycb_v + 1,
							x1 != super.boardRowCount - 1 ? ycb_s : ycb_u + 1,
							ycb_v + 1);
			}
		Image image = Ui(super.yb_a[y][x]);
		if (image != null && (x != super.k || y != super.l || super.yb_v)) {
			int j2 = image.getHeight(null);
			int k2 = image.getWidth(null);
			yahooGraphics.drawImage(Ui(super.yb_a[y][x]), ycb_u - k2 / 2, ycb_v
					- j2 / 2, null);
		}
		if (super.lastX == x && super.lastY == y && super.yb_o)
			if (super.yb_s) {
				yahooGraphics.setColor(Color.blue);
				yahooGraphics.drawRect(ycb_q, ycb_r, ycb_k - 1, ycb_k - 1);
				yahooGraphics.drawRect(ycb_q + 1, ycb_r + 1, ycb_k - 3,
						ycb_k - 3);
			}
			else {
				yahooGraphics.setColor(Color.red);
				yahooGraphics.drawLine(ycb_u - ycb_k / boardRowCount, ycb_v,
						ycb_u + ycb_k / boardColCount, ycb_v);
				yahooGraphics.drawLine(ycb_u, ycb_v - ycb_l / boardRowCount,
						ycb_u, ycb_v + ycb_l / boardColCount);
			}
		if (canDraw()) {
			if (myTurn != -1 && (currTurn == myTurn || currTurn == -2)
					&& cyanSquares[y][x]) {
				yahooGraphics.setColor(Color.cyan);
				yahooGraphics.drawLine(ycb_q, ycb_r, ycb_s, ycb_r);
				yahooGraphics.drawLine(ycb_q, ycb_r, ycb_q, ycb_t);
				yahooGraphics.drawLine(ycb_s, ycb_t, ycb_q, ycb_t);
				yahooGraphics.drawLine(ycb_s, ycb_t, ycb_s, ycb_r);
				yahooGraphics.drawLine(ycb_q + 1, ycb_r + 1, ycb_s - 1,
						ycb_r + 1);
				yahooGraphics.drawLine(ycb_q + 1, ycb_r + 1, ycb_q + 1,
						ycb_t - 1);
				yahooGraphics.drawLine(ycb_s - 1, ycb_t - 1, ycb_q + 1,
						ycb_t - 1);
				yahooGraphics.drawLine(ycb_s - 1, ycb_t - 1, ycb_s - 1,
						ycb_r + 1);
			}
			if (zo(x, y)) {
				yahooGraphics.setColor(Color.yellow);
				yahooGraphics.drawLine(ycb_q, ycb_r, ycb_s, ycb_r);
				yahooGraphics.drawLine(ycb_q, ycb_r, ycb_q, ycb_t);
				yahooGraphics.drawLine(ycb_s, ycb_t, ycb_q, ycb_t);
				yahooGraphics.drawLine(ycb_s, ycb_t, ycb_s, ycb_r);
				yahooGraphics.drawLine(ycb_q + 1, ycb_r + 1, ycb_s - 1,
						ycb_r + 1);
				yahooGraphics.drawLine(ycb_q + 1, ycb_r + 1, ycb_q + 1,
						ycb_t - 1);
				yahooGraphics.drawLine(ycb_s - 1, ycb_t - 1, ycb_q + 1,
						ycb_t - 1);
				yahooGraphics.drawLine(ycb_s - 1, ycb_t - 1, ycb_s - 1,
						ycb_r + 1);
			}
		}
	}

	@Override
	public int getHeight1() {
		if (ycb_p)
			return ycb_n + 24;
		return ycb_n + 32 + 2;
	}

	@Override
	public int getWidth1() {
		if (ycb_p)
			return ycb_m + 24;
		return ycb_m + 32 + 2;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		if (ycb_o) {
			Si(yahooGraphics);
			ycb_o = false;
		}
	}

	@Override
	protected void Ri(Event event, int i1, int j1) {
		Vg(new PieceImage(Ui(super.yb_a[super.l][super.k]), ycb_k, ycb_l),
				event, i1, j1);
	}

	@Override
	public void setMyTurn(int i1) {
		ycb_o = true;
		super.setMyTurn(i1);
	}

	public void Si(YahooGraphics _pcls100) {
		FontMetrics fontmetrics = getFontMetrics(YahooComponent.defaultFont);
		_pcls100.setFont(YahooComponent.defaultFont);
		if (ycb_p) {
			_pcls100.setColor(Color.black);
			_pcls100.fillRect(0, 0, 12, ycb_n + 24);
			_pcls100.fillRect(12, 0, ycb_m + 12, 12);
			_pcls100.fillRect(12, ycb_n + 12, ycb_m, 12);
			_pcls100.fillRect(12 + ycb_m, 12, 12, ycb_n + 12);
			_pcls100.setColor(Color.white);
		}
		else {
			_cls78.hi(_pcls100, 12, 12, ycb_k * super.boardRowCount + 7, ycb_l
					* super.boardColCount + 7);
			_pcls100.setColor(ycb_c);
		}
		for (int i1 = 0; i1 < super.boardRowCount; i1++) {
			int j1 = i1 >= boardRowCount ? i1 + 1 : i1;
			char ac[] = { (char) ((super.myTurn != 1 || !ycb_h ? j1
					: super.boardRowCount - 1 - j1) + 97) };
			String s2 = new String(ac);
			if (ycb_p)
				_pcls100.drawString(s2, 12 + i1 * ycb_k + ycb_k / 2
						- fontmetrics.stringWidth(s2) / 2, ycb_n + 24 - 1);
			else
				_pcls100.drawString(s2, 16 + i1 * ycb_k + ycb_k / 2
						- fontmetrics.stringWidth(s2) / 2, ycb_n + 32 - 1);
		}

		for (int k1 = 0; k1 < super.boardColCount; k1++) {
			String s1 = Integer
					.toString(((super.myTurn != 1 || !ycb_h ? super.boardColCount
							- 1 - k1
							: k1) + 1) % 10);
			if (ycb_p)
				_pcls100.drawString(s1, 0, 12 + k1 * ycb_l + ycb_l / 2
						+ fontmetrics.getHeight() / 2);
			else
				_pcls100.drawString(s1, 1, 12 + k1 * ycb_l + ycb_l / 2
						+ fontmetrics.getHeight() / 2);
		}

	}

	protected Image Ui(int i1) {
		return pieceImages[i1];
	}

	@Override
	protected void Wi(int i1, int j1) {
		if (super.myTurn == 1 && ycb_h) {
			i1 = super.boardRowCount - i1 - 1;
			j1 = super.boardColCount - j1 - 1;
		}
		super.bpX = i1 * ycb_k + 12;
		super.bpY = j1 * ycb_l + 12;
		if (!ycb_p) {
			super.bpX += 4;
			super.bpY += 4;
		}
	}

}
