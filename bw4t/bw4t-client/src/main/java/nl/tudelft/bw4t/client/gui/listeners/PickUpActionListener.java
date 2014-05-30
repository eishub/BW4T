package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.Launcher;

import org.apache.log4j.Logger;

import eis.iilang.Percept;

/**
 * ActionListener that performs the pick up action when that command is pressed
 * in the pop up menu
 * 
 * @author trens
 */
public class PickUpActionListener extends ClientActionListener {
    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    public PickUpActionListener(ClientController controller) {
        super(controller);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().pickUp();
            } catch (Exception e1) {
                LOGGER.error("Could tell the agent to perform a pickUp action.", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("pickUp");
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }
}
