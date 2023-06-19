package org.socionicasys.analyst;

import java.awt.event.FocusEvent;
import javax.swing.text.DefaultCaret;

/**
 * Cursor that does not hide the selection when the focus is lost.
 */
@SuppressWarnings("serial")
public class HighlightCaret extends DefaultCaret {
	@Override
	public void focusLost(FocusEvent e) {
	}
}
