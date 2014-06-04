package nl.tudelft.bw4t.scenariogui.gui.epartner;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.controllers.epartner.EpartnerController;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * This class creates the frame for the e-Partner GUI.
 * 
 * @author Katia
 */

public class EpartnerFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane = new JPanel();

	private JPanel infoPane = new JPanel();

	private JPanel buttonPane = new JPanel();

	private JPanel optionPane = new JPanel();

	private JPanel goalPane = new JPanel();

	private JTextField epartnerNameField = new JTextField();

	private JTextField epartnerAmountField = new JTextField();

	private JTextField epartnerReferenceField = new JTextField();

	private JTextField epartnerGoalFileField = new JTextField();

	private JButton saveButton = new JButton("Save");

	private JButton resetButton = new JButton("Reset");

	private JButton cancelButton = new JButton("Cancel");

	private JButton fileButton = new JButton("Use existing GOAL file");

	private JCheckBox leftAloneCheckbox = new JCheckBox("Left-alone Warning");

	private JCheckBox gpsCheckBox = new JCheckBox("Geolocator");

	private EPartnerConfig dataObject = new EPartnerConfig();

	private MainPanel panel;

	private int row;

	private EpartnerController controller;

	/**
	 * Create the frame.
	 */
	public EpartnerFrame(MainPanel panel, int row) {
		setTitle("E-Partner");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);

		this.panel = panel;
		this.row = row;
		this.dataObject = panel.getEntityPanel().getEPartnerConfig(row);

		createInfoPanel();
		createOptionPanel();
		createButtonPanel();

		contentPane.add(infoPane, BorderLayout.NORTH);
		contentPane.add(optionPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);

		setResizable(false);
		pack();
		setVisible(true);

		controller = new EpartnerController(this);
	}

	/** Create the panel which contains the epartner info. */
	private void createInfoPanel() {
		infoPane.setLayout(new GridLayout(1, 0));
		epartnerNameField.setText(dataObject.getEpartnerName());
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

		JLabel propertiesLabel = new JLabel("Properties");
		propertiesLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		optionPane.add(propertiesLabel);

		if (dataObject.isForgetMeNot()) {
			leftAloneCheckbox.setSelected(true);
		}
		optionPane.add(leftAloneCheckbox);

		if (dataObject.isGps()) {
			gpsCheckBox.setSelected(true);
		}
		optionPane.add(gpsCheckBox);
		optionPane.add(new JLabel(""));

		addGoalOptions();

		optionPane.add(new JLabel(""));
	}

	/**
	 * Add the goal options to the option panel.
	 */
	private void addGoalOptions() {
		JLabel goalLabel = new JLabel("GOAL options");
		goalLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		optionPane.add(goalLabel);
		optionPane.add(new JLabel("E-partner reference name:"));
		epartnerReferenceField.setText(dataObject.getReferenceName());
		optionPane.add(epartnerReferenceField);
		optionPane.add(new JLabel("GOAL file name:"));
		epartnerGoalFileField.setText(dataObject.getFileName());
		optionPane.add(epartnerGoalFileField);
		optionPane.add(fileButton);
	}

	/**
	 * Create the panel which contains the buttons.
	 */
	private void createButtonPanel() {
		buttonPane.setLayout(new GridLayout(1, 0));

		buttonPane.add(saveButton);
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
		return saveButton;
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
	public JCheckBox getLeftAloneCheckbox() {
		return leftAloneCheckbox;
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
	 * Returns the MainPanel.
	 * 
	 * @return The MainPanel.
	 */
	public MainPanel getPanel() {
		return panel;
	}

	/**
	 * Returns the selected row.
	 * 
	 * @return The selected row.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the data object with its values for usage.
	 * 
	 * @return The created data object.
	 */
	public EPartnerConfig getDataObject() {
		return dataObject;
	}

	/**
	 * Returns the JTextField which contains the goal reference name.
	 * 
	 * @return The JTextField which contains the goal reference name.
	 */
	public JTextField getEpartnerReferenceField() {
		return epartnerReferenceField;
	}

	/**
	 * Returns the JTextField which contains the goal file name.
	 * 
	 * @return The JTextField which contains the goal file name.
	 */
	public JTextField getEpartnerGoalFileField() {
		return epartnerGoalFileField;
	}

	/**
	 * Returns the JButton for adding a goal file.
	 * 
	 * @return The JButton for adding a goal file.
	 */
	public JButton getFileButton() {
		return fileButton;
	}

}
