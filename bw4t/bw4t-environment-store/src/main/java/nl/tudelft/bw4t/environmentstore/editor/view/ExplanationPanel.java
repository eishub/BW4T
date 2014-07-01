package nl.tudelft.bw4t.environmentstore.editor.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * This ExplanatinPanel contains an explanation for on top of the
 * EnvironmentStore.
 */
public class ExplanationPanel extends JPanel {

	/** Random generated serial version UID. */
	private static final long serialVersionUID = 7898118456497448363L;

	/**
	 * Set the layout and message that should be displayed.
	 */
	public ExplanationPanel() {
		setLayout(new BorderLayout());

		JLabel message = new JLabel(
				"Right click on a cell to do modify its appearance. "
						+ "If you choose to modify this cell to a room, "
						+ "you can right click to change the position of the door, "
						+ "or click on the white fiels to add or delete blocks.");

		add(message, BorderLayout.CENTER);
		add(new JSeparator(), BorderLayout.SOUTH);
	}

}
