package nl.tudelft.bw4t.scenariogui.botstore.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;

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

    private BotController controller;
    
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
    
    private void updateCheckBoxesModel() {
        gripperCheckbox.setSelected(controller.getGripperHandicap());
        colorblindCheckbox.setSelected(controller.getColorBlindHandicap());
        customSizeCheckbox.setSelected(controller.getSizeOverloadHandicap());
        movespeedCheckbox.setSelected(controller.getMoveSpeedHandicap());
        batteryEnabledCheckbox.setSelected(controller.isBatteryEnabled());
    }

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

    private void addBatteryCapPanel() {
        JPanel batteryCapPanel = new JPanel();

        JLabel batteryUseLabel = new JLabel("Battery use:");
        batteryUseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        batteryCapPanel.add(batteryUseLabel);
        batteryCapPanel.add(batteryUseValueLabel);
        batteryCapPanel.add(new JLabel("per tick"));

        botSliders.add(batteryCapPanel);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        botSliders.add(buttonPanel);
    }

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

    private void createSliders() {
        createGripperSlider();
        createSizeSlider();
        createSpeedSlider();
        createBatterySlider();
    }

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

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JCheckBox getGripperCheckbox() {
        return gripperCheckbox;
    }

    public JCheckBox getColorblindCheckbox() {
        return colorblindCheckbox;
    }

    public JCheckBox getsizeoverloadCheckbox() {
        return customSizeCheckbox;
    }

    public JSlider getSizeSlider() {
        return sizeSlider;
    }

    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    public JSlider getBatterySlider() {
        return batterySlider;
    }

    public JCheckBox getBatteryEnabledCheckbox() {
        return batteryEnabledCheckbox;
    }

    public JLabel getBatteryUseValueLabel() {
        return batteryUseValueLabel;
    }

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

    public JComboBox getBotControllerSelector() {
        return botControllerSelector;
    }

    public JTextField getBotAmountTextField() {
        return botAmountTextField;
    }

    public JTextField getBotReferenceField() {
        return botReferenceField;
    }

    public BotEditor getBotEditor() {
        return botEditor;
    }
    
    @Override
    public boolean getGripperHandicap() {
        return gripperCheckbox.isSelected();
    }

    @Override
    public boolean getColorBlindHandicap() {
        return colorblindCheckbox.isSelected();
    }

    @Override
    public boolean getMoveSpeedHandicap() {
        return movespeedCheckbox.isSelected();
    }

    @Override
    public boolean getSizeOverloadHandicap() {
        return customSizeCheckbox.isSelected();
    }

    @Override
    public int getBotSize() {
        return sizeSlider.getValue();
    }

    @Override
    public int getBotSpeed() {
        return speedSlider.getValue();
    }

    @Override
    public int getBotBatteryCapacity() {
        return batterySlider.getValue();
    }

    @Override
    public boolean isBatteryEnabled() {
        return batteryEnabledCheckbox.isSelected();
    }
    
    @Override
    public double getBotBatteryDischargeRate() {
        return Double.parseDouble(batteryUseValueLabel.getText());
    }

    @Override
    public int getGrippers() {
        return numberOfGrippersSlider.getValue();
    }

    @Override
    public String getFileName() {
        return fileNameField.getText();
    }

    @Override
    public String getBotName() {
        return botNameField.getText();
    }

    public JButton getFile() {
        return fileButton;
    }

    public int getBotAmount() {
        return Integer.parseInt(botAmountTextField.getText());
    }

    public String getReferenceName() {
        return botReferenceField.getText();
    }
    
    public void setBotEditor(BotEditor editor) {
        this.botEditor = editor;
    }
    
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
     * Update the sliders so they are enabled properly.
     */
    public void updateSliders() {
        sizeSlider.setEnabled(getSizeOverloadHandicap());
        numberOfGrippersSlider.setEnabled(!getGripperHandicap());
        batterySlider.setEnabled(isBatteryEnabled());
        speedSlider.setEnabled(getMoveSpeedHandicap());
    }
    
    /**
     * Update the discharge rate.
     */
    public void updateDischargeRate() {
        double dr = 0.0;
        if (isBatteryEnabled()) {
            dr = BotConfig.calculateDischargeRate(getBotSize(), getBotSpeed());
        }
        batteryUseValueLabel.setText(dischargeRateFormatter.format(dr));
    }

    public BotController getBotController() {
        return controller;
    }
    
    public void setController(BotController bsc) {
        this.controller = bsc;
    }
    
    @Override
    public void dispose() {
        controller.removeView(this);
    }
    
    public void setBatterySliderEnabled(boolean enabled) {
        batterySlider.setEnabled(enabled);
    }
    
    public void setGripperSliderEnabled(boolean enabled) {
        numberOfGrippersSlider.setEnabled(enabled);
    }
    
    public void setSpeedSliderEnabled(boolean enabled) {
        speedSlider.setEnabled(enabled);
    }
    
    public void setSizeSliderEnabled(boolean enabled) {
        sizeSlider.setEnabled(enabled);
    }

    public JCheckBox getMovespeedCheckbox() {
        return movespeedCheckbox;
    }
}
