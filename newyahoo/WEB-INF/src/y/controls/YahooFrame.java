// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Event;
import java.awt.Frame;
import java.awt.event.FocusEvent;

import y.utils.Processor;

// Referenced classes of package y.po:
// _cls21, _cls143, _cls173, _cls78,
// _cls168

public class YahooFrame extends Frame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6948517564292518725L;
	public YahooContainer		container;
	YahooComponent				defaultAction;
	boolean						disposeOnClose;

	public YahooFrame() {
		container = new Processor(this).defaultContainer;
	}

	public YahooFrame(Processor processor) {
		this("", processor);
	}

	public YahooFrame(String text, boolean disposeOnClose, Processor processor) {
		super(text);
		container = new YahooContainer(this, processor);
		this.disposeOnClose = disposeOnClose;
	}

	public YahooFrame(String text, Processor processor) {
		this(text, false, processor);
	}

	protected void close() {
		setVisible(false);
		if (disposeOnClose)
			dispose();
	}

	@Override
	public void dispose() {
		if (container != null) {
			container.nb();
			container.processor.framesToClose.put(this);
			container = null;
		}
	}

	/**
	 * 
	 */
	protected void doGotFocus() {

	}

	protected void doLostFocus() {

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean handleEvent(Event event) {
		if (event.id == 201 && event.target == this || event.id == 1001
				&& event.target == defaultAction) {
			close();
			return true;
		}
		else if (event.id == FocusEvent.FOCUS_GAINED) {
			doGotFocus();
			return true;
		}
		else if (event.id == FocusEvent.FOCUS_LOST) {
			doLostFocus();
			return true;
		}
		return super.handleEvent(event);
	}

	public void process() {
		super.dispose();
	}

	public void setDefaultAction(YahooComponent value) {
		defaultAction = value;
	}
}
