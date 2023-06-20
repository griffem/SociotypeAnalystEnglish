package org.socionicasys.analyst;

import javax.swing.text.AttributeSet;

public final class StyledText {
	private final String text;
	private final AttributeSet style;

	public StyledText(final String text, final AttributeSet style) {
		this.text = text;
		this.style = style.copyAttributes();
	}

	public String getText() {
		return text;
	}

	public AttributeSet getStyle() {
		return style;
	}
}
