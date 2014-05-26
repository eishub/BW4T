package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.operations.MapOperations;
import nl.tudelft.bw4t.controller.MapRenderSettings;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;

public class ActionPopUpMenu {
	/**
	 * Used for building the pop up menu that displays the actions a user can undertake
	 */
	public static void buildPopUpMenu(BW4TClientGUI gui) {
		// Check if pressing on a color in the goal sequence list
		int startPosX = 0;
		ClientController cmc = gui.getController();
		MapRenderSettings set = cmc.getRenderSettings();
		for (BlockColor color : cmc.getSequence()) {
			Shape colorBounds = new Rectangle2D.Double(startPosX, set.scale(set.getWorldHeight()),
					set.getSequenceBlockSize(), set.getSequenceBlockSize());
			if (colorBounds.contains(gui.getSelectedLocation())) {
				MapOperations.buildPopUpMenuForGoalColor(color, gui);
				gui.getjPopupMenu().show(gui, gui.getSelectedLocation().x, gui.getSelectedLocation().y);
				return;
			}
			startPosX += set.getSequenceBlockSize();
		}

		// Check if pressing on a room
		for (Zone room : cmc.getRooms()) {
			Shape roomBoundaries = set.transformRectangle(room.getBoundingbox().getRectangle());
			if (roomBoundaries.contains(gui.getSelectedLocation())) {
				// Check if pressing on a block
				for (Block box : cmc.getVisibleBlocks()) {

					Shape boxBoundaries = set.transformCenterRectangle(new Rectangle2D.Double(box.getPosition().getX(), box.getPosition().getY(), Block.BLOCK_SIZE, Block.BLOCK_SIZE));
					if (boxBoundaries.contains(gui.getSelectedLocation())) {
						if (MapOperations.closeToBox(box, gui.getController())) {
							RoomMenus.buildPopUpMenuForBeingAtBlock(box, room, gui);
						}
						else {
							RoomMenus.buildPopUpMenuForBlock(box, room, gui);
						}
						gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
						return;
					}
				}
				RoomMenus.buildPopUpMenuRoom(room, gui);
				gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
				return;
			}
		}
		// Check for dropzone
		//		DropZoneInfo dropZone = gui.getEnvironmentDatabase().getDropZone();
		//		Shape dropZoneBoundaries = MapOperations.transformRectangle(new Rectangle2D.Double(dropZone.getX(), dropZone
		//				.getY(), dropZone.getWidth(), dropZone.getHeight()));
		//		if (dropZoneBoundaries.contains(new Point(gui.getSelectedLocation()[0], gui.getSelectedLocation()[1]))) {
		//			RoomMenus.buildPopUpMenuRoom(dropZone, gui);
		//			gui.getjPopupMenu().show(gui, gui.getSelectedLocation()[0], gui.getSelectedLocation()[1]);
		//			return;
		//		}

		// Otherwise it is a hallway
		HallwayMenu.buildPopUpMenuForHallway(gui);
		gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
	}
}
