// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import y.controls.AvatarList;
import y.controls.ListItem;
import y.controls.ProcessHandler;
import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooListBox;
import y.controls.YahooTextBox;
import y.dialogs.OkDialog;
import y.dialogs.OkFrame;
import y.dialogs.YahooDialog;
import y.exceptions.AuthenticationFailed;
import y.exceptions._cls128;
import y.net.YahooConnectionHandler;
import y.net.YahooConnectionThread;
import y.utils.Formater;
import y.utils.Id;
import y.utils.ImageReader;
import y.utils.TimerHandler;
import y.utils.YahooImage;
import y.utils.YahooImageList;
import y.ycontrols.AvatarImageList;
import y.ycontrols.CustomTableOptions;
import y.ycontrols.UrlProcessEntry;
import y.ycontrols._cls18;
import y.ydialogs.InformationFrame;
import y.ydialogs.OptionsDialog;
import y.ydialogs.PrivateChatFrame;

import common.yutils.YahooUtils;
import common.yutils._cls159;

// Referenced classes of package y.po:
// _cls168, _cls159, _cls122, _cls37,
// _cls55, _cls145, _cls149, _cls86,
// _cls50, _cls4, _cls134, _cls174,
// _cls22, _cls152, _cls11, _cls49,
// _cls36, _cls118, _cls9, _cls105,
// _cls19, _cls128, _cls18, _cls102,
// _cls93, _cls47, _cls35, _cls133,
// _cls80, _cls78, _cls79, _cls82,
// _cls13, _cls87, _cls107, _cls155,
// _cls119, _cls136, _cls173, _cls27

public abstract class AbstractYahooGamesApplet extends AbstractYahooApplet
		implements TimerHandler, _cls18, YahooConnectionHandler {

	/**
	 * 
	 */
	private static final long	serialVersionUID		= -1139851215959500872L;
	public static final Color	defaultSayLabelColor	= new Color(0xffffcc);

	public static int compareIdsByRating(Id id, Id id1, ListItem item,
			ListItem item1) {
		int result;
		if (item.color != item1.color)
			result = item.color != Color.blue ? 1 : -1;
		else if (item.rating != item1.rating)
			result = item.rating <= item1.rating ? 1 : -1;
		else
			result = id.name.compareTo(id1.name);
		return result;
	}

	static Hashtable<String, String> readPropertyes(
			DataInputStream datainputstream) throws IOException {
		Hashtable<String, String> hashtable = new Hashtable<String, String>(7);
		short word0 = datainputstream.readShort();
		for (int i1 = 0; i1 < word0; i1++) {
			String s1 = datainputstream.readUTF();
			String s2 = datainputstream.readUTF();
			hashtable.put(s1, s2);
		}

		return hashtable;
	}

	public AvatarImageList						avatars;
	public Image								idStatusImages[];
	public Hashtable<String, String>			ignoreds;
	public Table								tables[];
	public Hashtable<String, Id>				ids;
	public Vector<String>						friends;
	protected Vector<YahooTable>				visibleTables;
	public Hashtable<String, PrivateChatFrame>	privateChatTable;
	public Hashtable<String, InformationFrame>	informationTable;
	public Hashtable<String, String>			idPropertyes;
	public int									myAvatar;
	public long									idFlags;
	public YahooCheckBox						chkImFromFriendsOnly;
	public YahooCheckBox						chkDeclineAllInvites;
	public YahooCheckBox						chkShowLinkToYahooMessenger;
	public YahooCheckBox						chkShowAllStarMemberShip;
	public YahooCheckBox						chkProfanityNone;
	public YahooCheckBox						chkProfanityWeak;
	public YahooCheckBox						chkProfanityStrong;
	public YahooControl							w;
	public YahooButton							btnHelp;
	public AvatarList							avatarList;
	YahooButton									btnExit;
	YahooButton									btnOptions;
	YahooDialog									dlgOptions;
	public YahooControl							C;
	int											reconnectCount;
	boolean										disconnected;
	YahooLabel									lblConnectionStatus;
	YahooControl								G;
	YahooTextBox								txtChat;
	YahooListBox								lbChat;
	YahooListBox								idList;
	YahooListBox								inviteList;
	int											connectionState;
	boolean										notSystemTested;
	public String								myId;
	public Id									currId;
	int											command;
	String										cookie;
	String										ycookie;
	String										agent;
	protected String							page_id;
	String										prof_id;
	String										page_title;
	String										host;
	int											port;
	String										yport;
	boolean										proxy_http;
	String										ad_info_url_prefix;
	String										ad_info_filename;
	String										ad_image_url_prefix;
	String										logo_image_url_prefix;
	String										profile_prefix;
	public boolean								noPopupTables;
	public String								avatar_host;
	int											minimum_endad_frequency;
	int											pending_ad_timeout;
	public String								room;
	public String								label;
	public boolean								nofaceicons;
	public CustomTableOptions					tableSettings;
	boolean										closed;
	long										last_endad_time;
	Vector<AdEntry>								ads;

	public YahooConnectionThread				yahooConnectionThread;

	public AbstractYahooGamesApplet() {
		idStatusImages = new Image[4];
		ignoreds = new Hashtable<String, String>(7);
		tables = new Table[256];
		ids = new Hashtable<String, Id>();
		friends = new Vector<String>();
		visibleTables = new Vector<YahooTable>();
		privateChatTable = new Hashtable<String, PrivateChatFrame>(7);
		informationTable = new Hashtable<String, InformationFrame>(7);
		idPropertyes = new Hashtable<String, String>(7);
		myAvatar = 0;
		idFlags = 0L;
		w = new YahooControl();
		C = new YahooControl();
		reconnectCount = 0;
		disconnected = false;
		inviteList = null;
		connectionState = 3;
		notSystemTested = false;
		command = 0;
		host = null;
		noPopupTables = true;
		nofaceicons = true;
		last_endad_time = 0L;
		ads = new Vector<AdEntry>();
		// smallwindows = Toolkit.getDefaultToolkit().getScreenSize().height <
		// 550;
	}

	public boolean action(Event event, Object obj) {
		if (event.target == btnExit) {
			closeRoom();
			if (room != null)
				showDocument("close.html?room=" + room, "yog_" + room);
			return true;
		}
		if (event.target == txtChat) {
			String s1 = YahooUtils.translateMessage((String) obj);
			if (s1.startsWith("/open")) {
				command = 1;
				int i1 = s1.indexOf(' ');
				if (i1 != -1) {
					cookie = s1.substring(i1 + 1);
					ycookie = "";
				}
				reconnectCount = 0;
				disconnected = false;
				if (connectionState == 3)
					connectToServer();
				else
					exit();
				txtChat.setText("");
			}
			else if (s1.equals("/reconnect") || s1.equals("/r")) {
				reconnectCount = 0;
				disconnected = false;
				connectionState = 3;
				txtChat.setText("");
				connectToServer();
			}
			else if (s1.startsWith("/join")) {
				command = 1;
				int i2 = s1.indexOf(' ');
				if (i2 != -1) {
					String sTable = s1.substring(i2 + 1);
					int table = Integer.parseInt(sTable);
					((CustomYahooGamesApplet) this).joinTable(table);
				}
			}
			else if (s1.length() > 0) {
				lbChat.Hs();
				chat(s1);
				txtChat.setText("");
			}
			return true;
		}
		if (event.target == idList) {
			ListItem _lcls155 = idList.getSelected();
			if (_lcls155 != null)
				information(((Id) _lcls155.obj).name);
			return true;
		}
		if (event.target == chkDeclineAllInvites) {
			declineAllInvites(chkDeclineAllInvites.isChecked());
			return true;
		}
		if (event.target == btnOptions) {
			dlgOptions.show();
		}
		else {
			if (event.target == chkImFromFriendsOnly) {
				imFromFriendsOnly(((Boolean) obj).booleanValue());
				return true;
			}
			if (event.target == chkShowLinkToYahooMessenger) {
				changeIdProperty("prowler_g", ((Boolean) obj).booleanValue());
				return true;
			}
			if (event.target == chkShowAllStarMemberShip) {
				changeIdProperty("games_common_hidestar", ((Boolean) obj)
						.booleanValue() ^ true);
				return true;
			}
			if (event.target == btnHelp) {
				showDocument("http://saddam.virtuaserver.com.br/help/"
						+ intl_code + "/games/play/play-23.html", "_blank");
				return true;
			}
			if (event.target == chkProfanityNone)
				changeIdProperty("games_common_profanity", "0");
			else if (event.target == chkProfanityWeak)
				changeIdProperty("games_common_profanity", "1");
			else if (event.target == chkProfanityStrong)
				changeIdProperty("games_common_profanity", "2");
		}
		return false;
	}

	void addIds(Vector<String> vector, Color color) {
		for (int i1 = 0; i1 < vector.size(); i1++) {
			String s1 = vector.elementAt(i1);
			Id _lcls49 = ids.get(s1);
			if (_lcls49 != null) {
				idList.setColor(color, _lcls49.idListItem);
				if (inviteList != null)
					inviteList.setColor(color, _lcls49.inviteListItem);
			}
		}

	}

	protected void addIgnored(String name) {
		ignoreds.put(name, name);
	}

	public boolean autoMoveEnabled() {
		return "1".equals(getIdProperty("games_common_automove"));
	}

	public void ban(String name, int time, String reason) {
		send('_', name, time, reason);
	}

	public void beep() {
		playSound("yog/resource/common/beep.au");
	}

	public void bi(AdEntry _pcls11) {
	}

	void boot(int i1, String s1) {
		if (isConnected()) {
			yahooConnectionThread.output.write(43);
			yahooConnectionThread.output.write(i1);
			yahooConnectionThread.output.write(81);
			yahooConnectionThread.output.writeUTF(s1);
			yahooConnectionThread.flush();
		}
	}

	public void changeAvatar(int i1) {
		send('F', i1);
	}

	protected void changeIdProperty(String s1, boolean flag) {
		changeIdProperty(s1, flag ? "1" : "0");
	}

	public void changeIdProperty(String s1, String s2) {
		idPropertyes.put(s1, s2);
		if (isConnected()) {
			yahooConnectionThread.output.write(20);
			yahooConnectionThread.output.writeUTF(s1);
			yahooConnectionThread.output.writeUTF(s2);
			yahooConnectionThread.flush();
		}
	}

	public void chat(String s1) {
		send('C', s1);
	}

	public void close() {
		closeRoom();
		if (avatars != null)
			avatars.flush();
		for (Image element : idStatusImages)
			if (element != null)
				element.flush();

	}

	public void closeInformationFrame(String name) {
		InformationFrame frame = informationTable.remove(name);
		if (frame != null) {
			frame.setVisible(false);
			frame.dispose();
		}
	}

	public void closeRoom() {
		if (!closed) {
			exit();
			disconnected = true;
			handleDisconnect(null, true);
			closed = true;
			PrivateChatFrame _lcls134;
			for (Enumeration<PrivateChatFrame> enumeration = privateChatTable
					.elements(); enumeration.hasMoreElements(); _lcls134
					.dispose()) {
				_lcls134 = enumeration.nextElement();
				_lcls134.setVisible(false);
			}

			InformationFrame _lcls36;
			for (Enumeration<InformationFrame> enumeration = informationTable
					.elements(); enumeration.hasMoreElements(); _lcls36
					.dispose()) {
				_lcls36 = enumeration.nextElement();
				_lcls36.setVisible(false);
			}
		}
	}

	void closeTable(int table, String id) {
		if (!closed)
			leaveTable(table);
	}

	public void connectToServer() {
		if (connectionState == 3) {
			lblConnectionStatus.setCaption(lookupString(0x665000f6));
			// Attempting
			// to
			// connect
			// to the games server
			connectionState = 1;
			yahooConnectionThread = new YahooConnectionThread(host, port,
					yport, this);
		}
	}

	public abstract YahooListBox createIdList(boolean flag, int i1);

	public void declineAllInvites(boolean flag) {
		send('&', flag);
	}

	void doAlert(String message) {
		if (!closed)
			new OkFrame(message, processor, this);
	}

	void doChangeAvatar(int avatar, Id id) {
		if (!closed) {
			id.avatar = avatar;
			if (id.name.equals(myId)) {
				myAvatar = avatar;
				notifyFlagsAvatar();
			}
			idList.putAvatarSquare(avatar, id.idListItem, 0);
		}
	}

	void doChangePrivateFlags(long l1) {
		idFlags = l1;
		notifyFlagsAvatar();
	}

	void doChangePublicFlags(Id id, int flags) {
		notifyChangeFlags(id.publicFlags, flags, idList, id.idListItem);
		id.setPublicFlags(flags);
	}

	void doChangeTablePrivacy(int i1, int j1) {
	}

	void doChangeTableProperties(int number,
			Hashtable<String, String> properties) {
		if (!closed) {
			Table table = tables[number];
			if (table.yahooTable != null)
				table.yahooTable.notifyChangeProperties(properties);
		}
	}

	void doChat(Id id, String message) {
		if (!closed && !isIgnored(id.name)) {
			Color color = lbChat.getForeColor();
			if (friends.contains(id.name))
				color = Color.blue;
			else if (id.adminFlags != 0)
				color = Color.orange.darker();
			lbChat.append(id, message, color);
		}
	}

	void doDeclineInvite(Id id, String reason) {
		if (id.hasSentInviation) {
			id.hasSentInviation = false;
			new OkFrame(id.caption + lookupString(0x665001f5) + reason,
					processor, this);
		}
	}

	void doDoneTable(int number) {
		if (!closed)
			tables[number] = null;
	}

	public void doError(Throwable error) {
		handleError(error);
		if (connectionState == 0) {
			String s = getParameter("no_send_errors");
			if (s == null || s.equals("0"))
				sendError(error);
			try {
				Thread.sleep(500L);
			}
			catch (InterruptedException _ex) {
			}
		}
	}

	void doIdEnter(String name, String caption) {
		if (!closed) {
			Id id = new Id(name, caption);
			id.idListItem = idList.add(caption, id);
			if (friends.indexOf(name) != -1)
				idList.setColor(Color.blue, id.idListItem);
			ids.put(name, id);
			if (id.name.equals(myId))
				currId = id;

			PrivateChatFrame frame = privateChatTable.get(name);
			if (frame != null)
				frame.apendMessage("***" + id.caption
						+ " has joined on the room", Color.blue);
		}
	}

	void doIdExit(Id id) {
		if (!closed) {
			PrivateChatFrame frame = privateChatTable.get(id.name);
			if (frame != null)
				frame.apendMessage(
						"***" + id.caption + " has left of the room",
						Color.blue);
			idList.remove(id.idListItem);
			ids.remove(id.name);
		}
	}

	void doIdSit(int i1, int j1, String name) {
	}

	void doIgnore(String name) {
		addIgnored(name);
	}

	void doImFromFriendsOnly(boolean flag) {
		chkImFromFriendsOnly.setChecked(flag);
	}

	void doInformation(Id id, String profile, int idle) {
		if (!closed) {
			InformationFrame frame = informationTable.get(id.name);
			if (frame == null) {
				frame = new InformationFrame(this, id,
						ignoreds.get(id.name) != null, profile);
				informationTable.put(id.name, frame);
			}
			else {
				frame.lblProfile.setCaption(profile);
				frame.pack();
				frame.setVisible(true);
			}
			frame.setIdle(idle);
		}
	}

	void doJoinTable(Id id, int number) {
		if (!closed) {
			Table table = tables[number];
			id.tables.add(table);
			table.ids.add(id);
			if (id.name.equals(myId)) {
				table.yahooTable = tableSettings.newTable();
				table.yahooTable.setup(table, this, yahooConnectionThread);
				visibleTables.add(table.yahooTable);
				for (int j1 = 0; j1 < table.ids.size(); j1++) {
					Id id1 = table.ids.elementAt(j1);
					table.yahooTable.addId(id1);
				}
			}
			else if (table.yahooTable != null)
				table.yahooTable.addId(id);
		}
	}

	public void doKeepAlive() {
		if (isConnected()) {
			yahooConnectionThread.output.write(0x21);
			yahooConnectionThread.flush();
		}
	}

	void doLeaveTable(Id id, int number) {
		if (!closed) {
			Table table = tables[number];
			id.tables.removeElement(table);
			table.ids.removeElement(id);
			if (table.yahooTable != null)
				table.yahooTable.jq(id);
			table.ids.removeElement(id);
			if (id.name.equals(myId)) {
				table.yahooTable.close();
				visibleTables.removeElement(table.yahooTable);
				new _cls159().cy(1000);
				if (!table.yahooTable.s && !idPropertyContains(1L))
					evalJS("gamesLoadBigAD()");
				table.yahooTable = null;
			}
		}
	}

	void doNewTable(int number, Hashtable<String, String> properties, long time) {
		if (!closed) {
			Table _lcls118 = new Table(this, number, properties, time);
			tables[number] = _lcls118;
		}
	}

	void doPing(String name) {
		responsePing(name);
	}

	void doPingDispatched(String name) {
		InformationFrame frame = informationTable.get(name);
		if (frame != null) {
			frame.lblPing.setCaption(lookupString(0x665000f2));
			frame.pingDispatchTime = System.currentTimeMillis();
		}
	}

	void doPrivateChat(String name, String message) {
		if (!closed
				&& ignoreds.get(name) == null
				&& (!chkImFromFriendsOnly.isChecked() || friends.contains(name))) {
			PrivateChatFrame frame = privateChatTable.get(name);
			if (frame == null) {
				frame = new PrivateChatFrame(this, name, chkImFromFriendsOnly
						.isChecked());
				privateChatTable.put(name, frame);
			}
			frame.apendMessage(name, message);
			if (!frame.isVisible())
				frame.setVisible(true);
		}
	}

	void doResponsePing(Id id) {
		InformationFrame frame = informationTable.get(id.name);
		if (frame != null) {
			int myDelay = (int) (frame.pingDispatchTime - frame.pingStartTime);
			int otherDelay = (int) (System.currentTimeMillis() - frame.pingDispatchTime);
			frame.waitingPingResponse = false;
			frame.btnPing.setEnabled(true);
			frame.lblPing.setCaption(id.caption + lookupString(0x665000fb)
					+ Formater.formatFloat(otherDelay, 1000)
					+ lookupString(0x665000ea)
					+ Formater.formatFloat(myDelay, 1000)
					+ frame.applet.lookupString(0x6650017c));
		}
	}

	void doShowOptions() {
		dlgOptions.show();
	}

	void doSystemMessage(String message) {
		if (!closed) {
			String line;
			for (StringTokenizer stringtokenizer = new StringTokenizer(message,
					"\n"); stringtokenizer.hasMoreElements(); lbChat.append(
					"*** " + line, Color.blue))
				line = stringtokenizer.nextToken();

			if (notSystemTested) {
				Zh(true);
				notSystemTested = false;
				lblConnectionStatus.setCaption(lookupString(0x66500125)
						+ ids.get(myId).caption);
				String follow = getParameter("follow");
				if (follow != null) {
					sendStatus("GAMEPROWLER FOLLOW");
					watch(follow);
				}
				sendStatus("RESOLUTION "
						+ Toolkit.getDefaultToolkit().getScreenSize()
								.toString());
				long currTime = System.currentTimeMillis();
				if (getParameter("testcpu") != null) {
					testCPU(28);
					sendStatus("FIBTIME "
							+ (System.currentTimeMillis() - currTime));
				}
				evalJS("gamesConnectSuccess()");
			}
		}
	}

	void doTableMessage(int number, byte byte0, DataInputStream datainputstream)
			throws IOException {
		if (!closed) {
			Table table = tables[number];
			if (table.yahooTable == null)
				return;
			table.yahooTable.parseData(byte0, datainputstream);
		}
	}

	public void exit() {
		if (isConnected()) {
			yahooConnectionThread.output.write('X');
			connectionState = 2;
			yahooConnectionThread.flush();
			yahooConnectionThread.exit();
		}
	}

	public void getAd2(UrlProcessEntry entry) {
		// System.out.println("got ad2: " + _pcls93.d + " " + _pcls93.a);
		if (entry.code == 2) {
			AdEntry ad = (AdEntry) entry.obj;
			String content = entry.content;
			ad.processed = true;
			if (entry.exception != null)
				ad.exception = entry.exception;
			else if (content.indexOf("noad") != -1
					|| content.indexOf("noconn") != -1)
				return;
			if (content != null) {
				StringTokenizer stringtokenizer = new StringTokenizer(content);
				if (stringtokenizer.hasMoreTokens()) {
					ad.imageUrl = stringtokenizer.nextToken();
					if (ad.imageUrl.endsWith(".gif")) {
						ad.image = getYahooImage(ad.imageUrl);
						// System.out.println("go ad2 image=" + _lcls11.d);
					}
				}
				if (stringtokenizer.hasMoreTokens())
					ad.content = stringtokenizer.nextToken();
				try {
					if (stringtokenizer.hasMoreTokens())
						ad.width = Integer
								.parseInt(stringtokenizer.nextToken());
					if (stringtokenizer.hasMoreTokens())
						ad.height = Integer.parseInt(stringtokenizer
								.nextToken());
				}
				catch (NumberFormatException _ex) {
				}
			}
		}
	}

	public Id getId(DataInputStream datainputstream) throws IOException {
		return getId(datainputstream.readUTF());
	}

	public Id getId(String name) {
		return ids.get(name);
	}

	public String getIdProperty(String s1) {
		return idPropertyes.get(s1);
	}

	int getInteger(String key, int defaultValue) {
		String s2 = getParameter(key);
		int j1 = defaultValue;
		if (s2 != null)
			j1 = Integer.parseInt(s2);
		return j1;
	}

	public String getPageTitle() {
		return page_title;
	}

	public Color getYahooColor(String path, int defaultColor) {
		String s2 = getParameter(path);
		if (s2 != null)
			try {
				return new Color(Integer.parseInt(s2, 16));
			}
			catch (NumberFormatException _ex) {
			}
		return new Color(defaultColor);
	}

	public final Image getYahooImage(String s1) {
		try {
			if (proxy_http)
				if (s1.startsWith(ad_image_url_prefix))
					s1 = "http://" + getParameter("host")
							+ s1.substring(ad_image_url_prefix.length());
				else if (s1.startsWith(logo_image_url_prefix))
					s1 = "http://" + getParameter("host")
							+ s1.substring(logo_image_url_prefix.length());
				else
					return null;
			// System.out.println("getting image: " + s1);
			return getImage(new URL(s1));
		}
		catch (MalformedURLException _ex) {
			return null;
		}
	}

	public final void Gi() {
		noPopupTables = "true"
				.equals(getParameter("yahoo.games.nopopuptables")) ^ true;
		try {
			noPopupTables = "true"
					.equals(getParameter("yahoo.games.nopopuptables")) ^ true;
			initProperties();
		}
		catch (Throwable _ex) {
			closed = true;
		}
	}

	public void handleDisconnect(YahooConnectionThread connection,
			boolean success) {
		if (!closed)
			if (connectionState == 1) {
				lblConnectionStatus.setCaption(lookupString(0x66500118));
				connectionState = 3;
				if (!success)
					new OkDialog(super.container, lookupString(0x66500105),
							this);
			}
			else {
				for (int i1 = 0; i1 < visibleTables.size(); i1++)
					visibleTables.elementAt(i1).close();

				visibleTables.clear();
				lblConnectionStatus.setCaption(lookupString(0x66500123));
				connectionState = 3;
				idList.clear();
				ignoreds.clear();
				lbChat.clear();
				for (int j1 = 0; j1 < 256; j1++)
					tables[j1] = null;

				ids.clear();
				if (isActive() && !disconnected /* && reconnectCount < 8 */)
					if (System.currentTimeMillis()
							- yahooConnectionThread.lastRequestTime > 0x249f00L) {
						lblConnectionStatus
								.setCaption(lookupString(0x6650010c));
					}
					else if (!success) {
						reconnectCount++;
						connectToServer();
					}
			}
	}

	public void handleError(Throwable throwable) {
		throwable.printStackTrace();
	}

	public void handlePrivateChatClose(String s1) {
		PrivateChatFrame _lcls134 = privateChatTable.remove(s1);
		if (_lcls134 != null) {
			_lcls134.setVisible(false);
			_lcls134.dispose();
		}
	}

	public void handleProcess(ProcessHandler handler, int i, Object obj) {
		processor.addProcess(handler, i, obj);
	}

	public void handleTimer(long l1) {
		if (!closed) {
			if (connectionState == 0) {
				for (int i1 = 0; i1 < visibleTables.size(); i1++)
					visibleTables.elementAt(i1).processMessages();

			}
			for (int j1 = 0; j1 < ads.size(); j1++) {
				AdEntry ad = ads.elementAt(j1);
				boolean flag = false;
				if (ad.exception != null
						|| ad.processed
						&& (ad.content == null || ad.image == null)
						|| System.currentTimeMillis() - ad.time > pending_ad_timeout) {
					sendStatus("ENDAD ABORT");
					flag = true;
				}
				else if (ad.processed)
					if (ad.image != null) {
						if (ad.width == -1)
							ad.width = ad.image.getWidth(null);
						if (ad.height == -1)
							ad.height = ad.image.getHeight(null);
						if (ad.width != -1 && ad.height != -1)
							flag = true;
					}
					else {
						flag = true;
					}
				if (flag) {
					if (ad.table != null)
						ad.table.Uq(ad);
					else
						bi(ad);
					ad.table = null;
					ads.remove(j1);
					j1--;
				}
			}

		}
	}

	public boolean idPropertyContains(long l1) {
		return (idFlags & l1) != 0L;
	}

	public void ignore(String s1) {
		send('$', s1);
	}

	public void ignoreName(String s1) {
		if (s1.equals(myId)) {
			new OkFrame(lookupString(0x6650011d), processor, this);
		}
		else {
			addIgnored(s1);
			ignore(s1);
		}
	}

	void imFromFriendsOnly(boolean flag) {
		send('^', flag);
	}

	public void information(String s1) {
		send('I', s1);
	}

	public void initProperties() {
		avatars = new AvatarImageList();
		idStatusImages[0] = ImageReader
				.getImage("\f\f\027\uFF66\u99CC\uFF99\u9999\uFF99\u9933\uFFCC\uFFFF\uFF00\u66CC\uFF66\uCCFF\uFF66\u6666\uFF99\u6699\uFFCC\uCCFF\uFF00\u99FF\uFF00\u33CC\uFF33\u99CC\uFF33\u3399\uFF66\u9999\uFFFF\uFFFF\uFF00f\uFF33\u66CC\uFF66\u6699\uFF99\uCCFF\uFF99\u6666\uFFCC\uCC00\uFF33\u9999\377\uFFFF\000\0008\000\013p\006\fYCz)t\004y!8@\005\000\000\177\177W\177r/;\024\001uTJ/y\002^@?s}\r\177\177\177\177\177?\177G/2\026aGxp\036X\017r{\r\000\000\030\000\002`G/o9r$8\000JA\020\020\004\002\000\177\177G\177x\0178P\000\004\005R\003{\004\036CGyy\016");
		idStatusImages[1] = ImageReader
				.getImage("\f\f\020\uFFAF\u7A00\uFFF0\u9105\uFFBD\u7200\uFFE4\uC274\uFF97\u6A00\uFFFF\uDA92\uFFF0\uAF32\uFFCB\u8904\uFFDE\uB559\uFFE7\uC985\uFFFF\uFFFF\uFF26\u1A00\uFFC3\u9324\uFFFF\uB74F\uFFFF\uC970\377\uFFFF\177\177\037\177I?~wVI%5m%;\031\033\017p\007\016\177\177S?Vg}^\037\177}?yo\177\031\037oww\016\177\177G?\177G{!\\\000\035`\003p\021\036`\017x\017\017\177\177k\177n\177g|s\177d\037{'n\035\033\017p\017\017");
		idStatusImages[2] = ImageReader
				.getImage("\f\f\005\uFF99\u9999\uFFF7\000\uFF00\000\uFFFF\u0707\377\uFFFF\000\000\000\000\000>\000F\005\033C30\004?\001\020\000\000\000\000\000\000\000\000\000~\000N\007#Cu`\f\016A7\000\000\000\000\177\177\177\177\177\001\1771x\004<\000\017s@>@\177\177\177\017");
		idStatusImages[3] = ImageReader
				.getImage("\f\f\020\uFFBE\u8EDC\uFFFE\uFEFC\uFF8E\u3ABC\uFFDA\uB2F4\uFFC7\u99E8\uFF9F\u52D4\uFF2E\u1244\uFF8B\u31D0\uFFA7\u55E9\uFF87\u43AF\uFF6A\u22A3\uFFB1\u73E4\uFFAA\u6ACC\uFFBF\u85E4\uFF7B\u25B7\377\uFFFF\177\177g?L\007hzX\"\\\007\027!T\034\000\017p\017\017\177\177'?hg{\004W]'xo>;{\177\177\177w\016\177\177K\177Gw{\007?]=8o\";Y;\017p\007\016\177\177c?\\\007t\002v\000\007`\001|\001>D\177\177\177\017");
		chkImFromFriendsOnly = new YahooCheckBox(lookupString(0x66500111));// IMs
		// from
		// friends
		// only
		chkDeclineAllInvites = new YahooCheckBox(lookupString(0x66500119));// Decline
		// all
		// inviations
		chkShowLinkToYahooMessenger = new YahooCheckBox(
				lookupString(0x66500b4c));// Show a link to my
		// game in my Yahoo!
		// Messenger status
		chkShowAllStarMemberShip = new YahooCheckBox(lookupString(0x665016b9),
				null, true);// Show All-Star
		// membership
		btnHelp = new YahooButton(lookupString(0x66500b4d));// Learn
		// More
		avatarList = new AvatarList(((YahooImageList) avatars).image[0]
				.getHeight(null) * 4);
		chkProfanityNone = new YahooCheckBox(lookupString(0x66500e1b), null,
				true);// None
		chkProfanityWeak = new YahooCheckBox(lookupString(0x66500e1d),
				chkProfanityNone, false);// Weak
		chkProfanityStrong = new YahooCheckBox(lookupString(0x66500e1c),
				chkProfanityWeak, false);// Strong
		agent = getParameter("agent");
		if (agent == null)
			agent = "undefined";
		page_id = getParameter("page_id");
		if (page_id == null)
			page_id = "undefined";
		prof_id = getParameter("prof_id");
		if (prof_id == null)
			prof_id = "chat_pf_1";
		page_title = getParameter("page_title");
		if (page_title == null)
			page_title = "undefined";
		cookie = getParameter("cookie");
		ycookie = getParameter("ycookie");
		proxy_http = getParameter("proxy_http") != null;
		ad_info_url_prefix = getParameter("ad_info_url_prefix");
		if (ad_info_url_prefix == null)
			ad_info_url_prefix = "http://saddam.virtuaserver.com.br/games";
		ad_info_filename = getParameter("yahoo.games.ad_info_filename");
		if (ad_info_filename == null)
			ad_info_filename = "appad.html";
		profile_prefix = getParameter("profile_prefix");
		if (profile_prefix == null)
			profile_prefix = "http://saddam.virtuaserver.com.br/games";
		avatar_host = getParameter("avatar_host");
		ad_image_url_prefix = getParameter("ad_image_url_prefix");
		if (ad_image_url_prefix == null)
			ad_image_url_prefix = "http://saddam.virtuaserver.com.br";
		logo_image_url_prefix = getParameter("logo_image_url_prefix");
		if (logo_image_url_prefix == null)
			logo_image_url_prefix = "http://saddam.virtuaserver.com.br";
		minimum_endad_frequency = 0x493e0;
		String s1 = getParameter("minimum_endad_frequency");
		if (s1 != null)
			try {
				minimum_endad_frequency = Integer.parseInt(s1);
			}
			catch (NumberFormatException _ex) {
			}
		pending_ad_timeout = 10000;
		String s2 = getParameter("pending_ad_timeout");
		if (s2 != null)
			try {
				pending_ad_timeout = Integer.parseInt(s2);
			}
			catch (NumberFormatException _ex) {
			}
		Color color = getYahooColor("yahoo.games.ante_bg", 0x999966);
		Color color1 = getYahooColor("yahoo.games.ante_bbbackground", 0xcccc99);
		Color color2 = getYahooColor("yahoo.games.ante_bboutlinelight",
				0xcccc99);
		Color color3 = getYahooColor("yahoo.games.ante_bbshadow", 0x999966);
		Color color4 = getYahooColor("yahoo.games.ante_bboutlinedark", 0x333300);
		nofaceicons = "true".equals(getParameter("yahoo.games.nofaceicons")) ^ true;
		super.container.nc(Color.black, color, color1, color4, color2, color3);
		if (cookie == null || cookie.equals("undefined"))
			cookie = "";
		if (ycookie == null || ycookie.equals("undefined"))
			ycookie = "";
		port = getInteger("port", 0);
		yport = getParameter("yport");
		if (yport == null)
			yport = "undefined";
		host = getParameter("host");
		btnExit = new YahooButton(lookupString(0x665000f9));// Exit
		// Games
		lbChat = new YahooListBox(ph(), 100, 1, -1, null, false, false, true,
				avatars.image);
		lbChat.ht(true);
		idList = createIdList(true, 2);
		YahooLabel lblSay = new YahooLabel(lookupString(0x665000fd));// Say:
		lblSay.setBackColor(defaultSayLabelColor);
		txtChat = new YahooTextBox(getTimerHandler());
		G = new YahooControl();
		G.addChildObject(lblSay, 1, 1, 0, 0);
		G.addChildObject(txtChat, 1, 1, 1, 0, false);
		G.addChildObject(lbChat, 2, 1, 0, 1, true);
		dlgOptions = new OptionsDialog(super.container, this);
		room = getParameter("room");
		label = getParameter("label");
		if (room == null)
			room = "undefined";
		connectToServer();
		super.processor.timerHandler.add(this, 15);
	}

	boolean isConnected() {
		return connectionState == 0;
	}

	boolean isIgnored(String s1) {
		return ignoreds.get(s1) != null;
	}

	public boolean isKids() {
		return "kids".equals(getParameter("category"));
	}

	public boolean isSmallWindows() {
		return "1".equals(getIdProperty("games_common_smallwindows"));
	}

	public void joinTable(int i1) {
		send('J', i1);
	}

	public void Kg(boolean flag) {
		changeIdProperty("games_common_sound", flag ? "1" : "0");
	}

	public void leaveTable(int i1) {
		send('L', i1);
	}

	void loadText(String path, int code, Object obj) {
		if (proxy_http)
			loadText("/games/" + super.intl_code + "/" + path, code, obj, true);
		else
			loadText(ad_info_url_prefix + "/" + path, code, obj, false);
	}

	public void logMessage(String s, Color color) {
		lbChat.append(s, color);
	}

	public void mute(String name, int time, String reason) {
		send('(', name, time, reason);
	}

	public void noIgnore(String s1) {
		send('%', s1);
	}

	public void noIgnoreName(String s1) {
		removeIgnored(s1);
		noIgnore(s1);
	}

	void notifyChangeFlags(int oldFlags, int newFlags, YahooListBox list,
			ListItem item) {
		for (int k1 = 0; k1 < idStatusImages.length; k1++)
			if ((oldFlags & 1 << k1) != 0)
				list.removeComponent(item, 0, 1);

		int l1 = 0;
		for (int i2 = 0; i2 < idStatusImages.length; i2++)
			if ((newFlags & 1 << i2) != 0) {
				list.putComponent(new YahooImage(idStatusImages[i2]), item, 0,
						l1 + 1);
				l1++;
			}

		list.update(item, Id.getAdminFlags(newFlags), null);
	}

	void notifyFlagsAvatar() {
		avatarList.Lu();
		for (int i1 = 0; i1 < 45 - (idPropertyContains(4L) ? 0 : 11); i1++)
			avatarList
					.add(new YahooImage(((YahooImageList) avatars).image[i1]));

		if (myAvatar < avatarList.size())
			avatarList.setCurrentAvatar(myAvatar);
	}

	public void openProfile(String name) {
		try {
			String profile_consolidater = getParameter("profile_consolidater");
			getAppletContext().showDocument(
					new URL(profile_prefix
							+ "/profile2?name="
							+ name
							+ "&intl="
							+ super.intl_code
							+ (profile_consolidater != null ? "&consolidater="
									+ profile_consolidater : "")), "_blank");
		}
		catch (MalformedURLException _ex) {
		}
	}

	public void parseData(int i1, DataInputStream datainputstream)
			throws IOException {
		Id id;
		switch (i1) {
		case 33: // '!' keep-alive
			doKeepAlive();
			break;

		case 97: // 'a' alert
			doAlert(datainputstream.readUTF());
			break;

		case 99: // 'c' chat
			doChat(getId(datainputstream), datainputstream.readUTF());
			break;

		case 100: // 'd' done table
			int j1 = YahooUtils.readUnsignedByte(datainputstream);
			doDoneTable(j1);
			break;

		case 101: // 'e' id enter
			doIdEnter(datainputstream.readUTF(), datainputstream.readUTF());
			break;

		case 102: // 'f'
			doChangePrivateFlags(datainputstream.readLong());
			break;

		case 103: // 'g' ping
			doPing(datainputstream.readUTF());
			break;

		case 104: // 'h' response ping
			doResponsePing(getId(datainputstream));
			break;

		case 106: // 'j' join table
			doJoinTable(getId(datainputstream), YahooUtils
					.readUnsignedByte(datainputstream));
			break;

		case 107: // 'k'
			doPingDispatched(datainputstream.readUTF());
			break;

		case 108: // 'l' leave table
			doLeaveTable(getId(datainputstream), YahooUtils
					.readUnsignedByte(datainputstream));
			break;

		case 109: // 'm'
			doSystemMessage(datainputstream.readUTF());
			break;

		case 110: // 'n': new table
			int k1 = YahooUtils.readUnsignedByte(datainputstream);
			Hashtable<String, String> hashtable = readPropertyes(datainputstream);
			boolean flag = datainputstream.readBoolean();
			long time = -1L;
			if (flag)
				time = System.currentTimeMillis() + datainputstream.readInt();
			doNewTable(k1, hashtable, time);
			break;

		case 111: // 'o': change avatar
			byte avatar = datainputstream.readByte();
			doChangeAvatar(avatar, getId(datainputstream));
			break;

		case 112: // 'p': change table privacy
			int l1 = YahooUtils.readUnsignedByte(datainputstream);
			byte byte0 = datainputstream.readByte();
			doChangeTablePrivacy(l1, byte0);
			break;

		case 113: // 'q': boot
			int i2 = YahooUtils.readUnsignedByte(datainputstream);
			String s3 = datainputstream.readUTF();
			closeTable(i2, s3);
			break;

		case 114: // 'r'
			doShowOptions();
			break;

		case 115: // 's' id sit
			int j2 = YahooUtils.readUnsignedByte(datainputstream);
			byte byte1 = datainputstream.readByte();
			String s6 = datainputstream.readUTF();
			doIdSit(j2, byte1, s6);
			break;

		case 120: // 'x' id exit
			doIdExit(getId(datainputstream));
			break;

		case 118: // 'v': decline invite
			doDeclineInvite(getId(datainputstream), datainputstream.readUTF());
			break;

		case 119: // 'w' hide star
			id = getId(datainputstream);
			doChangePublicFlags(id, datainputstream.readInt());
			break;

		case 121: // 'y' information
			doInformation(getId(datainputstream), datainputstream.readUTF(),
					datainputstream.readInt());
			break;

		case 61: // '=' table command
			int k2 = YahooUtils.readUnsignedByte(datainputstream);
			byte byte2 = datainputstream.readByte();
			doTableMessage(k2, byte2, datainputstream);
			break;

		case 49: // '1' friends
			short word0 = datainputstream.readShort();
			Vector<String> friends = new Vector<String>();
			for (int j3 = 0; j3 < word0; j3++)
				friends.add(datainputstream.readUTF());
			updateFriends(friends);
			break;

		case 51: // '3' private chat
			String s1 = datainputstream.readUTF();
			String s4 = datainputstream.readUTF();
			doPrivateChat(s1, s4);
			break;

		case 52: // '4' ignoreds
			short word1 = datainputstream.readShort();
			for (int i3 = 0; i3 < word1; i3++)
				doIgnore(datainputstream.readUTF());
			break;

		case 54: // '6' Im from friends only
			doImFromFriendsOnly(datainputstream.readByte() != 0);
			break;

		case 48: // '0' table properties
			int l2 = YahooUtils.readUnsignedByte(datainputstream);
			Hashtable<String, String> hashtable1 = readPropertyes(datainputstream);
			doChangeTableProperties(l2, hashtable1);
			break;

		case 20: // '\024' id property
			String s2 = datainputstream.readUTF();
			String s5 = datainputstream.readUTF();
			idPropertyes.put(s2, s5);
			updateIdProperty(s2, s5);
			break;

		default:
			throw new IllegalArgumentException("Illegal command: " + i1);
		}
	}

	public abstract int ph();

	public void ping(String s1) {
		send('G', s1);
	}

	public void plawNow() {
		if (isConnected()) {
			yahooConnectionThread.output.write('Q');
			yahooConnectionThread.flush();
		}
	}

	public void privateChat(String s1, String s2) {
		send('#', s1, s2);
	}

	public void processMessages(DataInputStream datainputstream, int state) {
		if (closed)
			yahooConnectionThread.close();
		try {
			if (state == 0) {
				if (!datainputstream.readUTF().equals("GAMES"))
					throw new IllegalArgumentException(
							"Error in startup protocol");
				datainputstream.readUTF();
				datainputstream.readUTF();
				/*
				 * if(!s1.equals(_cls105.n) ||
				 * !s2.equals(tableSettings.getVersion())) throw new
				 * _cls22(lookupString(0x665000f0));
				 */
				yahooConnectionThread.output.write(command);
				yahooConnectionThread.output.writeUTF(cookie);
				yahooConnectionThread.output.writeUTF(ycookie);
				yahooConnectionThread.output.writeUTF(agent);
				yahooConnectionThread.output.writeUTF(intl_code);
				yahooConnectionThread.flush();
			}
			else if (state == 1) {
				byte byte0 = datainputstream.readByte();
				if (byte0 != 0)
					throw new AuthenticationFailed(datainputstream.readUTF());
				String s3 = datainputstream.readUTF();
				setMyId(s3);
			}
			else {
				parseData(datainputstream.readByte(), datainputstream);
			}
			return;
		}
		catch (_cls128 _lcls128) {
			doError(_lcls128);
		}
		catch (AuthenticationFailed _lcls22) {
			new OkDialog(super.container, _lcls22.getMessage(), this);
			disconnected = true;
		}
		catch (OutOfMemoryError outofmemoryerror) {
			new OkDialog(super.container, lookupString(0x66500102), this);
			doError(outofmemoryerror);
			disconnected = true;
		}
		catch (Throwable throwable) {
			doError(throwable);
		}
		yahooConnectionThread.close();
	}

	protected void removeIgnored(String s1) {
		ignoreds.remove(s1);
	}

	public void responsePing(String s1) {
		send('H', s1);
	}

	public void send(char c, boolean flag) {
		if (isConnected()) {
			yahooConnectionThread.output.write(c);
			yahooConnectionThread.output.write(flag ? 1 : 0);
			yahooConnectionThread.flush();
		}
	}

	public void send(char c, int i1) {
		if (isConnected()) {
			yahooConnectionThread.output.write(c);
			yahooConnectionThread.output.write(i1);
			yahooConnectionThread.flush();
		}
	}

	public void send(char c, String s1) {
		if (isConnected()) {
			yahooConnectionThread.output.write(c);
			yahooConnectionThread.output.writeUTF(s1);
			yahooConnectionThread.flush();
		}
	}

	public void send(char c, String s1, int i1) {
		if (isConnected()) {
			yahooConnectionThread.output.write(c);
			yahooConnectionThread.output.writeUTF(s1);
			yahooConnectionThread.output.writeInt(i1);
			yahooConnectionThread.flush();
		}
	}

	/**
	 */
	public void send(char c, String s, int i, String s1) {
		if (isConnected()) {
			yahooConnectionThread.output.write(c);
			yahooConnectionThread.output.writeUTF(s);
			yahooConnectionThread.output.writeInt(i);
			yahooConnectionThread.output.writeUTF(s1);
			yahooConnectionThread.flush();
		}
	}

	public void send(char c, String s1, String s2) {
		if (isConnected()) {
			yahooConnectionThread.output.write(c);
			yahooConnectionThread.output.writeUTF(s1);
			yahooConnectionThread.output.writeUTF(s2);
			yahooConnectionThread.flush();
		}
	}

	public void sendError(Throwable throwable) {
		if (isConnected()) {
			yahooConnectionThread.output.write('E');
			PrintStream printstream = new PrintStream(
					yahooConnectionThread.output.output);
			if (!AbstractYahooApplet.isWindows && !AbstractYahooApplet.isMacOs
					&& AbstractYahooApplet.is1_0
					&& AbstractYahooApplet.isNestcape)
				printstream.println("No stack trace available");
			else
				throwable.printStackTrace(printstream);
			Runtime runtime = Runtime.getRuntime();
			printstream.println("total memory: " + runtime.totalMemory()
					+ "  free memory: " + runtime.freeMemory());
			printstream.println("java.version: "
					+ System.getProperty("java.version"));
			yahooConnectionThread.output.write(0);
			yahooConnectionThread.flush();
		}
	}

	public void sendPing(InformationFrame frame) {
		if (!frame.waitingPingResponse) {
			frame.waitingPingResponse = true;
			frame.btnPing.setEnabled(false);
			frame.lblPing.setCaption(lookupString(0x665000ff));
			frame.pingStartTime = System.currentTimeMillis();
			ping(frame.idName);
		}
	}

	public void sendStatus(String s1) {
		send('~', s1);
	}

	public void setImFromFriendsOnly(boolean flag) {
		chkImFromFriendsOnly.setChecked(flag);
		imFromFriendsOnly(flag);
	}

	void setMyId(String s1) {
		if (!closed) {
			myId = s1;
			lblConnectionStatus.setCaption(lookupString(0x66500125) + s1
					+ lookupString(0x66500115));
			connectionState = 0;
			Zh(false);
			notSystemTested = true;
		}
	}

	public boolean soundEnabled() {
		return "1".equals(getIdProperty("games_common_sound"));
	}

	int testCPU(int i1) {
		if (i1 < 2)
			return i1;
		return testCPU(i1 - 1) + testCPU(i1 - 2);
	}

	void updateFriends(Vector<String> vector) {
		if (!closed) {
			addIds(friends, null);
			friends = vector;
			addIds(vector, Color.blue);
		}
	}

	public void updateIdProperty(String key, String value) {
		if (key.equals("prowler_g"))
			chkShowLinkToYahooMessenger.setChecked(value.equals("1"));
		else if (key.equals("games_common_hidestar"))
			chkShowAllStarMemberShip.setChecked(value.equals("1") ^ true);
		else if (key.equals("games_common_profanity")) {
			chkProfanityNone.setChecked(value.equals("0"));
			chkProfanityWeak.setChecked(value.equals("1"));
			chkProfanityStrong.setChecked(value.equals("2"));
		}
	}

	public final void Vh(YahooTable table, String location) {
		AdEntry entry = new AdEntry(table, location);
		loadText(ad_info_filename + "?page=" + page_id + "&lang="
				+ super.intl_code + "&location=" + location + "&window="
				+ (table != null ? "table" : "ante"), 2, entry);
		ads.add(entry);
	}

	public void watch(String name) {
		send('P', name);
	}

	public void Wh(YahooTable table) {
		long time = System.currentTimeMillis();
		if (time - last_endad_time > minimum_endad_frequency
				&& !idPropertyContains(1)) {
			Vh(table, "END");
			last_endad_time = time;
		}
		else {
			AdEntry ad = new AdEntry(table, "END");
			ad.processed = true;
			ads.add(ad);
		}
	}

	void Zh(boolean flag) {
		idList.Qn(flag);
	}

}
