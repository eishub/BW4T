package nl.tudelft.bw4t.environmentstore.editor.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;

import org.junit.Before;
import org.junit.Test;

public class ZoneControllertest {

    private ZoneModel model;
    
    private MapPanelController mpc;
    
    private ZoneController controller;
    
    private UpdateableEditorInterface uei = new UpdateableEditorInterface() {
        @Override
        public void update() {
            // For testing purposes only
            
        }
    };
    
    @Before
    public void setUp() {
        model = new ZoneModel();
        controller = new ZoneController(mpc, model);
    }
    
    @Test
    public void testIsDropZone() {
        assertFalse(controller.isDropZone());
        controller.setDropZone(true);
        controller.setStartZone(true);
        assertTrue(controller.isDropZone());
        assertTrue(controller.isStartZone());
    }
    
    @Test
    public void testDoor() {
        assertFalse(controller.hasDoor(0));
        controller.setDoor(0, true);
        assertTrue(controller.hasDoor(0));
        assertFalse(controller.canPlaceDoor(0));
    }
    
    @Test
    public void testZoneModel() {
        assertEquals(model, controller.getZoneModel());
        ZoneModel model2 = new ZoneModel();
        model2.setDoor(0, true);
        controller.setZoneModel(model2);
        assertEquals(model2, controller.getZoneModel());
        assertTrue(controller.hasDoor(0));
        assertFalse(controller.canPlaceDoor(0));
    }
}
