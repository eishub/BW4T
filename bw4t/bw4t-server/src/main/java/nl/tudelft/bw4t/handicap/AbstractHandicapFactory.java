package nl.tudelft.bw4t.handicap;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.space.continuous.NdPoint;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.map.view.Entity;
import nl.tudelft.bw4t.robots.AgentRecord;
import nl.tudelft.bw4t.robots.Battery;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.robots.MoveType;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;

/**
 * @author Valentine Mairet & Ruben Starmans
 */
public abstract class AbstractHandicapFactory implements HandicapInterface {
    /**
     * Handicap which the current handicap is wrapped around.
     */
    private HandicapInterface parent;
    /**
     * Robot to which this handicap belongs to.
     */
    protected Robot robot = null;
    
    /**
     * Sets the parent to p,
     * Sets the robot to the super parent (last parent).
     * @param p HandicapInterface the Handicap wraps around.
     */
    public AbstractHandicapFactory(HandicapInterface p) {
        parent = p;
        robot = getSuperParent();
    }
    
	public String getName() {
		return parent.getName();
	}
	
	public void connect() {
		parent.connect();
	}
	
	public void disconnect() {
		parent.disconnect();
	}
	
	public List<Block> isHolding() {
		return parent.isHolding();
	}
	
	public NdPoint getTargetLocation() {
		return parent.getTargetLocation();
	}
	
	public void setTargetLocation(NdPoint ptargetLocation) {
		parent.setTargetLocation(ptargetLocation);
	}
	
	public boolean canPickUp(Block b) {
		return parent.canPickUp(b);
	}
	
	public void pickUp(Block b) {
		parent.pickUp(b);
	}
	
	public void drop() {
		parent.drop();
	}
	
	public void drop(int amount) {
		parent.drop(amount);
	}
	
	public void moveTo(double x, double y) {
		parent.moveTo(x, y);
	}
	
	public MoveType getMoveType(double endx, double endy) {
		return parent.getMoveType(endx, endy);
	}
	
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		parent.
	}
	
	public Door getCurrentDoor(double x, double y) {
		parent.
	}
	
	public Room getCurrentRoom(double x, double y) {
		parent.
	}
	
	public Zone getZone() {
		parent.
	}
	
	public void moveByDisplacement(double x, double y) {
		parent.
	}
	public void move() {
		parent.
	}
	public void stopRobot() {
		parent.
	}
	boolean isCollided() {
		parent.
	}
	public void setCollided(boolean collided) {
		parent.
	}
	public void clearCollided() {
		parent.
	}
	boolean isConnected() {
		parent.
	}
	boolean isOneBotPerZone() {
		parent.
	}
	int getSize() {
		parent.
	}
	public void setSize(int s) {
		parent.
	}
	Entity getView() {
		parent.
	}
	AgentRecord getAgentRecord() {
		parent.
	}
	Battery getBattery() {
		parent.
	}
	public void setBattery(Battery battery) {
		parent.
	}
	int getBatteryPercentage() {
		parent.
	}
	double getDischargeRate() {
		parent.
	}
	public void recharge() {
		parent.
	}
	HandicapInterface getParent() {
		parent.
	}
	public void setParent(HandicapInterface hI) {
		parent.
	}
	ArrayList<String> getHandicapsList() {
		parent.
	}
	public void setHandicapsList(ArrayList<String> handicapsList) {
		parent.
	}
	int getGripperCapacity() {
		parent.
	}
	public void setGripperCapacity(int newcap) {
		parent.
	}
	double getSpeedMod() {
		parent.
	}
	boolean isHuman() {
		parent.
	}
	EPartner getEPartner() {
		parent.
	}
	boolean isHoldingEPartner() {
		parent.
	}
	boolean canPickUpEPartner(EPartner eP) {
		parent.
	}
	public void pickUpEPartner(EPartner eP) {
		parent.
	}
	public void dropEPartner() {
		parent.
	}
	Robot getSuperParent() {
		parent.
	}
}
