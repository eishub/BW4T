package nl.tudelft.bw4t.visualizations.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * ActionListener that performs the goTo action when that command is pressed
 * in the pop up menu
 * 
 * @author trens
 * 
 */
public class GoToRoomActionListener implements ActionListener {
    private String id;
    private BW4TClientMapRenderer bw4tClientMapRenderer;

    public GoToRoomActionListener(String id, BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.id = id;
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRenderer.isGoal())
            try {
                bw4tClientMapRenderer.getHumanAgent().goTo(id);
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goTo", new Identifier(id));
            percepts.add(percept);
            bw4tClientMapRenderer.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }

    }
}