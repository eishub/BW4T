package nl.tudelft.bw4t.environmentstore.editor.randomizer.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/** Create a little menu allowing the user to randomise blocks in the rooms. */
public class RandomizeBlockFrame extends RandomizeFrame {
    private static final long serialVersionUID = 1L;
    
    /** If the user clicks this button the changes will be saved. */
    JButton applyButton = new JButton("Apply");
    
    /** If the user clicks this button the changes will not be saved. */
    JButton cancelButton = new JButton("Cancel");

    /** The spinner settings for the amount of blocks. */
    SpinnerModel spinnerModel = new SpinnerNumberModel(8, // initial value
            1, // min
            12, // max
            1); // step
    
    /** Here can the user set the number of blocks. */
    private JSpinner numberOfBlocksSpinner = new JSpinner(spinnerModel);
    
    /** The label above the spinner for the maximum number of blocks per room. */
    private JLabel lblNumberOfBlocks = new JLabel("Maximum number of blocks per room:");
    
    /**
     * @param title
     *             Title of the frame. 
     * @param mpc
     *            The controller to be linked to this frame. 
     */
    public RandomizeBlockFrame(String title) {
        super(title);
        
        super.getContentPane().add(numberOfBlocksSpinner, "cell 0 3,growx,aligny top");    
        super.getContentPane().add(applyButton, "flowx,cell 0 15,alignx left,aligny top");
        super.getContentPane().add(cancelButton, "cell 0 15");
        super.getContentPane().add(lblNumberOfBlocks, "cell 0 2,growx,aligny top");
        pack();
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
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
}