package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.bw4t.map.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.map.editor.controller.ZoneController;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

public class ZonePanel extends JPanel implements UpdateableEditorInterface {
    private static final long serialVersionUID = 1568360768572796042L;

    private ZoneController controller;
    
    private final Color originalColor;

    private JLabel nameLabel = new JLabel();
    private ColorSequenceEditor sequence = new ColorSequenceEditor();

    public ZonePanel(ZoneController control) {
        assert control != null;
        controller = control;
        originalColor = getBackground();
        controller.setUpdateableEditorInterface(this);
        
        this.setLayout(new BorderLayout());
        this.add(nameLabel, BorderLayout.CENTER);
        
        update();
    }
    
    public ZoneController getController() {
        return this.controller;
    }

    @Override
    public void update() {
        nameLabel.setText(controller.getName());
        this.remove(sequence);
        switch (controller.getType()) {
        case ROOM:
            if (controller.getName().equals(Zone.DROP_ZONE_NAME)) {
                this.setBackground(Color.DARK_GRAY);
            }
            else {
                this.setBackground(Color.GRAY);
                this.add(sequence, BorderLayout.SOUTH);
            }
            break;
        case CHARGINGZONE:
            this.setBackground(Zone.CHARGING_ZONE_COLOR);
            break;
        case BLOCKADE:
            this.setBackground(Zone.BLOCKADE_COLOR);
            break;
        case CORRIDOR:
        default:
            this.setBackground(originalColor);
            break;
        }
    }

}
