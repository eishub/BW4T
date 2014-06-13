package nl.tudelft.bw4t.map.editor.model;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.map.editor.model.Node.DoorDirection;

/**
 * Every space in the map is a Zone that is later changed to a corridor, room etc.
 *
 */
public class ZoneModel {

	private Type type = Type.CORRIDOR;
	
	private List<BlockColor> colors = new ArrayList<>();
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private boolean[] doorsbool = new boolean[4];
	
	private boolean isDropZone = false;
	private boolean isStartZone = false;
	
	private String coordiname = "";
	
	public ZoneModel() {
		
	}
	
	public ZoneModel(Node n) {
		type = n.getType();
		Node.DoorDirection dir = n.getDir();
		int index;
		if (type == Type.ROOM) {
			if (dir == DoorDirection.NORTH) {
				index = 0;
			} 
			else if (dir == DoorDirection.EAST) {
				index = 1;
			} 
			else if (dir == DoorDirection.SOUTH) {
				index = 2;
			} 
			else {
				index = 3;
			}
			doorsbool[index] = true;
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type t) {
		type = t;
	}
	
	/**
	 * Gets the name of the Zone based on the type of Zone.
	 * @return the name of the zone based on its type.
	 */
	public String getName() {
	    if (isDropZone()) {
	        return nl.tudelft.bw4t.map.Zone.DROP_ZONE_NAME;
	    } else if (isStartZone()) {
	    	return nl.tudelft.bw4t.map.Zone.START_ZONE_NAME + coordiname;
	    } else if (getType() == Type.BLOCKADE) {
	    	return nl.tudelft.bw4t.map.Zone.BLOCKADE_NAME + coordiname;
	    } else if (getType() == Type.CHARGINGZONE) {
	    	return nl.tudelft.bw4t.map.Zone.CHARGING_ZONE_NAME + coordiname;
	    } else if (getType() == Type.CORRIDOR) {
	    	return nl.tudelft.bw4t.map.Zone.CORRIDOR_NAME + coordiname;
	    } else if (getType() == Type.ROOM) {
	    	return nl.tudelft.bw4t.map.Zone.ROOM_NAME + coordiname;
	    } else {
	    	return coordiname;
	    }
	}

	public void setName(String n) {
		coordiname = n;
	}
	
	public List<BlockColor> getColors() {
		if (type == Type.ROOM) {
			return colors;
		} else {
			return new ArrayList<BlockColor>();
		}
		
	}
	
	public void setColors(List<BlockColor> cs) {
		if (colors == null) {
			throw new NullPointerException("Null list not allowed for colors");
		}
		colors = cs;
	}
	
	public boolean hasDoor(int index) {
		return doorsbool[index];
	}
	
	public void setDoor(int index) {
		doorsbool[index] = true;
	}

	public boolean isDropZone() {
		return isDropZone;
	}

	public void setDropZone(boolean isDZ) {
		isDropZone = isDZ;
	}

	public boolean isStartZone() {
		return isStartZone;
	}

	public void setStartZone(boolean isSZ) {
		isStartZone = isSZ;
	}
}