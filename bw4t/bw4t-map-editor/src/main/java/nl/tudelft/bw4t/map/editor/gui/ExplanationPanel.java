package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * This ExplanatinPanel contains an explanation for on top of the EnvironmentStore.
 *
 */
public class ExplanationPanel extends JPanel {

	private static final long serialVersionUID = 7898118456497448363L;
	
	/**
	 * Set the layout and message that should be displayed.
	 */
	public ExplanationPanel() {
		setLayout(new BorderLayout());
		
		JLabel message = new JLabel("To add blocks to the map, enter the corresponding numbers in rooms "
				+ "(up to 10) and target sequence, or use the pallette.");
		
		add(message, BorderLayout.CENTER);
		add(new JSeparator(), BorderLayout.SOUTH);
	}

}
