package nl.tudelft.bw4t.environmentstore.editor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import nl.tudelft.bw4t.environmentstore.editor.controller.ColorSequenceEditor;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.map.Zone;

/** The ZonePanel is the panel added to the grid for each Zone. */
public class ZonePanel extends JPanel implements UpdateableEditorInterface {

    /** Random generated serial version UID. */
    private static final long serialVersionUID = 1568360768572796042L;

    /** Standard size of a zone panel. */
    private static final Dimension SIZE = new Dimension(100, 70);

    /** The controller for this view class. */
    private ZoneController zoneController;

    /** The original cell colour. */
    private final Color originalColor;

    /** The label displayed per cell. */
    private JLabel nameLabel = new JLabel();

    /** The sequence editor which modifies the sequence of blocks to be picked. */
    private ColorSequenceEditor sequence = new ColorSequenceEditor();

    /** The default border of each cell. */
    private Border defaultBorder;

    /**
     * Constructor for the ZonePanel based on the ZoneController.
     * 
     * @param control
     *            is the ZoneController being used to construct the Panel.
     */
    public ZonePanel(ZoneController control) {
        assert control != null;
        zoneController = control;
        originalColor = getBackground();
        zoneController.setUpdateableEditorInterface(this);

        this.setBorder(BorderFactory.createEtchedBorder());

        this.addMouseListener(zoneController);
        zoneController.getMapController().getCSController()
                .addColorSequenceEditor(sequence);
        sequence.addChangeListener(zoneController);

        this.setLayout(new BorderLayout());

        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(nameLabel, BorderLayout.CENTER);

        this.setSize(SIZE);
        this.setPreferredSize(SIZE);
        this.setMinimumSize(SIZE);
        this.defaultBorder = this.getBorder();

        update();
    }

    public ZoneController getController() {
        return this.zoneController;
    }

    /** There are different zone colours depending on which zone the user chooses. */
    @Override
    public void update() {
        nameLabel.setText(zoneController.getName());
        nameLabel.setForeground(Color.BLACK);
        this.remove(sequence);
        this.removeBorder();
        switch (zoneController.getType()) {
        case ROOM:
            if (zoneController.isDropZone()) {
                nameLabel.setForeground(Color.WHITE);
                this.setBackground(Color.DARK_GRAY);
            } else {
                this.setBackground(Color.GRAY);
                this.add(sequence, BorderLayout.SOUTH);
                this.sequence.setSequence(zoneController.getColors());
            }
            updateDoor();
            break;
        case CHARGINGZONE:
            this.setBackground(Zone.CHARGING_ZONE_COLOR);
            break;
        case BLOCKADE:
            this.setBackground(Zone.BLOCKADE_COLOR);
            break;
        case CORRIDOR:
            if (zoneController.isStartZone()) {
                this.setBackground(Color.YELLOW);
            } else {
                this.setBackground(originalColor);
            }
        default:
            break;
        }

        revalidate();
        repaint();
    }

    /**
     * Draws a green border at the position the door is at. It is possible to
     * draw multiple doors per room with this code, although this feature has
     * not been implemented. Only one door per room can be added.
     */
    private void updateDoor() {
        int east = 0;
        int north = 0;
        int south = 0;
        int west = 0;

        if (zoneController.hasDoor(ZoneModel.EAST)) {
            east = 2;
        }
        if (zoneController.hasDoor(ZoneModel.NORTH)) {
            north = 2;
        }
        if (zoneController.hasDoor(ZoneModel.SOUTH)) {
            south = 2;
        }
        if (zoneController.hasDoor(ZoneModel.WEST)) {
            west = 2;
        }

        this.setBorder(BorderFactory.createMatteBorder(north, west, south,
                east, Color.GREEN));
    }

    /** Removes the border when the door is deleted. */
    private void removeBorder() {
        this.setBorder(this.defaultBorder);
    }
}
