package nl.tudelft.bw4t.client.environment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.log4j.Logger;

import eis.EnvironmentListener;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.RelationException;
import eis.iilang.EnvironmentState;
import nl.tudelft.bw4t.client.agent.BW4TAgent;
import nl.tudelft.bw4t.client.startup.InitParam;

/**
 * Class that can be registered to BW4TRemoteEnvironment as EnvironmentListener
 * and will launch new agents when new entities are available This is needed
 * when the BW4TRemoteEnvironment is runned stand-alone. Do not start this when
 * running GOAL, as GOAL already is an environment runner and will associate
 * agents to entities if entities appear.
 */
public class BW4TEnvironmentListener implements EnvironmentListener {

	/** The log4j Logger which displays logs on console. */
	private static final Logger LOGGER = Logger
			.getLogger(BW4TEnvironmentListener.class);
	/** {@link RemoteEnvironment} to listen to and interact with. */
	private final RemoteEnvironment environment;

	/**
	 * @param env
	 *            {@link RemoteEnvironment} to listen to and interact with.
	 */
	public BW4TEnvironmentListener(RemoteEnvironment env) {
		environment = env;
	}

	/**
	 * Handle a deleted entity
	 * 
	 * @param entity
	 *            - The deleted entity.
	 * @param agents
	 *            - The list of associated agents.
	 */
	@Override
	public void handleDeletedEntity(String entity, Collection<String> agents) {
		for (String name : agents) {
			BW4TAgent agent = environment.getRunningAgent(name);
			if (agent != null) {
				agent.setKilled();
				environment.removeRunningAgent(agent);
			}
		}
	}

	/**
	 * Handle a free entity
	 * 
	 * @param entity
	 *            - The free entity.
	 * @param associatedEntities
	 *            - The list of associated agents.
	 */
	@Override
	public void handleFreeEntity(String entity,
			Collection<String> associatedEntities) {
		// Not used currently
	}

	/**
	 * Handle a new entity, load the human agent if it is of type human
	 * otherwise load the agent that was specified in the program argument or
	 * the default one (BW4TAgent).
	 * 
	 * @param entity
	 *            - The new entity.
	 */
	@Override
	public void handleNewEntity(String entity) {
		LOGGER.debug("Handeling new entity of the environment: " + entity);

		try {
			BW4TAgent agent = newAgent(InitParam.AGENTCLASS.getValue(), entity);

			agent.registerEntity(entity);
			environment.addRunningAgent(agent);
			environment.associateEntity(agent.getAgentId(), entity);

			agent.start();
		} catch (InstantiationException | AgentException | RelationException
				| EntityException e) {
			LOGGER.error("Failed to handle new entity event.", e);
		}
	}

	/**
	 * Creates a new agent.
	 * 
	 * @param clazz
	 * @param entity
	 * @return Returns the newly created agent
	 * @throws InstantiationException
	 * @throws EntityException
	 */
	protected BW4TAgent newAgent(String clazz, String entity)
			throws InstantiationException, EntityException {
		try {
			Class<? extends BW4TAgent> c = Class.forName(clazz).asSubclass(
					BW4TAgent.class);
			@SuppressWarnings("unchecked")
			Constructor<BW4TAgent> cons = (Constructor<BW4TAgent>) c
					.getConstructor(String.class, RemoteEnvironment.class);
			// we use the entityId as name for the agent as well. #2761
			BW4TAgent agent = cons.newInstance(entity, environment);

			agent.setType(environment.getType(entity));

			return agent;
		} catch (InstantiationException | ClassNotFoundException
				| NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new InstantiationException(e.getMessage());
		}
	}

	/**
	 * Handles a state change.
	 * 
	 * @param newState
	 *            - The new state.
	 */
	@Override
	public void handleStateChange(EnvironmentState newState) {
		LOGGER.debug("Handeling new environment state: " + newState);
		if (newState.equals(EnvironmentState.KILLED)) {
			System.exit(0);
		}
	}
}
