package nl.tudelft.bw4t.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.tudelft.bw4t.config.BW4TClientConfig;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class XMLManagerTest {
	
	private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/XMLManagerTest.xml";
	
	/**
	 * Tests the toXML and fromXML methods.
	 */
	@Test
	public void testConsistency() throws JAXBException, FileNotFoundException {
		
		/** Creates a new client config with a random client ip address: */
		BW4TClientConfig config = new BW4TClientConfig();
		String ip = "133.130.48.45";
		config.setClientIp(ip);
		
		/** Converts the config to XML file format: */
		XMLManager.toXML(FILE_PATH, config);
		
		/** Checks if the ip address is still the same after constructing a new config
		 * object from the XML file: */
		BW4TClientConfig config2 = (BW4TClientConfig) XMLManager.fromXML(FILE_PATH, BW4TClientConfig.class);
		assertTrue(config2.getClientIp().equals(ip));
		
		File file = new File(FILE_PATH);
		if (!file.delete())
			System.out.println("Test file could not be deleted, please delete is manually.");

		
	}

}
