package nl.tudelft.bw4t.map.editor.model;

import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.map.editor.controller.ColorSequence;

public class Zone {

	private Type type = Type.CORRIDOR;
	private ColorSequence colors;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private boolean[] doorsbool = new boolean[4];
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type t) {
		type = t;
	}
	
	public ColorSequence getColors() {
		return colors;
	}
	
	public void setColors(ColorSequence cs) {
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
}
