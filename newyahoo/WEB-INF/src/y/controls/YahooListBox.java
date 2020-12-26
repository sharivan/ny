// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.util.Vector;

import y.utils.Id;
import y.utils.ListItemSort;
import y.utils.NameSort;
import y.utils.YahooImage;

// Referenced classes of package y.po:
// _cls79, _cls160, _cls75, _cls30,
// _cls90, _cls78, _cls116, _cls158,
// _cls155, _cls44, _cls32, _cls123

public class YahooListBox extends YahooControl {

	public long						ylb_f;
	public Vector<ListItem>			items;
	public int						h;
	public int						i;
	public int						itemHeight;
	public int						listColCount;
	public String					cells[];
	public double					proportionalColLeft[];
	public int						ylb_n[];
	public boolean					ylb_o[];
	public double					ylb_p;
	public ListItem					selected;
	public int						ylb_r;
	public int						ylb_s;
	public boolean					ylb_t;
	public boolean					ylb_u;
	public YahooHeader				header;
	public FontMetrics				fontMetrics;
	public Font						listBoxFont;
	public ListItemSort				sorter;
	public int						count;
	public boolean					ylb_A;
	public int						ylb_B;
	public Color					color;
	public _cls44					ylb_D;
	public Vector<YahooComponent>	listBoxComponents;
	public boolean					ylb_F;
	private int						imageHeight;
	private Image[]					avatarImages;

	public YahooListBox() {
		this(4, 50, 1, -1, null, false, true, true, null);
	}

	public YahooListBox(int i1, int j1, int colCount, int l1,
			ListItemSort sorter, boolean flag, boolean flag1, boolean flag2,
			Image[] avatarImages) {
		items = new Vector<ListItem>();
		imageHeight = 0;
		ylb_p = 0.0D;
		selected = null;
		listBoxFont = YahooComponent.defaultFont;
		count = 0;
		ylb_A = false;
		color = Color.lightGray;
		listBoxComponents = new Vector<YahooComponent>();
		ylb_D = new _cls44(this, flag2);
		cells = new String[colCount];
		proportionalColLeft = new double[colCount];
		ylb_o = new boolean[colCount];
		for (int i2 = 0; i2 < colCount; i2++) {
			cells[i2] = "";
			proportionalColLeft[i2] = 1.0D;
		}

		ylb_t = flag1;
		ylb_s = l1;
		this.sorter = sorter = l1 != -1 && sorter == null ? new NameSort()
				: sorter;
		i = j1;
		YahooControl control = new YahooControl();
		header = new YahooHeader(this);
		if (flag) {
			control.addChildObject(header, 10, 2, 2, 1, 1, 0, 0);
			YahooComponent component = new YahooComponent(1, 1);
			control.addChildObject(component, 10, 2, 2, 1, 1, 0, 1);
			component.setBackColor(color);
			listBoxComponents.add(component);
		}
		h = i1;
		listColCount = colCount;
		this.avatarImages = avatarImages;
		ylb_n = new int[colCount + 1];
		YahooComponent cell = new YahooComponent(1, 1);
		listBoxComponents.add(cell);
		addChildObject(cell, 3, 1, 0, 0, false);
		cell = new YahooComponent(1, 1);
		listBoxComponents.add(cell);
		addChildObject(cell, 1, 2, 0, 1, false);
		cell = new YahooComponent(1, 1);
		listBoxComponents.add(cell);
		addChildObject(cell, 3, 1, 0, 3, false);
		cell = new YahooComponent(1, 1);
		listBoxComponents.add(cell);
		addChildObject(cell, 1, 2, 2, 1, false);
		addChildObject(control, 10, 2, 2, 1, 1, 1, 1);
		addChildObject(ylb_D, 1, 1, 1, 2, true);
		setBackColor(Color.white);
	}

	void add(ListItem item) {
		insert(item, sorter == null ? size() : sorter.binarySearch(items, item));
	}

	public ListItem add(String text) {
		return add(text, null);
	}

	public ListItem add(String text, Object obj) {
		ListItem item = new ListItem(this, obj);
		item.colls[0] = text;
		add(item);
		return item;
	}

	public ListItem append(Id id, String message) {
		return append(id, message, getForeColor());
	}

	public ListItem append(Id id, String message, Color color) {
		return insert(id, message, items.size(), color);
	}

	public void append(String text) {
		append(text, getForeColor());
	}

	public void append(String text, Color color) {
		insert(text, items.size(), color);
	}

	public void canvasMouseDown(Event event, int i1, int j1) {
		Event event1 = null;
		int k1 = selected != null ? indexOf(selected) : -1;
		int l1 = k1;
		if (j1 < 0)
			k1 = -1;
		else
			k1 = j1 / itemHeight;
		if (k1 >= items.size())
			k1 = -1;
		invalidate();
		if (event.when - ylb_f < 500L && l1 == k1 && l1 != -1) {
			event1 = new Event(this, 1001, new Integer(l1));
			ylb_f = 0L;
		}
		else if (k1 != -1) {
			for (int i2 = 1; i2 <= listColCount; i2++) {
				if (i1 >= ylb_n[i2])
					continue;
				ylb_r = i2 - 1;
				break;
			}

			event1 = new Event(this, 701, new Integer(k1));
		}
		ylb_f = event.when;
		if (ylb_t)
			if (k1 >= 0)
				selected = items.elementAt(k1);
			else
				selected = null;
		if (event1 != null)
			doEvent(event1);
	}

	public void canvasMouseUp(Event event, int i1, int j1) {
	}

	public void clear() {
		items.clear();
		selected = null;
		ylb_D.Xu();
	}

	public int Ds() {
		return h * itemHeight;
	}

	public int Es() {
		return i;
	}

	public ListItem getSelected() {
		return selected;
	}

	public void Hs() {
		ylb_F = false;
		pt();
	}

	public void ht(boolean flag) {
		ylb_u = flag;
		if (flag)
			invalidate();
	}

	public int indexOf(ListItem item) {
		if (sorter != null) {
			int i1 = sorter.binarySearch(items, item);
			if (items.elementAt(i1) == item)
				return i1;
		}
		else {
			for (int j1 = 0; j1 < items.size(); j1++)
				if (items.elementAt(j1) == item)
					return j1;

		}
		return -1;
	}

	public ListItem insert(Id id, String message, int index, Color color) {
		ListItem result = insert(id.caption + ": " + message, index, color);
		putAvatarSquare(id.avatar, result, 0);
		return result;
	}

	void insert(ListItem item, int index) {
		items.insertElementAt(item, index);
		ylb_D.cv(index * itemHeight, itemHeight);
	}

	public ListItem insert(String text, int index) {
		for (int j1 = 0; j1 < items.size(); j1++) {
			ListItem item = items.elementAt(j1);
			if (item.colls[index].equals(text))
				return item;
		}

		return null;
	}

	public ListItem insert(String text, int index, Color color) {
		if (color == null)
			color = getForeColor();
		ListItem result = null;
		if (ylb_B == 0 || fontMetrics == null) {
			result = add(text);
			result.color = color;
		}
		else {
			while (text.length() > 0) {
				int j1;
				for (j1 = text.length(); (fontMetrics.stringWidth(text
						.substring(0, j1)) > ylb_B - 4 || j1 != text.length()
						&& text.charAt(j1) != ' ')
						&& j1 > 1; j1--) {
				}
				if (j1 == 1)
					j1 = text.length();
				ListItem item = ts(text.substring(0, j1), index);
				item.color = color;
				if (result == null)
					result = item;
				index++;
				if (j1 == text.length())
					break;
				text = text.substring(j1 + 1);
				if (ylb_B > 50)
					text = "  " + text;
			}
			if (size() > 256)
				remove(0);
			if (!ylb_F)
				ylb_D.jv();
		}
		return result;
	}

	void Is(int i1) {
		ylb_p = 0.0D;
		for (int j1 = 0; j1 < listColCount; j1++)
			ylb_p += proportionalColLeft[j1];

		double d = 0.0D;
		for (int k1 = 0; k1 <= listColCount; k1++) {
			ylb_n[k1] = (int) (i1 * d / ylb_p);
			if (k1 < listColCount)
				d += proportionalColLeft[k1];
		}

		ylb_A = true;
	}

	public void it(int i1, boolean flag) {
		ylb_o[i1] = flag;
		invalidate();
	}

	public int km() {
		return ylb_r;
	}

	public void Km(int i1) {
		if (fontMetrics != null)
			ylb_D.fv(i1 * fontMetrics.getHeight(), fontMetrics.getHeight());
	}

	public void kt(int i1) {
		if (ylb_s != i1) {
			ylb_s = i1;
			if (sorter != null) {
				sorter.quickSort(items);
				invalidate();
			}
		}
	}

	@Override
	public void paint(YahooGraphics graphics, int left, int top, int rigth,
			int bottom) {
		int i2 = top - left;
		graphics.setFont(listBoxFont);
		graphics.setColor(ylb_D.getBackColor());
		graphics.fillRect(left, rigth, top - left, bottom - rigth);
		if (!ylb_A)
			Is(top);
		graphics.setColor(color);
		for (int j2 = 1; j2 < listColCount; j2++) {
			int k2 = ylb_n[j2];
			int i3 = ylb_n[j2 + 1];
			graphics.drawLine(k2, rigth, k2, bottom);
			graphics.drawLine(i3, rigth, i3, bottom);
		}

		int l2 = rigth / itemHeight;
		for (int j3 = l2 * itemHeight; l2 < items.size()
				&& l2 * itemHeight < bottom; j3 += itemHeight) {
			ListItem item = items.elementAt(l2);
			Color color = item.color;
			if (color == null)
				color = getForeColor();
			if (item == selected) {
				graphics.setColor(color);
				graphics.fillRect(0, j3, i2, itemHeight);
				color = ylb_D.getBackColor();
			}
			for (int k3 = 0; k3 < listColCount; k3++) {
				int l3 = 0;
				for (int i4 = 0; i4 < item.componentTable[k3].length; i4++) {
					YahooComponent component = item.componentTable[k3][i4];
					if (component != null)
						l3 += component.getWidth1();
				}

				int j4 = (int) (i2 * proportionalColLeft[k3] / ylb_p) - l3;
				String text = YahooList.getText(
						new StringBuffer(item.colls[k3]), fontMetrics, j4);
				int k4 = ylb_o[k3] ? ylb_n[k3 + 1] - 2
						- fontMetrics.stringWidth(text) - l3 : ylb_n[k3] + 2;
				int l4 = graphics.left;
				int i5 = graphics.top;
				for (int j5 = 0; j5 < item.componentTable[k3].length; j5++) {
					YahooComponent component = item.componentTable[k3][j5];
					if (component != null) {
						graphics.left = l4 + k4;
						graphics.top = i5 + j3
								+ (itemHeight - component.getHeight1()) / 2;
						component.paint(graphics);
						k4 += component.getWidth1();
					}
				}

				graphics.left = l4;
				graphics.top = i5;
				graphics.setColor(color);
				int j5 = j3 + (itemHeight + fontMetrics.getHeight()) / 2
						- fontMetrics.getDescent();
				if (item.colors.size() == 0)
					graphics.drawString(text, k4, j5);
				else {
					int k5 = 0;
					for (int l5 = 0; l5 < item.colors.size(); l5++) {
						int i6 = item.g.getInteger(l5);
						int j6 = item.h.getInteger(l5);
						Color color1 = item.colors.elementAt(l5);
						String s2 = text.substring(k5, i6);
						graphics.drawString(s2, k4, j5);
						k4 += fontMetrics.stringWidth(s2);
						s2 = text.substring(i6, j6);
						graphics.setColor(color1);
						int k6 = fontMetrics.stringWidth(s2);
						graphics.fillRect(k4, j3, k6, itemHeight);
						graphics.setColor(getBackColor());
						graphics.drawString(s2, k4, j5);
						k4 += k6;
						k5 = j6;
					}

					graphics.setColor(color);
					graphics.drawString(text.substring(k5), k4, j5);
				}
			}

			l2++;
		}

	}

	public void pt() {
		ylb_D.jv();
	}

	public void putAvatarSquare(int avatarIndex, ListItem item, int x) {
		if (item.componentTable[x][5] != null)
			removeComponent(item, x, 5);
		Image image = avatarImages[avatarIndex];
		boolean flag = image.getHeight(null) > imageHeight;
		if (flag)
			imageHeight = image.getHeight(null);
		putComponent(new YahooImage(image), item, x, 5);
		if (flag)
			realingChilds();
	}

	public void putComponent(YahooComponent component, ListItem item, int x,
			int y) {
		if (item.componentTable[x][y] == null) {
			int index = indexOf(item);
			item.componentTable[x][y] = component;
			if (super.invalidated)
				component.realingChilds();
			update(item, index);
		}
	}

	public void putRatingSquare(Color color, ListItem item, int col) {
		if (item.componentTable[col][4] != null)
			removeComponent(item, col, 4);
		if (color != null)
			putComponent(new YahooRatingSquare(color), item, col, 4);
	}

	@Override
	public void realingChilds() {
		fontMetrics = getFontMetrics(listBoxFont);
		itemHeight = Math.max(fontMetrics.getHeight(), imageHeight);
		ylb_D.gv(itemHeight);
		ylb_D.Xu();
		ylb_D.cv(0, items.size() * itemHeight);
		for (int i1 = 0; i1 < items.size(); i1++) {
			ListItem item = items.elementAt(i1);
			for (int j1 = 0; j1 < listColCount; j1++) {
				for (int k1 = 0; k1 < item.componentTable[j1].length; k1++)
					if (item.componentTable[j1][k1] != null)
						item.componentTable[j1][k1].realingChilds();

			}

		}

		super.realingChilds();
	}

	public void remove(int index) {
		if (index == -1)
			return;
		if (items.elementAt(index) == selected)
			selected = null;
		items.remove(index);
		ylb_D.Zu(index * itemHeight, itemHeight);
	}

	public void remove(ListItem item) {
		remove(indexOf(item));
	}

	public void removeComponent(ListItem item, int x, int y) {
		YahooComponent component = item.componentTable[x][y];
		if (component != null) {
			int index = indexOf(item);
			item.componentTable[x][y] = null;
			if (super.invalidated)
				component.En();
			update(item, index);
		}
	}

	public void setCell(int col, String text) {
		cells[col] = text;
		header.invalidate();
	}

	public void setCell(String text, ListItem item, int col) {
		int index = indexOf(item);
		item.colls[col] = text;
		update(item, index);
	}

	public void setColor(Color color, ListItem _pcls155) {
		int i1 = indexOf(_pcls155);
		_pcls155.color = color;
		update(_pcls155, i1);
	}

	public void setProportionalColLeft(int col, double proportion) {
		proportionalColLeft[col] = proportion;
		ylb_A = false;
		invalidate();
	}

	public int size() {
		return items.size();
	}

	public ListItem ts(String s1, int i1) {
		return us(s1, i1, null);
	}

	public void um(Color color, int i1, int j1) {
		putRatingSquare(color, items.elementAt(i1), j1);
	}

	void update(ListItem item, int index) {
		if (sorter != null) {
			items.remove(index);
			int newIndex = sorter.binarySearch(items, item);
			if (index != newIndex)
				ylb_D.Zu(index * itemHeight, itemHeight);
			items.insertElementAt(item, newIndex);
			if (index != newIndex)
				ylb_D.cv(newIndex * itemHeight, itemHeight);
		}
		invalidate();
	}

	public void update(ListItem item, int flags, Object obj) {
		int index = indexOf(item);
		sorter.setValue(item, flags, obj);
		update(item, index);
	}

	public ListItem us(String s1, int i1, Object obj) {
		ListItem _lcls155 = new ListItem(this, obj);
		_lcls155.colls[0] = s1;
		insert(_lcls155, i1);
		return _lcls155;
	}

	public void Us(int i1, int j1) {
		Is(i1);
		ylb_B = i1;
		if (ylb_u) {
			for (int k1 = 0; k1 < items.size(); k1++) {
				ListItem _lcls155 = items.elementAt(k1);
				if (fontMetrics.stringWidth(_lcls155.colls[0]) > i1 - 4) {
					remove(k1);
					ys(_lcls155.colls[0], k1);
				}
			}

			ylb_D.jv();
		}
		invalidate();
		header.invalidate();
	}

	public void Vs(int i1) {
		ylb_F = ylb_D.av() > i1 + ylb_D.getHeight();
	}

	public void ys(String s1, int i1) {
		insert(s1, i1, getForeColor());
	}
}
