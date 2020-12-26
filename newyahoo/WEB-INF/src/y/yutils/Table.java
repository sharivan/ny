// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.util.Hashtable;
import java.util.Vector;

import y.utils.Id;

// Referenced classes of package y.po:
// _cls56, _cls49, _cls105, _cls19,
// _cls174

public class Table {

	public int							a;
	public int							b;
	public int							c;
	public int							d;
	public int							number;
	public long							time;
	public Hashtable<String, String>	properties;
	public YahooTable					yahooTable;
	public Id							sit[];
	public Vector<Id>					ids;
	public int							training;
	public int							automat;
	public String						strProperties;
	public int							privacy;
	public boolean						invited;

	public Table(AbstractYahooGamesApplet _pcls56, int number,
			Hashtable<String, String> properties, long time) {
		ids = new Vector<Id>();
		privacy = 0;
		this.number = number;
		this.properties = properties;
		automat = training = _pcls56.tableSettings.isTraining(properties);
		sit = new Id[_pcls56.tableSettings.getSitCount()];
		strProperties = _pcls56.tableSettings.toString(properties, _pcls56);
		this.time = time;
	}

}
