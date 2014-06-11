package nl.tudelft.bw4t.map.editor.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.bw4t.map.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.map.editor.controller.ZoneController;

public class ZonePanel extends JPanel implements UpdateableEditorInterface{
    private static final long serialVersionUID = 1568360768572796042L;

    private ZoneController controller;
    
    private JLabel nameLabel = new JLabel();
    private ColorSequenceEditor sequence = new ColorSequenceEditor();
    
    public ZonePanel(ZoneController control) {
        assert control != null;
        controller = control;
    }

    @Override
    public void update() {
    }
    
    
}
