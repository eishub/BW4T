package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.bw4t.environmentstore.editor.model.Node.DoorDirection;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * Every space in the map is a Zone that is later changed to a corridor, room
 * etc.
 * 
 */
public class ZoneModel {
    
    /** max amount spawn points per spawn point */
    public static final int SPAWN_POINTS_PER_START_ZONE = 4;

    private Zone zone;

    private Type type = Type.CORRIDOR;

    /** BlockColors the zone contains */
    private List<BlockColor> colors = new ArrayList<>();

    /** direction for doors */
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    /** true if zone has door for that direction */
    private boolean[] doorsbool = new boolean[4];

    /** the 4 neighbours of a zone */
    private ZoneModel[] neighbours = new ZoneModel[4];

    /** true if zone is dropzone */
    private boolean isDropZone = false;
    
    /** true if zone is startzone */
    private boolean isStartZone = false;

    /** name of the zone on the grid */
    private String coordiname = "";

    public ZoneModel() {
    }

    public ZoneModel(Zone zone) {
        this.zone = zone;
        this.type = zone.getType();

        this.colors = zone.getBlocks();

        this.isStartZone = isStartZone(zone);
        this.isDropZone = isDropZone(zone);

        this.doorsbool[0] = zone.hasNorth();
        this.doorsbool[1] = zone.hasEast();
        this.doorsbool[2] = zone.hasSouth();
        this.doorsbool[3] = zone.hasWest();
    }

    public ZoneModel(Node n) {
        type = n.getType();
        Node.DoorDirection dir = n.getDir();
        int index;

        if (type == Type.ROOM) {
            if (dir == DoorDirection.NORTH) {
                index = 0;
            } else if (dir == DoorDirection.EAST) {
                index = 1;
            } else if (dir == DoorDirection.SOUTH) {
                index = 2;
            } else {
                index = 3;
            }

            doorsbool[index] = true;
        }
    }

    public Zone getZone() {
        return zone;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type t) {
        type = t;
        if (type == Type.ROOM && !hasDoors()) {
            placeDoor();
        }
        changeNeighboursDoor();
    }

    /** Places a door at the first spot it can find. */
    private void placeDoor() {
        if (canPlaceDoor(NORTH)) {
            setDoor(NORTH, true);
        } else if (canPlaceDoor(EAST)) {
            setDoor(EAST, true);
        } else if (canPlaceDoor(SOUTH)) {
            setDoor(SOUTH, true);
        } else if (canPlaceDoor(WEST)) {
            setDoor(WEST, true);
        }
    }

    /** Removes all doors. */
    private void removeDoors() {
        setDoor(EAST, false);
        setDoor(NORTH, false);
        setDoor(SOUTH, false);
        setDoor(WEST, false);
    }

    /**
     * This ensures that when a change is brought to the map, the doors can
     * still be reachable for the bots.
     */
    private void changeNeighboursDoor() {
        changeNeighbourDoor(NORTH, SOUTH);
        changeNeighbourDoor(EAST, WEST);
        changeNeighbourDoor(SOUTH, NORTH);
        changeNeighbourDoor(WEST, EAST);
    }

    /** Changes the door of one neighbour. */
    private void changeNeighbourDoor(int dir, int oppositdir) {
        if (neighbours[dir] != null
                && neighbours[dir].getType() == Type.ROOM
                && (neighbours[dir].hasDoor(oppositdir) || !neighbours[dir].hasDoors())) {
            
            if (!neighbours[dir].canPlaceDoors()) {
                neighbours[dir].removeDoors();
            } else {
                neighbours[dir].placeDoor(); 
            }
        }
    }

    /** If the neighbour is a corridor or charge zone, we can place a door on this side. */
    public boolean canPlaceDoor(int dir) {
        return getNeighbour(dir) != null
                && (getNeighbour(dir).type == Type.CORRIDOR || getNeighbour(dir).type == Type.CHARGINGZONE);
    }

    /** Returns true if a door can be placed. */
    private boolean canPlaceDoors() {
        return canPlaceDoor(NORTH) || canPlaceDoor(EAST) || canPlaceDoor(SOUTH)
                || canPlaceDoor(WEST);
    }

    /**
     * Gets the name of the Zone based on the type of Zone.
     * 
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
        if (cs == null) {
            throw new NullPointerException("Null list not allowed for colors");
        }
        colors = cs;
    }

    public boolean hasDoor(int dir) {
        return doorsbool[dir];
    }

    public boolean hasDoors() {
        return hasDoor(NORTH) || hasDoor(EAST) || hasDoor(SOUTH)
                || hasDoor(WEST);
    }

    public void setDoor(int dir, boolean value) {
        doorsbool = new boolean[4];
        doorsbool[dir] = value;
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

    public boolean[] getDoorsBool() {
        return doorsbool;
    }

    /**
     * @param zone
     *            The current zone.
     * @return The row the zone belongs to.
     */
    public static int calcRow(Zone zone) {
        double y = zone.getBoundingbox().getY();
        return calcRow(y - MapConverter.ROOMHEIGHT / 2.);
    }

    /**
     * @param y
     *            The position in y
     * @return The row the point belongs to.
     */
    public static int calcRow(double y) {
        return (int) (y / MapConverter.ROOMHEIGHT);
    }

    /**
     * @param zone
     *            The current zone.
     * @return The column the zone belongs to.
     */
    public static int calcColumn(Zone zone) {
        double x = zone.getBoundingbox().getX();
        return calcColumn(x - MapConverter.ROOMWIDTH / 2.);
    }

    /**
     * @param x
     *            The position in x
     * @return The column the point belongs to.
     */
    public static int calcColumn(double x) {
        return (int) (x / MapConverter.ROOMWIDTH);
    }

    private boolean isStartZone(Zone zone) {
        return zone.getName().startsWith("StartZone");
    }

    private boolean isDropZone(Zone zone) {
        return zone.getName().startsWith(Zone.DROP_ZONE_NAME);
    }

    public int getSpawnCount() {
        if (isStartZone()) {
            return SPAWN_POINTS_PER_START_ZONE;
        }
        return 0;
    }

    /**
     * Randomizes the blocks in a zone
     * @param amount the max amount of blocks in that zone
     * @param validcolors list of colors to choose from
     */
    public void generateRandomBlocks(int amount, List<BlockColor> validcolors) {
        if (getType() == Type.ROOM && !isDropZone()) {
            final int numColors = validcolors.size();
            Random random = new Random();
            ArrayList<BlockColor> colors = new ArrayList<BlockColor>();
            for (int i = 0; i < random.nextInt(amount) + 1; i++) {
                colors.add(validcolors.get(random.nextInt(numColors)));
            }
            setColors(colors);
        }
    }

    public void generateNameFromPosition(int row, int col) {
        StringBuilder sb = new StringBuilder();

        while (col >= 26) {
            sb.append((char) ('A' + (col % 26)));
            col = col / 26 - 1;
        }
        sb.append((char) ('A' + col));
        sb.reverse();

        sb.append(row + 1);
        setName(sb.toString());
    }

    public ZoneModel getNeighbour(int pos) {
        return neighbours[pos];
    }

    public void setNeighbour(int pos, ZoneModel neighbour) {
        neighbours[pos] = neighbour;
    }
}
