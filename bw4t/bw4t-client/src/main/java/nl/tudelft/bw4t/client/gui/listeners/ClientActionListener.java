package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionListener;

import nl.tudelft.bw4t.client.controller.ClientController;

public abstract class ClientActionListener implements ActionListener {

    private final ClientController controller;

    public ClientActionListener(ClientController controller){
        this.controller = controller;
    }

    public ClientController getController() {
        return controller;
    }

}
