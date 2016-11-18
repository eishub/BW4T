package nl.tudelft.bw4t.server;

import java.rmi.server.RemoteServer;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;

/**
 * This stores the entities and their type that a client requests when
 * connecting to the server
 *
 */
class ClientInfo {

	private BW4TClientConfig clientConfig;
	private List<BotConfig> requestedBots = new LinkedList<>();
	private List<EPartnerConfig> requestedEPartners = new LinkedList<>();
	private final String name;
	private Double speed = null; // environment rate, in steps per second.
									// null=not set (server keep as it is).

	/**
	 * The usual call when used from GOAL.
	 * 
	 * @param uniqueName
	 * @param reqAgent
	 *            #bots that the client needs
	 * @param reqHuman
	 *            #humans that the client needs
	 * @param sp
	 *            the speed (fps). Can be null to ignore
	 */
	public ClientInfo(String uniqueName, int reqAgent, int reqHuman, Double sp) {
		assert reqHuman >= 0;
		assert reqAgent >= 0;
		name = uniqueName;
		this.clientConfig = new BW4TClientConfig();
		if (reqAgent > 0) {
			BotConfig bot = BotConfig.createDefaultRobot();
			bot.setBotAmount(reqAgent);
			clientConfig.addBot(bot);
			requestedBots.add(bot);
		}
		if (reqHuman > 0) {
			BotConfig bot = BotConfig.createDefaultHumans();
			bot.setBotAmount(reqHuman);
			clientConfig.addBot(bot);
			requestedBots.add(bot);
		}
		speed = sp;
	}

	/**
	 * 
	 * @param clientConfig
	 * @param uniqueName
	 *            the {@link RemoteServer#getClientHost() value}
	 */
	public ClientInfo(BW4TClientConfig clientConfig, String uniqueName) {
		assert clientConfig != null;

		name = uniqueName;
		if (clientConfig.getBots() != null) {
			this.requestedBots = clientConfig.getBots();
		}
		if (clientConfig.getEpartners() != null) {
			this.requestedEPartners = clientConfig.getEpartners();
		}
		this.clientConfig = clientConfig;
	}

	public List<BotConfig> getRequestedBots() {
		return requestedBots;
	}

	public List<EPartnerConfig> getRequestedEPartners() {
		return requestedEPartners;
	}

	public String getMapFile() {
		return clientConfig == null ? "" : clientConfig.getMapFile();
	}

	public Object getName() {
		return name;
	}

	/**
	 * 
	 * @return environment rate, in steps per second. null=not set (server keep
	 *         as it is).
	 * 
	 */
	public Double getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            environment rate, in steps per second. null=not set (server
	 *            keep as it is).
	 */

	public void setSpeed(Double speed) {
		this.speed = speed;
	}
}
