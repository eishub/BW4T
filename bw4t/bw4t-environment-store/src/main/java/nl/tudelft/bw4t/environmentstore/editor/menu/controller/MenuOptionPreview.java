package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.map.MapFormatException;

public class MenuOptionPreview extends AbstractMenuOption {

	public MenuOptionPreview(MenuBar newView,
			EnvironmentStoreController controller) {
		super(newView, controller);
	}
	
    /**
     * Gets called when the menu item save as is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
    	try {
    		previewMap();
    	}
        catch (MapFormatException mfe) {
        	if (mfe.getMessage().equals("No DropZone found on the map!")) {
        		EnvironmentStore.showDialog("The current map does not have a drop zone.");
        	}
        	else {
        		EnvironmentStore.showDialog("The current map does not have a start zone.");
        	}
        }
    }

}