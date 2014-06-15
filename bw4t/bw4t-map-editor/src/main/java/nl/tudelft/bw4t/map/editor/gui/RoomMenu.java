package nl.tudelft.bw4t.map.editor.gui;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.model.ZoneModel;

public class RoomMenu extends ZoneMenu {
	
	private static final long serialVersionUID = 5919212594524584613L;
	
	private JMenu doorSide;
    private JCheckBoxMenuItem north, east, south, west;

	public RoomMenu(MapPanelController mpc) {
		super(mpc);
		
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
	    
	    update();

	}
	
    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JCheckBoxMenuItem getMenuItemDoorSideNorth() {
        return north;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JCheckBoxMenuItem getMenuItemDoorSideEast() {
        return east;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JCheckBoxMenuItem getMenuItemDoorSideSouth() {
        return south;
    }

    /**
     * JMenuItem to change an zone to a dropZone.
     * 
     * @return The JMenuItem to change an zone to a dropZone.
     */
    public final JCheckBoxMenuItem getMenuItemDoorSideWest() {
        return west;
    }
    
    @Override
    public void update() {
    	super.update();
    	
        north.setSelected(zone.hasDoor(ZoneModel.NORTH));
        east.setSelected(zone.hasDoor(ZoneModel.EAST));
        south.setSelected(zone.hasDoor(ZoneModel.SOUTH));
        west.setSelected(zone.hasDoor(ZoneModel.WEST));
    }
	
 

}
