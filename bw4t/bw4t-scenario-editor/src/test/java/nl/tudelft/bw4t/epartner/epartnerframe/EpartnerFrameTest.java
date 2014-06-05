package nl.tudelft.bw4t.epartner.epartnerframe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

public class EpartnerFrameTest {
	
	private EpartnerFrame frame;
	private EpartnerFrame spyframe;
	
	@Before
	public final void setupEpartnerFrame() {
        frame = new EpartnerFrame(new EpartnerController(new EPartnerConfig()));
        spyframe= spy(frame);
	}
	
	@After
	public final void dispose(){
		frame.dispose();
	}
	
	@Test
	public final void testInititalSetup() {
		assertFalse(spyframe.getForgetMeNot());
		assertFalse(spyframe.getGPSCheckbox().isSelected());
	}
}
