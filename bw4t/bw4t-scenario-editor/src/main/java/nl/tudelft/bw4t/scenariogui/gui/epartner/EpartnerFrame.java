package nl.tudelft.bw4t.scenariogui.gui.epartner;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import nl.tudelft.bw4t.scenariogui.BotConfig;

/**
 * This class creates the frame for the e-Partner GUI.
 * @author Wendy
 */

public class EpartnerFrame extends JFrame {
    /** The required serial version UID. */
    private static final long serialVersionUID = 1L;
    /** The JPanel containing all the buttons. */
    private JPanel contentPane;

    /** The apply button. */
    private JButton applyButton = new JButton("Apply");
    /** The reset button. */
    private JButton resetButton = new JButton("Reset");
    /** The cancel button. */
    private JButton cancelButton = new JButton("Cancel");
    /** The checkbox to enable GPS. */
    private JCheckBox gpsCheckbox = new JCheckBox("GPS");
    /** The checkbox for the Forget-me-not app. */
    private JCheckBox forgetCheckBox = new JCheckBox("Forget-me-not");
    /** The data object. */
    //TODO this should be epartnerconfig
    private BotConfig dataObject = new BotConfig();

    /**
     * Create the frame.
     */
    public EpartnerFrame() {
        setTitle("e-Partner");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 292, 144);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[][141px][][141px][141px]", "[][][125px][125px]"));

        gpsCheckbox.setAlignmentY(Component.TOP_ALIGNMENT);
        forgetCheckBox.setVerticalAlignment(SwingConstants.TOP);

        contentPane.add(gpsCheckbox, "cell 0 0,grow");
        contentPane.add(forgetCheckBox, "cell 0 1,grow");
        contentPane.add(cancelButton, "cell 0 3,growx,aligny bottom");
        contentPane.add(resetButton, "cell 2 3,growx,aligny bottom");
        contentPane.add(applyButton, "cell 4 3,alignx right,aligny bottom");
    }
    /**
     * getters and setters
     */
    /**
     * Returns the used apply button.
     * @return The apply button.
     */
    public JButton getApplyButton() {
        return applyButton;
    }
    /**
     * Returns the reset button used.
     * @return The reset button.
     */
    public JButton getResetButton() {
        return resetButton;
    }
    /**
     * Returns the currently used cancel button.
     * @return The cancel button.
     */
    public JButton getCancelButton() {
        return cancelButton;
    }
    /**
     * Returns the checkbox enabling or disabling
     * warnings when the bot is left alone.
     * @return The checkbox.
     */
    public JCheckBox getLeftAloneCheckbox() {
        return gpsCheckbox;
    }
    /**
     * Returns the checkbox enabling or
     * disabling GPS functionality.
     * @return The checkbox.
     */
    public JCheckBox getGPSCheckbox() {
        return forgetCheckBox;
    }
    /**
     * This method plugs the GUI values into the data object.
     */
    public void setDataObject() {
        //TODO set epartnerconfig
    }

    /**
     * Returns the data object with its values for usage.
     * @return The created data object.
     */
    //TODO this should be epartnerconfig
    public BotConfig getDataObject() {
        return dataObject;
    }
}
