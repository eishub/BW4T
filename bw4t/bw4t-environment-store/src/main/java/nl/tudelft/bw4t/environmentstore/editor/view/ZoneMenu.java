package nl.tudelft.bw4t.environmentstore.editor.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;

/**
 * The ZonePopupMenu shows us the options for the different Zones through right click.
 *
 */
public class ZoneMenu extends JPopupMenu implements UpdateableEditorInterface {

    private static final long serialVersionUID = -5335591852441574491L;

    private JMenu zoneType;
    
    private JRadioButtonMenuItem corridor, room, blockade, startZone, chargingZone, dropZone;

    private final MapPanelController mapController;
    
    protected ZoneController zone;

    /**
     * Constructor adds all the options to the menu.
     * @param mpc is the MapPanelController used.
     */
    public ZoneMenu(MapPanelController mpc) {
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
        
        chargingZone = new JRadioButtonMenuItem("Charge Zone");
        group.add(chargingZone);
        zoneType.add(chargingZone);

        startZone = new JRadioButtonMenuItem("Start Zone");
        group.add(startZone);
        zoneType.add(startZone);

        dropZone = new JRadioButtonMenuItem("Drop Zone");
        group.add(dropZone);
        zoneType.add(dropZone);
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

    @Override
    public void update() {
        zone = mapController.getSelected();

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
    }
}