package nl.tudelft.bw4t.environmentstore.editor.randomizer.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.controller.RandomizeSequenceController;

/**
 * Create a randomize frame to randomize blocks in the sequence.
 *
 */
public class RandomizeSequenceFrame extends JFrame {

	private static final long serialVersionUID = 1993091627565106917L;
	
	private RandomizeSequenceController randomController;
	
	private MapPanelController mapController;
	
	private JPanel contentPane = new JPanel();
	
	private JLabel lblTitle;
	
	private JLabel lblNumberOfBlocks = new JLabel("Number of blocks created:");
	
    SpinnerModel spinnerModel = new SpinnerNumberModel(8, // initial value
            1, // min
            12, // max
            1); // step
	/**
	 * Here can the user set the number of blocks.
	 */
	private JSpinner numberOfBlocksSpinner = new JSpinner(spinnerModel);
	
	private JLabel lblColorsUsed = new JLabel("Colors Used:");
	
	/**
	 * All the color-checkboxes. Here can the user choose what colors will be used.
	 */
	private JCheckBox chckbxRRed = new JCheckBox("R Red");
	private JCheckBox chckbxGGreen = new JCheckBox("G Green");
	private JCheckBox chckbxYYellow = new JCheckBox("Y Yellow");
	private JCheckBox chckbxBBlue = new JCheckBox("B Blue");
	private JCheckBox chckbxOOrange = new JCheckBox("O Orange");
	private JCheckBox chckbxWWhite = new JCheckBox("W White");
	private JCheckBox chckbxPPink = new JCheckBox("P Pink");
	
	/**
	 * By pressing this button, a randomized color sequence will be made.
	 */
	private JButton randomizeButton = new JButton("Randomize");
	
	/**
	 * Label with the text 'Result:'.
	 */
	private JLabel lblResult = new JLabel("Result:");
	
	/**
	 * This is the textField where the randomized sequence will appear.
	 */
	private JTextField randomizedSequence = new JTextField();
	
	/**
	 * If the user clicks this button the changes will be saved.
	 */
	JButton applyButton = new JButton("Apply");
	
	/**
	 * If the user clicks this button the changes will not be saved.
	 */
	JButton cancelButton = new JButton("Cancel");

	/**
	 * Create the frame.
	 */
	public RandomizeSequenceFrame(String title, MapPanelController mpc) {
		mapController = mpc;
		this.randomController = new RandomizeSequenceController(this, mapController);
		
		lblTitle = new JLabel(title);
		setTitle("Randomize " + title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 299, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[272px]", "[20px][14px][][20px][14px][23px][23px][23px][23px][23px][23px][23px][23px][14px][20px][23px]"));
		setResizable(false);
		
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		contentPane.add(lblTitle, "cell 0 0,grow");
		contentPane.add(lblNumberOfBlocks, "cell 0 2,growx,aligny top");
		contentPane.add(numberOfBlocksSpinner, "cell 0 3,growx,aligny top");
		contentPane.add(lblColorsUsed, "cell 0 4,growx,aligny top");
		contentPane.add(chckbxRRed, "cell 0 5,growx,aligny top");
		contentPane.add(chckbxGGreen, "cell 0 6,growx,aligny top");
		contentPane.add(chckbxYYellow, "cell 0 7,growx,aligny top");
		contentPane.add(chckbxBBlue, "cell 0 8,growx,aligny top");
		contentPane.add(chckbxOOrange, "cell 0 9,growx,aligny top");
		contentPane.add(chckbxWWhite, "cell 0 10,growx,aligny top");
		contentPane.add(chckbxPPink, "cell 0 11,growx,aligny top");
		contentPane.add(randomizeButton, "cell 0 12,growx,aligny top");
		contentPane.add(lblResult, "cell 0 13,growx,aligny top");
		contentPane.add(randomizedSequence, "cell 0 14,growx,aligny top");
		
		chckbxRRed.setSelected(true);
		chckbxGGreen.setSelected(true);
		chckbxYYellow.setSelected(true);
		chckbxBBlue.setSelected(true);
		chckbxOOrange.setSelected(true);
		chckbxWWhite.setSelected(true);
		chckbxPPink.setSelected(true);
		
		randomizedSequence.setEditable(false);		
		randomizedSequence.setColumns(10);
		
		contentPane.add(applyButton, "flowx,cell 0 15,alignx left,aligny top");
		contentPane.add(cancelButton, "cell 0 15");
		pack();
	}
	
    public JLabel getLabelTitle() {
    	return lblTitle;
    }
	
	public boolean isRed() {
        return chckbxRRed.isSelected();
    }
	
	public boolean isGreen() {
        return chckbxGGreen.isSelected();
    }
	
	public boolean isYellow() {
        return chckbxYYellow.isSelected();
    }
	
	public boolean isBlue() {
        return chckbxBBlue.isSelected();
    }
	
	public boolean isOrange() {
        return chckbxOOrange.isSelected();
    }
	
	public boolean isWhite() {
        return chckbxWWhite.isSelected();
    }
	
	public boolean isPink() {
        return chckbxPPink.isSelected();
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
