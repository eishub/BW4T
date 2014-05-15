package nl.tudelft.bw4t.client;

import nl.tudelft.bw4t.server.BW4TEnvironment;

/**
 * Available init parameters and default values for {@link BW4TEnvironment}.
 * 
 * @author W.Pasman 20mar13
 */
enum InitParam {
	/**
	 * our ip. Passed to server so server must be able to find us with this ip.
	 */
	CLIENTIP("localhost"),
	/**
	 * the port that we use.
	 */
	CLIENTPORT("2000"),
	/**
	 * The ip addres of server.
	 */
	SERVERIP("localhost"),
	/**
	 * port used by the server
	 */
	SERVERPORT("8000"),
	/**
	 * Number of agents that we have
	 */

	AGENTCOUNT("1"),
	/**
	 * Launch gui for entities? (check is this only for humans?)
	 */
	LAUNCHGUI("true"),
	/**
	 * Number of human bots (is this to connect GUIs?)
	 */
	HUMANCOUNT("1"),
	/**
	 * The java agent class to load when new entities appear.
	 */
	AGENTCLASS("nl.tudelft.bw4t.agent.BW4TAgent"),
	/**
	 * are we connected with GOAL?
	 */
	GOAL("false");

	private final String defaultvalue;

	/**
	 * @param def
	 *            the default value of the init parameter
	 */
	InitParam(String def) {
		defaultvalue = def;
	}

	public String getDefaultValue() {
		return defaultvalue;
	}

	/**
	 * {@link #name()} but in lower case
	 * 
	 * @return name in lower case
	 */
	public String nameLower() {
		return this.name().toLowerCase();
	}
}
