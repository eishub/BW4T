package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.Zone;
/**
 * The node class to be used in the random map generator.
 *
 */
public class Node {
    /**
     * Walls the door can be on, used in the Node class.
     */
    public enum DoorDirection {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
    /**
     * The required nodes.
     */
    private Node north, east, south, west;
    /**
     * The type of room this node represents.
     */
    private Zone.Type type;
    /**
     * The orientation of the door if the node represents a room.
     */
    private DoorDirection dir = DoorDirection.NORTH;
    /**
     * Constructs the Node object with only the type of the room
     * the node is representing.
     * @param t The type of room this node should represent.
     */
    public Node(Zone.Type t) {
        type = t;
    }
    public Node getNorth() {
        return north;
    }
    public void setNorth(Node north) {
        this.north = north;
    }
    public Node getEast() {
        return east;
    }
    public void setEast(Node east) {
        this.east = east;
    }
    public Node getSouth() {
        return south;
    }
    public void setSouth(Node south) {
        this.south = south;
    }
    public Node getWest() {
        return west; 
    }
    public void setWest(Node west) {
        this.west = west;
    }
    public Zone.Type getType() {
        return type;
    }
    public void setType(Zone.Type type) {
        this.type = type;
    }
    public DoorDirection getDir() {
        return dir;
    }
    public void setDir(DoorDirection dir) {
        this.dir = dir;
    }
    
    /**
     * Looks if a door is free or not
     * @return list with doors that are not blocked
     */
    public List<DoorDirection> getFreeDirs() {
        List<DoorDirection> dirList = new ArrayList<DoorDirection>();
        if (north != null && north.isNotBlocking()) {
            dirList.add(DoorDirection.NORTH);
        }
        if (east != null && east.isNotBlocking()) {
            dirList.add(DoorDirection.EAST);
        }
        if (south != null && south.isNotBlocking()) {
            dirList.add(DoorDirection.SOUTH);
        }
        if (west != null && west.isNotBlocking()) {
            dirList.add(DoorDirection.WEST);
        }
        return dirList;
    }
    
    /**
     * check if a door is blocked
     * @return true if door is chargingzone or corridor
     */
    public boolean isNotBlocking() {
        return type == Zone.Type.CHARGINGZONE 
                || type == Zone.Type.CORRIDOR;
    }
}