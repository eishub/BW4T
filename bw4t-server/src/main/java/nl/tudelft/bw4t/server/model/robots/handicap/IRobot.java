package nl.tudelft.bw4t.server.model.robots.handicap;

import java.util.Stack;

import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.server.model.BoundedMoveableInterface;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.doors.Door;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.robots.AgentRecord;
import nl.tudelft.bw4t.server.model.robots.MoveType;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot.State;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.NdPoint;

/**
 * This interface contains all the methods from the original Robot class.
 */
public interface IRobot extends BoundedMoveableInterface {

	/**
	 * @return The name of the robot
	 */
	String getName();

	/**
	 * called when robot becomes connected and should now be injected in repast.
	 */
	void connect();

	/**
	 * called when robot should be disconnected.
	 */
	void disconnect();

	/**
	 * @return The stack of blocks the robot is holding. Notice that
	 *         {@link Stack} has the top element last.
	 */
	Stack<Block> getHolding();

	/**
	 * @return The targetlocation of the robot
	 */
	NdPoint getTargetLocation();

	/**
	 * Sets the location to which the robot should move. This also clears the
	 * {@link #collided} flag.
	 * 
	 * @param ptargetLocation
	 *            the location to move to
	 */
	void setTargetLocation(NdPoint ptargetLocation);

	/**
	 * Check if robot can pick up a block.
	 * 
	 * @param b
	 *            the block to check
	 * 
	 * @return true if the block is within reach and if the bot has gripper
	 *         space available.
	 */
	boolean canPickUp(BoundedMoveableObject b);

	/**
	 * Pick up a block
	 * 
	 * @param b
	 *            the block to pick up
	 */
	void pickUp(Block b);

	/**
	 * Drops the block the robot is holding on the current location. The block
	 * is assigned a random position inside the room that it was dropped in. If
	 * the bot is not holding any block, this fails silently.
	 */
	void drop();

	/**
	 * A method for dropping multiple blocks at once.
	 * 
	 * @param amount
	 *            The amount of blocks that have to be dropped. If the amount is
	 *            bigger than the actual number of blocks held by the bot, all
	 *            blocks are dropped.
	 */
	void drop(int amount);

	/**
	 * This method moves the robot to a location (x, y).
	 * 
	 * @param x
	 *            the coord of location
	 * @param y
	 *            the coord of location
	 */
	void moveTo(double x, double y);

	/**
	 * Check motion type for robot to move to <endx, endy>. The
	 * {@link #MoveType} gives the actual type / possibility of the move, plus
	 * the details why it is (not) possible.
	 * 
	 * @param endx
	 *            is x position of target
	 * @param endy
	 *            is y position of target
	 * 
	 * @return Type of move to access the point (x, y)
	 */
	MoveType getMoveType(double endx, double endy);

	/**
	 * check if we can access endzone from startzone.
	 * 
	 * @param startzone
	 *            the zone where the robot is
	 * @param endzone
	 *            the zone the robot is going to
	 * @param door
	 *            the door leading to the zone
	 * 
	 * @return Type of move to access the zone.
	 */
	MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door);

	/**
	 * get door at a given position. Note that you can be in a door and at the
	 * same time in a room. This is because rooms and doors partially overlap
	 * usually.
	 * 
	 * @param x
	 *            is x coord of position
	 * @param y
	 *            is y coord of position
	 * 
	 * @return Door or null if not on a door
	 */
	Door getCurrentDoor(double x, double y);

	/**
	 * get room at a given position. CHECK maybe move this to RoomLocator?
	 * 
	 * @param x
	 *            is x coord of position
	 * @param y
	 *            is y coord of position
	 * 
	 * @return Room or null if not inside a room
	 */
	Room getCurrentRoom(double x, double y);

	/**
	 * Get current zone that the robot is in.
	 * 
	 * @return zone the bot is in
	 */
	Zone getZone();

	/**
	 * makes the robot moves in the environment.
	 */
	void move();

	/**
	 * Stop the motion of the robot. Effectively sets the target location to
	 * null. You can override this to catch this event.
	 */
	void stopRobot();

	/**
	 * @return True if an agent is connected to the robot
	 */
	boolean isConnected();

	/**
	 * @return The size of the robot
	 */
	int getSize();

	/**
	 * @param s
	 *            gives a new size to the robot
	 */
	void setSize(int s);

	/**
	 * @return translates the robot object to a map entity which can be drawn by
	 *         the map renderer
	 */
	ViewEntity getView();

	/**
	 * @return the agent record containing statistics about actions performed by
	 *         the robot
	 */
	AgentRecord getAgentRecord();

	/**
	 * get the parent, returns null because Robot is the super parent
	 * 
	 * @return null
	 */
	IRobot getParent();

	/**
	 * Gets the top most parent, 'the Adam / oldest ancestor / founding father'
	 * robot.
	 * 
	 * @return The founding father, null if this robot is the founding father.
	 */
	IRobot getEarliestParent();

	/**
	 * @param hI
	 *            changes the parent
	 */
	void setParent(IRobot hI);

	/**
	 * @return how much the speed of the robot is multiplied by
	 */
	double getSpeedMod();

	/**
	 * change the speed multiplier of the robot.
	 * 
	 * @param speedMod
	 *            must be rate between 0 and 1. Default is 0.5.
	 */
	void setSpeedMod(double speedMod);

	/**
	 * Get the current state of the robot.
	 * 
	 * @return the state
	 */
	State getState();

	/**
	 * Set a target for the navigating robot. If your start and/or target is not
	 * near a Zone, we go through the nearest Zone.
	 * 
	 * @param target
	 *            the object i will move to
	 */
	void setTarget(BoundedMoveableObject target);

	/**
	 * @return the location of the robot
	 */
	NdPoint getLocation();

	/**
	 * @return the ID of the robot
	 */
	long getId();

	/**
	 * Repast stores all objects in a context, this is the context in which this
	 * IRobot is.
	 * 
	 * @return a giant HashMap handled by Repast containing a bunch of objects
	 */
	Context<Object> getContext();

	/**
	 * @param b
	 *            the block we want to calculate the distance to
	 * @return the distance in question
	 */
	double distanceTo(BoundedMoveableObject b);

	/**
	 * get the {@link NavigatingRobot} at the head of the chain.
	 * 
	 * @return the Robot
	 */
	AbstractRobot getSuperParent();

}
