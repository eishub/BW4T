package nl.tudelft.bw4t.scenariogui.editor.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Component;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.util.NoMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * Tests the warning when more e-parters than bots are added.
 */
public class UpdateEPartnerCountTest {
    
    private ScenarioEditor scenarioEditor;
    
    private EntityPanel entityPanel;
    
    private NoMockOptionPrompt spyOption;
    
    @Before
    public void startUp() {
        entityPanel = new EntityPanel();
        scenarioEditor = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);
    }
    
    @After
    public void dispose() {
        scenarioEditor.dispose();
    }
    
    /**
     * Tests if no warning pops up when not needed.
     */
    @Test
    public void testNoWarning() {
        entityPanel.getNewBotButton().doClick();
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, never()).showMessageDialog((Component) any(), any());
    }
    
    /**
     * Tests if the warning pops up when needed.
     */
    @Test
    public void testWarning() {
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, times(1)).showMessageDialog((Component) any(), anyString());
    }
    
    /**
     * Tests if the warning doesn't pop up again after it
     * has been shown once.
     */
    @Test
    public void testNoSecondWarning() {
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, times(1)).showMessageDialog((Component) any(), any());
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, times(1)).showMessageDialog((Component) any(), any());
    }
    
    /**
     * Tests if the warning pops up again after the e-partner count
     * has dropped below (or equals) the bot count again.
     */
    @Test
    public void testWarningAfterReset() {
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, times(1)).showMessageDialog((Component) any(), any());
        entityPanel.getDeleteEPartnerButton().doClick();
        verify(spyOption, times(2)).showMessageDialog((Component) any(), any());
    }

}
