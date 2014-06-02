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
import nl.tudelft.bw4t.robots.AbstractRobot;
import nl.tudelft.bw4t.robots.MoveType;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
/**
 * This interface contains all the methods in Robot that the handicaps will affect. 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public interface IRobot {
	
	String getName();
	
	void connect();
	
	void disconnect();
	
	List<Block> isHolding();
	
	NdPoint getTargetLocation();
	
	void setTargetLocation(NdPoint ptargetLocation);
	
	boolean canPickUp(Block b);
	
	void pickUp(Block b);
	
	void drop();
	
	void drop(int amount);
	
	void moveTo(double x, double y);
	
	MoveType getMoveType(double endx, double endy);
	
	MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door);
	
	Door getCurrentDoor(double x, double y);
	
	Room getCurrentRoom(double x, double y);
	
	Zone getZone();
	
	void moveByDisplacement(double x, double y);
	
	void move();
	
	void stopRobot();
	
	boolean isCollided();
	
	void setCollided(boolean collided);
	
	void clearCollided();
	
	boolean isConnected();
	
	boolean isOneBotPerZone();
	
	int getSize();
	
	void setSize(int s);
	
	Entity getView();
	
	AgentRecord getAgentRecord();
	
	Battery getBattery();
	
	void setBattery(Battery battery);
	
	int getBatteryPercentage();
	
	double getDischargeRate();
	
	void recharge();
	
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
	
	AbstractRobot getSuperParent();
}
