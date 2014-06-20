package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.environment.Launcher;
import eis.exceptions.ActException;
import eis.iilang.Numeral;
import eis.iilang.Percept;

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
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
                try {
					getController().getHumanAgent().goToBlock(boxID);
				} catch (ActException e1) {
					LOGGER.error(e1);
				}
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goToBlock", new Numeral(boxID));
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }

    }
}
