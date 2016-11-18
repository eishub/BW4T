package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Percept;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.map.view.ViewEPartner;

public class EPartnerMessageSenderActionListener extends AbstractClientActionListener {
    /**
     * The log4j Logger which displays logs on console
     */
    private static final Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);
    private final BW4TMessage message;
    private final ClientMapController mapController;

    public EPartnerMessageSenderActionListener(BW4TMessage message, BW4TClientGUI clientGUI) {
        super(clientGUI.getController());
        this.message = message;
        mapController = clientGUI.getController().getMapController();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        /** Finds the names of the receivers of the message: */
        String eParterName = findEPartner();
        if (eParterName == null) {
            return;
        }
        String ownName = getController().getMapController().getTheBot().getName();
        String[] receivers = new String[] {ownName, eParterName};
        if (ownName.equals(eParterName)) {
            receivers = new String[] {eParterName};
        }
        
        /** Sends the message to the receiver(s): */
        sendMessages(ownName, receivers);
        
    }

    /**
     * Sends messages to all the receivers
     * @param ownName 
     * @param receivers 
     */
    private void sendMessages(String ownName, String[] receivers) {
        
        for (String name : receivers) {
            if (!getController().getEnvironment().isConnectedToGoal() || !InitParam.GOALHUMAN.getBoolValue()) {
                try {
                    getController().getHumanAgent().sendMessage(name, message);
                } catch (Exception e1) {
                    LOGGER.error("Could not send message to e-partner.", e1);
                }
            } else {
                Percept percept = new Percept("sendMessage", new Identifier(name), MessageTranslator.translateMessage(
                        message, ownName));
                getController().addToBePerformedAction(percept);
            }
        }
    }
    
    private String findEPartner() {
        ViewEPartner epartner = mapController.getViewEPartner(
                mapController.getTheBot().getHoldingEpartner());
        if (epartner == null) {
            return null;
        }
        return epartner.getName();
    }
    
}
