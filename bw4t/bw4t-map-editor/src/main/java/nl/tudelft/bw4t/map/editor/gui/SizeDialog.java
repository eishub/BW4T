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

public class SizeDialog extends JFrame {
	
	private JPanel contentPane;

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
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 0 0 1 4,alignx left,growy");
		panel_1.setLayout(new MigLayout("", "[113px]", "[23px]"));
		
		JButton btnEditExistingMap = new JButton("Edit existing map");
		panel_1.add(btnEditExistingMap, "cell 0 0,alignx left,aligny top");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 1 1 9 3,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][]"));
		
		JLabel lblrows = new JLabel("#rows");
		panel.add(lblrows, "cell 0 0,alignx trailing");
		
		SpinnerModel rowmodel = new SpinnerNumberModel(5, // initial value
	            5, // min
	            24, // max
	            1); // step
	    JSpinner rows = new JSpinner(rowmodel);
	    panel.add(rows, "cell 1 0");

	    SpinnerModel colmodel = new SpinnerNumberModel(5, // initial value
	            3, // min
	            24, // max
	            1); // step
	    JSpinner cols = new JSpinner(colmodel);
	    panel.add(cols, "cell 1 1");
		
		
		JLabel lblcolumns = new JLabel("#columns");
		panel.add(lblcolumns, "cell 0 1,alignx trailing");
		
		JCheckBox chckbxShowZoneLabels = new JCheckBox("Show Zone Labels");
		panel.add(chckbxShowZoneLabels, "cell 1 2");
		
		JButton btnBeginFromScratch = new JButton("Begin from scratch");
		panel.add(btnBeginFromScratch, "cell 0 4");
		
		JButton btnUseStandardBasis = new JButton("Use standard basis");
		panel.add(btnUseStandardBasis, "cell 1 4");
			
	}

}
