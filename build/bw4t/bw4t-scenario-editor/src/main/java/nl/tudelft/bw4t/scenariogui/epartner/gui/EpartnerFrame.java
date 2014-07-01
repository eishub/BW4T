package nl.tudelft.bw4t.scenariogui.epartner.gui;

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
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;

/**
 * This class creates the frame for the e-Partner GUI.
 */

public class EpartnerFrame extends JFrame implements EPartnerViewInterface {
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
    
    private JCheckBox forgetMeNotCheckbox = new JCheckBox("Forget-me-not");

    private JButton fileButton = new JButton("Use existing GOAL file");

    private JCheckBox gpsCheckBox = new JCheckBox("GPS");

    private EPartnerConfig dataObject = new EPartnerConfig();

    private EpartnerController controller;

    /**
     * Create the EpartnerFrame.
     * @param controller The EpartnerController.
     */
    public EpartnerFrame(EpartnerController controller) {
        setTitle("E-Partner");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane.setLayout(new BorderLayout(5, 5));
        setContentPane(contentPane);
        this.setController(controller);

        createInfoPanel();
        createOptionPanel();
        createButtonPanel();

        contentPane.add(infoPane, BorderLayout.NORTH);
        contentPane.add(optionPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        getResetButton().addActionListener(new EpartnerResetButton(this));
        getCancelButton().addActionListener(new EpartnerCancelButton(this));
        getSaveButton().addActionListener(new EpartnerSaveButton(this));
        getFileButton().addActionListener(new EGoalFileButton(this));

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        
        controller.addView(this);
        
        setVisible(true);
    }

    private void createInfoPanel() {
        infoPane.setLayout(new GridLayout(1, 0));
        infoPane.add(epartnerNameField);
        infoPane.add(new JLabel("  Amount of this type:"));
        infoPane.add(epartnerAmountField);
    }

    private void createOptionPanel() {
        optionPane.setLayout(new GridLayout(0, 1));
        
        optionPane.add(new JLabel(""));

        addGoalOptions();

        optionPane.add(new JLabel(""));

        JLabel propertiesLabel = new JLabel("Properties");
        propertiesLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        optionPane.add(propertiesLabel);
        optionPane.add(forgetMeNotCheckbox);
        optionPane.add(gpsCheckBox);
        
        optionPane.add(new JLabel(""));
    }

    private void addGoalOptions() {
        JLabel goalLabel = new JLabel("GOAL options");
        goalLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        optionPane.add(goalLabel);
        optionPane.add(new JLabel("E-partner reference name:"));
        optionPane.add(epartnerReferenceField);
        optionPane.add(new JLabel("GOAL file name:"));
        optionPane.add(epartnerGoalFileField);
        optionPane.add(fileButton);
    }

    private void createButtonPanel() {
        buttonPane.setLayout(new GridLayout(1, 0));
        buttonPane.add(saveButton);
        buttonPane.add(resetButton);
        buttonPane.add(cancelButton);
    }

    public String getEpartnerName() {
        return this.epartnerNameField.getText();
    }

    public int getEpartnerAmount() {
        return Integer.parseInt(this.epartnerAmountField.getText());
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

    public JCheckBox getForgetMeNotCheckbox() {
        return forgetMeNotCheckbox;
    }

    public JCheckBox getGPSCheckbox() {
        return gpsCheckBox;
    }

    /**
     * Updates the EpartnerFrame.
     */
    public void updateView() {
        epartnerNameField.setText(getEpartnerController().getEpartnerName());
        epartnerAmountField.setText("" + getEpartnerController().getEpartnerAmount());
        forgetMeNotCheckbox.setSelected(controller.isForgetMeNot());
        gpsCheckBox.setSelected(getEpartnerController().isGps());
        epartnerReferenceField.setText(getEpartnerController().getReferenceName());
        epartnerGoalFileField.setText(getEpartnerController().getFileName());
    }


    protected EpartnerController getEpartnerController() {
        return controller;
    }

    public void setController(EpartnerController controller) {
        this.controller = controller;
    }
    
    @Override
    public void dispose() {
        controller.removeView(this);
        super.dispose();
    }

    @Override
    public boolean getForgetMeNot() {
        return forgetMeNotCheckbox.isSelected();
    }

    @Override
    public boolean getGPS() {
        return gpsCheckBox.isSelected();
    }

    @Override
    public String getEpartnerReference() {
        return epartnerReferenceField.getText();
    }

    @Override
    public String getEpartnerGoalFile() {
        return epartnerGoalFileField.getText();
    }

    public JTextField getEpartnerReferenceField() {
        return epartnerReferenceField;
    }

    public JTextField getEpartnerGoalFileField() {
        return epartnerGoalFileField;
    }

    public JButton getFileButton() {
        return fileButton;
    }

    public JTextField getEpartnerNameField() {
        return epartnerNameField;
    }

    public JTextField getEpartnerAmountField() {
        return epartnerAmountField;
    }
    
}
