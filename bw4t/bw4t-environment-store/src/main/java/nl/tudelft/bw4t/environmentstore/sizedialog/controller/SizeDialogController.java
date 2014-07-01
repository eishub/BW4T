package nl.tudelft.bw4t.environmentstore.sizedialog.controller;

import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;


/**
 * The SizeDialogController class serves as a controller for the SizeDialog
 */
public class SizeDialogController {

    /**
     * the view being controlled
     */
    private SizeDialog view;
    
    
    /**
     * The SizeDialogController class takes care of all the ActionListeners.
     */
    public SizeDialogController(SizeDialog pview) {
        this.view = pview;
    
        view.getStartButton().addActionListener(
                new StartButtonListener(getMainView()));
    }
    
    /**
     * Return the view being controlled.
     * @return The JFrame being controlled.
     */
    public final SizeDialog getMainView() {
        return view;
    }
}

   

