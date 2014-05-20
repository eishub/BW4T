package nl.tudelft.bw4t.scenariogui.gui.panel;

import static org.mockito.Mockito.spy;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

import org.junit.Before;

/**
 * Created on 14-5-2014.
 */
public class BotPanelTest {

    /** The bot panel of the GUI. */
    private BotPanel botPanel;
    /** A spy object of the bot panal of the GUI. */
    private BotPanel spyBotPanel;

    /**
     * Initializes the panel and GUI.
     */
    @Before
    public final void setUp() {
        botPanel = new BotPanel();
        spyBotPanel = spy(botPanel);

        ConfigurationPanel config = new ConfigurationPanel();
        /*
         * The editor itself isn't used. It's simple so the BotPanel gets
         * handled by a controller.
         */
        new ScenarioEditor(config, spyBotPanel);
    }

    /*
     * @Test public void testAddNewBot() { spyBotPanel.getNewBot().doClick();
     * verify(spyBotPanel, times(1)).addNewAction();
     * has actually been added. }
     *
     * @Test public void testModifyBot() { spyBotPanel.getModifyBot().doClick();
     * verify(spyBotPanel, times(1)).modifyAction(); //TODO: Verify if the bot
     * has actually been modified. }
     *
     * @Test public void testRenameBotInvalid() {
     * Mockito.doNothing().when(spyBotPanel
     * ).showMessageDialog((java.awt.Component) any(), anyString());
     *
     * spyBotPanel.getRenameBot().doClick(); verify(spyBotPanel,
     * times(1)).renameAction(); verify(spyBotPanel,
     * times(1)).showMessageDialog((java.awt.Component) any(), anyString()); }
     */
    /*
     * @Test public void testRenameBotValid() {
     * Mockito.doNothing().when(spyBotPanel
     * ).showMessageDialog((java.awt.Component) any(), anyString());
     *
     * String botname = "Quandesha";
     * spyBotPanel.setNewBotNameLabelText(botname);
     *
     * spyBotPanel.getRenameBot().doClick(); verify(spyBotPanel,
     * times(1)).renameAction();
     */
    /* Make sure no message dialog is created since nothing went wrong */
    /*
     * verify(spyBotPanel, times(0)).showMessageDialog((java.awt.Component)
     * any(), anyString());
     *
     * assertEquals(botname, spyBotPanel.getNewBotNameLabelText()); //TODO:
     * Check if the name of the bot itself was actually changed. }
     */

    /*
     * @Test public void testDuplicateBotYes() {
     * doReturn(JOptionPane.YES_OPTION).when(spyBotPanel)
     * .showConfirmDialog((Component) any(), anyString(), anyString(),
     * anyInt());
     *
     * spyBotPanel.getDuplicateBot().doClick(); verify(spyBotPanel,
     * times(1)).duplicateAction(); verify(spyBotPanel,
     * times(1)).showConfirmDialog((java.awt.Component) any(), anyString(),
     * anyString(), anyInt()); //TODO: Test if actually duplicated }
     *
     * @Test public void testDuplicateBotNo() {
     * doReturn(JOptionPane.NO_OPTION).when(spyBotPanel)
     * .showConfirmDialog((Component) any(), anyString(), anyString(),
     * anyInt());
     *
     * spyBotPanel.getDuplicateBot().doClick(); verify(spyBotPanel,
     * times(1)).duplicateAction(); verify(spyBotPanel,
     * times(1)).showConfirmDialog((java.awt.Component) any(), anyString(),
     * anyString(), anyInt());
     *
     * //TODO: Test if NOT duplicated }
     *
     * @Test public void testDeleteBotYes() {
     * doReturn(JOptionPane.YES_OPTION).when(spyBotPanel)
     * .showConfirmDialog((Component) any(), anyString(), anyString(),
     * anyInt());
     *
     * spyBotPanel.getDeleteBot().doClick(); verify(spyBotPanel,
     * times(1)).deleteAction(); verify(spyBotPanel,
     * times(1)).showConfirmDialog((java.awt.Component) any(), anyString(),
     * anyString(), anyInt()); //TODO: Test if actually deleted }
     *
     * @Test public void testDeleteBotNo() {
     * doReturn(JOptionPane.NO_OPTION).when(spyBotPanel)
     * .showConfirmDialog((Component) any(), anyString(), anyString(),
     * anyInt());
     *
     * spyBotPanel.getDeleteBot().doClick(); verify(spyBotPanel,
     * times(1)).deleteAction(); verify(spyBotPanel,
     * times(1)).showConfirmDialog((java.awt.Component) any(), anyString(),
     * anyString(), anyInt());
     *
     * //TODO: Test if not deleted }
     */
}