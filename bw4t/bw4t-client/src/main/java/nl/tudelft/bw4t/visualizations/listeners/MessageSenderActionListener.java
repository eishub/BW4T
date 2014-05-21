package nl.tudelft.bw4t.visualizations.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * ActionListener that sends a message when the connected menu item is
 * pressed.
 * 
 * @author trens
 * 
 */
public class MessageSenderActionListener implements ActionListener {
    private BW4TMessage message;
    private BW4TClientMapRenderer bw4tClientMapRenderer;

    public MessageSenderActionListener(BW4TMessage message, BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.message = message;
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRenderer.isGoal())
            try {
                bw4tClientMapRenderer.getHumanAgent().sendMessage("all", message);
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("sendMessage", new Identifier(
                    "all"), MessageTranslator.translateMessage(message,
                            bw4tClientMapRenderer.getEntityId()));
            percepts.add(percept);
            bw4tClientMapRenderer.setToBePerformedAction(percepts);
        }
    }
}