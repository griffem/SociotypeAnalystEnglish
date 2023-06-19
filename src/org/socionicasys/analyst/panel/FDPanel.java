package org.socionicasys.analyst.panel;

import org.socionicasys.analyst.model.AData;
import org.socionicasys.analyst.model.DocumentSelectionModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Function Dichotomies Panel
 */
public final class FDPanel extends ActivePanel {
	private final Map<String, JRadioButton> buttons;
	private final ButtonGroup buttonGroup;
	private final JButton clearButton;

	public FDPanel(DocumentSelectionModel selectionModel) {
		super(selectionModel);

		buttons = new HashMap<String, JRadioButton>(4);
		buttons.put(AData.MENTAL, new JRadioButton("Mental"));
		buttons.put(AData.VITAL, new JRadioButton("Vital"));
		buttons.put(AData.EVALUATORY, new JRadioButton("Evaluatory"));
		buttons.put(AData.SITUATIONAL, new JRadioButton("Situational"));

		buttonGroup = new ButtonGroup();
		for (Map.Entry<String, JRadioButton> entry : buttons.entrySet()) {
			String buttonKey = entry.getKey();
			JRadioButton button = entry.getValue();
			button.addItemListener(this);
			button.setActionCommand(buttonKey);
			buttonGroup.add(button);
		}

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonGroup.clearSelection();
			}
		});

		Panel pp1 = new Panel();
		Panel pp2 = new Panel();
		Panel pp = new Panel();
		pp.setMinimumSize(new Dimension(200, 100));
		setMinimumSize(new Dimension(200, 100));
		setMaximumSize(new Dimension(200, 100));

		pp1.setLayout(new BoxLayout(pp1, BoxLayout.Y_AXIS));
		pp2.setLayout(new BoxLayout(pp2, BoxLayout.Y_AXIS));
		pp1.add(buttons.get(AData.MENTAL));
		pp1.add(buttons.get(AData.VITAL));
		pp2.add(buttons.get(AData.EVALUATORY));
		pp2.add(buttons.get(AData.SITUATIONAL));
		pp.setLayout(new BoxLayout(pp, BoxLayout.X_AXIS));

		pp.add(pp1);
		pp.add(pp2);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(pp);
		add(clearButton);
		setBorder(new TitledBorder("Function Dichotomies"));

		updateView();
	}

	/**
	 * Updates the panel controls according to the associated data from the selection model.
	 */
	@Override
	protected void updateView() {
		String fd = selectionModel.getFD();
		boolean panelEnabled = !selectionModel.isEmpty() && !selectionModel.isMarkupEmpty();
		boolean selectionEnabled = panelEnabled && fd != null;

		for (JRadioButton button : buttons.values()) {
			button.setEnabled(panelEnabled);
		}
		clearButton.setEnabled(selectionEnabled);

		if (selectionEnabled) {
			JRadioButton selectedButton = buttons.get(fd);
			buttonGroup.setSelected(selectedButton.getModel(), true);
		} else {
			buttonGroup.clearSelection();
		}
	}

	/**
	 * Обновляет модель в соответствии с измененными в панели данными.
	 */
	@Override
	protected void updateModel() {
		ButtonModel selectedButtonModel = buttonGroup.getSelection();
		if (selectedButtonModel == null) {
			selectionModel.setFD(null);
		} else {
			selectionModel.setFD(selectedButtonModel.getActionCommand());
		}
	}
}
