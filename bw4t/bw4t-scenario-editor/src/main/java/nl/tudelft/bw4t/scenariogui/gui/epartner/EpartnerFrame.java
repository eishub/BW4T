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

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JButton applyButton = new JButton("Apply");
	private JButton resetButton = new JButton("Reset");
	private JButton cancelButton = new JButton("Cancel");
	
	private JCheckBox leftAloneCheckbox = new JCheckBox("Left-alone Warning");
	private JCheckBox gpsCheckBox = new JCheckBox("Geolocator");
	
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
	
	public JButton getApplyButton() {
		return applyButton;
	}

	public void setApplyButton(JButton ApplyButton) {
		this.applyButton = ApplyButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton ResetButton) {
		this.resetButton = ResetButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton CancelButton) {
		this.cancelButton = CancelButton;
	}

	public JCheckBox getLeftAloneCheckbox() {
		return leftAloneCheckbox;
	}

	public void setLeftAloneCheckbox(JCheckBox LeftAloneCheckbox) {
		this.leftAloneCheckbox = LeftAloneCheckbox;
	}

	public JCheckBox getGPSCheckbox() {
		return gpsCheckBox;
	}

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
	
	public BotEditorData getDataObject() {
		return dataObject;
	}
}

