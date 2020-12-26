// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;

// Referenced classes of package y.po:
// _cls79, _cls116

public class YahooComponent {

	public static final Color	defaultColor	= new Color(128);
	@SuppressWarnings("deprecation")
	public static final Font	defaultFont		= new Font(Toolkit
														.getDefaultToolkit()
														.getFontList()[0], 0,
														13);

	public boolean				a;
	public boolean				invalidated;
	public boolean				c;
	public YahooComponent		d;
	public YahooControl			parent;
	public int					left;
	public int					top;
	public int					width;
	public int					height;
	public int					n;
	public int					o;
	public int					colCount;
	public int					rowCount;
	public int					col;
	public int					row;
	public int					t;
	public int					u;
	public int					v;
	public int					w;
	public int					x;
	public boolean				useAbsoluteCoords;
	public boolean				z;
	public boolean				A;
	public Color				foreColor;
	public Color				backColor;
	public Color				ante_bbbackground;
	public Color				ante_bbshadow;
	public Color				ante_bboutlinelight;
	public Color				ante_bboutlinedark;
	public boolean				H;
	public boolean				visible;
	public boolean				J;
	protected int				width1;
	protected int				height1;

	public YahooComponent() {
		this(false);
	}

	public YahooComponent(boolean flag) {
		this(flag, 0, 0);
	}

	public YahooComponent(boolean visible, int width, int height) {
		invalidated = false;
		c = false;
		d = null;
		z = true;
		A = true;
		J = false;
		this.visible = visible;
		width1 = width;
		height1 = height;
	}

	public YahooComponent(int width, int height) {
		this(false, width, height);
	}

	int Bn() {
		return getHeight1() + t + v;
	}

	int Cn() {
		return getWidth1() + u + w;
	}

	public YahooComponent componentAtPos(int x, int y) {
		return this;
	}

	public boolean contains(int x, int y) {
		return x >= left && x < left + width && y >= top && y < top + height;
	}

	boolean containsLine(int x1, int y1, int x2, int y2) {
		boolean flag = x1 < left + width && y1 < top + height && x2 >= left
				&& y2 >= top;
		return flag;
	}

	public void Dn() {
		z = true;
	}

	public final void doEvent(Event event) {
		YahooComponent component = d;
		if (event.id == Event.KEY_PRESS && event.key == Event.TAB
				&& component != null) {
			component.Gn(true);
		}
		else {
			if (!processEvent(event)) {
				event.x += left;
				event.y += top;
				if (parent != null)
					parent.doEvent(event);
			}
		}
	}

	public void En() {
		invalidated = false;
	}

	public boolean eventActionEvent(Event event, Object obj) {
		return false;
	}

	public boolean eventKeyPress(Event event, int key) {
		return false;
	}

	public boolean eventKeyRelease(Event event, int key) {
		return false;
	}

	public boolean eventMouseDown(Event event, int x, int y) {
		return false;
	}

	public boolean eventMouseDrag(Event event, int x, int y) {
		return false;
	}

	public boolean eventMouseEnter(Event event, int x, int y) {
		return false;
	}

	public boolean eventMouseExit(Event event, int x, int y) {
		return false;
	}

	public boolean eventMouseMove(Event event, int x, int y) {
		return false;
	}

	public boolean eventMouseUp(Event event, int x, int y) {
		return false;
	}

	public Color getBackColor() {
		return backColor != null ? backColor : parent == null ? null : parent
				.getBackColor();
	}

	public YahooControl getContainer() {
		if (parent != null)
			return parent.getContainer();
		return null;
	}

	public FontMetrics getFontMetrics(Font font) {
		if (parent != null)
			return parent.getFontMetrics(font);
		return null;
	}

	public Color getForeColor() {
		return foreColor != null ? foreColor : parent == null ? null : parent
				.getForeColor();
	}

	public int getHeight() {
		return height;
	}

	public int getHeight1() {
		return height1;
	}

	public YahooControl getParent() {
		return parent;
	}

	public YahooComponent getParentBackColor() {
		return backColor == null ? parent == null ? null : parent
				.getParentBackColor() : null;
	}

	public int getWidth() {
		return width;
	}

	public int getWidth1() {
		return width1;
	}

	public int getWorldLeft(YahooComponent component) {
		if (component == this)
			return 0;
		return left + parent.getWorldLeft(component);
	}

	public int getWorldTop(YahooComponent component) {
		if (component == this)
			return 0;
		return top + parent.getWorldTop(component);
	}

	public void Gn(boolean flag) {
		xb(this, flag);
	}

	public void Hn(boolean flag) {
		J = flag;
	}

	public Color in() {
		return ante_bbbackground != null ? ante_bbbackground
				: parent == null ? null : parent.in();
	}

	public void invalidate() {
		if (!invalidated)
			return;
		notifyRemoveComponent(0, 0, width, height);
		if (!J)
			vb();
	}

	public Color jn() {
		return ante_bboutlinedark != null ? ante_bboutlinedark
				: parent == null ? null : parent.jn();
	}

	public Color kn() {
		return ante_bboutlinelight != null ? ante_bboutlinelight
				: parent == null ? null : parent.kn();
	}

	public Color ln() {
		return ante_bbshadow != null ? ante_bbshadow : parent == null ? null
				: parent.ln();
	}

	public void m() {
	}

	public void nc(Color color, Color color1, Color color2, Color color3,
			Color color4, Color color5) {
		foreColor = color;
		backColor = color1;
		ante_bbbackground = color2;
		ante_bbshadow = color5;
		ante_bboutlinelight = color4;
		ante_bboutlinedark = color3;
		invalidate();
	}

	void notifyRemoveComponent(int x, int y, int width, int height) {
		if (parent != null)
			parent.notifyRemoveComponent(x + left, y + top, width, height);
	}

	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
	}

	public void paint(YahooGraphics graphics, int left, int top, int rigth,
			int bottom) {
		paint(graphics);
	}

	public void paintTo(YahooGraphics graphics) {
		if (!a) {
			YahooComponent component = getParentBackColor();
			if (component == this || component == null) {
				graphics.setColor(getBackColor());
				graphics.fillRect(0, 0, width, height);
			}
			else {
				int x = getWorldLeft(component.getParent());
				int y = getWorldTop(component.getParent());
				if (component.visible && !visible) {
					YahooGraphics graphics1 = null;
					try {
						graphics1 = graphics.create(0, 0, width, height);
						graphics1.left = -x;
						graphics1.top = -y;
						component.paint(graphics1, x, y, width, height);
					}
					finally {
						if (graphics1 != null) {
							graphics1.dispose();
							graphics1 = null;
						}
					}
				}
				else {
					graphics.left -= x;
					graphics.top -= y;
					component.paint(graphics, x, y, width, height);
					graphics.left += x;
					graphics.top += y;
				}
			}
		}
	}

	public boolean processEvent(Event event) {
		if (event.id == Event.MOUSE_DOWN)
			return eventMouseDown(event, event.x, event.y);
		if (event.id == Event.MOUSE_UP)
			return eventMouseUp(event, event.x, event.y);
		if (event.id == Event.MOUSE_DRAG)
			return eventMouseDrag(event, event.x, event.y);
		if (event.id == Event.MOUSE_MOVE)
			return eventMouseMove(event, event.x, event.y);
		if (event.id == Event.MOUSE_ENTER)
			return eventMouseEnter(event, event.x, event.y);
		if (event.id == Event.MOUSE_EXIT)
			return eventMouseExit(event, event.x, event.y);
		if (event.id == Event.ACTION_EVENT)
			return eventActionEvent(event, event.arg);
		if (event.id == 0x10000)
			return wn(event, event.x, event.y);
		if (event.id == Event.KEY_PRESS || event.id == Event.KEY_ACTION)
			return eventKeyPress(event, event.key);
		if (event.id == Event.KEY_RELEASE
				|| event.id == Event.KEY_ACTION_RELEASE)
			return eventKeyRelease(event, event.key);
		return false;
	}

	public void Qn(boolean flag) {
		A = flag;
		if (flag && z)
			invalidate();
	}

	public void rb() {
		H = false;
		if (parent != null)
			parent.rb();
		invalidate();
	}

	public void realingChilds() {
		invalidated = true;
	}

	public void setBackColor(Color color) {
		backColor = color;
		invalidate();
	}

	public void setBounds(int left, int top, int width, int height) {
		notifyRemoveComponent(0, 0, width, height);
		this.left = left;
		this.top = top;
		this.width = width1 = width;
		this.height = height1 = height;
		if (H) {
			m();
			invalidate();
		}
	}

	public void setCoords(int i1, int j1) {
		setCoords(i1, j1, true);
	}

	public void setCoords(int i1, int j1, boolean erase) {
		if (erase)
			notifyRemoveComponent(0, 0, width, height);
		left = i1;
		top = j1;
		notifyRemoveComponent(0, 0, width, height);
	}

	public void setForeColor(Color color) {
		foreColor = color;
		invalidate();
	}

	public void Sn(boolean flag) {
		a = flag;
	}

	public void Tn(int i1) {
		Un(i1, true);
	}

	public void Un(int i1, boolean flag) {
		rowCount = i1;
		if (parent != null && flag)
			parent.ro();
	}

	public void vb() {
		z = true;
		if (parent != null)
			parent.vb();
	}

	public final void Vg(YahooComponent _pcls62, Event event, int i1, int j1) {
		getContainer().Ah(this, _pcls62, event, i1, j1);
	}

	public void wb() {
		if (parent != null)
			parent.wb();
	}

	public boolean wn(Event event, int i1, int j1) {
		return false;
	}

	public void xb(YahooComponent _pcls78, boolean flag) {
		parent.xb(_pcls78, flag);
	}

	protected void zn(YahooGraphics graphics, int left, int top, int rigth,
			int bottom) {
		if (J) {
			Dn();
			paint(graphics);
		}
	}

}
