package nl.tudelft.bw4t.client.environment;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.bw4t.agent.BW4TAgent;
import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;

import org.apache.log4j.Logger;

import eis.EnvironmentListener;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.RelationException;
import eis.iilang.EnvironmentState;

/**
 * Class that can be registered to BW4TRemoteEnvironment as EnvironmentListener and will launch new agents when new
 * entities are available
 * <p>
 * This is needed when the BW4TRemoteEnvironment is runned stand-alone. Do not start this when running GOAL, as GOAL
 * already is an environment runner and will associate agents to entities if entities appear.
 * 
 * @author trens
 */
public class BW4TEnvironmentListener implements EnvironmentListener {

	private int agentCount;
	/**
	 * The log4j Logger which displays logs on console
	 */
	private final static Logger LOGGER = Logger.getLogger(BW4TEnvironmentListener.class);

	/**
	 * This map associates agents with a renderer. I suppose agents not having a renderer do not end up in this list.
	 */
	private final Map<BW4TAgent, BW4TClientGUI> agentData = new HashMap<BW4TAgent, BW4TClientGUI>();

	private final RemoteEnvironment environment;

	public BW4TEnvironmentListener(RemoteEnvironment env) {
		environment = env;
	}

	/**
	 * Handle a deleted entity
	 * 
	 * @param arg0
	 *            , the deleted entity
	 * @param arg1
	 *            , the list of associated agents
	 */
	@Override
	public void handleDeletedEntity(String arg0, Collection<String> arg1) {
		for (String agent : arg1) {
			for (BW4TAgent agentB : agentData.keySet()) {
				if (agentB.getName().equals(agent)) {
					agentB.setKilled();
					if (agentData.get(agentB) != null) {
						agentData.get(agentB).stop = true;
					}
					agentData.remove(agentB);
					return;
				}
			}
		}
	}

	/**
	 * Handle a free entity
	 * 
	 * @param arg0
	 *            , the free entity
	 * @param arg1
	 *            , the list of associated agents TODO: Unimplemented
	 */
	@Override
	public void handleFreeEntity(String arg0, Collection<String> arg1) {
		// Not implemented.
	}

	/**
	 * Handle a new entity, load the human agent if it is of type human otherwise load the agent that was specified in
	 * the program argument or the default one (BW4TAgent)
	 * 
	 * @param entityId
	 *            , the new entity
	 */
	@Override
	public void handleNewEntity(String entityId) {
		LOGGER.debug("Handeling new entity of the environment: " + entityId);
		try {
			handleNewEntity1(entityId);
		} catch (Exception e) {
			LOGGER.error("Failed to handle new entity event.", e);
		}
	}

	/**
	 * Internal handleNewEntity, throwing if there is a problem.
	 * 
	 * @param entityId
	 * @throws EntityException
	 * @throws AgentException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws RelationException
	 */
	private void handleNewEntity1(String entityId) throws EntityException, AgentException, ClassNotFoundException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
			IOException, RelationException {
		agentCount = environment.getAgents().size();
		if ("human".equals(environment.getType(entityId))) {
			HumanAgent agent = new HumanAgent("Agent" + agentCount, environment);
			agent.registerEntity(entityId);
			environment.registerAgent(agent.getAgentId());
			environment.associateEntity(agent.getAgentId(), entityId);
			BW4TClientGUI renderer;
			renderer = new BW4TClientGUI(environment, entityId, agent);

			agent.start();
			agentCount++;

			agentData.put(agent, renderer);
		}
		else {
			String agentClassName = InitParam.AGENTCLASS.getValue();
			Class<? extends BW4TAgent> c = Class.forName(agentClassName).asSubclass(BW4TAgent.class);
			Class[] types = new Class[] { String.class, RemoteEnvironment.class };
			Constructor<BW4TAgent> cons = (Constructor<BW4TAgent>) c.getConstructor(types);
			// we use the entityId as name for the agent as well. #2761
			Object[] args = new Object[] { entityId, environment };
			BW4TAgent agent = cons.newInstance(args);
			agent.registerEntity(entityId);
			environment.registerAgent(agent.getAgentId());
			environment.associateEntity(agent.getAgentId(), entityId);
			agent.start();
			agentCount++;

			agentData.put(agent, null);

		}
	}

	/**
	 * Handle a state change
	 * 
	 * @param arg0
	 *            , the new state
	 */
	@Override
	public void handleStateChange(EnvironmentState newState) {
		LOGGER.debug("Handeling new environment state: " + newState);
		if (newState.equals(EnvironmentState.KILLED)) {
			// YUK YUK. FIXME
			System.exit(0);
		}
	}
}
