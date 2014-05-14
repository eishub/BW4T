package nl.tudelft.bw4t.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.tudelft.bw4t.config.BW4TClientConfig;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class XMLManagerTest {
	
	private static final String filePath = System.getProperty("user.dir") + "/src/test/resources/XMLManagerTest.xml";
	
	/**
	 * Tests the toXML and fromXML methods.
	 */
	@Test
	public void testConsistency() throws JAXBException, FileNotFoundException {
		XMLManager manager = new XMLManager();
		/** Creates a new client config with a random client ip address: */
		BW4TClientConfig config = new BW4TClientConfig();
		String ip = "133.130.48.45";
		config.setClientIp(ip);
		
		/** Converts the config to XML file format: */
		manager.toXML(filePath, config);
		
		/** Checks if the ip address is still the same after constructing a new config
		 * object from the XML file: */
		BW4TClientConfig config2 = (BW4TClientConfig) manager.fromXML(filePath, BW4TClientConfig.class);
		assertTrue(config2.getClientIp().equals(ip));
		
	}

}
