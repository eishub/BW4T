package nl.tudelft.bw4t.scenariogui.epartner.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;

public interface EPartnerViewInterface {
	
	/**
	 * 
	 */
	void updateView();
	
	/**
	 * 
	 * @param epc
	 */
	void setController(EpartnerController epc);
	
	/**
	 * Returns the JTextField containing the epartner name.
	 * 
	 * @return The JTextField containing the epartner name.
	 */
	public String getEpartnerName();

	/**
	 * Returns the JTextField containing the epartner amount.
	 * 
	 * @return The JTextField containing the epartner amount.
	 */
	public int getEpartnerAmount();

	/**
	 * Returns the checkbox enabling or disabling warnings when the bot is left
	 * alone.
	 * 
	 * @return The checkbox.
	 */
	public boolean getForgetMeNot();

	/**
	 * Returns the checkbox enabling or disabling GPS functionality.
	 * 
	 * @return The checkbox.
	 */
	public boolean getGPS();

}
