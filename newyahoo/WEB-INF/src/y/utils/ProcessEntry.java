// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import y.controls.ProcessHandler;

// Referenced classes of package y.po:
// _cls81, _cls66, _cls65

public class ProcessEntry {

	public static ObjectPool	pool	= new ObjectPool(new ProcessEntry(), 8);

	public static ProcessEntry getInstance(ProcessHandler handler, int code,
			Object obj) {
		ProcessEntry entry = (ProcessEntry) pool.aqcuire();
		entry.handler = handler;
		entry.code = code;
		entry.obj = obj;
		return entry;
	}

	public ProcessHandler	handler;
	public int				code;

	public Object			obj;

	public ProcessEntry() {
	}

}
