package nl.tudelft.bw4t.scenariogui.editor.gui.panel;

import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.DefaultConfigurationValues;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
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

public class ConfigurationPanelTest {

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    private final String filePathMap = BASE + "test.map";

    private final String fileMapFail = BASE + "test.fail";

    private ScenarioEditor editor;

    private ConfigurationPanel configPanel;

    private JFileChooser fileChooser;

    /**
     * Initialize the variables.
     */
    @Before
    public final void setUp() {
        // Mock the JFileChooser
        fileChooser = mock(JFileChooser.class);
        configPanel = new ConfigurationPanel();

        editor = new ScenarioEditor(configPanel, new EntityPanel(), new BW4TClientConfig());

        // Set the fileChooser used to the mocked one.
        configPanel.setFileChooser(fileChooser);
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
        configPanel.setMapFile(filePathMap);

        assertEquals(filePathMap, configPanel.getMapFile());
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
        configPanel.getChooseMapFile().doClick();

        /* Replace the backward slashes with forward slashes */
        assertEquals(mapFile.getAbsolutePath(), configPanel
                .getMapFile());
    }

    /**
     * Tests if the choosing of an incorrect map file
     * shows a pop-up message with an error.
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
        configPanel.getChooseMapFile().doClick();

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
        String startURI = configPanel.getMapFile();

        // Trigger the event.
        configPanel.getChooseMapFile().doClick();

        assertEquals(startURI, configPanel.getMapFile());
    }

    /**
     * Tests whether the old values are updated correctly.
     */
    @Test
    public void testGetOldValues() {
        assertTrue(configPanel
                .getOldValues().equals(editor.getMainPanel()
                        .getConfigurationPanel()
                        .getCurrentValues()));

        //check when values change
        configPanel.setClientIP("Other IP");

        assertFalse(configPanel
                .getOldValues().equals(editor.getMainPanel()
                        .getConfigurationPanel()
                        .getCurrentValues()));

        //update values
        configPanel.updateOldValues();

        assertTrue(configPanel
                .getOldValues().equals(editor.getMainPanel()
                        .getConfigurationPanel()
                        .getCurrentValues()));
    }

    /**
     * Tests the isDefault function.
     */
    @Test
    public void testDefault() {
        assertTrue(configPanel.isDefault());

    	configPanel.setClientIP("Other IP");
    	
        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests setting the Client IP field.
     */
    @Test
    public void testSetClientIP() {
        assertTrue(configPanel.isDefault());
    	assertEquals(DefaultConfigurationValues.DEFAULT_CLIENT_IP.getValue(), configPanel.getClientIP());
    	
    	String newIP = "New IP";
        configPanel.setClientIP(newIP);

        assertEquals(newIP, configPanel.getClientIP());
        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests setting the Client Port field.
     */
    @Test
    public void testSetClientPort() {
        assertTrue(configPanel.isDefault());
    	assertEquals(DefaultConfigurationValues.DEFAULT_CLIENT_PORT.getValue(), "" + configPanel.getClientPort());
    	
    	String newIP = "8888";
        configPanel.setClientPort(newIP);
        
        assertEquals(newIP, "" + configPanel.getClientPort());
        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests setting the Server IP field.
     */
    @Test
    public void testSetServerIP() {
        assertTrue(configPanel.isDefault());
    	assertEquals(DefaultConfigurationValues.DEFAULT_SERVER_IP.getValue(), configPanel.getServerIP());
    	
    	String newIP = "New IP";
        configPanel.setServerIP(newIP);

        assertEquals(newIP, configPanel.getServerIP());
        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests setting the Server Port field.
     */
    @Test
    public void testSetServerPort() {
        assertTrue(configPanel.isDefault());
    	assertEquals(DefaultConfigurationValues.DEFAULT_SERVER_PORT.getValue(), "" + configPanel.getServerPort());
    	
    	String newPort = "9999";
        configPanel.setServerPort(newPort);

        assertEquals(newPort, "" + configPanel.getServerPort());
        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Launch GUI section.
     */
    @Test
    public void testChangesLaunchGUI() {
        assertTrue(configPanel.isDefault());
    	assertEquals(DefaultConfigurationValues.USE_GUI.getBooleanValue(), configPanel.getGUIYesCheckbox().getState());
    	assertEquals(!DefaultConfigurationValues.USE_GUI.getBooleanValue(), configPanel.getGUINoCheckbox().getState());
    	
        configPanel.setUseGui(!DefaultConfigurationValues.USE_GUI.getBooleanValue());

        assertEquals(!DefaultConfigurationValues.USE_GUI.getBooleanValue(), configPanel.getGUIYesCheckbox().getState());
        assertEquals(DefaultConfigurationValues.USE_GUI.getBooleanValue(), configPanel.getGUINoCheckbox().getState());
        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Map file section.
     */
    @Test
    public void testChangesMapFile() {
        assertTrue(configPanel.isDefault());
        assertEquals(DefaultConfigurationValues.MAP_FILE.getValue(), configPanel.getMapFile());
        
        configPanel.setMapFile(filePathMap);

        assertEquals(filePathMap, configPanel.getMapFile());
        assertFalse(configPanel.isDefault());
    }
}
