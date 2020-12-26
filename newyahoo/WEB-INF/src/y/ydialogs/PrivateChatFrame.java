// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ydialogs;

import java.awt.Color;
import java.awt.Event;
import java.util.Date;

import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooControl;
import y.controls.YahooFrame;
import y.controls.YahooListBox;
import y.controls.YahooTextBox;
import y.yutils.AbstractYahooGamesApplet;

// Referenced classes of package y.po:
// _cls82, _cls56, _cls168, _cls35,
// _cls80, _cls79, _cls107, _cls136

public class PrivateChatFrame extends YahooFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -386578776681660035L;
	YahooListBox				chatList;
	YahooTextBox				txtMessage;
	YahooCheckBox				chkImFromFriendsOnly;
	YahooButton					btnIgnore;
	YahooButton					btnProfile;
	YahooButton					btnSend;
	YahooButton					btnClose;
	AbstractYahooGamesApplet	applet;
	String						fromIdName;

	public PrivateChatFrame(AbstractYahooGamesApplet applet, String fromIdName,
			boolean imFromFriendsOnly) {
		super(applet.lookupString(0x66500085) + fromIdName
				+ applet.lookupString(0x66500086)
				+ new Date(System.currentTimeMillis()), applet.processor);
		txtMessage = new YahooTextBox(applet.getTimerHandler(), 320);
		btnIgnore = new YahooButton(applet.lookupString(0x66500084));
		btnProfile = new YahooButton(applet.lookupString(0x66500087));
		btnSend = new YahooButton(applet.lookupString(0x66500088));
		btnClose = new YahooButton(applet.lookupString(0x66500089));
		YahooControl cointainer = new YahooControl();
		super.container
				.addChildObject(cointainer, 1, 1, 0, 0, true, 4, 4, 4, 4);
		this.applet = applet;
		this.fromIdName = fromIdName;
		chatList = new YahooListBox(6, 380, 1, -1, null, false, false, true,
				applet.avatars.image);
		chatList.ht(true);
		cointainer.addChildObject(chatList, 4, 1, 0, 0, true);
		cointainer.addChildObject(txtMessage, 4, 1, 0, 1, false);
		chkImFromFriendsOnly = new YahooCheckBox(applet
				.lookupString(0x66500083), null, imFromFriendsOnly);
		cointainer.addChildObject(chkImFromFriendsOnly, 17, 0, 2, 4, 1, 0, 2);
		cointainer.addChildObject(btnIgnore, 17, 0, 0, 1, 1, 0, 3);
		cointainer.addChildObject(btnProfile, 17, 0, 2, 1, 1, 1, 3, 0, 4, 0, 0);
		cointainer.addChildObject(btnSend, 17, 0, 0, 1, 1, 2, 3, 0, 0, 0, 4);
		cointainer.addChildObject(btnClose, 1, 1, 3, 3);
		pack();
	}

	@Override
	public boolean action(Event event, Object obj) {
		if (event.target == btnClose)
			applet.handlePrivateChatClose(fromIdName);
		else if (event.target == btnSend || event.target == txtMessage) {
			String message = txtMessage.getText();
			applet.privateChat(fromIdName, message);
			apendMessage(applet.myId, message);
			txtMessage.setText("");
		}
		else if (event.target == btnIgnore) {
			applet.ignoreName(fromIdName);
			applet.handlePrivateChatClose(fromIdName);
		}
		else if (event.target == chkImFromFriendsOnly)
			applet.setImFromFriendsOnly(chkImFromFriendsOnly.isChecked());
		else if (event.target == btnProfile)
			applet.openProfile(fromIdName);
		else
			return false;
		return true;
	}

	public void apendMessage(String message) {
		chatList.append(message);
	}

	public void apendMessage(String message, Color color) {
		chatList.append(message, color);
	}

	public void apendMessage(String name, String message) {
		chatList.append(name + ": " + message);
	}

	public void apendMessage(String name, String message, Color color) {
		chatList.append(name + ": " + message, color);
	}

	@Override
	protected void close() {
		applet.handlePrivateChatClose(fromIdName);
	}

}
