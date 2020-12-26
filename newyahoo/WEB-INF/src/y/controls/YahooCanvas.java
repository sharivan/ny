// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

// Referenced classes of package y.po:
// _cls81, _cls67, _cls21, _cls79,
// _cls173

public class YahooCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5173232417672805969L;
	YahooContainer				container;
	Image						primaryImage;
	Image						secundaryImage;
	Graphics					primaryGraphics;
	Graphics					secundaryGraphics;
	int							width;
	int							height;
	boolean						update;

	public YahooCanvas(YahooContainer container) {
		update = false;
		this.container = container;
	}

	@Override
	public void addNotify() {
		synchronized (container.processor.lock) {
			try {
				super.addNotify();
				container.realingChilds();
			}
			catch (Throwable throwable) {
				container.processor.handleError(throwable);
			}
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		synchronized (container.processor.lock) {
			try {
				if (event.id == 401 && event.key == 17
						&& (event.modifiers & 2) != 0)
					update = update ^ true;
				else
					container.mb(event);
			}
			catch (Throwable throwable) {
				container.processor.handleError(throwable);
			}
		}
		return true;
	}

	@Override
	public Dimension minimumSize() {
		return preferredSize();
	}

	@Override
	public void paint(Graphics graphics) {
		synchronized (container.processor.lock) {
			try {
				long t = System.currentTimeMillis();
				if (primaryImage == null) {
					if (width <= 0 || height <= 0)
						return;
					primaryImage = createImage(width, height);
					primaryGraphics = primaryImage.getGraphics();
					secundaryImage = createImage(width, height);
					secundaryGraphics = secundaryImage.getGraphics();
					container.Dn();
				}
				if (container.yc_g || !container.yc_n) {
					container.setGraphics(primaryGraphics);
					Rectangle rectangle = graphics.getClipBounds();
					if (rectangle == null)
						return;
					Graphics graphics1 = null;
					try {
						graphics1 = secundaryGraphics.create(rectangle.x,
								rectangle.y, rectangle.width, rectangle.height);
						graphics1.drawImage(primaryImage, -rectangle.x,
								-rectangle.y, null);
						container.tb(graphics1, rectangle.x, rectangle.y,
								rectangle.x + rectangle.width - 1, rectangle.y
										+ rectangle.height - 1);
						graphics.drawImage(secundaryImage, 0, 0, null);
					}
					finally {
						if (graphics1 != null) {
							graphics1.dispose();
							graphics1 = null;
						}
					}
					Toolkit.getDefaultToolkit().sync();
					if (update && (rectangle.width > 1 || rectangle.height > 1)) {
						graphics.setColor(Color.blue);
						String s = Integer.toString(container.m.Ol());
						graphics.fillRect(rectangle.x, rectangle.y, 36, 12);
						graphics.setColor(Color.yellow);
						graphics.drawString(s, rectangle.x, rectangle.y + 10);
						int dt = (int) (System.currentTimeMillis() - t);
						container.m.Nl(dt);
					}
				}
				else
					graphics.drawImage(primaryImage, 0, 0, null);
				if (container.processor.stoped)
					return;
				container.processor.stoped = true;
				container.processor.pool.process();
			}
			catch (Throwable throwable) {
				container.processor.handleError(throwable);
			}
			container.processor.stoped = false;
		}
	}

	@Override
	public Dimension preferredSize() {
		Dimension dimension;
		synchronized (container.processor.lock) {
			try {
				dimension = container.getDimension();
			}
			catch (Throwable throwable) {
				container.processor.handleError(throwable);
				dimension = new Dimension(1, 1);
			}
		}
		return dimension;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reshape(int x, int y, int width, int height) {
		synchronized (container.processor.lock) {
			try {
				primaryImage = null;
				this.width = width;
				this.height = height;
				super.reshape(x, y, width, height);
				container.yb(x, y, width, height);
			}
			catch (Throwable throwable) {
				container.processor.handleError(throwable);
			}
		}
	}

	@Override
	public void update(Graphics graphics) {
		paint(graphics);
	}
}
