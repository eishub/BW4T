package nl.tudelft.bw4t.environmentstore.sizedialog.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import nl.tudelft.bw4t.environmentstore.sizedialog.controller.SizeDialogController;

import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JPanel contentPane = new JPanel();
	
	/**
	 * The left panel of the frame (which will hold the editExistingMapButton)
	 */
	private JPanel leftPanel = new JPanel();
	
	/**
	 * The right panel of the frame (which will hold the rest)
	 */
	private JPanel rightPanel = new JPanel();
	
	/**
	 * With this button the user can choose to start building a map from scratch.
	 */
	private JButton beginFromScratchButton = new JButton("Begin from scratch");
	
	/**
	 * With this button the user can choose to use a standard basis.
	 */
	private JButton useStandardBasisButton = new JButton("Use standard basis");
	
	/**
	 * Label with the text #rows.
	 */
	private JLabel rowsLabel = new JLabel("#rows");
	
	/**
	 * Label with the text #columns.
	 */
	private JLabel columnsLabel = new JLabel("#columns");
	
	/**
	 * Label with the text {@link #entities}.
	 */
	private JLabel entityLabel = new JLabel("#entities");
	
	/**
	 * Here can the user set the wanted numbers of rows.
	 */
	private JSpinner rows = new JSpinner();
	
	/**
	 * Here can the user set the wanted number of columns.
	 */
	private JSpinner cols = new JSpinner();
	
	/**
	 * Here can the user set the wanted number of entities.
	 */
	private JSpinner entities = new JSpinner();
	
	/**
	 * SizeDialogController
	 */
	private SizeDialogController sdc;

	/**
	 * Create the frame.
	 */
	public SizeDialog() {
		sdc = new SizeDialogController(this);
		
		setTitle("Size Dialog");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 230);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][][][][][][][][grow]", "[][][][grow]"));
		
		getContentPane().add(leftPanel, "cell 0 0 1 4,alignx left,growy");
		leftPanel.setLayout(new MigLayout("", "[113px]", "[23px]"));
		
		getContentPane().add(rightPanel, "cell 1 1 9 3,grow");
		rightPanel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][]"));
	
		
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
	    
	    SpinnerModel entitymodel = new SpinnerNumberModel(1, // initial value
	            1, // min
	            4, // max (because there is going to be 1 startzone with a maximum of 4 spawning points)
	            1); // step
	    entities = new JSpinner(entitymodel);
	    rightPanel.add(entities, "cell 1 2");
	    
	    rightPanel.add(rowsLabel, "cell 0 0,alignx trailing");
		rightPanel.add(columnsLabel, "cell 0 1,alignx trailing");
	    rightPanel.add(entityLabel, "cell 0 2,alignx right");
		rightPanel.add(beginFromScratchButton, "cell 0 6");
		rightPanel.add(useStandardBasisButton, "cell 1 6");
		
		setLocationRelativeTo(null);
			
	}
	
    /**
     * get {@link #rows} as set by user
     * 
     * @return {@link #rows}
     */
    public int getRows() {
        return (Integer) (rows.getValue());
    }

    /**
     * get {@link #cols} as set by user
     * 
     * @return {@link #cols}
     */
    public int getColumns() {
        return (Integer) (cols.getValue());
    }
    
    /**
     * get {@link #entities} as set by user
     * 
     * @return {@link #entities}
     */
    public int getEntities() {
    	return (Integer) (entities.getValue());
    }
	
    /**
     * @return beginFromScratchButton
     */
	public JButton getBeginFromScratchButton() {
		return beginFromScratchButton;
	}
	
	/**
     * @return useStandardBasisButton
     */
	public JButton getStandardBasisButton() {
		return useStandardBasisButton;
	}
	
	/**
	 * Return the SizeDialogController
	 * @return sdc
	 */
	public SizeDialogController getSizeDialogController() { 
		return sdc;
	}   
}
