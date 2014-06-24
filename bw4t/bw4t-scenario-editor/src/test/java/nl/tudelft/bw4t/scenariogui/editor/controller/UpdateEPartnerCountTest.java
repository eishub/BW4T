package nl.tudelft.bw4t.scenariogui.editor.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Component;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the warning when more e-parters than bots are added.
 */
public class UpdateEPartnerCountTest {

    private ScenarioEditor scenarioEditor;

    private EntityPanel entityPanel;

    private OptionPrompt spyOption;

    @Before
    public void startUp() {
        entityPanel = new EntityPanel();
        scenarioEditor = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);
    }

    @After
    public void dispose() {
        scenarioEditor.dispose();
    }

    @Test
    public void testNoWarning() {
        entityPanel.getNewBotButton().doClick();
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, never()).showMessageDialog((Component) any(), any());
    }

    @Test
    public void testWarning() {
        entityPanel.getNewEPartnerButton().doClick();
        verify(spyOption, times(1)).showMessageDialog((Component) any(), anyString());
    }

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
