package nl.tudelft.bw4t.scenariogui.config;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * @author      Calvin Wong Loi Sing  
 * @version     0.1                
 * @since       12-05-2014        
 */
public class BW4TClientConfigIntegrationTest {

    /** The custom config object used in this test. */
    private static BW4TClientConfig config;

    /** The path of the file to save the config file in. */
    private static final String FILE_PATH = System.getProperty("user.dir")
            + "/src/test/resources/XMLSaveTest.xml";

    /**
     * Setting up the client config.
     */
    @BeforeClass
    public static void testSetup() {
        config = new BW4TClientConfig();
    }

    /**
     * Breaks down the object (not used atm).
     */
    @AfterClass
    public static void testCleanup() {

    }

    /**
     * Tests whether the setFileLocation method works.
     */
    @Test
    public final void testFileLocation() {
        String filename = "testname/gui.xml";
        config.setFileLocation(filename);

        assertEquals(filename, config.getFileLocation());
    }

    /**
     * Tests the set client and port methods.
     */
    @Test
    public final void testClientConfig() {
        String ip = "127.0.0.1";
        config.setClientIp(ip);

        int port = 2;
        config.setClientPort(port);

        assertEquals(ip, config.getClientIp());
        assertEquals(port, config.getClientPort());
    }

    /**
     * Tests the set server and port methods.
     */
    @Test
    public final void testServerConfig() {
        String ip = "8.8.8.9";
        config.setServerIp(ip);

        int port = 2 + 2;
        config.setServerPort(port);

        assertEquals(ip, config.getServerIp());
        assertEquals(port, config.getServerPort());
    }

    /**
     * Tests whether the set gui and set goal methods work.
     */
    @Test
    public final void testCheckboxes() {
        config.setLaunchGui(true);
        config.setUseGoal(true);

        assertTrue(config.isLaunchGui());
        assertTrue(config.isUseGoal());

        config.setLaunchGui(false);
        config.setUseGoal(false);

        assertFalse(config.isLaunchGui());
        assertFalse(config.isLaunchGui());
    }

    /**
     * Tests whether the fields stay the same after saving
     * and then loading them from xml.
     * 
     * @throws FileNotFoundException When the file could not be found.
     * @throws JAXBException If there was an error with the xml loader.
     */
    @Test
    public final void testSaveIntegrity()
            throws FileNotFoundException, JAXBException {
        MainPanel panel = new MainPanel(
                new ConfigurationPanel(), new EntityPanel());

        // Set some dummy changes.

        String dummyIP = "8.8.8.8";
        String dummyPort = "6000";

        panel.getConfigurationPanel().setClientIP(dummyIP);
        panel.getConfigurationPanel().setClientPort(dummyPort);

        BW4TClientConfig configuration = BW4TClientConfigIntegration.
                createConfigFromPanel(panel, FILE_PATH);

        configuration.toXML();

        // Now we load from the file.

        BW4TClientConfig loadedConfiguration = BW4TClientConfig
                .fromXML(FILE_PATH);

        assertEquals(panel.getConfigurationPanel().getClientIP(),
                loadedConfiguration.getClientIp());
        assertEquals(panel.getConfigurationPanel().getClientPort(),
                loadedConfiguration.getClientPort());
    }

    /*
     * @Test public void testBotStorage() { java.util.LinkedList bots = new
     * LinkedList<BotConfig>(); bots.add(new BotConfig()); bots.add(new
     * BotConfigTest());
     *
     * config.setBots(bots); assertEquals(bots, config.getBots()); }
     */
}
