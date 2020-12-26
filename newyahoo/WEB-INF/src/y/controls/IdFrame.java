// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;

import y.utils.Id;
import y.utils.Processor;
import y.utils.StringLookuper;

// Referenced classes of package y.po:
// _cls82, _cls74, _cls49, _cls168,
// _cls35, _cls79, _cls87, _cls107,
// _cls155, _cls136

public class IdFrame extends YahooFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7614635173037319529L;
	int							table;
	public YahooListBox			if_c;
	YahooTextBox				d;
	YahooButton					e;
	boolean						f;
	boolean						g;
	private IdFrameHandler		handler;

	public IdFrame(IdFrameHandler handler, Processor _pcls173,
			StringLookuper lookuper, YahooListBox _pcls107, String s,
			boolean flag) {
		super(s, flag ^ true, _pcls173);
		d = new YahooTextBox(_pcls173.timerHandler, 100);
		g = flag;
		if_c = _pcls107;
		this.handler = handler;
		super.container.addChildObject(new YahooLabel(lookuper
				.lookupString(0x665000bf)), 1, 1, 0, 0);// Enter
		// Name:
		super.container.addChildObject(d, 1, 1, 1, 0);
		super.container.addChildObject(new YahooLabel(lookuper
				.lookupString(0x665000c1)), 2, 1, 0, 1);// - or
		// -
		super.container.addChildObject(new YahooLabel(lookuper
				.lookupString(0x665000c0)), 2, 1, 0, 2);// Select
		// From
		// List
		super.container.addChildObject(_pcls107, 2, 1, 0, 3, true);
		YahooControl _lcls79 = new YahooControl(1);
		super.container.addChildObject(_lcls79, 2, 1, 0, 4);
		_lcls79.addChildObject(e = new YahooButton(s), 0, 0, 2);
		YahooButton _lcls35 = new YahooButton(lookuper.lookupString(0x66500168));// Cancel
		setDefaultAction(_lcls35);
		_lcls79.addChildObject(_lcls35, 1, 0, 2);
		pack();
	}

	@Override
	public boolean action(Event event, Object obj) {
		if (event.target == e) {
			String s = d.getText().trim();
			handler.handleIdClick(s, table);
			setVisible(false);
			if (!g)
				dispose();
			return true;
		}
		return false;
	}

	@Override
	public boolean handleEvent(Event event) {
		if (event.target == if_c && event.id == 701) {
			ListItem _lcls155 = if_c.getSelected();
			if (_lcls155 != null) {
				Id _lcls49 = (Id) _lcls155.obj;
				d.setText(_lcls49.caption);
			}
			return true;
		}
		return super.handleEvent(event);
	}

	public void open(int i) {
		table = i;
		setVisible(true);
	}
}
