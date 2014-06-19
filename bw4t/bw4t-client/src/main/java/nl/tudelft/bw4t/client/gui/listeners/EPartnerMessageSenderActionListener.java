package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class EPartnerMessageSenderActionListener extends ClientActionListener {
    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);
    private final BW4TMessage message;
    private ClientMapController mapController;
    private BW4TClientGUI clientGUI;

    public EPartnerMessageSenderActionListener(BW4TMessage message, BW4TClientGUI clientGUI) {
        super(clientGUI.getController());
        this.message = message;
        this.clientGUI = clientGUI;
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
        String[] receivers = new String[] { ownName, eParterName };
        if (ownName.equals(eParterName)) {
            receivers = new String[] { eParterName };
        }
        
        /** Sends the message to the receiver(s): */
        for (String name : receivers) {
            if (!Launcher.getEnvironment().isConnectedToGoal()) {
                try {
                    getController().getHumanAgent().sendMessage(name, message);
                } catch (Exception e1) {
                    LOGGER.error("Could not send message to e-partner.", e1);
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
    
    private String findEPartner() {
        ViewEPartner epartner = mapController.getViewEPartner(
                mapController.getTheBot().getHoldingEpartner());
        if (epartner == null)
            return null;
        return epartner.getName();
    }
    
}
