package nl.tudelft.bw4t.scenariogui.gui.panel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 14-5-2014.
 */
public class EntityPanelTest {

    /** The entity panel of the GUI. */
    private EntityPanel entityPanel;
    /** A spy object of the entity panel of the GUI. */
    private EntityPanel spyEntityPanel;

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
        new ScenarioEditor(config, spyEntityPanel);
    }

    /**
     * Test if the count of bots is correct after adding a bot
     * to the table.
     */
    @Test
    public void testBotCount() {
        Object[] data = {"D1", "D2", "D3"};

        spyEntityPanel.getBotTable().addRow(data);
        spyEntityPanel.updateEntitiesCount();

        assertEquals(spyEntityPanel.getBotCount(), 1);
    }

    /**
     * Test if the count of epartners is correct after adding
     * an epartner to the table.
     */
    @Test
    public void testEPartnerCount() {
        Object[] data = {"D1", "D2", "D3"};

        spyEntityPanel.getEPartnerTable().addRow(data);
        spyEntityPanel.updateEntitiesCount();

        assertEquals(spyEntityPanel.getEPartnerCount(), 1);
    }

    /**
     * Test if a bot is succesfully added when the add
     * bot button is clicked.
     */
     @Test
     public void testAddNewBot() {
         spyEntityPanel.getNewBotButton().doClick();
         verify(spyEntityPanel, times(1)).addBotAction();
         //TODO: Verify if the bot has been actually added.
    }

    /**
     * Test if a bot is succesfully modified when the
     * modify bot button is clicked.
     */
    @Test
    public void testModifyBot() {
        spyEntityPanel.getModifyBotButton().doClick();
        verify(spyEntityPanel, times(1)).modifyBotAction();
        //TODO: Verify if the bot has actually been modified.
    }

    /**
     * Test if a bot is actually deleted when the
     * delete bot is clicked, and the YES option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteBotConfirmDelete() {
        doReturn(JOptionPane.YES_OPTION).when(spyEntityPanel).showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());

        spyEntityPanel.getDeleteBotButton().doClick();
        verify(spyEntityPanel, times(1)).deleteBotAction();
        //TODO: Verify if the bot has actually been modified.
    }

    /**
     * Test if a bot is not deleted when the
     * delete bot is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteBotDeclineDelete() {
        doReturn(JOptionPane.NO_OPTION).when(spyEntityPanel).showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());

        spyEntityPanel.getDeleteBotButton().doClick();
        verify(spyEntityPanel, times(1)).deleteBotAction();
        //TODO: Verify if the bot has NOT been removed.
    }

}