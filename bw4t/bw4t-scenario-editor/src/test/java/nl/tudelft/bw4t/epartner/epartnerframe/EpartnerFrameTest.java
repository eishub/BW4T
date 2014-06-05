package nl.tudelft.bw4t.epartner.epartnerframe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

public class EpartnerFrameTest {
	
	private EpartnerFrame frame;
	private EpartnerFrame spyframe;
	
	@Before
	public final void setupEpartnerFrame() {
        EntityPanel entityPanel = new EntityPanel();
        entityPanel.getEPartnerConfigs().add(new EPartnerConfig());
        MainPanel parent = new MainPanel(new ConfigurationPanel(), entityPanel);
        frame = new EpartnerFrame(parent, 0);
        spyframe= spy(frame);
	}
	
	@After
	public final void dispose(){
		frame.dispose();
	}
	
	@Test
	public final void testInititalSetup() {
		assertFalse(spyframe.getForgetCheckbox().isSelected());
		assertFalse(spyframe.getGPSCheckbox().isSelected());
	}
		
	@Test
	public final void testResetButton() {
		spyframe.getResetButton().doClick();
		assertFalse(spyframe.getForgetCheckbox().isSelected());
		assertFalse(spyframe.getGPSCheckbox().isSelected());
	}
}
