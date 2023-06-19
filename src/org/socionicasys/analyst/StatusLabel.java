package org.socionicasys.analyst;

import org.socionicasys.analyst.model.DocumentSelectionModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Строка состояния, отображающая подсказки и информацю о текущем выделении в документе.
 */
public class StatusLabel extends JLabel implements PropertyChangeListener {
	/**
	 * Модель выделения, к которой привязана строка состояния.
	 */
	private final DocumentSelectionModel selectionModel;

	/**
	 * Документ, с которым связана строка состояния
	 */
	private final DocumentHolder documentHolder;

	/**
	 * Инициализирует строку состояния, связанную с определенной моделью выделения в документе.
	 *
	 * @param selectionModel модель выделения, состояние которой будет отображать строка
	 * @param documentHolder документ, с которым будет связана строка состояния
	 */
	public StatusLabel(DocumentSelectionModel selectionModel, DocumentHolder documentHolder) {
		this.selectionModel = selectionModel;
		this.selectionModel.addPropertyChangeListener(this);
		this.documentHolder = documentHolder;
		updateView();
	}

	/**
	 * Отображает изменение свойств выделения, к которой привязана строка состояния, в текст строки.
	 *
	 * @param evt параметр не используется
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		updateView();
	}

	/**
	 * Обновляет текст строки в зависимости от текущего выделения.
	 */
	private void updateView() {
		if (documentHolder.getModel().getLength() == 0) {
			setText("Open existing document or paste text into the central pane");
		} else if (selectionModel.isEmpty()) {
			setText("Select some text to begin analysis...");
		} else if (selectionModel.isMarkupEmpty()) {
			setText("Select information element and respective IM function's parameters using the controls on the right...");
		} else {
			setText(selectionModel.getMarkupData().toString());
		}
	}
}
