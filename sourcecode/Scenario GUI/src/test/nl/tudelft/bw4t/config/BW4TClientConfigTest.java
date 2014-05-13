package nl.tudelft.bw4t.config;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Calvin on 12-5-2014.
 */
public class BW4TClientConfigTest {

    private static BW4TClientConfig config;

    @BeforeClass
    public static void testSetup() {
        config = new BW4TClientConfig();
    }

    @AfterClass
    public static void testCleanup() {

    }

    @Test
    public void testFileLocation() {
        String filename = "testname/gui.xml";
        config.setFileLocation(filename);

        assertEquals(filename, config.getFileLocation());
    }

    @Test
    public void testClientConfig() {
        String ip = "127.0.0.1";
        config.setClientIp(ip);

        int port = 8000;
        config.setClientPort(port);

        assertEquals(ip, config.getClientIp());
        assertEquals(port, config.getClientPort());
    }

    @Test
    public void testServerConfig() {
        String ip = "8.8.8.8";
        config.setServerIp(ip);

        int port = 9000;
        config.setServerPort(port);

        assertEquals(ip, config.getServerIp());
        assertEquals(port, config.getServerPort());
    }

    @Test
    public void testCheckboxes() {
        config.setLaunchGui(true);
        config.setUseGoal(true);

        assertTrue(config.isLaunchGui());
        assertTrue(config.isUseGoal());

        config.setLaunchGui(false);
        config.setUseGoal(false);

        assertFalse(config.isLaunchGui());
        assertFalse(config.isLaunchGui());
    }

    @Test
    public void testAgentClass() {
        String agentClass = "gangsta-agent.java";
        config.setAgentClass(agentClass);

        String mapFile = "banana.map";
        config.setMapFile(mapFile);

        assertEquals(agentClass, config.getAgentClass());
        assertEquals(mapFile, config.getMapFile());
    }

    /*
    @Test
    public void testBotStorage() {
        java.util.LinkedList bots = new LinkedList<BotConfig>();
        bots.add(new BotConfig());
        bots.add(new BotConfigTest());

        config.setBots(bots);
        assertEquals(bots, config.getBots());
    }
    */


}