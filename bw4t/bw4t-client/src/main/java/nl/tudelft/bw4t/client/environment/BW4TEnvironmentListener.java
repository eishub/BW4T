package nl.tudelft.bw4t.client.environment;

import eis.EnvironmentListener;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.RelationException;
import eis.iilang.EnvironmentState;
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

/**
 * Class that can be registered to {@link RemoteEnvironment} as {@link BW4TEnvironmentListener}
 * and will launch new agents when new entities are available.
 * <p>
 * This is needed when the RemoteEnvironment is run stand-alone. Do not start this when running GOAL,
 * as GOAL already is an environment runner and will associate agents to entities if entities appear.
 * @author trens
 */
public class BW4TEnvironmentListener implements EnvironmentListener {

	/** Number of agents present. */
    private int agentCount;
    /** The log4j Logger which displays logs on console. */
    private final static Logger LOGGER = Logger.getLogger(BW4TEnvironmentListener.class);
    /**
     * This map associates agents with a renderer. I suppose agents not having a renderer do not end up in this list.
     * TODO: Need to find out if agents that do not have a renderer end up in this list or not.
     */
    private final Map<BW4TAgent, BW4TClientGUI> agentData = new HashMap<BW4TAgent, BW4TClientGUI>();
    /** {@link RemoteEnvironment} to listen to and interact with. */
    private final RemoteEnvironment environment;

    /** @param env {@link RemoteEnvironment} to listen to and interact with. */
    public BW4TEnvironmentListener(RemoteEnvironment env) {
        environment = env;
    }

    /**
     * Handle a deleted entity
     * 
     * @param entity
     *            - The deleted entity.
     * @param associatedEntities
     *            - The list of associated agents.
     */
    @Override
    public void handleDeletedEntity(String entity, Collection<String> associatedEntities) {
        for (String agent : associatedEntities) {
            for (BW4TAgent agentB : agentData.keySet()) {
                if (agentB.getName().equals(agent)) {
                    agentB.setKilled();
                    if (agentData.get(agentB) != null) {
                        //FIXME agentData.get(agentB).stop = true;
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
     * @param entity
     *            - The free entity.
     * @param associatedEntities
     *            - The list of associated agents.
     */
    @Override
    public void handleFreeEntity(String entity, Collection<String> associatedEntities) {
        // TODO Not implemented.
    }

    /**
     * Handle a new entity, load the human agent if it is of type human otherwise load the agent
     * that was specified in the program argument or the default one (BW4TAgent).
     * 
     * @param entityId
     *            - The new entity.
     */
    @Override
    public void handleNewEntity(String entityId) {
        LOGGER.debug("Handeling new entity of the environment: " + entityId);
            try {
				handleNewEntity1(entityId);
			} catch (EntityException | AgentException | ClassNotFoundException | NoSuchMethodException
					| InstantiationException | IllegalAccessException | InvocationTargetException 
					| RelationException | IOException e) {
	            LOGGER.error("Failed to handle new entity event.", e);
			}
    }

    /**
     * Internal handleNewEntity, throwing if there is a problem.
     * 
     * @param entityId
     *            - The new entity.
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
        } else {
            String agentClassName = InitParam.AGENTCLASS.getValue();
            Class<? extends BW4TAgent> c = Class.forName(agentClassName).asSubclass(BW4TAgent.class);
            Class[] types = new Class[] {String.class, RemoteEnvironment.class};
            Constructor<BW4TAgent> cons = (Constructor<BW4TAgent>) c.getConstructor(types);
            // we use the entityId as name for the agent as well. #2761
            Object[] args = new Object[] {entityId, environment};
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
     * Handles a state change.
     * 
     * @param newState
     *            - The new state.
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
