// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;

import y.controls.YahooComponent;
import y.controls.YahooGraphics;
import y.utils.YahooImage;

import common.po.IBall;
import common.po.PoolConsts;
import common.po.PoolMath;
import common.po.Vel;
import common.po.YPoint;
import common.po.YVector;

// Referenced classes of package y.po:
// _cls13, _cls94, _cls89, _cls45,
// _cls124, _cls33, _cls69, _cls169,
// _cls108, _cls16, _cls78, _cls116

public class BallSprite extends YahooImage {
	static boolean	C;
	int				radius;
	boolean			active;
	IBall			poolBall;
	boolean			bs_d;
	public YPoint	e;
	private Image	bs_f;
	Image			bs_g;
	boolean			inSlot;
	boolean			k;
	boolean			isWhite;
	boolean			isBlack;
	Image			image;
	YahooComponent	bs_o;
	boolean			bs_p;
	String			color;
	boolean			bs_r;
	YVector			bs_s;
	YVector			bs_t;
	YVector			bs_u;
	String			bs_v;
	Image			bs_x[];
	Image			bs_y;
	int				bs_A;

	public BallSprite(IBall _pcls124) {
		super(null, 20, 20);
		active = false;
		inSlot = false;
		k = true;
		isWhite = false;
		isBlack = false;
		bs_p = false;
		bs_s = new YVector();
		bs_t = new YVector();
		bs_u = new YVector();
		bs_v = "";
		bs_A = 2;
		poolBall = _pcls124;
		bs_o = new YahooBallImage(this);
		radius = (int) PoolMath.yintToFloat(_pcls124.getRadius());
		Wd();
		Hn(true);
		Sn(true);
		inSlot = _pcls124.inSlot();
		bs_d = !_pcls124.Jv();
		int j = 0;
		for (int i1 = _pcls124.vv(); i1 != 1;) {
			i1 >>= 1;
			j++;
		}

		color = PoolConsts.color[j];
		if (color.equals("white")) {
			isWhite = true;
			image = YahooPoolImageList.loadImages().whiteBall;
		}
		else if (color.equals("black"))
			isBlack = true;
		e = new YPoint((float) ((Math.random() - 0.5D) * radius),
				(float) ((Math.random() - 0.5D) * radius));
		if (bs_d)
			bs_x = YahooPoolImageList.loadImages().y;
		else
			bs_x = YahooPoolImageList.loadImages().z;
		if (isWhite) {
			bs_f = image;
		}
		else {
			bs_f = bs_x[Od()];
			bs_g = YahooPoolImageList.loadImages().B[j];
		}
		bs_o = new YahooBallImage(this);
		bs_y = YahooPoolImageList.loadImages().A[poolBall.getIndex()];
	}

	public void ae(int j) {
		Tn(j);
		((YahooBallImage) bs_o).dp();
	}

	public void be() {
		e.setCoords(5F, 5F);
		bs_p = true;
	}

	public boolean containsPoint(int j, int i1) {
		int j1 = getLeft1() + radius - j;
		int k1 = getTop1() + radius - i1;
		int l1 = j1 * j1 + k1 * k1;
		return l1 < radius * radius;
	}

	public void ee(Vel _pcls33) {
		bs_u.setCoords(e.x, e.y);
		bs_u.rotate(-1F * PoolMath.yintToFloat(_pcls33.ke()));
		e.setCoords(bs_u.x, bs_u.y);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int i1) {
		return false;
	}

	@Override
	public boolean eventMouseDrag(Event event, int j, int i1) {
		return false;
	}

	@Override
	public boolean eventMouseMove(Event event, int j, int i1) {
		return false;
	}

	@Override
	public boolean eventMouseUp(Event event, int j, int i1) {
		return false;
	}

	public void fe(int j, int i1, YVector _pcls108, Vel _pcls33) {
		j = (int) PoolMath.yintToFloat(j);
		i1 = (int) PoolMath.yintToFloat(i1);
		setCenter(j, i1);
		if (_pcls108 != null && _pcls108.abs() != 0.0F) {
			Xd(_pcls108);
			bs_r = true;
		}
		if (_pcls33 != null && _pcls33.he() > 0) {
			ee(_pcls33);
			bs_r = true;
		}
	}

	public int getIndex() {
		return poolBall.getIndex();
	}

	public boolean getInSlot() {
		return poolBall.inSlot();
	}

	public int getLeft1() {
		return super.left;
	}

	public int getTop1() {
		return super.top;
	}

	public boolean isMoving() {
		return poolBall.isMoving();
	}

	public void Ld(int j, int i1, YVector _pcls108, Vel _pcls33) {
		fe(j, i1, _pcls108, _pcls33);
		setInSlot(false);
		setActive(true);
	}

	public int Od() {
		int j = (int) (e.x / bs_A);
		int i1 = (int) (e.y / bs_A);
		if (Math.abs(j) > 5 - Math.abs(i1))
			if (j < 0)
				j = Math.abs(i1) - 5;
			else
				j = 5 - Math.abs(i1);
		return j * 10 + 50 + i1;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		if (active) {
			super.paint(yahooGraphics);
			if (!isWhite) {
				int j = Od();
				bs_f = bs_x[j];
				yahooGraphics.drawImage(bs_g, 0, 0, this);
			}
			yahooGraphics.drawImage(bs_f, 0, 0, this);
			if (bs_p && bs_y != null)
				yahooGraphics.drawImage(bs_y, 5, 3, this);
			if (isWhite && k) {
				yahooGraphics.setColor(new Color(110, 156, 233));
				if (e.abs() < radius - 4)
					yahooGraphics.fillOval((int) (radius + e.x - 2.0F),
							(int) (radius + e.y - 2.0F), 4, 4);
				else if (e.abs() < radius - 3)
					yahooGraphics.fillOval((int) (radius + e.x - 2.0F),
							(int) (radius + e.y - 2.0F), 3, 3);
				else if (e.abs() < radius - 2)
					yahooGraphics.fillOval((int) (radius + e.x) - 1,
							(int) (radius + e.y) - 1, 2, 2);
				else
					yahooGraphics.fillOval((int) (radius + e.x),
							(int) (radius + e.y), 1, 1);
				if (!bs_d) {
					yahooGraphics.setColor(Color.white);
					yahooGraphics.drawLine(0, radius, radius * 2, radius);
				}
			}
		}
	}

	public YahooComponent Pd() {
		return bs_o;
	}

	public void Sd() {
		bs_p = false;
	}

	public void setActive(boolean flag) {
		if (inSlot && C)
			active = false;
		else
			active = flag;
	}

	public void setCenter(int j, int i1) {
		super.setCoords(j - radius, i1 - radius);
		if (bs_o != null)
			bs_o.setCoords(j - radius, i1 - radius);
	}

	public void setInSlot(boolean flag) {
		inSlot = flag;
	}

	public void Td(int j, int i1) {
		j = (int) PoolMath.yintToFloat(j);
		i1 = (int) PoolMath.yintToFloat(i1);
		setCenter(j, i1);
		setActive(false);
		setInSlot(true);
	}

	public void Wd() {
		setCenter((int) PoolMath.yintToFloat(poolBall.getX()), (int) PoolMath
				.yintToFloat(poolBall.getY()));
	}

	public void Xd(YVector _pcls108) {
		bs_t = _pcls108.setCoordsTo(bs_t);
		float f1 = e.angle(bs_t);
		float f2 = 1.0F - f1 / radius * f1 / radius;
		bs_t.mul(f2);
		e.add(bs_t);
		float f3 = e.abs() - radius;
		if (f3 > 0.0F) {
			bs_s.setCoords(e.x, e.y);
			bs_s.setAbs(f3);
			bs_s.neg();
			bs_s.setAbs(f3);
			e.setCoords(bs_s.x, bs_s.y);
			if (isWhite)
				k = !k;
		}
	}
}
