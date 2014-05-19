package nl.tudelft.bw4t.scenariogui.gui.panel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on 13/05/2014.
 */
public class ConfigurationPanelTest {

    private ScenarioEditor editor;
    private final String filePath = "testpath.temp";

    private JFileChooser fileChooser;

    @Before
    public void setUp() {
        // Mock the JFileChooser
        fileChooser = mock(JFileChooser.class);

        editor = new ScenarioEditor();

        // Set the fileChooser used to the mocked one.
        editor.getMainPanel().getConfigurationPanel().fileChooser = fileChooser;
    }

    @After
    public void tearDown() {
        new File(filePath).delete();
    }

    @Test
    public void testSetMapFile() {
        editor.getMainPanel().getConfigurationPanel().setMapFile(filePath);

        assertEquals(filePath, editor.getMainPanel().getConfigurationPanel().getMapFile());
    }

    @Test
    public void testMapFileAction() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePath));

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseMapFile().doClick();

        assertEquals(filePath, editor.getMainPanel().getConfigurationPanel().getMapFile());
    }

    // Test the if clause, in case the return of JFileChooser is not APPROVE_OPTION

    @Test
    public void testMapFileActionBranch() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(1);
        when(fileChooser.getSelectedFile()).thenReturn(new File("ss"));

        // Original value
        String startURI = editor.getMainPanel().getConfigurationPanel().getMapFile();

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseMapFile().doClick();

        assertEquals(startURI, editor.getMainPanel().getConfigurationPanel().getMapFile());
    }


}
