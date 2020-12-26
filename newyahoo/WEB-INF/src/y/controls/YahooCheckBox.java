// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;
import java.awt.Image;

import y.utils.ImageReader;

// Referenced classes of package y.po:
// _cls79, _cls55, _cls35, _cls167,
// _cls78, _cls116, _cls87

public class YahooCheckBox extends YahooControl {

	YahooRadioGroup	group;
	YahooComponent	ycb_b;
	YahooCheckBox	nextCheckBox;
	YahooCheckBox	ycb_d;
	public boolean	checked;
	boolean			ycb_f;
	boolean			enabled;
	Image			h;

	public YahooCheckBox(String s) {
		this(s, null, false);
	}

	public YahooCheckBox(String s, YahooCheckBox _pcls80, boolean flag) {
		this(new YahooLabel(s), _pcls80, flag);
	}

	public YahooCheckBox(YahooComponent _pcls78, YahooCheckBox _pcls80,
			boolean flag) {
		nextCheckBox = this;
		ycb_d = this;
		enabled = true;
		h = ImageReader
				.getImage("\f\f\005\uFFFF\uFFFF\uFF00\000\uFF7F\u7F7F\377\uFFFF\uFFDF\uDFDF\017~<n?{'\177c\177x\037~'\177\f\017\007p\007\017\177\177C\037\017wg~S\177t\037}g~\034\017\007p\007\017\000\000\000\000\000\000\b\000\004\000\001 \000\b\000a0`\003\000\0");
		if (_pcls80 != null) {
			nextCheckBox = _pcls80.nextCheckBox;
			ycb_d = _pcls80;
			_pcls80.nextCheckBox = this;
			nextCheckBox.ycb_d = this;
		}
		group = new YahooRadioGroup(this);
		ycb_b = _pcls78;
		checked = flag;
		addChildObject(group, 18, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0);
		addChildObject(ycb_b, 17, 2, 2, 1, 1, 1, 0, 0, 2, 0, 0);
	}

	@Override
	public boolean eventMouseDown(Event event, int j, int k) {
		if (enabled) {
			ycb_f = true;
			group.invalidate();
		}
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int j, int k) {
		if (ycb_f) {
			ycb_f = false;
			if (j >= 0 && k >= 0 && j < super.width && k < super.height
					&& (!checked || nextCheckBox == this)) {
				zo();
				doEvent(new Event(this, 1001, new Boolean(checked)));
			}
			group.invalidate();
		}
		return true;
	}

	public boolean isChecked() {
		return checked;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		super.paint(yahooGraphics);
		if (!enabled) {
			yahooGraphics.setColor(getBackColor());
			YahooButton.setGraphic(yahooGraphics, ycb_b.getWorldLeft(this),
					ycb_b.getWorldTop(this), ycb_b.getWidth(), ycb_b
							.getHeight());
		}
	}

	public void setChecked(boolean flag) {
		if (flag != checked)
			zo();
	}

	public void setEnabled(boolean flag) {
		enabled = flag;
		invalidate();
	}

	public void toggle() {
		checked = checked ^ true;
		invalidate();
	}

	public void zo() {
		toggle();
		if (checked) {
			for (YahooCheckBox _lcls80 = nextCheckBox; _lcls80 != this; _lcls80 = _lcls80.nextCheckBox)
				if (_lcls80.checked)
					_lcls80.toggle();

		}
		group.invalidate();
	}
}
