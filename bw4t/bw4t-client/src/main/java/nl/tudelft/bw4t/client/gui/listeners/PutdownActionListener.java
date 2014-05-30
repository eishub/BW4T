package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.Launcher;
import eis.iilang.Percept;

/**
 * ActionListener that performs the put down action when that command is pressed
 * in the pop up menu
 * 
 * @author trens
 */
public class PutdownActionListener extends ClientActionListener {

    public PutdownActionListener(ClientController controller) {
        super(controller);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().putDown();
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("putDown");
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }
}
