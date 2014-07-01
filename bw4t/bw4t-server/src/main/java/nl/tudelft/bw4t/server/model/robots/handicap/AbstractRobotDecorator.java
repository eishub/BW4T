package nl.tudelft.bw4t.server.model.robots.handicap;

import java.util.List;

import nl.tudelft.bw4t.map.view.ViewEntity;
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

/**
 * The robot decorator.
 */
public abstract class AbstractRobotDecorator implements IRobot {
    
    /**
     * This is the parent of the component.
     * It can be the robot itself, or another handicap. 
     */
    private IRobot parent;
    
    /**
     * This is the robot all handicaps wrap around. 
     */
    protected AbstractRobot robot = null;
    
    /***
     * @param p
     * The interface the handicap wraps around. 
     */
    public AbstractRobotDecorator(IRobot p) {
        parent = p;
        robot = getSuperParent();
        robot.setTopMostHandicap(this);
    }
    
    @Override
    public String getName() {
        return parent.getName();
    }
    
    @Override
    public void connect() {
        parent.connect();
    }
    
    @Override
    public void disconnect() {
        parent.disconnect();
    }
    
    @Override
    public List<Block> isHolding() {
        return parent.isHolding();
    }
    
    @Override
    public NdPoint getTargetLocation() {
        return parent.getTargetLocation();
    }
    
    @Override
    public void setTargetLocation(NdPoint ptargetLocation) {
        parent.setTargetLocation(ptargetLocation);
    }
    
    @Override
    public boolean canPickUp(BoundedMoveableObject b) {
        return parent.canPickUp(b);
    }
    
    @Override
    public void pickUp(Block b) {
        parent.pickUp(b);
    }
    
    @Override
    public void drop() {
        parent.drop();
    }
    
    @Override
    public void drop(int amount) {
        parent.drop(amount);
    }
    
    @Override
    public void moveTo(double x, double y) {
        parent.moveTo(x, y);
    }
    
    @Override
    public MoveType getMoveType(double endx, double endy) {
        return parent.getMoveType(endx, endy);
    }
    
    @Override
    public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
        return parent.checkZoneAccess(startzone, endzone, door);
    }
    
    @Override
    public Door getCurrentDoor(double x, double y) {
        return parent.getCurrentDoor(x, y);
    }
    
    @Override
    public Room getCurrentRoom(double x, double y) {
        return parent.getCurrentRoom(x, y);
    }
    
    @Override
    public Zone getZone() {
        return parent.getZone();
    }
    
    @Override
    public void moveByDisplacement(double x, double y) {
        parent.moveByDisplacement(x, y);
    }
    
    @Override
    public void move() {
        parent.move();
    }
    
    @Override
    public void stopRobot() {
        parent.stopRobot();
    }
    
    @Override
    public boolean isCollided() {
        return parent.isCollided();
    }
    
    @Override
    public void setCollided(boolean collided) {
        parent.setCollided(collided);
    }
    
    @Override
    public void clearCollided() {
        parent.clearCollided();
    }
    
    @Override
    public boolean isConnected() {
        return parent.isConnected();
    }
    
    @Override
    public boolean isOneBotPerZone() {
        return parent.isOneBotPerZone();
    }
    
    @Override
    public int getSize() {
        return parent.getSize();
    }
    
    @Override
    public void setSize(int s) {
        parent.setSize(s);
    }
    
    @Override
    public ViewEntity getView() {
        return parent.getView();
    }
    
    @Override
    public AgentRecord getAgentRecord() {
        return parent.getAgentRecord();
    }
    
    @Override
    public Battery getBattery() {
        return parent.getBattery();
    }
    
    @Override
    public void setBattery(Battery battery) {
        parent.setBattery(battery);
    }
    
    @Override
    public void recharge() {
        parent.recharge();
    }
    
    @Override
    public IRobot getParent() {
        return parent;
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
        parent.setParent(hI);
    }
    
    @Override
    public List<String> getHandicapsList() {
        return parent.getHandicapsList();
    }
    
    @Override
    public int getGripperCapacity() {
        return parent.getGripperCapacity();
    }
    
    @Override
    public void setGripperCapacity(int newcap) {
        parent.setGripperCapacity(newcap);
    }
    
    @Override
    public double getSpeedMod() {
        return parent.getSpeedMod();
    }
    
    @Override
    public void setSpeedMod(double speedMod) {
        parent.setSpeedMod(speedMod);
    }
    
    @Override
    public boolean isHuman() {
        return parent.isHuman();
    }
    
    @Override
    public EPartner getEPartner() {
        return parent.getEPartner();
    }
    
    @Override
    public boolean isHoldingEPartner() {
        return parent.isHoldingEPartner();
    }
    
    @Override
    public void pickUpEPartner(EPartner eP) {
        parent.pickUpEPartner(eP);
    }
    
    @Override
    public void dropEPartner() {
        parent.dropEPartner();
    }
    
    @Override
    public State getState() {
        return parent.getState();
    }
    
    @Override
    public void setTarget(BoundedMoveableObject target) {
        parent.setTarget(target);
    }
    
    @Override
    public NdPoint getLocation() {
        return parent.getLocation();
    }

    @Override
    public long getId() {
        return parent.getId();
    }

    @Override
    public Context<Object> getContext() {
        return parent.getContext();
    }

    @Override
    public double distanceTo(BoundedMoveableObject b) {
        return parent.distanceTo(b);
    }

    @Override
    public AbstractRobot getSuperParent() {
        if (robot == null && parent != null) {
            robot = parent.getSuperParent();
        }
        
        return robot;
    }

    @Override
    public List<BoundedMoveableObject> getObstacles() { 
        return parent.getObstacles(); 
    }

    @Override
    public void clearObstacles() { 
        parent.clearObstacles(); 
    }

    @Override
    public boolean isDestinationUnreachable() {
        return parent.isDestinationUnreachable();
    }
}
