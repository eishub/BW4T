package nl.tudelft.bw4t.scenariogui;

import java.util.LinkedList;

/**
 * This class holds the possible options that can be specified
 * in the Scenario GUI and is meant to be directly convertible
 * to XML and constructible from XML.
 * 
 * @author Nick
 *
 */
public class ClientConfig {
	
	private String clientIp;
	private int clientPort;
	private String serverIp;
	private int serverPort;
	private boolean launchGui;
	private boolean useGoal;
	private String agentClass;
	
	private String mapFile;
	private LinkedList<BotConfig> bots = new LinkedList<BotConfig>();
	
	public String getClientIp() {
		return clientIp;
	}
	
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	public int getClientPort() {
		return clientPort;
	}
	
	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}
	
	public String getServerIp() {
		return serverIp;
	}
	
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public boolean isLaunchGui() {
		return launchGui;
	}
	
	public void setLaunchGui(boolean launchGui) {
		this.launchGui = launchGui;
	}
	
	public boolean isUseGoal() {
		return useGoal;
	}
	
	public void setUseGoal(boolean useGoal) {
		this.useGoal = useGoal;
	}
	
	public String getAgentClass() {
		return agentClass;
	}
	
	public void setAgentClass(String agentClass) {
		this.agentClass = agentClass;
	}

	public String getMapFile() {
		return mapFile;
	}

	public void setMapFile(String mapFile) {
		this.mapFile = mapFile;
	}
	
}
