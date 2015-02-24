package nl.tudelft.bw4t.scenariogui.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.awt.Color;
import java.awt.Component;
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


public class EntityTableCellRendererTest {

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
     * Checks whether the correct color is displayed when a file name exists or doesn't exist in the bot table. 
     */
    @Test
    public void getTableCellRendererComponentBotTableTest() {
        
        entityPanel.getNewBotButton().doClick();      
        entityPanel.getBotTableModel().setValueAt(FILE_TEST_ROBOT_GOAL, 0, 2);
        assertTrue(getTableCellForeground(botJTable, 0, 2).equals(Color.BLACK));
        
        entityPanel.getBotTableModel().setValueAt("Somenonexistingfile.goal", 0, 2);
        assertTrue(getTableCellForeground(botJTable, 0, 2).equals(Color.RED));
        
    }

    /**
     * Checks whether the correct color is displayed when a file name exists or doesn't exist in the epartner table. 
     */
    @Test
    public void getTableCellRendererComponentEPartnerTableTest() {
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(yesMockOption);
        
        entityPanel.getNewEPartnerButton().doClick();      
        entityPanel.getEPartnerTableModel().setValueAt(FILE_TEST_ROBOT_GOAL, 0, 1);
        assertTrue(getTableCellForeground(ePartnerTable, 0, 1).equals(Color.BLACK));
        
        entityPanel.getEPartnerTableModel().setValueAt("Somenonexistingfile.goal", 0, 1);
        assertTrue(getTableCellForeground(ePartnerTable, 0, 1).equals(Color.RED));
        
    }
    
    /**
     * Tests whether the file name exist function works as it should.    
     */
    @Test
    public void fileNameExistsTest() {
        File f = new File(FILE_TEST_ROBOT_GOAL);
        File f2 = new File(BASE + "test_robot2.goal");
        assertTrue(f.exists());
        assertFalse(f2.exists());
    }
    
    /**
     * Used to get the foreground color of a specific cell in the bot table. 
     * 
     * @param table The table the cell is in.
     * @param row The row the cell is in.
     * @param col The column the cell is in.
     * @return The foreground color of the cell.
     */
    public Color getTableCellForeground(EntityJTable table, int row, int col) {
        TableCellRenderer renderer = table.getCellRenderer(row, col);
        Component component = table.prepareRenderer(renderer, row, col);    
        return component.getForeground();
    }
    
}
