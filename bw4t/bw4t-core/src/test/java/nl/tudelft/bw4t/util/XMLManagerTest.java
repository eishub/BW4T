package nl.tudelft.bw4t.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;

import org.junit.Test;


/**
 * A test class for the XMLManager.     
 */
public class XMLManagerTest {

    /** The path of the xml file used to test the toXML and fromXML methods. */
    private static final String FILE_PATH = System.getProperty("user.dir")
            + "/src/test/resources/XMLManagerTest.xml";


    /**
     * Tests the toXML and fromXML methods.
     * 
     * @throws JAXBException If there was an error with the XML loader.
     * @throws FileNotFoundException When a file was not found.
     */
    @Test
    public final void testConsistency()
            throws JAXBException, FileNotFoundException {

        /** Creates a new client config with a random client ip address: */
        BW4TClientConfig config = new BW4TClientConfig();
        String ip = "133.130.48.45";
        config.setClientIp(ip);

        /** Converts the config to XML file format: */
        XMLManager.toXML(FILE_PATH, config);

        /**
         * Checks if the ip address is still the same after constructing a new
         * config object from the XML file:
         */
        BW4TClientConfig config2 = (BW4TClientConfig) XMLManager.fromXML(
                FILE_PATH, BW4TClientConfig.class);
        assertTrue(config2.getClientIp().equals(ip));

        if (!new File(FILE_PATH).delete()) {
            System.out.println(
                    "Test file could not be deleted, "
                    + "please delete it manually.");
        }

    }

}
