package nl.tudelft.bw4t.scenariogui.epartner.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;

public interface EPartnerViewInterface {
	
	/**
	 * 
	 */
	void update();
	
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
	public JTextField getEpartnerName();

	/**
	 * Returns the JTextField containing the epartner amount.
	 * 
	 * @return The JTextField containing the epartner amount.
	 */
	public JTextField getEpartnerAmount();

	/**
	 * Returns the used apply button.
	 * 
	 * @return The apply button.
	 */
	public JButton getApplyButton();

	/**
	 * Returns the reset button used.
	 * 
	 * @return The reset button.
	 */
	public JButton getResetButton();

	/**
	 * Returns the currently used cancel button.
	 * 
	 * @return The cancel button.
	 */
	public JButton getCancelButton();

	/**
	 * Returns the checkbox enabling or disabling warnings when the bot is left
	 * alone.
	 * 
	 * @return The checkbox.
	 */
	public JCheckBox getLeftAloneCheckbox();

	/**
	 * Returns the checkbox enabling or disabling GPS functionality.
	 * 
	 * @return The checkbox.
	 */
	public JCheckBox getGPSCheckbox();

}
