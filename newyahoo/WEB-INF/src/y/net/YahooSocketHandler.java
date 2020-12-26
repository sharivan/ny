// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.net;

import java.io.DataInputStream;

import y.io.YPort;

// Referenced classes of package y.po:
// _cls176

public interface YahooSocketHandler {
	public YahooConnectionHandler getHandler();

	public abstract void handleClose(YPort _pcls176);

	public abstract void handleFail(String s);

	public abstract void handleOpen(YPort _pcls176);

	public abstract void processMessage(YPort _pcls176,
			DataInputStream datainputstream, int i);
}
