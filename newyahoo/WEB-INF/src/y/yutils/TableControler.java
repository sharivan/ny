// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.awt.Event;
import java.io.DataInputStream;
import java.util.Hashtable;

import y.utils.Id;
import y.ycontrols.YahooGamesTableListener;

import common.io.YData;

// Referenced classes of package y.po:
// _cls74, _cls99, _cls51, _cls111,
// _cls49

public class TableControler implements YahooGamesTableListener {

	YahooGamesTable	table;

	public TableControler(YahooGamesTable table) {
		this.table = table;
	}

	public void handeHide() {
		table.getYahooGamesApplet().addComponent(null);
	}

	public void handleAddId(Id id) {
	}

	public void handleClose() {
		table = null;
	}

	public void handleCreateFrame() {
		table.getYahooGamesApplet().addComponent(
				table.getTableControlContainer());
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
	}

	public void im() {
	}

	public void nm(int i) {
	}

	public boolean tg() {
		return true;
	}

	public boolean ug() {
		return true;
	}
}
