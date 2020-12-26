// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;

import y.controls.ArrowControl;
import y.controls.YahooButton;
import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooGraphics;
import y.controls.YahooLabel;
import y.ycontrols.SitContainer;

import common.po2.IBall;
import common.po2.Setup;
import common.po2.Slot;
import common.po2.Vel;
import common.po2.YPoint;
import common.po2.YVector;

// Referenced classes of package y.po:
// _cls79, _cls172, _cls174, _cls31,
// _cls58, _cls23, _cls62, _cls29,
// _cls106, _cls64, _cls57, _cls89,
// _cls45, _cls124, _cls100, _cls164,
// _cls169, _cls16, _cls46, _cls168,
// _cls35, _cls78, _cls87, _cls108,
// _cls33, _cls116

public class PoolArea extends YahooControl {

	YahooLabel				pa_b[];

	YahooButton				pa_c[];

	public PoolAimer		poolAimer;

	public English			english;

	private PowerBar		powerBar;

	public ArrowControl		arrow1;

	public ArrowControl		arrow2;

	public YahooControl		j;

	public YahooControl		k;

	public YahooControl		l;

	public YahooControl		m;

	private YahooComponent	btnReset;

	private BallImage		ballImage1;

	private BallImage		ballImage2;

	public YahooLabel		pa_u;

	PoolAreaHandler			handler;

	Color					pa_w;

	Color					pa_x;

	public YahooLabel		lblTime;

	BallSprite				ballSprite[];

	Slot					slot[];

	int						pa_B;

	boolean					pa_C;

	boolean					pa_D;

	BallSprite				pa_E;

	BallSprite				pa_F;

	BallSprite				pa_G;

	boolean					pa_H;

	public CueSprite		cueSprite;

	boolean					pa_J;

	public PoolArea(PoolAreaHandler _pcls29) {
		super(0);
		pa_b = new YahooLabel[4];
		pa_c = new YahooButton[3];
		pa_w = new Color(0, 51, 0);
		pa_x = new Color(97, 143, 97);
		lblTime = new YahooLabel("00:00", YahooLabel.yl_a, 60);
		pa_B = -1;
		handler = _pcls29;
		ic();
		english = new English();
		english.setCaption(handler.lookupString(0x66501338));
		if (handler.isSmallWidows()) {
			poolAimer = new PoolAimer(_pcls29, false);
			addChildObject(poolAimer, 13, 0, 0, 2, 9, 0, 1, 0, 1, 1, 1);
			m = new YahooControl(0);
			powerBar = new PowerBar(_pcls29);
			m.addChildObject(powerBar, 17, 3, 3, 1, 1, 0, 1);
			m.setBackColor(new Color(124, 179, 124));
			addChildObject(m, 17, 3, 3, 1, 10, 2, 0, 1, 0, 1, 1);
			pa_u = new YahooLabel("");
		}
		else {
			poolAimer = new PoolAimer(_pcls29, true);
			addChildObject(poolAimer, 13, 0, 0, 2, 9, 0, 1, 0, 1, 1, 1);
			m = new YahooControl(0);
			m.addChildObject(english, 18, 0, 0, 1, 1, 0, 0);
			english.setVisible(true);
			powerBar = new PowerBar(_pcls29);
			m.addChildObject(powerBar, 17, 3, 3, 1, 1, 0, 1);
			m.setBackColor(new Color(124, 179, 124));
			addChildObject(m, 17, 3, 3, 1, 10, 2, 0, 1, 0, 1, 1);
			pa_u = new YahooLabel("");
		}
		cueSprite = new CueSprite(this, poolAimer, handler, 65536, 10);
		slot = (Slot[]) handler.getPool().getProperty("SLOTS");
	}

	public void _c() {
		k.qo(0);
	}

	public void activate() {
		cueSprite.setActive(true);
		english.setActive(true);
	}

	public boolean cc(Event event, int i1, int j1) {
		if (handler.getPool().getCurrentState() != 0)
			return true;
		for (int k1 = 0; k1 < ballSprite.length; k1++)
			if (!ballSprite[k1].isMoving()
					&& ballSprite[k1].containsPoint(i1, j1)) {
				if (cueSprite.isActive() && !cueSprite.isGrabed()
						&& !ballSprite[k1].getInSlot()
						&& handler.getPool().xj(ballSprite[k1].poolBall)) {
					pa_F = ballSprite[k1];
					pc(pa_F);
					yc();
					handler.Hd();
				}
				if (cueSprite.isActive() && !cueSprite.isPulled() && !pa_D
						&& handler.getPool().Aj(ballSprite[k1].poolBall)) {
					hideCue();
					deactivate();
					pa_E = ballSprite[k1];
					pa_E.ae(40);
					if (poolAimer != null)
						poolAimer.ms();
					pa_D = true;
				}
			}

		for (int l1 = 0; l1 < slot.length; l1++)
			if (slot[l1].contains(i1, j1)) {
				handler.setSlot(l1);
				return true;
			}
		if (poolAimer != null)
			poolAimer.wb();
		return true;
	}

	public boolean dc(Event event, int i1, int j1) {
		if (pa_D) {
			if (handler.getPool().Fj(handler.getMySitIndex(), pa_E.getIndex(),
					i1, j1)) {
				handler.updatePB(pa_E.getIndex(), i1, j1);
			}
			else {
				pa_E.Wd();
				activate();
				yc();
			}
			pa_D = false;
			pa_E.ae(20);
			if (poolAimer != null)
				poolAimer.ms();
		}
		return false;
	}

	public void deactivate() {
		cueSprite.setActive(false);
		english.setActive(false);
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		if (!pa_J)
			return true;
		if (event.target == btnReset) {
			handler.reset();
			invalidate();
			return true;
		}
		if (event.target == cueSprite) {
			if (event.arg.equals("releasePowerbar"))
				releasePowerbar();
			else if (event.arg.equals("pullCue")) {
				pullCue(event.modifiers);
				if (handler.get_n()) {
					handler.Fd(handler.lookupString(0x66501243));
					handler.set_n(false);
				}
			}
			else if (event.arg.equals("strike"))
				handler.strike();
			return true;
		}
		return false;
	}

	@Override
	public boolean eventKeyPress(Event event, int i1) {
		return false;
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		if (!pa_J)
			return true;
		return event.modifiers == 4 ? kc(event, i1, j1) : cc(event, i1, j1);
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		if (!pa_J)
			return true;
		if (pa_D)
			pa_E.setCenter(i1, j1);
		if (poolAimer != null)
			poolAimer.wb();
		return true;
	}

	@Override
	public boolean eventMouseMove(Event event, int i1, int j1) {
		return false;
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		if (!pa_J)
			return true;
		return event.modifiers == 4 ? lc(event, i1, j1) : dc(event, i1, j1);
	}

	public void fc(int index, int cmd, float x, float y, YVector wX, Vel h) {
		BallSprite _lcls31 = ballSprite[index];
		switch (cmd) {
		case 1: // '\001'
			_lcls31.setActive(true);
			_lcls31.fe(x, y, wX, h);
			break;

		case 0: // '\0'
			_lcls31.Ld(x, y, wX, h);
			break;

		case 2: // '\002'
			_lcls31.Td(x, y);
			handler.handleColl(4);
			break;
		}
	}

	public void gc() {
		Ub();
		if (poolAimer != null) {
			poolAimer.fs(ballSprite);
			poolAimer.setCueSprite(cueSprite);
		}
		if (handler.getPool().type0 != 0)
			vc(handler.getPool().type0);
		Slot _lcls164 = handler.getPool().getSelectedSlot();
		if (_lcls164 != null && poolAimer != null)
			poolAimer.os((int) ((YPoint) _lcls164).x,
					(int) ((YPoint) _lcls164).y);
		BallSprite.C = handler.isSmallWidows();
		pa_J = true;
	}

	public PoolAimer getPoolAimer() {
		return poolAimer;
	}

	public void hc(IBall _pcls124) {
		fc(_pcls124.getIndex(), 0, _pcls124.getX(), _pcls124.getY(), null, null);
		if (handler.isMyTurn()) {
			activate();
			yc();
		}
	}

	public void hideCue() {
		cueSprite.setVisible(false);
	}

	private void ic() {
		YahooControl _lcls79 = new YahooControl(0);
		j = new YahooControl(0);
		arrow1 = new ArrowControl(1, 14, 20);
		j.addChildObject(arrow1, 17, 0, 0, 1, 1, 0, 0);
		SitContainer _lcls132 = handler.Sz(0);
		j.addChildObject(_lcls132, 17, 0, 0, 1, 1, 1, 0);
		pa_b[1] = new YahooLabel();
		j.addChildObject(pa_b[1], 17, 0, 0, 1, 1, 2, 0);
		ballImage1 = new BallImage(null, 20, 20);
		j.addChildObject(ballImage1, 17, 0, 0, 1, 1, 3, 0);
		j.setBackColor(pa_x);
		_lcls79.addChildObject(j, 17, 2, 2, 1, 1, 0, 0, 1, 1, 1, 1);
		arrow2 = new ArrowControl(1, 14, 20);
		ballImage2 = new BallImage(null, 20, 20);
		k = new YahooControl(0);
		k.setBackColor(pa_x);
		if (handler.getSitCount() == 2) {
			k.addChildObject(arrow2, 13, 0, 0, 1, 1, 0, 0);
			SitContainer _lcls132_1 = handler.Sz(1);
			pa_b[2] = new YahooLabel();
			k.addChildObject(pa_b[2], 13, 0, 0, 1, 1, 1, 0);
			k.addChildObject(_lcls132_1, 13, 0, 0, 1, 1, 2, 0);
			k.addChildObject(ballImage2, 13, 0, 0, 1, 1, 3, 0);
			l = new YahooControl(0);
			l.setBackColor(pa_x);
			l.addChildObject(lblTime, 10, 2, 2, 1, 1, 0, 0);
			if (handler.haveInitTimePorMove())
				_lcls79.addChildObject(l, 13, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0);
		}
		else {
			btnReset = new YahooButton(handler.lookupString(0x665012b5));
			k = new YahooControl(2);
			k.setBackColor(pa_x);
			k.addChildObject(new YahooComponent(), 0);
			k.addChildObject(btnReset, 1);
		}
		_lcls79.addChildObject(k, 13, 2, 2, 1, 1, 2, 0, 1, 1, 1, 1);
		addChildObject(_lcls79, 10, 2, 2, 2, 1, 0, 0);
	}

	public boolean kc(Event event, int i1, int j1) {
		for (int k1 = 0; k1 < ballSprite.length; k1++) {
			if (ballSprite[k1].isMoving()
					|| !ballSprite[k1].containsPoint(i1, j1)
					|| !cueSprite.isActive() || cueSprite.isGrabed()
					|| ballSprite[k1].getInSlot())
				continue;
			cueSprite.Tk(new YPoint(ballSprite[k1].poolBall.getX(),
					ballSprite[k1].poolBall.getY()));
			break;
		}

		return false;
	}

	public boolean lc(Event event, int i1, int j1) {
		return false;
	}

	@Override
	public void m() {
		super.m();
		english.Ob(this);
	}

	@Override
	public void nc(Color color, Color color1, Color color2, Color color3,
			Color color4, Color color5) {
		super.nc(color, color1, color2, color3, color4, color5);
	}

	public void oc() {
		if (pa_G != null && pa_G.getInSlot()
				&& handler.getPool().isInSlot(pa_G.poolBall)) {
			pc(pa_G);
			return;
		}
		pc(null);
		return;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
	}

	public void pc(BallSprite _pcls31) {
		Setup _lcls100 = handler.getPool().getSetup();
		BallSprite _lcls31 = _pcls31;
		if (_lcls100.nl()) {
			IBall _lcls124 = _lcls100.getWhiteBall();
			_lcls31 = ballSprite[_lcls124.getIndex()];
		}
		pa_G = _lcls31;
		if (_lcls31 == null) {
			pa_H = true;
			return;
		}
		cueSprite.setSelectedBall(_lcls31.poolBall);
		return;
	}

	public void pullCue(int i1) {
		powerBar.setPower(i1);
	}

	public void qc(int i1, int j1) {
		if (j1 != -1) {
			BallSprite _lcls31 = ballSprite[j1];
			uc(i1, _lcls31.bs_g, _lcls31.bs_y);
		}
	}

	public void releasePowerbar() {
		powerBar.reset();
		getContainer().wb();
	}

	public void sc(int i1) {
		pa_B = i1;
		pa_C = true;
		Slot _lcls164 = handler.getPool().getSelectedSlot();
		if (_lcls164 != null && poolAimer != null)
			poolAimer.os((int) ((YPoint) _lcls164).x,
					(int) ((YPoint) _lcls164).y);
	}

	public void setBackColor() {
		super.setBackColor(pa_w);
	}

	public void swapArrow(int i1) {
		if (i1 == 0) {
			arrow1.setVisible(true);
			arrow2.setVisible(false);
		}
		else {
			arrow2.setVisible(true);
			arrow1.setVisible(false);
		}
		english.reset();
	}

	public void tc(int i1, Image image) {
		if (i1 == 0)
			ballImage1.setImage(image);
		else
			ballImage2.setImage(image);
	}

	private void Ub() {
		IBall a_lcls124[] = handler.getPool().getBall();
		ballSprite = new BallSprite[a_lcls124.length];
		for (IBall _lcls124 : a_lcls124) {
			ballSprite[_lcls124.getIndex()] = new BallSprite(_lcls124);
		}

	}

	public void uc(int i1, Image image, Image image1) {
		if (image1 == null) {
			tc(i1, image);
			return;
		}
		if (i1 == 0) {
			ballImage1.setImage(image);
			ballImage1.xl(image1, 5, 3);
		}
		else {
			ballImage2.setImage(image);
			ballImage2.xl(image1, 5, 3);
		}
	}

	public void vc(int i1) {
		if (i1 == 1024) {
			ballImage1.setImage(YahooPoolImageList.loadImages().s);
			ballImage2.setImage(YahooPoolImageList.loadImages().t);
		}
		else if (i1 == 2048) {
			ballImage1.setImage(YahooPoolImageList.loadImages().t);
			ballImage2.setImage(YahooPoolImageList.loadImages().s);
		}
		else {
			ballImage1.setImage(null);
			ballImage2.setImage(null);
		}
	}

	public void wc() {
		for (int i1 = 1; i1 < ballSprite.length; i1++)
			ballSprite[i1].be();

	}

	public void xc() {
		for (BallSprite element : ballSprite)
			element.setActive(true);

	}

	public void Yb() {
		for (int i1 = 0; i1 < ballSprite.length; i1++)
			if (!ballSprite[i1].getInSlot())
				ballSprite[i1].Sd();

	}

	public void yc() {
		cueSprite.Wk();
		cueSprite.setVisible(true);
	}

	public void zc() {
		k.qo(1);
	}
}
