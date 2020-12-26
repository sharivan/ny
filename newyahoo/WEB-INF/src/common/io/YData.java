// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface YData {

	public abstract void read(DataInput input) throws IOException;

	public abstract void write(DataOutput output) throws IOException;
}
