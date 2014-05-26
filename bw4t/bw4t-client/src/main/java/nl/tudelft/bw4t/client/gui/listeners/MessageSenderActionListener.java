package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.Launcher;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;

import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * ActionListener that sends a message when the connected menu item is pressed.
 * 
 * @author trens
 */
public class MessageSenderActionListener extends ClientActionListener {
	/**
	 * The log4j Logger which displays logs on console
	 */
	private final static Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);
	private final BW4TMessage message;

	public MessageSenderActionListener(BW4TMessage message, ClientController controller) {
		super(controller);
		this.message = message;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!Launcher.getEnvironment().isConnectedToGoal()) {
			try {
				getController().getHumanAgent().sendMessage("all", message);
			} catch (Exception e1) {
				LOGGER.error("Could not send message to all other bots.", e1);
			}
		} else {
			List<Percept> percepts = new LinkedList<Percept>();
			Percept percept = new Percept("sendMessage", new Identifier("all"), MessageTranslator.translateMessage(
					message, getController().getTheBot().getName()));
			percepts.add(percept);
			getController().setToBePerformedAction(percepts);
		}
	}
}
