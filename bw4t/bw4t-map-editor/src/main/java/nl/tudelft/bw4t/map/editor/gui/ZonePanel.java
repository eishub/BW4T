package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
        
        this.setBorder(BorderFactory.createEtchedBorder());
        
        this.addMouseListener(controller);
        controller.getMapController().getCSController().addColorSequenceEditor(sequence);
        sequence.addChangeListener(controller);
        
        this.setLayout(new BorderLayout());
        this.add(nameLabel, BorderLayout.CENTER);
        
        Dimension d = new Dimension(75,50);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        
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
            if (controller.isDropZone()) {
                this.setBackground(Color.DARK_GRAY);
            } else {
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
        	if (controller.isStartZone()) {
        		this.setBackground(Color.CYAN);
        	} else {
        		this.setBackground(originalColor);
        	}
        default:
            break;
        }
        
        revalidate();
        repaint();
    }

}
