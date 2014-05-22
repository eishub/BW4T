package nl.tudelft.bw4t;

import java.io.IOException;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.operations.GraphicalUpdateOperations;
import nl.tudelft.bw4t.map.Constants;
import nl.tudelft.bw4t.map.Door.Orientation;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.map.Zone;

/**
 * This class is loads maps into the {@link BW4TClientGUI}.
 * 
 * @author W.Pasman
 */
public class RendererMapLoader {

    /**
     * Used for loading a {@link NewMap} for the BW4TRenderer into Repasts.
     * 
     * @param location
     *            , the location of the map
     * @param renderer
     *            , the BW4TRenderer
     * @throws IOException
     *             when reading the file produces errors
     */
    public static void loadMap(NewMap map, BW4TClientGUI renderer)
            throws IOException {

        Point size = map.getArea();
        GraphicalUpdateOperations.setWorldDimensions((int) size.getX(),
                (int) size.getY());

        createDropZone(map.getZone("DropZone").getBoundingbox(), renderer);
        for (Zone navpt : map.getZones()) {
            // HACK: client uses navpt as items to navigate to. You can navigate
            // to any zone.
            // these labels are rendered on top of the rooms.
            createNavPointLabel(navpt, renderer);
        }

        for (Zone room : map.getZones(Zone.Type.ROOM)) {
            createRoom(room, renderer);
            for (nl.tudelft.bw4t.map.Door doorargs : room.getDoors()) {
                createDoor(doorargs, room.getName(), renderer);
            }

        }
        for (Entity playerargs : map.getEntities()) {
            createPlayer(playerargs, renderer);
        }

    }

    /**
     * Add players to the BW4TRenderer
     * 
     * @param tokenizer
     *            , StringTokenizer containing the current line
     * @param renderer
     *            , the BW4TRenderer
     */
    private static void createPlayer(Entity args, BW4TClientGUI renderer) {
        String playerId = args.getName();
        renderer.addPlayer(playerId);
    }

    /**
     * Add doors to the BW4TRenderer
     * 
     * @param doorargs
     *            the {@link nl.tudelft.bw4t.map.Door}.
     * @param roomname
     *            the name of the room in the {@link NewMap}
     * @param renderer
     *            the {@link BW4TClientGUI}.
     */
    private static void createDoor(nl.tudelft.bw4t.map.Door doorargs,
            String roomname, BW4TClientGUI renderer) {
        double x = doorargs.getPosition().getX();
        double y = doorargs.getPosition().getY();

        Orientation ori = doorargs.getOrientation();

        int width = Constants.DOOR_THICKNESS;
        int height = Constants.DOOR_THICKNESS;
        switch (ori) {
        case HORIZONTAL:
            width = Constants.DOOR_WIDTH;
            break;
        case VERTICAL:
            height = Constants.DOOR_WIDTH;
            break;
        }

        GraphicalUpdateOperations.addDoor(x, y, width, height, roomname,
                renderer.getBW4TClientInfo());

    }

    /**
     * Add rooms to the BW4TRenderer
     * 
     * @param room
     *            the room {@link Zone}
     * @param renderer
     *            the {@link BW4TClientGUI}.
     */
    private static void createRoom(Zone room, BW4TClientGUI renderer) {
        Rectangle rect = room.getBoundingbox();
        GraphicalUpdateOperations.addRoom(rect.getX(), rect.getY(),
                rect.getWidth(), rect.getHeight(), room.getName(),
                renderer.getBW4TClientInfo());
    }

    /**
     * Add the drop zone to the BW4TRenderer
     * 
     * @param tokenizer
     *            , StringTokenizer containing the current line
     * @param renderer
     *            , the BW4TRenderer
     */
    private static void createDropZone(Rectangle dropzone,
            BW4TClientGUI renderer) {
        GraphicalUpdateOperations
                .addDropZone(new Integer[] { (int) dropzone.getX(),
                        (int) dropzone.getY(), (int) dropzone.getWidth(),
                        (int) dropzone.getHeight() },
                        renderer.getBW4TClientInfo());
    }

    /**
     * Add a nav point label to the BW4TRenderer
     * 
     * @param tokenizer
     *            , StringTokenizer containing the current line
     * @param renderer
     *            , the BW4TRenderer
     */
    private static void createNavPointLabel(Zone args, BW4TClientGUI renderer) {
        String label = args.getName();
        int x = (int) args.getBoundingbox().getX();
        int y = (int) args.getBoundingbox().getY();

        renderer.addLabel(label, new java.awt.Point(x, y));
    }

}
