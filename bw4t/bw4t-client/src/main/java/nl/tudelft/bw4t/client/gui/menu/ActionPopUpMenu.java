package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.VisualizerSettings;
import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.client.gui.data.structures.DropZoneInfo;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;
import nl.tudelft.bw4t.client.gui.operations.MapOperations;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Constants;

public class ActionPopUpMenu {
    /**
     * Used for building the pop up menu that displays the actions a user can
     * undertake
     */
    public static void buildPopUpMenu(BW4TClientGUI bw4tClientMapRenderer) {
        // Check if pressing on a color in the goal sequence list
        int startPosX = 0;
        BW4TClientInfo bw4tClientInfo = bw4tClientMapRenderer
                .getBW4TClientInfo();
        for (BlockColor color : bw4tClientInfo.environmentDatabase
                .getSequence()) {
            Shape colorBounds = new Rectangle2D.Double(startPosX,
                    VisualizerSettings.worldY * VisualizerSettings.scale, 20,
                    20);
            if (colorBounds.contains(new Point(
                    bw4tClientInfo.selectedLocation[0],
                    bw4tClientInfo.selectedLocation[1]))) {
                MapOperations.buildPopUpMenuForGoalColor(color,
                        bw4tClientMapRenderer);
                bw4tClientInfo.jPopupMenu.show(bw4tClientMapRenderer,
                        bw4tClientInfo.selectedLocation[0],
                        bw4tClientInfo.selectedLocation[1]);
                return;
            }
            startPosX += 20;
        }

        // Check if pressing on a room
        for (RoomInfo room : bw4tClientInfo.environmentDatabase.getRooms()) {
            Shape roomBoundaries = MapOperations
                    .transformRectangle(new Rectangle2D.Double(room.getX(),
                            room.getY(), room.getWidth(), room.getHeight()));
            if (roomBoundaries.contains(new Point(
                    bw4tClientInfo.selectedLocation[0],
                    bw4tClientInfo.selectedLocation[1]))) {
                // Check if pressing on a block
                for (Long boxID : bw4tClientInfo.environmentDatabase
                        .getVisibleBlocks()) {
                    HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = bw4tClientInfo.environmentDatabase
                            .getObjectPositions();
                    Shape boxBoundaries = MapOperations
                            .transformRectangle(new Rectangle2D.Double(
                                    objectPositions.get(boxID).getX(),
                                    objectPositions.get(boxID).getY(),
                                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE));
                    if (boxBoundaries.contains(new Point(
                            bw4tClientInfo.selectedLocation[0],
                            bw4tClientInfo.selectedLocation[1]))) {
                        if (MapOperations.closeToBox(boxID, bw4tClientInfo)) {
                            RoomMenus.buildPopUpMenuForBeingAtBlock(boxID,
                                    room, bw4tClientMapRenderer);
                            bw4tClientInfo.jPopupMenu.show(
                                    bw4tClientMapRenderer,
                                    bw4tClientInfo.selectedLocation[0],
                                    bw4tClientInfo.selectedLocation[1]);
                        } else {
                            RoomMenus.buildPopUpMenuForBlock(boxID, room,
                                    bw4tClientMapRenderer);
                            bw4tClientInfo.jPopupMenu.show(
                                    bw4tClientMapRenderer,
                                    bw4tClientInfo.selectedLocation[0],
                                    bw4tClientInfo.selectedLocation[1]);
                        }
                        return;
                    }
                }
                RoomMenus.buildPopUpMenuRoom(room, bw4tClientMapRenderer);
                bw4tClientInfo.jPopupMenu.show(bw4tClientMapRenderer,
                        bw4tClientInfo.selectedLocation[0],
                        bw4tClientInfo.selectedLocation[1]);
                return;
            }
        }
        // Check for dropzone
        DropZoneInfo dropZone = bw4tClientInfo.environmentDatabase
                .getDropZone();
        Shape dropZoneBoundaries = MapOperations
                .transformRectangle(new Rectangle2D.Double(dropZone.getX(),
                        dropZone.getY(), dropZone.getWidth(), dropZone
                                .getHeight()));
        if (dropZoneBoundaries.contains(new Point(
                bw4tClientInfo.selectedLocation[0],
                bw4tClientInfo.selectedLocation[1]))) {
            RoomMenus.buildPopUpMenuRoom(dropZone, bw4tClientMapRenderer);
            bw4tClientInfo.jPopupMenu.show(bw4tClientMapRenderer,
                    bw4tClientInfo.selectedLocation[0],
                    bw4tClientInfo.selectedLocation[1]);
            return;
        }

        // Otherwise it is a hallway
        HallwayMenu.buildPopUpMenuForHallway(bw4tClientInfo);
        bw4tClientInfo.jPopupMenu.show(bw4tClientMapRenderer,
                bw4tClientInfo.selectedLocation[0],
                bw4tClientInfo.selectedLocation[1]);
    }
}
