package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

import org.apache.log4j.Logger;

import eis.iilang.Percept;

/**
 * ActionListener that performs the pick up action when that command is pressed in the pop up menu
 */
public class NavigateObstaclesActionListener extends ClientActionListener {
    
    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    public NavigateObstaclesActionListener(ClientController controller) {
        super(controller);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!getController().getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().navigateObstacles();
            } catch (Exception e1) {
                LOGGER.error("Could tell the agent to perform a navigateObstacles action.", e1);
            }
        } else {
            List<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("navigateObstacles");
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }
}
