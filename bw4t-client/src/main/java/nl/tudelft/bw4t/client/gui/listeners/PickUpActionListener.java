package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import eis.exceptions.ActException;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

/**
 * ActionListener that performs the pick up action when that command is pressed
 * in the pop up menu
 */
public class PickUpActionListener extends AbstractClientActionListener {
	/** Logger to report error messages to. */
	private static final Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);
	private Long boxID;

	/*
	 * @param boxID - ID of the box to goTo when this listener is fired.
	 * 
	 * @param controller - The {@link ClientController} to listen to and
	 * interact with.
	 */
	public PickUpActionListener(Long boxID, ClientController controller) {
		super(controller);
		this.boxID = boxID;
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
		Percept percept = new Percept("pickUp", new Numeral(boxID));
		getController().addToBePerformedAction(percept);
	}
}
