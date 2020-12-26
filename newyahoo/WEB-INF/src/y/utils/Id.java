// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.utils;

import java.awt.Color;
import java.util.Vector;

import y.controls.ListItem;
import y.yutils.Table;

import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls105, _cls102, _cls155

public class Id {

	public static int getAdminFlags(int flags) {
		if ((flags & 4) != 0 || (flags & 2) != 0)
			return 1;
		return (flags & 8) == 0 ? 0 : -1;
	}

	public String			name;
	public int				publicFlags;
	public int				adminFlags;
	public String			caption;
	public int				rating;
	public String			str;
	public Color			color;
	public ListItem			idListItem;
	public ListItem			inviteListItem;
	public Vector<Table>	tables;

	public boolean			hasSentInviation;
	public int				avatar;

	public Id(String s, String s1) {
		publicFlags = 0;
		rating = YahooUtils.provisional;
		str = "";
		color = null;
		tables = new Vector<Table>();
		name = s;
		caption = s1;
	}

	public void setPublicFlags(int value) {
		publicFlags = value;
		adminFlags = getAdminFlags(value);
	}
}
