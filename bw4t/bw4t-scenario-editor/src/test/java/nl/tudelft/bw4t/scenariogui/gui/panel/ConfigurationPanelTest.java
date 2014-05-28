package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.util.YesMockOptionPrompt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>
 * @author        
 * @version     0.1                
 * @since       13-05-2014        
 */
public class ConfigurationPanelTest {

    /**
     * The main GUI.
     */
    private ScenarioEditor editor;
    /**
     * The base directory of all files used in the test.
     */
    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";
    /**
     * The file that has to be opened.
     */
    private final String filePathMap = BASE + "test.map";
    /**
     * The selected file that cannot be opened.
     */
    private final String fileMapFail = BASE + "test.fail";
    /**
     * The file chooser object.
     */
    private JFileChooser fileChooser;

    /**
     * Initialized the variables.
     */
    @Before
    public final void setUp() {
        // Mock the JFileChooser
        fileChooser = mock(JFileChooser.class);

        editor = new ScenarioEditor();

        // Set the fileChooser used to the mocked one.
        editor.getMainPanel().getConfigurationPanel()
                .setFileChooser(fileChooser);
    }

    /**
     * Close the ScenarioEditor to prevent to many windows from cluttering
     * the screen during the running of the tests
     */
    @After
    public final void closeEditor() {
        editor.dispose();
    }

    /**
     * Tests the setMapFile method.
     */
    @Test
    public final void testSetMapFile() {
        editor.getMainPanel().getConfigurationPanel().setMapFile(filePathMap);

        assertEquals(filePathMap, editor.getMainPanel().getConfigurationPanel()
                .getMapFile());
    }

    /**
     * Tests the choosing of a map file.
     */
    @Test
    public final void testMapFileAction() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(
                JFileChooser.APPROVE_OPTION);

        File mapFile = new File(filePathMap);

        when(fileChooser.getSelectedFile()).thenReturn(mapFile);

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseMapFile()
                .doClick();

        /* Replace the backward slashes with forward slashes */
        assertEquals(mapFile.getAbsolutePath(), editor.getMainPanel().getConfigurationPanel()
                .getMapFile());
    }

    /**
     * Tests the choosing of an incorrect map file.
     * Shows a pop-up message with an error.
     */
    @Test
    public final void testMapFileActionIncorrect() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(
                JFileChooser.APPROVE_OPTION);
        when(fileChooser.getSelectedFile()).thenReturn(new File(fileMapFail));

        YesMockOptionPrompt spyOption = spy(new YesMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseMapFile()
                .doClick();

        // Verify that the pop-up message was shown.
        verify(spyOption, times(1)).showMessageDialog(editor.getMainPanel(), "This is not a valid file.");
    }

    /**
     * Tests whether the map file can be selected.
     */
    @Test
    public final void testMapFileActionBranch() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(1);
        when(fileChooser.getSelectedFile()).thenReturn(new File("ss"));

        // Original value
        String startURI = editor.getMainPanel().getConfigurationPanel()
                .getMapFile();

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseMapFile()
                .doClick();

        assertEquals(startURI, editor.getMainPanel().getConfigurationPanel()
                .getMapFile());
    }

    /**
     * Tests whether the old values are updated correctly.
     */
    @Test
    public void testGetOldValues() {
        assertTrue(editor.getMainPanel().getConfigurationPanel()
                .getOldValues().equals(editor.getMainPanel()
                        .getConfigurationPanel()
                        .getCurrentValues()));

        //check when values change
        editor.getMainPanel().getConfigurationPanel().setClientIP("Other IP");

        assertFalse(editor.getMainPanel().getConfigurationPanel()
                .getOldValues().equals(editor.getMainPanel()
                        .getConfigurationPanel()
                        .getCurrentValues()));

        //update values
        editor.getMainPanel().getConfigurationPanel().updateOldValues();

        assertTrue(editor.getMainPanel().getConfigurationPanel()
                .getOldValues().equals(editor.getMainPanel()
                        .getConfigurationPanel()
                        .getCurrentValues()));
    }

    /**
     * Tests the isDefault function.
     */
    @Test
    public void testDefault() {
    	assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
    }
    
    /**
     * Tests setting the Client IP field.
     */
    @Test
    public void testSetClientIP() {
    	editor.getMainPanel().getConfigurationPanel().setClientIP("New IP");

        assertEquals("New IP", editor.getMainPanel().getConfigurationPanel()
                .getClientIP());
    }
    
    /**
     * Tests setting the Client Port field.
     */
    @Test
    public void testSetClientPort() {
    	editor.getMainPanel().getConfigurationPanel().setClientPort("8888");
    	
    	String res = "" + editor.getMainPanel().getConfigurationPanel().getClientPort();

    	assertEquals("8888", res);
    }
    
    /**
     * Tests setting the Server IP field.
     */
    @Test
    public void testSetServerIP() {
    	editor.getMainPanel().getConfigurationPanel().setServerIP("New IP");

        assertEquals("New IP", editor.getMainPanel().getConfigurationPanel()
                .getServerIP());
    }
    
    /**
     * Tests setting the Server Port field.
     */
    @Test
    public void testSetServerPort() {
    	editor.getMainPanel().getConfigurationPanel().setServerPort("9999");
    	
    	String res = "" + editor.getMainPanel().getConfigurationPanel().getServerPort();

        assertEquals("9999", res);
    }
    
    /**
     * Tests whether changes have been made to the Client IP field.
     */
    @Test
    public void testChangesClientIP() {
    	editor.getMainPanel().getConfigurationPanel().setClientIP("New IP");
    	
    	assertEquals(false, editor.getMainPanel().getConfigurationPanel().isDefault());
    }
    
    /**
     * Tests whether changes have been made to the Client Port field.
     */
    @Test
    public void testChangesClientPort() {
    	editor.getMainPanel().getConfigurationPanel().setClientPort("7777");
    	
    	assertEquals(false, editor.getMainPanel().getConfigurationPanel().isDefault());
    }
    
    /**
     * Tests whether changes have been made to the Server IP field.
     */
    @Test
    public void testChangesServerIP() {
    	editor.getMainPanel().getConfigurationPanel().setServerIP("New IP");
    	
    	assertEquals(false, editor.getMainPanel().getConfigurationPanel().isDefault());
    }
    
    /**
     * Tests whether changes have been made to the Server Port field.
     */
    @Test
    public void testChangesServerPort() {
    	editor.getMainPanel().getConfigurationPanel().setServerPort("1010");
    	
    	assertEquals(false, editor.getMainPanel().getConfigurationPanel().isDefault());
    }
    
    /**
     * Tests whether changes have been made to the Launch GUI section.
     */
    @Test
    public void testChangesLaunchGUI() {
    	editor.getMainPanel().getConfigurationPanel().setUseGui(false);
    	
    	assertEquals(false, editor.getMainPanel().getConfigurationPanel().isDefault());
    }
    
    /**
     * Tests whether changes have been made to the Map file section.
     */
    @Test
    public void testChangesMapFile() {
    	editor.getMainPanel().getConfigurationPanel().setMapFile(filePathMap);
    	
    	assertEquals(false, editor.getMainPanel().getConfigurationPanel().isDefault());
    }
}
