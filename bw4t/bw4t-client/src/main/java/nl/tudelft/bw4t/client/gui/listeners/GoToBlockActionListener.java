package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
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
    private BW4TClientInfo bw4tClientInfo;

    public GoToBlockActionListener(long id) {

    }

    public GoToBlockActionListener(Long boxID, BW4TClientInfo bw4tClientInfo) {
        this.boxID = boxID;
        this.bw4tClientInfo = bw4tClientInfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientInfo.goal)
            try {
                bw4tClientInfo.humanAgent.goToBlock(boxID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goToBlock", new Numeral(boxID));
            percepts.add(percept);
            bw4tClientInfo.environmentDatabase.setToBePerformedAction(percepts);
        }

    }
}
