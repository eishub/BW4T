package nl.tudelft.bw4t.scenariogui.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Component;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EntityJTableTest {
    private EntityPanel entityPanel;
    private EntityJTable botJTable;
    private EntityJTable ePartnerTable;
    private EntityPanel spyEntityPanel;
    private ScenarioEditor editor;

    @Before
    public final void setUp() {
        entityPanel = new EntityPanel();
        spyEntityPanel = spy(entityPanel);
        editor = new ScenarioEditor(new ConfigurationPanel(),
                spyEntityPanel, new BW4TClientConfig());
        botJTable = entityPanel.getBotTable();
        ePartnerTable = entityPanel.getEPartnerTable();
    }

    @After
    public final void tearDown() {
        editor.dispose();
    }

    @Test
    public void getToolTipTextTest() {
        entityPanel.getNewBotButton().doClick();
        assertEquals(botJTable.getToolTipText(0, 0), null);
        assertEquals(botJTable.getToolTipText(0, 1), null);
        assertFalse(botJTable.getToolTipText(0, 2) == null);
        assertEquals(botJTable.getToolTipText(0, 3), null);
    }

    @Test
    public void getAgentFileColumnTest() {
        assertEquals(botJTable.getAgentFileColumn(), 2);
        assertEquals(ePartnerTable.getAgentFileColumn(), 1);
    }

    @Test
    public void getAgentFileColumnExceptionTest1() {
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(yesMockOption);

        EntityJTable errorTable = new EntityJTable();
        errorTable.getAgentFileColumn();

        verify(yesMockOption, times(1)).showMessageDialog((Component) any(), any());
    }

    @Test
    public void getAgentFileColumnExceptionTest2() {
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(yesMockOption);

        EntityJTable errorTable = new EntityJTable();
        errorTable.setName("InvalidName");
        errorTable.getAgentFileColumn();

        verify(yesMockOption, times(1)).showMessageDialog((Component) any(), any());
    }
}
