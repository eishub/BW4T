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
     * The file used to save the file in.
     */
    private final String filePath = "testpath.xml";
    /**
     * The file that has to be opened.
     */
    private final String filePathMap = "test.map";
    /**
     * The selected file that cannot be opened.
     */
    private final String fileMapFail = "test.fail";
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
     * Deletes the file after having used it.
     */
    @After
    public final void deleteFiles() {
        new File(filePath).delete();
    }

    /**
     * Tests the setMapFile method.
     */
    @Test
    public final void testSetMapFile() {
        editor.getMainPanel().getConfigurationPanel().setMapFile(filePath);

        assertEquals(filePath, editor.getMainPanel().getConfigurationPanel()
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
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePathMap));

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseMapFile()
                .doClick();

        assertEquals(filePathMap, editor.getMainPanel().getConfigurationPanel()
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

}
