// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.yutils;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import y.controls.ListItem;
import y.controls.YahooButton;
import y.controls.YahooCheckBox;
import y.controls.YahooComboBox;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.dialogs.YesNoDialog;
import y.dialogs.YesNoDialogHandler;
import y.utils.Formater;
import y.utils.Id;
import y.utils.YahooImage;
import y.ycontrols.Advertisement;
import y.ycontrols.TableControlContainer;
import y.ycontrols.TableTitle;
import y.ycontrols.YahooGamesTableListener;
import y.ycontrols._cls88;
import y.ydialogs.GameOverDialog;

import common.io.YData;

// Referenced classes of package y.po:
// _cls174, _cls145, _cls98, _cls56,
// _cls70, _cls74, _cls51, _cls135,
// _cls25, _cls11, _cls49, _cls132,
// _cls2, _cls118, _cls101, _cls110,
// _cls105, _cls95, _cls88, _cls168,
// _cls35, _cls80, _cls77, _cls78,
// _cls79, _cls13, _cls87, _cls107,
// _cls111

public abstract class YahooGamesTable extends YahooTable implements
		YesNoDialogHandler {

	static final boolean sq(String s1) {
		return s1.length() > 0 && s1.charAt(0) == '~';
	}

	public TableControlContainer	tableControlContainer;
	public YahooComboBox			cmbChangePrivacy;
	public YahooCheckBox			chkSound;
	public YahooButton				btnHelp;
	public YahooButton				btnAuto;
	public YahooButton				btnStartGame;
	public YahooButton				btnStand;
	public YahooButton				btnBoot;
	public YahooButton				btnInvite;
	public YahooButton				ygt_l;
	public Vector<YahooComponent>	yahooObjects;
	public Vector<YahooComponent>	objects;
	public YahooComponent			o;
	public YahooControl				gameArea;
	public YahooControl				q;
	public _cls88					ygt_r;
	private String					ygt_s[];
	private String					t[];
	private String					u[];
	private boolean					v[];
	private int						w;
	private String					x;
	public boolean					imHost;
	protected boolean				sitBegin[];
	public String					A;
	public Image					B;
	public boolean					ready;
	public boolean					D1;
	public int						E;
	public int						F;
	public Color					table_side_tabcolor_bg;
	public Color					table_side_tabcolor_fg;
	public YahooComponent			I;
	public YahooButton				btnResign;

	public YesNoDialog				resignDialog;

	public YahooGamesTable() {
		yahooObjects = new Vector<YahooComponent>();
		objects = new Vector<YahooComponent>();
		ready = false;
		D1 = false;
		E = 0;
		F = 0;
	}

	void _r() {
		if (super.mySitIndex == -1)
			Nq(!t[super.sitId.length].equals("") && !v[super.sitId.length] ? t[super.sitId.length]
					: u[super.sitId.length]);
		else
			Nq(!t[super.mySitIndex].equals("") && !v[super.sitId.length] ? t[super.mySitIndex]
					: u[super.mySitIndex]);
	}

	public void actionChat(Id id, String message) {
		if (!isIgnored(id.name) && !getApplet().isKids()) {
			String s3 = getCaption(id.name);
			logMessage((s3 != null ? s3 : id.caption) + ": " + message);
		}
	}

	@Override
	public void activateControls(int i1) {
		btnResign.setEnabled(false);
		btnAuto.setEnabled(false);
		updateSitButtons();
		Eq();
		_r();
		btnStand.setEnabled(false);
		refreshGame();
	}

	@Override
	void addId(Id id) {
		tableControlContainer.idList.add(id.caption, id);
		logMessage("*** " + id.caption + getApplet().lookupString(0x6650009f),
				Color.blue);
		if (id.name.equals(getApplet().myId))
			ready = true;
		else if (Lp() && ready)
			beep();
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.handleAddId(id);

		Vq(id);
		addToList(id);
		ar(id, 0, id.publicFlags);
	}

	public final void addObject(YahooComponent _pcls78, int i1) {
		if (i1 < 0)
			objects.add(_pcls78);
		else
			objects.insertElementAt(_pcls78, 0);
	}

	void addToList(Id id) {
		if (getYahooGamesApplet().ratingmilestones
				|| getYahooGamesApplet().isladder) {
			ListItem _lcls155 = tableControlContainer.idList.insert(id.caption,
					0);
			tableControlContainer.idList.setCell(id.str, _lcls155, 1);
		}
	}

	public final void addYahooObject(YahooComponent _pcls78) {
		yahooObjects.add(_pcls78);
	}

	public void Aq(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 3, 1, 0, 0, false, 2, 2, 2, 2);
	}

	protected void ar(Id id, int i1, int j1) {
		ListItem _lcls155 = tableControlContainer.idList.insert(id.caption, 0);
		getApplet().notifyChangeFlags(i1, j1, tableControlContainer.idList,
				_lcls155);
	}

	final void auto() {
		send('A');
	}

	public final void beep() {
		if (chkSound.isChecked())
			getYahooGamesApplet().beep();
	}

	public void bootAll() {
		if (!imHost)
			return;
		for (Id id : table.sit) {
			if (id.name != applet.myId)
				applet.boot(table.number, id.name);
		}
	}

	public void Bq(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 1, 1, 2, 3, false);
	}

	public boolean canSave() {
		return false;
	}

	public void cd(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 1, 1, 0, 0, false);
	}

	@Override
	public void close() {
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.handleClose();

		if (B != null)
			try {
				B.flush();
			}
			catch (SecurityException _ex) {
				_ex.printStackTrace();
			}

		if (tableControlContainer != null) {
			tableControlContainer.close();
			tableControlContainer = null;
		}

		super.close();
	}

	public void createArea() {
		gameArea = getGameArea();
		Color color = getApplet().getYahooColor("yahoo.games.table_panel_bg",
				0xcccc99);
		gameArea.nc(Color.black, color, new Color(0xcccc99),
				new Color(0x333300), new Color(0xffffcc), new Color(0x999966));
		tableControlContainer.nc(Color.black, new Color(0xffffcc), new Color(
				0xcccc99), new Color(0x333300), new Color(0xffffcc), new Color(
				0x999966));
		tableControlContainer.initialize();
		for (int i1 = 0; i1 < yahooObjects.size(); i1++)
			if (!((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
				tableControlContainer.tcc_b.addChildObject(yahooObjects
						.elementAt(i1), 10, 2, 2, 1, 1, i1, 0, 1, 2, 1, 2);
			else
				tableControlContainer.tcc_b.addChildObject(yahooObjects
						.elementAt(i1), 10, 2, 2, 1, 1, 0, i1, 1, 0, 1, 0);

		for (int j1 = 0; j1 < objects.size(); j1++)
			if (!((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
				tableControlContainer.tcc_c.addChildObject(objects
						.elementAt(j1), 10, 2, 2, 1, 1, j1, 0, 1, 2, 1, 2);
			else
				tableControlContainer.tcc_c.addChildObject(objects
						.elementAt(j1), 10, 2, 2, 1, 1, 0, j1, 1, 0, 1, 0);

		x = getApplet().lookupString(0x6650009d)
				+ (getAutomat() <= 2 ? "." : getApplet().lookupString(
						0x6650009c))
				+ (isRobot() ? getApplet().lookupString(0x6650008b) : "");
		Kq();
		Jq();
		q = Mp();
		if (q != null) {
			boolean flag = false;
			ygt_r = new _cls88(flag);
			if (!flag) {
				ygt_r.setBackColor(Color.white);
				ygt_r.setForeColor(Color.black);
			}
			q.addChildObject(ygt_r, 0, 0, true);
		}
	}

	public abstract TableControlContainer createTableControlContainer();

	public void dd(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 10, 2, 2, 1, 1, 0, 1);
	}

	@Override
	public void deactivateControls(int i1) {
		btnAuto.setEnabled(true);
		btnStand.setEnabled(true);
		updateSitButtons();
		Eq();
		refreshGame();
	}

	@Override
	protected void doChangeHost(String s1) {
		super.doChangeHost(s1);
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.handleUpdateTitle(s1);

		imHost = s1.equals(getMyId());
		if (imHost)
			logMessage(getApplet().lookupString(0x665000af), Color.blue);
		cmbChangePrivacy.setEnabled(imHost);
		btnInvite.setEnabled(imHost);
		Jq();
		updateSitButtons();
	}

	@Override
	void doVoice(String s1, boolean flag) {
		ListItem item = tableControlContainer.idList.insert(getId(s1).caption,
				0);
		if (flag)
			tableControlContainer.idList.putComponent(new YahooImage(
					getYahooGamesApplet().voiceImage), item, 0, 0);
		else
			tableControlContainer.idList.removeComponent(item, 0, 0);
	}

	void Eq() {
		for (int i1 = 0; i1 < getAutomat(); i1++) {
			setSit(i1);
			if (Qz(i1) > 1)
				Hq(i1);
			if (super.sits[i1] != null && super.game.hasPartner())
				super.sits[i1].sc_b.setForeColor(Wp(getRealIndex(i1)));
		}

	}

	public void exit() {
		boolean flag = true;
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			if (!((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.tg())
				flag = false;

		if (super.r.ad_b > 0) {
			D1 = true;
			flag = false;
		}
		if (flag)
			hide();
	}

	@Override
	public Advertisement getAdvertisement() {
		return tableControlContainer.advertisement;
	}

	public Color getBlackColor() {
		return Color.black;
	}

	protected abstract YahooControl getGameArea();

	@Override
	public int getRealIndex(int i1) {
		if (super.mySitIndex >= 0 && Kc())
			return (i1 + super.mySitIndex) % getAutomat();
		return i1;
	}

	@Override
	public int getRealSitIndex(int index) {
		if (super.mySitIndex >= 0 && Kc())
			return (index - super.mySitIndex + getAutomat()) % getAutomat();
		return index;
	}

	public final String getSitIdCaption(int i1) {
		return super.sitId[i1] != null ? super.sitId[i1].caption : "????";
	}

	public final YahooControl getTableControlContainer() {
		return tableControlContainer;
	}

	public Color getWhiteColor() {
		return Color.white;
	}

	public final CustomYahooGamesApplet getYahooGamesApplet() {
		return (CustomYahooGamesApplet) super.getApplet();
	}

	protected boolean gq(int i1) {
		return true;
	}

	public synchronized void handleNo(YesNoDialog _pcls4) {
		if (_pcls4 == resignDialog)
			resignDialog = null;
	}

	@Override
	public void handleStart() {
		super.handleStart();
		if (q != null)
			q.removeChildObject(ygt_r);
		Kq();
		if (!((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
			getApplet().evalJS("gamesGameOn()");
		if (getMySitIndex() != -1)
			btnResign.setEnabled(true);
	}

	@Override
	public void handleStop(YData data) {
		super.handleStop(data);
		if (q != null)
			q.addChildObject(ygt_r, 0, 0, true);
		Kq();
		if (((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
			getYahooGamesApplet().Jm(this, false);
		if (!((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
			getApplet().evalJS("gamesGameOff()");
	}

	@Override
	public void handleUpdateStatus(boolean flag) {
		Kq();
		hq();
	}

	public synchronized void handleYes(YesNoDialog _pcls4) {
		if (_pcls4 == resignDialog) {
			resignDialog = null;
			resign();
		}
	}

	public final void hide() {
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.handeHide();

		getYahooGamesApplet().leaveTable(super.table.number);
	}

	protected void hq() {
		btnStartGame.setEnabled(System.currentTimeMillis() >= getTime()
				&& super.game.allSitsStarted() && getMySitIndex() >= 0
				&& super.r.ad_b <= 0);
	}

	void Hq(int i1) {
		super.sits[getRealSitIndex(i1)].Pw(ygt_s[i1] != null ? ygt_s[i1] : "");
	}

	public final boolean imHost() {
		return imHost;
	}

	final boolean invited() {
		return super.table.invited;
	}

	public boolean isRobot() {
		return false;
	}

	@Override
	void jq(Id _pcls49) {
		Wq(_pcls49);
		ListItem _lcls155 = tableControlContainer.idList.insert(
				_pcls49.caption, 0);
		tableControlContainer.idList.remove(_lcls155);
		logMessage("*** " + _pcls49.caption
				+ getApplet().lookupString(0x665000a2), Color.blue);
		if (!_pcls49.name.equals(getApplet().myId) && Lp())
			beep();
	}

	void Jq() {
		boolean flag = w > 0
				&& (cmbChangePrivacy.getItemIndex() == 0 || invited() || imHost);
		Mq(-1, flag ? getApplet().lookupString(0x66500094) : "", false, false);
	}

	protected boolean Kc() {
		return true;
	}

	void Kq() {
		if (super.game.isRunning())
			Mq(-2, "", false, false);
		else if (getTime() >= 0L && getFinishedGameCount() == 0) {
			long l1 = getTime() - System.currentTimeMillis();
			if (l1 < 0L)
				l1 = 0L;
			Mq(-2, getApplet().lookupString(0x66501605)
					+ Formater.formatTimer(l1 + 999L), false, false);
		}
		else if (!super.game.allSitsStarted()) {
			Mq(-2, x, false, false);
		}
		else {
			for (int i1 = 0; i1 < getAutomat(); i1++)
				Mq(i1, sitBegin[i1] ? getApplet().lookupString(0x665000b2)
						: getApplet().lookupString(0x665000a0), false, false);

		}
	}

	@Override
	public void logMessage(String s1, Color color) {
		tableControlContainer.tcc_f.append(s1, color);
	}

	protected boolean Lp() {
		return true;
	}

	public final void Lq(int i1, String s1) {
		Mq(i1, s1, true, false);
	}

	public YahooControl Mp() {
		return gameArea;
	}

	void Mq(int i1, String s1, boolean flag, boolean flag1) {
		String as[] = flag ? u : t;
		if (i1 == -1 || i1 == -3) {
			as[super.sitId.length] = s1;
			if (flag1)
				v[super.sitId.length] = s1.equals("") ^ true;
		}
		if (i1 == -2 || i1 == -3) {
			for (int j1 = 0; j1 < super.sitId.length; j1++)
				Mq(j1, s1, flag, flag1);

		}
		if (i1 >= 0) {
			as[i1] = s1;
			if (flag1)
				v[i1] = s1.equals("") ^ true;
		}
		_r();
	}

	void notifyChangePrivacy(int i1) {
		if (i1 < cmbChangePrivacy.cn())
			cmbChangePrivacy.fn(i1);
		if (imHost)
			if (i1 == 0)
				logMessage(getApplet().lookupString(0x6650009b), Color.blue);
			else if (i1 == 1)
				logMessage(getApplet().lookupString(0x665000ac), Color.blue);
			else if (i1 == 2)
				logMessage(getApplet().lookupString(0x665000a3), Color.blue);
		Jq();
		updateSitButtons();
	}

	void notifyInvited() {
		Jq();
		updateSitButtons();
	}

	public final void Nq(String s1) {
		if (ygt_r != null && !super.game.isRunning())
			ygt_r.Ro(s1);
		tableControlContainer.lblStatus.setCaption(s1);
	}

	public int Op() {
		return isSmallWidows() ? 3 : 4;
	}

	void openInviteFrame() {
		getYahooGamesApplet().inviteFrame.open(super.table.number);
	}

	@Override
	public void parseData(byte byte0, DataInputStream datainputstream)
			throws IOException {
		switch (byte0) {
		case 99: // 'c': chat
			Id id = getId(datainputstream);
			String message = datainputstream.readUTF();
			actionChat(id, message);
			break;

		case 91: // '[': resign
			getGame().doResign(datainputstream.readByte());
			break;

		case 120: // 'x': cancel response
			boolean saved = datainputstream.readBoolean();
			String name = datainputstream.readUTF();
			getGame().doStop();
			if (name.length() > 0)
				logMessage(getApplet().lookupString(0x665000b5)
						+ (saved ? getApplet().lookupString(0x665000b6)
								: getApplet().lookupString(0x66500096)),
						Color.blue);
			if (name.equals(getMyId()))
				exit();
			break;

		case 54: // '6': stop
			getGame().stop();
			break;

		case 57: // '9': sit begin
			byte byte1 = datainputstream.readByte();
			if (byte1 == -1) {
				for (int i1 = 0; i1 < getAutomat(); i1++)
					sitBegin[i1] = false;

			}
			else {
				sitBegin[byte1] = true;
			}
			Kq();
			break;

		default:
			super.parseData(byte0, datainputstream);
			break;
		}
	}

	public YahooComponent Pp() {
		return ygt_l;
	}

	public final void Pq(YahooComponent _pcls78) {
		if (I == null) {
			getApplet().Wh(this);
			I = _pcls78;
		}
	}

	public void processEvent(Event event) {
		if (event.id == Event.ACTION_EVENT) {
			if (event.target == cmbChangePrivacy) {
				changePrivacy((byte) cmbChangePrivacy.getItemIndex());
				return;
			}
			if (event.target == btnHelp) {
				showDocument(
						getApplet().isKids() ? "http://saddam.virtuaserver.com.br/help/us/yahooligans/games/"
								: "http://"
										+ ((AbstractYahooApplet) getApplet()).intl_host
										+ "/games/helphub.html?page="
										+ getApplet().page_id, "_blank");
				return;
			}
			if (event.target == btnStartGame) {
				startgame();
				return;
			}
			if (event.target == btnStand) {
				stand();
				return;
			}
			if (event.target == btnInvite) {
				openInviteFrame();
				return;
			}
			if (event.target == btnBoot) {
				getYahooGamesApplet().openBootFrame(super.table);
				return;
			}
			if (event.target == btnAuto) {
				auto();
				return;
			}
		}
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			if (((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.handleEvent(event))
				return;

	}

	@Override
	public void processMessages() {
		super.processMessages();
		if (getTime() > 0L && !super.game.isRunning())
			Kq();
		if (E > 0) {
			E--;
			if (F == 0) {
				E = F;
				getYahooGamesApplet().Jm(this, false);
			}
		}
	}

	public final void resign() {
		send('{', (byte) getMySitIndex());
	}

	public final void resign(int i1) {
		send('{', (byte) i1);
	}

	@Override
	public void rq() {
		if (((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
			addSitParser(new TableTitle(this));
		else
			addSitParser(new TableControler(this));
		super.rq();
		tableControlContainer = createTableControlContainer();
		int i1 = getAutomat();
		w = i1;
		ygt_s = new String[i1];
		t = new String[i1 + 1];
		u = new String[i1 + 1];
		v = new boolean[i1 + 1];
		sitBegin = new boolean[i1];
		for (int j1 = 0; j1 < i1 + 1; j1++) {
			u[j1] = "";
			t[j1] = "";
		}

		String s1 = applet.getParameter("adv_interval");
		if (s1 != null)
			try {
				E = F = Integer.parseInt(s1) / 15;
			}
			catch (NumberFormatException _ex) {
			}
		btnResign = new YahooButton("Resign");
		cmbChangePrivacy = new YahooComboBox(getTimerHandler());
		cmbChangePrivacy.setText(getApplet().lookupString(0x665000a7));
		cmbChangePrivacy.setText(getApplet().lookupString(0x66500090));
		if (applet.getParameter("isladder") == null)
			cmbChangePrivacy.setText(getApplet().lookupString(0x66500092));
		cmbChangePrivacy.setEnabled(false);
		chkSound = new YahooCheckBox(getApplet().lookupString(0x665000b9),
				null, getApplet().soundEnabled());
		btnHelp = new YahooButton(getApplet().lookupString(0x665000a1));
		btnStartGame = new YahooButton(getApplet().lookupString(0x665000a4));
		btnAuto = new YahooButton(getApplet().lookupString(0x665000a8));
		btnStand = new YahooButton(
				((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables ? getApplet()
						.lookupString(0x66500097)
						: getApplet().lookupString(0x665018cd));
		btnBoot = new YahooButton(getApplet().lookupString(0x665000aa));
		ygt_l = new YahooButton(getApplet().lookupString(0x6650009e));
		btnInvite = new YahooButton(getApplet().lookupString(0x665000b7));
		btnStand.setEnabled(false);
		btnInvite.setEnabled(false);
		btnStartGame.setEnabled(false);
		btnAuto.setEnabled(false);
		table_side_tabcolor_bg = getApplet().getYahooColor(
				"yahoo.games.table_side_tabcolor_bg", YahooTable.c.getRGB());
		table_side_tabcolor_fg = getApplet().getYahooColor(
				"yahoo.games.table_side_tabcolor_fg", 0xffffff);
		createArea();
	}

	@Override
	public void Rq(int i1, String s1) {
		super.Rq(i1, s1);
		updateSitButton(i1);
		setSit(i1);
		w--;
		Kq();
		if (w == 0) {
			Jq();
			btnAuto.setEnabled(false);
		}
		_r();
		hq();
	}

	@Override
	public void setSit(int i1) {
		super.setSit(i1);
		boolean flag = super.sitId[i1] == null;
		super.sits[getRealSitIndex(i1)].sc_c
				.qo(!flag
						&& (!sq(super.sitId[i1].name) || getMySitIndex() != -1) ? 1
						: 0);
		if (!flag && sq(super.sitId[i1].name))
			super.sits[getRealSitIndex(i1)].image
					.setImage(getYahooGamesApplet().cyga_a);
	}

	@Override
	public void setSit1(int index) {
		super.setSit1(index);
		if (super.mySitIndex != -1)
			btnAuto.setEnabled(true);
		for (int j1 = 0; j1 < super.sitParsers.size(); j1++)
			((YahooGamesTableListener) super.sitParsers.elementAt(j1))
					.handleStand(index);

		updateSitButton(index);
		setSit(index);
		w++;
		Jq();
		hq();
		Kq();
	}

	public final void showQuickMessage(String message) {
		Pq(new YahooLabel(message));
	}

	public void sit(int i1) {
		send('T', (byte) i1);
	}

	public final boolean soundEnabled() {
		return chkSound != null && chkSound.isChecked();
	}

	public boolean Sp() {
		return true;
	}

	public void stand() {
		boolean flag = true;
		for (int i1 = 0; i1 < super.sitParsers.size(); i1++)
			if (!((YahooGamesTableListener) super.sitParsers.elementAt(i1))
					.ug())
				flag = false;

		if (flag) {
			send('D');
		}
	}

	public boolean Tp() {
		return true;
	}

	@Override
	public void Up() {
		if (D1) {
			D1 = false;
			exit();
		}
		hq();
	}

	void updateSitButton(int sitIndex) {
		super.sits[getRealSitIndex(sitIndex)].btnSit
				.setEnabled((cmbChangePrivacy.getItemIndex() == 0 || invited() || imHost)
						&& super.mySitIndex == -1
						&& (super.sitId[sitIndex] == null || sq(super.sitId[sitIndex].name))
						&& gq(sitIndex));
	}

	public void updateSitButtons() {
		for (int i1 = 0; i1 < getAutomat(); i1++)
			updateSitButton(i1);

	}

	public void uq(YahooControl _pcls79, YahooControl _pcls79_1) {
		_pcls79.addChildObject(_pcls79_1, 2, 2, 1, 1, false);
	}

	@Override
	public void Uq(AdEntry _pcls11) {
		if (tableControlContainer == null)
			return;
		if (_pcls11.location.equals("TAB")) {
			tableControlContainer.image.setImage(_pcls11.getImage());
			if (B != null)
				try {
					B.flush();
				}
				catch (SecurityException _ex) {
					_ex.printStackTrace();
				}
			B = _pcls11.getImage();
			A = _pcls11.Y();
		}
		else if (_pcls11.location.equals("END")) {
			YahooComponent _lcls78 = I;
			I = null;
			if (_pcls11.getImage() == null || _pcls11.Y() == null)
				new GameOverDialog(tableControlContainer, super.applet,
						_lcls78, null, null);
			else if (_pcls11.getHeight() == 31 && _pcls11.getWidth() == 88) {
				new GameOverDialog(tableControlContainer, super.applet,
						_lcls78, _pcls11.getImage(), _pcls11.Y());
			}
			else {
				YahooControl _lcls79 = new YahooControl();
				YahooLabel _lcls87 = new YahooLabel(getApplet().lookupString(
						0x6650111f));
				_lcls87.setBackColor(Color.white);
				_lcls79.addChildObject(_lcls87, 1, 1, 0, 0, false, 1, 1, 0, 1);
				_lcls79.addChildObject(_lcls78, 1, 1, 0, 1, false, 0, 1, 1, 1);
				_lcls79.setBackColor(Color.black);
				_lcls78.setBackColor(Color.white);
				YahooControl _lcls79_1 = new YahooControl();
				_lcls79_1.addChildObject(_lcls79, 1, 1, 0, 0);
				tableControlContainer.advertisement.Dp(_lcls79_1);
				tableControlContainer.advertisement.Ap(_pcls11.Y(), _pcls11
						.getImage());
				hq();
			}
		}
	}

	public boolean Vp() {
		return false;
	}

	public void vq(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 1, 1, 1, 3, true);
	}

	protected void Vq(Id _pcls49) {
	}

	@Override
	public Color Wp(int i1) {
		return super.game.hasPartner() ? i1 % 2 != 0 ? Color.blue.darker()
				: Color.red.darker() : Color.black;
	}

	public void wq(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 1, 1, 0, 2, true);
	}

	protected void Wq(Id _pcls49) {
	}

	public void xq(YahooControl _pcls79, YahooComponent _pcls78) {
		if (!((AbstractYahooGamesApplet) getYahooGamesApplet()).noPopupTables)
			_pcls79.addChildObject(_pcls78, 17, 2, 2, 2, 1, 1, 3);
		else
			_pcls79.addChildObject(_pcls78, 10, 3, 0, 1, 3, 0, 1);
	}

	public void yq(YahooControl _pcls79) {
	}

	public void zq(YahooControl _pcls79, YahooComponent _pcls78) {
		_pcls79.addChildObject(_pcls78, 10, 2, 2, 1, 1, 0, 1);
	}
}
