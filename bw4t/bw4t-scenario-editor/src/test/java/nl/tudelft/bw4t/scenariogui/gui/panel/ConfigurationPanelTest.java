package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
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
 * @author Xander Zonneveld
 * @version 0.1
 * @since 13-05-2014
 */
public class ConfigurationPanelTest {

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
     * The main GUI.
     */
    private ScenarioEditor editor;
    /**
     * The configuration panel to be tested.
     */
    private ConfigurationPanel configPanel;
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

        assertEquals(filePathMap, configPanel
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
        configPanel.getChooseMapFile()
                .doClick();

        /* Replace the backward slashes with forward slashes */
        assertEquals(mapFile.getAbsolutePath(), configPanel
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
        configPanel.getChooseMapFile()
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
        String startURI = configPanel
                .getMapFile();

        // Trigger the event.
        configPanel.getChooseMapFile()
                .doClick();

        assertEquals(startURI, configPanel
                .getMapFile());
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
    }

    /**
     * Tests setting the Client IP field.
     */
    @Test
    public void testSetClientIP() {
        configPanel.setClientIP("New IP");

        assertEquals("New IP", configPanel
                .getClientIP());
    }

    /**
     * Tests setting the Client Port field.
     */
    @Test
    public void testSetClientPort() {
        configPanel.setClientPort("8888");

        String res = "" + configPanel.getClientPort();

        assertEquals("8888", res);
    }

    /**
     * Tests setting the Server IP field.
     */
    @Test
    public void testSetServerIP() {
        configPanel.setServerIP("New IP");

        assertEquals("New IP", configPanel
                .getServerIP());
    }

    /**
     * Tests setting the Server Port field.
     */
    @Test
    public void testSetServerPort() {
        configPanel.setServerPort("9999");

        String res = "" + configPanel.getServerPort();

        assertEquals("9999", res);
    }

    /**
     * Tests whether changes have been made to the Client IP field.
     */
    @Test
    public void testChangesClientIP() {
        configPanel.setClientIP("New IP");

        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Client Port field.
     */
    @Test
    public void testChangesClientPort() {
        configPanel.setClientPort("7777");

        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Server IP field.
     */
    @Test
    public void testChangesServerIP() {
        configPanel.setServerIP("New IP");

        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Server Port field.
     */
    @Test
    public void testChangesServerPort() {
        configPanel.setServerPort("1010");

        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Launch GUI section.
     */
    @Test
    public void testChangesLaunchGUI() {
        configPanel.setUseGui(false);

        assertFalse(configPanel.isDefault());
    }

    /**
     * Tests whether changes have been made to the Map file section.
     */
    @Test
    public void testChangesMapFile() {
        configPanel.setMapFile(filePathMap);

        assertFalse(configPanel.isDefault());
    }
}
