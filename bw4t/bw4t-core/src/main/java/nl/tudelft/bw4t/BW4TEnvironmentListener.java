package nl.tudelft.bw4t;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import nl.tudelft.bw4t.agent.BW4TAgent;
import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import eis.EnvironmentListener;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.RelationException;
import eis.iilang.EnvironmentState;
import eis.iilang.Identifier;

/**
 * Class that can be registered to BW4TRemoteEnvironment as EnvironmentListener
 * and will launch new agents when new entities are available
 * <p>
 * This is needed when the BW4TRemoteEnvironment is runned stand-alone. Do not
 * start this when running GOAL, as GOAL already is an environment runner and
 * will associate agents to entities if entities appear.
 * 
 * @author trens
 */
public class BW4TEnvironmentListener implements EnvironmentListener {

	private int agentCount;

	/**
	 * This map associates agents with a renderer. I suppose agents not having a
	 * renderer do not end up in this list.
	 */
	private HashMap<BW4TAgent, BW4TClientMapRenderer> agentData = new HashMap<BW4TAgent, BW4TClientMapRenderer>();

	private BW4TRemoteEnvironment environment;

	public BW4TEnvironmentListener(BW4TRemoteEnvironment env) {
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
						agentData.get(agentB).setStop();
						agentData.get(agentB).getFrame().dispose();
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
	}

	/**
	 * Handle a new entity, load the human agent if it is of type human
	 * otherwise load the agent that was specified in the program argument or
	 * the default one (BW4TAgent)
	 * 
	 * @param entityId
	 *            , the new entity
	 * 
	 */
	@Override
	public void handleNewEntity(String entityId) {
		try {
			handleNewEntity1(entityId);
		} catch (Exception e) {
			System.out.println("failed to handle new entity event");
			e.printStackTrace();
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
	private void handleNewEntity1(String entityId) throws EntityException,
			AgentException, ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, IOException, RelationException {
		if (environment.getType(entityId).equals("human")) {
			HumanAgent agent = new HumanAgent("Agent" + agentCount, environment);
			agent.registerEntity(entityId);
			environment.registerAgent(agent.getAgentId());
			environment.associateEntity(agent.getAgentId(), entityId);
			BW4TClientMapRenderer renderer;
			renderer = new BW4TClientMapRenderer(environment, entityId, agent);

			agent.start();
			agentCount++;

			agentData.put(agent, renderer);
		} else {
			String agentClassName = "nl.tudelft.bw4t.agent.BW4TAgent";
			Identifier agentClass = ((Identifier) environment
					.getInitParameters().get("agentclass"));

			if (agentClass != null) {
				agentClassName = agentClass.getValue();
			}
			Class<? extends BW4TAgent> c = (Class<? extends BW4TAgent>) Class
					.forName(agentClassName);
			Class[] types = new Class[] { String.class,
					BW4TRemoteEnvironment.class };
			Constructor<BW4TAgent> cons = (Constructor<BW4TAgent>) c
					.getConstructor(types);
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
		if (newState.equals(EnvironmentState.KILLED)) {
			System.exit(0); // YUK YUK. FIXME
		}
	}
}
