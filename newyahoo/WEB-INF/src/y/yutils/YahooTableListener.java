// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.awt.Event;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

import common.io.YData;

// Referenced classes of package y.po:
// _cls111

public interface YahooTableListener {

	public abstract void handleClose();

	public abstract void handleCreateFrame();

	public abstract boolean handleEvent(Event event);

	public abstract void handleIterate();

	public abstract boolean handleParseData(byte byte0,
			DataInputStream datainputstream) throws IOException;

	public abstract void handleSetProperties(
			Hashtable<String, String> properties);

	public abstract void handleStart();

	public abstract void handleStop(YData data);

	public abstract void im();

	public abstract void nm(int i);
}
