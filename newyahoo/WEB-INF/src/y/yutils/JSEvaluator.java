// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.applet.Applet;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

// Referenced classes of package y.po:
// _cls53, _cls168

public class JSEvaluator {

	JSObject	obj;

	public JSEvaluator(Applet applet) {
		try {
			obj = JSObject.getWindow(applet);
		}
		catch (JSException e) {
			obj = null;
		}
	}

	public void eval(String s) throws Throwable {
		if (obj != null)
			obj.eval(s);
	}
}
