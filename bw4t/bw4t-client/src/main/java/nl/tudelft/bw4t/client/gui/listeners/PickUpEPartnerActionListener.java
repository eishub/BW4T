package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

import org.apache.log4j.Logger;

import eis.iilang.Percept;

public class PickUpEPartnerActionListener extends AbstractClientActionListener {
    private static final Logger LOGGER = Logger.getLogger(PickUpEPartnerActionListener.class);
    
    private final BW4TClientGUI gui;

    public PickUpEPartnerActionListener(ClientController controller, BW4TClientGUI gui) {
        super(controller);
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().pickUpEPartner();
                gui.getEpartnerMessageButton().setEnabled(true);
                gui.getEpartnerChatPane().setVisible(true);
            } catch (Exception e1) {
                LOGGER.warn("failed to put down the e-partner", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("pickUpEPartner");
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }

}
