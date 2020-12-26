// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.ycontrols;

import java.applet.AudioClip;
import java.util.Hashtable;
import java.util.Vector;

import y.yutils.AbstractYahooApplet;

import common.utils.Buffer;

// Referenced classes of package y.po:
// _cls160, _cls72, _cls127, _cls168

public class Sound extends Thread {

	Vector<String>					soundList;
	Hashtable<String, AudioClip>	b;
	Buffer							c;
	boolean							d;
	AbstractYahooApplet				applet;

	public Sound(AbstractYahooApplet _pcls168) {
		super(Sound.class.getName());
		soundList = new Vector<String>();
		b = new Hashtable<String, AudioClip>();
		c = new Buffer();
		applet = _pcls168;
	}

	public synchronized void addSound(String s) {
		soundList.add(s);
		notify();
	}

	@Override
	public void run() {
		while (!d) {
			int i = 0;
			String s;
			synchronized (this) {
				while (soundList.size() == 0 && !d)
					try {
						wait();
					}
					catch (InterruptedException _ex) {
					}
				if (d)
					break;
				s = soundList.elementAt(0);
				soundList.remove(0);
				if (s == null) {
					i = c.getInteger(0);
					c.delete(0);
				}
			}
			if (s != null) {
				AudioClip audioclip = b.get(s);
				if (audioclip == null) {
					audioclip = applet.getAudioClip(applet.getCodeBase(), s);
					b.put(s, audioclip);
				}
				audioclip.play();
			}
			else {
				try {
					Thread.sleep(i);
				}
				catch (InterruptedException _ex) {
				}
			}
		}
	}

	public synchronized void terminate() {
		d = true;
		notify();
	}
}
