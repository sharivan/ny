// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po2;

import java.awt.Color;
import java.awt.Event;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import y.controls.YahooComponent;
import y.controls.YahooControl;
import y.controls.YahooGraphics;

import common.io.YData;
import common.po2.IBall;
import common.po2.PoolBall;
import common.po2.YPoint;
import common.po2.YVector;

// Referenced classes of package y.po:
// _cls78, _cls111, _cls106, _cls89,
// _cls124, _cls169, _cls108, _cls16,
// _cls46, _cls116, _cls79, _cls29

public class CueSprite extends YahooComponent implements YData {

	YPoint				cs_a;
	YPoint				m_ball;
	YPoint				cs_c;
	boolean				m_visible;
	private boolean		m_active;
	boolean				m_grabed;
	boolean				cs_g;
	boolean				m_pulled;
	boolean				m_pseudoPulled;
	YPoint				j;
	YPoint				k;
	boolean				m_stroke;
	public int			power1;
	int					power;
	Color				cs_o;
	Color				cs_p;
	YVector				m_cue;
	public IBall		m_selectedBall;
	YPoint				cs_s;
	private int			posX;
	private int			posY;
	Polygon				cs_v;
	Polygon				cs_w;
	boolean				cs_x;
	PoolAimer			cs_y;
	private boolean		changed;
	YPoint				cueTip;
	YVector				cueTipOffset;
	YVector				D;
	double				E;
	int					F;
	boolean				G;
	YVector				cs_H;
	int					cs_J;
	int					K;
	private int			iterateEffectCount;
	private int			maxPower;
	private int			N;
	private int			O;
	private int			P;
	private int			Q;
	private int			R;
	private int			S;
	private int			T;
	private YPoint		U;
	private YVector		V;
	private YVector		W;
	private YVector		X;
	private Rectangle	rectangle;
	PoolArea			poolArea;

	public CueSprite(PoolArea poolArea, YahooControl _pcls79,
			PoolAreaHandler _pcls29) {
		this(poolArea, _pcls79, _pcls29, 120, 3);
	}

	public CueSprite(PoolArea poolArea, YahooControl _pcls79,
			PoolAreaHandler _pcls29, int maxPower, int iterateEffectCount) {
		super(160, 160);
		this.poolArea = poolArea;
		cs_a = new YPoint();
		m_ball = new YPoint();
		cs_c = new YPoint();
		m_visible = false;
		m_active = false;
		m_grabed = false;
		cs_g = false;
		// g = true;
		m_pulled = false;
		m_pseudoPulled = false;
		j = new YPoint();
		k = new YPoint();
		m_stroke = false;
		power1 = 0;
		power = 0;
		m_cue = new YVector();
		cs_s = new YPoint();
		posX = 0;
		posY = 0;
		cs_v = new Polygon();
		cs_w = new Polygon();
		cs_x = false;
		changed = true;
		cueTip = new YPoint();
		cueTipOffset = new YVector();
		D = new YVector();
		G = false;
		cs_H = new YVector();
		this.iterateEffectCount = iterateEffectCount;
		this.maxPower = maxPower;
		N = 10;
		O = 150;
		P = 5;
		Q = 40;
		R = 0;
		S = 1;
		T = 8;
		U = new YPoint();
		V = new YVector();
		W = new YVector();
		X = new YVector();
		rectangle = new Rectangle();
		cs_y = (PoolAimer) _pcls79;
		Sn(true);
		cs_o = new Color(125, 63, 0);
		cs_p = new Color(168, 84, 0);
		cs_s.setCoords(275F, 148F);
		Wk();
	}

	private void beginDrag(Event event) {
		event.x = event.x + posX;
		event.y = event.y + posY;
		cs_y.doEvent(event);
	}

	@Override
	public boolean contains(int i1, int j1) {
		return ik(i1 - cs_J, j1 - K, true);
	}

	public boolean containsPoint(int i, int j) {
		if (cs_v == null)
			return false;
		int ai[] = cs_w.xpoints;
		int ai1[] = cs_w.ypoints;
		int k1 = cs_w.npoints;
		if (hk(ai, ai1, k1).contains(i, j)) {
			int l1 = 0;
			int i2 = 0;
			int j2;
			for (j2 = 0; j2 < k1 && ai1[j2] == j; j2++) {
			}
			for (int k2 = 0; k2 < k1; k2++) {
				int l2 = (j2 + 1) % k1;
				int i3 = ai[l2] - ai[j2];
				int j3 = ai1[l2] - ai1[j2];
				if (j3 != 0) {
					int k3 = i - ai[j2];
					int l3 = j - ai1[j2];
					if (ai1[l2] == j && ai[l2] >= i)
						i2 = ai1[j2];
					if (ai1[j2] == j && ai[j2] >= i && i2 > j != ai1[l2] > j)
						l1--;
					float f1 = (float) l3 / (float) j3;
					if (f1 >= 0.0D && f1 <= 1.0D && f1 * i3 >= k3)
						l1++;
				}
				j2 = l2;
			}

			return l1 % 2 != 0;
		}
		return false;
	}

	public void doChangeDirection(Event event, int i1) {
		if (!isActive() || i1 != 1006 && i1 != 1007)
			return;
		if (isPulled()) {
			doResetCue();
			return;
		}
		double d1 = 0.0040000001899898052D;
		if (i1 == 1006) // set para esquerda
			d1 *= -1D;
		if (event.modifiers == 1)
			d1 *= 20D;
		rotate(d1);
	}

	public void doChangePower(Event event, int i1) {
		if (!isActive() || m_selectedBall == null || !isVisible())
			return;
		float f1 = cs_a.distance(m_ball);
		if (i1 == 1005 && f1 + 1.0F < maxPower) {
			setPulled(true);
			pullCue(f1 + 2.0F);
		}
		if (i1 == 1004 && isPulled())
			if (f1 - 2.0F <= 0.0F) {
				m_ball.setCoords(cs_a.x, cs_a.y);
				setPulled(false);
			}
			else {
				pullCue(f1 - 2.0F);
			}
		invalidate();
	}

	public void doResetCue() {
		if (!isActive() || m_selectedBall == null || !isVisible()) {
			return;
		}
		m_ball.setCoords(cs_a.x, cs_a.y);
		setPulled(false);
		invalidate();
		return;
	}

	public void doStrike(Event event, int i1) {
		if (isPulled() && cs_a.distance(m_ball) > 0.0F) {
			setStroke(true);
			setPulled(false);
			strike();
		}
	}

	public void el(long l1) {
		if (G) {
			m_ball.add(D);
			m_cue.rotate(E);
			F--;
			if (F == 0)
				G = false;
			Xk();
		}
	}

	@Override
	public boolean eventKeyPress(Event event, int key) {
		if (!isActive())
			return false;
		if (key == 1006 || key == 1007) // setas para esquerda e para direita
			doChangeDirection(event, key);
		if (key == 1004 || key == 1005) // setas para baixo e para cima
			doChangePower(event, key);
		if (key == 32) // espaço
			doStrike(event, key);
		if (key == 27)
			doResetCue();
		return true;
	}

	@Override
	public boolean eventKeyRelease(Event event, int i1) {
		cs_y.wb();
		return true;
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		if (!isActive()) {
			beginDrag(event);
			return true;
		}
		Gn(true);
		boolean flag = event.modifiers != 4 && event.modifiers != 1 ? Fk(event,
				i1, j1) : Ik(event, i1, j1);
		return flag;
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		if (!isActive()) {
			beginDrag(event);
			return true;
		}
		return cs_x ? releasePowerBar(event, i1, j1) : Gk(event, i1, j1);
	}

	@Override
	public boolean eventMouseMove(Event event, int i1, int j1) {
		boolean flag = false;
		if (ik(i1, j1, false))
			flag = true;
		return flag;
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		if (!isActive()) {
			beginDrag(event);
			return true;
		}
		boolean flag = cs_x ? Kk(event, i1, j1) : Hk(event, i1, j1);
		return flag;
	}

	public boolean Fk(Event event, int i1, int j1) {
		if (!isGrabed()) {
			if (isPulled())
				doResetCue();
			if (m_selectedBall != null && !m_selectedBall.inSlot())
				setGrabed(true);
		}
		return true;
	}

	public YPoint getPos(boolean flag) {
		U.setCoords((int) m_ball.x, (int) m_ball.y);
		if (flag) {
			m_ball.setCoords(cs_a.x, cs_a.y);
			setGrabed(false);
			power1 = 0;
		}
		return U;
	}

	public IBall getSelectedBall() {
		return m_selectedBall;
	}

	public boolean Gk(Event event, int i1, int j1) {
		boolean flag = false;
		if (isGrabed()) {
			tk(i1, j1, true);
			beginDrag(event);
			flag = true;
		}
		return flag;
	}

	public Rectangle hk(int ai[], int ai1[], int i1) {
		int j1 = 0x7fffffff;
		int k1 = 0x7fffffff;
		int l1 = 0x80000000;
		int i2 = 0x80000000;
		for (int j2 = 0; j2 < i1; j2++) {
			int k2 = ai[j2];
			j1 = Math.min(j1, k2);
			l1 = Math.max(l1, k2);
			int l2 = ai1[j2];
			k1 = Math.min(k1, l2);
			i2 = Math.max(i2, l2);
		}

		rectangle.x = j1;
		rectangle.y = k1;
		rectangle.width = l1 - j1;
		rectangle.height = i2 - k1;
		return rectangle;
	}

	public boolean Hk(Event event, int i1, int j1) {
		setGrabed(false);
		return true;
	}

	public boolean ik(int i1, int j1, boolean flag) {
		if (flag) {
			i1 -= posX;
			j1 -= posY;
		}
		return containsPoint(i1, j1);
	}

	public boolean Ik(Event event, int i1, int j1) {
		if (m_selectedBall != null && !m_selectedBall.inSlot()) {
			setPulled(true);
			j.setCoords(i1 + posX, j1 + posY);
			m_stroke = false;
		}
		cs_x = true;
		return true;
	}

	public boolean isActive() {
		return m_active;
	}

	public boolean isChanged() {
		return changed;
	}

	public boolean isGrabed() {
		return m_grabed;
	}

	public boolean isPulled() {
		return m_pulled;
	}

	public boolean isVisible() {
		return m_visible;
	}

	public boolean Kk(Event event, int i1, int j1) {
		cs_x = false;
		boolean flag = false;
		if (isActive() && isPulled()) {
			if (m_selectedBall.distance(m_ball) > m_selectedBall.getRadius())
				strike();
			else
				setPulled(false);
			flag = true;
		}
		if (flag) {
			return true;
		}
		beginDrag(event);
		return false;
	}

	private Polygon mk() {
		m_cue.setCoordsTo(V);
		V.rotate(1.571D);
		V.setCoordsTo(W);
		W.versor();
		W.mul(1.0F);
		V.setCoordsTo(X);
		X.versor();
		X.mul(1.0F);
		m_cue.setCoordsTo(V);
		V.versor();
		V.mul(4F);
		Polygon polygon = new Polygon();
		polygon.addPoint((int) (m_ball.x + W.x - posX),
				(int) (m_ball.y + W.y - posY));
		polygon.addPoint((int) (m_ball.x - W.x - posX),
				(int) (m_ball.y - W.y - posY));
		polygon.addPoint((int) (m_ball.x + V.x - X.x - posX), (int) (m_ball.y
				+ V.y - X.y - posY));
		polygon.addPoint((int) (m_ball.x + V.x + X.x - posX), (int) (m_ball.y
				+ V.y + X.y - posY));
		return polygon;
	}

	private Polygon nk() {
		m_cue.setCoordsTo(V);
		V.rotate(1.571D);
		V.setCoordsTo(W);
		W.versor();
		W.mul(1.0F);
		V.setCoordsTo(X);
		X.versor();
		X.mul(T / 2);
		Polygon polygon = new Polygon();
		polygon.addPoint((int) (m_ball.x + W.x - posX),
				(int) (m_ball.y + W.y - posY));
		polygon.addPoint((int) (m_ball.x - W.x - posX),
				(int) (m_ball.y - W.y - posY));
		polygon.addPoint((int) (m_ball.x + m_cue.x - X.x - posX),
				(int) (m_ball.y + m_cue.y - X.y - posY));
		polygon.addPoint((int) (m_ball.x + m_cue.x + X.x - posX),
				(int) (m_ball.y + m_cue.y + X.y - posY));
		cs_w = new Polygon();
		cs_w.addPoint((int) (m_ball.x + W.x * 3F - posX), (int) (m_ball.y + W.y
				* 3F - posY));
		cs_w.addPoint((int) (m_ball.x - W.x * 3F - posX), (int) (m_ball.y - W.y
				* 3F - posY));
		cs_w.addPoint((int) (m_ball.x + m_cue.x - X.x * 2.0F - posX),
				(int) (m_ball.y + m_cue.y - X.y * 2.0F - posY));
		cs_w.addPoint((int) (m_ball.x + m_cue.x + X.x * 2.0F - posX),
				(int) (m_ball.y + m_cue.y + X.y * 2.0F - posY));
		return polygon;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		if (m_visible) {
			super.paint(yahooGraphics);
			cs_v = nk();
			if (cs_g)
				yahooGraphics.setColor(cs_p);
			else
				yahooGraphics.setColor(cs_o);
			yahooGraphics.fillPolygon(cs_v);
			Polygon polygon = mk();
			yahooGraphics.setColor(Color.white);
			if (cs_g)
				yahooGraphics.drawPolygon(cs_v);
			yahooGraphics.fillPolygon(polygon);
		}
	}

	public void pullCue(float f1) {
		m_cue.setCoordsTo(V);
		V.versor();
		V.mul(f1);
		m_ball.setCoords(cs_a.x + V.x, cs_a.y + V.y);
		power1 = (int) f1;
		changed = true;
		Event event = new Event(this, 1001, "pullCue");
		event.modifiers = (int) f1;
		Xk();
		beginDrag(event);
	}

	public void read(DataInput input) throws IOException {
		input.readInt();
		float f1 = input.readFloat();
		float f2 = input.readFloat();
		float f3 = input.readFloat();
		float f4 = input.readFloat();
		cueTip.setCoords(f1, f2);
		cueTipOffset.setCoords(f3, f4);
		power = input.readInt();
	}

	public void read(YPoint _cueTip, YVector _cueTipOffset, int _power) {
		cueTip.setCoords(_cueTip.x, _cueTip.y);
		cueTipOffset.setCoords(_cueTipOffset.x, _cueTipOffset.y);
		power = _power;
	}

	public boolean releasePowerBar(Event event, int i1, int j1) {
		boolean flag = false;
		if (isPulled()) {
			U.setCoords(i1 + posX, j1 + posY);
			cs_H.setCoords(j, U);
			float f1 = cs_H.proj(m_cue);
			boolean flag1 = m_cue.angle(cs_H) < 1.5707963267948966D;
			if (flag1) {
				if (f1 < maxPower)
					pullCue(f1);
				else
					pullCue(maxPower);
			}
			else {
				m_ball.setCoords(cs_a.x, cs_a.y);
				beginDrag(new Event(this, 1001, "releasePowerBar"));
			}
			invalidate();
			flag = true;
		}
		return flag;
	}

	public void rotate(double angle) {
		m_cue.rotate(angle);
		m_ball.setCoordsTo(U);
		U.add(m_cue);
		Vk(U);
	}

	public void setActive(boolean flag) {
		m_active = flag;
	}

	public void setChanged(boolean flag) {
		changed = flag;
	}

	public void setCue(float x, float y) {
		m_cue.setCoords(x, y);
		m_ball.setCoordsTo(U);
		U.add(m_cue);
		Vk(U);
	}

	public void setCue(YVector cue) {
		setCue(cue.x, cue.y);
	}

	public void setGrabed(boolean flag) {
		m_grabed = flag;
		invalidate();
	}

	public void setPower(int power2) {

	}

	public void setPulled(boolean flag) {
		m_pulled = flag;
	}

	public void setSelectedBall(IBall _pcls124) {
		m_selectedBall = _pcls124;
		setGrabed(false);
	}

	public void setStroke(boolean flag) {
		m_stroke = flag;
	}

	public void setVisible(boolean flag) {
		m_visible = flag;
		invalidate();
	}

	public void sk(float f1, float f2, boolean flag) {
		if (flag) {
			f1 += posX;
			f2 += posY;
		}
		U.setCoords(f1, f2);
		Vk(U);
	}

	public void strike() {
		if (isPulled()) {
			setStroke(true);
			setPulled(false);
			setActive(false);
		}
		beginDrag(new Event(this, 1001, "strike"));
	}

	public void tk(int i1, int j1, boolean flag) {
		sk(i1, j1, flag);
	}

	public void Tk(YPoint _pcls169) {
		if (!isActive() || m_selectedBall == null)
			return;
		if (_pcls169.distance(m_selectedBall.getX(), m_selectedBall.getY()) < 11F) {
			return;
		}
		V.setCoords(m_selectedBall.getX() - _pcls169.x, m_selectedBall.getY()
				- _pcls169.y);
		_pcls169.setCoords(m_selectedBall.getX(), m_selectedBall.getY());
		_pcls169.add(V);
		Vk(_pcls169);
		return;
	}

	@Override
	public String toString() {
		String s1 = "CueSprite\n";
		s1 = s1 + "m_visible=" + m_visible + "\n";
		s1 = s1 + "m_active=" + m_active + "\n";
		s1 = s1 + "m_grabed=" + m_grabed + "\n";
		s1 = s1 + "m_pulled=" + m_pulled + "\n";
		s1 = s1 + "m_pseudoPulled=" + m_pseudoPulled + "\n";
		s1 = s1 + "m_stroke=" + m_stroke + "\n";
		s1 = s1 + "m_cue=" + m_cue + "\n";
		s1 = s1 + "m_selectedBall=" + m_selectedBall + "\n";
		return s1;
	}

	public void update() {
		power1 = power;
		D.setCoords((cueTip.x - m_ball.x) / iterateEffectCount,
				(cueTip.y - m_ball.y) / iterateEffectCount);
		double d1 = m_cue.tt(cueTipOffset);
		E = d1 / iterateEffectCount;
		F = iterateEffectCount;
		G = true;
	}

	public void Vk(YPoint _pcls169) {
		if (m_selectedBall != null) {
			m_cue.setCoords(_pcls169.x - m_selectedBall.getX(), _pcls169.y
					- m_selectedBall.getY());
			if (m_cue.x == 0.0F && m_cue.y == 0.0F)
				return;
			m_cue.setCoordsTo(V);
			V.versor();
			V.mul(N);
			m_ball.setCoords(m_selectedBall.getX() + V.x, m_selectedBall.getY()
					+ V.y);
			m_cue.versor();
			m_cue.mul(O);
		}
		cs_a.setCoords(m_ball.x, m_ball.y);
		Xk();
	}

	public boolean wk() {
		return isVisible() && m_selectedBall != null && isActive();
	}

	public void Wk() {
		if (m_selectedBall != null && !m_selectedBall.inSlot()) {
			m_cue.setCoords(m_selectedBall.getX() - cs_s.x, m_selectedBall
					.getY()
					- cs_s.y);
			m_cue.setCoordsTo(V);
			V.versor();
			V.mul(N);
			m_ball.setCoords(m_selectedBall.getX() + V.x, m_selectedBall.getY()
					+ V.y);
			m_cue.versor();
			m_cue.mul(O);
		}
		else {
			m_ball.setCoords(P, Q);
			m_cue.setCoords(R, S);
			m_cue.mul(O);
		}
		cs_a.setCoords(m_ball.x, m_ball.y);
		Xk();
	}

	public void write(DataOutput output) throws IOException {
		byte byte0 = 20;
		output.writeInt(byte0);
		output.writeFloat(m_ball.x);
		output.writeFloat(m_ball.y);
		output.writeFloat(m_cue.x);
		output.writeFloat(m_cue.y);

		output.writeInt(power1 > 120 ? 120 : power1);
	}

	public void Xk() {
		int i1 = (int) m_ball.x - 5;
		int j1 = (int) m_ball.y - 5;
		if (m_cue.x < 0.0F)
			i1 = (int) (i1 + m_cue.x);
		if (m_cue.y < 0.0F)
			j1 = (int) (j1 + m_cue.y);
		posX = i1;
		posY = j1;
		super.setCoords(cs_J + posX, K + posY);
		if (m_selectedBall != null && isActive()) {
			PoolBall selectedBall = PoolAimer
					.cloneBall((PoolBall) m_selectedBall);
			YPoint cueDist = getPos(false);
			YPoint englishDist = poolArea.english.getPos();
			selectedBall.start(cueDist, englishDist, new YPoint(), -1);
			selectedBall.vel.setCoords(new YPoint(m_cue.x, m_cue.y));
			selectedBall.vel.neg();
			// TODO resolver isso aqui urgente!!!
			// y.computeAim(selectedBall);
			// y.computeAim(selectedBall);
		}
		changed = true;
		Dn();
		invalidate();
	}
}
