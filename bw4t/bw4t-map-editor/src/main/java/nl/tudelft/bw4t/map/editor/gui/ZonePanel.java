package nl.tudelft.bw4t.map.editor.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.bw4t.map.editor.controller.ZoneController;

public class ZonePanel extends JPanel {
    
    private ZoneController controller;
    
    private JLabel nameLabel = new JLabel();
    private ColorSequenceEditor sequence = new ColorSequenceEditor();
    
    public ZonePanel(ZoneController control) {
        assert control != null;
        controller = control;
    }
    
    
}
