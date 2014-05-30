package nl.tudelft.bw4t.scenariogui.gui.botstore;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import nl.tudelft.bw4t.scenariogui.BotConfig;

/**
 * BotEditorPanel which serves as the content pane for the BotEditor frame
 * @author Arun
 */
public class BotEditorPanel extends JPanel {
	
	/**
	 * The generated serial version UID.
	 */
	private static final long serialVersionUID = 1850617931893202292L;
	/**
	 * Side notes from Valentine:
	 * It would be nice to have the value of speed and size automatically change when a MoveSpeed or SizeOverload
	 * handicap is checked. This way, the BotEditorData updates the botSpeed and botSize to the right values,
	 * and we can use these values to instantiate the two handicaps. 
	 * Also, once those values are automatically changed, we should disable user interaction with the sliders.
	 */
	/**
	 * Panel for all checkboxes.
	 */
	private JPanel botCheckables = new JPanel();
	/**
	 * Panel for all sliders.
	 */
	private JPanel botSliders = new JPanel();
	/**
	 * The button to be clicked on to save the data object.
	 */
	private JButton applyButton = new JButton("Apply");
	/**
	 * The button to be clicked on to reset all
	 * checkboxes and sliders to the initial values.
	 */
	private JButton resetButton = new JButton("Reset");
	/**
	 * The button to cancel the editing of the
	 * data object and to close the frame.
	 */
	private JButton cancelButton = new JButton("Cancel");
	/**
	 * The label containing the name of the bot.
	 */
	private JLabel botNameTextField = new JLabel();
	/**
	 * The checkbox for enabling/disabling the gripper.
	 */
	private JCheckBox gripperCheckbox = new JCheckBox("Gripper Disabled");
	/**
	 * The checkbox for enabling/disabling color blindness.
	 */
	private JCheckBox colorblindCheckbox = new JCheckBox("Color Blind Handicap");
	/**
	 * The checkbox for enabling/disabling changing bot sizes.
	 */
	private JCheckBox customSizeCheckbox = new JCheckBox("Custom Bot Size");
	/**
	 * The checkbox for enabling/disabling changing bot speeds.
	 */
	private JCheckBox movespeedCheckbox = new JCheckBox("Custom Bot Speed");
	/**
	 * The checkbox for enabling/disabling the usage
	 * of a battery with finite capacity.
	 */
	private JCheckBox batteryEnabledCheckbox = new JCheckBox("Battery Capacity enabled");
	/**
	 * The slider to set the size of the bot.
	 */
	private JSlider sizeSlider = new JSlider();
	/**
	 * The slider to set the speed of the bot.
	 */
	private JSlider speedSlider = new JSlider();
	/**
	 * The slider to set the battery capacity of the bot.
	 */
	private JSlider batterySlider = new JSlider();
	/**
	 * The slider to set the amount of grippers the bot can have.
	 */
	private JSlider numberOfGrippersSlider = new JSlider();
	/**
	 * A dynamically updated label to show
	 * what the usage of battery charge is per tick.
	 */
	private JLabel batteryUseValueLabel = new JLabel("0,006500");
	/**
	 * The data object.
	 */
	private BotConfig dataObject = new BotConfig();
	
	/**
	 * Create the botEditorPanel
	 * @param name the bot gets
	 */
	public BotEditorPanel(String name) {
		botNameTextField.setText(name);
		setLayout(new BorderLayout(20, 20));		
		
		createBotCheckablesPanel();
		createBotSlidersPanel();
		
		add(botSliders, BorderLayout.WEST);
		add(botCheckables, BorderLayout.EAST);
	}
	
	/**
	 * create the checkables panel
	 */
	public void createBotCheckablesPanel() {
		botCheckables.setLayout(new GridLayout(4, 1));
		JLabel checkablesLabel = new JLabel("Checkables");
		JLabel handicapsLabel = new JLabel("Handicaps:");
		JLabel restrictionsLabel = new JLabel("Other options:");
		JLabel emptyLabel = new JLabel("\n");
		
		checkablesLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JPanel buttonPanel = new JPanel();
		JPanel checkablesPanel = new JPanel();
		JPanel empty = new JPanel();
		
		buttonPanel.add(applyButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(cancelButton);
		
		botCheckables.add(checkablesLabel);
		checkablesPanel.setLayout(new BoxLayout(checkablesPanel, BoxLayout.PAGE_AXIS));
		checkablesPanel.add(handicapsLabel);
		checkablesPanel.add(gripperCheckbox);
		checkablesPanel.add(colorblindCheckbox);
		checkablesPanel.add(emptyLabel);
		checkablesPanel.add(restrictionsLabel);
		checkablesPanel.add(customSizeCheckbox);
		checkablesPanel.add(movespeedCheckbox);
		checkablesPanel.add(batteryEnabledCheckbox);
		botCheckables.add(checkablesPanel);
		botCheckables.add(empty);
		botCheckables.add(buttonPanel);
		
	}
	/**
	 * creates the botSlidersPanel
	 */
	public void createBotSlidersPanel() {
		botSliders.setLayout(new GridLayout(10, 1));
		
		JLabel batteryUseLabel = new JLabel("Battery use:");
		JLabel perTickLabel = new JLabel("per tick");
		batteryUseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel botNameLabel = new JLabel("Bot name:");
		
		JPanel batteryCapPanel = new JPanel();
		JPanel botNamePanel = new JPanel();
		
		batteryCapPanel.add(batteryUseLabel);
		batteryCapPanel.add(batteryUseValueLabel);
		batteryCapPanel.add(perTickLabel);
		
		botNamePanel.setLayout(new BoxLayout(botNamePanel, BoxLayout.PAGE_AXIS));
		botNamePanel.add(botNameLabel);
		botNamePanel.add(botNameTextField);
		
		JLabel numberOfGrippersLabel = new JLabel("Number of Grippers");
		numberOfGrippersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfGrippersLabel.setToolTipText("default is 1");
		JLabel sizeLabel = new JLabel("Bot Size");
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sizeLabel.setToolTipText("default is 2");
		JLabel speedLabel = new JLabel("Bot speed");
		speedLabel.setToolTipText("This speed is relative to the bots. The default is 100%");
		speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel batteryCapacity = new JLabel("Battery Capacity");
		batteryCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		batteryCapacity.setToolTipText("Max capacity on a scale of 10-100");
		
		createSliders();
		botSliders.add(botNamePanel);
		botSliders.add(numberOfGrippersLabel);
		botSliders.add(numberOfGrippersSlider);
		botSliders.add(sizeLabel);
		botSliders.add(sizeSlider);
		botSliders.add(speedLabel);
		botSliders.add(speedSlider);
		botSliders.add(batteryCapacity);
		botSliders.add(batterySlider);
		botSliders.add(batteryCapPanel);
	}
	
	/**
	 * sets the default settings for the sliders
	 */
	public void createSliders() {
		numberOfGrippersSlider.setMajorTickSpacing(1);
		numberOfGrippersSlider.setMaximum(5);
		numberOfGrippersSlider.setMinimum(1);
		numberOfGrippersSlider.setPaintTicks(true);
		numberOfGrippersSlider.setPaintLabels(true);
		numberOfGrippersSlider.setSnapToTicks(true);
		numberOfGrippersSlider.setValue(1);
		numberOfGrippersSlider.setValueIsAdjusting(true);
		
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setMaximum(5);
		sizeSlider.setMinimum(1);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setSnapToTicks(true);
		sizeSlider.setValue(2);
		sizeSlider.setEnabled(false);
		sizeSlider.setValueIsAdjusting(true);
		
		speedSlider.setMajorTickSpacing(10);
		speedSlider.setMaximum(140);
		speedSlider.setMinimum(70);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(100);
        speedSlider.setEnabled(false);
        speedSlider.setValueIsAdjusting(true);
        
        batterySlider = new JSlider();
        batterySlider.setMinimum(10);
        batterySlider.setMaximum(100);
        batterySlider.setValue(10);
        batterySlider.setSnapToTicks(true);
        batterySlider.setPaintTicks(true);
        batterySlider.setPaintLabels(true);
        batterySlider.setEnabled(false);
        batterySlider.setMajorTickSpacing(10);
        
	}
	
	/**
	 * Returns the applybutton
	 * @return the applyButton
	 */
	public JButton getApplyButton() {
		return applyButton;
	}
	/**
	 * Sets the apply button.
	 * @param _applyButton The new apply button.
	 */
	public void setApplyButton(JButton _applyButton) {
		this.applyButton = _applyButton;
	}
	/**
	 * Returns the reset button.
	 * @return the reset button.
	 */
	public JButton getResetButton() {
		return resetButton;
	}
	/**
	 * Sets a new reset button.
	 * @param _resetButton The new reset button.
	 */
	public void setResetButton(JButton _resetButton) {
		this.resetButton = _resetButton;
	}
	/**
	 * Returns the cancel button.
	 * @return The cancel button.
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}
	/**
	 * Sets a new cancel button to be used.
	 * @param _cancelButton The new cancel button.
	 */
	public void setCancelButton(JButton _cancelButton) {
		this.cancelButton = _cancelButton;
	}
	/**
	 * Get the currently used gripper checkbox.
	 * @return The checkbox for setting the gripper handicap.
	 */
	public JCheckBox getGripperCheckbox() {
		return gripperCheckbox;
	}
	/**
	 * Set a new gripper checkbox.
	 * @param _gripperCheckbox The new gripper checkbox.
	 */
	public void setGripperCheckbox(JCheckBox _gripperCheckbox) {
		this.gripperCheckbox = _gripperCheckbox;
	}
	/**
	 * Get the currently used color blindness checkbox.
	 * @return The checkbox for setting the color blind handicap.
	 */
	public JCheckBox getColorblindCheckbox() {
		return colorblindCheckbox;
	}
	/**
	 * Set a new color blindness checkbox.
	 * @param _colorblindCheckbox The new checkbox.
	 */
	public void setColorblindCheckbox(JCheckBox _colorblindCheckbox) {
		this.colorblindCheckbox = _colorblindCheckbox;
	}
	/**
	 * Returns the currently used move speed checkbox.
	 * @return The move speed checkbox.
	 */
	public JCheckBox getmovespeedCheckbox() {
		return movespeedCheckbox;
	}
	/**
	 * Set a new move speed checkbox.
	 * @param _movespeedCheckbox The new checkbox.
	 */
	public void setmovespeedCheckbox(JCheckBox _movespeedCheckbox) {
		this.movespeedCheckbox = _movespeedCheckbox;
	}
	/**
	 * Returns the used custom size checkbox.
	 * @return The custom size checkbox.
	 */
	public JCheckBox getsizeoverloadCheckbox() {
		return customSizeCheckbox;
	}
	/**
	 * Replace the custom size checkbox with a new checkbox.
	 * @param _sizeoverloadCheckbox The new checkbox.
	 */
	public void setsizeoverloadCheckbox(JCheckBox _sizeoverloadCheckbox) {
		this.customSizeCheckbox = _sizeoverloadCheckbox;
	}
	/**
	 * Returns the used size slider.
	 * @return The size slider.
	 */
	public JSlider getSizeSlider() {
		return sizeSlider;
	}
	/**
	 * Replace the size slider with a new one.
	 * @param _sizeSlider The new size slider.
	 */
	public void setSizeSlider(JSlider _sizeSlider) {
		this.sizeSlider = _sizeSlider;
	}
	/**
	 * Returns the current speed slider.
	 * @return The used speed slider.
	 */
	public JSlider getSpeedSlider() {
		return speedSlider;
	}
	/**
	 * Replaces the speed slider with a new slider.
	 * @param _speedSlider The new slider.
	 */
	public void setSpeedSlider(JSlider _speedSlider) {
		this.speedSlider = _speedSlider;
	}
	/**
	 * Returns the currently used battery slider.
	 * @return The battery slider.
	 */
	public JSlider getBatterySlider() {
		return batterySlider;
	}
	/**
	 * Replaces the battery enabled checkbox with a new one.
	 * @param _batteryEnabledCheckbox The new checkbox.
	 */
	public void setBatteryEnabledCheckbox(JCheckBox _batteryEnabledCheckbox) {
		this.batteryEnabledCheckbox = _batteryEnabledCheckbox;
	}
	/**
	 * Returns the used battery enabled checkbox.
	 * @return The used checkbox.
	 */
	public JCheckBox getBatteryEnabledCheckbox() {
		return batteryEnabledCheckbox;
	}
	/**
	 * Replaces the battery slider.
	 * @param _batterySlider The new slider.
	 */
	public void setBatterySlider(JSlider _batterySlider) {
		this.batterySlider = _batterySlider;
	}
	/**
	 * Return the label describing what the robot
	 * uses regarding battery potential per tick.
	 * @return The aforementioned label.
	 */
	public JLabel getBatteryUseValueLabel() {
		return batteryUseValueLabel;
	}	
	/**
	 * Replace the label describing the battery usage
	 * of the robot.
	 * @param _batteryUseValueLabel The new label.
	 */
	public void setBatteryUseValueLabel(JLabel _batteryUseValueLabel) {
		this.batteryUseValueLabel = _batteryUseValueLabel;
	}
	/**
	 * Returns the created data object and the
	 * settings contained.
	 * @return The data object.
	 */
	public BotConfig getDataObject() {
		return dataObject;
	}
	/**
	 * Returns the slider determining the amount of
	 * grippers the bot can use.
	 * @return The aforementioned slider.
	 */
	public JSlider getNumberOfGrippersSlider() {
		return numberOfGrippersSlider;
	}
	/**
	 * Replaces the slider determining the amount of
	 * grippers the bot can use.
	 * @param _numberOfGrippersSlider The new slider.
	 */
	public void setNumberOfGrippersSlider(JSlider _numberOfGrippersSlider) {
		this.numberOfGrippersSlider = _numberOfGrippersSlider;
	}
}
