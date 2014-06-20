package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

public class RandomMapCreator {
    public RandomMapCreator() {
        
    }
    /**
     * Create the entire random grid as a model for a randomized map.
     * @param rows The amount of rows in the table.
     * @param cols The amount of columns in the table.
     * @param roomCount The amount of rooms that need to be in the map.
     * @return A 2D array of nodes representing the randomized map.
     */
    public static ZoneModel[][] createRandomGrid(int rows, int cols, int roomCount) {
        if (rows <= 0) {
            throw new IllegalArgumentException("The amount of rows has to be positive and nonzero.");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("The amount of columns has to be positive and nonzero.");
        }
        Node[][] grid = new Node[rows][cols];
        initGrid(grid);
        createRooms(grid);
        int amountPossibleRooms = maxRoomsPossible(rows, cols);
        if (amountPossibleRooms < roomCount) {
            throw new IllegalArgumentException("The amount of rooms wanted"
            + " cannot be higher than the amount of rooms possible.");
        }
        if (roomCount < 1) {
            throw new IllegalArgumentException("The amount of rooms has to be at least one.");
        }
        List<Node> allRooms = getRooms(grid);
        List<Node> allCorridors = getCorridors(grid);
        randomizeCorridors(allCorridors);
        randomizeRooms(allRooms, amountPossibleRooms - roomCount);
        randomizeDoorDirs(allRooms);
        ZoneModel[][] newGrid = convertToZoneModelGrid(grid);
        selectRandomStartPoint(newGrid);
        selectRandomDropZone(newGrid);
        return newGrid;
    }
    /**
     * Convert a node grid to a zone model grid.
     * @param grid The node grid.
     * @return The representing node model grid.
     */
    private static ZoneModel[][] convertToZoneModelGrid(Node[][] grid) {
        ZoneModel[][] newGrid = new ZoneModel[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = new ZoneModel(grid[i][j]);
                newGrid[i][j].generateNameFromPosition(i, j);
            }
        }
        return newGrid;
    }
    /**
     * Calculates the max. amount of rooms in a map (use only for randomized maps).
     * @param r The amount of rows.
     * @param c The amount of columns.
     * @return The maximum amount of rooms in a map of this size.
     */
    public static int maxRoomsPossible(int r, int c) {
        return (c - 2)*(r/2);
    }
    
    /**
     * Uses a list of nodes classified as corridors and has a 1% chance
     * to reclassify a node as a charge zone.
     * @param corridors The list of corridors to be randomized.
     */
    private static void randomizeCorridors(List<Node> corridors) {
        Random r = new Random(System.currentTimeMillis());
        Node stdCharge = corridors.get(r.nextInt(corridors.size()));
        stdCharge.setType(Type.CHARGINGZONE);
        for (Node n : corridors) {
            if (r.nextDouble() < 0.01) {
                n.setType(Zone.Type.CHARGINGZONE);
            }
        }
    }
    private static void selectRandomStartPoint(ZoneModel[][] grid) {
        List<ZoneModel> list = getCorridors(grid);
        Random r = new Random(System.currentTimeMillis());
        int index = r.nextInt(list.size());
        list.get(index).setStartZone(true);
    }
    /**
     * Returns a list with all corridors, used in randomization.
     * @param grid The grid containing the corridors.
     * @return A list containing all the nodes classified as a corridor in the given grid.
     */
    private static List<ZoneModel> getCorridors(ZoneModel[][] grid) {
        List<ZoneModel> l = new LinkedList<ZoneModel>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType() == Zone.Type.CORRIDOR) {
                    l.add(grid[i][j]);
                }
            }
        }
        return l;
    }
    /**
     * Returns a list with all corridors, used in randomization.
     * @param grid The grid containing the corridors.
     * @return A list containing all the nodes classified as a corridor in the given grid.
     */
    private static List<Node> getCorridors(Node[][] grid) {
        List<Node> l = new LinkedList<Node>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType() == Zone.Type.CORRIDOR) {
                    l.add(grid[i][j]);
                }
            }
        }
        return l;
    }
    
    /**
     * Randomize the door directions for all rooms in such a way that
     * completing the map is still possible.
     * @param rooms The rooms that need a random door direction.
     */
    private static void randomizeDoorDirs(List<Node> rooms) {
        for (Node n : rooms) {
            Random r = new Random(System.currentTimeMillis());
            n.setDir(n.getFreeDirs().get(r.nextInt(n.getFreeDirs().size())));
        }
    }
    
    /**
     * Reduce the amount of rooms by the given integer to get a desired map,
     * by randomly selecting rooms from the list and reclassifying them.
     * @param rooms The list of rooms.
     * @param changedRooms The amount of rooms that need to be reclassified.
     */
    private static void randomizeRooms(List<Node> rooms, int changedRooms) {
        List<Zone.Type> typeList = new ArrayList<Zone.Type>();
        typeList.add(Zone.Type.CORRIDOR);
        typeList.add(Zone.Type.CORRIDOR);
        typeList.add(Zone.Type.CORRIDOR);
        typeList.add(Zone.Type.CORRIDOR);
        typeList.add(Zone.Type.CHARGINGZONE);
        typeList.add(Zone.Type.BLOCKADE);
        typeList.add(Zone.Type.BLOCKADE);
        typeList.add(Zone.Type.BLOCKADE);
        typeList.add(Zone.Type.BLOCKADE);
        typeList.add(Zone.Type.BLOCKADE);
        Random typeSelector = new Random(System.currentTimeMillis());
        for (int i = 0; i < changedRooms; i++) {
            Random r = new Random(System.currentTimeMillis());
            Node n = rooms.remove(r.nextInt(rooms.size()));
            n.setType(typeList.get(typeSelector.nextInt(10)));
        }
    }
    private static void selectRandomDropZone(ZoneModel[][] grid) {
        List<ZoneModel> list = getRooms(grid);
        Random r = new Random(System.currentTimeMillis());
        int index = r.nextInt(list.size());
        list.get(index).setDropZone(true);
    }
    /**
     * Returns a list of all the rooms in the grid.
     * @param grid The grid from which the rooms have to be extracted.
     * @return A list containing every tile in the grid classified as a room.
     */
    private static List<ZoneModel> getRooms(ZoneModel[][] grid) {
        List<ZoneModel> l = new LinkedList<ZoneModel>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType() == Zone.Type.ROOM) {
                    l.add(grid[i][j]);
                }
            }
        }
        return l;
    }
    /**
     * Returns a list of all the rooms in the grid.
     * @param grid The grid from which the rooms have to be extracted.
     * @return A list containing every tile in the grid classified as a room.
     */
    private static List<Node> getRooms(Node[][] grid) {
        List<Node> l = new LinkedList<Node>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType() == Zone.Type.ROOM) {
                    l.add(grid[i][j]);
                }
            }
        }
        return l;
    }
    
    /**
     * Create the initial rooms according to the standard model
     * of room creation (upper row no rooms, next row has rooms on
     * all tiles except for the leftmost and rightmost ones, next row has
     * no rooms, etc).
     * @param grid The grid in question.
     */
    private static void createRooms(Node[][] grid) {
        int lastCol = grid[0].length - 1;
        int lastRow = grid.length;
        for (int i = 1; i < lastRow; i++) {
            for (int j = 1; j < lastCol; j++) {
                if (i % 2 == 1) {
                    grid[i][j].setType(Zone.Type.ROOM);
                }
            }
        }
    }
    
    /**
     * Initialize the grid by creating a node object
     * for every square and then connecting adjacent node objects.
     * @param grid The grid to be initialized.
     */
    private static void initGrid(Node[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Node(Zone.Type.CORRIDOR);
            }
        }
        connectTiles(grid);
    }
    
    /**
     * Connect all tiles of a grid to their adjacent tiles
     * in the grid.
     * @param grid The grid of tiles that need to be connected.
     */
    private static void connectTiles(Node[][] grid) {
        configureCorners(grid);
        configureBorders(grid);
        configureInnerNodes(grid);
    }
    
    /**
     * Set the tiles that would be out of bound to null.
     * The corner nodes are connected to their adjacent grid tiles later.
     * @param grid The grid containing the corner tiles to be configured.
     */
    private static void configureCorners(Node[][] grid) {
        int lastCol = grid[0].length - 1;
        int lastRow = grid.length - 1;
        grid[0][0].setNorth(null);
        grid[0][0].setWest(null);
        grid[lastRow][0].setSouth(null);
        grid[lastRow][0].setWest(null);
        grid[0][lastCol].setEast(null);
        grid[0][lastCol].setNorth(null);
        grid[lastRow][lastCol].setEast(null);
        grid[lastRow][lastCol].setSouth(null);
    }
    
    /**
     * Connect all the borders, setting the tiles out of bound to null.
     * @param grid The grid in question.
     */
    private static void configureBorders(Node[][] grid) {
        int lastCol = grid[0].length - 1;
        int lastRow = grid.length - 1;
        // Connect upper and lower rows.
        for (int i = 1; i < lastCol; i++) {
            grid[0][i].setNorth(null);
            grid[0][i].setEast(grid[0][i + 1]);
            grid[0][i + 1].setWest(grid[0][i]);
            grid[0][i].setWest(grid[0][i - 1]);
            grid[0][i - 1].setEast(grid[0][i]);
            grid[0][i].setSouth(grid[1][i]);
            grid[1][i].setNorth(grid[0][i]);
            grid[lastRow][i].setSouth(null);
            grid[lastRow][i].setEast(grid[lastRow][i + 1]);
            grid[lastRow][i + 1].setWest(grid[lastRow][i]);
            grid[lastRow][i].setWest(grid[lastRow][i - 1]);
            grid[lastRow][i - 1].setEast(grid[lastRow][i]);
            grid[lastRow][i].setNorth(grid[lastRow][i - 1]);
            grid[lastRow][i - 1].setSouth(grid[lastRow][i]);
        }
        // Connect leftmost and rightmost columns.
        for (int i = 1; i < lastRow; i++) {
            grid[i][0].setWest(null);
            grid[i][0].setNorth(grid[i - 1][0]);
            grid[i - 1][0].setSouth(grid[i][0]);
            grid[i][0].setSouth(grid[i + 1][0]);
            grid[i + 1][0].setNorth(grid[i][0]);
            grid[i][0].setEast(grid[i][1]);
            grid[i][1].setWest(grid[i][0]);
            grid[i][lastCol].setEast(null);
            grid[i][lastCol].setNorth(grid[i - 1][lastCol]);
            grid[i - 1][lastCol].setSouth(grid[i][lastCol]);
            grid[i][lastCol].setSouth(grid[i + 1][lastCol]);
            grid[i + 1][lastCol].setNorth(grid[i][lastCol]);
            grid[i][lastCol].setWest(grid[i][lastCol - 1]);
            grid[i][lastCol - 1].setEast(grid[i][lastCol]);
        }
    }
    
    /**
     * Connect all the nodes within the outer areas to their adjacent
     * tiles and vice versa.
     * @param grid The grid in question.
     */
    private static void configureInnerNodes(Node[][] grid) {
        int lastCol = grid[0].length - 1;
        int lastRow = grid.length - 1;
        for (int i = 1; i < lastRow; i++) {
            for (int j = 1; j < lastCol; j++) {
                grid[i][j].setNorth(grid[i - 1][j]);
                grid[i - 1][j].setSouth(grid[i][j]);
                grid[i][j].setEast(grid[i][j + 1]);
                grid[i][j + 1].setWest(grid[i][j]);
                grid[i][j].setSouth(grid[i + 1][j]);
                grid[i + 1][j].setNorth(grid[i][j]);
                grid[i][j].setWest(grid[i][j - 1]);
                grid[i][j - 1].setEast(grid[i][j]);
            }
        }
    }
}
