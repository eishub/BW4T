package nl.tudelft.bw4t.scenariogui.gui.botstore;
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
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

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
     * Panel that shows name, controller type and amount.
     */
    private JPanel botInfo = new JPanel();

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
     * The button that opens the file chooser to select
     * an existing .goal file to use.
     */
    private JButton fileButton = new JButton("Use existing GOAL file");
    /**
     * Combobox for the controller types.
     */
    private JComboBox botControllerSelector = new JComboBox();

    /**
     * TextField that shows the name of the bot.
     */
    private JLabel botNameTextField = new JLabel();
    /**
     * Textfield that shows the amount of bots of this type there are.
     */
    private JTextField botAmountTextField = new JTextField();

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
     * The text field containing the file name the
     * bot should use.
     */
    private JTextField fileNameField = new JTextField(".goal");
    /**
     * The text field containing the reference name
     * of the bot.
     */
    private JTextField botNameField = new JTextField();
    /**
     * The slider to set the size of the bot.
     */
    private JTextField botReferenceField = new JTextField();

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
    private JLabel batteryUseValueLabel = new JLabel("0");
    /**
     * The data object.
     */
    private BotConfig dataObject = new BotConfig();

    /**
     * The MainPanel.
     */
    private MainPanel mainPanel;

    /**
     * The BotEditor.
     */
    private BotEditor botEditor;

    /**
     * Create the botEditorPanel.
     * @param botEditor The BotEditor.
     * @param mainPanel The MainPanel.
     */
    public BotEditorPanel(BotEditor botEditor, MainPanel mainPanel) {
        setLayout(new BorderLayout(20, 20));

        this.mainPanel = mainPanel;
        this.botEditor = botEditor;
        this.dataObject = mainPanel.getEntityPanel().getBotConfig(botEditor.getRow());

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

        JLabel nameLabel = new JLabel("Names");
        JLabel fileNameLabel = new JLabel("GOAL File name:");
        JLabel botNameLabel = new JLabel("Bot Name:");

        botNameField.setText(dataObject.getBotName());
        botControllerSelector.setModel(new DefaultComboBoxModel(new String[]{"Agent","Human"}));
        botAmountTextField.setText("" + dataObject.getBotAmount());

        botNameTextField.setText(dataObject.getBotName());
        nameLabel.setFont( new Font("Tahoma", Font.PLAIN, 24));

        botAmountTextField.setText("" + dataObject.getBotAmount());

        JPanel controllerpanel = new JPanel();
        controllerpanel.setLayout(new GridLayout(1, 0));

        botInfo.add(nameLabel);
        controllerpanel.add(botControllerSelector);
        controllerpanel.add(new JLabel("   Amount of this type:"));
        controllerpanel.add(botAmountTextField);
        botInfo.add(controllerpanel);
        botInfo.add(botNameLabel);
        botInfo.add(botNameField);
        botInfo.add(new JLabel("Bot reference name:"));
        botInfo.add(botReferenceField);
        botInfo.add(fileNameLabel);
        botInfo.add(fileNameField);
        botInfo.add(fileButton);
    }

    /**
     * create the checkables panel
     */
    public void createBotCheckablesPanel() {
        final String newLine = "\n";
        botCheckables.setLayout(new GridLayout(2, 1));
        JLabel checkablesLabel = new JLabel("Specs");
        JLabel handicapsLabel = new JLabel("Handicaps:");
        JLabel restrictionsLabel = new JLabel("Other options:");

        checkablesLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));

        JPanel checkablesPanel = new JPanel();

        checkablesPanel.setLayout(new BoxLayout(checkablesPanel, BoxLayout.PAGE_AXIS));
        checkablesPanel.add(checkablesLabel);
        checkablesPanel.add(handicapsLabel);
        if (dataObject.getGripperHandicap()) {
            gripperCheckbox.setSelected(true);
        }
        checkablesPanel.add(gripperCheckbox);
        if (dataObject.getColorBlindHandicap()) {
            colorblindCheckbox.setSelected(true);
        }
        checkablesPanel.add(colorblindCheckbox);
        checkablesPanel.add(restrictionsLabel);
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
    public void createBotSlidersPanel() {
        botSliders.setLayout(new GridLayout(6, 1));

        JLabel batteryUseLabel = new JLabel("Battery use:");
        JLabel perTickLabel = new JLabel("per tick");
        batteryUseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        JPanel batteryCapPanel = new JPanel();
        JPanel speedPanel = new JPanel();
        JPanel sizePanel = new JPanel();
        JPanel gripperPanel = new JPanel();
        JPanel batteryPanel = new JPanel();

        buttonPanel.add(applyButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        batteryCapPanel.add(batteryUseLabel);
        batteryCapPanel.add(batteryUseValueLabel);
        batteryCapPanel.add(perTickLabel);

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
        gripperPanel.setLayout(new BoxLayout(gripperPanel, BoxLayout.PAGE_AXIS));
        gripperPanel.add(numberOfGrippersLabel);
        numberOfGrippersSlider.setValue(dataObject.getGrippers());
        if (dataObject.getGripperHandicap()) {
            numberOfGrippersSlider.setEnabled(false);
        }
        gripperPanel.add(numberOfGrippersSlider);

        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.PAGE_AXIS));
        sizePanel.add(sizeLabel);
        sizeSlider.setValue(dataObject.getBotSize());
        if (dataObject.getSizeOverloadHandicap()) {
            sizeSlider.setEnabled(true);
        }
        sizePanel.add(sizeSlider);

        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.PAGE_AXIS));
        speedPanel.add(speedLabel);
        speedSlider.setValue(dataObject.getBotSpeed());
        if (dataObject.getMoveSpeedHandicap()) {
            speedSlider.setEnabled(true);
        }
        speedPanel.add(speedSlider);

        batteryPanel.setLayout(new BoxLayout(batteryPanel, BoxLayout.PAGE_AXIS));
        batteryPanel.add(batteryCapacity);
        batterySlider.setValue(dataObject.getBotBatteryCapacity());
        if (dataObject.isBatteryEnabled()) {
            speedSlider.setEnabled(true);
        }
        batteryPanel.add(batterySlider);

        botSliders.add(gripperPanel);
        botSliders.add(sizePanel);
        botSliders.add(speedPanel);
        botSliders.add(batteryPanel);
        botSliders.add(batteryCapPanel);
        botSliders.add(buttonPanel);
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
     * Returns the reset button.
     * @return the reset button.
     */
    public JButton getResetButton() {
        return resetButton;
    }
    /**
     * Returns the cancel button.
     * @return The cancel button.
     */
    public JButton getCancelButton() {
        return cancelButton;
    }
    /**
     * Get the currently used gripper checkbox.
     * @return The checkbox for setting the gripper handicap.
     */
    public JCheckBox getGripperCheckbox() {
        return gripperCheckbox;
    }
    /**
     * Get the currently used color blindness checkbox.
     * @return The checkbox for setting the color blind handicap.
     */
    public JCheckBox getColorblindCheckbox() {
        return colorblindCheckbox;
    }
    /**
     * Returns the currently used move speed checkbox.
     * @return The move speed checkbox.
     */
    public JCheckBox getmovespeedCheckbox() {
        return movespeedCheckbox;
    }
    /**
     * Returns the used custom size checkbox.
     * @return The custom size checkbox.
     */
    public JCheckBox getsizeoverloadCheckbox() {
        return customSizeCheckbox;
    }
    /**
     * Returns the used size slider.
     * @return The size slider.
     */
    public JSlider getSizeSlider() {
        return sizeSlider;
    }
    /**
     * Returns the current speed slider.
     * @return The used speed slider.
     */
    public JSlider getSpeedSlider() {
        return speedSlider;
    }
    /**
     * Returns the currently used battery slider.
     * @return The battery slider.
     */
    public JSlider getBatterySlider() {
        return batterySlider;
    }
    /**
     * Returns the used battery enabled checkbox.
     * @return The used checkbox.
     */
    public JCheckBox getBatteryEnabledCheckbox() {
        return batteryEnabledCheckbox;
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

    public JTextField getFileNameField() {
        return fileNameField;
    }

    public JTextField getBotNameField() {
        return botNameField;
    }

    public JCheckBox getCustomSizeCheckbox() {
        return customSizeCheckbox;
    }

    public JButton getFileButton() {
        return fileButton;
    }

    public JComboBox getBotControllerSelector() {
        return botControllerSelector;
    }

    public JTextField getBotAmountTextField() {
        return botAmountTextField;
    }

    public JTextField getBotReferenceField() {
        return botReferenceField;
    }
}
