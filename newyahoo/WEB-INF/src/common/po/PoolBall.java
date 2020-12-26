// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Vector;

// Referenced classes of package y.po:
// _cls46, _cls57, _cls89, _cls121,
// _cls124, _cls33, _cls68, _cls164,
// _cls120, _cls16, _cls20, _cls48

public class PoolBall extends YIPoint implements IBall {
	static YIVector				e1;
	static final YIPoint		N	= new YIPoint(PoolMath.intToYInt(431),
											PoolMath.intToYInt(172));
	static int					O	= 0x13880;
	static boolean				P	= false;
	private static YRectangle	IN_AREA;
	static int					linearFriction;
	static int					n_0_4;
	static int					n_0_3;
	static int					rotationFriction;
	static int					sideRotationFriction;
	static int					n_6;
	static int					n_8;
	static int					Y;
	static int					Z;
	static int					n_0_25;
	static boolean				aa	= false;
	static YRectangle			bounceArea;
	static YRectangle			pocketArea;
	public static YRectangle	slot[];
	static {
		e1 = new YIVector(PoolMath.n_1, 0);
		slot = new YRectangle[6];
		slot[0] = toYRectangle(PoolConsts.Lb);
		slot[1] = toYRectangle(PoolConsts.Mb);
		slot[2] = toYRectangle(PoolConsts.Nb);
		slot[3] = toYRectangle(PoolConsts.Ob);
		slot[4] = toYRectangle(PoolConsts.Pb);
		slot[5] = toYRectangle(PoolConsts.Qb);
	}

	public static void Az(YIVector _pcls48) {
		int i1 = (int) PoolMath.yintToFloat(_pcls48.abs());
		int j1 = 0x10000;
		if (i1 > 5) {
			int k1 = i1 - 5;
			j1 += k1 * 579;
		}
		O = j1;
	}

	private static YRectangle toYRectangle(int ai[]) {
		return new YRectangle(PoolMath.intToYInt(ai[0]), PoolMath
				.intToYInt(ai[1]), PoolMath.intToYInt(ai[2]), PoolMath
				.intToYInt(ai[3]));
	}

	public int			radius;
	public YIVector		vel;
	public int			d;
	public int			type;
	public YIVector		wX;
	public Vel			h;
	public boolean		sliding;
	public int			index;
	public int			x0;
	public int			y0;
	PoolHandler			handler	= null;
	public Obstacle		obstacle;
	public int			o;
	public byte			m_inSlot;
	boolean				q;
	int					t;
	private YIVector	u;
	private YIVector	v;
	private YIVector	w;
	private YIVector	x;
	private YIVector	A;
	YIVector			C;
	YIPoint				D;
	public boolean		ballColided;
	YIPoint				firstColl;
	int					collBall;
	YIVector			H;
	Vector<String>		msgList;
	boolean				J;

	public boolean		K;

	public boolean		L;

	public PoolParams	pool;

	public PoolBall() {
		super(0, 0);
		vel = new YIVector();
		type = 0;
		wX = new YIVector();
		h = new Vel();
		sliding = true;
		x0 = 0;
		y0 = 0;
		o = 1;
		m_inSlot = -1;
		q = false;
		u = new YIVector();
		v = new YIVector();
		w = new YIVector();
		x = new YIVector();
		A = new YIVector();
		C = new YIVector();
		D = new YIPoint();
		ballColided = false;
		H = new YIVector();
		msgList = new Vector<String>();
		J = false;
		K = false;
		L = false;
		reset();
	}

	public PoolBall(int i1, int j1, int k1) {
		super(PoolMath.intToYInt(i1), PoolMath.intToYInt(j1));
		vel = new YIVector();
		type = 0;
		wX = new YIVector();
		h = new Vel();
		sliding = true;
		x0 = 0;
		y0 = 0;
		o = 1;
		m_inSlot = -1;
		q = false;
		u = new YIVector();
		v = new YIVector();
		w = new YIVector();
		x = new YIVector();
		A = new YIVector();
		C = new YIVector();
		D = new YIPoint();
		ballColided = false;
		H = new YIVector();
		msgList = new Vector<String>();
		J = false;
		K = false;
		L = false;
		radius = PoolMath.intToYInt(k1);
	}

	public void appendMsg(String s) {
		if (msgList.size() > 20)
			msgList.remove(0);
		msgList.add(s);
	}

	public void assign(PoolBall ball) {
		index = ball.index;
		x0 = ball.x0;
		y0 = ball.y0;
		a = ball.a;
		b = ball.b;
		m_inSlot = ball.m_inSlot;
		radius = ball.radius;
		d = ball.d;
		type = ball.type;
		reset();
	}

	public Vel Bv() {
		return h;
	}

	public void checkSlots(Slot[] slots) {
		if (pocketArea != null
				&& pocketArea.containsPoint(
						(int) PoolMath.yintToFloat(super.a), (int) PoolMath
								.yintToFloat(super.b)) || !isMoving())
			return;
		for (Slot slot2 : slots) {
			if (slot2.Qy(this)) {
				Mv(slot2);
				break;
			}
			if (slot2.Oy(this))
				vel.add(slot2.My(this));
		}
	}

	public IBall Copy() {
		PoolBall result = new PoolBall();
		result.setCoords(super.a, super.b);
		result.radius = radius;
		result.index = index;
		result.x0 = x0;
		result.y0 = y0;
		result.m_inSlot = m_inSlot;
		result.d = d;
		result.type = type;
		return result;
	}

	public int getIndex() {
		return index;
	}

	public int getRadius() {
		return radius;
	}

	public byte getSlot() {
		return m_inSlot;
	}

	public int getType() {
		return type;
	}

	public YIVector getVel() {
		return vel;
	}

	public int getX() {
		return super.a;
	}

	public int getY() {
		return super.b;
	}

	public float getYIntX() {
		return PoolMath.yintToFloat(super.a);
	}

	public float getYIntY() {
		return PoolMath.yintToFloat(super.b);
	}

	public boolean inSlot() {
		return m_inSlot >= 0;
	}

	public boolean isInitPos() {
		return super.a == x0 && super.b == y0;
	}

	public boolean isMoving() {
		return vel.a != 0 || vel.b != 0;
	}

	public boolean Jv() {
		return type == 2048;
	}

	public boolean Kv(int i1) {
		if (isMoving()) {
			vel.setTo(w);
			w.mul(i1);
			add(w);
			if (K && m_inSlot == -1 && !IN_AREA.containsPoint(super.a, super.b)) {
				boolean flag = false;
				for (int j1 = 0; j1 < 6; j1++) {
					if (!slot[j1].containsPoint(super.a, super.b))
						continue;
					flag = true;
					break;
				}

				if (!flag) {
					appendMsg(" outOfBoundFix" + this);
					nv();
					YIVector _lcls48 = vel.je();
					_lcls48.neg2();
					_lcls48.mul(PoolMath.floatToYInt(1.2F));
					YIVector _lcls48_1 = new YIVector(this, (YIPoint) pool
							.getProperty("CENTER_POINT"));
					_lcls48_1.versor();
					_lcls48_1.mul(PoolMath.n_2);
					Vector<IBall> vector = pool.getBallInPlayArea();
					for (boolean flag1 = false; !((YRectangle) pool
							.getProperty("PLAY_AREA_BALLS")).containsPoint(
							super.a, super.b)
							|| flag1;) {
						add(_lcls48_1);
						boolean flag2 = false;
						for (int k1 = 0; k1 < vector.size(); k1++) {
							IBall _lcls124 = vector.elementAt(k1);
							if (!equals(_lcls124)
									&& distance((YIPoint) _lcls124) < PoolMath
											.intToYInt(20))
								flag2 = true;
						}

						if (flag2)
							flag1 = true;
						else
							flag1 = false;
					}

				}
			}
		}
		if (isMoving() || wX.abs() > 0 || h.he() > 0) {
			if (handler != null)
				handler.handleSetPos(index, 1, super.a, super.b, wX, h);
			return true;
		}
		return false;
	}

	public void Mv(Slot _pcls164) {
		vel.set(0, 0);
		wX.set(0, 0);
		h.setVel(0);
		super.a = ((YIPoint) _pcls164).a;
		super.b = ((YIPoint) _pcls164).b;
		m_inSlot = _pcls164.Ny();
		if (handler != null)
			handler.handleSetPos(index, 2, super.a, super.b, null, null);
	}

	public void nextPosition() {
		if (isMoving() || wX.abs() > 0 || h.he() > 0)
			if (sliding)
				uz();
			else
				tz();
		h.ne(sideRotationFriction);
	}

	public void nv() {
		msgList.clear();
		t = 0;
	}

	public void ov(IBall ball, YIVector v) {
		PoolBall _lcls171 = (PoolBall) ball;
		if (ballColided && collBall == _lcls171.getIndex())
			setPos(firstColl.a, firstColl.b);
		if (L) {
			A.setFrom(vel);
			if (P) {
				if (isMoving())
					Az(vel);
				else
					Az(v);
				P = false;
			}
		}
		C.setFrom(this, _lcls171);
		C.versor();
		long l1 = vel.mul(C);
		long l2 = v.mul(C);
		long l3 = l1 - l2;
		C.mul((int) l3);
		vel.subFrom(C);
		if (L && distance(N) < PoolMath.intToYInt(40) && index < 11
				&& (A.norm() < 5L || A.zf(vel) > PoolMath.pi / 4))
			vel.mul(vel.norm() >= PoolMath.n_1 ? O : O
					* (5 - (int) PoolMath.yintToFloat(vel.abs() * 5)));
		int i1 = Math.abs(vel.proj(x));
		C.versor();
		zz(i1, C);
		uncolide();
	}

	public void pv() {
		if (obstacle == null)
			return;
		if (vel.norm() > PoolMath.four && handler != null)
			handler.handleColl(2);
		YIVector _lcls48 = obstacle.Rl(this);
		int i1 = 0;
		int j1 = Math.abs(vel.proj(_lcls48));
		int k1 = PoolMath.mul(j1, n_0_25);
		if (wX.noIsNull()) {
			int l1 = PoolMath.mul(j1, n_6);
			_lcls48.setTo(C);
			C.versor();
			C.mul(l1);
			i1 = Math.abs(C.proj(wX));
		}
		int i2 = vel.angleFrom(_lcls48);
		vel.rotate(PoolMath.mul(i2, PoolMath.n_2));
		vel.neg2();
		vel.sub(k1);
		if (h.he() > 0) {
			int k2 = PoolMath.mul(n_8, j1);
			k2 = h.ke() <= 0 ? -k2 : k2;
			int j2 = h.he() <= Math.abs(k2) ? h.ke() : k2;
			_lcls48.setTo(C);
			C.rotate(-PoolMath.c);
			C.versor();
			C.mul(j2);
			vel.add(C);
			h.ne(j2);
		}
		if (i1 > 0)
			wX.sub(i1);
		sliding = true;
		uncolide();
	}

	public void Pv(boolean flag) {
		L = flag;
	}

	@Override
	public void read(DataInput input) throws IOException {
		index = input.readInt();
		x0 = input.readInt();
		y0 = input.readInt();
		super.a = input.readInt();
		super.b = input.readInt();
		m_inSlot = input.readByte();
		radius = input.readInt();
		d = input.readInt();
		type = input.readInt();
		reset();
	}

	public void reset() {
		vel = new YIVector();
		wX = new YIVector();
		h = new Vel();
	}

	public void resetBall() {
		vel.set(0, 0);
		wX.set(0, 0);
		h.set(0, 0);
		h.setVel(0);
		m_inSlot = -1;
		setCoords(x0, y0);
		if (handler != null)
			handler.handleSetPos(index, 0, super.a, super.b, wX, h);
	}

	public void setHandler(PoolHandler _pcls121) {
		handler = _pcls121;
	}

	public void setNull() {
		vel.set(0, 0);
		wX.set(0, 0);
		h.setVel(0);
		v.set(0, 0);
		u.set(0, 0);
		sliding = false;
	}

	public void setPool(PoolParams _pcls57) {
		pool = _pcls57;
		if (!aa)
			updateParams();
	}

	public void setPos(int i1, int j1) {
		setCoords(i1, j1);
		if (handler != null)
			handler.handleSetPos(index, 1, i1, j1, wX, h);
	}

	public void setRadius(int i1) {
		radius = PoolMath.intToYInt(i1);
	}

	public void setSlot(byte byte0) {
		m_inSlot = byte0;
	}

	public void start(YIPoint cueDist, YIPoint englishDist, YIPoint _firstColl,
			int _collBall) {
		int j1 = distance(cueDist) - radius;
		if (j1 <= 0)
			return;
		int k1 = englishDist.abs();
		int l1 = PoolMath.div(k1, radius);
		int i2;
		if (k1 != 0) {
			i2 = PoolMath.div(PoolMath.mul(PoolMath.mul(PoolMath
					.floatToYInt(30F), j1), PoolMath.floatToYInt(0.001F)),
					PoolMath.floatToYInt(0.157F));
			int j2 = PoolMath
					.sqrt(PoolMath.intToYInt(1) - PoolMath.mul(l1, l1));
			i2 = PoolMath.mul(i2, j2);
		}
		else {
			i2 = PoolMath.div(PoolMath.mul(PoolMath.mul(PoolMath
					.floatToYInt(30F), j1), PoolMath.floatToYInt(0.001F)),
					PoolMath.floatToYInt(0.157F));
		}
		YIVector _lcls48 = new YIVector(super.a - cueDist.a, super.b
				- cueDist.b);
		_lcls48.versor();
		_lcls48.mul(i2);
		vel.setFrom(_lcls48);
		wX.set(0, 0);
		h.set(0, 0);
		if (k1 != 0) {
			int k2 = PoolMath.div(PoolMath.mul(PoolMath.floatToYInt(30F),
					PoolMath.mul(j1, PoolMath.mul(PoolMath.floatToYInt(0.001F),
							PoolMath.mul(PoolMath.floatToYInt(2.5F), l1)))),
					PoolMath.mul(PoolMath.floatToYInt(0.157F), radius));
			YIVector _lcls48_1 = new YIVector(englishDist.a, -1 * englishDist.b);
			_lcls48_1.versor();
			_lcls48_1.mul(k2);
			YIVector _lcls48_2 = vel.je();
			_lcls48_2.versor();
			_lcls48_2.mul(PoolMath.mul(_lcls48_1.b, radius));
			wX.setFrom(_lcls48_2);
			h.setVel(PoolMath.mul(_lcls48_1.a, PoolMath.n_2));
		}
		sliding = true;
		uncolide();
		if (_firstColl.a != 0) {
			ballColided = true;
			firstColl = _firstColl;
			collBall = _collBall;
		}
		else {
			ballColided = false;
		}
		if (L) {
			e1 = vel.setTo(e1);
			e1.versor();
			if (e1.a == PoolMath.n_1)
				e1.mul(PoolMath.n_1 / 2);
			P = true;
		}
		else {
			P = false;
		}
		if (handler != null)
			handler.handleColl(3);
		nv();
	}

	public void stop() {
		vel.set(0, 0);
		wX.set(0, 0);
		h.setVel(0);
		if (handler != null)
			handler.handleSetPos(index, 3, super.a, super.b, wX, h);
	}

	public int timeToBall(IBall _pcls124) {
		PoolBall _lcls171 = (PoolBall) _pcls124;
		int d = radius + _lcls171.radius;
		int deltaVx = vel.a - _lcls171.vel.a;
		int deltaVy = vel.b - _lcls171.vel.b;
		int deltaX = super.a - ((YIPoint) _lcls171).a;
		int deltaY = super.b - ((YIPoint) _lcls171).b;
		if (deltaVx == 0 && deltaVy == 0)
			return PoolMath.n_2;
		if (Math.abs(deltaX) - Math.abs(deltaVx) > d + PoolMath.n_5
				&& Math.abs(deltaY) - Math.abs(deltaVy) > d + PoolMath.n_5)
			return PoolMath.n_2;
		long l2 = d;
		long l3 = deltaVx;
		long l4 = deltaVy;
		long l5 = deltaX;
		long l6 = deltaY;
		long a = PoolMath2.mul(l3, l3) + PoolMath2.mul(l4, l4);
		if (a == 0L)
			return PoolMath.n_2;
		long b = PoolMath2.mul(PoolMath2.n_2, PoolMath2.mul(l5, l3)
				+ PoolMath2.mul(l6, l4));
		long c = PoolMath2.mul(l5, l5) + PoolMath2.mul(l6, l6)
				- PoolMath2.mul(l2, l2);
		long delta = PoolMath2.mul(b, b)
				- PoolMath2.mul(PoolMath2.n_4, PoolMath2.mul(a, c));
		if (delta < 0L)
			return PoolMath.n_2;
		long sqrtDelta = PoolMath2.sqrt(delta);
		long x2 = PoolMath2
				.div(-b - sqrtDelta, PoolMath2.mul(PoolMath2.n_2, a));
		int k2 = PoolMath2.toInt(x2);
		return k2;
	}

	public int timeToObstacle(Obstacle obstacles[]) {
		int i1 = PoolMath.n_100;
		if (vel.abs() == 0)
			return i1;
		if (bounceArea.containsPoint((int) PoolMath
				.yintToFloat(super.a + vel.a), (int) PoolMath
				.yintToFloat(super.b + vel.b))) {
			K = false;
			return i1;
		}
		K = true;
		obstacle = null;
		for (Obstacle _lcls68 : obstacles) {
			int k1 = _lcls68.Ul(this);
			if (k1 < i1 && k1 >= 0) {
				i1 = k1;
				obstacle = _lcls68;
			}
		}
		return i1;
	}

	@Override
	public String toString() {
		String s = "Ball " + getIndex();
		s = s + "(" + PoolMath.yintToFloat(super.a) + ","
				+ PoolMath.yintToFloat(super.b) + ")";
		// s = s + "(" + a + "," + b + ")";
		s = s + "vel=(" + PoolMath.yintToFloat(vel.a) + " "
				+ PoolMath.yintToFloat(vel.b) + ")";
		s = s + " wX=(" + PoolMath.yintToFloat(wX.a) + ","
				+ PoolMath.yintToFloat(wX.b) + ")";
		s = s + " sliding=" + sliding + " m_inSlot=" + m_inSlot;
		return s;
	}

	public void tz() {
		vel.sub(rotationFriction);
		vel.setTo(wX);
		if (ballColided) {
			D.setCoords(firstColl.a, firstColl.b);
			C.setFrom(this, D);
			C.versor();
			C.mul(vel.abs());
			C.setTo(vel);
			C.setTo(wX);
		}
	}

	public void uncolide() {
		ballColided = false;
	}

	private void updateParams() {
		IN_AREA = (YRectangle) pool.getProperty("IN_AREA");
		linearFriction = pool.getIntProperty("linearFriction");
		n_0_4 = PoolMath.floatToYInt(0.4F);
		n_0_3 = PoolMath.floatToYInt(0.3F);
		rotationFriction = pool.getIntProperty("rotationFriction");
		sideRotationFriction = pool.getIntProperty("sideRotationFriction");
		n_6 = PoolMath.floatToYInt(6F);
		n_8 = PoolMath.floatToYInt(8F);
		Y = PoolMath.floatToYInt(0.5F);
		Z = PoolMath.floatToYInt(0.1F);
		n_0_25 = PoolMath.floatToYInt(0.25F);
		bounceArea = (YRectangle) pool.getProperty("OUT_OF_BOUNCE_AREA");
		pocketArea = (YRectangle) pool.getProperty("OUT_OF_POCKET_AREA");
		aa = true;
	}

	public void uz() {
		byte byte0 = (byte) (vel.Nf(wX) ? -1 : 1);
		vel.setTo(v);
		int i1 = vel.abs();
		int j1 = wX.abs();
		int k1 = j1 - i1;
		int l1 = PoolMath.mul(linearFriction, n_0_4);
		if (vel.Of(wX) || byte0 == -1 || j1 == 0) {
			vel.setVersor(v);
			if (byte0 == 1) {
				if (k1 < 0) {
					if (k1 * -1 <= linearFriction + l1) {
						vel.sub(linearFriction);
						wX.setFrom(vel);
					}
					else {
						vel.sub(l1);
						if (!wX.noIsNull())
							vel.setVersor(wX);
						wX.add(linearFriction);
					}
				}
				else if (k1 <= linearFriction + l1) {
					vel.sub(l1);
					wX.setFrom(vel);
				}
				else {
					wX.sub(linearFriction);
					vel.add(l1);
				}
			}
			else {
				wX.sub(linearFriction);
				vel.sub(l1);
			}
			if (vel.Ef(wX))
				sliding = false;
		}
		else {
			wX.setTo(u);
			vel.setTo(v);
			v.absMul(n_0_3);
			v.mul(linearFriction);
			u.absMul(n_0_3);
			u.mul(linearFriction);
			wX.subFrom(u);
			wX.add(v);
			if (!wX.Of(vel)) {
				vel.subFrom(v);
				vel.add(u);
			}
			else {
				int i2 = wX.abs();
				vel.setTo(wX);
				wX.versor();
				wX.absMul(i2);
			}
		}
	}

	public int vv() {
		return d;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeInt(index);
		output.writeInt(x0);
		output.writeInt(y0);
		output.writeInt(super.a);
		output.writeInt(super.b);
		output.writeByte(m_inSlot);
		output.writeInt(radius);
		output.writeInt(d);
		output.writeInt(type);
	}

	public void zz(int i1, YIVector _pcls48) {
		int j1 = PoolMath.mul(i1, Y);
		_pcls48.setTo(w);
		w.versor();
		w.mul(j1);
		int k1 = w.proj(wX);
		if (k1 > 0)
			wX.sub(k1);
		if (h.he() > 0) {
			int i2 = PoolMath.mul(Z, i1);
			i2 = h.ke() <= 0 ? -i2 : i2;
			int l1 = h.he() <= Math.abs(i2) ? h.ke() : i2;
			h.ne(l1);
		}
		vel.mul(55536);
		if (vel.norm() < 5L) {
			vel.set(0, 0);
			wX.set(0, 0);
		}
	}
}
