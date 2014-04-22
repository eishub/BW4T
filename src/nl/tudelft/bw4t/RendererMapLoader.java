package nl.tudelft.bw4t;

import java.io.IOException;

import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.map.Door.Orientation;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;

/**
 * This class is loads maps into the {@link BW4TClientMapRenderer}.
 * 
 * @author W.Pasman
 * 
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
	public static void loadMap(NewMap map, BW4TClientMapRenderer renderer)
			throws IOException {

		Point size = map.getArea();
		renderer.setWorldDimensions((int) size.getX(), (int) size.getY());

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
	private static void createPlayer(Entity args, BW4TClientMapRenderer renderer) {
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
	 *            the {@link BW4TClientMapRenderer}.
	 */
	private static void createDoor(nl.tudelft.bw4t.map.Door doorargs,
			String roomname, BW4TClientMapRenderer renderer) {
		double x = doorargs.getPosition().getX();
		double y = doorargs.getPosition().getY();

		Orientation ori = doorargs.getOrientation();

		int width = Door.DOOR_THICKNESS;
		int height = Door.DOOR_THICKNESS;
		switch (ori) {
		case HORIZONTAL:
			width = Door.DOOR_WIDTH;
			break;
		case VERTICAL:
			height = Door.DOOR_WIDTH;
			break;
		}

		renderer.addDoor(x, y, width, height, roomname);

	}

	/**
	 * Add rooms to the BW4TRenderer
	 * 
	 * @param room
	 *            the room {@link Zone}
	 * @param renderer
	 *            the {@link BW4TClientMapRenderer}.
	 */
	private static void createRoom(Zone room, BW4TClientMapRenderer renderer) {
		Rectangle rect = room.getBoundingbox();
		renderer.addRoom(rect.getX(), rect.getY(), rect.getWidth(),
				rect.getHeight(), room.getName());
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
			BW4TClientMapRenderer renderer) {
		renderer.addDropZone(new Integer[] { (int) dropzone.getX(),
				(int) dropzone.getY(), (int) dropzone.getWidth(),
				(int) dropzone.getHeight() });
	}

	/**
	 * Add a nav point label to the BW4TRenderer
	 * 
	 * @param tokenizer
	 *            , StringTokenizer containing the current line
	 * @param renderer
	 *            , the BW4TRenderer
	 */
	private static void createNavPointLabel(Zone args,
			BW4TClientMapRenderer renderer) {
		String label = args.getName();
		int x = (int) args.getBoundingbox().getX();
		int y = (int) args.getBoundingbox().getY();

		renderer.addLabel(label, new java.awt.Point(x, y));
	}

}
