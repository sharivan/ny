// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.dialogs;

import java.awt.Color;
import java.awt.Event;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooGraphics;
import y.controls.YahooLabel;

// Referenced classes of package y.po:
// _cls133, _cls78, _cls79, _cls116,
// _cls87

public class YahooDialog extends YahooCustomDialog {

	YahooControl	dlgContainer;
	YahooLabel		lblText;

	public YahooDialog(YahooControl _pcls79) {
		this(_pcls79, "");
	}

	public YahooDialog(YahooControl control, String text) {
		super(control);
		dlgContainer = new YahooControl();
		super.container = control;
		super.addChildObject(lblText = new YahooLabel(text), 10, 1, 0, 1, 1, 0,
				0, 4, 4, 0, 4, false, false);
		lblText.setBackColor(YahooComponent.defaultColor);
		lblText.Oo(0, 1, 12);
		lblText.setForeColor(Color.white);
		super.addChildObject(dlgContainer, 10, 0, 1, 1, 1, 0, 1, 4, 4, 4, 4,
				false, false);
		nc(Color.black, Color.lightGray, Color.lightGray, Color.black,
				Color.white, Color.darkGray);
	}

	@Override
	public void addChildObject(YahooComponent _pcls78, int j, int i1, int j1,
			int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3,
			boolean flag, boolean flag1) {
		dlgContainer.addChildObject(_pcls78, j, i1, j1, k1, l1, i2, j2, k2, l2,
				i3, j3, flag, flag1);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int i1) {
		if (i1 >= getHeight() - 4 || i1 < 4 || j < 4 || j >= getWidth() - 4
				|| event.target == lblText) {
			super.ycd_d = true;
			super.ycd_b = j;
			super.ycd_c = i1;
		}
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int j, int i1) {
		if (super.ycd_d) {
			int j1 = getWorldLeft(super.container) + j - super.ycd_b;
			int k1 = getWorldTop(super.container) + i1 - super.ycd_c;
			if (j1 + getWidth() < 4)
				j1 = -getWidth() + 4;
			if (k1 + getHeight() < 4)
				k1 = -getHeight() + 4;
			if (j1 + 4 >= super.container.getWidth())
				j1 = super.container.getWidth() - 4;
			if (k1 + 4 >= super.container.getHeight())
				k1 = super.container.getHeight() - 4;
			setCoords(j1, k1);
			super.e = true;
			super.container.wb();
		}
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int j, int i1) {
		super.ycd_d = false;
		return true;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		yahooGraphics.eu(0, 0, super.width, super.height, false);
	}
}
