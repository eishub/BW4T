package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.InitParam;

/** Abstract listener for actions. */
public abstract class AbstractClientActionListener implements ActionListener {

    /** The {@link ClientController} to listen to and interact with. */
    private final ClientController controller;

    /** @param controller - The {@link ClientController} to listen to and interact with. */
    public AbstractClientActionListener(ClientController controller) {
        assert controller != null;
        this.controller = controller;
    }

    public ClientController getController() {
        return controller;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (!InitParam.GOAL.getBoolValue() || !InitParam.GOALHUMAN.getBoolValue()) {
            actionWithHumanAgent(arg0);
        } else {
            actionWithGoalAgent(arg0);
        }
    }

    protected void actionWithHumanAgent(ActionEvent arg0){
    }

    protected void actionWithGoalAgent(ActionEvent arg0) {
    }
}
