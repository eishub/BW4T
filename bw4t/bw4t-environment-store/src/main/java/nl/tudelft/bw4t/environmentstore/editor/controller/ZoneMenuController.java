package nl.tudelft.bw4t.environmentstore.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.editor.view.RoomMenu;
import nl.tudelft.bw4t.environmentstore.editor.view.ZoneMenu;
import nl.tudelft.bw4t.map.Zone.Type;

/** The controller of the ZoneMenu
 * This attaches all the listeners to the ZoneMenu
 */
public class ZoneMenuController {

    /**
     * this method attaches all the listeners to the Zone Menu
     * @param menu the menu to which the listener is attached
     * @param mapcontroller the controller of the environment
     */
    public void attachListenersToZoneMenu(ZoneMenu menu,
            MapPanelController mapcontroller) {

        attachListenerToZoneMenuItem(menu.getMenuItemZoneBlockade(),
                mapcontroller, Type.BLOCKADE, false, false);

        attachListenerToZoneMenuItem(menu.getMenuItemZoneChargingZone(),
                mapcontroller, Type.CHARGINGZONE, false, false);

        attachListenerToZoneMenuItem(menu.getMenuItemZoneCorridor(),
                mapcontroller, Type.CORRIDOR, false, false);

        attachListenerToZoneMenuItem(menu.getMenuItemZoneDropZone(),
                mapcontroller, Type.ROOM, true, false);

        attachListenerToZoneMenuItem(menu.getMenuItemZoneRoom(), mapcontroller,
                Type.ROOM, false, false);

        attachListenerToZoneMenuItem(menu.getMenuItemZoneStartZone(),
                mapcontroller, Type.CORRIDOR, false, true);
    }

    /**
     * This method ensures the map controller creates the appropriate zone.
     * @param item The type of zone in the ZoneMenu
     * @param mapcontroller controller of the map
     * @param t The type of zone to be created
     * @param dropzone true if type is dropzone
     * @param startzone true if type is startzone
     */
    private void attachListenerToZoneMenuItem(final JMenuItem item,
            final MapPanelController mapcontroller, final Type t,
            final boolean dropzone, final boolean startzone) {
            item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mapcontroller.createZone(t, dropzone, startzone);
            }
        });
    }

    /**
     * this method attaches all the listeners to the Room Menu. 
     * @param menu the menu to which the listener is attached
     * @param mapcontroller controller of the map
     */
    public void attachListenersToRoomMenu(RoomMenu menu,
            MapPanelController mapcontroller) {

        attachListenersToZoneMenu(menu, mapcontroller);

        attachListenerToRoomMenuItem(menu.getMenuItemDoorSideEast(),
                mapcontroller, ZoneModel.EAST);

        attachListenerToRoomMenuItem(menu.getMenuItemDoorSideNorth(),
                mapcontroller, ZoneModel.NORTH);

        attachListenerToRoomMenuItem(menu.getMenuItemDoorSideSouth(),
                mapcontroller, ZoneModel.SOUTH);

        attachListenerToRoomMenuItem(menu.getMenuItemDoorSideWest(),
                mapcontroller, ZoneModel.WEST);
    }

    /**
     * This method ensures the map controller sets the appropriate doors.   
     * @param item option chosen by user
     * @param mapcontroller controller of the map
     * @param direction the direction of the door
     */
    private void attachListenerToRoomMenuItem(final JMenuItem item,
            final MapPanelController mapcontroller, final int direction) {
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mapcontroller.getSelected().setDoor(direction,
                        item.isSelected());
                mapcontroller.getSelected().getUpdateableEditorInterface().update();
            }
        });
    }
}
