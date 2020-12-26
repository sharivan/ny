// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooFrame;
import y.controls.YahooLabel;
import y.controls.YahooTextBox;
import y.utils.Formater;
import y.utils.Id;
import y.ycontrols.AvatarImage;
import y.ycontrols.YahooAdmPannel;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls82, _cls145, _cls8, _cls52,
// _cls56, _cls49, _cls168, _cls35,
// _cls80, _cls78, _cls79, _cls87,
// _cls136

public class InformationFrame extends YahooFrame {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 7149772906527383665L;
	public YahooButton				btnPing;
	public YahooLabel				lblPing;
	public YahooLabel				lblProfile;
	public YahooButton				btnProfile;
	public YahooButton				btnWatch;
	public YahooCheckBox			chkIgnore;
	public YahooButton				btnSend;
	public YahooTextBox				txtMessage;
	public YahooLabel				lblIdle;
	public String					idName;
	public AbstractYahooGamesApplet	applet;
	public boolean					waitingPingResponse;
	public long						pingStartTime;
	public long						pingDispatchTime;
	public AvatarImage				avatarImage;

	public InformationFrame(AbstractYahooGamesApplet applet, Id id,
			boolean ignored, String profile) {
		super(applet.lookupString(0x665000ca) + id.caption, true,
				applet.processor);
		txtMessage = new YahooTextBox(applet.getTimerHandler(), 240);
		lblIdle = new YahooLabel();
		btnPing = new YahooButton(applet.lookupString(0x665000c9));
		lblPing = new YahooLabel("\n");
		btnProfile = new YahooButton(applet.lookupString(0x6650016d));
		if (!id.name.equals(applet.myId))
			btnWatch = new YahooButton(applet.lookupString(0x66500d44));
		chkIgnore = new YahooCheckBox(applet.lookupString(0x6650016c));
		btnSend = new YahooButton(applet.lookupString(0x6650016e));
		idName = id.name;
		this.applet = applet;
		YahooControl _lcls79 = new YahooControl();
		_lcls79.addChildObject(new YahooLabel(applet.lookupString(0x665000ca)
				+ id.caption + ":"), 2, 1, 0, 0);
		lblProfile = new YahooLabel(profile);
		_lcls79.addChildObject(lblProfile, 2, 1, 0, 1);
		YahooControl _lcls79_1 = new YahooControl();
		super.container.addChildObject(_lcls79_1, 2, 1, 0, 4, true);
		_lcls79_1.addChildObject(
				new YahooLabel(applet.lookupString(0x665000cf)), 17, 0, 0, 1,
				1, 0, 0);
		_lcls79_1.addChildObject(lblIdle, 17, 2, 2, 1, 1, 1, 0);
		YahooControl _lcls79_2 = new YahooControl();
		super.container.addChildObject(_lcls79_2, 2, 1, 0, 5, true);
		_lcls79_2.addChildObject(btnPing, 1, 1, 0, 0);
		_lcls79_2.addChildObject(lblPing, 1, 1, 1, 0, true);
		super.container.addChildObject(new YahooLabel(applet
				.lookupString(0x665000cb)), 2, 1, 0, 2, false);
		super.container.addChildObject(txtMessage, 10, 2, 2, 1, 1, 0, 3);
		super.container.addChildObject(btnSend, 1, 1, 1, 3);
		YahooControl _lcls79_3 = new YahooControl(1);
		super.container.addChildObject(_lcls79_3, 1, 1, 0, 6);
		chkIgnore.setChecked(ignored);
		_lcls79_3.addChildObject(chkIgnore, 0, 0, 2);
		_lcls79_3.addChildObject(btnProfile, 1, 0, 2);
		if (btnWatch != null)
			_lcls79_3.addChildObject(btnWatch, 2, 0, 2);
		YahooButton btnClose = new YahooButton(applet.lookupString(0x6650016f));
		setDefaultAction(btnClose);
		_lcls79_3.addChildObject(btnClose, btnWatch != null ? 3 : 2, 0, 2);
		if (applet.currId != null && applet.currId.adminFlags == 1) {
			YahooComponent _lcls78 = new YahooComponent(1, 1);
			_lcls78.setBackColor(Color.darkGray);
			super.container.addChildObject(_lcls78, 1, 7, 2, 0, true, 8, 8, 8,
					8);
			YahooAdmPannel _lcls8 = new YahooAdmPannel(applet, this);
			super.container.addChildObject(_lcls8, 1, 7, 3, 0, false, 0, 8, 0,
					8);
		}
		if (applet.avatar_host != null) {
			URL url = null;
			try {
				url = new URL("http://" + applet.getParameter("host") + "/yai"
						+ id.caption + "_180x180.gif");
			}
			catch (MalformedURLException _ex) {
			}
			YahooControl _lcls79_4 = new YahooControl(101, 181);
			Image image = applet.getImage(url);
			avatarImage = new AvatarImage(image, applet);
			_lcls79_4.addChildObject(avatarImage, 1, 1, 0, 0, true);
			YahooControl _lcls79_5 = new YahooControl();
			_lcls79_5.addChildObject(_lcls79_4, 17, 3, 3, 1, 1, 0, 0, 0, 0, 0,
					20);
			_lcls79_5
					.addChildObject(_lcls79, 13, 3, 3, 1, 1, 1, 0, 0, 0, 0, 30);
			super.container.addChildObject(_lcls79_5, 2, 1, 0, 1);
		}
		else {
			super.container.addChildObject(_lcls79, 2, 1, 0, 1);
		}
		pack();
		setVisible(true);
	}

	@Override
	public boolean action(Event event, Object obj) {
		if (event.target == btnProfile) {
			applet.openProfile(idName);
			applet.closeInformationFrame(idName);
		}
		else if (event.target == chkIgnore) {
			if (chkIgnore.isChecked())
				applet.ignoreName(idName);
			else
				applet.noIgnoreName(idName);
		}
		else if (event.target == btnSend || event.target == txtMessage) {
			applet.privateChat(idName, txtMessage.getText());
			txtMessage.setText("");
			applet.closeInformationFrame(idName);
		}
		else if (event.target == btnPing)
			applet.sendPing(this);
		else if (event.target == btnWatch) {
			applet.watch(idName);
			applet.closeInformationFrame(idName);
		}
		else {
			return false;
		}
		return true;
	}

	@Override
	protected void close() {
		applet.closeInformationFrame(idName);
	}

	public void setIdle(int value) {
		int j1 = value % 60000;
		value /= 60000;
		lblIdle.setCaption((value <= 0 ? "" : value
				+ (value <= 1 ? applet.lookupString(0x66500e0c) : applet
						.lookupString(0x665000ce)))
				+ Formater.formatFloat(j1, 1000)
				+ applet.lookupString(0x665000cc));
	}
}
