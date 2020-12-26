// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import y.utils.Id;
import y.yutils.YahooTableListener;

// Referenced classes of package y.po:
// _cls70, _cls49

public interface YahooGamesTableListener extends YahooTableListener {

	public abstract void handeHide();

	public abstract void handleAddId(Id id);

	public abstract void handleStand(int index);

	public abstract void handleUpdateTitle(String text);

	public abstract boolean tg();

	public abstract boolean ug();
}
