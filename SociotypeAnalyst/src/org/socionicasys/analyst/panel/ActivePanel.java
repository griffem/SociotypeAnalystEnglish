package org.socionicasys.analyst.panel;

import org.socionicasys.analyst.model.DocumentSelectionModel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A common class for all panels that allow you to change the marks
 * (aspect/size, etc.) in the current selection.
 */
public abstract class ActivePanel extends JPanel implements PropertyChangeListener, ItemListener {
	/**
	 * Selection model to which this panel is attached.
	 */
	protected final DocumentSelectionModel selectionModel;

	/**
	 * Used to synchronize updates model->view and view->model.
	 * If this field is {@code false}, the data in it is in the process of filling and
	 * should not be synchronized back into the model.
	 */
	protected boolean viewInitialized;

	/**
	 * Creates a panel with a link to a given selection model.
	 *
	 * @param selectionModel selection model that the panel will display and modify
	 */
	protected ActivePanel(DocumentSelectionModel selectionModel) {
		this.selectionModel = selectionModel;
		this.selectionModel.addPropertyChangeListener(this);
		viewInitialized = true;
	}

	/**
	 * Обрабатывает изменение свойств в модели выделения, к которой привязана эта панель.
	 * Включает/отключает панель и меняет состояние элементов при изменениях в выделении.
	 *
	 * @param evt параметр не используется
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!selectionModel.isInitialized()) {
			return;
		}

		viewInitialized = false;
		updateView();
		viewInitialized = true;
	}

	/**
	 * Обновляет элементы управления панели в соответствии с данными из модели выделения.
	 */
	protected abstract void updateView();

	/**
	 * Обрабатывает изменение в состоянии этой панели и отображает их на модель выделения,
	 * к которой панель привязана.
	 *
	 * @param e параметр не используется
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (!viewInitialized) {
			return;
		}

		selectionModel.setInitialized(false);
		updateModel();
		selectionModel.setInitialized(true);
	}

	/**
	 * Обновляет модель в соответствии с измененными в панели данными.
	 */
	protected abstract void updateModel();
}
