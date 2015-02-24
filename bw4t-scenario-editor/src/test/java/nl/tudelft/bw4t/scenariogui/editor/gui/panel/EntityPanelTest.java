package nl.tudelft.bw4t.scenariogui.editor.gui.panel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EntityPanelTest {

    private EntityPanel entityPanel;
    private EntityPanel spyEntityPanel;
    private ScenarioEditor editor;

    @Before
    public final void setUp() {
        entityPanel = new EntityPanel();
        spyEntityPanel = spy(entityPanel);
        editor = new ScenarioEditor(new ConfigurationPanel(),
                spyEntityPanel, new BW4TClientConfig());
    }

    @After
    public final void tearDown() {
        editor.dispose();
    }

    @Test
    public final void testAddBot() {
        spyEntityPanel.getNewBotButton().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testBotCountListener() {
        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getBotTableModel().setValueAt("12", 0, 3);

        assertEquals(12, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddEpartner() {
        spyEntityPanel.getNewEPartnerButton().doClick();

        assertEquals(1, editor.getController().getModel().getAmountEPartner());
    }

    @Test
    public final void testEPartnerCountListener() {
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(yesMockOption);

        spyEntityPanel.getNewEPartnerButton().doClick();
        spyEntityPanel.getEPartnerTableModel().setValueAt("12", 0, 2);

        assertEquals(12, editor.getController().getModel().getAmountEPartner());
    }

    @Test
    public void testChangeBotName() {
        spyEntityPanel.getNewBotButton().doClick();

        assertEquals("Bot 1", editor.getController().getModel().getBot(0).getBotName());

        String testBotName = "testBotName";
        spyEntityPanel.getBotTableModel().setValueAt(testBotName, 0, 0);

        assertEquals(testBotName, editor.getController().getModel().getBot(0).getBotName());
    }

    @Test
    public void testChangeFileNameBot() {
        spyEntityPanel.getNewBotButton().doClick();

        assertEquals("robot.goal", editor.getController().getModel().getBot(0).getFileName());

        String testFileName = "testRobot.goal";
        spyEntityPanel.getBotTableModel().setValueAt(testFileName, 0, 2);

        assertEquals(testFileName, editor.getController().getModel().getBot(0).getFileName());
    }

    @Test
    public void testChangeBotNumber() {
        spyEntityPanel.getNewBotButton().doClick();

        assertEquals(1, editor.getController().getModel().getBot(0).getBotAmount());

        int testNumber = 5;
        spyEntityPanel.getBotTableModel().setValueAt(testNumber, 0, 3);

        assertEquals(testNumber, editor.getController().getModel().getBot(0).getBotAmount());
    }

    @Test
    public void testChangeBotType() {
        spyEntityPanel.getNewBotButton().doClick();

        assertEquals(EntityType.AGENT, editor.getController().getModel().getBot(0).getBotController());

        EntityType testType = EntityType.HUMAN;
        spyEntityPanel.getBotTableModel().setValueAt(testType, 0, 1);

        assertEquals(testType, editor.getController().getModel().getBot(0).getBotController());
    }

    @Test
    public void testModifyBot() {
        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getBotTable().setRowSelectionInterval(0, 0);
        spyEntityPanel.getModifyBotButton().doClick();
        assertTrue(spyEntityPanel.isBotStore());
    }

    @Test
    public void testModifyBotNoSelection() {
        OptionPrompt spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);

        spyEntityPanel.getModifyBotButton().doClick();

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
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getYesOptionPrompt());

        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getBotTable().setRowSelectionInterval(0, 0);
        spyEntityPanel.getDeleteBotButton().doClick();

        assertEquals(0, editor.getController().getModel().getAmountBot());
    }

    /**
     * Test if a bot is not deleted when the
     * delete bot is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteBotDeclineDelete() {
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getNoOptionPrompt());

        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getBotTable().setRowSelectionInterval(0, 0);
        spyEntityPanel.getDeleteBotButton().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }


    /**
     * Test if a bot is not deleted when delete bot is clicked
     * while no bot is selected.
     */
    @Test
    public void testDeleteBotNoSelection() {
        OptionPrompt spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);

        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getDeleteBotButton().doClick();

        assertEquals(editor.getController().getModel().getAmountBot(), 1);
        verify(spyOption, times(1)).showMessageDialog(null, "Please select the bot you want to delete.");
    }

    @Test
    public void testChangeEPartnerName() {
        spyEntityPanel.getNewEPartnerButton().doClick();

        assertEquals("E-Partner 1", editor.getController().getModel().getEpartner(0).getEpartnerName());

        String testEPartnerName = "testEPartnerName";
        spyEntityPanel.getEPartnerTableModel().setValueAt(testEPartnerName, 0, 0);

        assertEquals(testEPartnerName, editor.getController().getModel().getEpartner(0).getEpartnerName());
    }

    @Test
    public void testChangeFileNameEPartner() {
        spyEntityPanel.getNewEPartnerButton().doClick();

        assertEquals("epartner.goal", editor.getController().getModel().getEpartner(0).getFileName());

        String testFileName = "testEpartner.goal";
        spyEntityPanel.getEPartnerTableModel().setValueAt(testFileName, 0, 1);

        assertEquals(testFileName, editor.getController().getModel().getEpartner(0).getFileName());
    }

    @Test
    public void testChangeEpartnerNumber() {
        spyEntityPanel.getNewEPartnerButton().doClick();

        assertEquals(1, editor.getController().getModel().getEpartner(0).getEpartnerAmount());

        int testNumber = 5;
        spyEntityPanel.getEPartnerTableModel().setValueAt(testNumber, 0, 2);

        assertEquals(testNumber, editor.getController().getModel().getEpartner(0).getEpartnerAmount());
    }

    @Test
    public void testModifyEPartner() {
        spyEntityPanel.getNewEPartnerButton().doClick();
        spyEntityPanel.getEPartnerTable().setRowSelectionInterval(0, 0);
        spyEntityPanel.getModifyEPartnerButton().doClick();
        assertTrue(spyEntityPanel.isEpartnerStore());
    }

    /**
     * Test if a warning is shown when the modify E-partner button is clicked
     * while there are no epartners selected.
     */
    @Test
    public void testModifyEPartnerNoSelection() {
        OptionPrompt spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);

        spyEntityPanel.getModifyEPartnerButton().doClick();

        verify(spyOption, times(1)).showMessageDialog(
                null, "Please select the E-partner you want to modify.");
    }

    /**
     * Test if a pop-up is generated notifying the user that they made no selection
     * when they try to modify an epartner without first selecting one.
     */
    @Test
    public void testEPartnerModifyNoSelection() {
        OptionPrompt spyOption = OptionPromptHelper.getYesOptionPrompt();
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
        spyEntityPanel.getNewEPartnerButton().doClick();
        spyEntityPanel.getEPartnerTable().setRowSelectionInterval(0, 0);
        spyEntityPanel.getDeleteEPartnerButton().doClick();

        assertEquals(editor.getController().getModel().getAmountBot(), 0);
    }

    /**
     * Test if a E-partner is not deleted when the
     * delete E-partner is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteEPartnerDeclineDelete() {
        //deal with the dialog that shows up when there's more epartners than bots
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getNoOptionPrompt());

        spyEntityPanel.getNewEPartnerButton().doClick();
        spyEntityPanel.getEPartnerTable().setRowSelectionInterval(0, 0);
        spyEntityPanel.getDeleteEPartnerButton().doClick();

        assertEquals(editor.getController().getModel().getAmountEPartner(), 1);
    }

    @Test
    public void testDeleteEPartnerSelection() {
        spyEntityPanel.getNewEPartnerButton().doClick();
        spyEntityPanel.getDeleteEPartnerButton().doClick();

        assertEquals(editor.getController().getModel().getAmountEPartner(), 1);
    }

    @Test
    public final void testAddStandardBot() {
        spyEntityPanel.getAddNewStandardBotMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardBotBig() {
        spyEntityPanel.getAddNewStandardBotBigMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardGripperBot() {
        spyEntityPanel.getAddNewStandardBotGripperMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardBotBigGripper() {
        spyEntityPanel.getAddNewStandardBotBigGripperMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardBotSeeer() {
        spyEntityPanel.getAddNewStandardBotSeeerMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardBotBigSeeer() {
        spyEntityPanel.getAddNewStandardBotBigSeeerMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardBotCommunicator() {
        spyEntityPanel.getAddNewStandardBotCommunicatorMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    @Test
    public final void testAddStandardBotBigCommunicator() {
        spyEntityPanel.getAddNewStandardBotBigCommunicatorMenuItem().doClick();

        assertEquals(1, editor.getController().getModel().getAmountBot());
    }

    public void testDefault() {
        assertTrue(entityPanel.isDefault());
    }

    @Test
    public void testBotnonDefault() {
        spyEntityPanel.getNewBotButton().doClick();

        assertFalse(entityPanel.isDefault());
    }

    @Test
    public void testEpartnernonDefault() {
        spyEntityPanel.getNewEPartnerButton().doClick();

        assertFalse(entityPanel.isDefault());
    }

    @Test
    public void testBotAndEpartnernonDefault() {
        spyEntityPanel.getNewBotButton().doClick();
        spyEntityPanel.getNewEPartnerButton().doClick();

        assertFalse(entityPanel.isDefault());
    }

    @Test
    public void testBotTableUpdate() {
        spyEntityPanel.getNewBotButton().doClick();

        assertEquals("Bot 1", spyEntityPanel.getBotTableModel().getValueAt(0, 0));
        assertEquals(EntityType.AGENT.toString(), spyEntityPanel
                .getBotTableModel().getValueAt(0, 1));
        assertEquals("robot.goal", spyEntityPanel.getBotTableModel().getValueAt(0, 2));
        assertEquals(1, spyEntityPanel.getBotTableModel().getValueAt(0, 3));

        BotEditor botEditor = new BotEditor(editor.getMainPanel(), 0,
                editor.getController().getModel());
        BotEditorPanel botEditorPanel = botEditor.getBotEditorPanel();

        botEditorPanel.getBotNameField().setText("TestBot");
        botEditorPanel.getBotControllerSelector().setSelectedIndex(1);
        botEditorPanel.getBotAmountTextField().setText("99");
        botEditorPanel.getFileNameField().setText("TestBot.goal");

        botEditorPanel.getSaveButton().doClick();

        assertEquals("TestBot", spyEntityPanel.getBotTableModel().getValueAt(0, 0));
        assertEquals(EntityType.HUMAN.toString(), spyEntityPanel
                .getBotTableModel().getValueAt(0, 1));
        assertEquals("TestBot.goal", spyEntityPanel.getBotTableModel().getValueAt(0, 2));
        assertEquals(99, spyEntityPanel.getBotTableModel().getValueAt(0, 3));
    }

    @Test
    public void testEpartnerTableUpdate() {
        //deal with the dialog that shows up when there's more epartners than bots
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getNoOptionPrompt());

        spyEntityPanel.getNewEPartnerButton().doClick();

        assertEquals("E-Partner 1", spyEntityPanel.getEPartnerTableModel()
                .getValueAt(0, 0));
        assertEquals("epartner.goal", spyEntityPanel.getEPartnerTableModel().getValueAt(0, 1));
        assertEquals(1, spyEntityPanel.getEPartnerTableModel().getValueAt(0, 2));

        EpartnerFrame epartnerFrame = new EpartnerFrame(new EpartnerController(
                editor.getMainPanel(), 0));

        epartnerFrame.getEpartnerNameField().setText("TestEPartner");
        epartnerFrame.getEpartnerAmountField().setText("99");
        epartnerFrame.getEpartnerGoalFileField().setText("TestEPartner.goal");

        epartnerFrame.getSaveButton().doClick();

        assertEquals("TestEPartner", spyEntityPanel.getEPartnerTableModel()
                .getValueAt(0, 0));
        assertEquals("TestEPartner.goal", spyEntityPanel.getEPartnerTableModel().getValueAt(0, 1));
        assertEquals(99, spyEntityPanel.getEPartnerTableModel().getValueAt(0, 2));
    }
}
