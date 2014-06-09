package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.bw4t.map.editor.controller.Map;

public class ColorSequencePanelCell extends JPanel {

	private static final long serialVersionUID = 7898118456497448363L;
	
    private ColorSequenceEditorCell seqEdit;
    
	private Map map;
	
	public ColorSequencePanelCell(Map theMap) {
		this.map = theMap;
		setLayout(new BorderLayout());

        add(new JLabel("Target sequence: "), BorderLayout.WEST);
        seqEdit = new ColorSequenceEditorCell(map.getSequence());
        add(seqEdit, BorderLayout.CENTER);
        // the only way to enforce small sequence panel??
        setMaximumSize(new Dimension(400, 20));

	
	    seqEdit.addFocusListener(new FocusListener() {
	        @Override
	        public void focusLost(FocusEvent arg0) {
	            map.setSequence(seqEdit.getColors());
	
	        }	
	        @Override
	        public void focusGained(FocusEvent e) {
	        }
	    });
	}

}
