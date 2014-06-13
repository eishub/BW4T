package nl.tudelft.bw4t.map.editor.gui;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.map.editor.controller.ZoneController;
import nl.tudelft.bw4t.map.editor.model.ZoneModel;

/**
 * The ZonePopupMenu shows us the options for the different Zones through right click.
 *
 */
public class ZonePopupMenu extends JPopupMenu implements UpdateableEditorInterface {

    private static final long serialVersionUID = -5335591852441574491L;

    private JMenu zoneType, doorSide;

    private JMenuItem randomize;
    
    private JRadioButtonMenuItem corridor, room, blockade, startZone, chargingZone, dropZone;
    
    private JCheckBoxMenuItem north, east, south, west;

    private final MapPanelController mapController;

    /**
     * Constructor adds all the options to the menu.
     * @param mpc is the MapPanelController used.
     */
    public ZonePopupMenu(MapPanelController mpc) {
        mapController = mpc;

        // Create a Menu for Type Of Space
        zoneType = new JMenu("Type of Zone");
        add(zoneType);

        // Add all MenuItems for type of Space
        ButtonGroup group = new ButtonGroup();

        corridor = new JRadioButtonMenuItem("Corridor");
        group.add(corridor);
        zoneType.add(corridor);

        room = new JRadioButtonMenuItem("Room");
        group.add(room);
        zoneType.add(room);

        blockade = new JRadioButtonMenuItem("Blockade");
        group.add(blockade);
        zoneType.add(blockade);

        startZone = new JRadioButtonMenuItem("Start Zone");
        group.add(startZone);
        zoneType.add(startZone);

        chargingZone = new JRadioButtonMenuItem("Charge Zone");
        group.add(chargingZone);
        zoneType.add(chargingZone);

        dropZone = new JRadioButtonMenuItem("Drop Zone");
        group.add(dropZone);
        zoneType.add(dropZone);

        // Create a Menu for Door Side
        doorSide = new JMenu("Door Side");
        add(doorSide);

        // Add all MenuItems for Door Side
        north = new JCheckBoxMenuItem("North");
        doorSide.add(north);

        east = new JCheckBoxMenuItem("East");
        doorSide.add(east);

        south = new JCheckBoxMenuItem("South");
        doorSide.add(south);

        west = new JCheckBoxMenuItem("West");
        doorSide.add(west);

        // Create a MenuItem for Randomize Blocks in Room
        randomize = new JMenuItem("Randomize Blocks");
        randomize.setToolTipText("Randomize the blocks inside this room");
        add(randomize);

    }

    /**
     * JMenuItem to change an zone to a corridor.
     * 
     * @return The JMenuItem to change an zone to a corridor.
     */
    public final JMenuItem getMenuItemZoneCorridor() {
        return corridor;
    }

    /**
     * JMenuItem to change an zone to a room.
     * 
     * @return The JMenuItem to change an zone to a room.
     */
    public final JMenuItem getMenuItemZoneRoom() {
        return room;
    }

    /**
     * JMenuItem to change an zone to a blockade.
     * 
     * @return The JMenuItem to change an zone to a blockade.
     */
    public final JMenuItem getMenuItemZoneBlockade() {
        return blockade;
    }

    /**
     * JMenuItem to change an zone to a chargingZone.
     * 
     * @return The JMenuItem to change an zone to a chargingZone.
     */
    public final JMenuItem getMenuItemZoneChargingZone() {
        return chargingZone;
    }

    /**
     * JMenuItem to change an zone to a startZone.
     * 
     * @return The JMenuItem to change an zone to a startZone.
     */
    public final JMenuItem getMenuItemZoneStartZone() {
        return startZone;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemZoneDropZone() {
        return dropZone;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideNorth() {
        return north;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideEast() {
        return east;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideSouth() {
        return south;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideWest() {
        return west;
    }
    
	public JMenuItem getRandomize() {
		return randomize;
	}

    @Override
    public void update() {
        ZoneController zone = mapController.getSelected();

        corridor.setSelected(true);

        if (zone == null)
            return;

        switch (zone.getType()) {
        case BLOCKADE:
            blockade.setSelected(true);
            break;
        case CHARGINGZONE:
            chargingZone.setSelected(true);
            break;
        case ROOM:
            if (zone.isDropZone()) {
                dropZone.setSelected(true);
            }
            else {
                room.setSelected(true);
            }
            break;
        case CORRIDOR:
        default:
            if (zone.isStartZone()) {
                startZone.setSelected(true);
            }
            break;
        }
        
        north.setSelected(zone.hasDoor(ZoneModel.NORTH));
        east.setSelected(zone.hasDoor(ZoneModel.EAST));
        south.setSelected(zone.hasDoor(ZoneModel.SOUTH));
        west.setSelected(zone.hasDoor(ZoneModel.WEST));
    }
}
