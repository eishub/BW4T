package nl.tudelft.bw4t.visualizations.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;

import eis.iilang.Percept;

/**
 * ActionListener that performs the pick up action when that command is
 * pressed in the pop up menu
 * 
 * @author trens
 * 
 */
public class PickUpActionListener implements ActionListener {
    
    private BW4TClientMapRenderer bw4tClientMapRenderer;
    
    public PickUpActionListener(BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRenderer.isGoal())
            try {
                bw4tClientMapRenderer.getHumanAgent().pickUp();
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("pickUp");
            percepts.add(percept);
            bw4tClientMapRenderer.setToBePerformedAction(percepts);
        }
    }
}
