package nl.tudelft.bw4t.map.editor.model;

import nl.tudelft.bw4t.map.editor.ColorSequence;

/**
 * This contains the room information: the blocks that the room contains.
 */
public class Room_Temp {

    private int row, column; // should match the position in the map table
    private String direction;
    private boolean isRoom;
    private ColorSequence colors = new ColorSequence();

    public Room_Temp(int r, int c, String dir, boolean isARoom) {
        if (r > 24 || r < 0) {
            // prepare to compare rows to 'A'..'Y'. we need r=24 for the
            // dropzone row and
            throw new IllegalArgumentException("row should be in range 0..24");
        }
        if (c < 0) {
            throw new IllegalArgumentException(
                    "column should be bigger or equal to 0");
        }

        row = r;
        column = c;
        setDirection(dir);
        setRoom(isARoom);
    }

    public ColorSequence getColors() {
        return colors;
    }

    public void setColors(ColorSequence colors) {
        if (colors == null) {
            throw new NullPointerException("null list not allowed for colors");
        }
        this.colors = colors;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * gets the name of the room. The room names are of the form
     * "room "+character+number. character is A..Z for the rows. The column
     * determines the number, starting at 0.
     */
    public String toString() {
        return "Room" + (char) (row + 65) + (column + 1);
    }

    /**
     * Sets the value identical to the given room. The coordinates of the
     * otherRoom are ignored, assuming that the original coordinates of our room
     * are correct.
     * 
     * @param otherRoom
     */
    public void setValue(Room otherRoom) {
        setColors(otherRoom.getColors());
    }

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the isRoom
	 */
	public boolean isRoom() {
		return isRoom;
	}

	/**
	 * @param isRoom the isRoom to set
	 */
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

}
