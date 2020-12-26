// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooGraphics;

import common.io.YData;
import common.po.Point;
import common.po.PoolMath;
import common.po.YPoint;

// Referenced classes of package y.po:
// _cls79, _cls111, _cls45, _cls169,
// _cls16, _cls78, _cls116

public class English extends YahooControl implements YData {

	Color			e_a;
	Color			e_b;
	Color			e_c;
	int				e_d;
	int				e;
	Image			e_f;
	Image			e_g;
	Image			h;
	int				i;
	int				j;
	int				k;
	YPoint			l;
	YPoint			m;
	YPoint			e_n;
	boolean			e_visible;
	Point			e_p;
	YahooComponent	e_q;
	private boolean	active;
	String			caption;
	private boolean	changed;
	Color			e_u;
	private YPoint	e_v;

	public English() {
		super(50, 100);
		e_a = Color.white;
		e_b = Color.red;
		e_c = Color.red;
		i = 2;
		j = 20;
		k = 6;
		e_visible = false;
		e_p = new Point(0, 0);
		caption = "";
		changed = false;
		e_u = new Color(97, 141, 38);
		e_v = new YPoint();
		e_d = 23;
		e = e_d * 3 / 5;
		l = new YPoint(e_d, e_d);
		m = new YPoint(e_d, e_d);
		e_n = new YPoint();
		e_f = YahooPoolImageList.loadImages().u;
		e_g = YahooPoolImageList.loadImages().v;
		h = YahooPoolImageList.loadImages().w;
	}

	public YPoint Db() {
		YPoint _lcls169 = new YPoint(m.x - l.x, m.y - l.y);
		float f1 = 0.5F;
		_lcls169.setCoords((int) (_lcls169.x * f1), (int) (_lcls169.y * f1));
		return _lcls169;
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		if (event.modifiers == 4) {
			e_v.setCoords(i1 - i, j1 - j);
			l.distance(e_v);
		}
		e_v.setCoords(i1 - i, j1 - j);
		if (active && l.distance(e_v) <= e)
			setPos(i1 - i, j1 - j);
		invalidate();
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		e_v.setCoords(i1 - i, j1 - j);
		if (active && l.distance(e_v) <= e)
			setPos(i1 - i, j1 - j);
		invalidate();
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		e_v.setCoords(i1 - i, j1 - j);
		if (active && l.distance(e_v) <= e)
			setPos(i1 - i, j1 - j);
		invalidate();
		return true;
	}

	public YPoint getPos() {
		return Db();
	}

	public boolean isChanged() {
		return changed;
	}

	public void Ob(YahooComponent _pcls78) {
		e_q = _pcls78;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		if (e_visible) {
			super.paint(yahooGraphics);
			yahooGraphics.drawImage(e_f, i, j, null);
			yahooGraphics.drawImage(e_g, (int) l.x + i - k, (int) l.y + j - k,
					null);
			yahooGraphics.setColor(e_u);
			yahooGraphics.drawOval(i + e_d - e, j + e_d - e, e * 2, e * 2);
			yahooGraphics.drawImage(h, (int) m.x + i - k, (int) m.y + j - k,
					null);
			yahooGraphics.setColor(Color.black);
			yahooGraphics.drawString(caption, 5, 80);
		}
	}

	public void read(DataInput input) throws IOException {
		input.readInt();
		e_n.x = PoolMath.yintToFloat(input.readInt());
		e_n.y = PoolMath.yintToFloat(input.readInt());
	}

	public void reset() {
		setPos((int) l.x, (int) l.y);
		invalidate();
	}

	public void setActive(boolean flag) {
		active = flag;
	}

	public void setCaption(String s1) {
		caption = s1;
	}

	public void setChanged(boolean flag) {
		changed = flag;
	}

	public void setPos(int i1, int j1) {
		m.setCoords(i1, j1);
		changed = true;
	}

	public void setVisible(boolean flag) {
		e_visible = flag;
	}

	public void update() {
		m = e_n;
		invalidate();
	}

	public void write(DataOutput output) throws IOException {
		byte byte0 = 8;
		output.writeInt(byte0);
		output.writeInt(PoolMath.floatToYInt(m.x));
		output.writeInt(PoolMath.floatToYInt(m.y));
	}
}
