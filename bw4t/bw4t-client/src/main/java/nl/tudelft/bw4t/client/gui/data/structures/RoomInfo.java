package nl.tudelft.bw4t.client.gui.data.structures;

import java.util.ArrayList;

/**
 * Helper class to store information about rooms
 */
public class RoomInfo {
    private double x, y, width, height;
    private long id;
    private String name;
    private ArrayList<DoorInfo> doors;
    private boolean occupied;

    public RoomInfo(double x2, double y2, double width2, double height2,
            String name) {
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
        this.name = name;
        doors = new ArrayList<DoorInfo>();
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addDoor(DoorInfo door) {
        doors.add(door);
    }

    public ArrayList<DoorInfo> getDoors() {
        return doors;
    }

    @SuppressWarnings("unused")
    public boolean isOccupied() {
        return occupied;
    }

    @SuppressWarnings("unused")
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String toString() {
        return "Room[" + id + "," + x + "," + y + "]";
    }
}