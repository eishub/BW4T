package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 * 
 * @author trens
 */
public class GoToBlockActionListener implements ActionListener {
    private long boxID;
    private BW4TClientGUI bw4tClientGUI;

    public GoToBlockActionListener(long id) {

    }

    public GoToBlockActionListener(Long boxID, BW4TClientGUI bw4tClientGUI) {
        this.boxID = boxID;
        this.bw4tClientGUI = bw4tClientGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientGUI.isGoal()) {
            try {
                bw4tClientGUI.getHumanAgent().goToBlock(boxID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goToBlock", new Numeral(boxID));
            percepts.add(percept);
            bw4tClientGUI.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }

    }
}
