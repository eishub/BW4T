package nl.tudelft.bw4t.scenariogui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.table.TableCellRenderer;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class EntityJTableTest {

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    private static final String FILE_TEST_ROBOT_GOAL = BASE + "robot.goal";

    private EntityPanel entityPanel;
    
    private EntityJTable botJTable;
    
    private EntityJTable ePartnerTable;
    
    private JFileChooser filechooser;

    private EntityPanel spyEntityPanel;

    private ScenarioEditor editor;

    /**
     * Initializes the panel and GUI.
     */
    @Before
    public final void setUp() {
        filechooser = mock(JFileChooser.class);
        entityPanel = new EntityPanel();
        spyEntityPanel = spy(entityPanel);
        editor = new ScenarioEditor(new ConfigurationPanel(),
                spyEntityPanel, new BW4TClientConfig());
        botJTable = entityPanel.getBotTable();
        ePartnerTable = entityPanel.getEPartnerTable();
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
     * Tests whether the tooltip is displayed at the right time
     */
    @Test
    public void getToolTipTextTest() {
        entityPanel.getNewBotButton().doClick();
        assertEquals(botJTable.getToolTipText(0, 0), null);
        assertEquals(botJTable.getToolTipText(0, 1), null);
        assertFalse(botJTable.getToolTipText(0, 2) == null);
        assertEquals(botJTable.getToolTipText(0, 3), null);
    }
    
    /**
     * Checks whether the correct agent file column index is returned for a table
     */
    @Test
    public void getAgentFileColumnTest() {       
        assertEquals(botJTable.getAgentFileColumn(), 2);
        assertEquals(ePartnerTable.getAgentFileColumn(), 1);
    }
    /**
     * Checks whether the getAgentFileColumn handles a null pointer exception
     */
    @Test
    public void getAgentFileColumnExceptionTest1(){
        YesMockOptionPrompt yesMockOption = spy(new YesMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(yesMockOption);
        
        EntityJTable errorTable = new EntityJTable();
        errorTable.getAgentFileColumn();        
        
        verify(yesMockOption, times(1)).showMessageDialog((Component) any(), anyObject());
    }   
    
    /**
     * Checks whether the getAgentFileColumn method handles the index out of bounds exception
     */
    @Test
    public void getAgentFileColumnExceptionTest2(){
        YesMockOptionPrompt yesMockOption = spy(new YesMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(yesMockOption);
        
        EntityJTable errorTable = new EntityJTable();
        errorTable.setName("InvalidName");
        errorTable.getAgentFileColumn();
        
        verify(yesMockOption, times(1)).showMessageDialog((Component) any(), anyObject());
    }
}
