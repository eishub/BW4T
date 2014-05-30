package nl.tudelft.bw4t.scenariogui.gui.epartner;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorData;

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
	/** The checkbox for the left-alone warnings. */
	private JCheckBox leftAloneCheckbox = new JCheckBox("Left-alone Warning");
	/** The checkbox to enable GPS. */
	private JCheckBox gpsCheckBox = new JCheckBox("Geolocator");
	/** The data object. */
	private BotEditorData dataObject = new BotEditorData();
	
	/**
	 * Create the frame.
	 */
	public EpartnerFrame() {
		setTitle("e-Partner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 144);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][141px][][141px][141px]", "[][][125px][125px]"));
		
		leftAloneCheckbox.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(leftAloneCheckbox, "cell 0 0,grow");
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		gpsCheckBox.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(gpsCheckBox, "cell 0 1,grow");
		contentPane.add(cancelButton, "cell 0 3,growx,aligny bottom");
		
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		contentPane.add(resetButton, "cell 2 3,growx,aligny bottom");
		contentPane.add(applyButton, "cell 4 3,alignx right,aligny bottom");
	}
	
	/**
	 * Executes action that needs to happen when  the "Apply" button is pressed.
	 * TODO save the bot
	 */
	public void applyAction() {
		setDataObject();
	}
	
	/**
	 * Executes action that needs to happen when  the "Reset" button is pressed.
	 * Resets to default settings
	 */
	public void resetAction() {
        leftAloneCheckbox.setSelected(false);
        gpsCheckBox.setSelected(false);
	}
	
	/**
	 * Executes action that needs to happen when  the "Cancel" button is pressed. 
	 * closes the BotEditor
	 */
	
	public void cancelAction() {	
		
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
	 * Replaces the apply button.
	 * @param ApplyButton The new button.
	 */
	public void setApplyButton(JButton ApplyButton) {
		this.applyButton = ApplyButton;
	}
	/**
	 * Returns the reset button used.
	 * @return The reset button.
	 */
	public JButton getResetButton() {
		return resetButton;
	}
	/**
	 * Replaces the reset button with a new one.
	 * @param ResetButton The new button.
	 */
	public void setResetButton(JButton ResetButton) {
		this.resetButton = ResetButton;
	}
	/**
	 * Returns the currently used cancel button.
	 * @return The cancel button.
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}
	/**
	 * Replaces the cancel button.
	 * @param CancelButton The new cancel button.
	 */
	public void setCancelButton(JButton CancelButton) {
		this.cancelButton = CancelButton;
	}
	/**
	 * Returns the checkbox enabling or disabling
	 * warnings when the bot is left alone.
	 * @return The checkbox.
	 */
	public JCheckBox getLeftAloneCheckbox() {
		return leftAloneCheckbox;
	}
	/**
	 * Replaces the checkbox enabling or disabling
	 * warnings when the bot is left alone.
	 * @param LeftAloneCheckbox The new checkbox.
	 */
	public void setLeftAloneCheckbox(JCheckBox LeftAloneCheckbox) {
		this.leftAloneCheckbox = LeftAloneCheckbox;
	}
	/**
	 * Returns the checkbox enabling or
	 * disabling GPS functionality.
	 * @return The checkbox.
	 */
	public JCheckBox getGPSCheckbox() {
		return gpsCheckBox;
	}
	/**
	 * Replaces the checkbox enabling or disabling
	 * GPS functionality.
	 * @param GPSCheckbox The new checkbox.
	 */
	public void setGPSCheckbox(JCheckBox GPSCheckbox) {
		this.gpsCheckBox = GPSCheckbox;
	}
	
	/**
	 * This method plugs the GUI values into the data object.
	 */
	public void setDataObject() {
		dataObject.setLeftAlone(leftAloneCheckbox.isEnabled());
		dataObject.setGPS(gpsCheckBox.isEnabled());
	}
	
	/**
	 * Returns the data object with its values for usage.
	 * @return The created data object.
	 */
	public BotEditorData getDataObject() {
		return dataObject;
	}
}

