// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package common.po2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Vector;

// Referenced classes of package y.po:
// _cls46, _cls57, _cls89, _cls121,
// _cls124, _cls33, _cls68, _cls164,
// _cls120, _cls16, _cls20, _cls48

public class PoolBall extends YPoint implements IBall {
	static YVector				e1;
	static final YPoint			N	= new YPoint(431, 172);
	static float				O	= 0x13880;
	static boolean				P	= false;
	private static YRectangle	IN_AREA;
	static float				linearFriction;
	static float				rotationFriction;
	static float				sideRotationFriction;
	static boolean				aa	= false;
	static YRectangle			bounceArea;
	static YRectangle			pocketArea;
	public static YRectangle	slot[];
	static {
		e1 = new YVector(1, 0);
		slot = new YRectangle[6];
		slot[0] = toYRectangle(PoolConsts.Lb);
		slot[1] = toYRectangle(PoolConsts.Mb);
		slot[2] = toYRectangle(PoolConsts.Nb);
		slot[3] = toYRectangle(PoolConsts.Ob);
		slot[4] = toYRectangle(PoolConsts.Pb);
		slot[5] = toYRectangle(PoolConsts.Qb);
	}

	public static void Az(YVector _pcls48) {
		float i1 = _pcls48.abs();
		int j1 = 0x1000000;
		if (i1 > 5) {
			float k1 = i1 - 5;
			j1 += k1 * 579;
		}
		O = j1;
	}

	private static YRectangle toYRectangle(float ai[]) {
		return new YRectangle(ai[0], ai[1], ai[2], ai[3]);
	}

	public float		radius;
	public YVector		vel;
	public int			d;
	public int			type;
	public YVector		wX;
	public Vel			h;
	public boolean		sliding;
	public int			index;
	public float		x0;
	public float		y0;
	PoolHandler			handler	= null;
	public Obstacle		obstacleToCollide;
	public int			o;
	public byte			m_inSlot;
	boolean				q;
	int					t;
	private YVector		u;
	private YVector		v;
	private YVector		tempVel;
	private YVector		x_;
	private YVector		A;
	YVector				tempDeltaPosFromFirstColl;
	YPoint				tempFirstColl;
	public boolean		ballColided;
	YPoint				firstColl;
	int					collBall;
	YVector				H;
	Vector<String>		msgList;
	boolean				J;

	public boolean		willCollideWithAObstacle;

	public boolean		L;

	public PoolParams	pool;

	public PoolBall() {
		super(0, 0);
		vel = new YVector();
		type = 0;
		wX = new YVector();
		h = new Vel();
		sliding = true;
		x0 = 0;
		y0 = 0;
		o = 1;
		m_inSlot = -1;
		q = false;
		u = new YVector();
		v = new YVector();
		tempVel = new YVector();
		x_ = new YVector();
		A = new YVector();
		tempDeltaPosFromFirstColl = new YVector();
		tempFirstColl = new YPoint();
		ballColided = false;
		H = new YVector();
		msgList = new Vector<String>();
		J = false;
		willCollideWithAObstacle = false;
		L = false;
		reset();
	}

	public PoolBall(float i1, float j1, float k1) {
		super(i1, j1);
		vel = new YVector();
		type = 0;
		wX = new YVector();
		h = new Vel();
		sliding = true;
		x0 = 0;
		y0 = 0;
		o = 1;
		m_inSlot = -1;
		q = false;
		u = new YVector();
		v = new YVector();
		tempVel = new YVector();
		x_ = new YVector();
		A = new YVector();
		tempDeltaPosFromFirstColl = new YVector();
		tempFirstColl = new YPoint();
		ballColided = false;
		H = new YVector();
		msgList = new Vector<String>();
		J = false;
		willCollideWithAObstacle = false;
		L = false;
		radius = k1;
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
		x = ball.x;
		y = ball.y;
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
		if (pocketArea != null && pocketArea.containsPoint(x, y) || !isMoving())
			return;
		for (Slot slot2 : slots) {
			if (slot2.contains(this)) {
				putOnSlot(slot2);
				break;
			}
			if (slot2.containsIncludingTheBorder(this))
				vel.add(slot2.getVelToMySelf(this));
		}
	}

	public IBall Copy() {
		PoolBall result = new PoolBall();
		result.setCoords(super.x, super.y);
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

	public float getRadius() {
		return radius;
	}

	public byte getSlot() {
		return m_inSlot;
	}

	public int getType() {
		return type;
	}

	public YVector getVel() {
		return vel;
	}

	public float getX() {
		return super.x;
	}

	public float getY() {
		return super.y;
	}

	public boolean inSlot() {
		return m_inSlot >= 0;
	}

	public boolean isInitPos() {
		return super.x == x0 && super.y == y0;
	}

	public boolean isMoving() {
		return vel.x != 0 || vel.y != 0;
	}

	public boolean Jv() {
		return type == 2048;
	}

	public boolean Kv(float t) {
		if (isMoving()) {
			vel.setCoordsTo(tempVel);
			tempVel.mul(t);
			add(tempVel);
			if (willCollideWithAObstacle && m_inSlot == -1
					&& !IN_AREA.containsPoint(super.x, super.y)) {
				boolean flag = false;
				for (int j1 = 0; j1 < 6; j1++) {
					if (!slot[j1].containsPoint(super.x, super.y))
						continue;
					flag = true;
					break;
				}

				if (!flag) {
					appendMsg(" outOfBoundFix" + this);
					resetMsgList();
					YVector vel1 = (YVector) vel.clone();
					vel1.neg();
					vel1.mul(1.2F);
					YVector _lcls48_1 = new YVector(this, (YPoint) pool
							.getProperty("CENTER_POINT"));
					_lcls48_1.versor();
					_lcls48_1.mul(2);
					Vector<IBall> vector = pool.getBallInPlayArea();
					for (boolean flag1 = false; !((YRectangle) pool
							.getProperty("PLAY_AREA_BALLS")).containsPoint(
							super.x, super.y)
							|| flag1;) {
						add(_lcls48_1);
						boolean flag2 = false;
						for (int k1 = 0; k1 < vector.size(); k1++) {
							IBall _lcls124 = vector.elementAt(k1);
							if (!equals(_lcls124)
									&& distance((YPoint) _lcls124) < 20)
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
		if (isMoving() || wX.abs() > 0 || h.getAbsVel() > 0) {
			if (handler != null)
				handler.handleSetPos(index, 1, super.x, super.y, wX, h);
			return true;
		}
		return false;
	}

	public void nextPosition() {
		if (isMoving() || wX.abs() > 0 || h.getAbsVel() > 0)
			if (sliding)
				uz();
			else
				tz();
		h.ne(sideRotationFriction);
	}

	public void ov(IBall ball, YVector v) {
		PoolBall _lcls171 = (PoolBall) ball;
		if (ballColided && collBall == _lcls171.getIndex())
			setPos(firstColl.x, firstColl.y);
		if (L) {
			A.setCoords(vel);
			if (P) {
				if (isMoving())
					Az(vel);
				else
					Az(v);
				P = false;
			}
		}
		tempDeltaPosFromFirstColl.setCoords(this, _lcls171);
		tempDeltaPosFromFirstColl.versor();
		float l1 = vel.mul(tempDeltaPosFromFirstColl);
		float l2 = v.mul(tempDeltaPosFromFirstColl);
		float l3 = l1 - l2;
		tempDeltaPosFromFirstColl.mul(l3);
		vel.sub(tempDeltaPosFromFirstColl);
		if (L && distance(N) < 40 && index < 11
				&& (A.abs() < 5F / 65536F || A.angle(vel) > Math.PI / 4))
			vel.mul((vel.abs() >= 1 ? O : O * (5 - vel.abs() * 5)) / 65536F);
		float i1 = Math.abs(vel.proj(x_));
		tempDeltaPosFromFirstColl.versor();
		zz(i1, tempDeltaPosFromFirstColl);
		uncolide();
	}

	public void putOnSlot(Slot slot) {
		vel.setCoords(0, 0);
		wX.setCoords(0, 0);
		h.setVel(0);
		super.x = slot.x;
		super.y = slot.y;
		m_inSlot = slot.getIndex();
		if (handler != null)
			handler.handleSetPos(index, 2, super.x, super.y, null, null);
	}

	public void pv() {
		if (obstacleToCollide == null)
			return;
		if (vel.abs() > 4 && handler != null)
			handler.handleColl(2);
		YVector _lcls48 = obstacleToCollide.Rl(this);
		float i1 = 0;
		float j1 = vel.proj(_lcls48);
		float k1 = j1 * 0.25F;
		if (wX.noIsNull()) {
			float l1 = j1 * 6;
			_lcls48.setCoordsTo(tempDeltaPosFromFirstColl);
			tempDeltaPosFromFirstColl.versor();
			tempDeltaPosFromFirstColl.mul(l1);
			i1 = Math.abs(tempDeltaPosFromFirstColl.proj(wX));
		}
		double i2 = vel.angle(_lcls48);
		vel.rotate(i2 * 2);
		vel.neg();
		vel.sub(k1);
		if (h.getAbsVel() > 0) {
			float k2 = 8 * j1;
			k2 = h.getVel() <= 0 ? -k2 : k2;
			float j2 = h.getAbsVel() <= Math.abs(k2) ? h.getVel() : k2;
			_lcls48.setCoordsTo(tempDeltaPosFromFirstColl);
			tempDeltaPosFromFirstColl.rotate(-Math.PI / 2);
			tempDeltaPosFromFirstColl.versor();
			tempDeltaPosFromFirstColl.mul(j2);
			vel.add(tempDeltaPosFromFirstColl);
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
		x0 = input.readFloat();
		y0 = input.readFloat();
		super.x = input.readFloat();
		super.y = input.readFloat();
		m_inSlot = input.readByte();
		radius = input.readFloat();
		d = input.readInt();
		type = input.readInt();
		reset();
	}

	public void reset() {
		vel = new YVector();
		wX = new YVector();
		h = new Vel();
	}

	public void resetBall() {
		vel.setCoords(0, 0);
		wX.setCoords(0, 0);
		h.setCoords(0, 0);
		h.setVel(0);
		m_inSlot = -1;
		setCoords(x0, y0);
		if (handler != null)
			handler.handleSetPos(index, 0, super.x, super.y, wX, h);
	}

	public void resetMsgList() {
		msgList.clear();
		t = 0;
	}

	public void setHandler(PoolHandler _pcls121) {
		handler = _pcls121;
	}

	public void setNull() {
		vel.setCoords(0, 0);
		wX.setCoords(0, 0);
		h.setVel(0);
		v.setCoords(0, 0);
		u.setCoords(0, 0);
		sliding = false;
	}

	public void setPool(PoolParams _pcls57) {
		pool = _pcls57;
		if (!aa)
			updateParams();
	}

	public void setPos(float i1, float j1) {
		setCoords(i1, j1);
		if (handler != null)
			handler.handleSetPos(index, 1, i1, j1, wX, h);
	}

	public void setRadius(float i1) {
		radius = i1;
	}

	public void setSlot(byte byte0) {
		m_inSlot = byte0;
	}

	public void start(YPoint cueDist, YPoint englishDist, YPoint _firstColl,
			int _collBall) {
		float j1 = distance(cueDist) - radius;
		if (j1 <= 0)
			return;
		float k1 = englishDist.abs();
		float l1 = k1 / radius;
		float i2;
		if (k1 != 0) {
			i2 = 30F * j1 * 0.001F / 0.157F;
			float j2 = (float) Math.sqrt(1 - l1 * l1);
			i2 = i2 * j2;
		}
		else
			i2 = 30F * j1 * 0.001F / 0.157F;
		YVector _lcls48 = new YVector(super.x - cueDist.x, super.y - cueDist.y);
		_lcls48.versor();
		_lcls48.mul(i2);
		vel.setCoords(_lcls48);
		wX.setCoords(0, 0);
		h.setCoords(0, 0);
		if (k1 != 0) {
			float k2 = 30F * j1 * 0.001F * 2.5F * l1 / (0.157F * radius);
			YVector _lcls48_1 = new YVector(englishDist.x, -englishDist.y);
			_lcls48_1.versor();
			_lcls48_1.mul(k2);
			YVector _lcls48_2 = (YVector) vel.clone();
			_lcls48_2.versor();
			_lcls48_2.mul(_lcls48_1.y * radius);
			wX.setCoords(_lcls48_2);
			h.setVel(2 * _lcls48_1.x);
		}
		sliding = true;
		uncolide();
		if (_firstColl.x != 0) {
			ballColided = true;
			firstColl = _firstColl;
			collBall = _collBall;
		}
		else {
			ballColided = false;
		}
		if (L) {
			e1 = vel.setCoordsTo(e1);
			e1.versor();
			if (e1.x == 1)
				e1.mul(0.5F);
			P = true;
		}
		else {
			P = false;
		}
		if (handler != null)
			handler.handleColl(3);
		resetMsgList();
	}

	public void stop() {
		vel.setCoords(0, 0);
		wX.setCoords(0, 0);
		h.setVel(0);
		if (handler != null)
			handler.handleSetPos(index, 3, super.x, super.y, wX, h);
	}

	public float timeToBall(IBall _pcls124) {
		float d = radius + _pcls124.getRadius();
		float deltaVx = vel.x - _pcls124.getVel().x;
		float deltaVy = vel.y - _pcls124.getVel().y;
		float deltaX = super.x - _pcls124.getX();
		float deltaY = super.y - _pcls124.getY();
		if (deltaVx == 0 && deltaVy == 0)
			return 2;
		if (Math.abs(deltaX) - Math.abs(deltaVx) > d + 5
				&& Math.abs(deltaY) - Math.abs(deltaVy) > d + 5)
			return 2;
		float a = deltaVx * deltaVx + deltaVy * deltaVy;
		if (a == 0)
			return 2;
		float b = 2 * (deltaX * deltaVx + deltaY * deltaVy);
		float c = deltaX * deltaX + deltaY * deltaY - d * d;
		float delta = b * b - 4 * a * c;
		if (delta < 0)
			return 2;
		float sqrtDelta = (float) Math.sqrt(delta);
		float t = (-b - sqrtDelta) / (2 * a);
		return t;
	}

	public float timeToObstacle(Obstacle obstacles[]) {
		float t = 100;
		if (vel.x == 0 && vel.y == 0)
			return t;
		if (bounceArea.containsPoint(super.x + vel.x, super.y + vel.y)) {
			willCollideWithAObstacle = false;
			return t;
		}
		willCollideWithAObstacle = true;
		obstacleToCollide = null;
		for (Obstacle obstacle : obstacles) {
			float t1 = obstacle.timeToBall(this);
			if (t1 < t && t1 >= 0) {
				t = t1;
				obstacleToCollide = obstacle;
			}
		}
		return t;
	}

	@Override
	public String toString() {
		String s = "Ball " + getIndex();
		s = s + "(" + super.x + "," + super.y + ")";
		// s = s + "(" + a + "," + b + ")";
		s = s + "vel=(" + vel.x + " " + vel.y + ")";
		s = s + " wX=(" + wX.x + "," + wX.y + ")";
		s = s + " sliding=" + sliding + " m_inSlot=" + m_inSlot;
		return s;
	}

	public void tz() {
		vel.sub(rotationFriction);
		vel.setCoordsTo(wX);
		if (ballColided) {
			tempFirstColl.setCoords(firstColl.x, firstColl.y);
			tempDeltaPosFromFirstColl.setCoords(this, tempFirstColl);
			tempDeltaPosFromFirstColl.versor();
			tempDeltaPosFromFirstColl.mul(vel.abs());
			tempDeltaPosFromFirstColl.setCoordsTo(vel);
			tempDeltaPosFromFirstColl.setCoordsTo(wX);
		}
	}

	public void uncolide() {
		ballColided = false;
	}

	private void updateParams() {
		IN_AREA = (YRectangle) pool.getProperty("IN_AREA");
		linearFriction = pool.getFloatProperty("linearFriction");
		rotationFriction = pool.getFloatProperty("rotationFriction");
		sideRotationFriction = pool.getFloatProperty("sideRotationFriction");
		bounceArea = (YRectangle) pool.getProperty("OUT_OF_BOUNCE_AREA");
		pocketArea = (YRectangle) pool.getProperty("OUT_OF_POCKET_AREA");
		aa = true;
	}

	public void uz() {
		byte byte0 = (byte) (vel.Nf(wX) ? -1 : 1);
		vel.setCoordsTo(v);
		float i1 = vel.abs();
		float j1 = wX.abs();
		float k1 = j1 - i1;
		float l1 = 0.4F;
		if (vel.Of(wX) || byte0 == -1 || j1 == 0) {
			vel.setVersor(v);
			if (byte0 == 1) {
				if (k1 < 0) {
					if (k1 * -1 <= linearFriction + l1) {
						vel.sub(linearFriction);
						wX.setCoords(vel);
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
					wX.setCoords(vel);
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
			if (vel.equalsWithRounding(wX))
				sliding = false;
		}
		else {
			wX.setCoordsTo(u);
			vel.setCoordsTo(v);
			v.absMul(0.3F);
			v.mul(linearFriction);
			u.absMul(0.3F);
			u.mul(linearFriction);
			wX.sub(u);
			wX.add(v);
			if (!wX.Of(vel)) {
				vel.sub(v);
				vel.add(u);
			}
			else {
				float i2 = wX.abs();
				vel.setCoordsTo(wX);
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
		output.writeFloat(x0);
		output.writeFloat(y0);
		output.writeFloat(super.x);
		output.writeFloat(super.y);
		output.writeByte(m_inSlot);
		output.writeFloat(radius);
		output.writeInt(d);
		output.writeInt(type);
	}

	public void zz(float i1, YVector _pcls48) {
		float j1 = i1 * 0.5F;
		_pcls48.setCoordsTo(tempVel);
		tempVel.versor();
		tempVel.mul(j1);
		float k1 = tempVel.proj(wX);
		if (k1 > 0)
			wX.sub(k1);
		if (h.getAbsVel() > 0) {
			float i2 = 0.1F * i1;
			i2 = h.getVel() <= 0 ? -i2 : i2;
			float l1 = h.getAbsVel() <= Math.abs(i2) ? h.getVel() : i2;
			h.ne(l1);
		}
		vel.mul(55536F / 65536F);
		if (vel.abs() < 5F / 65536F) {
			vel.setCoords(0, 0);
			wX.setCoords(0, 0);
		}
	}

}
