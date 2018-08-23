package nl.tudelft.bw4t.server.model.robots.handicap;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Stack;

import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.doors.Door;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.robots.AgentRecord;
import nl.tudelft.bw4t.server.model.robots.Battery;
import nl.tudelft.bw4t.server.model.robots.MoveType;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot.State;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.GridPoint;

/**
 * The robot decorator.
 */
public abstract class AbstractRobotDecorator implements IRobot {
	/**
	 * This is the parent of the component. It can be the robot itself, or another
	 * handicap.
	 */
	private IRobot parent;

	/**
	 * This is the robot all handicaps wrap around.
	 */
	protected AbstractRobot robot = null;

	/***
	 * @param p
	 *            The interface the handicap wraps around.
	 */
	public AbstractRobotDecorator(IRobot p) {
		this.parent = p;
		this.robot = getSuperParent();
		this.robot.setTopMostHandicap(this);
	}

	@Override
	public String getName() {
		return this.parent.getName();
	}

	@Override
	public void connect() {
		this.parent.connect();
	}

	@Override
	public void disconnect() {
		this.parent.disconnect();
	}

	@Override
	public Stack<Block> getHolding() {
		return this.parent.getHolding();
	}

	@Override
	public NdPoint getTargetLocation() {
		return this.parent.getTargetLocation();
	}

	@Override
	public void setTargetLocation(NdPoint ptargetLocation) {
		this.parent.setTargetLocation(ptargetLocation);
	}

	@Override
	public boolean canPickUp(BoundedMoveableObject b) {
		return this.parent.canPickUp(b);
	}

	@Override
	public void pickUp(Block b) {
		this.parent.pickUp(b);
	}

	@Override
	public void drop() {
		this.parent.drop();
	}

	@Override
	public void drop(int amount) {
		this.parent.drop(amount);
	}

	@Override
	public void moveTo(double x, double y) {
		this.parent.moveTo(x, y);
	}

	@Override
	public MoveType getMoveType(double endx, double endy) {
		return this.parent.getMoveType(endx, endy);
	}

	@Override
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		return this.parent.checkZoneAccess(startzone, endzone, door);
	}

	@Override
	public Door getCurrentDoor(double x, double y) {
		return this.parent.getCurrentDoor(x, y);
	}

	@Override
	public Room getCurrentRoom(double x, double y) {
		return this.parent.getCurrentRoom(x, y);
	}

	@Override
	public Zone getZone() {
		return this.parent.getZone();
	}

	@Override
	public void moveByDisplacement(double x, double y) {
		this.parent.moveByDisplacement(x, y);
	}

	@Override
	public void move() {
		this.parent.move();
	}

	@Override
	public void stopRobot() {
		this.parent.stopRobot();
	}

	@Override
	public boolean isCollided() {
		return this.parent.isCollided();
	}

	@Override
	public void setCollided(boolean collided) {
		this.parent.setCollided(collided);
	}

	@Override
	public void clearCollided() {
		this.parent.clearCollided();
	}

	@Override
	public boolean isConnected() {
		return this.parent.isConnected();
	}

	@Override
	public boolean isOneBotPerZone() {
		return this.parent.isOneBotPerZone();
	}

	@Override
	public int getSize() {
		return this.parent.getSize();
	}

	@Override
	public void setSize(int s) {
		this.parent.setSize(s);
	}

	@Override
	public ViewEntity getView() {
		return this.parent.getView();
	}

	@Override
	public AgentRecord getAgentRecord() {
		return this.parent.getAgentRecord();
	}

	@Override
	public Battery getBattery() {
		return this.parent.getBattery();
	}

	@Override
	public void setBattery(Battery battery) {
		this.parent.setBattery(battery);
	}

	@Override
	public void recharge() {
		this.parent.recharge();
	}

	@Override
	public IRobot getParent() {
		return this.parent;
	}

	@Override
	public IRobot getEarliestParent() {
		IRobot parent = getParent();
		while (parent != null && parent.getParent() != null) {
			parent = parent.getParent();
		}
		return parent;
	}

	@Override
	public void setParent(IRobot hI) {
		this.parent.setParent(hI);
	}

	@Override
	public List<String> getHandicapsList() {
		return this.parent.getHandicapsList();
	}

	@Override
	public int getGripperCapacity() {
		return this.parent.getGripperCapacity();
	}

	@Override
	public void setGripperCapacity(int newcap) {
		this.parent.setGripperCapacity(newcap);
	}

	@Override
	public double getSpeedMod() {
		return this.parent.getSpeedMod();
	}

	@Override
	public void setSpeedMod(double speedMod) {
		this.parent.setSpeedMod(speedMod);
	}

	@Override
	public boolean isHuman() {
		return this.parent.isHuman();
	}

	@Override
	public EPartner getEPartner() {
		return this.parent.getEPartner();
	}

	@Override
	public boolean isHoldingEPartner() {
		return this.parent.isHoldingEPartner();
	}

	@Override
	public void pickUpEPartner(EPartner eP) {
		this.parent.pickUpEPartner(eP);
	}

	@Override
	public void dropEPartner() {
		this.parent.dropEPartner();
	}

	@Override
	public State getState() {
		return this.parent.getState();
	}

	@Override
	public void setTarget(BoundedMoveableObject target) {
		this.parent.setTarget(target);
	}

	@Override
	public NdPoint getLocation() {
		return this.parent.getLocation();
	}

	@Override
	public long getId() {
		return this.parent.getId();
	}

	@Override
	public boolean hasContext() {
		return this.parent.hasContext();
	}

	@Override
	public Context<Object> getContext() {
		return this.parent.getContext();
	}

	@Override
	public double distanceTo(BoundedMoveableObject b) {
		return this.parent.distanceTo(b);
	}

	@Override
	public AbstractRobot getSuperParent() {
		if (this.robot == null && this.parent != null) {
			this.robot = this.parent.getSuperParent();
		}

		return this.robot;
	}

	@Override
	public List<BoundedMoveableObject> getObstacles() {
		return this.parent.getObstacles();
	}

	@Override
	public void clearObstacles() {
		this.parent.clearObstacles();
	}

	@Override
	public boolean isDestinationUnreachable() {
		return this.parent.isDestinationUnreachable();
	}

	@Override
	public BW4TServerMap getServerMap() {
		return this.parent.getServerMap();
	}

	@Override
	public GridPoint getGridLocation() {
		return this.parent.getGridLocation();
	}

	@Override
	public Rectangle2D getBoundingBox() {
		return this.parent.getBoundingBox();
	}

	@Override
	public void setSize(double width, double height) {
		this.parent.setSize(width, height);
	}

	@Override
	public void removeFromContext() {
		this.parent.removeFromContext();
	}

	@Override
	public double distanceTo(NdPoint there) {
		return this.parent.distanceTo(there);
	}

	@Override
	public List<NdPoint> getPointsOccupiedByObject(double padding) {
		return this.parent.getPointsOccupiedByObject(padding);
	}

	@Override
	public boolean isFree(Class<? extends BoundedMoveableObject> freeOfType) {
		return this.parent.isFree(freeOfType);
	}
}
