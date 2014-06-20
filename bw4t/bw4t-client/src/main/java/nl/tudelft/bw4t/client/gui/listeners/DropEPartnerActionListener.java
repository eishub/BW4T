package nl.tudelft.bw4t.client.gui.listeners;

import eis.iilang.Percept;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

import org.apache.log4j.Logger;

public class DropEPartnerActionListener extends AbstractClientActionListener {
    private static final Logger LOGGER = Logger.getLogger(DropEPartnerActionListener.class);
    private final BW4TClientGUI gui;

    public DropEPartnerActionListener(ClientController controller, BW4TClientGUI gui) {
        super(controller);
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!getController().getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().putDownEPartner();
                gui.getEpartnerMessageButton().setEnabled(false);
            } catch (Exception e1) {
                LOGGER.warn("failed to put down the e-partner", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("putDownEPartner");
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }

}
