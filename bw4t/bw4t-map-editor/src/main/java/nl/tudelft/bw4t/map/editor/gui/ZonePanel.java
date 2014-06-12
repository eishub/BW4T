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

/**
 * The ZonePanel is the panel added to the grid for each Zone.
 *
 */
public class ZonePanel extends JPanel implements UpdateableEditorInterface {
	
    private static final long serialVersionUID = 1568360768572796042L;
    
    private static final Dimension SIZE = new Dimension(100,70);

    private ZoneController controller;
    
    private final Color originalColor;

    private JLabel nameLabel = new JLabel();
    
    private ColorSequenceEditor sequence = new ColorSequenceEditor();

    /**
     * Constructor for the ZonePanel based on the ZoneController.
     * @param control is the ZoneController being used to construct the Panel.
     */
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
        
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(nameLabel, BorderLayout.CENTER);
        
        this.setSize(SIZE);
        this.setPreferredSize(SIZE);
        this.setMinimumSize(SIZE);
        
        update();
    }
    
    public ZoneController getController() {
        return this.controller;
    }

    @Override
    public void update() {
        nameLabel.setText(controller.getName());
        nameLabel.setForeground(Color.BLACK);
        this.remove(sequence);
        switch (controller.getType()) {
        case ROOM:
            if (controller.isDropZone()) {
                nameLabel.setForeground(Color.WHITE);
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
