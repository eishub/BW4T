package nl.tudelft.bw4t.scenariogui.epartner.gui;

import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;

/**
 * EPartnerViewInterface for the EpartnerFrame.
 *
 */
public interface EPartnerViewInterface {
    
    void updateView();
    
    void setController(EpartnerController epc);
    
    String getEpartnerName();

    int getEpartnerAmount();

    boolean getForgetMeNot();

    boolean getGPS();

    String getEpartnerReference();

    String getEpartnerGoalFile();
}
