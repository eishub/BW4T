package nl.tudelft.bw4t.client.gui.operations;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.VisualizerSettings;
import nl.tudelft.bw4t.client.gui.data.structures.DoorInfo;
import nl.tudelft.bw4t.client.gui.data.structures.DropZoneInfo;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;

public class GraphicalUpdateOperations {
    /**
     * Add door to the renderer
     * 
     * @param x
     *            center x
     * @param y
     *            center y
     * @param width
     *            width of door
     * @param height
     *            height of door
     * @param roomname
     *            name of room in which door is placed
     */
    public static void addDoor(double x, double y, double width, double height, String roomname,
            BW4TClientGUI bw4tClientGUI) {
        DoorInfo info = new DoorInfo(x, y, width, height);
        for (RoomInfo room : bw4tClientGUI.getEnvironmentDatabase().getRooms()) {
            if (room.getName().equals(roomname)) {
                room.addDoor(info);
                return;
            }
        }

        if (roomname.equals("DropZone")) {
            bw4tClientGUI.getEnvironmentDatabase().getDropZone().addDoor(info);
        }
    }

    /**
     * @param worldX
     *            , the x dimension of the world as specified in the map file
     * @param worldY
     *            , the y dimension of the world as specified in the map file
     */
    public static void setWorldDimensions(int worldX, int worldY) {
        VisualizerSettings.worldX = worldX;
        VisualizerSettings.worldY = worldY;
    }

    /**
     * @param room
     *            , add a representation of a room (Integer[4] with x, y, width
     *            and height)
     */
    public static void addRoom(double x, double y, double width, double height, String name, BW4TClientGUI data) {
        data.getEnvironmentDatabase().getRooms().add(new RoomInfo(x, y, width, height, name));
    }

    /**
     * @param dropZone
     *            , add a representation of the dropzone (Integer[4] with x, y,
     *            width and height)
     */
    public static void addDropZone(Integer[] dropZone, BW4TClientGUI data) {
        data.getEnvironmentDatabase().setDropZone(new DropZoneInfo(dropZone[0], dropZone[1], dropZone[2], dropZone[3]));
    }

}
