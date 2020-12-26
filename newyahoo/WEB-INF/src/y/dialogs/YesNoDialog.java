// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.dialogs;

import java.awt.Event;

import y.controls.YahooButton;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.utils.StringLookuper;

// Referenced classes of package y.po:
// _cls27, _cls150, _cls14, _cls35,
// _cls133, _cls78, _cls79, _cls87

public class YesNoDialog extends YahooDialog {

	YahooButton			btnYes;
	YahooButton			btnNo;
	YesNoDialogHandler	handler;
	YahooLabel			label;
	public Object		ynd_e;

	public YesNoDialog(String s1, String s2, YahooControl _pcls79, String s3,
			YesNoDialogHandler _pcls150, Object obj) {
		super(_pcls79);
		handler = _pcls150;
		ynd_e = obj;
		label = new YahooLabel(s3);
		addChildObject(label, 10, 2, 0, 1, 1, 0, 0, 16, 0, 0, 0);
		YahooControl _lcls79 = new YahooControl(1);
		addChildObject(_lcls79, 10, 0, 0, 1, 1, 0, 1, 0, 0, 16, 0);
		btnYes = new YahooButton(s1);
		_lcls79.addChildObject(btnYes, 0, 0, 2);
		btnNo = new YahooButton(s2);
		_lcls79.addChildObject(btnNo, 1, 0, 2);
		show();
	}

	public YesNoDialog(StringLookuper _pcls14, YahooControl _pcls79, String s1,
			YesNoDialogHandler _pcls150) {
		this(_pcls14, _pcls79, s1, _pcls150, null);
	}

	public YesNoDialog(StringLookuper _pcls14, YahooControl _pcls79, String s1,
			YesNoDialogHandler _pcls150, Object obj) {
		this(_pcls14.lookupString(0x66500041),
				_pcls14.lookupString(0x66500042), _pcls79, s1, _pcls150, obj);
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (event.target == btnYes)
			handler.handleYes(this);
		else if (event.target == btnNo)
			handler.handleNo(this);
		else
			return super.eventActionEvent(event, obj);
		close();
		return true;
	}
}
