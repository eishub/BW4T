package nl.tudelft.bw4t.epartner.epartnerframe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;

public class EpartnerFrameTest {
	
	private EpartnerFrame frame;
	private EpartnerFrame spyframe;
	
	@Before
	public final void setupEpartnerFrame() {
		frame = new EpartnerFrame();
		spyframe = spy(frame);
	}
	
	@After
	public final void dispose(){
		frame.dispose();
	}
	
	@Test
	public final void testInititalSetup() {
		assertFalse(spyframe.getLeftAloneCheckbox().isSelected());
		assertFalse(spyframe.getGPSCheckbox().isSelected());
	}
	
	@Test
	public final void testModifyCheckBoxes() {
		spyframe.getLeftAloneCheckbox().setSelected(true);
		spyframe.getGPSCheckbox().setSelected(true);
		assertTrue(spyframe.getLeftAloneCheckbox().isSelected());
		assertTrue(spyframe.getGPSCheckbox().isSelected());
	}
	
	@Test
	public final void testResetButton() {
		spyframe.getResetButton().doClick();
		assertFalse(spyframe.getLeftAloneCheckbox().isSelected());
		assertFalse(spyframe.getGPSCheckbox().isSelected());
	}
}
