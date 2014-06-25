package nl.tudelft.bw4t.epartner.epartnerframe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EpartnerFrameTest {

    private EpartnerFrame frame;
    private EpartnerFrame spyframe;
    private EpartnerController controller;
    private EPartnerConfig config;

    @Before
    public final void setupEpartnerFrame() {
        EntityPanel entityPanel = new EntityPanel();
        ScenarioEditor main = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        MainPanel parent = main.getMainPanel();
        config = new EPartnerConfig();
        main.getController().getModel().getEpartners().add(config);
        controller = new EpartnerController(parent, 0);
        frame = new EpartnerFrame(controller);
        spyframe = spy(frame);
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getYesOptionPrompt());
    }

    @After
    public final void dispose() {
        frame.dispose();
    }

    @Test
    public final void testUpdateConfig() {
        spyframe.getGPSCheckbox().setSelected(true);
        spyframe.getForgetMeNotCheckbox().setSelected(true);
        controller.updateConfig(spyframe);
        assertTrue(config.isGps());
        assertTrue(config.isForgetMeNot());
    }

    @Test
    public final void testResetButton() {
        spyframe.getResetButton().doClick();
        assertEquals(config.isGps(), spyframe.getGPSCheckbox().isSelected());
        assertEquals(config.isForgetMeNot(), spyframe.getForgetMeNotCheckbox().isSelected());
    }

    @Test
    public final void testApplyButton() {
        spyframe.getGPSCheckbox().setSelected(false);
        spyframe.getForgetMeNotCheckbox().setSelected(true);
        spyframe.getSaveButton().doClick();
        assertEquals(config.isGps(), spyframe.getGPSCheckbox().isSelected());
        assertEquals(config.isForgetMeNot(), spyframe.getForgetMeNotCheckbox().isSelected());
    }
}
