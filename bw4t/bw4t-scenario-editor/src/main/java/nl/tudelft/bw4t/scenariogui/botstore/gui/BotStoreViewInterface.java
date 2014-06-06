package nl.tudelft.bw4t.scenariogui.botstore.gui;

import nl.tudelft.bw4t.scenariogui.botstore.controller.BotStoreController;

public interface BotStoreViewInterface {
	
	/**
	 * Updates the BotEditorPanel with the values from the controller
	 */
	void updateView();
	
	/**
	 * Set the controller
	 * @param bsc 
	 */
	void setController(BotStoreController bsc);

}
