package nl.tudelft.bw4t.scenariogui.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Calvin on 12-5-2014.
 */
public class BW4TClientConfigTest {
    @BeforeClass
    public static void testSetup() {

    }

    @AfterClass
    public static void testCleanup() {

    }

    @Test
    public void testFileLocation() {
        BW4TClientConfig config = new BW4TClientConfig();

        String filename = "testname/gui.xml";
        config.setFileLocation(filename);

        assertEquals(filename, config.getFileLocation());
    }

    @Test
    public void testClientIP() {
        BW4TClientConfig config = new BW4TClientConfig();

        String ip = "127.0.0.1";
        config.setClientIp(ip);

        assertEquals(ip, config.getClientIp());
    }
}