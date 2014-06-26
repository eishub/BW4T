package nl.tudelft.bw4t.client.gui.listeners;

import eis.exceptions.ActException;
import eis.iilang.Numeral;
import eis.iilang.Percept;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientController;

import org.apache.log4j.Logger;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 */
public class GotoPositionActionListener extends AbstractClientActionListener {
    /** The point to goTo when this listener is fired. */
    private final Point position;
    /** Logger to report error messages to. */
    private static final Logger LOGGER = Logger.getLogger(GotoPositionActionListener.class);

    /**
     * @param position - The {@link Point} to go to when this listener is fired.
     * @param control - The {@link ClientController} to listen to and interact with.
     */
    public GotoPositionActionListener(Point position, ClientController control) {
        super(control);
        this.position = position;
    }

    @Override
    protected void actionWithHumanAgent(ActionEvent arg0) {
        try {
            getController().getHumanAgent().goTo(position.getX(), position.getY());
        } catch (ActException e1) {
            LOGGER.error(e1); 
        }
    }

    @Override
    protected void actionWithGoalAgent(ActionEvent arg0) {
        List<Percept> percepts = new LinkedList<Percept>();
        Percept percept = new Percept("goTo", new Numeral(position.getX()), new Numeral(position.getY()));
        percepts.add(percept);
        getController().setToBePerformedAction(percepts);
    }
}
