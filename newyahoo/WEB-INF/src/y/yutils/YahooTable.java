package y.yutils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import y.net.YahooConnectionThread;
import y.utils.Id;
import y.utils.TimerEngine;
import y.utils.YahooImageList;
import y.ycontrols.Advertisement;
import y.ycontrols.SitContainer;

import common.io.YData;
import common.yutils.Game;
import common.yutils.GameHandler;
import common.yutils.GameHistory;

public abstract class YahooTable implements GameHandler {

	public static final Color			c	= new Color(26265);
	public Table						table;
	public AbstractYahooGamesApplet		applet;
	public YahooConnectionThread		yahooConnectionThread;
	public Game							game;
	public int							finishedGameCount;
	boolean								smallWindows;
	public long							deltaTime;
	public long							time;
	public boolean						ticking;
	public SitContainer					sits[];
	protected int						avatars[];
	public Id							sitId[];
	public int							mySitIndex;
	int									avatarCounter;
	public Advertisement				r;
	public boolean						s;
	public Vector<YahooTableListener>	sitParsers;
	public int							avatar;
	protected GameHistory				gameHistory;

	public YahooTable() {
		finishedGameCount = 0;
		mySitIndex = -1;
		avatarCounter = 0;
		s = false;
		sitParsers = new Vector<YahooTableListener>(1);
		gameHistory = new GameHistory();
	}

	public void activateControls(int i1) {
	}

	abstract void addId(Id _pcls49);

	public void addSitParser(YahooTableListener _pcls70) {
		sitParsers.add(_pcls70);
	}

	public void changeAvatar(int index, boolean previous) {
		if (mySitIndex >= 0 && getRealSitIndex(mySitIndex) == index) {
			int avatar = previous ? previousAvatar() : nextAvatar();
			avatarCounter = 533;
			sits[index].image
					.setImage(((YahooImageList) getApplet().avatars).image[avatar]);
		}
	}

	final void changePrivacy(int i1) {
		send('%', (byte) i1);
	}

	public void close() {
		if (sits != null) {
			for (SitContainer sit : sits)
				sit.close();
			sits = null;
		}
		if (game != null) {
			game.close();
			game = null;
		}
		sitId = null;
		avatars = null;
	}

	public void deactivateControls(int i1) {
	}

	void doChangeAvatar(int i1, int j1) {
		avatars[i1] = j1;
		sits[getRealSitIndex(i1)].image
				.setImage(((YahooImageList) getApplet().avatars).image[j1]);
	}

	protected void doChangeHost(String s1) {
	}

	/**
	 * @param sitIndex
	 * @param flag
	 */
	public void doFocus(byte sitIndex, boolean flag) {
		sits[getRealSitIndex(sitIndex)].sc_b
				.setForeColor(flag ? Color.lightGray : Color.black);
	}

	public void doSit(int sitIndex, int avatar, Id id) {
		sitId[sitIndex] = id;
		doChangeAvatar(sitIndex, avatar);
		game.setSitState(sitIndex, true);
		if (sitId[sitIndex].name.equals(getMyId())) {
			mySitIndex = sitIndex;
			this.avatar = avatar;
			deactivateControls(sitIndex);
		}
		Rq(sitIndex, sitId[sitIndex].name);
	}

	public void doStand(byte sitIndex) {
		sitId[sitIndex] = null;
		game.setSitState(sitIndex, false);
		if (sitIndex == mySitIndex) {
			mySitIndex = -1;
			activateControls(sitIndex);
		}
		setSit1(sitIndex);
	}

	public void doUpdate(DataInputStream datainputstream) throws IOException {
		game.read(datainputstream);
		finishedGameCount = datainputstream.readInt();
		boolean flag = datainputstream.readBoolean();
		if (flag)
			gameHistory.read(datainputstream);
		game.refresh();
		time -= deltaTime;
	}

	void doVoice(String s1, boolean flag) {
	}

	public abstract Advertisement getAdvertisement();

	public AbstractYahooGamesApplet getApplet() {
		return applet;
	}

	public final int getAutomat() {
		return table.automat;
	}

	public Color getBackColor() {
		return Color.white;
	}

	protected String getCaption(String s1) {
		return null;
	}

	public final int getFinishedGameCount() {
		return finishedGameCount;
	}

	public final Game getGame() {
		return game;
	}

	protected final Id getId(DataInputStream datainputstream)
			throws IOException {
		return getId(datainputstream.readUTF());
	}

	public final Id getId(String s1) {
		return applet.ids.get(s1);
	}

	public final String getLabel() {
		return applet.label;
	}

	public final String getMyId() {
		return applet.myId;
	}

	public final int getMySitIndex() {
		return mySitIndex;
	}

	public final int getNumber() {
		return table.number;
	}

	public final Hashtable<String, String> getPropertyes() {
		return table.properties;
	}

	public int getRealIndex(int index) {
		return index;
	}

	public int getRealSitIndex(int i1) {
		return i1;
	}

	public final long getTime() {
		return table.time;
	}

	public TimerEngine getTimerHandler() {
		return applet.getTimerHandler();
	}

	public final void handleInformation(int index) {
		int index1 = getRealIndex(index);
		if (sitId[index1] != null)
			information(sitId[index1].name);
	}

	public void handleStart() {
		for (int i1 = 0; i1 < sitParsers.size(); i1++)
			sitParsers.elementAt(i1).handleStart();
	}

	public void handleStartTick(int i1) {
		time = System.currentTimeMillis() + i1;
		ticking = true;
	}

	public void handleStop(YData data) {
		finishedGameCount++;
		for (int i1 = 0; i1 < sitParsers.size(); i1++)
			sitParsers.elementAt(i1).handleStop(data);
	}

	public void handleStopTick() {
		ticking = false;
	}

	public void handleUpdateStatus(boolean flag) {
	}

	public final void information(String s1) {
		applet.information(s1);
	}

	final boolean isIgnored(String s1) {
		return applet.isIgnored(s1);
	}

	public boolean isSmallWidows() {
		return smallWindows;
	}

	public final boolean isTicking() {
		return ticking;
	}

	abstract void jq(Id _pcls49);

	public int Jz(int i1) {
		return 23;
	}

	public void Kd(int i1) {
		for (int j1 = 0; j1 < sitParsers.size(); j1++)
			sitParsers.elementAt(j1).nm(i1);

	}

	public int Kz(int i1) {
		return 34;
	}

	public final void logMessage(String s1) {
		logMessage(s1, null);
	}

	public abstract void logMessage(String s1, Color color);

	public boolean Mz() {
		return true;
	}

	int nextAvatar() {
		avatar = (avatar + 1)
				% (45 - (getApplet().idPropertyContains(4L) ? 0 : 11));
		return avatar;
	}

	public void notifyChangeProperties(Hashtable<String, String> hashtable) {
		for (int i1 = 0; i1 < sitParsers.size(); i1++)
			sitParsers.elementAt(i1).handleSetProperties(hashtable);

	}

	protected Color Nz(int i1) {
		return null;
	}

	public void oA() {
		for (int i1 = 0; i1 < sitParsers.size(); i1++)
			sitParsers.elementAt(i1).handleCreateFrame();

	}

	@SuppressWarnings("deprecation")
	public Font Oz(int i1) {
		return new Font(Toolkit.getDefaultToolkit().getFontList()[0], 1, 14);
	}

	public void parseData(byte byte0, DataInputStream datainputstream)
			throws IOException {
		for (int i1 = 0; i1 < sitParsers.size(); i1++)
			if (sitParsers.elementAt(i1)
					.handleParseData(byte0, datainputstream))
				return;

		boolean flag;

		switch (byte0) {
		case 97: // 'a': update game
			doUpdate(datainputstream);
			break;

		case 98: // 'b': begin game
			game.setRunning(true);
			break;

		case 100: // 'd': stand
			byte byte1 = datainputstream.readByte();
			doStand(byte1);
			break;

		case 116: // 't': sit
			int j1 = datainputstream.readByte();
			int k1 = datainputstream.readByte();
			Id id = getId(datainputstream);
			doSit(j1, k1, id);
			break;

		case 48: // '0': time decremet
			deltaTime = datainputstream.readLong();
			break;

		case 49: // '1': table message
			logMessage("*** " + datainputstream.readUTF(), Color.blue);
			break;

		case 50: // '2': focus
			byte sitIndex = datainputstream.readByte();
			flag = datainputstream.readBoolean();
			doFocus(sitIndex, flag);
			break;

		case 52: // '4': voice
			flag = datainputstream.readBoolean();
			String s2 = datainputstream.readUTF();
			doVoice(s2, flag);
			break;

		case 53: // '5': host
			String s1 = datainputstream.readUTF();
			doChangeHost(s1);
			break;

		case 56: // '8': change avatar
			byte byte2 = datainputstream.readByte();
			byte byte3 = datainputstream.readByte();
			doChangeAvatar(byte2, byte3);
			break;

		default:
			throw new IllegalArgumentException("YOG protocol error: " + byte0
					+ " " + (char) byte0);
		}
	}

	int previousAvatar() {
		avatar = (avatar - 1)
				% (45 - (getApplet().idPropertyContains(4L) ? 0 : 11));
		return avatar;
	}

	public void processMessages() {
		if (avatarCounter > 0) {
			avatarCounter--;
			if (avatarCounter == 0)
				applet.changeAvatar(avatar);
		}
		if (ticking && game.isRunning()) {
			int i1 = (int) (time - System.currentTimeMillis());
			Kd(Math.max(i1, 0));
			if (i1 <= 0) {
				ticking = false;
				send('(');
			}
		}
		if (r.ad_b > 0) {
			s = true;
			r.ad_b -= 15;
			r.Bp(r.ad_b);
			if (r.ad_b <= 0) {
				r.Cp();
				Up();
			}
		}
		for (int j1 = 0; j1 < sitParsers.size(); j1++)
			sitParsers.elementAt(j1).handleIterate();

	}

	public Color Pz(int i1) {
		return null;
	}

	public int Qz(int i1) {
		return 1;
	}

	protected final void refreshGame() {
		long l1 = time;
		int i1 = finishedGameCount;
		game.refresh();
		time = l1;
		finishedGameCount = i1;
	}

	public void rq() {
		for (int i1 = 0; i1 < sitParsers.size(); i1++)
			sitParsers.elementAt(i1).im();

	}

	public void Rq(int i1, String s1) {
		setSit(i1);
	}

	public int Rz(int i1) {
		return 80;
	}

	public final void send(char c1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, boolean flag, int j1, int k1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeBoolean(flag);
		yahooConnectionThread.output.writeInt(j1);
		yahooConnectionThread.output.writeInt(k1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, byte byte0) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.write(byte0);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, byte byte0, byte byte1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.write(byte0);
		yahooConnectionThread.output.write(byte1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, int i1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeInt(i1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, int i1, float j1, float k1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeInt(i1);
		yahooConnectionThread.output.writeFloat(j1);
		yahooConnectionThread.output.writeFloat(k1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, int i1, int j1, byte byte0, YData _pcls111,
			YData _pcls111_1, YData _pcls111_2) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeInt(i1);
		yahooConnectionThread.output.writeInt(j1);
		yahooConnectionThread.output.write(byte0);
		yahooConnectionThread.output.writeData(_pcls111);
		yahooConnectionThread.output.writeData(_pcls111_1);
		yahooConnectionThread.output.writeData(_pcls111_2);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, int i1, int j1, int k1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeInt(i1);
		yahooConnectionThread.output.writeInt(j1);
		yahooConnectionThread.output.writeInt(k1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, int i1, YData _pcls111) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeInt(i1);
		yahooConnectionThread.output.writeData(_pcls111);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, String s1) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeUTF(s1);
		yahooConnectionThread.flush();
		return;
	}

	public final void send(char c1, YData _pcls111) {
		if (yahooConnectionThread == null) {
			return;
		}
		writeTableHeader();
		yahooConnectionThread.output.write(c1);
		yahooConnectionThread.output.writeData(_pcls111);
		yahooConnectionThread.flush();
		return;
	}

	void setSit(int i1) {
		int j1 = getRealSitIndex(i1);
		if (sitId[i1] == null) {
			sits[j1].sc_b.setBackColor(getBackColor());
			sits[j1].setText("");
			sits[j1].image.setImage(null);
		}
		else {
			sits[j1].sc_b.setBackColor(null);
			sits[j1].setText(sitId[i1].caption);
			doChangeAvatar(i1, avatars[i1]);
		}
	}

	public void setSit1(int index) {
		setSit(index);
	}

	void setup(Table _pcls118, AbstractYahooGamesApplet _pcls56,
			YahooConnectionThread _pcls4) {
		yahooConnectionThread = _pcls4;
		smallWindows = _pcls56.isSmallWindows();
		table = _pcls118;
		applet = _pcls56;
		game = _pcls56.tableSettings.makeGame(_pcls118.properties, this);
		int i1 = getAutomat();
		sits = new SitContainer[i1];
		sitId = new Id[i1];
		avatars = new int[i1];
		rq();
		r = getAdvertisement();
		oA();
	}

	public final void showDocument(String s1, String s2) {
		applet.showDocument(s1, s2);
	}

	public final void startgame() {
		send('B');
	}

	public SitContainer Sz(int i1) {
		Color color = Pz(i1);
		if (color == null)
			color = Wp(i1);
		Color color1 = Nz(i1);
		if (color1 == null) {
			color1 = color.equals(Color.black) ? Color.red : color.brighter()
					.brighter();
			if (Pz(i1) == null)
				color = color.darker();
		}
		return sits[i1] = new SitContainer(color, color1, Qz(i1), Rz(i1), this,
				i1, Mz());
	}

	public void Up() {
	}

	public abstract void Uq(AdEntry _pcls11);

	protected Color Wp(int i1) {
		return Color.black;
	}

	void writeTableHeader() {
		yahooConnectionThread.output.write(43);
		yahooConnectionThread.output.write(table.number);
	}

}
