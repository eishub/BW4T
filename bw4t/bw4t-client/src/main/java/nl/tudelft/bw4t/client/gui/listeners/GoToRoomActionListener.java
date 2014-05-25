package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 * 
 * @author trens
 */
public class GoToRoomActionListener implements ActionListener {
    private final String id;
    private final BW4TClientGUI bw4tClientMapRenderer;

    public GoToRoomActionListener(String id, BW4TClientGUI bw4tClientMapRenderer) {
        this.id = id;
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BW4TClientGUI data = bw4tClientMapRenderer;
        if (!data.isGoal()) {
            try {
                data.getHumanAgent().goTo(id);
            } catch (Exception e1) {
                // Also catch NoServerException. Nothing we can do really.
                e1.printStackTrace();
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goTo", new Identifier(id));
            percepts.add(percept);
            data.getEnvironmentDatabase().setToBePerformedAction(percepts);
        }

    }
}
