// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst

package common.k;

import y.utils.Square;

import common.yutils.ObjectManager;

// Referenced classes of package y.k:
// _cls138, _cls53

public class Maker {

	// public static ObjectManager hashMovesManager = new ObjectManager(new
	// Vector<Square>(), 512);
	public static ObjectManager	moveListManager	= new ObjectManager(
														new Tree<Square>(), 512);

	@SuppressWarnings("unchecked")
	public static Tree<Square> allocateMoveList() {
		Tree<Square> _lcls138 = (Tree<Square>) moveListManager.allocate();
		_lcls138.clear();
		return _lcls138;
	}

	public Maker() {
	}

}
