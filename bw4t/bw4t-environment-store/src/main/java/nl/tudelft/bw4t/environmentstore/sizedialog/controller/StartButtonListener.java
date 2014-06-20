package nl.tudelft.bw4t.environmentstore.sizedialog.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;

public class StartButtonListener implements ActionListener {

    private SizeDialog view;

    /**
     * The constructor for this action listener.
     * 
     * @param pview
     *            The frame with the button in it.
     */
    
    public StartButtonListener(SizeDialog pview) {
        this.view = pview;
    }

    /**
     * Perform the required action 
     * 
     * @param ae
     *            The action event triggering this method.
     */
    public void actionPerformed(ActionEvent ae) {
        MapPanelController mc = new MapPanelController(view.getRows(), view.getColumns());

        EnvironmentStore es = new EnvironmentStore(mc);
        es.setVisible(true);
        view.setVisible(false);
    }
}
