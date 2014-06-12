package nl.tudelft.bw4t.map.editor.gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class RandomizeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private JLabel lblRoomA = new JLabel("Room A1 / Sequence");
	private JLabel lblNumberOfBlocks = new JLabel("Number of blocks created:");
	
	/**
	 * Here can the user set the number of blocks.
	 */
	private JSpinner numberOfBlocksSpinner = new JSpinner();
	
	private JLabel lblColorsUsed = new JLabel("Colors Used:");
	
	/**
	 * All the color-checkboxes. Here can the user choose what colors will be used.
	 */
	private JCheckBox chckbxNewCheckBox = new JCheckBox("R Red");
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RandomizeFrame frame = new RandomizeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RandomizeFrame() {
		setTitle("Randomize Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[272px]", "[20px][14px][][20px][14px][23px][23px][23px][23px][23px][23px][23px][23px][14px][20px][23px]"));
		
		lblRoomA.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		contentPane.add(lblRoomA, "cell 0 0,grow");
		contentPane.add(lblNumberOfBlocks, "cell 0 2,growx,aligny top");
		contentPane.add(numberOfBlocksSpinner, "cell 0 3,growx,aligny top");
		contentPane.add(lblColorsUsed, "cell 0 4,growx,aligny top");
		contentPane.add(chckbxNewCheckBox, "cell 0 5,growx,aligny top");
		contentPane.add(chckbxGGreen, "cell 0 6,growx,aligny top");
		contentPane.add(chckbxYYellow, "cell 0 7,growx,aligny top");
		contentPane.add(chckbxBBlue, "cell 0 8,growx,aligny top");
		contentPane.add(chckbxOOrange, "cell 0 9,growx,aligny top");
		contentPane.add(chckbxWWhite, "cell 0 10,growx,aligny top");
		contentPane.add(chckbxPPink, "cell 0 11,growx,aligny top");
		contentPane.add(randomizeButton, "cell 0 12,growx,aligny top");
		contentPane.add(lblResult, "cell 0 13,growx,aligny top");
		contentPane.add(textField, "cell 0 14,growx,aligny top");
		
		textField.setColumns(10);
		
		contentPane.add(applyButton, "flowx,cell 0 15,alignx left,aligny top");
		contentPane.add(cancelButton, "cell 0 15");
	}
	
	public boolean isRed() {
        return chckbxNewCheckBox.isSelected();
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

}
