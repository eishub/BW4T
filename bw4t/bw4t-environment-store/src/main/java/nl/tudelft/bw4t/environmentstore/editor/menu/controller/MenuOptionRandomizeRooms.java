package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.model.RandomMapCreator;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

public class MenuOptionRandomizeRooms extends AbstractMenuOption {

	public MenuOptionRandomizeRooms(MenuBar newView,
			EnvironmentStoreController controller) {
		super(newView, controller);
	}
	
    /**
     * Gets called when the menu item Randomize Rooms is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
    	MapPanelController mpc = super.getMapController();
    	int amountRooms = 0;
    	int rows = mpc.getZoneControllers().length;
    	int cols = mpc.getZoneControllers()[0].length;
    	int maxRooms = RandomMapCreator.maxRoomsPossible(rows, cols);
    	while (amountRooms < 1 || amountRooms > maxRooms) {
    		String answer = JOptionPane.showInputDialog(null, "Please enter the amount of rooms you want.\n"
    			+ "(Must be between 1 and " + maxRooms + ").", 1);
    		try {
    			amountRooms = Integer.parseInt(answer);
    		}
    		catch(NumberFormatException nfx) {
    			amountRooms = 0;
    		}
    	}
    	
        ZoneModel[][] model = RandomMapCreator.createRandomGrid(rows, cols, amountRooms);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	// TODO rooms are not set in the list in mapPanelController. Should be refactored.
            	mpc.getZoneController(i, j).setZoneModel(model[i][j]);
            	mpc.getZoneController(i, j).getUpdateableEditorInterface().update();
            }
        }
        
    }

}