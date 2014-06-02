package nl.tudelft.bw4t.handicap;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.map.view.Entity;
import nl.tudelft.bw4t.robots.AbstractRobot;
import nl.tudelft.bw4t.robots.AgentRecord;
import nl.tudelft.bw4t.robots.Battery;
import nl.tudelft.bw4t.robots.MoveType;
import nl.tudelft.bw4t.robots.NavigatingRobot.State;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.space.continuous.NdPoint;
/**
 * This interface contains all the methods from the original Robot class.  
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public interface IRobot {
    
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
     * @return The block the robot is holding, null if holding none
     */
	List<Block> isHolding();
	
    /**
     * @return The targetlocation of the robot
     */
	NdPoint getTargetLocation();
	
    /**
     * Sets the location to which the robot should move. This also clears the {@link #collided} flag.
     * 
     * @param ptargetLocation
     *            the location to move to.
     */
	void setTargetLocation(NdPoint ptargetLocation);
	
    /**
     * Check if robot can pick up a block.
     * 
     * @param b
     *            the block to check
     * @return true if the block is within reach and if the bot isn't holding a block already.
     */
	boolean canPickUp(Block b);
	
    /**
     * Pick up a block
     * 
     * @param b
     *            , the block to pick up
     */
	void pickUp(Block b);
	
    /**
     * Drops the block the robot is holding on the current location. TODO: What if multiple blocks dropped at same spot?
     */
	void drop();
	
    /**
     * A method for dropping multiple blocks at once.
     * 
     * @param amount
     *              The amount of blocks that have to be dropped, needs to be less than the amount of blocks in the list.
     */
	void drop(int amount);
	
	void moveTo(double x, double y);
	
    /**
     * Check motion type for robot to move to <endx, endy>. The {@link #MoveType} gives the actual type / possibility of
     * the move, plus the details why it is (not) possible.
     * 
     * @param end
     *            is x position of target
     * @param endy
     *            is y position of target
     */
	MoveType getMoveType(double endx, double endy);
	
    /**
     * check if we can access endzone from startzone.
     * @param startzone
     * @param endzone
     */
	MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door);
	
    /**
     * get door at a given position. Note that you can be in a door and at the same time in a room. This is because
     * rooms and doors partially overlap usually.
     * 
     * @param x
     *            is x coord of position
     * @param y
     *            is y coord of position
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
     * @return Room or null if not inside a room
     */
	Room getCurrentRoom(double x, double y);
	
    /**
     * Get current zone that the robot is in.
     * 
     * @return zone the bot is in.
     */
	Zone getZone();
	
	 /**
     * Moves the robot by displacing it for the given amount. If the robot collides with something, the movement target
     * is cancelled to avoid continuous bumping.
     * 
     * @param x
     *            the displacement in the x-dimension.
     * @param y
     *            the displacement in the y-dimension.
     */
	void moveByDisplacement(double x, double y);
	
	void move();
	
    /**
     * Stop the motion of the robot. Effectively sets the target location to null. You can override this to catch this
     * event.
     */
	void stopRobot();
	
	boolean isCollided();
	
	void setCollided(boolean collided);
	
    /**
     * clear the collision flag. You can use this to reset the flag after you took notice of the collision.
     * 
     */
	void clearCollided();
	
	boolean isConnected();
	
	boolean isOneBotPerZone();
	
	/**
     * Gets the size of the robot
     * 
     * @return size
     */
	int getSize();
	
	/**
     * Sets the size of a robot to a certain integer
     * 
     * @param s
     */
	void setSize(int s);
	
	Entity getView();
	
    /**
     * gets AgentRecord
     * 
     * @return AgentRecord
     */
	AgentRecord getAgentRecord();
	
	Battery getBattery();
	
	void setBattery(Battery battery);
	
	/**
     * @return This method returns the battery percentage.
     */
	int getBatteryPercentage();
	
	/**
     * 
     * @return discharge rate of battery.
     */
	double getDischargeRate();
	
	/**
     * The robot is in a charging zone. The robot charges.
     */
	void recharge();

	/**
     * get the parent, returns null because Robot is the super parent
     * 
     * @return null
     */
	IRobot getParent();
	
	void setParent(IRobot hI);
	
	ArrayList<String> getHandicapsList();
	
	void setHandicapsList(ArrayList<String> handicapsList);
	
	int getGripperCapacity();
	
	void setGripperCapacity(int newcap);
	
	double getSpeedMod();
	
	boolean isHuman();
	
	EPartner getEPartner();
	
	boolean isHoldingEPartner();
	
	boolean canPickUpEPartner(EPartner eP);
	
	void pickUpEPartner(EPartner eP);
	
	void dropEPartner();
	
    /**
     * get the {@link NavigatingRobot} at the head of the chain.
     * 
     * @return the Robot
     */
	AbstractRobot getSuperParent();

	/**
	 * Get the current state of the robot.
	 * @return the state
	 */
    State getState();

    /**
     * Set a target for the navigating robot. If your start and/or target is not near a Zone, we go through the nearest
     * Zone.
     * 
     * @param target the object i will move to
     */
    void setTarget(BoundedMoveableObject target);
}
