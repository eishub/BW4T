package nl.tudelft.bw4t.client.gui.listeners;

import eis.exceptions.ActException;
import eis.iilang.Identifier;
import eis.iilang.Percept;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.Launcher;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
import org.apache.log4j.Logger;

/**
 * ActionListener that sends a message when the connected menu item is pressed.
 * @author trens
 */
public class MessageSenderActionListener extends AbstractClientActionListener {
	/** Message to send when this listener is fired. */
    private final BW4TMessage message;
    /** Logger to report error messages to. */
    private final static Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);

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
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().sendMessage("all", message);
            } catch (ActException e1) {
                LOGGER.error("Could not send message to all other bots.", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("sendMessage", new Identifier("all"), MessageTranslator.translateMessage(
                    message, getController().getMapController().getTheBot().getName()));
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }
}
