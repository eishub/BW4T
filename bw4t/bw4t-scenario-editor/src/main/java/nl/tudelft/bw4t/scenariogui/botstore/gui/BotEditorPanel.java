package nl.tudelft.bw4t.scenariogui.botstore.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.controller.BatteryDischargeUpdater;
import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;
import nl.tudelft.bw4t.scenariogui.botstore.controller.CancelButton;
import nl.tudelft.bw4t.scenariogui.botstore.controller.GoalFileButton;
import nl.tudelft.bw4t.scenariogui.botstore.controller.ResetButton;
import nl.tudelft.bw4t.scenariogui.botstore.controller.SaveButton;
import nl.tudelft.bw4t.scenariogui.botstore.controller.SliderEnabler;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * BotEditorPanel which serves as the content pane for the BotEditor frame
 */
public class BotEditorPanel extends JPanel implements BotStoreViewInterface {

    private static final long serialVersionUID = 1850617931893202292L;
    
    public static final DecimalFormat dischargeRateFormatter = new DecimalFormat("0.0000");

    private JPanel botCheckables = new JPanel();

    private JPanel botSliders = new JPanel();

    private JPanel botInfo = new JPanel();

    private JButton saveButton = new JButton("Save");

    private JButton resetButton = new JButton("Reset");

    private JButton cancelButton = new JButton("Cancel");

    private JButton fileButton = new JButton("Use existing GOAL file");

    private JComboBox<String> botControllerSelector = new JComboBox<String>();

    private JTextField botNameField = new JTextField();

    private JTextField botAmountTextField = new JTextField();

    private JCheckBox gripperCheckbox = new JCheckBox("Gripper Disabled");

    private JCheckBox colorblindCheckbox = new JCheckBox("Color Blind Handicap");

    private JCheckBox customSizeCheckbox = new JCheckBox("Custom Bot Size");

    private JCheckBox movespeedCheckbox = new JCheckBox("Custom Bot Speed");

    private JCheckBox batteryEnabledCheckbox = new JCheckBox(
            "Battery Capacity enabled");

    private JTextField fileNameField = new JTextField(BotConfig.DEFAULT_GOAL_FILENAME);

    private JTextField botReferenceField = new JTextField(BotConfig.DEFAULT_GOAL_FILENAME_REFERENCE);

    private JSlider sizeSlider = new JSlider();

    private JSlider speedSlider = new JSlider();

    private JSlider batterySlider = new JSlider();

    private JSlider numberOfGrippersSlider = new JSlider();

    private JLabel batteryUseValueLabel = new JLabel("0");

    private MainPanel mainPanel;

    private BotEditor botEditor;

    /**
     * Create a new BotController.
     */
    private BotController controller;
    
    /**
     * Create a new BW4TClientConfig.
     */
    private BW4TClientConfig model;

    /**
     * Create the botEditorPanel.
     * 
     * @param controller is the controller that will control this view
     */
      public BotEditorPanel(BotController controller) {
        setLayout(new BorderLayout(20, 20));
        
        this.setController(controller);
        mainPanel = controller.getMainPanel();
        model = controller.getBW4TClientConfig();

        createBotInfoPanel();
        createBotCheckablesPanel();
        createBotSlidersPanel();

        add(botInfo, BorderLayout.NORTH);
        add(botSliders, BorderLayout.EAST);
        add(botCheckables, BorderLayout.WEST);
        
        getResetButton().addActionListener(new ResetButton(this));
        getSaveButton().addActionListener(new SaveButton(this));
        getCancelButton().addActionListener(new CancelButton(this));
        getFile().addActionListener(new GoalFileButton(this));

        final BatteryDischargeUpdater bdu = new BatteryDischargeUpdater(this);
        getSpeedSlider().addMouseListener(bdu);
        getSizeSlider().addMouseListener(bdu);

        final SliderEnabler su = new SliderEnabler(this);
        getGripperCheckbox().addActionListener(su);
        getCustomSizeCheckbox().addActionListener(su);
        getCustomSizeCheckbox().addActionListener(bdu);
        getMovespeedCheckbox().addActionListener(su);
        getMovespeedCheckbox().addActionListener(bdu);
        getBatteryEnabledCheckbox().addActionListener(su);
        getBatteryEnabledCheckbox().addActionListener(bdu);
        
        controller.addView(this);
        setVisible(true);
    }

    /**
     * Create the panel which contains the bots name and the controller type
     */
    private void createBotInfoPanel() {
        botInfo.setLayout(new GridLayout(10, 0));

        botControllerSelector.setModel(new DefaultComboBoxModel<String>(new String[] {
                EntityType.AGENT.toString(), EntityType.HUMAN.toString() }));

        botInfo.add(new JLabel("Bot name:"));
        botInfo.add(botNameField);
        
        JPanel controllerpanel = new JPanel();
        controllerpanel.setLayout(new GridLayout(1, 0));
        
        controllerpanel.add(botControllerSelector);
        controllerpanel.add(new JLabel("   Amount of this type:"));
        controllerpanel.add(botAmountTextField);
        botInfo.add(controllerpanel);
        
        createGOALOptions();
    }

    /**
     * Shows the GOAL options on the panel.
     */
    private void createGOALOptions() {
        botInfo.add(new JLabel(""));
        JLabel goalLabel = new JLabel("GOAL options");
        goalLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        botInfo.add(goalLabel);
        botInfo.add(new JLabel("Bot reference name:"));
        botInfo.add(botReferenceField);
        botInfo.add(new JLabel("GOAL file name:"));
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
        checkablesPanel.add(gripperCheckbox);
        checkablesPanel.add(colorblindCheckbox);
        checkablesPanel.add(new JLabel("Other options:"));
        checkablesPanel.add(customSizeCheckbox);
        checkablesPanel.add(movespeedCheckbox);
        checkablesPanel.add(batteryEnabledCheckbox);
        botCheckables.add(checkablesPanel);
    }
    
    /**
     * Checks the checkboxes in the bot spec section of the panel
     * if they're true in the model.
     */
    private void updateCheckBoxesModel() {
        gripperCheckbox.setSelected(controller.getGripperHandicap());
        colorblindCheckbox.setSelected(controller.getColorBlindHandicap());
        customSizeCheckbox.setSelected(controller.getSizeOverloadHandicap());
        movespeedCheckbox.setSelected(controller.getMoveSpeedHandicap());
        batteryEnabledCheckbox.setSelected(controller.isBatteryEnabled());
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

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        botSliders.add(buttonPanel);
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
    
        if (controller.getMoveSpeedHandicap()) {
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
    
        batteryPanel.add(batterySlider);
    
        botSliders.add(batteryPanel);
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
     * Returns the save button
     * 
     * @return the save button.
     */
    public JButton getSaveButton() {
        return saveButton;
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

    /**
     * Returns the BotEditor.
     * @return The BotEditor.
     */
    public BotEditor getBotEditor() {
        return botEditor;
    }
    
    /**
     * Get the currently used gripper checkbox.
     * 
     * @return true if the gripperCheckbox is selected
     */
    @Override
    public boolean getGripperHandicap() {
        return gripperCheckbox.isSelected();
    }

    /**
     * Get the currently used color blindness checkbox.
     * 
     * @return true if the getColorblindCheckbox is selected
     */
    @Override
    public boolean getColorBlindHandicap() {
        return colorblindCheckbox.isSelected();
    }

    /**
     * Returns the currently used move speed checkbox.
     * 
     * @return true if the getmovespeedCheckbox is selected
     */
    @Override
    public boolean getMoveSpeedHandicap() {
        return movespeedCheckbox.isSelected();
    }

    /**
     * Returns the used custom size checkbox.
     * 
     * @return true if the custom size checkbox is selected.
     */
    @Override
    public boolean getSizeOverloadHandicap() {
        return customSizeCheckbox.isSelected();
    }

    /**
     * Returns the value of the SizeSlider.
     * 
     * @return the size.
     */
    @Override
    public int getBotSize() {
        return sizeSlider.getValue();
    }

    /**
     * Returns the current speed slider.
     * 
     * @return speed.
     */
    @Override
    public int getBotSpeed() {
        return speedSlider.getValue();
    }

    /**
     * Returns the currently used battery slider.
     * 
     * @return the value of the battery slider.
     */
    @Override
    public int getBotBatteryCapacity() {
        return batterySlider.getValue();
    }

    /**
     * Returns the used battery enabled checkbox.
     * 
     * @return true if the checkbox is checked.
     */
    @Override
    public boolean isBatteryEnabled() {
        return batteryEnabledCheckbox.isSelected();
    }
    
    /**
     * Return the label describing what the robot uses regarding battery
     * potential per tick.
     * 
     * @return The text on the aforementioned label.
     */
    @Override
    public double getBotBatteryDischargeRate() {
        return Double.parseDouble(batteryUseValueLabel.getText());
    }

    /**
     * Returns the slider determining the amount of grippers the bot can use.
     * 
     * @return The value of the aforementioned slider.
     */
    @Override
    public int getGrippers() {
        return numberOfGrippersSlider.getValue();
    }

    /**
     * Returns the String that contains the goal file name.
     * 
     * @return the string that contains the goal file name.
     */
    @Override
    public String getFileName() {
        return fileNameField.getText();
    }

    /**
     * Returns the string that contains the bot name.
     * 
     * @return the string that contains the bot name.
     */
    @Override
    public String getBotName() {
        return botNameField.getText();
    }

    /**
     * Returns the JButton for adding a goal file.
     * 
     * @return The JButton for adding a goal file.
     */
    public JButton getFile() {
        return fileButton;
    }

    /**
     * Returns the string that contains the amount of a bot.
     * 
     * @return The string that contains the amount of a bot.
     */
    public int getBotAmount() {
        return Integer.parseInt(botAmountTextField.getText());
    }

    /**
     * Returns the string that contains the goal reference name.
     * 
     * @return The string that contains the goal reference name.
     */
    public String getReferenceName() {
        return botReferenceField.getText();
    }
    
    public void setBotEditor(BotEditor editor) {
        this.botEditor = editor;
    }
    
    /**
     * Return the MainPanel.
     * @return mainpanel
     */
    public MainPanel getMainPanel() {
        return mainPanel;
    }
    
    /**
     * Update the view with the values from the controller.
     */
    public void updateView() {
        botControllerSelector.setSelectedItem(controller.getBotController().toString());
        botNameField.setText(controller.getBotName());
        botAmountTextField.setText(Integer.toString(controller.getBotAmount()));
        updateCheckBoxesModel();
        updateSliders();
        
        fileNameField.setText(controller.getFileName());
        botReferenceField.setText(controller.getReferenceName());
        numberOfGrippersSlider.setValue(controller.getGrippers());
        speedSlider.setValue(controller.getBotSpeed());
        sizeSlider.setValue(controller.getBotSize());
        batterySlider.setValue(controller.getBotBatteryCapacity());
        
        updateDischargeRate();
        
    }
    
    /**
     * update the sliders so they are enabled properly.
     */
    public void updateSliders() {
        sizeSlider.setEnabled(getSizeOverloadHandicap());
        numberOfGrippersSlider.setEnabled(!getGripperHandicap());
        batterySlider.setEnabled(isBatteryEnabled());
        speedSlider.setEnabled(getMoveSpeedHandicap());
    }
    
    public void updateDischargeRate() {
        double dr = 0.0;
        if (isBatteryEnabled()) {
            dr = BotConfig.calculateDischargeRate(getBotSize(), getBotSpeed());
        }
        batteryUseValueLabel.setText(dischargeRateFormatter.format(dr));
    }

    /**
     * @return the current BotController
     */
    public BotController getBotController() {
        return controller;
    }
    
    /**
     * Set the current BotStoreController
     * @param bsc 
     */
    public void setController(BotController bsc) {
        this.controller = bsc;
    }
    
    /**
     * Remove the current view.
     */
    @Override
    public void dispose() {
        controller.removeView(this);
    }
    
    /**
     * This function enables/disables the battery slider
     * @param enabled Boolean: true if BatterySlider should be enabled
     * and false if BatterySlider should be disabled.
     */
    public void setBatterySliderEnabled(boolean enabled) {
        batterySlider.setEnabled(enabled);
    }
    
    /**
     * This function enables/disables the gripper slider
     * @param enabled Boolean: true if numberOfGrippersSlider should be enabled
     * and false if numberOfGrippersSlider should be disabled.
     */
    public void setGripperSliderEnabled(boolean enabled) {
        numberOfGrippersSlider.setEnabled(enabled);
    }
    
    /**
     * This function enables/disables the speed slider
     * @param enabled Boolean: true if speedSlider should be enabled
     * and false if speedSlider should be disabled.
     */
    public void setSpeedSliderEnabled(boolean enabled) {
        speedSlider.setEnabled(enabled);
    }
    
    /**
     * This function enables/disables the size slider
     * @param enabled Boolean: true if sizeSlider should be enabled
     * and false if sizeSlider should be disabled.
     */
    public void setSizeSliderEnabled(boolean enabled) {
        sizeSlider.setEnabled(enabled);
    }

    public JCheckBox getMovespeedCheckbox() {
        return movespeedCheckbox;
    }
}
