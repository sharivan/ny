// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;

// Referenced classes of package y.po:
// _cls123, _cls90, _cls107, _cls158,
// _cls116

public class _cls44 extends YahooList {

	YahooListBox	listBox;

	public _cls44(YahooListBox listBox, boolean flag) {
		super(flag);
		this.listBox = listBox;
	}

	@Override
	public void canvasMouseDown(Event event, int i, int j) {
		listBox.canvasMouseDown(event, i, j);
	}

	@Override
	public void canvasMouseUp(Event event, int i, int j) {
		listBox.canvasMouseUp(event, i, j);
	}

	@Override
	public int Ds() {
		return listBox.Ds();
	}

	@Override
	public int Es() {
		return listBox.Es();
	}

	@Override
	public void paint(YahooGraphics graphics, int left, int top, int rigth,
			int bottom) {
		listBox.paint(graphics, left, top, rigth, bottom);
	}

	@Override
	public void Us(int i, int j) {
		listBox.Us(i, j);
	}

	@Override
	public void Vs(int i) {
		listBox.Vs(i);
	}
}
