// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst

package y.controls;

import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import y.utils.TimerEngine;
import y.utils.TimerEntry;
import y.utils.TimerHandler;

// Referenced classes of package y.po:
// _cls78, _cls116

public class YahooTextBox extends YahooComponent implements TimerHandler {

	static char[]	acents		= { '~', '^', '´', '`', '¨' };
	static char[][]	acentueds	= { { ' ', '~', '^', '´', '`', '¨' },
			{ 'A', 'Ã', 'Â', 'Á', 'À', 'Ä' }, { 'E', 0, 'Ê', 'É', 'È', 'Ë' },
			{ 'I', 0, 'Î', 'Í', 'Ì', 'Ï' }, { 'N', 'Ñ', 0, 0, 0, 0 },
			{ 'O', 'Õ', 'Ô', 'Ó', 'Ò', 'Ö' }, { 'U', 0, 'Û', 'Ú', 'Ù', 'Ü' },
			{ 'Y', 0, 0, 'Ý', 0, 0 }, { ' ', '~', '^', '´', '`', '¨' },
			{ 'a', 'ã', 'â', 'á', 'à', 'ä' }, { 'e', 0, 'ê', 'é', 'è', 'ë' },
			{ 'i', 0, 'î', 'í', 'ì', 'ï' }, { 'n', 'ñ', 0, 0, 0, 0 },
			{ 'o', 'õ', 'ô', 'ó', 'ò', 'ö' }, { 'u', 0, 'û', 'ú', 'ù', 'ü' },
			{ 'y', 0, 0, 'ý', 0, 'ÿ' }, };
	static int[]	acceptAcent	= { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1 };
	static {
		for (int i = 0; i < acentueds.length; i++)
			acceptAcent[acentueds[i][0]] = i;
	}

	static int indexOfAcent(char c) {
		for (int i = 0; i < acents.length; i++)
			if (acents[i] == c)
				return i;
		return -1;
	}

	FontMetrics		fontMetrics;
	char			textBuf[];
	int				selStart;
	int				selEnd;
	int				textLen;
	int				selEnd1;

	int				ytb_left;
	boolean			focused;
	boolean			enabled;

	private boolean	cursorOn;

	int				currAcentIndex;

	// TextField textField;

	TimerEntry		selfTimer;

	public YahooTextBox(TimerEngine timeHandler) {
		this(timeHandler, 0);
	}

	public YahooTextBox(TimerEngine timeHandler, int i1) {
		this(timeHandler, "", i1);
	}

	public YahooTextBox(TimerEngine timerHandler, String s, int i1) {
		cursorOn = false;

		textBuf = new char[1024];
		selStart = 0;
		selEnd = -1;
		textLen = 0;
		selEnd1 = 0;
		enabled = true;
		setBackColor(Color.white);
		setForeColor(Color.black);
		ytb_left = i1;
		currAcentIndex = -1;
		setText(s);

		selfTimer = timerHandler.add(this, 300);

		/*
		 * super(new TextField(s)); textField = (TextField)component;
		 * textField.setSelectionStart(i1);
		 * textField.setForeground(Color.black);
		 * textField.setBackground(Color.white); textField.setEnabled(true);
		 */
	}

	public void deleteSelectedText() {
		if (selEnd >= 0) {
			int i1;
			int j1;
			if (selStart < selEnd) {
				i1 = selStart;
				j1 = selEnd;
			}
			else {
				i1 = selEnd;
				j1 = selStart;
			}
			System.arraycopy(textBuf, j1, textBuf, i1, textLen - j1);
			textLen -= j1 - i1;
			selEnd = -1;
			Yw();
		}
	}

	@Override
	public boolean eventMouseDown(Event event, int i1, int j1) {
		if (enabled) {
			xb(this, false);
			Xw(event, i1, j1);
			selEnd = selStart;
		}
		return true;
	}

	@Override
	public boolean eventMouseDrag(Event event, int i1, int j1) {
		Xw(event, i1, j1);
		return true;
	}

	@Override
	public boolean eventMouseUp(Event event, int i1, int j1) {
		Xw(event, i1, j1);
		return true;
	}

	@Override
	public int getHeight1() {
		return fontMetrics.getHeight() + 6;
		// return textField.getHeight();
	}

	public String getSelectedText() {
		if (selEnd >= 0) {
			int i1;
			int j1;
			if (selStart < selEnd) {
				i1 = selStart;
				j1 = selEnd;
			}
			else {
				i1 = selEnd;
				j1 = selStart;
			}
			return new String(textBuf, i1, j1 - i1);
		}
		return null;
	}

	public String getText() {
		return new String(textBuf, 0, textLen);
		// return textField.getText();
	}

	@Override
	public int getWidth1() {
		return ytb_left + 6;
		// return textField.getSelectionStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see y.utils.TimerEvent#handleTimer(long)
	 */
	@Override
	public void handleTimer(long l) {
		cursorOn = !cursorOn;
		invalidate();
	}

	@Override
	public void paint(YahooGraphics yahooGraphics) {
		paintTo(yahooGraphics);
		yahooGraphics.setFont(YahooComponent.defaultFont);
		Yw();
		if (enabled) {
			yahooGraphics.setColor(Color.white);
			yahooGraphics.drawRect(0, 0, super.width - 1, super.height - 1);
			yahooGraphics.setColor(Color.black);
			yahooGraphics.drawLine(1, 1, super.width - 2, 1);
			yahooGraphics.drawLine(1, 1, 1, super.height - 2);
			yahooGraphics.setColor(Color.lightGray);
			yahooGraphics.drawLine(super.width - 2, 1, super.width - 2,
					super.height - 2);
			yahooGraphics.drawLine(1, super.height - 2, super.width - 2,
					super.height - 2);
		}
		int i1 = 3 + fontMetrics.getAscent();
		yahooGraphics.setColor(getForeColor());
		yahooGraphics.drawChars(textBuf, selEnd1, textLen - selEnd1, 3, i1,
				super.width - 6, fontMetrics);
		yahooGraphics.setColor(YahooComponent.defaultColor);
		int j1 = fontMetrics.charsWidth(textBuf, selEnd1, selStart - selEnd1);
		if (selEnd >= 0) {
			int k1 = selEnd >= selEnd1 ? fontMetrics.charsWidth(textBuf,
					selEnd1, selEnd - selEnd1) : 0;
			int l1;
			int i2;
			if (selStart < selEnd) {
				l1 = selStart;
				i2 = j1;
			}
			else {
				l1 = selEnd;
				i2 = k1;
			}
			int l2 = Math.min(Math.abs(j1 - k1), super.width - 6 - i2);
			yahooGraphics.fillRect(3 + i2, 3, l2, fontMetrics.getHeight());
			yahooGraphics.setColor(Color.white);
			yahooGraphics.drawChars(textBuf, l1, textLen - l1, 3 + i2, i1, l2,
					fontMetrics);
		}
		// desenha o cursor
		if (enabled && focused && cursorOn) {
			yahooGraphics.setColor(getForeColor());
			yahooGraphics.drawCursor(3 + j1, i1, fontMetrics);
		}
	}

	@Override
	public boolean processEvent(Event event) {
		Event event1 = null;
		if (enabled) {
			if (event.id == Event.KEY_PRESS) {
				if ((event.modifiers & Event.CTRL_MASK) == Event.CTRL_MASK) {
					Clipboard clip = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					String selText;
					StringSelection selection;
					try {
						// System.out.println(event.key);
						switch (event.key) {
						case 3: // ctrl+C
							selText = getSelectedText();
							// System.out.println(selText);
							selection = new StringSelection(selText);
							clip.setContents(selection, selection);
							break;
						case 22: // ctrl+V
							selText = (String) clip
									.getData(DataFlavor.stringFlavor);
							replaceSelectedText(selText);
							break;
						case 24: // ctrl+X
							selText = getSelectedText();
							// System.out.println(selText);
							selection = new StringSelection(selText);
							clip.setContents(selection, selection);
							deleteSelectedText();
							break;
						case 5:
							replaceSelectedText('°');
							break;
						case 23:
							replaceSelectedText('?');
						}
					}
					catch (UnsupportedFlavorException e) {
						// ignore
					}
					catch (IOException e) {
						// ignore
					}
				}
				else {
					deleteSelectedText();
					if (event.key == 0)
						return true;
					if (event.key == Event.BACK_SPACE) {
						if (selStart > 0) {
							System.arraycopy(textBuf, selStart, textBuf,
									selStart - 1, textLen - selStart);
							selStart--;
							textLen--;
						}
					}
					else if (event.key == Event.ENTER)
						event1 = new Event(this, 1001, getText());
					// else if (event.key == 1)
					// selStart = 0;
					else if (event.key == Event.META_MASK
							|| event.key == Event.DELETE) {
						if (selStart < textLen) {
							System.arraycopy(textBuf, selStart + 1, textBuf,
									selStart, textLen - selStart + 1);
							textLen--;
						}
					}
					// else if (event.key == 5)
					// selStart = textLen;
					else if (Character.isDefined((char) event.key)) {
						char c = (char) event.key;
						if (currAcentIndex != -1) {
							if (acceptAcent[c] != -1
									&& acentueds[acceptAcent[c]][currAcentIndex + 1] != 0)
								c = acentueds[acceptAcent[c]][currAcentIndex + 1];
							else
								replaceSelectedText(acents[currAcentIndex]);
							currAcentIndex = -1;

							replaceSelectedText(c);
						}
						else {
							currAcentIndex = indexOfAcent(c);
							if (currAcentIndex == -1)
								replaceSelectedText(c);
						}
					}
				}
			}
			else if (event.id == Event.KEY_ACTION) {
				if (event.key == Event.LEFT) {
					if (selStart > 0) {
						if ((event.modifiers & Event.SHIFT_MASK) == Event.SHIFT_MASK) {
							if (selEnd == -1)
								selEnd = selStart;
						}
						else
							Vw();
						selStart--;
					}
				}
				else if (event.key == Event.RIGHT) {
					if (selStart < textLen) {
						if ((event.modifiers & Event.SHIFT_MASK) == Event.SHIFT_MASK) {
							if (selEnd == -1)
								selEnd = selStart;
						}
						else
							Vw();
						selStart++;
					}
				}
				else if (event.key == Event.HOME) {
					if ((event.modifiers & Event.SHIFT_MASK) == Event.SHIFT_MASK) {
						if (selEnd == -1)
							selEnd = selStart;
					}
					else
						Vw();
					selStart = 0;
				}
				else if (event.key == Event.END) {
					if ((event.modifiers & Event.SHIFT_MASK) == Event.SHIFT_MASK) {
						if (selEnd == -1)
							selEnd = selStart;
					}
					else
						Vw();
					selStart = textLen;
				}
			}
			else if (event.id == Event.DOWN)
				focused = false;
			else if (event.id == Event.UP)
				focused = true;
			else
				return super.processEvent(event);
			invalidate();
		}
		if (event1 != null) {
			doEvent(event1);
			return true;
		}
		return super.processEvent(event);
	}

	@Override
	public void realingChilds() {
		super.realingChilds();
		fontMetrics = getFontMetrics(YahooComponent.defaultFont);
	}

	public void replaceSelectedText(char c) {
		deleteSelectedText();

		if (textLen < textBuf.length) {
			System.arraycopy(textBuf, selStart, textBuf, selStart + 1, textLen
					- selStart);
			textBuf[selStart] = c;
			selStart++;
			textLen++;
		}
	}

	public void replaceSelectedText(String newText) {
		deleteSelectedText();

		for (int i = 0; i < newText.length() && textLen < textBuf.length; i++) {
			System.arraycopy(textBuf, selStart, textBuf, selStart + 1, textLen
					- selStart);
			textBuf[selStart] = newText.charAt(i);
			selStart++;
			textLen++;
		}
	}

	public void setEnabled(boolean flag) {
		enabled = flag;
		invalidate();
		// textField.setEnabled(flag);
	}

	public void setText(String s) {
		selStart = textLen = Math.min(textBuf.length, s.length());
		selEnd1 = 0;
		s.getChars(0, selStart, textBuf, 0);
		invalidate();
		// textField.setText(s);
	}

	void Vw() {
		if (selEnd >= 0) {
			selEnd = -1;
			Yw();
		}
	}

	void Xw(Event event, int i1, int j1) {
		int l1 = fontMetrics.charsWidth(textBuf, 0, selEnd1);
		int k1;
		for (k1 = 0; k1 < textLen; k1++)
			if (fontMetrics.charsWidth(textBuf, 0, k1) > i1 + l1 - 3)
				break;

		selStart = k1;
		invalidate();
	}

	void Yw() {
		if (selStart > textLen)
			selStart = textLen;
		if (selEnd1 > textLen)
			selEnd1 = textLen;
		for (; selStart > selEnd1
				&& fontMetrics.charsWidth(textBuf, selEnd1, selStart - selEnd1) > super.width - 6; selEnd1++) {
		}
		for (; selEnd1 > 0
				&& fontMetrics.charsWidth(textBuf, selEnd1 - 1, textLen
						- selEnd1 + 1) < super.width - 6; selEnd1--) {
		}
		if (selStart < selEnd1)
			selEnd1 = selStart;
	}
}
