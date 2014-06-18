package nl.tudelft.bw4t.environmentstore.editor.view;

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;

public class RoomMenu extends ZoneMenu {
	
	private static final long serialVersionUID = 5919212594524584613L;
	
	private JMenu doorSide;
    private JRadioButtonMenuItem north, east, south, west;

	public RoomMenu(MapPanelController mpc) {
		super(mpc);
	}

	protected void init() {
		super.init();
		
		// Create a Menu for Door Side
	    doorSide = new JMenu("Door Side");
	    add(doorSide);

	    // Add all MenuItems for Door Side
	    north = new JRadioButtonMenuItem("North");
	    doorSide.add(north);

	    east = new JRadioButtonMenuItem("East");
	    doorSide.add(east);

	    south = new JRadioButtonMenuItem("South");
	    doorSide.add(south);

	    west = new JRadioButtonMenuItem("West");
	    doorSide.add(west);
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

    	north.setSelected(zone.hasDoor(ZoneModel.NORTH));
        east.setSelected(zone.hasDoor(ZoneModel.EAST));
        south.setSelected(zone.hasDoor(ZoneModel.SOUTH));
        west.setSelected(zone.hasDoor(ZoneModel.WEST));
    }
	
 

}
