// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Event;
import java.io.DataInputStream;
import java.util.Hashtable;

import y.controls.YahooFrame;
import y.utils.Id;
import y.yutils.YahooGamesTable;

import common.io.YData;

// Referenced classes of package y.po:
// _cls56, _cls174, _cls99, _cls51,
// _cls49, _cls59, _cls168, _cls79,
// _cls82, _cls111

public class TableTitle implements YahooGamesTableListener {

	YahooGamesTable	table;
	YahooTableFrame	frame;

	public TableTitle(YahooGamesTable _pcls99) {
		table = _pcls99;
	}

	public void handeHide() {
		if (frame != null)
			frame.setVisible(false);
	}

	public void handleAddId(Id id) {
		if (table != null && id.name.equals(table.getMyId()))
			setTitle(null);
	}

	public void handleClose() {
		if (frame != null) {
			try {
				Thread.sleep(100L);
			}
			catch (InterruptedException _ex) {
			}
			frame.setVisible(false);
			try {
				Thread.sleep(100L);
			}
			catch (InterruptedException _ex) {
			}
			frame.dispose();
			frame = null;
		}
		table = null;
	}

	public void handleCreateFrame() {
		frame = new YahooTableFrame(table);
		((YahooFrame) frame).container.addChildObject(table
				.getTableControlContainer(), 1, 1, 0, 0, true);
		frame.setDefaultAction(table.Pp());
		frame.pack();
		frame.setVisible(true);
	}

	public boolean handleEvent(Event event) {
		return false;
	}

	public void handleIterate() {
	}

	public boolean handleParseData(byte byte0, DataInputStream datainputstream) {
		return false;
	}

	public void handleSetProperties(Hashtable<String, String> properties) {
	}

	public void handleStand(int index) {
	}

	public void handleStart() {
	}

	public void handleStop(YData data) {
	}

	public void handleUpdateTitle(String text) {
		if (table == null)
			return;
		Id id = table.getId(text);
		setTitle(id.caption);
	}

	public void im() {
	}

	public void nm(int i) {
	}

	void setTitle(String s) {
		if (frame != null)
			frame.setTitle(table.getApplet().lookupString(0x665000b0)
					+ table.getApplet().getPageTitle()
					+ table.getApplet().lookupString(0x6650008d)
					+ table.getLabel()
					+ table.getApplet().lookupString(0x665000ae)
					+ table.getNumber()
					+ (s != null ? table.getApplet().lookupString(0x665000a6)
							+ s : ""));
	}

	public boolean tg() {
		return true;
	}

	public boolean ug() {
		return true;
	}
}
