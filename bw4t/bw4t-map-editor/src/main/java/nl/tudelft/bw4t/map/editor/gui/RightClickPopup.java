package nl.tudelft.bw4t.map.editor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class RightClickPopup extends JPopupMenu {

	private static final long serialVersionUID = -5335591852441574491L;
	
	private JMenu areaType, doorSide;
	
	private JMenuItem randomize;
	private JMenuItem corridor, room, blockade, startZone, chargingZone, dropZone;
	private JMenuItem north, east, south, west;
	
	public RightClickPopup(JTable table) {
		
		// Create a Menu for Type Of Space
		areaType = new JMenu("Type of Area");
		areaType.setToolTipText("Set the type of area");
        add(areaType);
        
        // Add all MenuItems for type of Space
        corridor = new JMenuItem("Corridor");
        
		// TODO: Refactor for MVC
        corridor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		    	System.out.println("Change to Corridor");
			}
		});
        areaType.add(corridor);
        
        room = new JMenuItem("Room");
        areaType.add(room);

        blockade = new JMenuItem("Blockade");
        areaType.add(blockade);
        
        startZone = new JMenuItem("Start Zone");
        areaType.add(startZone);
        
        chargingZone = new JMenuItem("Charging Zone");
        areaType.add(chargingZone);
        
        dropZone = new JMenuItem("Drop Zone");
        areaType.add(dropZone);
        
        // Create a Menu for Door Side
        doorSide = new JMenu("Door Side");
        doorSide.setToolTipText("Set the side of a rooms door");
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
        randomize = new JMenuItem("Randomize Room");
        randomize.setToolTipText("Randomize the blocks inside this room");
        add(randomize);
        
        // Add the right click to the table
        // TODO: Refactor to be MVC
        table.addMouseListener( new MouseAdapter() {
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


}
