package nl.tudelft.bw4t.gui.panel;

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

    private MainPanel panel;
    private final String filePath = "testpath.temp";

    private JFileChooser fileChooser;

    @Before
    public void setUp() {
        // Mock the JFileChooser
        fileChooser = mock(JFileChooser.class);

        panel = new MainPanel(new ConfigurationPanel(), new BotPanel());
        // Set the fileChooser used to the mocked one.
        panel.getConfigurationPanel().fileChooser = fileChooser;
    }

    @After
    public void tearDown() {
        new File(filePath).delete();
    }

    @Test
    public void testSetAgentFile() {
        panel.getConfigurationPanel().setAgentClassFile(filePath);

        assertEquals(filePath, panel.getConfigurationPanel().getAgentClassFile());
    }

    @Test
    public void testSetMapFile() {
        panel.getConfigurationPanel().setMapFile(filePath);

        assertEquals(filePath, panel.getConfigurationPanel().getMapFile());
    }

    @Test
    public void testAgentFileAction() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(panel)).thenReturn(JFileChooser.APPROVE_OPTION);
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePath));

        // Trigger the event.
        panel.getConfigurationPanel().getChooseAgentFile().doClick();

        assertEquals(filePath, panel.getConfigurationPanel().getAgentClassFile());
    }

    @Test
    public void testMapFileAction() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(panel)).thenReturn(JFileChooser.APPROVE_OPTION);
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePath));

        // Trigger the event.
        panel.getConfigurationPanel().getChooseMapFile().doClick();

        assertEquals(filePath, panel.getConfigurationPanel().getMapFile());
    }

    // Test the if clause, in case the return of JFileChooser is not APPROVE_OPTION

    @Test
    public void testAgentFileActionBranch() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(panel)).thenReturn(1);
        when(fileChooser.getSelectedFile()).thenReturn(new File(filePath));

        // Original value
        String startURI = panel.getConfigurationPanel().getAgentClassFile();

        // Trigger the event.
        panel.getConfigurationPanel().getChooseAgentFile().doClick();

        assertEquals(startURI, panel.getConfigurationPanel().getAgentClassFile());
    }

    @Test
    public void testMapFileActionBranch() {
        // Setup the mocks behaviour.
        when(fileChooser.showOpenDialog(panel)).thenReturn(1);
        when(fileChooser.getSelectedFile()).thenReturn(new File("ss"));

        // Original value
        String startURI = panel.getConfigurationPanel().getMapFile();

        // Trigger the event.
        panel.getConfigurationPanel().getChooseMapFile().doClick();

        assertEquals(startURI, panel.getConfigurationPanel().getMapFile());
    }


}
