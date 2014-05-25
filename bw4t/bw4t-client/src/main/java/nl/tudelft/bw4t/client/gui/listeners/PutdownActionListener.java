package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import eis.iilang.Percept;

/**
 * ActionListener that performs the put down action when that command is pressed
 * in the pop up menu
 * 
 * @author trens
 */
public class PutdownActionListener implements ActionListener {
    private final BW4TClientGUI bw4tClientInfo;

    public PutdownActionListener(BW4TClientGUI bw4tClientInfo) {
        this.bw4tClientInfo = bw4tClientInfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientInfo.isGoal()) {
            try {
                bw4tClientInfo.getHumanAgent().putDown();
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("putDown");
            percepts.add(percept);
            bw4tClientInfo.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }
    }
}
