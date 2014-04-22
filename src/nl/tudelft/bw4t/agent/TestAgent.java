package nl.tudelft.bw4t.agent;

import java.util.ArrayList;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;
import eis.exceptions.ActException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

public class TestAgent extends BW4TAgent {

	private ArrayList<String> places;
	private String state = "arrived";

	public TestAgent(String agentId, BW4TRemoteEnvironment env) {
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
			e.printStackTrace();
		}
	}

	/**
	 * get percepts
	 */
	private void percepts() {
		try {
			LinkedList<Percept> percepts = getEnvironment()
					.getAllPerceptsFromEntity(entityId);
			if (percepts != null)
				processPercepts(percepts);
		} catch (PerceiveException e) {
			e.printStackTrace();
		} catch (NoEnvironmentException e) {
			e.printStackTrace();
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

	public void processPercepts(LinkedList<Percept> percepts) {
		for (Percept percept : percepts) {
			String name = percept.getName();

			if (name.equals("place")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				places.add(((Identifier) parameters.get(0)).getValue());
			} else if (name.equals("state")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				state = ((Identifier) parameters.get(0)).getValue();
			} else if (name.equals("player")) {
				System.out.println(percept);
			}
		}
	}
}
