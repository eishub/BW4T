package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import eis.exceptions.ActException;
import eis.iilang.Percept;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

public class PickUpEPartnerActionListener extends AbstractClientActionListener {
    private static final Logger LOGGER = Logger.getLogger(PickUpEPartnerActionListener.class);
    
    private final BW4TClientGUI gui;

    public PickUpEPartnerActionListener(ClientController controller, BW4TClientGUI gui) {
        super(controller);
        this.gui = gui;
    }
    
    @Override
    protected void actionWithHumanAgent(ActionEvent arg0) {
        try {
            getController().getHumanAgent().pickUpEPartner();
            gui.getEpartnerMessageButton().setEnabled(true);
            gui.getEpartnerChatPane().setVisible(true);
        } catch (ActException e1) {
            LOGGER.warn("failed to put down the e-partner", e1);
        }
    }
    
    @Override
    protected void actionWithGoalAgent(ActionEvent arg0) {
        Percept percept = new Percept("pickUpEPartner");
        getController().addToBePerformedAction(percept);
    }
}
