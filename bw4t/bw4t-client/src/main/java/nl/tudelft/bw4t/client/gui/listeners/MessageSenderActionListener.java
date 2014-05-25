package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
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
public class MessageSenderActionListener implements ActionListener {
    private final BW4TMessage message;
    private final BW4TClientGUI bw4tClientMapRendererData;
    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    public MessageSenderActionListener(BW4TMessage message, BW4TClientGUI bw4tClientMapRendererData) {
        this.message = message;
        this.bw4tClientMapRendererData = bw4tClientMapRendererData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRendererData.isGoal()) {
            try {
                bw4tClientMapRendererData.getHumanAgent().sendMessage("all", message);
            } catch (Exception e1) {
                LOGGER.error("Could not send message to all other bots.", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("sendMessage", new Identifier("all"), MessageTranslator.translateMessage(
                    message, bw4tClientMapRendererData.getEnvironmentDatabase().getEntityId()));
            percepts.add(percept);
            bw4tClientMapRendererData.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }
    }
}
