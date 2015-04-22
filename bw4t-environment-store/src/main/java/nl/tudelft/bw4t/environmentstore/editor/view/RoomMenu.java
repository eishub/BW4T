package nl.tudelft.bw4t.environmentstore.editor.view;

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;

/**
 * This class extends the zone menu to a room menu so that only when clicking on
 * a room can a user add a door (which is the main component of the room menu).
 */
public class RoomMenu extends ZoneMenu {

    /** Random generated serial version UID. */
    private static final long serialVersionUID = 5919212594524584613L;

    /** The menu where the user can choose which side the door should be. */
    private JMenu doorSide;

    /**
     * Radio buttons for the position of the door. We chose to use radio buttons
     * because there can only be one door per room.
     */
    private JRadioButtonMenuItem north, east, south, west;

    /**
     * @param mpc
     *            The map panel controller liked to the room menu.
     */
    public RoomMenu(MapPanelController mpc) {
        super(mpc);
    }

    /** Initialises the radio buttons that appear in the Room menu. */
    protected void init() {
        super.init();

        // Create a Menu for Door Side
        doorSide = new JMenu("Door Side");
        add(doorSide);

        // Add all MenuItems for Door Side
        north = new JRadioButtonMenuItem("North");

        east = new JRadioButtonMenuItem("East");

        south = new JRadioButtonMenuItem("South");

        west = new JRadioButtonMenuItem("West");
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JRadioButtonMenuItem getMenuItemDoorSideNorth() {
        return north;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JRadioButtonMenuItem getMenuItemDoorSideEast() {
        return east;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JRadioButtonMenuItem getMenuItemDoorSideSouth() {
        return south;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JRadioButtonMenuItem getMenuItemDoorSideWest() {
        return west;
    }

    @Override
    public void update() {
        super.update();

        restrictDoorOptions();

        north.setSelected(zone.hasDoor(ZoneModel.NORTH));
        east.setSelected(zone.hasDoor(ZoneModel.EAST));
        south.setSelected(zone.hasDoor(ZoneModel.SOUTH));
        west.setSelected(zone.hasDoor(ZoneModel.WEST));
    }

    /** Only shows the options where one can add a door. */
    private void restrictDoorOptions() {
        if (zone.canPlaceDoor(ZoneModel.NORTH)) {
            doorSide.add(north);
        }
        if (zone.canPlaceDoor(ZoneModel.EAST)) {
            doorSide.add(east);
        }
        if (zone.canPlaceDoor(ZoneModel.SOUTH)) {
            doorSide.add(south);
        }
        if (zone.canPlaceDoor(ZoneModel.WEST)) {
            doorSide.add(west);
        }
    }
}
