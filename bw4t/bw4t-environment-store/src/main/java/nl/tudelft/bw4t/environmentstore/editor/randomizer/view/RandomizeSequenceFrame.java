package nl.tudelft.bw4t.environmentstore.editor.randomizer.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.controller.RandomizeSequenceController;

/** Create a little menu allowing the user to randomise the sequence to be picked. */
public class RandomizeSequenceFrame extends RandomizeFrame {

    /** Random generated serial version UID. */
    private static final long serialVersionUID = 1993091627565106917L;
    
    /** The controller for this view class. */
    private RandomizeSequenceController randomController;
    
    /** The label above the spinner for the amount of blocks. */
    private JLabel lblNumberOfBlocks = new JLabel("Number of blocks created:");
    
    /** The spinner settings for the amount of blocks. */
    SpinnerModel spinnerModel = new SpinnerNumberModel(8, // initial value
            1, // min
            12, // max
            1); // step
    
    /** Here can the user set the number of blocks. */
    private JSpinner numberOfBlocksSpinner = new JSpinner(spinnerModel);
    
    /** By pressing this button, a randomized color sequence will be made. */
    private JButton randomizeButton = new JButton("Randomize");
    
    /** Label with the text 'Result:'. */
    private JLabel lblResult = new JLabel("Result:");
    
    /** This is the textField where the randomized sequence will appear. */
    private JTextField randomizedSequence = new JTextField();
    
    /**
     * If the user clicks this button the changes will be saved. */
    JButton applyButton = new JButton("Apply");
    
    /** If the user clicks this button the changes will not be saved. */
    JButton cancelButton = new JButton("Cancel");

    /**
     * Creates the frame. 
     * @param title
     * @param mpc
     *           Controller of the map panel linked to this frame. 
     */
    public RandomizeSequenceFrame(String title, MapPanelController mpc) {
        super(title, mpc);
        this.randomController = new RandomizeSequenceController(this, mpc);
    
        super.getContentPane().add(lblNumberOfBlocks, "cell 0 2,growx,aligny top");
        super.getContentPane().add(numberOfBlocksSpinner, "cell 0 3,growx,aligny top");
        super.getContentPane().add(randomizeButton, "cell 0 12,growx,aligny top");
        super.getContentPane().add(lblResult, "cell 0 13,growx,aligny top");
        super.getContentPane().add(randomizedSequence, "cell 0 14,growx,aligny top");
        
        randomizedSequence.setEditable(false);        
        randomizedSequence.setColumns(10);
        
        super.getContentPane().add(applyButton, "flowx,cell 0 15,alignx left,aligny top");
        super.getContentPane().add(cancelButton, "cell 0 15");
        pack();
    }
    
    /**
     * get number of blocks as set by user
     * 
     * @return number of blocks
     */
    public Integer getNumberOfBlocks() {
        return (Integer) (numberOfBlocksSpinner.getValue());
    }
    
    public void setSpinnerModel(int n) {
        spinnerModel.setValue(n);
    }
    
    public JButton getRandomizeButton() {
        return randomizeButton;
    }
    
    public JTextField getRandomizedSequence() {
        return randomizedSequence;
    }
    
    public JButton getApplyButton() {
        return applyButton;
    }
    
    public JButton getCancelButton() {
        return cancelButton;
    }
    
    public RandomizeSequenceController getRandomController() {
        return this.randomController;
    }
}
