package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;

import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Percept;
/**
 * ActionListener that sends a message when the connected menu item is pressed.
 */
public class MessageSenderActionListener extends AbstractClientActionListener {
    /** Message to send when this listener is fired. */
    private final BW4TMessage message;

    /** Logger to report error messages to. */
    private static final Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);

    /**
     * @param message - The {@link BW4TMessage} to send when this listener is fired.
     * @param controller - The {@link ClientController} to listen to and interact with.
     */
    public MessageSenderActionListener(BW4TMessage message, ClientController controller) {
        super(controller);
        this.message = message;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /** Finds the names of the receivers of the message: */
        String receiver = (String) getController().getGui().getAgentSelector().getModel().getSelectedItem();
        String ownName = getController().getMapController().getTheBot().getName();
        String[] receivers = new String[] {ownName, receiver};
        if ("all".equals(receiver) || ownName.equals(receiver)) {
            receivers = new String[] {receiver};
        }
        
        /** Sends the message to the receiver(s): */
        sendMessages(ownName, receivers);
        
    }
    
    
    /**
     * Sends the message to the receiver(s): 
     * @param ownName 
     * @param receivers 
     */
    private void sendMessages(String ownName, String[] receivers) {
        for (String name : receivers) {
            if (!getController().getEnvironment().isConnectedToGoal()) {
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
