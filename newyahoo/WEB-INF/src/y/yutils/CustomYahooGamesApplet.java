// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import y.controls.IdFrame;
import y.controls.IdFrameHandler;
import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooListBox;
import y.controls.YahooPannel;
import y.dialogs.OkFrame;
import y.dialogs.YesNoDialog;
import y.dialogs.YesNoDialogHandler;
import y.net.YahooConnectionThread;
import y.utils.Id;
import y.utils.ImageReader;
import y.utils.YahooImage;
import y.ycontrols.TableCreator;
import y.ycontrols.TableList;
import y.ydialogs.InvitedFrame;

import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls56, _cls122, _cls37, _cls7,
// _cls150, _cls55, _cls38, _cls4,
// _cls174, _cls99, _cls42, _cls25,
// _cls142, _cls97, _cls49, _cls118,
// _cls156, _cls105, _cls19, _cls102,
// _cls168, _cls35, _cls133, _cls80,
// _cls78, _cls79, _cls82, _cls13,
// _cls87, _cls107

public class CustomYahooGamesApplet extends AbstractYahooGamesApplet implements
		YesNoDialogHandler, IdFrameHandler {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4559604370901277417L;
	static Color				ratingColor[]		= { new Color(200, 48, 48),
			new Color(248, 162, 96), new Color(152, 100, 200),
			new Color(96, 152, 200), new Color(96, 152, 96) };
	public Image				cyga_a;
	public Image				cyga_b;
	public Image				voiceImage;
	public Image				d;
	IdFrame						bootFrame;
	protected YahooButton		btnCreateTable;
	protected YahooButton		btnPlayNow;
	protected YahooCheckBox		chkSmallWindows;
	YahooControl				rightPannel;
	YahooComponent				j;
	YahooComponent				component;
	protected TableList			tableList;
	protected YahooControl		m;
	IdFrame						inviteFrame;
	public String				provisional;
	boolean						isladder;
	boolean						ratingmilestones;
	int							cyga_r[];
	int							minimun_tablead_frequency;
	int							tableColIndex;
	long						last_tablead_time;

	public CustomYahooGamesApplet() {
		bootFrame = null;
		rightPannel = new YahooControl();
		j = new YahooComponent();
		component = null;
		last_tablead_time = 0L;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean action(Event event, Object obj) {
		if (event.target == btnCreateTable)
			createTable();
		else if (event.target == btnPlayNow) {
			plawNow();
		}
		else {
			if (event.target == tableList) {
				int i1 = ((Vector<Table>) obj).elementAt(0).number;
				int j1 = ((Vector<Integer>) obj).elementAt(1).intValue();
				joinTable(i1);
				if (j1 != -1)
					sit(i1, j1);
				return true;
			}
			if (event.target == chkSmallWindows) {
				changeIdProperty("games_common_smallwindows", chkSmallWindows
						.isChecked());
				return true;
			}
		}
		return super.action(event, obj);
	}

	void actionInvite(int number, Id id) {
		if (!super.closed && !isIgnored(id.name)) {
			Table table1 = tables[number];
			table1.invited = true;
			if (table1.yahooTable != null) {
				new OkFrame(id.caption + lookupString(0x665000eb), processor,
						this);
				((YahooGamesTable) table1.yahooTable).notifyInvited();
			}
			else {
				new InvitedFrame(table1, id, this);
			}
		}
	}

	public void addComponent(YahooComponent component) {
		if (component == null)
			component = super.noPopupTables ? (YahooComponent) rightPannel : j;
		if (this.component != null)
			((AbstractYahooApplet) this).container
					.removeChildObject(this.component);
		this.component = component;
		if (super.noPopupTables)
			((AbstractYahooApplet) this).container.addChildObject(component, 1,
					1, 0, 0, true);
		else
			((AbstractYahooApplet) this).container.addChildObject(component,
					18, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public void close() {
		super.close();
		inviteFrame.setVisible(false);
		inviteFrame.dispose();
		if (bootFrame != null) {
			bootFrame.setVisible(false);
			bootFrame.dispose();
		}
		cyga_a.flush();
		voiceImage.flush();
		try {
			if (cyga_b != null)
				cyga_b.flush();
		}
		catch (SecurityException _ex) {
			_ex.printStackTrace();
		}
	}

	@Override
	void closeTable(int i1, String s1) {
		super.closeTable(i1, s1);
		if (!super.closed)
			new OkFrame(lookupString(0x6650010b) + i1
					+ lookupString(0x665000e8) + s1 + ".", processor, this);
	}

	@Override
	public YahooListBox createIdList(boolean hasTables, int i1) {
		int colCount = 1;
		if (ratingmilestones || isladder)
			colCount++;
		if (hasTables)
			colCount++;
		RatingSort ratingSort = new RatingSort(ratingmilestones, isladder, this);
		YahooListBox result = new YahooListBox(i1, !ratingmilestones
				&& !isladder ? 190 : 240, colCount, 0, ratingSort, hasTables,
				true, true, avatars.image);
		result.setCell(0, lookupString(0x665000f5));// Name
		if (ratingmilestones || isladder) {
			result.setProportionalColLeft(1, 0.5D);
			result.setCell(1, isladder ? lookupString(0x6650011c)
					: lookupString(0x665000e9));// Rtng
		}
		if (hasTables) {
			result.setProportionalColLeft(colCount - 1, 0.34999999999999998D);
			result.setCell(colCount - 1, lookupString(0x66500109));// Tbl
		}
		result.setBackColor(Color.white);
		result.setForeColor(Color.black);
		result.header.setForeColor(Color.black);
		for (int k1 = 1; k1 < colCount; k1++)
			result.it(k1, true);

		return result;
	}

	void createTable() {
		TableCreator tableCreator = super.tableSettings.createTableCreator();
		tableCreator.container = ((AbstractYahooApplet) this).container;
		tableCreator.applet = this;
		tableCreator.createDialog();
	}

	protected TableList createTableList() {
		TableList result = new TableList(this, lookupString(0x66500b80),
				lookupString(0x665000e2));// Who
		// is
		// Watching
		return result;
	}

	public void declineInvite(String s1, String s2) {
		send('V', s1, s2);
	}

	public boolean Dm() {
		return true;
	}

	@Override
	void doChangePublicFlags(Id id, int flags) {
		for (int j1 = 0; j1 < super.visibleTables.size(); j1++) {
			YahooGamesTable table = (YahooGamesTable) super.visibleTables
					.elementAt(j1);
			if (((YahooTable) table).table.ids.contains(id))
				table.ar(id, id.publicFlags, flags);
		}

		super.doChangePublicFlags(id, flags);
	}

	void doChangeRating(Id id, int rating) {
		if (!closed && (ratingmilestones || isladder)) {
			id.rating = rating;
			if (ratingmilestones) {
				int j1;
				if (rating < 0)
					j1 = ratingColor.length - 1;
				else
					for (j1 = 0; j1 < cyga_r.length; j1++)
						if (rating >= cyga_r[j1])
							break;

				Color color = rating != YahooUtils.provisional ? ratingColor[j1]
						: Color.gray;
				idList.putRatingSquare(color, id.idListItem, 0);
				super.inviteList.putRatingSquare(color, id.inviteListItem, 0);
				tableList.rx(id, color);
			}
			id.str = rating != YahooUtils.provisional ? Integer
					.toString(rating) : provisional;
			super.idList.setCell(id.str, id.idListItem, 1);
			super.inviteList.setCell(id.str, id.inviteListItem, 1);
			for (int k1 = 0; k1 < super.visibleTables.size(); k1++) {
				YahooGamesTable _lcls99 = (YahooGamesTable) super.visibleTables
						.elementAt(k1);
				if (((YahooTable) _lcls99).table.ids.contains(id))
					_lcls99.addToList(id);
			}

		}
	}

	@Override
	void doChangeTablePrivacy(int number, int value) {
		super.doChangeTablePrivacy(number, value);
		if (!super.closed) {
			Table table = super.tables[number];
			tableList.qx(super.tables[number], value);
			if (table.yahooTable != null)
				((YahooGamesTable) table.yahooTable).notifyChangePrivacy(value);
		}
	}

	@Override
	void doChangeTableProperties(int number,
			Hashtable<String, String> properties) {
		if (!super.closed)
			tableList.notifyChangeProperties(super.tables[number], properties);
		super.doChangeTableProperties(number, properties);
	}

	@Override
	void doDoneTable(int number) {
		if (!super.closed)
			tableList.notifyClose(super.tables[number]);
		super.doDoneTable(number);
	}

	void doGameRunning(int number, String message) {
		if (!super.closed) {
			new YesNoDialog(this, ((AbstractYahooApplet) this).container,
					message, this, new Integer(number)).show();
			evalJS("focus()");
		}
	}

	@Override
	void doIdEnter(String name, String caption) {
		super.doIdEnter(name, caption);
		if (!super.closed) {
			Id id = super.ids.get(name);
			id.inviteListItem = super.inviteList.add(caption, id);
			if (super.friends.indexOf(name) != -1)
				super.inviteList.setColor(Color.blue, id.inviteListItem);
		}
	}

	@Override
	void doIdExit(Id id) {
		super.doIdExit(id);
		if (!super.closed)
			super.inviteList.remove(id.inviteListItem);
	}

	@Override
	void doIdSit(int number, int index, String name) {
		super.doIdSit(number, index, name);
		if (!super.closed) {
			Table table = super.tables[number];
			Id id = super.ids.get(name);
			tableList.notifySit(table, index, id);
		}
	}

	@Override
	public void doJoinTable(Id id, int number) {
		super.doJoinTable(id, number);
		if (!super.closed) {
			Table table = tables[number];
			tableList.remove(table);
			super.idList.setCell(Integer.toString(number), id.idListItem,
					tableColIndex);
			super.inviteList.setCell(Integer.toString(number),
					id.inviteListItem, tableColIndex);
			if (id.name.equals(super.myId)) {
				((YahooGamesTable) table.yahooTable)
						.notifyChangePrivacy(table.privacy);
				if (table.invited)
					((YahooGamesTable) table.yahooTable).notifyInvited();
			}
		}
	}

	@Override
	void doLeaveTable(Id id, int number) {
		super.doLeaveTable(id, number);
		if (!super.closed) {
			tableList.remove(super.tables[number]);
			super.idList.setCell("", id.idListItem, tableColIndex);
			super.inviteList.setCell("", id.inviteListItem, tableColIndex);
		}
	}

	@Override
	public void doNewTable(int number, Hashtable<String, String> properties,
			long timer) {
		super.doNewTable(number, properties, timer);
		if (!super.closed)
			tableList.add(super.tables[number]);
	}

	public void Gm(String s1, int i1) {
		for (Enumeration<Id> enumeration = super.ids.elements(); enumeration
				.hasMoreElements();) {
			Id _lcls49 = enumeration.nextElement();
			if (_lcls49.caption.equalsIgnoreCase(s1)) {
				if (i1 >= 256) {
					boot(i1 - 256, _lcls49.name);
					return;
				}
				_lcls49.hasSentInviation = true;
				invite(i1, _lcls49.name);
				return;
			}
		}

	}

	@Override
	public void handleDisconnect(YahooConnectionThread connection,
			boolean success) {
		super.handleDisconnect(connection, success);
		if (!super.closed) {
			super.inviteList.clear();
			tableList.ex();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean handleEvent(Event event) {
		if (event.target == tableList && event.id == 701)
			information((String) event.arg);
		else
			return super.handleEvent(event);
		return true;
	}

	public void handleIdClick(String s, int table) {
		Gm(s, table);
	}

	public void handleNo(YesNoDialog _pcls7) {
	}

	public void handleYes(YesNoDialog _pcls7) {
		joinTable(((Integer) _pcls7.ynd_e).intValue());
	}

	@Override
	public void initProperties() {
		addComponent(null);
		minimun_tablead_frequency = 0x493e0;
		String s1 = getParameter("minimum_tablead_frequency");
		if (s1 != null)
			try {
				minimun_tablead_frequency = Integer.parseInt(s1);
			}
			catch (NumberFormatException _ex) {
			}
		lblConnectionStatus = new YahooLabel();
		ratingmilestones = getParameter("ratingmilestones") != null;
		isladder = getParameter("isladder") != null;
		super.initProperties();
		provisional = lookupString(0x665000f4);// provisional
		chkSmallWindows = new YahooCheckBox(lookupString(0x66500108));// Small
		// Windows
		boolean flag = super.tableSettings.getSitCount() == 2;
		tableColIndex = !ratingmilestones && !isladder ? 1 : 2;
		m = new YahooControl();
		rightPannel.addChildObject(m, 10, 3, 0, 1, 4, 0, 1, 4, 4, 0, 0);
		cyga_a = ImageReader
				.getImage("\027\"\n\uFF97\uCCCC\uFF99\u9999\uFF35\u98CB\uFFCB\uCBCB\uFF66\u6666\uFFCF\uFFFF\uFF00\000\uFF67\uCD99\uFF34\u6666\377\uFFFF\177\177\177\177\177\177U*U\177\177\177\177_\177o\001\000\\\177?\177\177l\177k\003\000t\177w\000\000z\177\032\000\000}\177\r\000@~_\002\000 \177?\003\000P\177W\000\000h\177/\000\000t\1775\000\000z\177\013\000\000}?\r\000@~\177\002\000 \177o\003\000P\177\177\001\000l\177{\000\000w?x\177\177}\177\000\000\000\177\177\177\177\177\037\000\000\000\000\000\000\000\000\000\0000\000\000\000\000\b\000\000\000\000\006\000*\002\000\003\000\000\002@\001\000\000\001`\000\000@\0000\000\000 \000\030\000A\020\000\f@ \b\000\006\000\000\004\000\003\000\000\002@\001\000\000\001`\000\000@\0000\000\000 \000\030\000\000\020\000\f\001\000\b\000\004\001\000\002\000\001\177\177\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000*U*\000\000\000\000 \000\020~\177#\000@\177\177\020\000t\003\000\b\000x\000\000\004\000\035\000\000\002\000\016\000\000\001 \003A@\000@C  \000h\000\000\020\0000\000\000\b\000:\000\000\004\000\f\000\000\002@\016\000\000\001\000\003\000@\000\020\003\000 \000\000\001\000\020\000\004\000\000\b@\007\000\000\002\000\177\177\177\000\000\000\000\000\000\177\177\177\177\177\037\000\000@\177\007\000\000@\177\003\000\000@\177\000\000\000`?\000\000\000p\037\000\000\000x\017\000\000\000|\007\000\000\000~\003\000\000\000\177\001\000\000@\177\000\000\000`?\000\000\000p\037\000\000\000x\017\000\002\004|\007\000~\001~\003\000\000\000\177\001\000\000@\177\000\000\000`?\000\000\000p?\000\000\000|\177\000\000\000\177\177\177\177\177\037");
		voiceImage = ImageReader
				.getImage("\f\f\002\uFF00\u3000\377\uFFFF{?wov]-[m6[m6[[mv;\177}\017");
		d = ImageReader
				.getImage("\020\020\005\uFF00\200\uFF80\u8080\uFFFF\uFFFF\uFF00\000\uFFC0\uC0C0D@ A\004B\024\020T P\002BJ$* (A \005\005\024\"H|\003a\007\000\016\000t\000H\007\000\000\000\000\000\000\004\000\b\000@\000\000\001@(\000\020\000\000\001\000\002\000\000\000 \000`\007\000\004\0000\000 \002\000\003?O<;1kA+\027.\025(\025\002U\002U*T*P+A6\001x\016p\177q\177\003\177\007x\017");
		// b = getYahooImage(super.logo_image_url_prefix + "/i/"
		// + intl_code + "/ga/sx/" + page_id + ".gif");
		btnCreateTable = new YahooButton(lookupString(0x66500106));// Create
		// Table
		btnPlayNow = new YahooButton(lookupString(0x665000f7));// Play Now
		super.inviteList = createIdList(true, 10);
		inviteFrame = new IdFrame(this, processor, this, super.inviteList,
				lookupString(0x66500180), true);// Invite
		rightPannel.addChildObject(idList, 10, 3, 0, 1, flag ? 4 : 3, 3,
				flag ? 1 : 2, 4, 0, 4, 4);
		YahooControl control = new YahooControl();
		control.setBackColor(Color.black);
		rightPannel.addChildObject(control, 10, 2, 0, 2, 1, 1, 2, 4, 4, 0, 4);
		YahooControl container = new YahooControl();
		container.addChildObject(lblConnectionStatus, 1, 1, 0, 0, true, 1, 1,
				1, 1);
		control.addChildObject(container, 1, 1, 0, 0, true, 1, 1, 1, 1);
		Color color = getYahooColor("yahoo.games.ante_tip_bg", 0xff9966);
		Color color1 = getYahooColor("yahoo.games.tablelist_fg", 255);
		lblConnectionStatus.setBackColor(color);
		lblConnectionStatus.setForeColor(color1);
		tableList = createTableList();
		rightPannel.addChildObject(tableList, 17, 1, 3, super.tableSettings
				.getSitCount() != 2 ? 3 : 2, 1, 1, 1, 4, 4, 0, 4);
		if (ratingmilestones) {
			YahooControl _lcls79_1 = new YahooControl();
			YahooPannel _lcls38 = new YahooPannel(lookupString(0x66500128),
					_lcls79_1, YahooTable.c, Color.white);
			_lcls79_1.setBackColor(Color.white);
			StringTokenizer stringtokenizer = new StringTokenizer(
					getParameter("ratingmilestones"), "|");
			cyga_r = new int[stringtokenizer.countTokens()];
			for (int i1 = 0; i1 < ratingColor.length; i1++) {
				Object obj = new YahooComponent(8, 8);
				((YahooComponent) obj).setBackColor(ratingColor[i1]);
				_lcls79_1.addChildObject(((YahooComponent) obj), 13, 0, 0, 1,
						1, 0, i1, 0, 4, 0, 0);
				try {
					cyga_r[i1] = Integer.parseInt(stringtokenizer.nextToken());
				}
				catch (NoSuchElementException _ex) {
					_ex.printStackTrace();
				}
				catch (NumberFormatException _ex) {
					_ex.printStackTrace();
				}
				if (i1 == 0)
					obj = new YahooLabel(cyga_r[i1] + "+");
				else
					obj = new YahooLabel(cyga_r[i1] + "-"
							+ (cyga_r[i1 - 1] - 1));
				_lcls79_1.addChildObject(((YahooComponent) obj), 17, 0, 0, 1,
						1, 1, i1);
			}

			Object obj1 = new YahooImage(null, 8, 8);
			((YahooComponent) obj1).setBackColor(Color.gray);
			_lcls79_1.addChildObject(((YahooComponent) obj1), 13, 0, 0, 1, 1,
					0, ratingColor.length, 0, 4, 0, 0);
			obj1 = new YahooLabel(lookupString(0x665000f4));
			_lcls79_1.addChildObject(((YahooComponent) obj1), 17, 0, 0, 1, 1,
					1, ratingColor.length);
			if (getParameter("small") == null && !isSmallWindows())
				m.addChildObject(_lcls38, 1, 1, 0, 3);
		}
		if (!isSmallWindows() && Dm())
			super.w.addChildObject(chkSmallWindows, 1, 1, 0, 0);
		m.addChildObject(super.btnExit, 15, 2, 0, 1, 1, 0, 10, 4, 0, 4, 0);
		m.addChildObject(btnPlayNow, 11, 2, 0, 1, 1, 0, 0);
		m.addChildObject(btnCreateTable, 11, 2, 3, 1, 1, 0, 1, 4, 0, 0, 0);
		m.addChildObject(new YahooComponent(), 1, 1, 0, 2, true);
		if (isKids()) {
			String s2 = getParameter("yahoo.games.ante_tips_image");
			if (s2 != null) {
				YahooControl _lcls79_2 = new YahooControl();
				_lcls79_2.addChildObject(new YahooImage(getYahooImage(s2), 450,
						150), 1, 1, 0, 0);
				rightPannel.addChildObject(_lcls79_2, 10, 1, 2, 2, 1, 1, 4, 0,
						4, 4, 4);
			}
		}
		else {
			super.btnOptions = new YahooButton(lookupString(0x66500d43));
			m
					.addChildObject(super.btnOptions, 15, 2, 0, 1, 1, 0, 9, 4,
							0, 0, 0);
			rightPannel.addChildObject(super.G, 10, 1, 2, 2, 1, 1, 4, 4, 4, 4,
					4);
		}
	}

	public void invite(int i1, String s1) {
		if (isConnected()) {
			super.yahooConnectionThread.output.write('+');
			super.yahooConnectionThread.output.write(i1);
			super.yahooConnectionThread.output.write('^');
			super.yahooConnectionThread.output.writeUTF(s1);
			super.yahooConnectionThread.flush();
		}
	}

	public void Jm(YahooTable _pcls174, boolean flag) {
		long time = System.currentTimeMillis();
		if (flag || time - last_tablead_time > minimun_tablead_frequency) {
			Vh(_pcls174, "TAB");
			last_tablead_time = time;
		}
	}

	public void makeTable(Hashtable<String, String> propertyes) {
		if (isConnected()) {
			super.yahooConnectionThread.output.write(78);
			super.yahooConnectionThread.output.writeShort(propertyes.size());
			String s2;
			for (Enumeration<String> enumeration = propertyes.keys(); enumeration
					.hasMoreElements(); super.yahooConnectionThread.output
					.writeUTF(s2)) {
				String s1 = enumeration.nextElement();
				s2 = propertyes.get(s1);
				super.yahooConnectionThread.output.writeUTF(s1);
			}

			super.yahooConnectionThread.flush();
		}
	}

	void openBootFrame(Table table) {
		YahooListBox _lcls107 = new YahooListBox();
		if (bootFrame != null) {
			bootFrame.setVisible(false);
			bootFrame.dispose();
		}
		bootFrame = new IdFrame(this, processor, this, _lcls107,
				lookupString(0x66500179), false);
		for (int i1 = 0; i1 < table.ids.size(); i1++) {
			Id id = table.ids.elementAt(i1);
			_lcls107.add(id.caption, id);
		}

		bootFrame.open(table.number + 256);
	}

	@Override
	public void parseData(int i1, DataInputStream datainputstream)
			throws IOException {
		switch (i1) {
		case 105: // 'i' invite
			int j1 = YahooUtils.readUnsignedByte(datainputstream);
			actionInvite(j1, getId(datainputstream));
			break;

		case 116: // 't' rating
			Id id = getId(datainputstream);
			int rating = datainputstream.readShort();
			if (rating == -1)
				rating = datainputstream.readInt();
			if (rating < 0 && isladder)
				rating += 0x10000;
			doChangeRating(id, rating);
			break;

		case 117: // 'u' game running
			int k1 = YahooUtils.readUnsignedByte(datainputstream);
			String s1 = datainputstream.readUTF();
			doGameRunning(k1, s1);
			break;

		default:
			super.parseData(i1, datainputstream);
			break;
		}
	}

	@Override
	public int ph() {
		return isSmallWindows() ? 6 : 9;
	}

	public void sit(int i1, int j1) {
		if (isConnected()) {
			super.yahooConnectionThread.output.write('+');
			super.yahooConnectionThread.output.write(i1);
			super.yahooConnectionThread.output.write('T');
			super.yahooConnectionThread.output.write((byte) j1);
			super.yahooConnectionThread.flush();
		}
	}

	@Override
	public void updateIdProperty(String key, String value) {
		if (key.equals("games_common_smallwindows"))
			chkSmallWindows.setChecked(value.equals("1"));
		else
			super.updateIdProperty(key, value);
	}

	@Override
	void Zh(boolean flag) {
		super.Zh(flag);
		super.inviteList.Qn(flag);
		tableList.Qn(flag);
	}

}
