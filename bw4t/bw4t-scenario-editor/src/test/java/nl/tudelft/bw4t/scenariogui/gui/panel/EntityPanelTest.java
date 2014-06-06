package nl.tudelft.bw4t.scenariogui.gui.panel;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.util.NoMockOptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.YesMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class EntityPanelTest {

    /**
     * The entity panel of the GUI.
     */
    private EntityPanel entityPanel;

    /**
     * A spy object of the entity panel of the GUI.
     */
    private EntityPanel spyEntityPanel;

    /**
     * Store the scenario editor so it can be properly disposed of
     * at the end of the test run.
     */
    private ScenarioEditor editor;

    /**
     * Initializes the panel and GUI.
     */
    @Before
    public final void setUp() {
        entityPanel = new EntityPanel();
        spyEntityPanel = spy(entityPanel);

        ConfigurationPanel config = new ConfigurationPanel();
        /* The editor itself isn't used. It's simple so the BotPanel
         * gets handled by a controller. */
        editor = new ScenarioEditor(config, spyEntityPanel, new BW4TClientConfig());
    }

    /**
     * Close the ScenarioEditor to prevent to many windows from cluttering
     * the screen during the running of the tests
     */
    @After
    public final void tearDown() {
        editor.dispose();
    }

    /**
     * Test if the count of bots is correct after adding a bot
     * to the table.
     */
    @Test
    public final void testBotCount() {
        Object[] data = {"d1", "d2", "1"};

        spyEntityPanel.getNewBotButton().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    /**
     * Test if the count of bots is correct after changing
     * the number of bots
     */
    @Test
    public final void testBotCountListener() {
        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getBotTableModel().setValueAt("12", 0, 2);

        assertEquals(12, editor.getController().getModel().getAmountBot());
    }

    /**
     * Test if the count of E-partners is correct after adding
     * an E-partner to the table.
     */
    @Test
    public final void testEPartnerCount() {
        Object[] data = {"D1", "1"};

        spyEntityPanel.getNewEPartnerButton().doClick();

        assertEquals(1, editor.getController().getModel().getAmountEPartner());
    }

    /**
     * Test if the count of bots is correct after changing
     * the number of bots
     */
    @Test
    public final void testEPartnerCountListener() {
        Object[] data = {"d1", "1"};
        
        spyEntityPanel.getNewEPartnerButton().doClick();
        spyEntityPanel.getEPartnerTableModel().setValueAt("12", 0, 1);

        assertEquals(12, editor.getController().getModel().getAmountEPartner());
    }

    /**
     * Test if a bot is successfully added when the add
     * bot button is clicked.
     */
    @Test
    public void testAddNewBot() {
        spyEntityPanel.getNewBotButton().doClick();
        assertEquals(editor.getController().getModel().getAmountBot(), 1);
    }

    /**
     * Test if a bot is successfully modified when the
     * modify bot button is clicked.
     */
    @Test
    public void testModifyBot() {
        spyEntityPanel.getModifyBotButton().doClick();
        //TODO: Verify if the bot has actually been modified.
    }
    
    /**
     * Test if a bot is not modified when the
     * modify bot button is clicked,
     * while the bot is not selected.
     */
    @Test
    public void testModifyBotNoSelection() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);
        
        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();
        
        /* Attempt to modify the bot */
        spyEntityPanel.getModifyBotButton().doClick();
        
        /* Check if the warning message is shown  */
        verify(spyOption, times(1)).showMessageDialog(
                null, "Please select the bot you want to modify.");
    }

    /**
     * Test if a bot is actually deleted when the
     * delete bot is clicked, and the YES option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteBotConfirmDelete() {
        ScenarioEditor.setOptionPrompt(new YesMockOptionPrompt());

        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();

        /* Select that bot */
        spyEntityPanel.getBotTable().selectAll();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteBotButton().doClick();

        /* check if the bot count is zero */
        assertEquals(editor.getController().getModel().getAmountBot(), 0);
    }

    /**
     * Test if a bot is not deleted when the
     * delete bot is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteBotDeclineDelete() {
        ScenarioEditor.setOptionPrompt(new NoMockOptionPrompt());

        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();

        /* Select that bot */
        spyEntityPanel.getBotTable().selectAll();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteBotButton().doClick();

        /* check if the bot count is still 1. */
        assertEquals(editor.getController().getModel().getAmountBot(), 1);
    }


    /**
     * Test if a bot is not deleted when the
     * delete bot is clicked, while no row is selected.
     */
    @Test
    public void testDeleteBotNoSelection() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);

        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteBotButton().doClick();

        /* check if the bot count is still 1. */
        assertEquals(editor.getController().getModel().getAmountBot(), 1);
        
        /* Check if the warning message is shown */
        verify(spyOption, times(1)).showMessageDialog(null, "Please select the bot you want to delete.");
    }

    /**
     * Test if an E-partner is successfully added when the add
     * E-partner button is clicked.
     */
    @Test
    public void testAddEPartner() {
        spyEntityPanel.getNewEPartnerButton().doClick();
        assertEquals(editor.getController().getModel().getAmountEPartner(), 1);
    }


    /**
     * Test if an E-partner is successfully modified when the
     * modify E-partner button is clicked.
     */
    @Test
    public void testModifyEPartner() {
        /* Select an E-Partner */
        spyEntityPanel.getEPartnerTable().selectAll();

        /* Click the modify epartner button */
        spyEntityPanel.getModifyEPartnerButton().doClick();


        //TODO: Verify if the E-partner has actually been modified.
    }
    
    /**
     * Test if an E-partner is not modified when the
     * modify E-partner button is clicked,
     * while the E-partner is not selected.
     */
    @Test
    public void testModifyEPartnerNoSelection() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);
        
        /* Add an E-partner to the list */
        spyEntityPanel.getNewEPartnerButton().doClick();
        
        /* Attempt to modify the E-partner */
        spyEntityPanel.getModifyEPartnerButton().doClick();
        
        /* Check if the warning message is shown  */
        verify(spyOption, times(1)).showMessageDialog(
                null, "Please select the E-partner you want to modify.");
    }

    /**
     * Test if a pop-up is generated notifying the user that they made no selection
     * when they try to modify an epartner without first selecting one.
     */
    @Test
    public void testEPartnerModifyNoSelection() {
        YesMockOptionPrompt spyOption = spy(new YesMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);

        editor.getMainPanel().getEntityPanel().getModifyEPartnerButton().doClick();

        verify(spyOption, times(1)).showMessageDialog(null, "Please select the E-partner you want to modify.");
    }


    /**
     * Test if an E-partner is actually deleted when the
     * delete E-partner is clicked, and the YES option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteEPartnerConfirmDelete() {
        ScenarioEditor.setOptionPrompt(new YesMockOptionPrompt());


        /* Add an E-partner to the list */
        spyEntityPanel.getNewEPartnerButton().doClick();

        /* Select that E-partner */
        spyEntityPanel.getEPartnerTable().selectAll();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteEPartnerButton().doClick();

        /* Check if the E-partner count is zero */
        assertEquals(editor.getController().getModel().getAmountBot(), 0);
    }

    /**
     * Test if a E-partner is not deleted when the
     * delete E-partner is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteEPartnerDeclineDelete() {
        ScenarioEditor.setOptionPrompt(new NoMockOptionPrompt());


        /* Add an E-partner to the list */
        spyEntityPanel.getNewEPartnerButton().doClick();

        /* Select that E-partner */
        spyEntityPanel.getEPartnerTable().selectAll();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteEPartnerButton().doClick();

        /* Check if the E-partner count is still one */
        assertEquals(editor.getController().getModel().getAmountEPartner(), 1);
    }

    /**
     * Test if an E-partner is not deleted when the
     * delete E-partner button is clicked, while no row is selected.
     */
    @Test
    public void testDeleteEPartnerSelection() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);

        /* Add an E-partner to the list */
        spyEntityPanel.getNewEPartnerButton().doClick();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteEPartnerButton().doClick();

        /* Check if the E-partner count is still one */
        assertEquals(editor.getController().getModel().getAmountEPartner(), 1);
        
        /* Check if the warning message is shown */
        verify(spyOption, times(1)).showMessageDialog(
                null, "Please select the E-partner you want to delete.");
    }


    /**
     * Test if the bot dropdown menu when creating a new bot
     * is shown when the button is clicked.
     */
    @Ignore
    public void testBotDropdown() {
        spyEntityPanel.getDropDownButton().doClick();
        verify(spyEntityPanel, times(1)).showBotDropDown();
    }

    /**
     * Tests the BotConfig compare and the update functions.
     */
    @Test
    public void testBotConfigs() {
        
        assertTrue(editor.getController().getModel().compareBotConfigs(
                editor.getController().getModel().getOldBots()));
        
        editor.getController().getModel().getBots().add(new BotConfig());
        
        assertFalse(editor.getController().getModel().compareBotConfigs(
                editor.getController().getModel().getOldBots()));
        
        editor.getController().getModel().updateBotConfigs();
        
        assertTrue(editor.getController().getModel().compareBotConfigs(
                editor.getController().getModel().getOldBots()));
    }
    
    /**
     * Tests the EPartnerConfig compare and the update functions.
     */
    @Test
    public void testEPartnerConfigs() {
        assertTrue(editor.getController().getModel().compareEpartnerConfigs(
                editor.getController().getModel().getOldEpartners()));
        
        editor.getController().getModel().getEpartners().add(new EPartnerConfig());
        
        assertFalse(editor.getController().getModel().compareEpartnerConfigs(
                editor.getController().getModel().getOldEpartners()));
        
        editor.getController().getModel().updateEpartnerConfigs();
        
        assertTrue(editor.getController().getModel().compareEpartnerConfigs(
                editor.getController().getModel().getOldEpartners()));
    }

    /**
     * Tests the isDefault function.
     */
    @Test
    public void testDefault() {
        assertTrue(entityPanel.isDefault());
    }

    /**
     * Tests the isDefault function.
     */
    @Test
    public void testBotnonDefault() {
        Object[] botData = {"d1", "d2", "1"};
        
        entityPanel.getBotTableModel().addRow(botData);
        
        assertFalse(entityPanel.isDefault());
    }

    /**
     * Tests the isDefault function.
     */
    @Test
    public void testEpartnernonDefault() {
        Object[] epartnerData = {"d1", "1"};
        
        entityPanel.getEPartnerTableModel().addRow(epartnerData);
        
        assertFalse(entityPanel.isDefault());
    }

    /**
     * Tests the isDefault function.
     */
    @Test
    public void testBotAndEpartnernonDefault() {
        Object[] botData = {"d1", "d2", "1"};
        Object[] epartnerData = {"d1", "1"};
        
        entityPanel.getBotTableModel().addRow(botData);
        entityPanel.getEPartnerTableModel().addRow(epartnerData);
        
        assertFalse(entityPanel.isDefault());
    }
}
