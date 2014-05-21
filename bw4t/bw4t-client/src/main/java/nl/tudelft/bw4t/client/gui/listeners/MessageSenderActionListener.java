package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
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
    private BW4TClientInfo bw4tClientMapRendererData;

    public MessageSenderActionListener(BW4TMessage message, BW4TClientInfo bw4tClientMapRendererData) {
        this.message = message;
        this.bw4tClientMapRendererData = bw4tClientMapRendererData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRendererData.goal)
            try {
                bw4tClientMapRendererData.humanAgent.sendMessage("all", message);
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("sendMessage", new Identifier(
                    "all"), MessageTranslator.translateMessage(message,
                            bw4tClientMapRendererData.environmentDatabase.getEntityId()));
            percepts.add(percept);
            bw4tClientMapRendererData.environmentDatabase.setToBePerformedAction(percepts);
        }
    }
}