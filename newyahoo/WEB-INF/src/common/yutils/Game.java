// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.yutils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111, _cls40, _cls12

public abstract class Game implements YData {

	public static boolean canForceForfeit(Hashtable<String, String> hashtable) {
		return hashtable.get("ff") == null;
	}

	public static int getInRange(String key,
			Hashtable<String, String> hashtable, int low, int high,
			int defaultValue) {
		int l = defaultValue;
		String s1 = hashtable.get(key);
		if (s1 != null)
			try {
				l = Integer.parseInt(s1);
			}
			catch (NumberFormatException _ex) {
			}
		else
			return defaultValue;
		return l >= low ? l <= high ? l : high : low;
	}

	public static boolean haveTimePerMove(Hashtable<String, String> hashtable) {
		return hashtable.get("pl") == null;
	}

	public static boolean isRated(Hashtable<String, String> hashtable) {
		return hashtable.get("rd") != null;
	}

	protected boolean	sitState[];

	protected int		sitStarteds;

	public boolean		running;

	GameHandler			handler;

	public Game() {
		sitStarteds = 0;
		running = false;
	}

	public boolean allSitsStarted() {
		return sitStarteds == sitState.length && !running;
	}

	public void close() {
		sitState = null;
		handler = null;
	}

	public abstract void configDoStart();

	public final void createSits(int i) {
		sitState = new boolean[i];
	}

	public abstract boolean doResign(int turn);

	public abstract boolean doStop();

	protected void doStop(YData _pcls111) {
		running = false;
		handler.handleStop(_pcls111);
		doUpdateStatus();
	}

	public final void doUpdateStatus() {
		handler.handleUpdateStatus(allSitsStarted());
	}

	public boolean hasPartner() {
		return false;
	}

	public final void initialize(Hashtable<String, String> hashtable,
			GameHandler _handler, int i) {
		handler = _handler;
		createSits(i);
		initializeProperties(hashtable, _handler);
	}

	public abstract void initializeProperties(
			Hashtable<String, String> hashtable, GameHandler handler);

	public boolean isCurrentTurn(int turn) {
		return false;
	}

	public final boolean isRunning() {
		return running;
	}

	public abstract void read(DataInput input) throws IOException;

	public void refresh() {
		doUpdateStatus();
		if (running)
			handler.handleStart();
	}

	public final boolean setRunning(boolean value) {
		if (value && !running || allSitsStarted()) {
			running = true;
			handler.handleStart();
			configDoStart();
			doUpdateStatus();
			return true;
		}
		return false;
	}

	public void setSitState(int i, boolean flag) {
		if (sitState[i] != flag) {
			sitState[i] = flag;
			sitStarteds += flag ? 1 : -1;
			doUpdateStatus();
		}
	}

	public abstract void setup();

	public boolean stop() {
		return doStop();
	}

	public abstract void write(DataOutput output) throws IOException;

}
