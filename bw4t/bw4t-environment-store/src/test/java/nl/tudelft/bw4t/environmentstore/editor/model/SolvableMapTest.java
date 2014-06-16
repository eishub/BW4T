package nl.tudelft.bw4t.environmentstore.editor.model;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class SolvableMapTest {
	private MapPanelController mpc = new MapPanelController(5, 5, 1, true, true);
	@Spy private MapPanelController mpcSpy;
	@Before
	public void setUp() {
		mpcSpy = spy(mpc);
		when(mpcSpy.createMap()).thenReturn(null);
	}
	@Test
	public void pass() {
		
	}
}
