package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * The SizeDialog shows the user a dialog where he can enter the size of the map.
 */
public class SizeDialog extends JFrame {
	
	/**
	 * The contentPane.
	 */
	private JPanel contentPane;
	
	/**
	 * The left panel of the frame (which will hold the editExistingMapButton)
	 */
	private JPanel leftPanel;
	
	/**
	 * The right panel of the frame (which will hold the rest)
	 */
	private JPanel rightPanel;
	
	/**
	 * With this button the user can choose to edit an already existing map.
	 */
	private JButton editExistingMapButton = new JButton("Edit existing map");
	
	/**
	 * With this button the user can choose to start building a map from scratch.
	 */
	private JButton beginFromScratchButton = new JButton("Begin from scratch");
	
	/**
	 * With this button the user can choose to use a standard basis.
	 */
	private JButton useStandardBasisButton = new JButton("Use standard basis");
	
	/**
	 * Checkbox which the user can check if he wants the zone labels to be showed.
	 */
	private JCheckBox showZoneLabelsCheckbox = new JCheckBox("Show Zone Labels");
	
	/**
	 * Label with the text #rows
	 */
	private JLabel rowsLabel = new JLabel("#rows");
	
	/**
	 * Label with the text #columns
	 */
	private JLabel columnsLabel = new JLabel("#columns");
	
	/**
	 * Here can the user set the wanted numbers of rows.
	 */
	private JSpinner rows;
	
	/**
	 * Here can the user set the wanted number of columns.
	 */
	private JSpinner cols;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SizeDialog frame = new SizeDialog();
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
	public SizeDialog() {
		setTitle("Size Dialog");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 192);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][][][][][][][][grow]", "[][][][grow]"));
		
		getContentPane().add(leftPanel, "cell 0 0 1 4,alignx left,growy");
		leftPanel.setLayout(new MigLayout("", "[113px]", "[23px]"));
		
		leftPanel.add(editExistingMapButton, "cell 0 0,alignx left,aligny top");
		
		getContentPane().add(rightPanel, "cell 1 1 9 3,grow");
		rightPanel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][]"));
		
		rightPanel.add(rowsLabel, "cell 0 0,alignx trailing");
		
		SpinnerModel rowmodel = new SpinnerNumberModel(5, // initial value
	            5, // min
	            24, // max
	            1); // step
	    rows = new JSpinner(rowmodel);
	    rightPanel.add(rows, "cell 1 0");

	    SpinnerModel colmodel = new SpinnerNumberModel(5, // initial value
	            3, // min
	            24, // max
	            1); // step
	    cols = new JSpinner(colmodel);
	    rightPanel.add(cols, "cell 1 1");
	
		rightPanel.add(columnsLabel, "cell 0 1,alignx trailing");
		rightPanel.add(showZoneLabelsCheckbox, "cell 1 2");
		rightPanel.add(beginFromScratchButton, "cell 0 4");
		rightPanel.add(useStandardBasisButton, "cell 1 4");
			
	}
	
	public boolean isLabelsVisible() {
        return showZoneLabelsCheckbox.isSelected();
    }

    /**
     * get {@link #rows} as set by user
     * 
     * @return {@link #rows}
     */
    public Integer getRows() {
        return (Integer) (rows.getValue());
    }

    /**
     * get {@link #cols} as set by user
     * 
     * @return {@link #cols}
     */
    public Integer getColumns() {
        return (Integer) (cols.getValue());
    }
}
