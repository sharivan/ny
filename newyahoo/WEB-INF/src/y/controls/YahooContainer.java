// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import y.utils.Processor;
import y.utils.TimerEntry;
import y.utils.TimerHandler;
import y.utils._cls67;

// Referenced classes of package y.po:
// _cls79, _cls67, _cls86, _cls50,
// _cls177, _cls146, _cls168, _cls78,
// _cls116, _cls173

public class YahooContainer extends YahooControl implements TimerHandler {

	public int				yc_a;
	public YahooCanvas		canvas;
	public YahooGraphics	yahooGraphics;
	public Container		container;
	public YahooComponent	e;
	public YahooComponent	yc_f;
	public boolean			yc_g;
	public boolean			h;
	public TimerEntry		timer;
	public boolean			j;
	public boolean			invalidateOn;
	public _cls67			m;
	public boolean			yc_n;
	public Processor		processor;
	private int				minLeft;
	private int				maxRigth;
	private int				minTop;
	private int				maxBottom;
	public boolean			yc_t;
	public YahooComponent	yc_u;
	public int				yc_v;
	public int				yc_w;

	public YahooContainer(Container container, Processor _pcls173) {
		yc_a = 50;
		yahooGraphics = new YahooGraphics();
		yc_g = false;
		h = false;
		m = new _cls67(10);
		minLeft = -1;
		maxRigth = -1;
		minTop = -1;
		maxBottom = -1;
		((YahooComponent) this).backColor = Color.lightGray;
		((YahooComponent) this).foreColor = Color.black;
		((YahooComponent) this).ante_bbbackground = Color.lightGray;
		((YahooComponent) this).ante_bbshadow = Color.darkGray;
		((YahooComponent) this).ante_bboutlinelight = Color.white;
		((YahooComponent) this).ante_bboutlinedark = Color.black;
		container.setLayout(new BorderLayout());
		canvas = new YahooCanvas(this);
		this.container = container;
		processor = _pcls173;
		container.add("Center", canvas);
		timer = _pcls173.timerHandler.add(this);
	}

	public void Bb() {
		if (minLeft == -1)
			yc_t = true;
		jb();
	}

	@Override
	public YahooControl getContainer() {
		return this;
	}

	public Dimension getDimension() {
		return new Dimension(getWidth1(), getHeight1());
	}

	@Override
	public FontMetrics getFontMetrics(Font font) {
		return canvas.getFontMetrics(font);
	}

	public void handleTimer(long l) {
		if (invalidateOn) {
			invalidateOn = false;
			ob();
		}
	}

	void jb() {
		int l = m.Ol();
		invalidateOn = true;
		if (timer != null)
			timer.setInterval(Math.max(15, l * 100 / yc_a));
		else
			ob();
	}

	public void mb(Event event) {
		YahooComponent _lcls78 = null;
		YahooComponent _lcls78_1 = yc_f;
		switch (event.id) {
		case Event.MOUSE_DOWN:
			_lcls78 = e = componentAtPos(event.x, event.y);
			if (h && _lcls78_1 != null && _lcls78_1 != _lcls78) {
				_lcls78_1.doEvent(new Event(_lcls78_1, 1005, null));
				yc_f = null;
			}
			break;

		case Event.MOUSE_MOVE:
			_lcls78 = componentAtPos(event.x, event.y);
			if (yc_u != _lcls78) {
				if (yc_u != null) {
					Event event1 = new Event(yc_u, event.when, 505, yc_v, yc_w,
							event.key, event.modifiers);
					event1.x -= yc_u.getWorldLeft(this);
					event1.y -= yc_u.getWorldTop(this);
					yc_u.doEvent(event1);
				}
				Event event2 = new Event(_lcls78, event.when, 504, event.x,
						event.y, event.key, event.modifiers);
				event2.x -= _lcls78.getWorldLeft(this);
				event2.y -= _lcls78.getWorldTop(this);
				_lcls78.doEvent(event2);
				yc_u = _lcls78;
				yc_v = event.x;
				yc_w = event.y;
			}
			break;

		case Event.MOUSE_EXIT:
			_lcls78 = yc_u;
			yc_u = null;
			break;

		case Event.MOUSE_ENTER:
			_lcls78 = componentAtPos(event.x, event.y);
			yc_u = _lcls78;
			yc_v = event.x;
			yc_w = event.y;
			break;

		case Event.MOUSE_DRAG:
			if (super.E != null) {
				event.x += super.G;
				event.y += super.yctrl_H;
				event.arg = super.E;
				super.E.setCoords(event.x, event.y);
				wb();
			}
			_lcls78 = e;
			break;

		case Event.MOUSE_UP:
			_lcls78 = e;
			e = null;
			break;

		case Event.LOST_FOCUS:
			if (yc_g) {
				yc_g = false;
				if (_lcls78_1 != null)
					event.target = _lcls78 = _lcls78_1;
			}
			break;

		case Event.GOT_FOCUS:
			if (!yc_g) {
				yc_g = true;
				if (yc_f != null)
					event.target = _lcls78 = yc_f;
				if (yc_n)
					ob();
			}
			processor.container = this;
			break;

		case Event.KEY_PRESS:
		case Event.KEY_RELEASE:
		case Event.KEY_ACTION:
		case Event.KEY_ACTION_RELEASE:
			_lcls78 = yc_f;
			break;
		}
		if (_lcls78 != null) {
			event.x -= _lcls78.getWorldLeft(this);
			event.y -= _lcls78.getWorldTop(this);
			event.target = _lcls78;
			_lcls78.doEvent(event);
		}
		else {
			processEvent(event);
		}
	}

	public void nb() {
		j = true;
		En();
		if (timer != null)
			timer.stop();
		processor.container = null;
	}

	@Override
	void notifyRemoveComponent(int x, int y, int width, int height) {
		if (x < minLeft || minLeft == -1)
			minLeft = x;
		if (y < minTop || minTop == -1)
			minTop = y;
		if (x + width > maxRigth)
			maxRigth = x + width;
		if (y + height > maxBottom)
			maxBottom = y + height;
		if (minLeft < 0)
			minLeft = 0;
		if (minTop < 0)
			minTop = 0;
	}

	void ob() {
		if (!j)
			if (minLeft != -1) {
				if (!yc_g && yc_n) {
					canvas.repaint(0, 0, 1, 1);
				}
				else {
					int l = minLeft;
					int i1 = maxRigth;
					int j1 = minTop;
					int k1 = maxBottom;
					minLeft = minTop = maxRigth = maxBottom = -1;
					canvas.repaint(l, j1, i1 - l + 1, k1 - j1 + 1);
				}
			}
			else if (yc_t)
				canvas.repaint(0, 0, 1, 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean processEvent(Event event) {
		if (!super.processEvent(event))
			container.handleEvent(event);
		return true;
	}

	@Override
	public void rb() {
		super.rb();
		canvas.invalidate();
	}

	public void setGraphics(Graphics graphics) {
		yahooGraphics.graphics = graphics;
		yahooGraphics.left = 0;
		yahooGraphics.top = 0;
		paint(yahooGraphics);
	}

	public void tb(Graphics graphics, int left, int top, int rigth, int bottom) {
		yahooGraphics.graphics = graphics;
		yahooGraphics.left = -left;
		yahooGraphics.top = -top;
		super.zn(yahooGraphics, left, top, rigth, bottom);
	}

	@Override
	public void vb() {
		super.vb();
		jb();
	}

	@Override
	public void wb() {
		jb();
	}

	@Override
	public void xb(YahooComponent _pcls78, boolean flag) {
		YahooComponent component = yc_f;
		yc_f = _pcls78;
		h = flag;
		if (!yc_g)
			canvas.requestFocus();
		if (yc_g && component != yc_f) {
			if (component != null)
				component.doEvent(new Event(component, 1005, null));
			yc_f.doEvent(new Event(yc_f, 1004, null));
		}
	}

	public void yb(int l, int i1, int j1, int k1) {
		super.width = j1;
		super.height = k1;
		m();
	}
}
