package org.socionicasys.analyst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socionicasys.analyst.service.VersionInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class AboutAction extends AbstractAction {
	private static final Logger logger = LoggerFactory.getLogger(AboutAction.class);

	private final Component parent;

	AboutAction(Component parent) {
		super("About the software");
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JTextArea info = new JTextArea(6, 40);
		info.setEditable(false);
		info.setBackground(panel.getBackground());
		info.setEditable(false);
		info.setText(String.format("Program \"%s\"\n" +
			'\n' +
			"© School of System Socionics, Kiev, 2016.\n" +
			"http://socionicasys.org\n" +
			"Version: %s",
			VersionInfo.getApplicationName(),
			VersionInfo.getVersion()
		));

		JTextArea licText = new JTextArea(15, 40);
		licText.setEditable(false);
		licText.setLineWrap(true);
		licText.setMargin(new Insets(3, 3, 3, 3));
		licText.setWrapStyleWord(true);
		licText.setAutoscrolls(false);

		licText.setText("WARNING! Failed to open the license file.\n\n" +
			"According to the original GNU GPU licence the software must be supplied with the text of the original licence.\n\n" +
			"Absence of such a licence can unreasonably reduce your user rights. \n\n" +
			"Request the original licence from the sofware supplier.\n\n" +
			"Original text of the GNU GPL licence can be found here: http://www.gnu.org/copyleft/gpl.html");

		InputStream is = getClass().getClassLoader().getResourceAsStream("license.txt");
		if (is != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				try {
					StringBuilder license = new StringBuilder();
					String next = br.readLine();
					while (next != null) {
						license.append(next).append('\n');
						next = br.readLine();
					}
					licText.setText(license.toString());
				} catch (IOException ex) {
					logger.error("Error opening license file", ex);
				} finally {
					try {
						br.close();
					} catch (IOException ex) {
						logger.error("Error closing BufferedReader", ex);
					}
				}
			} catch (UnsupportedEncodingException ex) {
				logger.error("Error creating BufferedReader", ex);
			}
		}

		JScrollPane licenseScrl = new JScrollPane(licText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		licText.getCaret().setDot(0);
		licText.insert("", 0);

		Border border = BorderFactory.createTitledBorder("Licence:");
		licenseScrl.setBorder(border);

		panel.add(info);
		panel.add(licenseScrl);

		JOptionPane.showOptionDialog(parent,
			panel,
			"About the software",
			JOptionPane.INFORMATION_MESSAGE,
			JOptionPane.PLAIN_MESSAGE,
			null,
			new Object[]{"Close"},
			null
		);
	}
}
