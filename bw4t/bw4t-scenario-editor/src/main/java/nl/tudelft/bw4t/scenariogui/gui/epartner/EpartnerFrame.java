package src.main.java.nl.tudelft.bw4t.scenariogui.gui.epartner;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

/**
 * This class creates the frame for the e-Partner GUI.
 * @author Wendy
 */

public class EpartnerFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public EpartnerFrame() {
		setTitle("e-Partner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 144);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][141px][][141px][141px]", "[][][125px][125px]"));
		
		JCheckBox LeftAloneCheckBox = new JCheckBox("Left-alone Warning");
		LeftAloneCheckBox.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(LeftAloneCheckBox, "cell 0 0,grow");
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JCheckBox GPSCheckBox = new JCheckBox("Geolocator");
		GPSCheckBox.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(GPSCheckBox, "cell 0 1,grow");
		contentPane.add(CancelButton, "cell 0 3,growx,aligny bottom");
		
		JButton ApplyButton = new JButton("Apply");
		ApplyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton ResetButton = new JButton("Reset");
		contentPane.add(ResetButton, "cell 2 3,growx,aligny bottom");
		contentPane.add(ApplyButton, "cell 4 3,alignx right,aligny bottom");
	}
}
