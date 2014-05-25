package nl.tudelft.bw4t.map;

import org.codehaus.mojo.cobertura.IgnoreCoverage;

/**
 * This class contains the definitions that are shared between the server and
 * the client. It was easier to separate them out than to try and make the
 * classes available to each other. TODO we should probably find a better
 * solution, maybe make the size of these object a variable in the map?
 * 
 * @author Jan Giesenberg
 */
public class Constants {

	/**
	 * Utility class, cannot be instantiated.
	 */
	@IgnoreCoverage
	private Constants() {
	}

	/** The width and height of the blocks */
	public final static int BLOCK_SIZE = 2;

	/** Thickness of doors. */
	public static final int DOOR_THICKNESS = 1;
	/** width of doors. */
	public static final int DOOR_WIDTH = 4;

	/** The width and height of the robot */
	public final static int ROBOT_SIZE = 2;
}
