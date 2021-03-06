package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import eis.exceptions.ActException;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import nl.tudelft.bw4t.client.controller.ClientController;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 */
public class GoToBlockActionListener extends AbstractClientActionListener {
    /** ID of the box to goTo when this listener is fired. */
    private final long boxID;
    /** Logger to report error messages to. */
    private static final Logger LOGGER = Logger.getLogger(GoToBlockActionListener.class);

    /**
     * @param boxID - ID of the box to goTo when this listener is fired.
     * @param control - The {@link ClientController} to listen to and interact with.
     */
    public GoToBlockActionListener(Long boxID, ClientController control) {
        super(control);
        this.boxID = boxID;
    }

    @Override
    protected void actionWithHumanAgent(ActionEvent arg0) {
        try {
            getController().getHumanAgent().goToBlock(boxID);
        } catch (ActException e1) {
            LOGGER.error(e1);
        }
    }

    @Override
    protected void actionWithGoalAgent(ActionEvent arg0) {
        Percept percept = new Percept("goToBlock", new Numeral(boxID));
        getController().addToBePerformedAction(percept);
    }
}
