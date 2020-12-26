// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.util.Hashtable;
import java.util.Vector;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooGraphics;
import y.controls.YahooLabel;
import y.controls.YahooList;
import y.controls.YahooVector;
import y.utils.Id;
import y.utils.Square;
import y.yutils.AbstractYahooGamesApplet;
import y.yutils.Table;

// Referenced classes of package y.po:
// _cls123, _cls63, _cls115, _cls56,
// _cls49, _cls118, _cls105, _cls19,
// _cls168, _cls78, _cls79, _cls116,
// _cls87

public class TableList extends YahooList {

	static final Color			tlh_n	= new Color(0xdcdcdc);
	static final Color			tlh_o	= new Color(0x999999);
	CustomTableOptions			tlh_a;
	AbstractYahooGamesApplet	tlh_b;
	Vector<Table>				tlh_c;
	YahooLabel					tlh_d[];
	Object						e;
	int							tlh_r;
	int							tlh_s;
	int							tlh_t;
	int							tlh_u;
	Font						tlh_v;
	FontMetrics					tlh_w;
	FontMetrics					tlh_x;

	public TableList(AbstractYahooGamesApplet _pcls56, Object obj, String s1) {
		tlh_c = new Vector<Table>();
		tlh_r = -1;
		setBackColor(Color.white);
		e = obj;
		tlh_a = _pcls56.tableSettings;
		tlh_b = _pcls56;
		tlh_d = new YahooLabel[5];
		YahooControl _lcls79 = new YahooControl();
		addChildObject(_lcls79, 2, 1, 0, 0, false);
		Color color = _pcls56.getYahooColor("yahoo.games.tablelist_bg",
				0xffffcc);
		Color color1 = _pcls56.getYahooColor("yahoo.games.tablelist_fg", 0);
		_lcls79.setBackColor(color);
		_lcls79.setForeColor(color1);
		YahooLabel lblTable = new YahooLabel(_pcls56.lookupString(0x665000e1),
				1, 32);// Table
		_lcls79.addChildObject(lblTable, 1, 1, 0, 0);
		YahooLabel _lcls87_1 = new YahooLabel("", 1, 40);
		_lcls79.addChildObject(_lcls87_1, 1, 1, 1, 0);
		int i = tlh_a.getSitCount();
		int j = Math.min(5, i <= 5 ? i : i / 2);
		for (int k = 0; k < j; k++) {
			tlh_d[k] = new YahooLabel(tlh_a.sa(_pcls56, k), YahooLabel.yl_a, 78);
			_lcls79.addChildObject(tlh_d[k], 1, 1, 2 + k, 0);
		}

		YahooLabel _lcls87_2 = new YahooLabel(s1, YahooLabel.yl_b, 1);
		_lcls79.addChildObject(_lcls87_2, 1, 1, 2 + j, 0, true);
	}

	public synchronized void add(Table _pcls118) {
		int i = hx(_pcls118);
		tlh_c.insertElementAt(_pcls118, i);
		lx();
		if (tlh_r != -1 && tlh_r >= i)
			tlh_r++;
		cv(_pcls118.d, _pcls118.a);
	}

	@Override
	public synchronized void canvasMouseDown(Event event, int i, int j) {
		Square _lcls115 = ix(i, j);
		if (_lcls115 != null) {
			tlh_r = _lcls115.getY();
			tlh_s = _lcls115.getX();
			invalidate();
		}
	}

	@Override
	public void canvasMouseUp(Event event, int i, int j) {
		Event event1 = null;
		synchronized (this) {
			Square _lcls115 = ix(i, j);
			if (_lcls115 != null && _lcls115.getY() == tlh_r
					&& _lcls115.getX() == tlh_s) {
				Table _lcls118 = tlh_c.elementAt(tlh_r);
				if (_lcls118.privacy != 2 && tlh_s == -1
						|| _lcls118.privacy == 0 && _lcls118.sit[tlh_s] == null
						&& tlh_s < _lcls118.automat)
					event1 = new Event(this, 1001, new YahooVector<Object>(
							tlh_c.elementAt(tlh_r), new Integer(tlh_s)));
				else if (tlh_s != -1 && _lcls118.sit[tlh_s] != null)
					event1 = new Event(this, 701, _lcls118.sit[tlh_s].name);
			}
			tlh_r = -1;
			invalidate();
		}
		if (event1 != null)
			doEvent(event1);
	}

	@Override
	public int Ds() {
		return 10;
	}

	@Override
	public int Es() {
		return 10;
	}

	public synchronized void ex() {
		tlh_c.clear();
		tlh_r = -1;
		Xu();
	}

	void fx(YahooGraphics _pcls116, int i, Table _pcls118, int j, int k, int l) {
		if (k == -1) {
			_pcls116.setColor(tlh_n);
			_pcls116.fillRect(33, i, 39, l - 1);
			_pcls116.setColor(Color.lightGray);
			if (_pcls118.privacy != 2) {
				_pcls116.fill3DRect(33, i, 39, l - 1,
						(tlh_r == j && tlh_s == -1) ^ true);
				_pcls116.setColor(Color.black);
				if (e instanceof Image) {
					Image image = (Image) e;
					_pcls116.drawImage(image,
							32 + (40 - image.getWidth(null)) / 2, i
									+ (l - image.getHeight(null)) / 2, null);
				}
				else {
					String s1 = (String) e;
					_pcls116.drawString(s1,
							32 + (40 - tlh_w.stringWidth(s1)) / 2, i
									+ (l + tlh_w.getAscent()) / 2);
				}
			}
		}
		else {
			int i1 = k / _pcls118.c;
			int j1 = k % _pcls118.c;
			int k1 = 72 + j1 * 78;
			if (k < _pcls118.automat) {
				if (_pcls118.sit[k] != null || _pcls118.privacy != 0) {
					_pcls116.setColor(tlh_n);
					_pcls116.fillRect(k1 + 1, i + i1 * tlh_u, 77, tlh_u - 1);
					if (_pcls118.sit[k] != null) {
						byte byte0 = (byte) (_pcls118.sit[k].color == null ? 0
								: 12);
						int l1 = gx(_pcls116, i, i1, j1, YahooList.getText(
								new StringBuffer(_pcls118.sit[k].caption),
								tlh_w, 78 - byte0), byte0, tlh_r != j
								|| tlh_s != k ? Color.black : Color.red);
						if (_pcls118.sit[k].color != null) {
							_pcls116.setColor(_pcls118.sit[k].color);
							_pcls116.fillRect(l1 - 10, i + i1 * tlh_u + tlh_u
									/ 2 - 4, 8, 8);
						}
					}
				}
				else {
					_pcls116.setColor(Color.lightGray);
					_pcls116.fill3DRect(72 + j1 * 78 + 1, i + i1 * tlh_u, 77,
							tlh_u - 1, (tlh_r == j && tlh_s == k) ^ true);
					gx(_pcls116, i, i1, j1, tlh_b.lookupString(0x665000e4), 0,
							Color.black);
				}
			}
			else {
				_pcls116.setColor(tlh_n);
				_pcls116.fillRect(72 + j1 * 78, i + i1 * tlh_u, 78, tlh_u - 1);
			}
		}
	}

	int gx(YahooGraphics _pcls116, int i, int j, int k, String s1, int l,
			Color color) {
		_pcls116.setColor(color);
		int i1 = l + 32 + 40 + 78 * k + (78 - tlh_w.stringWidth(s1) - l) / 2;
		_pcls116.drawString(s1, i1, i + j * tlh_u + (tlh_u + tlh_w.getAscent())
				/ 2);
		return i1;
	}

	int hx(Table _pcls118) {
		int i = 0;
		int j = tlh_c.size();
		if (tlh_c.size() == 0)
			return 0;
		while (i < j) {
			int k = (i + j) / 2;
			Table _lcls118 = tlh_c.elementAt(k);
			int l = kx(_lcls118);
			int i1 = kx(_pcls118);
			boolean flag;
			if (l == i1)
				flag = _lcls118.number < _pcls118.number;
			else
				flag = l > i1;
			if (flag)
				i = k + 1;
			else
				j = k;
		}
		return i;
	}

	Square ix(int i, int j) {
		int k = jx(j);
		if (k == -1)
			return null;
		Table _lcls118 = tlh_c.elementAt(k);
		int l;
		if (i >= 32 && i < 72) {
			l = -1;
		}
		else {
			l = (i - 72) / 78;
			if (l >= _lcls118.c || i < 72)
				return null;
			int i1 = (j - _lcls118.d) / tlh_u;
			l += i1 * _lcls118.c;
			if (l < 0 || l >= _lcls118.automat)
				return null;
		}
		return new Square(l, k);
	}

	int jx(int i) {
		for (int j = 0; j < tlh_c.size(); j++) {
			Table _lcls118 = tlh_c.elementAt(j);
			if (i + 1 >= _lcls118.d && i <= _lcls118.d + _lcls118.a)
				return j;
		}

		return -1;
	}

	int kx(Table _pcls118) {
		return _pcls118.training <= 0 || _pcls118.privacy != 0 ? (int) (_pcls118.privacy == 2 ? 0
				: 1)
				: 2;
	}

	void lx() {
		int i = 1;
		for (int j = 0; j < tlh_c.size(); j++) {
			Table _lcls118 = tlh_c.elementAt(j);
			_lcls118.d = i;
			_lcls118.c = _lcls118.automat <= 5 ? _lcls118.automat
					: (_lcls118.automat + 1) / 2;
			_lcls118.b = _lcls118.automat != 0 ? (_lcls118.automat + _lcls118.c - 1)
					/ _lcls118.c
					: 2;
			_lcls118.a = tlh_u * _lcls118.b
					+ (_lcls118.strProperties != null ? tlh_t : 0) + 1;
			i += _lcls118.a;
		}

	}

	void mx(Table _pcls118) {
		notifyClose(_pcls118);
		add(_pcls118);
	}

	public synchronized void notifyChangeProperties(Table _pcls118,
			Hashtable<String, String> hashtable) {
		_pcls118.properties = hashtable;
		_pcls118.strProperties = tlh_a.toString(hashtable, tlh_b);
		remove(_pcls118);
	}

	public synchronized void notifyClose(Table _pcls118) {
		int i = tlh_c.indexOf(_pcls118);
		tlh_c.remove(i);
		lx();
		Zu(_pcls118.d, _pcls118.a);
		if (tlh_r != -1)
			if (tlh_r == i)
				tlh_r = -1;
			else if (tlh_r > i)
				tlh_r--;
	}

	public synchronized void notifySit(Table _pcls118, int i, Id _pcls49) {
		_pcls118.sit[i] = _pcls49;
		boolean flag;
		if (_pcls118.sit[i] == null) {
			_pcls118.training++;
			flag = _pcls118.training == 1;
		}
		else {
			_pcls118.training--;
			flag = _pcls118.training == 0;
		}
		if (flag)
			mx(_pcls118);
		else
			remove(_pcls118);
	}

	@Override
	public synchronized void paint(YahooGraphics graphics, int i, int j, int k,
			int l) {
		int i1 = jx(k);
		graphics.setColor(getBackColor());
		graphics.fillRect(i, k, j - i, l - k);
		if (i1 != -1)
			for (; i1 < tlh_c.size(); i1++) {
				Table _lcls118 = tlh_c.elementAt(i1);
				int j1 = _lcls118.d;
				if (j1 >= l)
					break;
				int k1 = _lcls118.b * tlh_u;
				graphics.setColor(Color.black);
				graphics.drawLine(0, j1 - 1, j - 1, j1 - 1);
				graphics.drawLine(0, j1, 0, j1 + _lcls118.a - 2);
				graphics.drawLine(j - 1, j1, j - 1, j1 + _lcls118.a - 2);
				graphics.drawLine(0, j1 + _lcls118.a - 2, j - 1, j1
						+ _lcls118.a - 2);
				graphics.drawLine(32, j1, 32, j1 + _lcls118.a - 2);
				graphics.setColor(tlh_n);
				graphics.fillRect(1, j1, 31, _lcls118.a - 2);
				graphics.setColor(Color.black);
				String s1 = "#" + Integer.toString(_lcls118.number);
				graphics.drawString(s1, (32 - tlh_w.stringWidth(s1)) / 2, j1
						+ (_lcls118.a - 1 + tlh_w.getAscent()) / 2);
				for (int l1 = 0; l1 < _lcls118.automat; l1++)
					fx(graphics, j1, _lcls118, i1, l1, k1);

				fx(graphics, j1, _lcls118, i1, -1, k1);
				graphics.setColor(tlh_n);
				int i2 = 72 + _lcls118.c * 78 + 1;
				int j2 = j - i2 - 1;
				graphics.fillRect(i2, j1, j2, k1 - 1);
				StringBuffer astringbuffer[] = new StringBuffer[_lcls118.b];
				for (int k2 = 0; k2 < _lcls118.b; k2++)
					astringbuffer[k2] = new StringBuffer();

				int l2 = 0;
				for (int i3 = 0; i3 < _lcls118.ids.size(); i3++) {
					Id _lcls49 = _lcls118.ids.elementAt(i3);
					int k3;
					for (k3 = 0; k3 < _lcls118.sit.length; k3++)
						if (_lcls118.sit[k3] != null
								&& _lcls49 == _lcls118.sit[k3])
							break;

					if (k3 == _lcls118.sit.length) {
						if (l2 > 0)
							astringbuffer[(l2 - 1) % _lcls118.b].append(",");
						astringbuffer[l2 % _lcls118.b].append(_lcls49.caption);
						l2++;
					}
				}

				graphics.setColor(Color.black);
				for (int j3 = 0; j3 < _lcls118.b; j3++) {
					String s2 = YahooList.getText(astringbuffer[j3], tlh_w, j2);
					graphics.drawString(s2, 72 + _lcls118.c * 78 + 2, j1 + j3
							* tlh_u + (tlh_u + tlh_w.getAscent()) / 2);
				}

				if (_lcls118.strProperties != null) {
					graphics.setColor(tlh_o);
					graphics.drawLine(33, j1 + k1 - 1, j - 2, j1 + k1 - 1);
					graphics.setColor(tlh_n);
					graphics.fillRect(33, j1 + k1, j - 34, tlh_t - 1);
					graphics.setColor(tlh_o);
					graphics.drawLine(33, j1 + k1 - 1, j - 2, j1 + k1 - 1);
					graphics.setFont(tlh_v);
					graphics.setColor(Color.black);
					graphics.drawString(tlh_b.lookupString(0x665000e3)
							+ _lcls118.strProperties, 36, j1 + k1
							+ (tlh_t + tlh_x.getAscent()) / 2);
					graphics.setFont(YahooComponent.defaultFont);
				}
				graphics.setColor(tlh_o);
				for (int l3 = 0; l3 < _lcls118.c; l3++)
					if (l3 < _lcls118.automat)
						graphics.drawLine(72 + l3 * 78, j1, 72 + l3 * 78, j1
								+ k1 - 2);

				graphics.drawLine(72 + _lcls118.c * 78, j1,
						72 + _lcls118.c * 78, j1 + k1 - 2);
				for (int i4 = 1; i4 < _lcls118.b; i4++)
					graphics.drawLine(73, j1 + tlh_u * i4 - 1, 78 * _lcls118.c
							+ 32 + 40 - 1, j1 + tlh_u * i4 - 1);

			}

	}

	public synchronized void qx(Table _pcls118, int i) {
		_pcls118.privacy = i;
		mx(_pcls118);
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		Xu();
		tlh_w = getFontMetrics(YahooComponent.defaultFont);
		tlh_v = new Font(YahooComponent.defaultFont.getName(), 0, 8);
		tlh_x = getFontMetrics(tlh_v);
		tlh_t = tlh_x.getHeight() + 2;
		tlh_u = 4 + tlh_w.getHeight();
		gv(tlh_u);
		lx();
		if (tlh_c.size() > 0) {
			Table _lcls118 = tlh_c.lastElement();
			cv(0, _lcls118.d + _lcls118.a);
		}
	}

	public void remove(Table table) {
		fv(table.d, table.a);
	}

	public synchronized void rx(Id _pcls49, Color color) {
		_pcls49.color = color;
		for (int i = 0; i < _pcls49.tables.size(); i++) {
			Table _lcls118 = _pcls49.tables.elementAt(i);
			for (Id element : _lcls118.sit)
				if (element == _pcls49)
					remove(_lcls118);

		}

	}

}
