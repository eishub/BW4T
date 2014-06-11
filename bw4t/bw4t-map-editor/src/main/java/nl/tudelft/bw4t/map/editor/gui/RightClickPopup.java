package nl.tudelft.bw4t.map.editor.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;

public class RightClickPopup extends JPopupMenu {

	private static final long serialVersionUID = -5335591852441574491L;
	
	private JMenu zoneType, doorSide;
	
	private EnvironmentStore view;
	
	private JMenuItem randomize;
	private JMenuItem corridor, room, blockade, startZone, chargingZone, dropZone;
	private JMenuItem north, east, south, west;
	
	public RightClickPopup(EnvironmentStore theView) {
		view = theView;
		
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
        
        // Add the right click to the table
        // TODO: Refactor to be MVC
        view.getMapTable().addMouseListener( new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable)e.getSource();
                    int row = source.rowAtPoint(e.getPoint() );
                    int column = source.columnAtPoint(e.getPoint() );

                    if (!source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
	}
	
    /**
     * Return the view being controlled.     *
     * @return The JFrame being controlled.
     */
    public final EnvironmentStore getMainView() {
        return view;
    }
	
    /**
     * JMenuItem to change an area to a corridor.
     * @return The JMenuItem to change an area to a corridor.
     */
    public final JMenuItem getMenuItemAreaCorridor() {
        return corridor;
    }
    
    /**
     * JMenuItem to change an area to a room.
     * @return The JMenuItem to change an area to a room.
     */
    public final JMenuItem getMenuItemAreaRoom() {
        return room;
    }
    
    /**
     * JMenuItem to change an area to a blockade.
     * @return The JMenuItem to change an area to a blockade.
     */
    public final JMenuItem getMenuItemAreaBlockade() {
        return blockade;
    }
    
    /**
     * JMenuItem to change an area to a chargingZone.
     * @return The JMenuItem to change an area to a chargingZone.
     */
    public final JMenuItem getMenuItemAreaChargingZone() {
        return chargingZone;
    }
    
    /**
     * JMenuItem to change an area to a startZone.
     * @return The JMenuItem to change an area to a startZone.
     */
    public final JMenuItem getMenuItemAreaStartZone() {
        return startZone;
    }
    
    /**
     * JMenuItem to change an area to a dropZone.
     * @return The JMenuItem to change an area to a dropZone.
     */
    public final JMenuItem getMenuItemAreaDropZone() {
        return dropZone;
    }

    /**
     * JMenuItem to change an area to a dropZone.
     * @return The JMenuItem to change an area to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideNorth() {
        return north;
    }
    
    /**
     * JMenuItem to change an area to a dropZone.
     * @return The JMenuItem to change an area to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideEast() {
        return east;
    }
    
    /**
     * JMenuItem to change an area to a dropZone.
     * @return The JMenuItem to change an area to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideSouth() {
        return south;
    }
    
    /**
     * JMenuItem to change an area to a dropZone.
     * @return The JMenuItem to change an area to a dropZone.
     */
    public final JMenuItem getMenuItemDoorSideWest() {
        return west;
    }
    
    

}
