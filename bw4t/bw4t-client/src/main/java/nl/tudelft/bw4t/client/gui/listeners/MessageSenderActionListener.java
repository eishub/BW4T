package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;

import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * ActionListener that sends a message when the connected menu item is pressed.
 */
public class MessageSenderActionListener extends ClientActionListener {
    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);
    private final BW4TMessage message;
    private final BW4TClientGUI gui;

    public MessageSenderActionListener(BW4TMessage message, BW4TClientGUI gui) {
        super(gui.getController());
        this.message = message;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        /** Finds the names of the receivers of the message: */
        String receiver = (String) gui.getAgentSelector().getModel().getSelectedItem();
        String ownName = getController().getMapController().getTheBot().getName();
        String[] receivers = new String[] { ownName, receiver };
        if ("all".equals(receiver) || ownName.equals(receiver)) {
            receivers = new String[] { receiver };
        }
        
        /** Sends the message to the receiver(s): */
        for (String name : receivers) {
            if (!Launcher.getEnvironment().isConnectedToGoal()) {
                try {
                    getController().getHumanAgent().sendMessage(name, message);
                } catch (Exception e1) {
                    LOGGER.error("Could not send message to all other bots.", e1);
                }
            } else {
                List<Percept> percepts = new LinkedList<Percept>();
                Percept percept = new Percept("sendMessage", new Identifier(name), MessageTranslator.translateMessage(
                        message, ownName));
                percepts.add(percept);
                getController().setToBePerformedAction(percepts);
            }
        }
        
    }
}
