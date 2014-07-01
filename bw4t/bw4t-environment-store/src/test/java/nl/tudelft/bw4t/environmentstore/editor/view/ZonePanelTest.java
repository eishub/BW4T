package nl.tudelft.bw4t.environmentstore.editor.view;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;

import org.junit.Test;
/**
 * Test suite to cover ZonePanel.
 *
 */
public class ZonePanelTest {
    private MapPanelController mpc = new MapPanelController(1, 1);
    private ZoneModel zm = new ZoneModel();
    private ZoneController zc = new ZoneController(mpc, zm);
    private ZonePanel zp = new ZonePanel(zc);
    
    @Test
    public void getControllerTest() {
        assertTrue(zp.getController() == zc);
    }
}
