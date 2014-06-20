package nl.tudelft.bw4t.environmentstore.editor.randomizer.view;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;

/**
 * Create a randomize frame to randomize blocks in the sequence.
 *
 */
public class RandomizeFrame extends JFrame {

    private static final long serialVersionUID = 1993091627565106917L;
    
    private MapPanelController mapController;
    
    private JPanel contentPane = new JPanel();
    
    private JLabel lblTitle;
    
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
     * Create the frame.
     */
    public RandomizeFrame(String title, MapPanelController mpc) {
        mapController = mpc;
        
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
        contentPane.add(lblColorsUsed, "cell 0 4,growx,aligny top");
        contentPane.add(chckbxRRed, "cell 0 5,growx,aligny top");
        contentPane.add(chckbxGGreen, "cell 0 6,growx,aligny top");
        contentPane.add(chckbxYYellow, "cell 0 7,growx,aligny top");
        contentPane.add(chckbxBBlue, "cell 0 8,growx,aligny top");
        contentPane.add(chckbxOOrange, "cell 0 9,growx,aligny top");
        contentPane.add(chckbxWWhite, "cell 0 10,growx,aligny top");
        contentPane.add(chckbxPPink, "cell 0 11,growx,aligny top");
        
        chckbxRRed.setSelected(true);
        chckbxGGreen.setSelected(true);
        chckbxYYellow.setSelected(true);
        chckbxBBlue.setSelected(true);
        chckbxOOrange.setSelected(true);
        chckbxWWhite.setSelected(true);
        chckbxPPink.setSelected(true);
        
        pack();
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
}
