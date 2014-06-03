package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.Launcher;

import org.apache.log4j.Logger;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 * 
 * @author trens
 */
public class GoToRoomActionListener extends ClientActionListener {
    private final String id;

    private static final Logger LOGGER = Logger.getLogger(GoToRoomActionListener.class);

    public GoToRoomActionListener(String id, ClientController controller) {
        super(controller);
        this.id = id;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().goTo(id);
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                LOGGER.error(e1); 
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goTo", new Identifier(id));
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }

    }
}
