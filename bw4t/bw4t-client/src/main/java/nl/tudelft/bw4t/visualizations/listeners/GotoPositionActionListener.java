package nl.tudelft.bw4t.visualizations.listeners;

import java.awt.Point;
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
public class GotoPositionActionListener implements ActionListener {
    private Point position;
    private BW4TClientMapRenderer bw4tClientMapRenderer;

    public GotoPositionActionListener(Point position, BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.position = position;
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientMapRenderer.isGoal())
            try {
                bw4tClientMapRenderer.getHumanAgent().goTo(position.getX(), position.getY());
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goTo", new Numeral(
                    position.getX()), new Numeral(position.getY()));
            percepts.add(percept);
            bw4tClientMapRenderer.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }
    }
}