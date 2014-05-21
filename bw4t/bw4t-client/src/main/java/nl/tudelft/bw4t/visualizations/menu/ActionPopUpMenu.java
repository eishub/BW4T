package nl.tudelft.bw4t.visualizations.menu;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Constants;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import nl.tudelft.bw4t.visualizations.BasicOperations;
import nl.tudelft.bw4t.visualizations.VisualizerSettings;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;
import nl.tudelft.bw4t.visualizations.data.DropZoneInfo;
import nl.tudelft.bw4t.visualizations.data.RoomInfo;

public class ActionPopUpMenu {
    /**
     * Used for building the pop up menu that displays the actions a user can
     * undertake
     */
    public static void buildPopUpMenu(BW4TClientMapRenderer bw4tClientMapRenderer) {
        // Check if pressing on a color in the goal sequence list
        int startPosX = 0;
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        for (BlockColor color : data.environmentDatabase.getSequence()) {
            Shape colorBounds = new Rectangle2D.Double(startPosX,
                    VisualizerSettings.worldY * VisualizerSettings.scale, 20,
                    20);
            if (colorBounds.contains(new Point(data.selectedLocation[0],
                    data.selectedLocation[1]))) {
                BasicOperations.buildPopUpMenuForGoalColor(color, bw4tClientMapRenderer);
                data.jPopupMenu.show(bw4tClientMapRenderer, data.selectedLocation[0],
                        data.selectedLocation[1]);
                return;
            }
            startPosX += 20;
        }

        // Check if pressing on a room
        for (RoomInfo room : data.environmentDatabase.getRooms()) {
            Shape roomBoundaries = BasicOperations.transformRectangle(new Rectangle2D.Double(
                    room.getX(), room.getY(), room.getWidth(), room.getHeight()));
            if (roomBoundaries.contains(new Point(data.selectedLocation[0],
                    data.selectedLocation[1]))) {
                // Check if pressing on a block
                for (Long boxID : data.environmentDatabase.getVisibleBlocks()) {
                    HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = data.environmentDatabase
                            .getObjectPositions();
                    Shape boxBoundaries = BasicOperations.transformRectangle(new Rectangle2D.Double(
                            objectPositions.get(boxID).getX(), objectPositions
                                    .get(boxID).getY(), Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE));
                    if (boxBoundaries
                            .contains(new Point(data.selectedLocation[0],
                                    data.selectedLocation[1]))) {
                        if (BasicOperations.closeToBox(boxID, data)) {
                            RoomMenu.buildPopUpMenuForBeingAtBlock(boxID, room, bw4tClientMapRenderer);
                            data.jPopupMenu.show(bw4tClientMapRenderer,
                                    data.selectedLocation[0],
                                    data.selectedLocation[1]);
                        } else {
                            RoomMenu.buildPopUpMenuForBlock(boxID, room, bw4tClientMapRenderer);
                            data.jPopupMenu.show(bw4tClientMapRenderer,
                                    data.selectedLocation[0],
                                    data.selectedLocation[1]);
                        }
                        return;
                    }
                }
                RoomMenu.buildPopUpMenuRoom(room, bw4tClientMapRenderer);
                data.jPopupMenu.show(bw4tClientMapRenderer, data.selectedLocation[0],
                        data.selectedLocation[1]);
                return;
            }
        }
        // Check for dropzone
        DropZoneInfo dropZone = data.environmentDatabase.getDropZone();
        Shape dropZoneBoundaries = BasicOperations.transformRectangle(new Rectangle2D.Double(
                dropZone.getX(), dropZone.getY(), dropZone.getWidth(),
                dropZone.getHeight()));
        if (dropZoneBoundaries.contains(new Point(data.selectedLocation[0],
                data.selectedLocation[1]))) {
            RoomMenu.buildPopUpMenuRoom(dropZone, bw4tClientMapRenderer);
            data.jPopupMenu.show(bw4tClientMapRenderer, data.selectedLocation[0],
                    data.selectedLocation[1]);
            return;
        }

        // Otherwise it is a hallway
        HallwayMenu.buildPopUpMenuForHallway(bw4tClientMapRenderer);
        data.jPopupMenu.show(bw4tClientMapRenderer, data.selectedLocation[0],
                data.selectedLocation[1]);
    }
}
