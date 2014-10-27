package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Stack;

/**
 * information about an robot for the map renderer.
 * 
 * This object is used in {@link Map}s that should be threadsafe and therefore
 * must implement a thread safe {@link #equals(Object)}.
 */
public class ViewEntity {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * Two ViewEntity objects are the same iff their ID is the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewEntity other = (ViewEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/** Initialize color for robot to black. */
	public final static Color ROBOT_COLOR = Color.BLACK;

	/** Initialize id, default 0 */
	private long id = 0;

	/** Initialize name, default empty. */
	private String name = "";

	/**
	 * Initialize Stack of blocks that bot is holding.
	 * 
	 * @Modified W.Pasman 21oct14 Changed Map into Stack: the order of this
	 *           stack is critical.
	 */
	private final Stack<ViewBlock> holding = new Stack<ViewBlock>();

	/** Initialize location */
	private Point2D location = new Point2D.Double();

	/** Initialize size of robot */
	private int robotsize = 2;

	/** Initialize holdingEpartner, default -1 */
	private long holdingEpartner = -1;

	/** Initialize collided to false. */
	private boolean collided = false;

	/** Initialize batteryLevel to 0. */
	private double batteryLevel = 0.0;

	/** the max number of blocks the gripper can hold */
	private int gripperCapacity = 1;

	/**
	 * Empty constructor, initialize default object.
	 */
	public ViewEntity(long id) {
		this.id = id;
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            long
	 * @param name
	 *            String
	 * @param x
	 *            coordinate location
	 * @param y
	 *            coordinate location
	 * @param newstack
	 *            blocks. The top one is the first in the list.
	 * @param robotsize
	 *            int
	 */
	public ViewEntity(long id, String name, double x, double y,
			Stack<ViewBlock> newstack, int robotsize) {
		this.setId(id);
		this.setName(name);
		this.setLocation(x, y);
		this.setHoldingStack(newstack);
		this.setSize(robotsize);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the stack of blocks that the bot is holding
	 * 
	 * @return {@link Stack} of {@link ViewBlock}s
	 */
	public Stack<ViewBlock> getHolding() {
		return holding;
	}

	/**
	 * @param newstack
	 *            remove current holdings, set to new holding. Order is
	 *            essential, first one is top #3209.
	 */
	public void setHoldingStack(Stack<ViewBlock> newstack) {
		holding.clear();
		holding.addAll(newstack);
	}

	/**
	 * @return the ViewBlock on top of stack, null if robot holds no blocks
	 */
	public ViewBlock getTopBlock() {
		// DEBUG System.out.println("stack=" + holding.toString());
		if (holding.isEmpty()) {
			return null;
		}
		return holding.peek();
	}

	/**
	 * @return Color of first block holding; When no block is hold, return color
	 *         of robot.
	 */
	public Color getColor() {
		if (holding.isEmpty()) {
			return ROBOT_COLOR;
		}
		return getTopBlock().getColor().getColor();
	}

	public Point2D getLocation() {
		return location;
	}

	/**
	 * @param x
	 *            coordinate of location
	 * @param y
	 *            coordinate of location
	 */
	public void setLocation(double x, double y) {
		this.setLocation(new Point2D.Double(x, y));
	}

	/**
	 * @param loc
	 *            position
	 */
	public void setLocation(Point2D loc) {
		location = loc;
	}

	public int getRobotSize() {
		return robotsize;
	}

	public void setSize(int robotsize) {
		this.robotsize = robotsize;
	}

	public long getHoldingEpartner() {
		return holdingEpartner;
	}

	public void setHoldingEpartner(long holdingEpartner) {
		this.holdingEpartner = holdingEpartner;
	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public double getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	/**
	 * Sets the current capacity of the robot's gripper.
	 * 
	 * @param capacity
	 *            the max number of blocks the gripper can hold.
	 */
	public void setGripperCapacity(int capacity) {
		gripperCapacity = capacity;

	}

	/**
	 * Get the current capacity of the robot's gripper.
	 * 
	 * @return the max number of blocks the gripper can hold
	 */

	public int getGripperCapacity() {
		return gripperCapacity;
	}
}
