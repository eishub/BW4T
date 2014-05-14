package nl.tudelft.bw4t.config;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;
import nl.tudelft.bw4t.util.XMLManager;


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
	private String agentClass;
	
	private String mapFile;
	
//  @XmlElementWrapper(name = "botlist")
//  @XmlElement(name = "bot")
// 	private LinkedList<BotConfig> bots = new LinkedList<BotConfig>();
	
	public BW4TClientConfig() {}
	
	public BW4TClientConfig(MainPanel mainPanel, String outputFile) {
		ConfigurationPanel configPanel = mainPanel.getConfigurationPanel();
		clientIp = configPanel.getClientIP();
		clientPort = configPanel.getClientPort();
		serverIp = configPanel.getServerIP();
		serverPort = configPanel.getServerPort();
		launchGui = configPanel.useGui();
		useGoal = configPanel.useGoal();
		agentClass = configPanel.getAgentClassFile();
		BotPanel botPanel = mainPanel.getBotPanel();
		//TODO: read out bot panel and add each BotConfig to the list of bots //botPanel.getTable().;
		this.outputFile = outputFile;
	}
	
	public void toXML() throws FileNotFoundException, JAXBException {
		XMLManager.toXML(outputFile, this);
	}
	
	/**
	 * Gets the location to store the XML version of this file in.
	 * @return The path of the file to store this object in as XML.
	 */
	public String getFileLocation() {
		return outputFile;
	}

	public void setFileLocation(String fileLocation) {
		this.outputFile = fileLocation;
	}

	public String getClientIp() {
		return clientIp;
	}

    @XmlElement
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	public int getClientPort() {
		return clientPort;
	}

    @XmlElement
	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}
	
	public String getServerIp() {
		return serverIp;
	}

    @XmlElement
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	public int getServerPort() {
		return serverPort;
	}

    @XmlElement
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public boolean isLaunchGui() {
		return launchGui;
	}

    @XmlElement
	public void setLaunchGui(boolean launchGui) {
		this.launchGui = launchGui;
	}
	
	public boolean isUseGoal() {
		return useGoal;
	}

    @XmlElement
	public void setUseGoal(boolean useGoal) {
		this.useGoal = useGoal;
	}
	
	public String getAgentClass() {
		return agentClass;
	}

    @XmlElement
	public void setAgentClass(String agentClass) {
		this.agentClass = agentClass;
	}

	public String getMapFile() {
		return mapFile;
	}

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
