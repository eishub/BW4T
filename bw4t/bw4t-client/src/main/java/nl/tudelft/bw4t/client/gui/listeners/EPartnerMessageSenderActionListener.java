package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;
import nl.tudelft.bw4t.map.NewMap;

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

    public EPartnerMessageSenderActionListener(
            BW4TMessage message, ClientController controller, ClientMapController mapController) {
        super(controller);
        this.message = message;
        this.mapController = mapController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                String epartnerName = mapController.getViewEPartner(mapController.getTheBot().getHoldingEpartner()).getName();
                getController().getHumanAgent().sendMessage(
                        epartnerName, message);
            } catch (Exception e1) {
                LOGGER.error("Could not send message to e-partner.", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("sendMessage", new Identifier("epartner"), MessageTranslator.translateMessage(
                    message, getController().getMapController().getTheBot().getName()));
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }
}
