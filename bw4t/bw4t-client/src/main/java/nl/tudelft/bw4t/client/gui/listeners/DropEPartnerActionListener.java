package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import eis.iilang.Percept;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.Launcher;

public class DropEPartnerActionListener extends ClientActionListener {
    private static final Logger LOGGER = Logger.getLogger(DropEPartnerActionListener.class);

    public DropEPartnerActionListener(ClientController controller) {
        super(controller);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().putDownEPartner();
            } catch (Exception e1) {
                LOGGER.warn("failed to put down the e-partner", e1);
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("putDownEPartner");
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }

}
