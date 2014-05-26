package nl.tudelft.bw4t.client.gui.operations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

public class ProcessingOperations {
	/**
	 * The log4j Logger which displays logs on console
	 */
	private final static Logger LOGGER = Logger.getLogger(ProcessingOperations.class);

	/**
	 * Processes the percepts list from the perceptPoller and updates the following data: - Visible blocks - Holding a
	 * block - Location of the entity - Group goal sequence - Room occupation
	 * 
	 * @param percepts
	 *            , a list of all received percepts
	 */
	public static void processPercepts(List<Percept> percepts, BW4TClientGUI data) {

		// First create updated information based on the new percepts.
		for (Percept percept : percepts) {
			String name = percept.getName();
			LinkedList<Parameter> parameters = percept.getParameters();

			// Initialize room ids in all rooms gotten from the map loader
			// Should only be done one time
			if (name.equals("player")) {
				String player = ((Identifier) parameters.get(0)).getValue();
				if (!data.getEnvironmentDatabase().getOtherPlayers().contains(player)) {
					data.getEnvironmentDatabase().getOtherPlayers().add(player);
				}
			}
			// Update chat history
			else if (name.equals("message")) {
				ParameterList parameterList = ((ParameterList) parameters.get(0));

				Iterator<Parameter> iterator = parameterList.iterator();

				String sender = ((Identifier) iterator.next()).getValue();
				String message = ((Identifier) iterator.next()).getValue();

				data.getChatSession().append(sender + " : " + message + "\n");
				data.getChatSession().setCaretPosition(data.getChatSession().getDocument().getLength());

				ArrayList<String> newMessage = new ArrayList<String>();
				newMessage.add(sender);
				newMessage.add(message);
				data.getEnvironmentDatabase().getChatHistory().add(newMessage);
			}

		}

	}
}
