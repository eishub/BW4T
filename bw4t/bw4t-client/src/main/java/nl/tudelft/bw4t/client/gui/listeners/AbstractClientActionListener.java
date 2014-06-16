package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionListener;
import nl.tudelft.bw4t.client.controller.ClientController;

/** Abstract listener for actions. */
public abstract class AbstractClientActionListener implements ActionListener {

	/** The {@link ClientController} to listen to and interact with. */
    private final ClientController controller;

    /** @param controller - The {@link ClientController} to listen to and interact with. */
    public AbstractClientActionListener(ClientController controller) {
        this.controller = controller;
    }

    public ClientController getController() {
        return controller;
    }

}
