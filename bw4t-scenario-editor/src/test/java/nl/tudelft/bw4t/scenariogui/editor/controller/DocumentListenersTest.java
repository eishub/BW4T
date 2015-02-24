package nl.tudelft.bw4t.scenariogui.editor.controller;

import static org.junit.Assert.assertEquals;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentListenersTest {

    private static final int RANDOM_VALUE = 154;

    private ScenarioEditor editor;

    @Before
    public void setup() {
        editor = new ScenarioEditor();
    }

    @After
    public void tearDown() {
        editor.dispose();
    }

    @Test
    public void testWriteClientIP() {
        getCPanel().getClientIPTextField().setText("" + RANDOM_VALUE);
        assertEquals("" + RANDOM_VALUE, getConfig().getClientIp());
    }

    @Test
    public void testWriteClientPort() {
        getCPanel().getClientPortTextField().setText("" + RANDOM_VALUE);
        assertEquals(RANDOM_VALUE, getConfig().getClientPort());
    }

    @Test
    public void testWriteServerIP() {
        getCPanel().getServerIPTextField().setText("" + RANDOM_VALUE);
        assertEquals("" + RANDOM_VALUE, getConfig().getServerIp());
    }

    @Test
    public void testWriteServerPort() {
        getCPanel().getServerPortTextField().setText("" + RANDOM_VALUE);
        assertEquals(RANDOM_VALUE, getConfig().getServerPort());
    }

    @Test
    public void testWriteMapFile() {
        getCPanel().getMapFileTextField().setText("" + RANDOM_VALUE);
        assertEquals("" + RANDOM_VALUE, getConfig().getMapFile());
    }

    private ConfigurationPanel getCPanel() {
        return editor.getMainPanel().getConfigurationPanel();
    }

    private BW4TClientConfig getConfig() {
        return editor.getController().getModel();
    }

}
