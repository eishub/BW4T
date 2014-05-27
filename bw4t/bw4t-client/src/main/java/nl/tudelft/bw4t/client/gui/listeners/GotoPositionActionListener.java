package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.Point;
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
public class GotoPositionActionListener implements ActionListener {
    private Point position;
    private BW4TClientInfo bw4tClientInfo;

    public GotoPositionActionListener(Point position,
            BW4TClientInfo bw4tClientInfo) {
        this.position = position;
        this.bw4tClientInfo = bw4tClientInfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!bw4tClientInfo.goal)
            try {
                bw4tClientInfo.humanAgent
                        .goTo(position.getX(), position.getY());
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goTo", new Numeral(position.getX()),
                    new Numeral(position.getY()));
            percepts.add(percept);
            bw4tClientInfo.environmentDatabase.setToBePerformedAction(percepts);
        }
    }
}