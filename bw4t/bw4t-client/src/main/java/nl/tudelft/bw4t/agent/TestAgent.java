package nl.tudelft.bw4t.agent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.environment.PerceptsHandler;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.apache.log4j.Logger;

import eis.exceptions.ActException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * The Class TestAgent.
 */
public class TestAgent extends BW4TAgent {

    /** The places. */
    private List<String> places;
    
    /** The state. */
    private String state = "arrived";
    
    /** The next destination. */
    private int nextDestination = 0;

    /** The log4j Logger which displays logs on console. */
    private final static Logger LOGGER = Logger.getLogger(TestAgent.class);

    /**
     * Instantiates a new test agent.
     *
     * @param agentId the agent id for the new agent.
     * @param env the remote environment on which the agent should "live".
     */
    public TestAgent(String agentId, RemoteEnvironment env) {
        super(agentId, env);
        places = new ArrayList<String>();
    }

    /**
     * Retrieve and process percepts in the environment.
     */
    @Override
    public void run() {
        try {
            while (!environmentKilled) {
                percepts();
                action();
                Thread.sleep(200);
            }
        } catch (InterruptedException | ActException e) {
            LOGGER.error("The Agent could not succesfully complete its run.", e);
        }
    }

    /**
     * Get the percepts from the remote environment.
     */
    private void percepts() {
        try {
            List<Percept> percepts = PerceptsHandler.getAllPerceptsFromEntity(entityId, getEnvironment());
            if (percepts != null) {
                processPercepts(percepts);
            }
        } catch (NoEnvironmentException | PerceiveException | NullPointerException e) {
            LOGGER.error("Could not poll the percepts from the environment. No environment was found.", e);
        }
    }

    /**
     * Perform the next action - only if we're not already busy.
     *
     * @throws ActException the act exception
     */
    private void action() throws ActException {
        if (!state.equals("traveling") && places.size() > 0) {
            goTo(places.get(nextDestination));
            nextDestination++;
            if (nextDestination == places.size()) {
                nextDestination = 0;
            }
        }
    }

    /**
     * Process the retrieved percepts.
     *
     * @param percepts , the processed percepts.
     */
    public void processPercepts(List<Percept> percepts) {
        for (Percept percept : percepts) {
            String name = percept.getName();
            if ("place".equals(name)) {
                LinkedList<Parameter> parameters = percept.getParameters();
                places.add(((Identifier) parameters.get(0)).getValue());
            } else if ("state".equals(name)) {
                LinkedList<Parameter> parameters = percept.getParameters();
                state = ((Identifier) parameters.get(0)).getValue();
            } else if ("player".equals(name)) {
                LOGGER.info(percept);
            }
        }
    }
}
