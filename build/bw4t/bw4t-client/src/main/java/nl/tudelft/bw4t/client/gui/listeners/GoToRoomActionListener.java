package nl.tudelft.bw4t.client.gui.listeners;

import eis.exceptions.ActException;
import eis.iilang.Identifier;
import eis.iilang.Percept;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;

import org.apache.log4j.Logger;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 */
public class GoToRoomActionListener extends AbstractClientActionListener {
    /** ID of the room to goTo when this listener is fired. */
    private final String id;
    /** Logger to report error messages to. */
    private static final Logger LOGGER = Logger.getLogger(GoToRoomActionListener.class);

    /**
     * @param id - ID of the room to goTo when this listener is fired.
     * @param controller - The {@link ClientController} to listen to and interact with.
     */
    public GoToRoomActionListener(String id, ClientController controller) {
        super(controller);
        this.id = id;
    }

    @Override
    protected void actionWithHumanAgent(ActionEvent arg0) {
        try {
            getController().getHumanAgent().goTo(id);
        } catch (ActException e1) {
            // Also catch NoServerException. Nothing we can do really.
            LOGGER.error(e1); 
        }
    }

    @Override
    protected void actionWithGoalAgent(ActionEvent arg0) {
        List<Percept> percepts = new LinkedList<Percept>();
        Percept percept = new Percept("goTo", new Identifier(id));
        percepts.add(percept);
        getController().setToBePerformedAction(percepts);
    }
}
