package nl.tudelft.bw4t.agent;

import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;

/**
 * Class that represents an agent that is controlled via the BW4TRenderer
 * 
 * @author trens
 * 
 */
public class HumanAgent extends BW4TAgent {

	/**
	 * Create a new human agent that should be linked to a BW4TRenderer
	 * 
	 * @param agentId
	 *            , the id of this agent
	 */
	public HumanAgent(String agentId, BW4TRemoteEnvironment env) {
		super(agentId, env);
	}

	@Override
	public void run() {

	}
}
