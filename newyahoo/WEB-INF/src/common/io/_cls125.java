// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.io;

import java.io.IOException;

import server.io.YahooConnectionId;

// Referenced classes of package y.po:
// _cls176

public interface _cls125 {

	public abstract void close();

	public abstract boolean isValidCommand(YahooConnectionId _pcls176,
			boolean flag) throws IOException;

	public abstract void processMessages() throws IOException;
}
