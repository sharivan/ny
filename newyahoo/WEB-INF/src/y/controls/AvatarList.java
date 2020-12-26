// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.util.Vector;

// Referenced classes of package y.po:
// _cls79, _cls166, _cls78, _cls126

public class AvatarList extends YahooControl {

	Vector<_cls166>			items;
	Vector<YahooComponent>	cm_b;
	_cls126					cm_c;
	int						cm_d;

	public AvatarList(int i) {
		super(0);
		items = new Vector<_cls166>();
		cm_b = new Vector<YahooComponent>();
		cm_d = -1;
		cm_c = new _cls126(i);
		YahooComponent _lcls78 = new YahooComponent(1, 1);
		YahooComponent _lcls78_1 = new YahooComponent(1, 1);
		YahooComponent _lcls78_2 = new YahooComponent(1, 1);
		_lcls78.setBackColor(Color.black);
		_lcls78_1.setBackColor(Color.black);
		_lcls78_2.setBackColor(Color.black);
		addChildObject(_lcls78, 2, 1, 0, 0, false);
		addChildObject(_lcls78_1, 1, 1, 0, 1, false);
		addChildObject(_lcls78_2, 2, 1, 0, 2, false);
		addChildObject(cm_c, 1, 1, 1, 1, false);
	}

	public void add(YahooComponent _pcls78) {
		cm_b.add(_pcls78);
		_cls166 _lcls166 = new _cls166(this, _pcls78);
		_lcls166.Uy(getForeColor(), getBackColor(), in(), jn(), kn(), ln());
		items.add(_lcls166);
		cm_c.ew(_lcls166);
		if (cm_d == -1)
			setCurrentAvatar(0);
	}

	public int Ku() {
		return cm_d;
	}

	public void Lu() {
		for (; cm_b.size() > 0; Nu(0)) {
		}

	}

	public void Nu(int i) {
		if (cm_d == i)
			setCurrentAvatar(-1);
		if (i < cm_d)
			cm_d--;
		cm_c.jw(i);
		items.remove(i);
		cm_b.remove(i);
	}

	public void Pu(_cls166 _pcls166) {
		setCurrentAvatar(items.indexOf(_pcls166));
	}

	public void setCurrentAvatar(int i) {
		for (int j = 0; j < items.size(); j++)
			items.elementAt(j).Ty(j == i);

		cm_d = i;
	}

	public int size() {
		return items.size();
	}
}
