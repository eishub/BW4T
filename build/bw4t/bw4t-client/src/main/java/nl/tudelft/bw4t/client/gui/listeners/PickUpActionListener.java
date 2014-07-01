package nl.tudelft.bw4t.client.gui.listeners;

import eis.exceptions.ActException;
import eis.iilang.Percept;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

import org.apache.log4j.Logger;

/**
 * ActionListener that performs the pick up action when that command is pressed
 * in the pop up menu
 */
public class PickUpActionListener extends AbstractClientActionListener {
    /** Logger to report error messages to. */
    private static final Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    /** @param controller - The {@link ClientController} to listen to and interact with. */
    public PickUpActionListener(ClientController controller) {
        super(controller);
    }
    
    @Override
    protected void actionWithHumanAgent(ActionEvent arg0) {
        try {
            getController().getHumanAgent().pickUp();
        } catch (ActException e1) {
            LOGGER.error("Could tell the agent to perform a pickUp action.", e1);
        }
    }
    
    @Override
    protected void actionWithGoalAgent(ActionEvent arg0) {
        List<Percept> percepts = new LinkedList<Percept>();
        Percept percept = new Percept("pickUp");
        percepts.add(percept);
        getController().setToBePerformedAction(percepts);
    }
}
