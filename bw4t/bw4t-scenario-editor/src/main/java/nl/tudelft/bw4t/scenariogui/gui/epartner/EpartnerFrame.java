package nl.tudelft.bw4t.scenariogui.gui.epartner;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.controller.EpartnerController;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
* This class creates the frame for the e-Partner GUI.
* @author Wendy
* @author Katia
*/

public class EpartnerFrame extends JFrame {
   /** The required serial version UID. */
   private static final long serialVersionUID = 1L;
   /** The JPanel containing all the content. */
   private JPanel contentPane = new JPanel();
   
   /** The panel containing the epartner info. */
   private JPanel infoPane = new JPanel();
   
   /** The panel containing the buttons. */
   private JPanel buttonPane = new JPanel();
   
   /** The panel that contains the epartner options. */
   private JPanel optionPane = new JPanel();
   
   /** The textfield containing the epartner name. */
   private JTextField epartnerNameField = new JTextField();
   
   /** The textfield containing the epartner amount. */
   private JTextField epartnerAmountField = new JTextField();
   
   /** The apply button. */
   private JButton applyButton = new JButton("Apply");
   /** The reset button. */
   private JButton resetButton = new JButton("Reset");
   /** The cancel button. */
   private JButton cancelButton = new JButton("Cancel");
   /** The checkbox for the left-alone warnings. */
   private JCheckBox leftAloneCheckbox = new JCheckBox("Left-alone Warning");
   /** The checkbox to enable GPS. */
   private JCheckBox gpsCheckBox = new JCheckBox("Geolocator");
   /** The data object. */
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
       
       createInfoPanel();
       createOptionPanel();
       createButtonPanel();
       
       contentPane.add(infoPane, BorderLayout.NORTH);
       contentPane.add(optionPane, BorderLayout.CENTER);
       contentPane.add(buttonPane, BorderLayout.SOUTH);
       
       pack();
       setVisible(true);
       
       controller = new EpartnerController(this);
   }
   
   /** Create the panel which contains the epartner info. */
   private void createInfoPanel() {
   	infoPane.setLayout(new GridLayout(1, 0));
   	epartnerNameField.setText(dataObject.getName());
   	infoPane.add(epartnerNameField);
   	infoPane.add(new JLabel("  Amount of this type:"));
   	epartnerAmountField.setText("" + dataObject.getAmount());
   	infoPane.add(epartnerAmountField);
   }
   
   /**
    * Create the panel which contains the epartner options.
    */
   private void createOptionPanel() {
   	optionPane.setLayout(new GridLayout(0, 1));
   	
   	optionPane.add(new JLabel(""));
   	optionPane.add(leftAloneCheckbox);
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
    * @return The JTextField containing the epartner name.
    */
   public JTextField getEpartnerName() {
   	return this.epartnerNameField;
   }
   
   /**
    * Returns the JTextField containing the epartner amount.
    * @return The JTextField containing the epartner amount.
    */
   public JTextField getEpartnerAmount() {
   	return this.epartnerAmountField;
   }
   
   /**
    * Returns the used apply button.
    * @return The apply button.
    */
   public JButton getApplyButton() {
       return applyButton;
   }
   /**
    * Returns the reset button used.
    * @return The reset button.
    */
   public JButton getResetButton() {
       return resetButton;
   }
   /**
    * Returns the currently used cancel button.
    * @return The cancel button.
    */
   public JButton getCancelButton() {
       return cancelButton;
   }
   /**
    * Returns the checkbox enabling or disabling
    * warnings when the bot is left alone.
    * @return The checkbox.
    */
   public JCheckBox getLeftAloneCheckbox() {
       return leftAloneCheckbox;
   }
   /**
    * Returns the checkbox enabling or
    * disabling GPS functionality.
    * @return The checkbox.
    */
   public JCheckBox getGPSCheckbox() {
       return gpsCheckBox;
   }
   
   public MainPanel getPanel() {
	   return panel;
   }
   
   public int getRow() {
	   return row;
   }
   
   /**
    * Returns the data object with its values for usage.
    * @return The created data object.
    */
   public EPartnerConfig getDataObject() {
       return dataObject;
   }
   
}
