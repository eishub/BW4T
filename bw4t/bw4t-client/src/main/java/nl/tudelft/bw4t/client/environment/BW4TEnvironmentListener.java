package nl.tudelft.bw4t.client.environment;

import eis.EnvironmentListener;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.RelationException;
import eis.iilang.EnvironmentState;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.bw4t.client.agent.BW4TAgent;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;

import org.apache.log4j.Logger;

/**
 * Class that can be registered to BW4TRemoteEnvironment as EnvironmentListener and will launch new agents when new
 * entities are available This is needed when the BW4TRemoteEnvironment is runned stand-alone. Do not start this when
 * running GOAL, as GOAL already is an environment runner and will associate agents to entities if entities appear.
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
    private final List<BW4TAgent> agentData = new ArrayList<>();
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
     * @param associatedEntities
     *            - The list of associated agents.
     */
    @Override
    public void handleDeletedEntity(String entity, Collection<String> associatedEntities) {
        for (String agent : associatedEntities) {
            for (BW4TAgent agentB : agentData) {
                if (agentB.getName().equals(agent)) {
                    agentB.setKilled();

                    agentData.remove(agentB);
                    return;
                }
            }
        }

        environment.removeEntityController(entity);
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
     * Handle a new entity, load the human agent if it is of type human otherwise load the agent that was specified in
     * the program argument or the default one (BW4TAgent).
     * 
     * @param entityId
     *            - The new entity.
     */
    @Override
    public void handleNewEntity(String entity) {
        LOGGER.debug("Handeling new entity of the environment: " + entity);
        
        try {
            final int agentCount = environment.getAgents().size();
            final boolean isHuman = "human".equals(environment.getType(entity));
            BW4TAgent agent = null;
        
            if (isHuman) {
                agent = new HumanAgent("Human" + agentCount, environment);
            }
            else {
                agent = newAgent(InitParam.AGENTCLASS.getValue(), "Agent" + agentCount);
            }

            agent.registerEntity(entity);
            environment.registerAgent(agent.getAgentId());
            environment.associateEntity(agent.getAgentId(), entity);

            if (isHuman || agent instanceof HumanAgent) {
                final ClientController control = new ClientController(environment, entity, (HumanAgent) agent);
                control.startupGUI();
                environment.putEntityController(entity, control);
            }

            agent.start();

            agentData.add(agent);
        } catch (InstantiationException | AgentException | RelationException | EntityException e) {
            LOGGER.error("Failed to handle new entity event.", e);
        }
    }

    protected BW4TAgent newAgent(String clazz, String entity) throws InstantiationException {
        try {
            Class<? extends BW4TAgent> c = Class.forName(clazz).asSubclass(BW4TAgent.class);
            Class[] types = new Class[] { String.class, RemoteEnvironment.class };
            Constructor<BW4TAgent> cons = (Constructor<BW4TAgent>) c.getConstructor(types);
            // we use the entityId as name for the agent as well. #2761
            Object[] args = new Object[] { entity, environment };
            BW4TAgent agent = cons.newInstance(args);
            return agent;
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
            // YUK YUK. FIXME
            System.exit(0);
        }
    }
}
