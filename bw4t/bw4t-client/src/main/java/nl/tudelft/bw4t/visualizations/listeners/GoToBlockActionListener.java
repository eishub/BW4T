package nl.tudelft.bw4t.visualizations.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;

import eis.iilang.Numeral;
import eis.iilang.Percept;


/**
 * ActionListener that performs the goTo action when that command is pressed
 * in the pop up menu
 * 
 * @author trens
 * 
 */
public class GoToBlockActionListener implements ActionListener {
    private long boxID;
    private BW4TClientMapRenderer bw4tClientMapRenderer;

    public GoToBlockActionListener(long id) {
        
    }

    public GoToBlockActionListener(Long boxID,
            BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.boxID = boxID;
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRenderer.isGoal())
            try {
                bw4tClientMapRenderer.getHumanAgent().goToBlock(boxID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goToBlock", new Numeral(boxID));
            percepts.add(percept);
            bw4tClientMapRenderer.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }

    }
}
