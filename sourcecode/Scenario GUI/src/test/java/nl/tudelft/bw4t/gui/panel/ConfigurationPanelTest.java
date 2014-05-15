package nl.tudelft.bw4t.gui.panel;

import nl.tudelft.bw4t.ScenarioEditor;
import org.junit.*;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.tudelft.bw4t.gui.panel.MainPanel;

import javax.swing.*;

import static org.mockito.Mockito.*;

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
    public void testSetAgentFile() {
        editor.getMainPanel().getConfigurationPanel().setAgentClassFile(filePath);

        assertEquals(filePath, editor.getMainPanel().getConfigurationPanel().getAgentClassFile());
    }

    @Test
    public void testSetMapFile() {
        editor.getMainPanel().getConfigurationPanel().setMapFile(filePath);

        assertEquals(filePath, editor.getMainPanel().getConfigurationPanel().getMapFile());
    }

    @Test
    public void testAgentFileAction() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePath));

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseAgentFile().doClick();

        assertEquals(filePath, editor.getMainPanel().getConfigurationPanel().getAgentClassFile());
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
    public void testAgentFileActionBranch() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(editor.getMainPanel())).thenReturn(1);
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePath));

        // Original value
        String startURI = editor.getMainPanel().getConfigurationPanel().getAgentClassFile();

        // Trigger the event.
        editor.getMainPanel().getConfigurationPanel().getChooseAgentFile().doClick();

        assertEquals(startURI, editor.getMainPanel().getConfigurationPanel().getAgentClassFile());
    }

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
