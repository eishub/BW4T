package nl.tudelft.bw4t.scenariogui.epartner.gui;

import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;

/**
 * EPartnerViewInterface for the EpartnerFrame
 *
 */
public interface EPartnerViewInterface {
    
    /**
     * Updates the EpartnerFrame with the values from the controller
     */
    void updateView();
    
    /**
     * Set the controller
     * @param epc 
     */
    void setController(EpartnerController epc);
    
    /**
     * Returns the ePartner name.
     * 
     * @return the ePartner name.
     */
    String getEpartnerName();

    /**
     * Returns the ePartner amount.
     * 
     * @return the ePartner amount.
     */
    int getEpartnerAmount();

    /**
     * Return true if the forgetMeNot function is used.
     * 
     * @return true or false
     */
    boolean getForgetMeNot();

    /**
     * Returns true if the GPS function is used.
     * 
     * @return true or false
     */
    boolean getGPS();

    /**
     * Returns the reference name in goal.
     * 
     * @return The reference name in goal.
     */
    String getEpartnerReference();

    /**
     * Returns the goal file name.
     * 
     * @return The goal file name.
     */
    String getEpartnerGoalFile();
}
