package nl.tudelft.bw4t.client.startup;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.util.XMLManager;

/**
 * Reads the config file that can be passed to the client to overwrite the
 * InitParam variables.
 */
public class ConfigFile {
	private static final Logger LOGGER = Logger.getLogger(ConfigFile.class);

	protected File file;
	protected BW4TClientConfig config;

	/**
	 * Reads the given file as configuration file.
	 * 
	 * @param filename
	 *            the path and filename of the config to be used #3205 This will
	 *            be relative to the directory where our environment jar file is
	 *            located.
	 * @throws JAXBException
	 *             If an error occurs while reading the xml config
	 * @throws FileNotFoundException
	 *             if the param is null or the file can not be read
	 * @throws URISyntaxException
	 */
	public ConfigFile(String filename) throws JAXBException, FileNotFoundException, URISyntaxException {

		File url = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		file = url.toPath().getParent().resolve(filename).toFile();

		if (file == null || !file.canRead())
			throw new FileNotFoundException("could not read config file " + filename + " inside " + url.getParent());
		// Reads the configuration file and constructs a BW4TClientConfig from
		// it:
		config = (BW4TClientConfig) XMLManager.fromXML(file, BW4TClientConfig.class);
	}

	/**
	 * Gets the value for an init param based on the info read from this
	 * configuration file.
	 * 
	 * @param param
	 *            The init param.
	 * @return the value of the given param in the file or null if not set
	 */
	public String getValue(InitParam param) {
		switch (param) {
		case CLIENTIP:
			return config.getClientIp();
		case CLIENTPORT:
			return Integer.toString(config.getClientPort());
		case GOAL:
			return Boolean.toString(config.isUseGoal());
		case LAUNCHGUI:
			return Boolean.toString(config.isLaunchGui());
		case SERVERIP:
			return config.getServerIp();
		case SERVERPORT:
			return Integer.toString(config.getServerPort());
		case MAP:
			return config.getMapFile();
		default:
			LOGGER.info(param.name() + " is not supported by the config file");
			return null;
		}
	}

	/**
	 * Gets the map file that was read from the init configuration file.
	 * 
	 * @return The location to the map file or {@code null} if no init file was
	 *         read.
	 */
	public String getMapFile() {
		return config.getMapFile();
	}

	public BW4TClientConfig getConfig() {
		return config;
	}

}
