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
                "Right click on a cell to do modify its appearance or its content. "
                + "If you want more options, use \"Tools\" in the menu bar.");

        add(message, BorderLayout.CENTER);
        add(new JSeparator(), BorderLayout.SOUTH);
    }

}
