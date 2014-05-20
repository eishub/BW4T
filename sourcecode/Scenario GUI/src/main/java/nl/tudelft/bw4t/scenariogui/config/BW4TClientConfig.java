package nl.tudelft.bw4t.scenariogui.config;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.tudelft.bw4t.scenariogui.gui.panel.BotPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.XMLManager;


/**
 * This class holds the possible options that can be specified
 * in the Scenario GUI and is meant to be directly convertible
 * to XML and constructible from XML.
 *
 * @author Nick / Calvin
 *
 */
@XmlRootElement
public class BW4TClientConfig {

	/** The file in which the configs in this class will be stored: */
	private String outputFile;

	private String clientIp;
	private int clientPort;
	private String serverIp;
	private int serverPort;
	private boolean launchGui;
	private boolean useGoal;
	private String mapFile;

//  @XmlElementWrapper(name = "botlist")
//  @XmlElement(name = "bot")
// 	private LinkedList<BotConfig> bots = new LinkedList<BotConfig>();

	public BW4TClientConfig() { }

	public BW4TClientConfig(MainPanel mainPanel, String outputFile) {
		ConfigurationPanel configPanel = mainPanel.getConfigurationPanel();
		clientIp = configPanel.getClientIP();
		clientPort = configPanel.getClientPort();
		serverIp = configPanel.getServerIP();
		serverPort = configPanel.getServerPort();
		launchGui = configPanel.useGui();
		useGoal = configPanel.useGoal();
		mapFile = configPanel.getMapFile();
		BotPanel botPanel = mainPanel.getBotPanel();
		//TODO: read out bot panel and add each BotConfig to the list of bots //botPanel.getTable().;
		this.outputFile = outputFile;
	}

	/**
	 * Converts Java Object into XML file.
	 *
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 * @throws JAXBException Root exception class for all JAXB exceptions.
	 */
	public final void toXML() throws FileNotFoundException, JAXBException {
		XMLManager.toXML(outputFile, this);
	}

	/**
	 * Construct Java Object from XML file.
	 *
	 * @param inputFile The file location of the XML-file
	 * @return The BW4TClientConfig object
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 * @throws JAXBException Root exception class for all JAXB exceptions.
	 */
	public static BW4TClientConfig fromXML(String inputFile) throws FileNotFoundException, JAXBException {
		return (BW4TClientConfig) XMLManager.fromXML(inputFile, BW4TClientConfig.class);
	}

	/**
	 * Gets the location to store the XML version of this file in.
	 * @return The path of the file to store this object in as XML.
	 */
	public String getFileLocation() {
		return outputFile;
	}

	/**
	 * Sets the location to store the XML version of this file in.
	 * @param fileLocation The path of the file to store this object in as XML.
	 */
	public void setFileLocation(String fileLocation) {
		this.outputFile = fileLocation;
	}

	/**
	 * Gets the clientIP.
	 * @return The clientIP.
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * Sets the clientIP.
	 * @param clientIp The clientIP.
	 */
    @XmlElement
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

    /**
	 * Gets the clientPort.
	 * @return The clientPort.
	 */
	public int getClientPort() {
		return clientPort;
	}

    /**
	 * Sets the clientPort.
	 * @param clientPort The clientPort.
	 */
    @XmlElement
	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

    /**
	 * Gets the serverIP.
	 * @return The serverIP.
	 */
	public String getServerIp() {
		return serverIp;
	}

    /**
	 * Sets the serverIP.
	 * @param serverIp The serverIP.
	 */
    @XmlElement
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

    /**
	 * Gets the serverPort.
	 * @return The serverPort.
	 */
	public int getServerPort() {
		return serverPort;
	}

    /**
	 * Sets the serverPort.
	 * @param serverPort The serverPort.
	 */
    @XmlElement
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

    /**
	 * Gets if GUI should be launched.
	 * @return if a GUI should be launched.
	 */
	public boolean isLaunchGui() {
		return launchGui;
	}

    /**
	 * Sets if a GUI should be launched.
	 * @param launchGui Boolean indicating if a GUI should be launched.
	 */
    @XmlElement
	public void setLaunchGui(boolean launchGui) {
		this.launchGui = launchGui;
	}
    /**
	 * Gets if Goal should be used.
	 * @return if Goal should be used.
	 */
	public boolean isUseGoal() {
		return useGoal;
	}

    /**
	 * Sets if GOAL should be used.
	 * @param useGoal Boolean indicating if GOAL should be used.
	 */
    @XmlElement
	public void setUseGoal(boolean useGoal) {
		this.useGoal = useGoal;
	}

    /**
	 * Gets location of the MapFile.
	 * @return mapFile The MapFile location.
	 */
	public String getMapFile() {
		return mapFile;
	}

    /**
	 * Sets the MapFile.
	 * @param mapFile The MapFile location.
	 */
    @XmlElement
	public void setMapFile(String mapFile) {
		this.mapFile = mapFile;
	}

//	public LinkedList<BotConfig> getBots() {
//		return bots;
//	}
//
//	public void setBots(LinkedList<BotConfig> bots) {
//		this.bots = bots;
//	}
}
