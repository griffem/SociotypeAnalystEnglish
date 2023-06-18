package org.socionicasys.analyst.panel;

import org.socionicasys.analyst.model.DocumentSelectionModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Виктор
 */
public class ControlsPane extends JToolBar {
	public ControlsPane(DocumentSelectionModel selectionModel) {
		super("Mark-up panel", JToolBar.VERTICAL);

		SignPanel signPanel = new SignPanel(selectionModel);
		FDPanel fdPanel = new FDPanel(selectionModel);
		DimensionPanel dimensionPanel = new DimensionPanel(selectionModel);
		AspectPanel aspectPanel = new AspectPanel(selectionModel);

		JPanel container = new JPanel();
		container.setMinimumSize(new Dimension(200, 500));
		JScrollPane scrl = new JScrollPane(container);

		scrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		container.add(aspectPanel);
		container.add(signPanel);
		container.add(dimensionPanel);
		container.add(fdPanel);
		add(container);
	}
}
