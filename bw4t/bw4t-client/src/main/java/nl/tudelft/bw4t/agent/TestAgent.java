package nl.tudelft.bw4t.agent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.environment.handlers.PerceptsHandler;

import org.apache.log4j.Logger;

import eis.exceptions.ActException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

public class TestAgent extends BW4TAgent {

    private List<String> places;
    private String state = "arrived";

    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(TestAgent.class);

    public TestAgent(String agentId, RemoteEnvironment env) {
        super(agentId, env);
        places = new ArrayList<String>();
    }

    @Override
    public void run() {
        try {
            while (!environmentKilled) {
                percepts();
                action();
                Thread.sleep(200);
            }
        } catch (Exception e) {
            LOGGER.error("The Agent could not succesfully complete its run.", e);
        }
    }

    /**
     * get percepts
     */
    private void percepts() {
        try {
            List<Percept> percepts = PerceptsHandler.getAllPerceptsFromEntity(
                    entityId, getEnvironment());
            if (percepts != null) {
                processPercepts(percepts);
            }
        } catch (PerceiveException e) {
            LOGGER.error("Could not poll the percepts from the environment.", e);
        } catch (NoEnvironmentException e) {
            LOGGER.error(
                    "Could not poll the percepts from the environment. No environment was found.",
                    e);
        }
    }

    int nextDestination = 0;

    /**
     * do next action - only if we're not already busy
     * 
     * @throws ActException
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
