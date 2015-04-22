package nl.tudelft.bw4t.environmentstore.editor.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * The ZonePopupMenu shows us the options for the different Zones through right click.
 * This allows the user to choose which zone type he or she wishes to add to the grid.
 * We chose to use radio buttons because there can only be one type of zone. 
 */
public class ZoneMenu extends JPopupMenu implements UpdateableEditorInterface {

    /** Random generated serial version UID. */
    private static final long serialVersionUID = -5335591852441574491L;

    /** The current zone type. */
    private JMenu zoneType;
    
    /** The different available radio buttons. */
    private JRadioButtonMenuItem corridor, room, blockade, startZone, chargingZone, dropZone;

    /** The map controller linked to this zone menu. */
    private final MapPanelController mapController;

    /** The controller linked to this view class. */
    protected ZoneController zone;

    /**
     * Constructor adds all the options to the menu.
     * @param mpc is the MapPanelController used.
     */
    public ZoneMenu(MapPanelController mpc) {
        mapController = mpc;

        init();
        
        update();
    }

    /** Initialises the options available in the zone menu. */
    protected void init() {
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

        if (zone == null) {
            return;
        }
        
        Type type = zone.getType();
        
        corridor.setSelected(true);
        blockade.setSelected(type == Type.BLOCKADE);
        chargingZone.setSelected(type == Type.CHARGINGZONE);
        room.setSelected(type == Type.ROOM);
        dropZone.setSelected(zone.isDropZone());
        startZone.setSelected(zone.isStartZone());
    }
}
