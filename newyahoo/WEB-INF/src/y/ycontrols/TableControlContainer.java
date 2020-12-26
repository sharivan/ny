// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.awt.Color;
import java.awt.Event;

import y.controls.ListItem;
import y.controls.YahooButton;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooLabel;
import y.controls.YahooListBox;
import y.controls.YahooPannel;
import y.controls.YahooTextBox;
import y.dialogs.YesNoDialog;
import y.utils.Id;
import y.utils.YahooImage;
import y.yutils.AbstractYahooGamesApplet;
import y.yutils.YahooGamesTable;
import y.yutils.YahooTable;

import common.yutils.YahooUtils;

// Referenced classes of package y.po:
// _cls79, _cls38, _cls98, _cls56,
// _cls174, _cls74, _cls99, _cls135,
// _cls49, _cls132, _cls105, _cls168,
// _cls35, _cls80, _cls78, _cls13,
// _cls87, _cls107, _cls155, _cls136

public class TableControlContainer extends YahooControl {
	public YahooGamesTable	table;
	public YahooControl		tcc_b;
	public YahooControl		tcc_c;
	public YahooLabel		lblStatus;
	public YahooListBox		idList;
	public YahooListBox		tcc_f;
	public YahooTextBox		txtChat;
	public YahooButton		btnVoice;
	public YahooImage		image;
	public YahooControl		j;
	public Advertisement	advertisement;

	public TableControlContainer(YahooGamesTable _pcls99) {
		table = _pcls99;
	}

	public void close() {
		table = null;
	}

	public boolean createControlPannel(YahooControl container, int[] counter) {
		boolean flag = table.isSmallWidows();
		lblStatus = new YahooLabel();
		idList = table.getApplet().createIdList(false, 2);
		tcc_f = new YahooListBox(table.Op(), 200, 1, -1, null, false, false,
				true, null);
		tcc_f.ht(true);
		YahooControl _lcls79 = new YahooControl();
		table.vq(this, _lcls79);
		idList.setBackColor(Color.white);
		tcc_f.setBackColor(Color.white);
		lblStatus.setBackColor(new Color(0x999966));
		lblStatus.setForeColor(Color.white);
		lblStatus.Oo(0, 1, 12);
		YahooComponent component;
		if (table.getApplet().isKids()
				&& !table.Vp()
				|| !((AbstractYahooGamesApplet) table.getYahooGamesApplet()).noPopupTables) {
			Color color = new Color(0xffcc66);
			lblStatus.setBackColor(color);
			lblStatus.setForeColor(Color.black);
			component = new YahooComponent();
			component.setBackColor(color);
		}
		else {
			YahooControl _lcls79_1 = new YahooControl();
			txtChat = new YahooTextBox(table.getTimerHandler());
			txtChat.setBackColor(table.getWhiteColor());
			txtChat.setForeColor(table.getBlackColor());
			_lcls79_1.addChildObject(txtChat, 10, 2, 2, 1, 1, 0, 0);
			if (table.getApplet().idPropertyContains(2L)) {
				YahooControl _lcls79_2 = new YahooControl();
				_lcls79_2.addChildObject(new YahooImage(table
						.getYahooGamesApplet().d), 1, 1, 0, 0);
				_lcls79_2.addChildObject(new YahooLabel(table.getApplet()
						.lookupString(0x665016ba)), 1, 1, 1, 0);
				btnVoice = new YahooButton(_lcls79_2);
				_lcls79_1.addChildObject(btnVoice, 1, 1, 1, 0);
			}
			table.zq(_lcls79, _lcls79_1);
			table.Bq(this, idList);
			component = tcc_f;
		}
		table.wq(_lcls79, component);
		if (((AbstractYahooGamesApplet) table.getYahooGamesApplet()).noPopupTables) {
			YahooControl _lcls79_3 = new YahooControl();
			table.Aq(this, _lcls79_3);
			YahooImage _lcls13 = new YahooImage(
					table.getYahooGamesApplet().cyga_b, 394, 64);
			table.getYahooGamesApplet().Jm(table, true);
			image = new YahooImage(null, 234, 60, true);
			_lcls79_3.addChildObject(_lcls13, 17, 0, 0, 1, 1, 0, 0);
			_lcls79_3.addChildObject(image, 13, 0, 1, 1, 1, 1, 0);
		}
		YahooControl _lcls79_4 = new YahooControl();
		Color color1 = table.getApplet().getYahooColor(
				"yahoo.games.table_side_foreground", 0);
		Color color2 = table.getApplet().getYahooColor(
				"yahoo.games.table_side_background", 0x669999);
		Color color3 = table.getApplet().getYahooColor(
				"yahoo.games.table_side_bbbackground", 0x99cccc);
		Color color4 = table.getApplet().getYahooColor(
				"yahoo.games.table_side_bboutlinelight", 0x99cccc);
		Color color5 = table.getApplet().getYahooColor(
				"yahoo.games.table_side_bbshadow", 0x669999);
		Color color6 = table.getApplet().getYahooColor(
				"yahoo.games.table_side_bboutlinedark", 0x669999);
		_lcls79_4.nc(color1, color2, color3, color6, color4, color5);
		table.xq(this, _lcls79_4);
		_lcls79_4.addChildObject(container, 10, 1, 1, 1, 1, 0, 0, flag ? 4 : 8,
				4, flag ? 4 : 8, 4);
		boolean flag1 = ((AbstractYahooGamesApplet) table.getYahooGamesApplet()).noPopupTables ^ true;
		if (table.isRobot())
			if (flag1)
				container.addChildObject(table.btnAuto, 10, 2, 0, 1, 1,
						++counter[0], 0, 2, 2, 0, 2);
			else
				container.addChildObject(table.btnAuto, 10, 2, 0, 1, 1, 0,
						++counter[0], 2, 0, 0, 0);
		if (table.Tp() && !flag1) {
			component = new YahooImage(null, 1, 1);
			component.setBackColor(Color.white);
			container.addChildObject(component, 10, 2, 0, 1, 1, 0,
					++counter[0], flag ? 2 : 4, 8, flag ? 2 : 4, 8);
		}
		tcc_b = new YahooControl();
		if (flag1)
			container.addChildObject(tcc_b, 10, 2, 0, 1, 1, ++counter[0], 0);
		else
			container.addChildObject(tcc_b, 10, 2, 0, 1, 1, 0, ++counter[0]);
		if (table.yahooObjects.size() > 0 && !flag1) {
			component = new YahooComponent(1, 1);
			component.setBackColor(Color.white);
			container.addChildObject(component, 10, 2, 0, 1, 1, 0,
					++counter[0], flag ? 2 : 4, 8, flag ? 2 : 4, 8);
		}
		component = flag ? table.cmbChangePrivacy : new YahooPannel(table
				.getApplet().lookupString(0x66500035), table.cmbChangePrivacy,
				table.table_side_tabcolor_bg, table.table_side_tabcolor_fg);
		if (table.getAutomat() > 0) {
			if (table.Tp())
				if (flag1)
					container.addChildObject(table.btnStartGame, 10, 2, 0, 1,
							1, 0, 0, 0, 2, 0, 2);
				else
					container.addChildObject(table.btnStartGame, 10, 2, 0, 1,
							1, 0, 0);
			container.addChildObject(table.btnResign, 10, 2, 0, 1, 1, 0,
					++counter[0], 1, 0, 1, 0);
			table.btnResign.setEnabled(false);
			if (table.Sp())
				if (flag1)
					container.addChildObject(table.btnStand, 10, 2, 0, 1, 1,
							++counter[0], 0, 1, 2, 1, 2);
				else
					container.addChildObject(table.btnStand, 10, 2, 0, 1, 1, 0,
							++counter[0], 1, 0, 1, 0);
			if (!table.getApplet().isKids()
					&& ((AbstractYahooGamesApplet) table.getYahooGamesApplet()).noPopupTables) {
				container.addChildObject(component, 10, 2, 0, 1, 1, 0,
						++counter[0], 1, 0, 1, 0);
				if (flag) {
					YahooControl _lcls79_6 = new YahooControl();
					container.addChildObject(_lcls79_6, 10, 2, 2, 1, 1, 0,
							++counter[0], 1, 0, 1, 0);
					_lcls79_6.addChildObject(table.btnInvite, 10, 2, 2, 1, 1,
							0, 0);
					_lcls79_6.addChildObject(table.btnBoot, 10, 2, 2, 1, 1, 1,
							0);
				}
				else {
					container.addChildObject(table.btnInvite, 10, 2, 2, 1, 1,
							0, ++counter[0], 1, 0, 1, 0);
					container.addChildObject(table.btnBoot, 10, 2, 2, 1, 1, 0,
							++counter[0], 1, 0, 1, 0);
				}
			}
		}
		tcc_c = new YahooControl();
		component = !flag && !flag1 ? new YahooPannel(table.getApplet()
				.lookupString(0x66500034), tcc_c, table.table_side_tabcolor_bg,
				table.table_side_tabcolor_fg) : tcc_c;
		if (table.objects.size() > 0)
			if (flag1)
				container.addChildObject(component, 10, 2, 0, 1, 1,
						++counter[0], 0, 1, 2, 1, 2);
			else
				container.addChildObject(component, 10, 2, 0, 1, 1, 0,
						++counter[0], 1, 0, 1, 0);
		component = table.chkSound;
		if (!flag1)
			container.addChildObject(component, 10, 2, 0, 1, 1, 0,
					++counter[0], 1, 0, 1, 0);
		return flag1;
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		for (int l = 0; l < table.getAutomat(); l++)
			if (event.target == ((YahooTable) table).sits[l].btnSit) {
				table.sit(l);
				return true;
			}
		if (event.target == txtChat) {
			String s1 = YahooUtils.translateMessage((String) obj);
			if (!parseCommand(s1)) {
				tcc_f.Hs();
				table.send('C', s1);
			}
			txtChat.setText("");
		}
		/*
		 * else if (event.target == table.btnResign) { table.resign(); }
		 */
		else if (event.target == table.btnResign) {
			String message = table.getApplet().lookupString(0x66500742);
			if (message.equals("X"))
				message = "Deseja mesmo desistir este jogo?";
			if (table.resignDialog == null)
				table.resignDialog = new YesNoDialog(table.getApplet(), this,
						message, table);
		}
		else if (event.target == table.chkSound)
			table.getApplet().Kg(table.chkSound.isChecked());
		else if (event.target == btnVoice)
			table.getApplet().evalJS(
					"startVoice(\"" + Integer.toString(table.getNumber())
							+ "\")");
		else if (event.target == idList) {
			ListItem id = idList.getSelected();
			if (id != null)
				table.information(((Id) id.obj).name);
		}
		else
			return false;
		return true;
	}

	@Override
	public boolean eventMouseDown(Event event, int l, int i1) {
		if (event.target == image) {
			if (table.A != null)
				table.showDocument(table.A, "_blank");
			return true;
		}
		return false;
	}

	public void initialize() {
		int[] counter = new int[1];
		counter[0] = 0;
		YahooControl _lcls79_5 = new YahooControl();
		boolean flag1 = createControlPannel(_lcls79_5, counter);
		int l = counter[0];

		YahooControl _lcls79_7 = new YahooControl();
		if (!flag1) {
			_lcls79_5.addChildObject(_lcls79_7, 10, 1, 1, 1, 1, 0, ++l);
			table.yq(_lcls79_7);
			YahooControl _lcls79_8 = new YahooControl(1);
			_lcls79_5.addChildObject(_lcls79_8, 10, 1, 2, 1, 1, 0, ++l);
			_lcls79_8.addChildObject(table.btnHelp, 0, 0, 2);
			_lcls79_8.addChildObject(table.ygt_l, 1, 0, 2);
		}
		YahooControl _lcls79_9 = new YahooControl();
		table.dd(_lcls79_9, lblStatus);
		if (table.o == null) {
			table.cd(_lcls79_9, table.gameArea);
		}
		else {
			j = new YahooControl(2);
			j.addChildObject(table.gameArea, 0);
			j.addChildObject(table.o, 1);
			j.qo(0);
			table.cd(_lcls79_9, j);
		}
		advertisement = new Advertisement(_lcls79_9, table.getApplet(), table
				.getApplet().lookupString(0x66501129));
		table.uq(this, advertisement);
	}

	public boolean parseCommand(String command) {
		if (command.equals("/auto"))
			table.send('A');
		else if (command.equals("/boot all"))
			table.bootAll();
		else if (command.startsWith("/force forfeit"))
			table.resign(1 - table.mySitIndex);
		else
			return false;
		return true;
	}

	@Override
	public boolean processEvent(Event event) {
		if (!super.processEvent(event)) {
			table.processEvent(event);
			return false;
		}
		return true;
	}

}
