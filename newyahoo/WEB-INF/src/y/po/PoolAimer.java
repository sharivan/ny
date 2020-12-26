// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.po;

import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.util.Vector;

import y.controls.ArrowControl;
import y.controls.YahooControl;
import y.controls.YahooGraphics;
import y.utils.TimerHandler;
import y.utils.YahooImage;

import common.po.PoolBall;
import common.po.PoolMath;
import common.po.YIPoint;
import common.po.YPoint;
import common.po.YRectangle;

// Referenced classes of package y.po:
// _cls79, _cls172, _cls86, _cls174,
// _cls39, _cls31, _cls58, _cls29,
// _cls89, _cls45, _cls171, _cls151,
// _cls169, _cls120, _cls108, _cls16,
// _cls46, _cls48, _cls78, _cls116,
// _cls13

public class PoolAimer extends YahooControl implements TimerHandler {

	public static PoolBall cloneBall(PoolBall ball) {
		PoolBall poolBall = new PoolBall();
		poolBall.setCoords(ball.a, ball.b);
		poolBall.index = ball.index;
		poolBall.radius = ball.radius;
		poolBall.x0 = ball.x0;
		poolBall.y0 = ball.y0;
		poolBall.pool = ball.pool;
		poolBall.m_inSlot = ball.m_inSlot;
		poolBall.vel.setFrom(ball.vel);
		poolBall.wX.setFrom(ball.wX);
		poolBall.h.setFrom(ball.h);
		poolBall.h.setVel(ball.h.he());
		poolBall.sliding = ball.sliding;
		poolBall.K = ball.K;
		poolBall.L = ball.L;
		poolBall.ballColided = ball.ballColided;
		return poolBall;
	}

	PoolAreaHandler		table;
	YahooImage			pa_b;
	YPoint				aimBallInitPoint;
	int					m;
	YRectangle			playAreaBalls;
	int					pa_o;
	int					pa_p;
	int					pa_q;
	int					pa_r;
	ArrowControl		arrow;
	boolean				arrowChanged;
	int					pa_v;
	int					pa_w;
	int					pa_x;
	int					pa_y;
	boolean				ballColided;
	YIPoint				firstColl;
	int					index;
	Color				pa_C;
	Color				pa_D;
	Image				pa_E;
	boolean				pa_F;
	Aim					aim;
	CueSprite			cueSprite;
	Image				pa_I;
	Image				pa_J;
	Image				pa_K;
	Image				pa_L;
	Image				pa_M;
	Image				pa_N;
	Image				pa_O;
	Image				pa_P;
	Image				pa_Q;
	Image				R;
	YIPoint				firstColl1;
	Vector<PoolBall>	colBalls;

	public int			maxAimCount	= 3;

	public PoolAimer(PoolAreaHandler _pcls29, boolean smallWindows) {
		super(550, 300 + (smallWindows ? 30 : 0));
		colBalls = new Vector<PoolBall>();
		pa_b = null;
		pa_v = 0;
		pa_w = 0;
		pa_x = 0;
		pa_y = 0;
		ballColided = false;
		firstColl = new YIPoint(0, 0);
		index = 0;
		pa_C = new Color(97, 143, 97);
		pa_D = new Color(99, 142, 38);
		pa_E = YahooPoolImageList.loadImages().q;
		pa_I = YahooPoolImageList.loadImages().g;
		pa_J = YahooPoolImageList.loadImages().h;
		pa_K = YahooPoolImageList.loadImages().i;
		pa_L = YahooPoolImageList.loadImages().j;
		pa_M = YahooPoolImageList.loadImages().k;
		pa_N = YahooPoolImageList.loadImages().l;
		pa_O = YahooPoolImageList.loadImages().m;
		pa_P = YahooPoolImageList.loadImages().n;
		pa_Q = YahooPoolImageList.loadImages().o;
		R = YahooPoolImageList.loadImages().p;
		firstColl1 = new YIPoint();
		table = _pcls29;
		arrow = new ArrowControl(2, 10, 20);
		arrow.setForeColor(Color.red);
		addChildObject(arrow, 0, 0, true);
		arrow.Sn(true);
		pa_F = smallWindows;
		aimBallInitPoint = (YPoint) table.getProperty("AIM_BALL_INIT_POINT");
		m = 228;
		playAreaBalls = (YRectangle) table.getProperty("PLAY_AREA_BALLS");
		pa_o = 50;
		pa_p = 470;
		pa_q = 317;
		pa_r = PoolMath.intToYInt(50);
		aim = new Aim(550, 300, pa_r, _pcls29);

		addChildObject(aim, 0, 0, true);
		invalidate();
	}

	@Override
	public boolean eventActionEvent(Event event, Object obj) {
		return false;
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		Gn(true);
		getParent().doEvent(event);
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		getParent().doEvent(event);
		return true;
	}

	/*
	 * private void computeAim0(YPoint m_ball, YVector m_cue) { YIPoint V = new
	 * YIPoint(); YIPoint Y = new YIPoint(); YIPoint g = new YIPoint(); YIPoint
	 * h = new YIPoint(); YIPoint i = new YIPoint(); YIPoint j = new YIPoint();
	 * YIPoint k = new YIPoint(); YIPoint S = new YIPoint(); YILine line1 = new
	 * YILine(); YILine line2 = new YILine(); YILine line3 = new YILine();
	 * YIPoint startPoint = new YIPoint(m_ball.x, m_ball.y); PoolBall poolBall =
	 * new PoolBall((int) m_ball.x, (int) m_ball.y, 10); PoolMath3.set(g,
	 * PoolMath.floatToYInt(m_cue.x), PoolMath .floatToYInt(m_cue.y));
	 * PoolMath3.neg(g); PoolMath3.versor(g); PoolMath3.mul(g,
	 * PoolMath.intToYInt(20)); poolBall.vel.set(g.a, g.b); int tg =
	 * PoolMath.n_1; PoolMath3.set(h, g); PoolMath3.versor(h); for (int j1 = 2;
	 * j1 < 600; j1++) { PoolMath3.set(i, h); PoolMath3.mul(i,
	 * PoolMath.intToYInt(j1)); if (playAreaBalls.containsPoint(poolBall.a +
	 * i.a, poolBall.b + i.b)) continue; tg = PoolMath.div(PoolMath3.abs(i),
	 * PoolMath3.abs(g)); break; }
	 * 
	 * int k1 = PoolMath.n_1; PoolMath3.set(V, poolBall.a, poolBall.b);
	 * PoolMath3.set(j, poolBall.vel.a, poolBall.vel.b);
	 * PoolMath3.set(firstColl, 0, 0); ballColided = false; PoolBall ball1 =
	 * null; Vector ball = table.getBallInPlayArea(); poolBall.sliding = true;
	 * poolBall.wX.set(0, 0); poolBall.uncolide(); for (int tick = 0; tick < 600
	 * && playAreaBalls.containsPoint(poolBall.a, poolBall.b); poolBall
	 * .nextPosition(), tick++) { for (int l1 = 0; l1 < ball.size(); l1++) {
	 * PoolBall _lcls171 = (PoolBall) ball.elementAt(l1); if
	 * (!PoolMath3.same(new YIPoint(_lcls171.a, _lcls171.b), poolBall.a,
	 * poolBall.b)) { int k2 = poolBall.timeToBall(_lcls171); if (k2 < k1 && k2
	 * > 0) { k1 = k2; ball1 = _lcls171; // colBalls.addElement(ball1); } } }
	 * 
	 * if (k1 < PoolMath.n_1) { PoolMath3.set(firstColl, poolBall.a,
	 * poolBall.b); poolBall.vel.mul(k1); PoolMath3.set(Y, poolBall.vel.a,
	 * poolBall.vel.b); PoolMath3.add(firstColl, Y); if
	 * (playAreaBalls.containsPoint(firstColl.a, firstColl.b)) { ballColided =
	 * true; index = ball1.index; } break; } poolBall.add(poolBall.vel); }
	 * 
	 * poolBall.setCoords(V.a, V.b); poolBall.vel.set(j.a, j.b); if
	 * (!ballColided) { poolBall.vel.mul(tg); poolBall.add(poolBall.vel);
	 * PoolMath3.set(S, poolBall.a, poolBall.b); PoolMath3.set(k, S.a -
	 * startPoint.a, startPoint.b - S.b); line1.setCoords(new
	 * YIPoint(startPoint.a, startPoint.b), S); } else {
	 * poolBall.setCoords(firstColl.a, firstColl.b); PoolMath3.set(S,
	 * poolBall.a, poolBall.b); line1.setCoords(new YIPoint(startPoint.a,
	 * startPoint.b), S); poolBall.vel.versor(); poolBall.vel.mul(0xa0000);
	 * PoolMath3.set(Y, poolBall.vel.a, poolBall.vel.b); poolBall.ov(ball1,
	 * ball1.vel); ball1.ov(poolBall, new y.po.YIVector(Y.a, Y.b)); int i2 =
	 * PoolMath3.distance(V, ball1.a, ball1.b); int j2 = r; if (i2 >
	 * PoolMath.intToYInt(40)) { j2 -= PoolMath.div(i2 - PoolMath.intToYInt(40),
	 * PoolMath .intToYInt(4)); if (j2 < 0) j2 = 0; } PoolMath3.set(k,
	 * poolBall.vel.a, poolBall.vel.b); // j2 = PoolMath.intToYInt(500); if (k.a
	 * == 0 && k.b == 0) { PoolMath3.set(S, poolBall.a, poolBall.b); } else {
	 * PoolMath3.versor(k); PoolMath3.mul(k, j2); PoolMath3.add(S, k); } YIPoint
	 * pv1 = new YIPoint(poolBall.a, poolBall.b); line2.setCoords(pv1, S); k =
	 * new YIPoint(ball1.vel.a, ball1.vel.b); PoolMath3.versor(k); //
	 * PoolMath3.mul(k, PoolMath.intToYInt(500)); PoolMath3.mul(k, j2); YIPoint
	 * pv2 = new YIPoint(ball1.a, ball1.b); YIPoint _lcls169_1 = new
	 * YIPoint(ball1.a, ball1.b); PoolMath3.add(_lcls169_1, k);
	 * line3.setCoords(pv2, _lcls169_1); ball1.stop(); } y.po.YLine yline1 = new
	 * YLine(PoolMath.yintToInt(line1.x.a), PoolMath .yintToInt(line1.x.b),
	 * PoolMath.yintToInt(line1.y.a), PoolMath .yintToInt(line1.y.b));
	 * y.po.YLine yline2 = new YLine(PoolMath.yintToInt(line2.x.a), PoolMath
	 * .yintToInt(line2.x.b), PoolMath.yintToInt(line2.y.a), PoolMath
	 * .yintToInt(line2.y.b)); y.po.YLine yline3 = new
	 * YLine(PoolMath.yintToInt(line3.x.a), PoolMath .yintToInt(line3.x.b),
	 * PoolMath.yintToInt(line3.y.a), PoolMath .yintToInt(line3.y.b));
	 * aim.add(yline1, yline2, yline3, poolBall.toYVector()); }
	 */

	/*
	 * private void computeAim1(y.po.PoolBall selectedBall, YIPoint cueDist,
	 * YIPoint englishDist, int counter[]) { counter[0]++; if (counter[0] >
	 * maxAimCount) return; YIPoint V = new YIPoint(); YIPoint Y = new
	 * YIPoint(); YIPoint g = new YIPoint(); YIPoint h = new YIPoint(); YIPoint
	 * i = new YIPoint(); YIPoint j = new YIPoint(); YIPoint k = new YIPoint();
	 * YIPoint S = new YIPoint(); YILine line1 = new YILine(); YILine line2 =
	 * new YILine(); YILine line3 = new YILine(); PoolBall poolBall = new
	 * PoolBall(); poolBall.setCoords(selectedBall.a, selectedBall.b);
	 * poolBall.index = selectedBall.index; poolBall.radius =
	 * selectedBall.radius; poolBall.x0 = selectedBall.x0; poolBall.y0 =
	 * selectedBall.y0; poolBall.m_inSlot = selectedBall.m_inSlot;
	 * PoolMath3.set(g, cueDist); PoolMath3.neg(g); PoolMath3.versor(g);
	 * PoolMath3.mul(g, PoolMath.intToYInt(20)); poolBall.vel.set(g.a, g.b); int
	 * tg = PoolMath.n_1; PoolMath3.set(h, g); PoolMath3.versor(h); for (int j1
	 * = 2; j1 < 600; j1++) { PoolMath3.set(i, h); PoolMath3.mul(i,
	 * PoolMath.intToYInt(j1)); if (playAreaBalls.containsPoint(selectedBall.a +
	 * i.a, selectedBall.b + i.b)) continue; tg = PoolMath.div(PoolMath3.abs(i),
	 * PoolMath3.abs(g)); break; }
	 * 
	 * int k1 = PoolMath.n_1; PoolMath3.set(V, poolBall.a, poolBall.b);
	 * PoolMath3.set(j, poolBall.vel.a, poolBall.vel.b);
	 * PoolMath3.set(firstColl, 0, 0); ballColided = false; PoolBall ball1 =
	 * null; Vector ball = table.getBallInPlayArea(); poolBall.sliding = true;
	 * poolBall.wX.set(0, 0); poolBall.uncolide(); for (int tick = 0; tick < 600
	 * && playAreaBalls.containsPoint(poolBall.a, poolBall.b); poolBall
	 * .nextPosition(), tick++) { for (int l1 = 0; l1 < ball.size(); l1++) {
	 * PoolBall _lcls171 = (PoolBall) ball.elementAt(l1); if
	 * (!PoolMath3.same(new YIPoint(_lcls171.a, _lcls171.b), selectedBall.a,
	 * selectedBall.b)) { int k2 = poolBall.timeToBall(_lcls171); if (k2 < k1 &&
	 * k2 > 0 && colBalls.indexOf(_lcls171) == -1) { k1 = k2; ball1 = _lcls171;
	 * colBalls.addElement(ball1); } } }
	 * 
	 * if (k1 < PoolMath.n_1) { PoolMath3.set(firstColl, poolBall.a,
	 * poolBall.b); poolBall.vel.mul(k1); PoolMath3.set(Y, poolBall.vel.a,
	 * poolBall.vel.b); PoolMath3.add(firstColl, Y); if
	 * (playAreaBalls.containsPoint(firstColl.a, firstColl.b)) { ballColided =
	 * true; index = ball1.index; } break; } poolBall.add(poolBall.vel); }
	 * 
	 * poolBall.setCoords(V.a, V.b); poolBall.vel.set(j.a, j.b); if
	 * (!ballColided) { poolBall.vel.mul(tg); poolBall.add(poolBall.vel);
	 * PoolMath3.set(S, poolBall.a, poolBall.b); PoolMath3.set(k, S.a -
	 * selectedBall.a, selectedBall.b - S.b); // if(S.a < playAreaBalls.left ||
	 * S.a > playAreaBalls.left + // playAreaBalls.width || S.b <
	 * playAreaBalls.top || S.b > // playAreaBalls.top + playAreaBalls.height)
	 * // PoolMath3.neg(k); YIPoint v1 = new YIPoint(k); YIPoint v2 = new
	 * YIPoint(S); line1.setCoords(new YIPoint(selectedBall.a, selectedBall.b),
	 * S); computeAim1(poolBall, v1, new YIPoint(0, 0), counter); } else {
	 * poolBall.setCoords(firstColl.a, firstColl.b); PoolMath3.set(S,
	 * poolBall.a, poolBall.b); line1.setCoords(new YIPoint(selectedBall.a,
	 * selectedBall.b), S); poolBall.vel.versor(); poolBall.vel.mul(0xa0000);
	 * PoolMath3.set(Y, poolBall.vel.a, poolBall.vel.b); poolBall.ov(ball1,
	 * ball1.vel); ball1.ov(poolBall, new y.po.YIVector(Y.a, Y.b)); int i2 =
	 * PoolMath3.distance(V, ball1.a, ball1.b); int j2 = r; if (i2 >
	 * PoolMath.intToYInt(40)) { j2 -= PoolMath.div(i2 - PoolMath.intToYInt(40),
	 * PoolMath .intToYInt(4)); if (j2 < 0) j2 = 0; } PoolMath3.set(k,
	 * poolBall.vel.a, poolBall.vel.b); j2 = PoolMath.intToYInt(500); if (k.a ==
	 * 0 && k.b == 0) { PoolMath3.set(S, poolBall.a, poolBall.b); } else {
	 * PoolMath3.versor(k); PoolMath3.mul(k, j2); PoolMath3.add(S, k); } YIPoint
	 * pv1 = new YIPoint(poolBall.a, poolBall.b); line2.setCoords(pv1, S); k =
	 * new YIPoint(ball1.vel.a, ball1.vel.b); PoolMath3.versor(k);
	 * PoolMath3.mul(k, PoolMath.intToYInt(500)); YIPoint pv2 = new
	 * YIPoint(ball1.a, ball1.b); YIPoint _lcls169_1 = new YIPoint(ball1.a,
	 * ball1.b); PoolMath3.add(_lcls169_1, k); line3.setCoords(pv2, _lcls169_1);
	 * ball1.stop(); } y.po.YLine yline1 = new
	 * YLine(PoolMath.yintToInt(line1.x.a), PoolMath .yintToInt(line1.x.b),
	 * PoolMath.yintToInt(line1.y.a), PoolMath .yintToInt(line1.y.b));
	 * y.po.YLine yline2 = new YLine(PoolMath.yintToInt(line2.x.a), PoolMath
	 * .yintToInt(line2.x.b), PoolMath.yintToInt(line2.y.a), PoolMath
	 * .yintToInt(line2.y.b)); y.po.YLine yline3 = new
	 * YLine(PoolMath.yintToInt(line3.x.a), PoolMath .yintToInt(line3.x.b),
	 * PoolMath.yintToInt(line3.y.a), PoolMath .yintToInt(line3.y.b));
	 * aim.add(yline1, yline2, yline3, poolBall.toYVector()); }
	 */

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		getParent().doEvent(event);
		return true;
	}

	/*
	 * public void computeAim2(y.po.PoolBall selectedBall, int counter[]) {
	 * counter[0]++; if (counter[0] > maxAimCount) return; YIPoint startPoint =
	 * new YIPoint(selectedBall.a, selectedBall.b); PoolBall poolBall =
	 * cloneBall(selectedBall); poolBall.vel.versor();
	 * poolBall.vel.mul(PoolMath.intToYInt(20)); YILine line = new YILine();
	 * YIPoint v1 = new YIPoint(); YIPoint v2 = new YIPoint(); YIPoint S = new
	 * YIPoint(); YIPoint Y = new YIPoint(); Vector ballInPlayArea =
	 * table.getBallInPlayArea(); Slot[] slots = table.getSlots(); Obstacle[]
	 * obstacles = table.getObstacles(); int minTimeToCollBall = PoolMath.n_1;
	 * int minTimeToObstacle = PoolMath.n_1; int cE = PoolMath.n_1; PoolBall cb
	 * = null; boolean inSlot = false; for (int tick = 0; tick < 600;
	 * poolBall.nextPosition(), tick++) { minTimeToCollBall = PoolMath.n_1;
	 * minTimeToObstacle = PoolMath.n_1; poolBall.checkSlots(slots); if
	 * (poolBall.inSlot()) { PoolMath3.set(firstColl, poolBall.a, poolBall.b);
	 * inSlot = true; break; }
	 * 
	 * int currTime1 = poolBall.timeToObstacle(obstacles); if (currTime1 <
	 * minTimeToObstacle && currTime1 >= 0) minTimeToObstacle = currTime1; for
	 * (int i3 = 0; i3 < ballInPlayArea.size(); i3++) { PoolBall balli3 =
	 * (PoolBall) ballInPlayArea.elementAt(i3); if (poolBall.index ==
	 * balli3.getIndex()) continue; int currTime2 = poolBall.timeToBall(balli3);
	 * if (currTime2 <= minTimeToCollBall && currTime2 > 0) { minTimeToCollBall
	 * = currTime2; cb = cloneBall(balli3); } } cE = Math.min(minTimeToCollBall,
	 * minTimeToObstacle); PoolMath3.set(firstColl, poolBall.a, poolBall.b); if
	 * (cE < PoolMath.n_1) { poolBall.vel.mul(cE); PoolMath3.set(Y,
	 * poolBall.vel.a, poolBall.vel.b); PoolMath3.add(firstColl, Y); break; }
	 * poolBall.add(poolBall.vel); } line.setCoords(startPoint, new
	 * YIPoint(firstColl.a, firstColl.b)); if (!inSlot) {
	 * poolBall.setCoords(firstColl.a, firstColl.b); if (minTimeToObstacle ==
	 * cE) { poolBall.pv(); computeAim2(poolBall, counter); } if
	 * (minTimeToCollBall == cE && cb != null) { PoolMath3.set(S, poolBall.a,
	 * poolBall.b); line.setCoords(startPoint, S); poolBall.vel.versor();
	 * poolBall.vel.mul(0xa0000);
	 * 
	 * PoolMath3.set(v1, poolBall.vel.a, poolBall.vel.b); PoolMath3.set(v2,
	 * cb.vel.a, cb.vel.b);
	 * 
	 * if (cb.index < poolBall.index) { cb.ov(poolBall, new y.po.YIVector(v1.a,
	 * v1.b)); poolBall.ov(cb, new y.po.YIVector(v2.a, v2.b)); } else {
	 * poolBall.ov(cb, new y.po.YIVector(v2.a, v2.b)); cb.ov(poolBall, new
	 * y.po.YIVector(v1.a, v1.b)); }
	 * 
	 * int[] counter1 = new int[1]; counter1[0] = counter[0];
	 * computeAim2(poolBall, counter1); computeAim2(cb, counter); } } y.po.YLine
	 * yline = new YLine(PoolMath.yintToInt(line.x.a), PoolMath
	 * .yintToInt(line.x.b), PoolMath.yintToInt(line.y.a), PoolMath
	 * .yintToInt(line.y.b)); aim.add(yline, new YLine(), new YLine(),
	 * poolBall.toYVector()); }
	 */

	/*
	 * public void computeAim(y.po.PoolBall selectedBall) { aim.clear();
	 * colBalls.removeAllElements(); int counter[] = new int[1]; counter[0] = 0;
	 * PoolBall ball = cloneBall(selectedBall); computeAim2(ball, counter);
	 * wb(); aim.invalidate(); }
	 */

	/*
	 * public void computeAim(YPoint m_ball, YVector m_cue) { aim.clear();
	 * colBalls.removeAllElements(); computeAim0(m_ball, m_cue); wb();
	 * aim.invalidate(); }
	 */

	public void fs(BallSprite a_pcls31[]) {
		for (BallSprite _lcls31 : a_pcls31) {
			addChildObject(_lcls31, _lcls31.getLeft1(), _lcls31.getTop1(), true);
			addChildObject(_lcls31.Pd(), _lcls31.getLeft1(), _lcls31.getTop1(),
					true);
			_lcls31.ae(20);
		}

	}

	public YIPoint getFirstColl() {
		if (ballColided) {
			firstColl1.setCoords(firstColl.a, firstColl.b);
			return firstColl1;
		}
		firstColl1.setCoords(0, 0);
		return firstColl1;
	}

	public int getIndex() {
		if (ballColided)
			return index;
		return -1;
	}

	public void handleTimer(long l1) {
		if (arrowChanged) {
			if (pa_v == 1)
				arrow.setCoords(pa_x, pa_w);
			else
				arrow.setCoords(pa_x, pa_w - 10);
			arrow.invalidate();
			pa_v = 1 - pa_v;
		}
	}

	public void ls() {
		arrowChanged = false;
		arrow.setVisible(false);
	}

	@Override
	public void m() {
		super.m();
		if (cueSprite != null) {
			cueSprite.cs_J = getWorldLeft(getContainer());
			cueSprite.K = getWorldTop(getContainer());
			cueSprite.Xk();
		}
	}

	public void ms() {
		aim.invalidate();
	}

	public void os(int i1, int j1) {
		pa_w = j1 - 20;
		pa_x = i1 - 5;
		arrow.setCoords(pa_x, pa_w);
		arrow.setVisible(true);
		arrowChanged = true;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		yahooGraphics.setColor(new Color(45, 109, 43));
		yahooGraphics.fillRect(0, 0, 550, 300);
		for (int i1 = 0; i1 < 550; i1++) {
			yahooGraphics.drawImage(pa_I, i1, -1, null);
			yahooGraphics.drawImage(pa_J, i1, 257, null);
		}

		for (int j1 = 0; j1 < 300; j1++) {
			yahooGraphics.drawImage(pa_K, -1, j1, null);
			yahooGraphics.drawImage(pa_L, 509, j1, null);
		}

		yahooGraphics.drawImage(pa_M, -1, -1, null);
		yahooGraphics.drawImage(pa_N, -1, 241, null);
		yahooGraphics.drawImage(pa_O, 239, -1, null);
		yahooGraphics.drawImage(pa_P, 239, 259, null);
		yahooGraphics.drawImage(pa_Q, 493, -1, null);
		yahooGraphics.drawImage(R, 495, 241, null);
		yahooGraphics.setColor(pa_D);
		yahooGraphics.drawLine(153, 36, 153, 262);
		if (pa_F) {
			yahooGraphics.setColor(new Color(10, 10, 10));
			yahooGraphics.fillOval(pa_o - 10, pa_q - 14, 25, 25);
			yahooGraphics.fillOval(pa_p - 10, pa_q - 14, 25, 25);
			yahooGraphics.fillRect(pa_o, pa_q - 14, 420, 25);
		}
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		setBackColor(pa_C);
	}

	public void setCueSprite(CueSprite _pcls58) {
		getContainer().addChildObject(_pcls58, 1, 1, true);
		cueSprite = _pcls58;
	}

}
