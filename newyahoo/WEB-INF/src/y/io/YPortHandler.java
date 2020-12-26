// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.io;

import java.io.IOException;

// Referenced classes of package y.po:
// _cls176

public interface YPortHandler {

	public abstract void close();

	public abstract void exit() throws IOException;

	public abstract boolean isValidCommand(YPort _pcls176) throws IOException;

	public abstract void openYPort(String s) throws IOException;

	public abstract void processMessages() throws IOException;
}
