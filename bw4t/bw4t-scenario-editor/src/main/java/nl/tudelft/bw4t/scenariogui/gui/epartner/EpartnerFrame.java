package nl.tudelft.bw4t.scenariogui.gui.epartner;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;

import javax.swing.BoxLayout;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorData;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.miginfocom.swing.MigLayout;

/**
 * This class creates the frame for the e-Partner GUI.
 * @author Wendy
 */

public class EpartnerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JButton ApplyButton = new JButton("Apply");
	private JButton ResetButton = new JButton("Reset");
	private JButton CancelButton = new JButton("Cancel");
	
	private JCheckBox LeftAloneCheckbox = new JCheckBox("Left-alone Warning");
	private JCheckBox GPSCheckBox = new JCheckBox("Geolocator");
	
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
		
		LeftAloneCheckbox.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(LeftAloneCheckbox, "cell 0 0,grow");
		
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		GPSCheckBox.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(GPSCheckBox, "cell 0 1,grow");
		contentPane.add(CancelButton, "cell 0 3,growx,aligny bottom");
		
		ApplyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		contentPane.add(ResetButton, "cell 2 3,growx,aligny bottom");
		contentPane.add(ApplyButton, "cell 4 3,alignx right,aligny bottom");
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
	public void resetAction(){
        LeftAloneCheckbox.setSelected(false);
        GPSCheckBox.setSelected(false);
	}
	
	/**
	 * Executes action that needs to happen when  the "Cancel" button is pressed. 
	 * closes the BotEditor
	 */
	
	public void cancelAction(){	
		
	}
	
	// getters and setters
		
	public JButton getApplyButton() {
		return ApplyButton;
	}

	public void setApplyButton(JButton ApplyButton) {
		this.ApplyButton = ApplyButton;
	}

	public JButton getResetButton() {
		return ResetButton;
	}

	public void setResetButton(JButton ResetButton) {
		this.ResetButton = ResetButton;
	}

	public JButton getCancelButton() {
		return CancelButton;
	}

	public void setCancelButton(JButton CancelButton) {
		this.CancelButton = CancelButton;
	}

	public JCheckBox getLeftAloneCheckbox() {
		return LeftAloneCheckbox;
	}

	public void setLeftAloneCheckbox(JCheckBox LeftAloneCheckbox) {
		this.LeftAloneCheckbox = LeftAloneCheckbox;
	}

	public JCheckBox getGPSCheckbox() {
		return GPSCheckBox;
	}

	public void setGPSCheckbox(JCheckBox GPSCheckbox) {
		this.GPSCheckBox = GPSCheckbox;
	}
	
	/**
	 * This method plugs the GUI values into the data object.
	 */
	public void setDataObject() {
		dataObject.setLeftAlone(LeftAloneCheckbox.isEnabled());
		dataObject.setGPS(GPSCheckBox.isEnabled());
	}
	
	public BotEditorData getDataObject() {
		return dataObject;
	}
}

