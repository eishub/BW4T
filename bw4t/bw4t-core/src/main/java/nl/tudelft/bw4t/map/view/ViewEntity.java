package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Stack;

import nl.tudelft.bw4t.util.OneTimeInitializing;

/**
 * information about an robot for the map renderer. Here information gathered
 * from incoming percepts is stored. FIXME maybe this should be stored in
 * separate object?
 * 
 * This object is used in {@link Map}s that should be threadsafe and therefore
 * must implement a thread safe {@link #equals(Object)}.
 */
public class ViewEntity implements OneTimeInitializing {

	/** Initialize color for robot to black. */
	public final static Color ROBOT_COLOR = Color.BLACK;

	private static final int DEFAULT_ROBOT_SIZE = 2;

	private static final int DEFAULT_GRIPPER_CAPACITY = 1;

	private static final double DEFAULT_BATTERY_LEVEL = 0.0;

	private static final boolean DEFAULT_COLORBLINDNESS = false;

	private static final long DEFAULT_HOLDING_EPARTNER = -1;

	/** Initialize id */
	private Long id = null;

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
	private int robotsize = DEFAULT_ROBOT_SIZE;

	/** Initialize holdingEpartner */
	private long holdingEpartner = DEFAULT_HOLDING_EPARTNER;

	/** Initialize collided to false. */
	private boolean collided = false;

	/** Initialize batteryLevel to 0. */
	private double batteryLevel = DEFAULT_BATTERY_LEVEL;

	/** the max number of blocks the gripper can hold */
	private int gripperCapacity = DEFAULT_GRIPPER_CAPACITY;

	/** true if agent is color blind */
	private boolean isColorBlind = DEFAULT_COLORBLINDNESS;

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
		this.id = id;
		this.setName(name);
		this.setLocation(x, y);
		this.setHoldingStack(newstack);
		this.setSize(robotsize);
	}

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

	@Override
	public boolean isInitialized() {
		return id != null;
	}

	public Long getId() {
		return id;
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

	/**
	 * check if entity is color blind
	 * 
	 * @return true if entity is color blind.
	 */
	public boolean isColorBlind() {
		return isColorBlind;
	}

	public void setColorBlind(boolean isblind) {
		isColorBlind = isblind;
	}

	/**
	 * Copy all field values (except ID) from given {@link ViewEntity}.
	 */
	public void duplicate(ViewEntity ent) {
		setName(ent.getName());
		setLocation(ent.getLocation());
		setHoldingStack(ent.getHolding());
		setSize(ent.getRobotSize());
		setGripperCapacity(ent.getGripperCapacity());
		setBatteryLevel(ent.getBatteryLevel());
		setCollided(ent.isCollided());
		setColorBlind(ent.isColorBlind());
		setHoldingEpartner(ent.getHoldingEpartner());
	}

	/**
	 * Copies all non-default values from given {@link ViewEntity} into our
	 * fields.
	 * 
	 * @param ent
	 */
	public void merge(ViewEntity ent) {
		if (!ent.getName().isEmpty()) {
			setName(ent.getName());
		}
		if (!ent.getLocation().equals(new Point2D.Double())) {
			setLocation(ent.getLocation());
		}
		if (!ent.getHolding().isEmpty()) {
			setHoldingStack(ent.getHolding());
		}
		if (ent.getRobotSize() != DEFAULT_ROBOT_SIZE) {
			setSize(ent.getRobotSize());
		}
		if (ent.getGripperCapacity() != DEFAULT_GRIPPER_CAPACITY) {
			setGripperCapacity(ent.getGripperCapacity());
		}
		if (ent.getBatteryLevel() != DEFAULT_BATTERY_LEVEL) {
			setBatteryLevel(ent.getBatteryLevel());
		}
		if (ent.isCollided()) {
			setCollided(ent.isCollided());
		}
		if (ent.isColorBlind == DEFAULT_COLORBLINDNESS) {
			setColorBlind(ent.isColorBlind());
		}
		if (ent.getHoldingEpartner() != DEFAULT_HOLDING_EPARTNER) {
			setHoldingEpartner(ent.getHoldingEpartner());
		}

	}
}
