package nl.tudelft.bw4t.environmentstore.editor.view;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
/**
 * Test suite to cover ZonePanel.
 *
 */
public class ZonePanelTest {
	private MapPanelController mpc = new MapPanelController(1, 1);
	private ZoneModel zm = new ZoneModel();
	@Mock private ZoneController zc = new ZoneController(mpc, zm);
	private ZonePanel zp = new ZonePanel(zc);
	
	@Test
	public void getControllerTest() {
		assertTrue(zp.getController() == zc);
	}
	
	@Test
	public void updateCaseRoomTest() {
	}
}
