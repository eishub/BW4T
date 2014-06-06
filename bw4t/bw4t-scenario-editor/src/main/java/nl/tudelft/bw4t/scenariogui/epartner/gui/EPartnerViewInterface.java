package nl.tudelft.bw4t.scenariogui.epartner.gui;

import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;

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

    String getEpartnerReference();

    String getEpartnerGoalFile();

}