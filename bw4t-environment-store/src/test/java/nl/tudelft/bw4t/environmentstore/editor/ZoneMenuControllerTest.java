package nl.tudelft.bw4t.environmentstore.editor;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneMenuController;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.editor.view.RoomMenu;
import nl.tudelft.bw4t.environmentstore.editor.view.ZoneMenu;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ZoneMenuControllerTest {
    
    private ZoneMenu zoneMenu;
    
    private RoomMenu roomMenu;
    
    private ZoneMenuController menuController;
    
    @Mock private ZoneController zoneController;
    
    private MapPanelController mapController;
    
    private UpdateableEditorInterface uei = new UpdateableEditorInterface() {
        @Override
        public void update() {
            // For testing purposes only.
        }
    };
    
    @Before
    public void setUp() {
        zoneController = mock(ZoneController.class);
        mapController = spy(new MapPanelController(5,5));
        
        when(mapController.getSelected()).thenReturn(new ZoneController(mapController, new ZoneModel()));
        when(mapController.getSelected()).thenReturn(zoneController);
        when(mapController.getSelected().getUpdateableEditorInterface()).thenReturn(uei);

        zoneMenu = new ZoneMenu(mapController);
        roomMenu = new RoomMenu(mapController) {
            @Override
            public void update() {
                // For testing purposes only.
            }
        };
        menuController = new ZoneMenuController();
        menuController.attachListenersToZoneMenu(zoneMenu,mapController);
    }
    

    @Test
    public void attachZoneListenersTest() {
        menuController.attachListenersToZoneMenu(zoneMenu, mapController);
        
        assertFalse(zoneMenu.getMenuItemZoneBlockade().getActionListeners()[0].equals(null));
        assertFalse(zoneMenu.getMenuItemZoneChargingZone().getActionListeners()[0].equals(null));
        assertFalse(zoneMenu.getMenuItemZoneCorridor().getActionListeners()[0].equals(null));
        assertFalse(zoneMenu.getMenuItemZoneDropZone().getActionListeners()[0].equals(null));
        assertFalse(zoneMenu.getMenuItemZoneStartZone().getActionListeners()[0].equals(null));
        assertFalse(zoneMenu.getMenuItemZoneRoom().getActionListeners()[0].equals(null));
    }
    
    @Test
    public void attachZoneListenersActionTest() {
        menuController.attachListenersToZoneMenu(zoneMenu, mapController);
        /* Click the create blockade option button */
        zoneMenu.getMenuItemZoneBlockade().doClick();
        /* Verify if createZone is called */
        verify(mapController, atLeastOnce()).createZone(Type.BLOCKADE, false, false);
    }
    
    @Test
    public void attachRoomListenersTest() {
        menuController.attachListenersToRoomMenu(roomMenu, mapController);
        /* Check whether the Zone listeners have been set (one is enough to check all, see attachZoneListenersTest). */
        assertFalse(zoneMenu.getMenuItemZoneBlockade().getActionListeners()[0].equals(null));
        
        assertFalse(roomMenu.getMenuItemDoorSideNorth().getActionListeners()[0].equals(null));
        assertFalse(roomMenu.getMenuItemDoorSideEast().getActionListeners()[0].equals(null));
        assertFalse(roomMenu.getMenuItemDoorSideSouth().getActionListeners()[0].equals(null));
        assertFalse(roomMenu.getMenuItemDoorSideWest().getActionListeners()[0].equals(null));
    }
    
    @Test
    public void attachRoomListenersActionTest() {
        menuController.attachListenersToRoomMenu(roomMenu, mapController);
        /* Click the exit button */
        roomMenu.getMenuItemDoorSideSouth().doClick();
        /* Verify if closeScenarioEditor is called */
        verify(mapController.getSelected(), atLeastOnce()).setDoor(2, true);
    }

}
