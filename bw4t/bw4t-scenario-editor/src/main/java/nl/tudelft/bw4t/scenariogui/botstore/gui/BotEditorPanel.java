package nl.tudelft.bw4t.scenariogui.botstore.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * BotEditorPanel which serves as the content pane for the BotEditor frame
 * 
 * @author Arun
 */
public class BotEditorPanel extends JPanel {

    private static final long serialVersionUID = 1850617931893202292L;

    private JPanel botCheckables = new JPanel();

    private JPanel botSliders = new JPanel();

    private JPanel botInfo = new JPanel();

    private JButton applyButton = new JButton("Apply");

    private JButton resetButton = new JButton("Reset");

    private JButton cancelButton = new JButton("Cancel");

    private JButton fileButton = new JButton("Use existing GOAL file");

    private JComboBox botControllerSelector = new JComboBox();

    private JTextField botNameField = new JTextField();

    private JTextField botAmountTextField = new JTextField();

    private JCheckBox gripperCheckbox = new JCheckBox("Gripper Disabled");

    private JCheckBox colorblindCheckbox = new JCheckBox("Color Blind Handicap");

    private JCheckBox customSizeCheckbox = new JCheckBox("Custom Bot Size");

    private JCheckBox movespeedCheckbox = new JCheckBox("Custom Bot Speed");

    private JCheckBox batteryEnabledCheckbox = new JCheckBox(
            "Battery Capacity enabled");

    private JTextField fileNameField = new JTextField(".goal");

    private JTextField botReferenceField = new JTextField();

    private JSlider sizeSlider = new JSlider();

    private JSlider speedSlider = new JSlider();

    private JSlider batterySlider = new JSlider();

    private JSlider numberOfGrippersSlider = new JSlider();

    private JLabel batteryUseValueLabel = new JLabel("0");

    private BotConfig dataObject = new BotConfig();

    private MainPanel mainPanel;

    private BotEditor botEditor;

    /**
     * Create the botEditorPanel.
     * 
     * @param botEditor
     *            The BotEditor.
     * @param mainPanel
     *            The MainPanel.
     */
    public BotEditorPanel(BotEditor botEditor, MainPanel mainPanel) {
        setLayout(new BorderLayout(20, 20));

        this.mainPanel = mainPanel;
        this.botEditor = botEditor;
        this.dataObject = mainPanel.getEntityPanel().getBotConfig(
                botEditor.getRow());

        createBotInfoPanel();
        createBotCheckablesPanel();
        createBotSlidersPanel();

        add(botInfo, BorderLayout.NORTH);
        add(botSliders, BorderLayout.EAST);
        add(botCheckables, BorderLayout.WEST);
    }

    /**
     * Create the panel which contains the bots name and the controller type
     */
    private void createBotInfoPanel() {
        botInfo.setLayout(new GridLayout(10, 0));

        botNameField.setText(dataObject.getBotName());
        botControllerSelector.setModel(new DefaultComboBoxModel(new String[] {
                "Agent", "Human" }));
        botAmountTextField.setText("" + dataObject.getBotAmount());

        botInfo.add(new JLabel("Bot name:"));
        botNameField.setText(dataObject.getBotName());
        botInfo.add(botNameField);
        JPanel controllerpanel = new JPanel();
        controllerpanel.setLayout(new GridLayout(1, 0));
        controllerpanel.add(botControllerSelector);
        controllerpanel.add(new JLabel("   Amount of this type:"));
        controllerpanel.add(botAmountTextField);
        botInfo.add(controllerpanel);
        botInfo.add(new JLabel(""));
        JLabel goalLabel = new JLabel("GOAL options");
        goalLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        botInfo.add(goalLabel);
        botInfo.add(new JLabel("Bot reference name:"));
        botReferenceField.setText(dataObject.getReferenceName());
        botInfo.add(botReferenceField);
        botInfo.add(new JLabel("GOAL file name:"));
        fileNameField.setText(dataObject.getFileName());
        botInfo.add(fileNameField);
        botInfo.add(fileButton);
    }

    /**
     * create the checkables panel
     */
    private void createBotCheckablesPanel() {
        botCheckables.setLayout(new GridLayout(2, 1));
        JLabel checkablesLabel = new JLabel("Specs");
        checkablesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JPanel checkablesPanel = new JPanel();
        checkablesPanel.setLayout(new BoxLayout(checkablesPanel,
                BoxLayout.PAGE_AXIS));
        checkablesPanel.add(checkablesLabel);
        checkablesPanel.add(new JLabel("Handicaps:"));
        if (dataObject.getGripperHandicap()) {
            gripperCheckbox.setSelected(true);
        }
        checkablesPanel.add(gripperCheckbox);
        if (dataObject.getColorBlindHandicap()) {
            colorblindCheckbox.setSelected(true);
        }
        checkablesPanel.add(colorblindCheckbox);
        checkablesPanel.add(new JLabel("Other options:"));
        if (dataObject.getSizeOverloadHandicap()) {
            customSizeCheckbox.setSelected(true);
        }
        checkablesPanel.add(customSizeCheckbox);
        if (dataObject.getMoveSpeedHandicap()) {
            movespeedCheckbox.setSelected(true);
        }
        checkablesPanel.add(movespeedCheckbox);
        if (dataObject.isBatteryEnabled()) {
            batteryEnabledCheckbox.setSelected(true);
        }
        checkablesPanel.add(batteryEnabledCheckbox);
        botCheckables.add(checkablesPanel);
    }

    /**
     * creates the botSlidersPanel
     */
    private void createBotSlidersPanel() {
        botSliders.setLayout(new GridLayout(6, 1));

        createSliders();

        addGripperPanel();
        addSizePanel();
        addSpeedPanel();
        addBatteryPanel();
        addBatteryCapPanel();
        addButtonPanel();
    }

    /**
     * Craete and add the gripper panel to the sliders panel.
     */
    private void addGripperPanel() {
        JPanel gripperPanel = new JPanel();

        gripperPanel
                .setLayout(new BoxLayout(gripperPanel, BoxLayout.PAGE_AXIS));
        JLabel numberOfGrippersLabel = new JLabel("Number of Grippers");
        numberOfGrippersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfGrippersLabel.setToolTipText("default is 1");
        gripperPanel.add(numberOfGrippersLabel);
        numberOfGrippersSlider.setValue(dataObject.getGrippers());

        if (dataObject.getGripperHandicap()) {
            numberOfGrippersSlider.setEnabled(false);
        }
        gripperPanel.add(numberOfGrippersSlider);

        botSliders.add(gripperPanel);
    }

    /**
     * Create and add the size panel to the sliders panel.
     */
    private void addSizePanel() {
        JPanel sizePanel = new JPanel();

        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.PAGE_AXIS));
        JLabel sizeLabel = new JLabel("Bot Size");
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sizeLabel.setToolTipText("default is 2");
        sizePanel.add(sizeLabel);
        sizeSlider.setValue(dataObject.getBotSize());

        if (dataObject.getSizeOverloadHandicap()) {
            sizeSlider.setEnabled(true);
        }
        sizePanel.add(sizeSlider);

        botSliders.add(sizePanel);
    }

    /**
     * Create and add the speed panel to the sliders panel.
     */
    private void addSpeedPanel() {
        JPanel speedPanel = new JPanel();

        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.PAGE_AXIS));
        JLabel speedLabel = new JLabel("Bot speed");
        speedLabel
                .setToolTipText("This speed is relative to the bots. The default is 100%");
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        speedPanel.add(speedLabel);
        speedSlider.setValue(dataObject.getBotSpeed());

        if (dataObject.getMoveSpeedHandicap()) {
            speedSlider.setEnabled(true);
        }
        speedPanel.add(speedSlider);

        botSliders.add(speedPanel);
    }

    /**
     * Create and add the battery panel to the sliders panel.
     */
    private void addBatteryPanel() {
        JPanel batteryPanel = new JPanel();

        batteryPanel
                .setLayout(new BoxLayout(batteryPanel, BoxLayout.PAGE_AXIS));
        JLabel batteryCapacity = new JLabel("Battery Capacity");
        batteryCapacity.setHorizontalAlignment(SwingConstants.CENTER);
        batteryCapacity.setToolTipText("Max capacity on a scale of 10-100");
        batteryPanel.add(batteryCapacity);
        batterySlider.setValue(dataObject.getBotBatteryCapacity());

        if (dataObject.isBatteryEnabled()) {
            speedSlider.setEnabled(true);
        }
        batteryPanel.add(batterySlider);

        botSliders.add(batteryPanel);
    }

    /**
     * Create and add the battery capacity panel to the sliders panel.
     */
    private void addBatteryCapPanel() {
        JPanel batteryCapPanel = new JPanel();

        JLabel batteryUseLabel = new JLabel("Battery use:");
        batteryUseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        batteryCapPanel.add(batteryUseLabel);
        batteryCapPanel.add(batteryUseValueLabel);
        batteryCapPanel.add(new JLabel("per tick"));

        botSliders.add(batteryCapPanel);
    }

    /**
     * Create and add the button panel to the sliders panel.
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(applyButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        botSliders.add(buttonPanel);
    }

    /**
     * sets the default settings for the sliders
     */
    private void createSliders() {
        createGripperSlider();
        createSizeSlider();
        createSpeedSlider();
        createBatterySlider();
    }

    /**
     * Create the gripper slider.
     */
    private void createGripperSlider() {
        numberOfGrippersSlider.setMajorTickSpacing(1);
        numberOfGrippersSlider.setMaximum(5);
        numberOfGrippersSlider.setMinimum(1);
        numberOfGrippersSlider.setPaintTicks(true);
        numberOfGrippersSlider.setPaintLabels(true);
        numberOfGrippersSlider.setSnapToTicks(true);
        numberOfGrippersSlider.setValue(1);
        numberOfGrippersSlider.setValueIsAdjusting(true);
    }

    /**
     * Create the size slider.
     */
    private void createSizeSlider() {
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setMaximum(5);
        sizeSlider.setMinimum(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.setSnapToTicks(true);
        sizeSlider.setValue(2);
        sizeSlider.setEnabled(false);
        sizeSlider.setValueIsAdjusting(true);
    }

    /**
     * Create the speed slider.
     */
    private void createSpeedSlider() {
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMaximum(140);
        speedSlider.setMinimum(70);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(100);
        speedSlider.setEnabled(false);
        speedSlider.setValueIsAdjusting(true);
    }

    /**
     * Create the battery slider.
     */
    private void createBatterySlider() {
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
     * 
     * @return the applyButton
     */
    public JButton getApplyButton() {
        return applyButton;
    }

    /**
     * Returns the reset button.
     * 
     * @return the reset button.
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /**
     * Returns the cancel button.
     * 
     * @return The cancel button.
     */
    public JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Get the currently used gripper checkbox.
     * 
     * @return The checkbox for setting the gripper handicap.
     */
    public JCheckBox getGripperCheckbox() {
        return gripperCheckbox;
    }

    /**
     * Get the currently used color blindness checkbox.
     * 
     * @return The checkbox for setting the color blind handicap.
     */
    public JCheckBox getColorblindCheckbox() {
        return colorblindCheckbox;
    }

    /**
     * Returns the currently used move speed checkbox.
     * 
     * @return The move speed checkbox.
     */
    public JCheckBox getmovespeedCheckbox() {
        return movespeedCheckbox;
    }

    /**
     * Returns the used custom size checkbox.
     * 
     * @return The custom size checkbox.
     */
    public JCheckBox getsizeoverloadCheckbox() {
        return customSizeCheckbox;
    }

    /**
     * Returns the used size slider.
     * 
     * @return The size slider.
     */
    public JSlider getSizeSlider() {
        return sizeSlider;
    }

    /**
     * Returns the current speed slider.
     * 
     * @return The used speed slider.
     */
    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    /**
     * Returns the currently used battery slider.
     * 
     * @return The battery slider.
     */
    public JSlider getBatterySlider() {
        return batterySlider;
    }

    /**
     * Returns the used battery enabled checkbox.
     * 
     * @return The used checkbox.
     */
    public JCheckBox getBatteryEnabledCheckbox() {
        return batteryEnabledCheckbox;
    }

    /**
     * Return the label describing what the robot uses regarding battery
     * potential per tick.
     * 
     * @return The aforementioned label.
     */
    public JLabel getBatteryUseValueLabel() {
        return batteryUseValueLabel;
    }

    /**
     * Returns the created data object and the settings contained.
     * 
     * @return The data object.
     */
    public BotConfig getDataObject() {
        return dataObject;
    }

    /**
     * Returns the slider determining the amount of grippers the bot can use.
     * 
     * @return The aforementioned slider.
     */
    public JSlider getNumberOfGrippersSlider() {
        return numberOfGrippersSlider;
    }

    /**
     * Returns the JTextField that contains the goal file name.
     * 
     * @return The JTextField that contains the goal file name.
     */
    public JTextField getFileNameField() {
        return fileNameField;
    }

    /**
     * Returns the JTextField that contains the bot name.
     * 
     * @return The JTextField that contains the bot name.
     */
    public JTextField getBotNameField() {
        return botNameField;
    }

    /**
     * Returns the JCheckbox for custom size.
     * 
     * @return The JCheckbox for custom size.
     */
    public JCheckBox getCustomSizeCheckbox() {
        return customSizeCheckbox;
    }

    /**
     * Returns the JButton for adding a goal file.
     * 
     * @return The JButton for adding a goal file.
     */
    public JButton getFileButton() {
        return fileButton;
    }

    /**
     * Returns the JComboBox that contains the bot controller type.
     * 
     * @return The JComboBox that contains the bot controller type.
     */
    public JComboBox getBotControllerSelector() {
        return botControllerSelector;
    }

    /**
     * Returns the JTextField that contains the amount of a bot.
     * 
     * @return The JTextField that contains the amount of a bot.
     */
    public JTextField getBotAmountTextField() {
        return botAmountTextField;
    }

    /**
     * Returns the JTextField that contains the goal reference name.
     * 
     * @return The JTextField that contains the goal reference name.
     */
    public JTextField getBotReferenceField() {
        return botReferenceField;
    }

	public BotEditor getBotEditor() {
		return botEditor;
	}
}
