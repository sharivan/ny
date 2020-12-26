// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;
import java.util.Vector;

import y.utils.ComponentSort;
import y.utils.Sort;

// Referenced classes of package y.po:
// _cls78, _cls75, _cls165, _cls116

public class YahooControl extends YahooComponent {

	public static Sort<YahooComponent>	Q	= new ComponentSort();
	public Vector<YahooComponent>		components;
	public YahooComponent				yctrl_u;
	public YahooComponent				componentLayout[][];
	public int							yctrl_w[];
	public int							yctrl_x[];
	public int							yctrl_y[];
	public int							yctrl_z[];
	public int							yctrl_A[];
	public int							B[];
	public int							C[];
	public int							D[];
	public YahooComponent				E;
	public YahooComponent				F;
	public int							G;
	public int							yctrl_H;
	public boolean						I;
	public boolean						yctrl_J[];
	public boolean						K[];
	public int							L;
	public int							M;
	public boolean						N;
	public int							O;
	public int							P;

	public YahooControl() {
		this(0);
	}

	public YahooControl(boolean flag) {
		this(0, flag);
	}

	public YahooControl(int i) {
		this(i, false);
	}

	public YahooControl(int i, boolean flag) {
		super(flag);
		components = new Vector<YahooComponent>();
		yctrl_u = null;
		componentLayout = new YahooComponent[16][16];
		yctrl_w = new int[16];
		yctrl_x = new int[16];
		yctrl_y = new int[17];
		yctrl_z = new int[17];
		yctrl_A = new int[16];
		B = new int[16];
		C = new int[17];
		D = new int[17];
		I = false;
		yctrl_J = new boolean[16];
		K = new boolean[16];
		L = 0;
		M = 0;
		N = true;
		P = 0;
		O = i;
	}

	public YahooControl(int i, int j) {
		super(i, j);
		components = new Vector<YahooComponent>();
		yctrl_u = null;
		componentLayout = new YahooComponent[16][16];
		yctrl_w = new int[16];
		yctrl_x = new int[16];
		yctrl_y = new int[17];
		yctrl_z = new int[17];
		yctrl_A = new int[16];
		B = new int[16];
		C = new int[17];
		D = new int[17];
		I = false;
		yctrl_J = new boolean[16];
		K = new boolean[16];
		L = 0;
		M = 0;
		N = true;
		P = 0;
	}

	public void _qo(YahooComponent component) {
		yctrl_u = component;
		component.parent = this;
		if (super.invalidated) {
			component.width = super.width;
			component.height = super.height;
			component.realingChilds();
			component.m();
		}
	}

	public void addChildObject(YahooComponent component, int i) {
		addChildObject(component, 1, 1, i, 0);
	}

	public void addChildObject(YahooComponent component, int i, int j,
			boolean flag) {
		addChildObject(component, 0, 0, 0, 0, 0, 0, 0, j, i, 0, 0, true, flag);
	}

	public void addChildObject(YahooComponent component, int col, int row, int k) {
		addChildObject(component, 1, 1, col, row, false, row <= 0 ? 0 : k,
				col <= 0 ? 0 : k, 0, 0);
	}

	public void addChildObject(YahooComponent component, int colCount,
			int rowCount, int col, int row) {
		addChildObject(component, 10, 0, 0, colCount, rowCount, col, row);
	}

	public void addChildObject(YahooComponent component, int colCount,
			int rowCount, int col, int row, boolean flag) {
		addChildObject(component, colCount, rowCount, col, row, flag, 0, 0, 0,
				0);
	}

	public void addChildObject(YahooComponent component, int colCount,
			int rowCount, int col, int row, boolean flag, int j1, int k1,
			int l1, int i2) {
		addChildObject(component, 10, 1, flag ? 1 : 0, colCount, rowCount, col,
				row, j1, k1, l1, i2);
	}

	public void addChildObject(YahooComponent component, int i, int j, int k,
			int colCount, int rowCount, int col, int row) {
		addChildObject(component, i, j, k, colCount, rowCount, col, row, 0, 0,
				0, 0);
	}

	public void addChildObject(YahooComponent component, int colCount,
			int rowCount, int col, int row, int j1, int k1, int l1, int i2) {
		addChildObject(component, 10, 0, 0, colCount, rowCount, col, row, j1,
				k1, l1, i2);
	}

	public void addChildObject(YahooComponent component, int i, int j, int k,
			int colCount, int rowCount, int col, int row, int i2, int j2,
			int k2, int l2) {
		addChildObject(component, i, j, k, colCount, rowCount, col, row, i2,
				j2, k2, l2, false);
	}

	public void addChildObject(YahooComponent component, int i, int j, int k,
			int colCount, int rowCount, int col, int row, int i2, int j2,
			int k2, int l2, boolean absoluteCoords) {
		addChildObject(component, i, j, k, colCount, rowCount, col, row, i2,
				j2, k2, l2, absoluteCoords, false);
	}

	public void addChildObject(YahooComponent component, int i, int j, int k,
			int colCount, int rowCount, int col, int row, int i2, int j2,
			int k2, int l2, boolean absoluteCoords, boolean flag1) {
		if (!components.contains(component)) {
			component.n = i;
			component.o = j;
			component.x = k;
			component.colCount = colCount;
			component.rowCount = rowCount;
			component.col = col;
			component.row = row;
			component.t = i2;
			component.u = j2;
			component.v = k2;
			component.w = l2;
			component.parent = this;
			component.J = flag1;
			if (super.invalidated)
				component.realingChilds();
			components.add(component);
			if (!absoluteCoords) {
				for (int i3 = col; i3 < col + colCount; i3++) {
					for (int j3 = row; j3 < row + rowCount; j3++)
						componentLayout[j3][i3] = component;

				}

			}
			else {
				component.useAbsoluteCoords = true;
			}
			if (super.invalidated)
				if (component.useAbsoluteCoords) {
					ho(component);
					if (!super.J)
						component.invalidate();
				}
				else {
					rb();
				}
		}
	}

	void Ah(YahooComponent component, YahooComponent component1, Event event,
			int i, int j) {
		getContainer().addChildObject(component1,
				i + component.getWorldLeft(getContainer()),
				j + component.getWorldTop(getContainer()), true);
		E = component1;
		F = component;
		G = i - event.x;
		yctrl_H = j - event.y;
	}

	@Override
	public YahooComponent componentAtPos(int i, int j) {
		for (int k = components.size() - 1; k >= 0; k--) {
			YahooComponent _lcls78 = components.elementAt(k);
			if (_lcls78.useAbsoluteCoords && _lcls78.contains(i, j)
					&& !_lcls78.c) {
				i -= _lcls78.left;
				j -= _lcls78.top;
				return _lcls78.componentAtPos(i, j);
			}
		}

		for (int i1 = 0; i1 < components.size(); i1++) {
			YahooComponent _lcls78_1 = components.elementAt(i1);
			if (!_lcls78_1.useAbsoluteCoords && _lcls78_1.contains(i, j)
					&& (O != 2 || _lcls78_1.col == P) && !_lcls78_1.c) {
				i -= _lcls78_1.left;
				j -= _lcls78_1.top;
				return _lcls78_1.componentAtPos(i, j);
			}
		}

		return this;
	}

	@Override
	public void Dn() {
		super.z = N = true;
		for (int i = 0; i < components.size(); i++) {
			YahooComponent _lcls78 = components.elementAt(i);
			_lcls78.Dn();
		}

	}

	@Override
	public void En() {
		super.En();
		for (int i = 0; i < components.size(); i++) {
			YahooComponent _lcls78 = components.elementAt(i);
			_lcls78.En();
		}

	}

	@Override
	public boolean eventMouseUp(Event event, int i, int j) {
		if (E != null) {
			removeChildObject(E);
			F.doEvent(new Event(E, System.currentTimeMillis(), 0x10000, i
					- F.getWorldLeft(this) + G, j - F.getWorldTop(this)
					+ yctrl_H, 0, 0, null));
			E = null;
			return true;
		}
		return false;
	}

	@Override
	public int getHeight1() {
		if (super.height1 != 0) {
			return super.height1;
		}
		so();
		return yctrl_z[16];
	}

	@Override
	public YahooComponent getParentBackColor() {
		return yctrl_u != null ? yctrl_u : super.parent == null ? null
				: super.backColor != null ? null : super.parent
						.getParentBackColor();
	}

	@Override
	public int getWidth1() {
		if (super.width1 != 0) {
			return super.width1;
		}
		so();
		return yctrl_y[16];
	}

	void ho(YahooComponent _pcls78) {
		_pcls78.width = _pcls78.getWidth1();
		_pcls78.height = _pcls78.getHeight1();
		_pcls78.top = _pcls78.t;
		_pcls78.left = _pcls78.u;
		_pcls78.m();
	}

	@Override
	public void invalidate() {
		if (super.invalidated) {
			Dn();
			super.invalidate();
		}
	}

	void io() {
		super.rb();
		L = 0;
		M = 0;
		int i;
		for (i = 0; i < 16; i++) {
			K[i] = false;
			yctrl_J[i] = false;
			yctrl_w[i] = 0;
			yctrl_x[i] = 0;
			yctrl_y[i] = 0;
			yctrl_z[i] = 0;
			yctrl_A[i] = 0;
			B[i] = 0;
			C[i] = 0;
			D[i] = 0;
			yctrl_J[i] = false;
			K[i] = false;
		}

		yctrl_y[i] = 0;
		yctrl_z[i] = 0;
		C[i] = 0;
		D[i] = 0;
	}

	void jo() {
		for (int i = 0; i < components.size(); i++) {
			YahooComponent _lcls78 = components.elementAt(i);
			if (_lcls78.useAbsoluteCoords) {
				_lcls78.width = _lcls78.getWidth1();
				_lcls78.height = _lcls78.getHeight1();
			}
			else if (O == 2) {
				_lcls78.left = 0;
				_lcls78.top = 0;
				_lcls78.width = super.width;
				_lcls78.height = super.height;
			}
			else if (O == 1 || O == 0 || O == 3) {
				int j = C[_lcls78.col + _lcls78.colCount] - C[_lcls78.col]
						- _lcls78.u - _lcls78.w;
				int k = D[_lcls78.row + _lcls78.rowCount] - D[_lcls78.row]
						- _lcls78.t - _lcls78.v;
				if (_lcls78.o == 1 || _lcls78.o == 2)
					_lcls78.width = j;
				else
					_lcls78.width = _lcls78.getWidth1();
				if (_lcls78.o == 1 || _lcls78.o == 3)
					_lcls78.height = k;
				else
					_lcls78.height = _lcls78.getHeight1();
				if (_lcls78.n == 13 || _lcls78.n == 12 || _lcls78.n == 14)
					_lcls78.left = C[_lcls78.col + _lcls78.colCount]
							- _lcls78.width - _lcls78.w;
				else if (_lcls78.n == 17 || _lcls78.n == 18 || _lcls78.n == 16)
					_lcls78.left = C[_lcls78.col] + _lcls78.u;
				else
					_lcls78.left = C[_lcls78.col] + _lcls78.u
							+ (j - _lcls78.width) / 2;
				if (_lcls78.n == 15 || _lcls78.n == 14 || _lcls78.n == 16)
					_lcls78.top = D[_lcls78.row + _lcls78.rowCount]
							- _lcls78.height - _lcls78.v;
				else if (_lcls78.n == 11 || _lcls78.n == 12 || _lcls78.n == 18)
					_lcls78.top = D[_lcls78.row] + _lcls78.t;
				else
					_lcls78.top = D[_lcls78.row] + _lcls78.t
							+ (k - _lcls78.height) / 2;
			}
		}

	}

	void ko(YahooComponent component, YahooGraphics graphics) {
		if (component.z && component.A) {
			component.z = false;
			if (component.visible)
				graphics = graphics.create(0, 0, component.width,
						component.height);
			component.paint(graphics);
			if (component.visible)
				graphics.dispose();
		}
	}

	public void lo(YahooGraphics _pcls116, boolean flag) {
		if (!super.H)
			m();
		if (N) {
			if (!flag)
				paintTo(_pcls116);
			Dn();
			N = super.z = false;
		}
		int i = _pcls116.left;
		int j = _pcls116.top;
		for (int k = 0; k < components.size(); k++) {
			YahooComponent _lcls78 = components.elementAt(k);
			if (!_lcls78.J
					&& (O != 2 || _lcls78.useAbsoluteCoords || P == _lcls78.col)) {
				_pcls116.left = i + _lcls78.left;
				_pcls116.top = j + _lcls78.top;
				ko(_lcls78, _pcls116);
			}
		}

		_pcls116.left = i;
		_pcls116.top = j;
	}

	@Override
	public void m() {
		super.m();
		N = true;
		so();
		int i = super.width - yctrl_y[16];
		int j = super.height - yctrl_z[16];
		C[0] = D[0] = 0;
		if (O == 1) {
			int k = 0;
			int k1 = 0;
			for (int i2 = 0; i2 < 16; i2++) {
				for (int k2 = 0; k2 < 16; k2++)
					if (componentLayout[i2][k2] != null) {
						if (i2 > k1)
							k1 = i2;
						if (k2 > k)
							k = k2;
					}

			}

			k1++;
			k++;
			int l2 = (super.width + k - 1) / k;
			int k3 = (super.height + k1 - 1) / k1;
			for (int l3 = 0; l3 < 16; l3++) {
				C[l3 + 1] = C[l3] + l2;
				D[l3 + 1] = D[l3] + k3;
				yctrl_A[l3] = l2;
				B[l3] = k3;
			}

		}
		else if (O == 0 || O == 3) {
			if (L == 0)
				C[0] = i / 2;
			if (M == 0)
				D[0] = j / 2;
			int i1 = 0;
			int l1 = 0;
			for (int j2 = 0; j2 < 16; j2++) {
				if (yctrl_J[j2] && i > 0) {
					int i3 = i / (L - i1);
					yctrl_A[j2] = yctrl_w[j2] + i3;
					i -= i3;
					i1++;
				}
				else {
					yctrl_A[j2] = yctrl_w[j2];
				}
				C[j2 + 1] = C[j2] + yctrl_A[j2];
				if (K[j2] && j > 0) {
					int j3 = j / (M - l1);
					B[j2] = yctrl_x[j2] + j3;
					j -= j3;
					l1++;
				}
				else {
					B[j2] = yctrl_x[j2];
				}
				D[j2 + 1] = D[j2] + B[j2];
			}

		}
		jo();
		for (int j1 = 0; j1 < components.size(); j1++) {
			YahooComponent _lcls78 = components.elementAt(j1);
			_lcls78.m();
			_lcls78.z = true;
		}

		if (yctrl_u != null) {
			yctrl_u.width = super.width;
			yctrl_u.height = super.height;
			yctrl_u.m();
		}
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		lo(yahooGraphics, false);
	}

	public void qo(int i) {
		P = i;
		invalidate();
	}

	@Override
	public void rb() {
		io();
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		for (int i = 0; i < components.size(); i++) {
			YahooComponent component = components.elementAt(i);
			component.realingChilds();
			if (component.useAbsoluteCoords)
				ho(component);
			if (yctrl_u != null)
				yctrl_u.realingChilds();
		}

	}

	public void removeChildObject(YahooComponent component) {
		components.remove(component);
		if (super.invalidated)
			component.En();
		if (!component.useAbsoluteCoords) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++)
					if (componentLayout[i][j] == component)
						componentLayout[i][j] = null;

			}

			io();
		}
		else {
			notifyRemoveComponent(component.left, component.top,
					component.width, component.height);
			if (!component.J)
				invalidate();
		}
	}

	public final void ro() {
		Q.quickSort(components);
	}

	void so() {
		if (super.H)
			return;
		super.H = true;
		yctrl_y[0] = yctrl_z[0] = 0;
		if (O == 0 || O == 3) {
			for (int i = 0; i < 16; i++) {
				yctrl_x[i] = 0;
				int ai[] = new int[i + 1];
				for (int l1 = 0; l1 < 16; l1++) {
					YahooComponent _lcls78 = componentLayout[i][l1];
					if (_lcls78 != null
							&& _lcls78.row + _lcls78.rowCount == i + 1) {
						int k3 = _lcls78.Bn();
						boolean flag = false;
						for (int j6 = _lcls78.row; j6 < i; j6++)
							if (K[j6])
								flag = true;

						if (!flag && (_lcls78.x == 1 || _lcls78.x == 3)
								&& !K[i]) {
							K[i] = true;
							M++;
						}
						if (k3 > ai[_lcls78.rowCount - 1])
							ai[_lcls78.rowCount - 1] = k3;
					}
				}

				for (int l2 = 1; l2 <= i + 1; l2++) {
					int l3 = 0;
					for (int l4 = 0; l4 < l2; l4++)
						l3 += yctrl_x[i - l4];

					int k6 = ai[l2 - 1] - l3;
					if (k6 > 0) {
						int i7 = 0;
						for (int i8 = 0; i8 < l2; i8++)
							if (K[i - i8])
								i7++;

						if (i7 == 0)
							yctrl_x[i] += k6;
						int l8 = 0;
						for (int k9 = 0; k9 < l2; k9++)
							if (K[i - k9]) {
								int j10 = k6 / (i7 - l8);
								yctrl_x[i - k9] += j10;
								k6 -= j10;
								l8++;
							}

					}
				}

			}

			for (int i1 = 0; i1 < 16; i1++) {
				yctrl_w[i1] = 0;
				int ai1[] = new int[i1 + 1];
				for (int i3 = 0; i3 < 16; i3++) {
					YahooComponent _lcls78_2 = componentLayout[i3][i1];
					if (_lcls78_2 != null
							&& _lcls78_2.col + _lcls78_2.colCount == i1 + 1) {
						int i5 = _lcls78_2.Cn();
						boolean flag1 = false;
						for (int j7 = _lcls78_2.col; j7 < i1; j7++)
							if (yctrl_J[j7])
								flag1 = true;

						if (!flag1 && (_lcls78_2.x == 1 || _lcls78_2.x == 2)
								&& !yctrl_J[i1]) {
							yctrl_J[i1] = true;
							L++;
						}
						if (i5 > ai1[_lcls78_2.colCount - 1])
							ai1[_lcls78_2.colCount - 1] = i5;
					}
				}

				for (int i4 = 1; i4 <= i1 + 1; i4++) {
					int j5 = 0;
					for (int l6 = 0; l6 < i4; l6++)
						j5 += yctrl_w[i1 - l6];

					int k7 = ai1[i4 - 1] - j5;
					if (k7 > 0) {
						int j8 = 0;
						for (int i9 = 0; i9 < i4; i9++)
							if (yctrl_J[i1 - i9])
								j8++;

						if (j8 == 0)
							yctrl_w[i1] += k7;
						int l9 = 0;
						for (int k10 = 0; k10 < i4; k10++)
							if (yctrl_J[i1 - k10]) {
								int l10 = k7 / (j8 - l9);
								yctrl_w[i1 - k10] += l10;
								k7 -= l10;
								l9++;
							}

					}
				}

			}

			if (O == 3) {
				if (yctrl_J[2])
					yctrl_w[0] = yctrl_w[2] = Math.max(yctrl_w[0], yctrl_w[2]);
				if (K[2])
					yctrl_x[0] = yctrl_x[2] = Math.max(yctrl_x[0], yctrl_x[2]);
			}
			for (int i2 = 0; i2 < 16; i2++) {
				yctrl_y[i2 + 1] = yctrl_y[i2] + yctrl_w[i2];
				yctrl_z[i2 + 1] = yctrl_z[i2] + yctrl_x[i2];
			}

		}
		else if (O == 2) {
			int j = 0;
			int j1 = 0;
			for (int j2 = 0; j2 < components.size(); j2++) {
				YahooComponent _lcls78_1 = components.elementAt(j2);
				int j4 = _lcls78_1.Cn();
				int k5 = _lcls78_1.Bn();
				if (j4 > j)
					j = j4;
				if (k5 > j1)
					j1 = k5;
			}

			yctrl_y[16] = j;
			yctrl_z[16] = j1;
		}
		else if (O == 1) {
			int k = 0;
			int k1 = 0;
			int k2 = 0;
			int j3 = 0;
			for (int k4 = 0; k4 < 16; k4++) {
				for (int l5 = 0; l5 < 16; l5++) {
					YahooComponent _lcls78_3 = componentLayout[k4][l5];
					if (_lcls78_3 != null) {
						int l7 = l5 + _lcls78_3.colCount;
						if (l7 > k2)
							k2 = l7;
						int k8 = (_lcls78_3.Cn() + _lcls78_3.colCount - 1)
								/ _lcls78_3.colCount;
						if (k8 > k)
							k = k8;
						int j9 = k4 + _lcls78_3.rowCount;
						if (j9 > j3)
							j3 = j9;
						int i10 = (_lcls78_3.Bn() + _lcls78_3.rowCount - 1)
								/ _lcls78_3.rowCount;
						if (i10 > k1)
							k1 = i10;
					}
				}

			}

			yctrl_w[0] = k;
			yctrl_x[0] = k1;
			for (int i6 = 1; i6 <= 16; i6++) {
				yctrl_y[i6] = yctrl_y[i6 - 1];
				yctrl_z[i6] = yctrl_z[i6 - 1];
				if (i6 <= k2) {
					if (i6 < k2)
						yctrl_w[i6] = k;
					yctrl_y[i6] += k;
				}
				if (i6 <= j3) {
					if (i6 < j3)
						yctrl_x[i6] = k1;
					yctrl_z[i6] += k1;
				}
			}

		}
	}

	@Override
	protected void zn(YahooGraphics graphics, int left, int top, int rigth,
			int bottom) {
		if (super.J) {
			super.zn(graphics, left, top, rigth, bottom);
		}
		else {
			int j1 = graphics.left;
			int k1 = graphics.top;
			for (int l1 = 0; l1 < components.size(); l1++) {
				YahooComponent component = components.elementAt(l1);
				if (component.containsLine(left, top, rigth, bottom)
						&& (O != 2 || component.col == P)
						&& (!I || !(component instanceof YahooControl))) {
					graphics.left = j1 + component.left;
					graphics.top = k1 + component.top;
					component.zn(graphics, left - component.left, top
							- component.top, rigth - component.left, bottom
							- component.top);
				}
			}

			for (int i2 = 0; i2 < components.size(); i2++) {
				YahooComponent _lcls78_1 = components.elementAt(i2);
				if (_lcls78_1.containsLine(left, top, rigth, bottom)
						&& (O != 2 || _lcls78_1.col == P) && I
						&& _lcls78_1 instanceof YahooControl) {
					graphics.left = j1 + _lcls78_1.left;
					graphics.top = k1 + _lcls78_1.top;
					_lcls78_1.zn(graphics, left - _lcls78_1.left, top
							- _lcls78_1.top, rigth - _lcls78_1.left, bottom
							- _lcls78_1.top);
				}
			}

		}
	}

}
