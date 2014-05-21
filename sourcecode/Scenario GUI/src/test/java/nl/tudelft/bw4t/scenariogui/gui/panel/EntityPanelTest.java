package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.Component;

import javax.swing.JOptionPane;


import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Created on 14-5-2014.
 */
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
    public final void testBotCount() {
        Object[] data = {"d1", "d2", "d3"};

        spyEntityPanel.getBotTableModel().addRow(data);
        spyEntityPanel.updateEntitiesCount();

        assertEquals(spyEntityPanel.getBotCount(), 1);
    }

    /**
     * Test if the count of E-partners is correct after adding
     * an E-partner to the table.
     */
    @Test
    public final void testEPartnerCount() {
        Object[] data = {"D1", "D2", "D3"};

        spyEntityPanel.getEPartnerTableModel().addRow(data);
        spyEntityPanel.updateEntitiesCount();

        assertEquals(spyEntityPanel.getEPartnerCount(), 1);
    }

    /**
     * Test if a bot is successfully added when the add
     * bot button is clicked.
     */
    @Test
    public void testAddNewBot() {
        spyEntityPanel.getNewBotButton().doClick();
        verify(spyEntityPanel, times(1)).addBotAction();
        assertEquals(spyEntityPanel.getBotCount(), 1);
    }

    /**
     * Test if a bot is successfully modified when the
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
        doReturn(JOptionPane.YES_OPTION).when(spyEntityPanel).
            showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());

        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();

        /* Select that bot */
        spyEntityPanel.getBotTable().selectAll();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteBotButton().doClick();
        verify(spyEntityPanel, times(1)).deleteBotAction();

        /* check if the bot count is zero */
        assertEquals(spyEntityPanel.getBotCount(), 0);
    }

    /**
     * Test if a bot is not deleted when the
     * delete bot is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteBotDeclineDelete() {
        doReturn(JOptionPane.NO_OPTION).when(spyEntityPanel).
            showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());


        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();

        /* Select that bot */
        spyEntityPanel.getBotTable().selectAll();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteBotButton().doClick();
        verify(spyEntityPanel, times(1)).deleteBotAction();

        /* check if the bot count is still 1. */
        assertEquals(spyEntityPanel.getBotCount(), 1);
    }


    /**
     * Test if a bot is not deleted when the
     * delete bot is clicked, while no row is selected.
     */
    @Test
    public void testDeleteBotNoSelection() {
        doReturn(JOptionPane.NO_OPTION).when(spyEntityPanel).
                showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());

        /* Add a bot to the list */
        spyEntityPanel.getNewBotButton().doClick();

        /* Attempt to delete it */
        spyEntityPanel.getDeleteBotButton().doClick();
        verify(spyEntityPanel, times(1)).deleteBotAction();

        /* check if the bot count is still 1. */
        assertEquals(spyEntityPanel.getBotCount(), 1);
    }
    
    /**
     * Test if an E-partner is successfully added when the add
     * E-partner button is clicked.
     */
    @Test
    public void testAddEPartner() {
        spyEntityPanel.getNewEPartnerButton().doClick();
        verify(spyEntityPanel, times(1)).addEPartnerAction();
        assertEquals(spyEntityPanel.getEPartnerCount(), 1);
    }
    
    /**
     * Test if an E-partner is successfully modified when the
     * modify E-partner button is clicked.
     */
    @Test
    public void testModifyEPartner() {
        spyEntityPanel.getModifyEPartnerButton().doClick();
        verify(spyEntityPanel, times(1)).modifyEPartnerAction();
        //TODO: Verify if the E-partner has actually been modified.
    }
    
    /**
     * Test if an E-partner is actually deleted when the
     * delete E-partner is clicked, and the YES option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteEPartnerConfirmDelete() {
        doReturn(JOptionPane.YES_OPTION).when(spyEntityPanel)
            .showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());
        
        /** Add an E-partner to the list */
        spyEntityPanel.getNewEPartnerButton().doClick();
        
        /** Select that E-partner */
        spyEntityPanel.getEPartnerTable().selectAll();

        /** Attempt to delete it */
        spyEntityPanel.getDeleteEPartnerButton().doClick();
        verify(spyEntityPanel, times(1)).deleteEPartnerAction();
        
        /** Check if the E-partner count is zero */
        assertEquals(spyEntityPanel.getBotCount(), 0);
    }

    /**
     * Test if a E-partner is not deleted when the
     * delete E-partner is clicked, and the NO option is
     * chosen on the subsequently shown confirmation dialog.
     */
    @Test
    public void testDeleteEPartnerDeclineDelete() {
        doReturn(JOptionPane.NO_OPTION).when(spyEntityPanel)
            .showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());
        
        /** Add an E-partner to the list */
        spyEntityPanel.getNewEPartnerButton().doClick();
        
        /** Select that E-partner */
        spyEntityPanel.getEPartnerTable().selectAll();
        
        /** Attempt to delete it */
        spyEntityPanel.getDeleteEPartnerButton().doClick();
        verify(spyEntityPanel, times(1)).deleteEPartnerAction();
        
        /** Check if the E-partner count is still one */
        assertEquals(spyEntityPanel.getEPartnerCount(), 1);
    }
    
    /**
     * Test if an E-partner is not deleted when the
     * delete E-partner button is clicked, while no row is selected.
     */
    @Test
    public void testDeleteEPartnerSelection() {
    	doReturn(JOptionPane.NO_OPTION).when(spyEntityPanel).
    		showConfirmDialog((Component) any(), anyString(), anyString(), anyInt());
    	
    	/** Add an E-partner to the list */
    	spyEntityPanel.getNewEPartnerButton().doClick();
    	
    	/** Attempt to delete it */
    	spyEntityPanel.getDeleteEPartnerButton().doClick();
    	verify(spyEntityPanel, times(1)).deleteEPartnerAction();
    	
    	/** Check if the E-partner count is still one */
    	assertEquals(spyEntityPanel.getEPartnerCount(), 1);
    }
}
