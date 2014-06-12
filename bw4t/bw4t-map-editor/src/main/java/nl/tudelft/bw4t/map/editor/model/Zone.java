package nl.tudelft.bw4t.map.editor.model;

import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;

public class Zone {

	private Type type = Type.CORRIDOR;
	private List<BlockColor> colors;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private boolean[] doorsbool = new boolean[4];
	
	private boolean isDropZone;
	private boolean isStartZone;
	
	private String name;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type t) {
		type = t;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}
	
	public List<BlockColor> getColors() {
		return colors;
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
