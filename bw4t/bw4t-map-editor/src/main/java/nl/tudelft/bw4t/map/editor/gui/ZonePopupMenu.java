package nl.tudelft.bw4t.map.editor.gui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ZonePopupMenu extends JPopupMenu {

	private static final long serialVersionUID = -5335591852441574491L;
	
	private JMenu zoneType, doorSide;
	
	private JMenuItem randomize;
	private JMenuItem corridor, room, blockade, startZone, chargingZone, dropZone;
	private JMenuItem north, east, south, west;
	
	public ZonePopupMenu() {
		// Create a Menu for Type Of Space
		zoneType = new JMenu("Type of Zone");
        add(zoneType);
        
        // Add all MenuItems for type of Space
        corridor = new JMenuItem("Corridor");
        zoneType.add(corridor);
        
        room = new JMenuItem("Room");
        zoneType.add(room);

        blockade = new JMenuItem("Blockade");
        zoneType.add(blockade);
        
        startZone = new JMenuItem("Start Zone");
        zoneType.add(startZone);
        
        chargingZone = new JMenuItem("Charging Zone");
        zoneType.add(chargingZone);
        
        dropZone = new JMenuItem("Drop Zone");
        zoneType.add(dropZone);
        
        // Create a Menu for Door Side
        doorSide = new JMenu("Door Side");
        add(doorSide);
        
        // Add all MenuItems for Door Side
        north = new JMenuItem("North");
        doorSide.add(north);
        
        east = new JMenuItem("East");
        doorSide.add(east);
        
        south = new JMenuItem("South");
        doorSide.add(south);
        
        west = new JMenuItem("West");
        doorSide.add(west);
        
        // Create a MenuItem for Randomize Blocks in Room
        randomize = new JMenuItem("Randomize Blocks");
        randomize.setToolTipText("Randomize the blocks inside this room");
        add(randomize);
        
	}
	
    /**
     * JMenuItem to change an zone to a corridor.
     * @return The JMenuItem to change an zone to a corridor.
     */
    public final JMenuItem getMenuItemZoneCorridor() {
        return corridor;
    }
    
    /**
     * JMenuItem to change an zone to a room.
     * @return The JMenuItem to change an zone to a room.
     */
    public final JMenuItem getMenuItemZoneRoom() {
        return room;
    }
    
    /**
     * JMenuItem to change an zone to a blockade.
     * @return The JMenuItem to change an zone to a blockade.
     */
    public final JMenuItem getMenuItemZoneBlockade() {
        return blockade;
    }
    
    /**
     * JMenuItem to change an zone to a chargingZone.
     * @return The JMenuItem to change an zone to a chargingZone.
     */
    public final JMenuItem getMenuItemZoneChargingZone() {
        return chargingZone;
    }
    
    /**
     * JMenuItem to change an zone to a startZone.
     * @return The JMenuItem to change an zone to a startZone.
     */
    public final JMenuItem getMenuItemZoneStartZone() {
        return startZone;
    }
    
    /**
     * JMenuItem to change an zone to a dropZone.
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemZoneDropZone() {
        return dropZone;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideNorth() {
        return north;
    }
    
    /**
     * JMenuItem to change an zone to a dropZone.
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideEast() {
        return east;
    }
    
    /**
     * JMenuItem to change an zone to a dropZone.
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideSouth() {
        return south;
    }
    
    /**
     * JMenuItem to change an zone to a dropZone.
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideWest() {
        return west;
    }
    
    

}
