package nl.tudelft.bw4t.scenariogui.epartner.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerApplyButton;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerCancelButton;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerResetButton;
import nl.tudelft.bw4t.scenariogui.epartner.controller.LeftAloneCheckBox;
import nl.tudelft.bw4t.scenariogui.epartner.controller.gpsCheckBox;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * This class creates the frame for the e-Partner GUI.
 * @author Wendy Bolier
 */

public class EpartnerFrame extends JFrame implements EPartnerViewInterface {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane = new JPanel();

	private JPanel infoPane = new JPanel();

	private JPanel buttonPane = new JPanel();

	private JPanel optionPane = new JPanel();

	private JTextField epartnerNameField = new JTextField();

	private JTextField epartnerAmountField = new JTextField();

	private JButton applyButton = new JButton("Apply");

	private JButton resetButton = new JButton("Reset");

	private JButton cancelButton = new JButton("Cancel");

	private JCheckBox forgetMeNotCheckbox = new JCheckBox("Left-alone Warning");

	private JCheckBox gpsCheckBox = new JCheckBox("Geolocator");

	private EpartnerController controller;

	/**
	 * Create the frame.
	 */
	public EpartnerFrame(EpartnerController controller) {
		setTitle("E-Partner");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);

		this.controller = controller;

		createInfoPanel();
		createOptionPanel();
		createButtonPanel();

		contentPane.add(infoPane, BorderLayout.NORTH);
		contentPane.add(optionPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		


		view.getResetButton().addActionListener(
				new EpartnerResetButton(getMainView()));

		view.getCancelButton().addActionListener(
				new EpartnerCancelButton(getMainView()));

		view.getApplyButton().addActionListener(
				new EpartnerApplyButton(getMainView()));

		view.getLeftAloneCheckbox().addActionListener(
				new LeftAloneCheckBox(getMainView()));

		view.getGPSCheckbox().addActionListener(new gpsCheckBox(getMainView()));

		pack();
		
		controller.addView(this);
		
		setVisible(true);
	}

	/** Create the panel which contains the epartner info. */
	private void createInfoPanel() {
		infoPane.setLayout(new GridLayout(1, 0));
		epartnerNameField.setText(controller.getEpartnerName());
		infoPane.add(epartnerNameField);
		infoPane.add(new JLabel("  Amount of this type:"));
		epartnerAmountField.setText("" + dataObject.getEpartnerAmount());
		infoPane.add(epartnerAmountField);
	}

	/**
	 * Create the panel which contains the epartner options.
	 */
	private void createOptionPanel() {
		optionPane.setLayout(new GridLayout(0, 1));

		optionPane.add(new JLabel(""));
		
		if (dataObject.isForgetMeNot()) {
			leftAloneCheckbox.setSelected(true);
		}
		optionPane.add(leftAloneCheckbox);
		
		if (dataObject.isGps()) {
			gpsCheckBox.setSelected(true);
		}
		optionPane.add(gpsCheckBox);
		optionPane.add(new JLabel(""));
	}

	/**
	 * Create the panel which contains the buttons.
	 */
	private void createButtonPanel() {
		buttonPane.setLayout(new GridLayout(1, 0));

		buttonPane.add(applyButton);
		buttonPane.add(resetButton);
		buttonPane.add(cancelButton);
	}

	/**
	 * Returns the JTextField containing the epartner name.
	 * 
	 * @return The JTextField containing the epartner name.
	 */
	public JTextField getEpartnerName() {
		return this.epartnerNameField;
	}

	/**
	 * Returns the JTextField containing the epartner amount.
	 * 
	 * @return The JTextField containing the epartner amount.
	 */
	public JTextField getEpartnerAmount() {
		return this.epartnerAmountField;
	}

	/**
	 * Returns the used apply button.
	 * 
	 * @return The apply button.
	 */
	public JButton getApplyButton() {
		return applyButton;
	}

	/**
	 * Returns the reset button used.
	 * 
	 * @return The reset button.
	 */
	public JButton getResetButton() {
		return resetButton;
	}

	/**
	 * Returns the currently used cancel button.
	 * 
	 * @return The cancel button.
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}

	/**
	 * Returns the checkbox enabling or disabling warnings when the bot is left
	 * alone.
	 * 
	 * @return The checkbox.
	 */
	public JCheckBox getForgetMeNotCheckbox() {
		return forgetMeNotCheckbox;
	}

	/**
	 * Returns the checkbox enabling or disabling GPS functionality.
	 * 
	 * @return The checkbox.
	 */
	public JCheckBox getGPSCheckbox() {
		return gpsCheckBox;
	}

	/**
	 * 
	 */
	public void update() {
		// hier moeten alle setters komen
	}
}
