package nl.tudelft.bw4t.map.editor.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Door;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.map.RenderOptions;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.map.editor.controller.ZoneController;
import nl.tudelft.bw4t.map.editor.gui.ColorSequenceEditor;
import nl.tudelft.bw4t.map.editor.gui.ZonePopupMenu;
import nl.tudelft.bw4t.map.editor.model.Node;

/**
 * This holds the map that the user designed. This is an abstract map containing only number of rows and columns, do not
 * confuse with {@link NewMap}.
 */
public class MapPanelController implements ChangeListener {

    /** basic size of the map */
    private ZoneController[][] zonecontrollers;
    
    private ColorSequenceController cscontroller;
    
    private int numberOfEntities = 0;
    
    private boolean randomize;
    
    private ZoneController selected = null;
    /**
     * the target sequence.
     * */
    private List<BlockColor> sequence = new ArrayList<>();

    /**
     * constants that map rooms to real positions on the map.
     */
    static final double ROOMHEIGHT = 10;
    static final double ROOMWIDTH = 10;
    static final int CORRIDORWIDTH = 10;
    static final int CORRIDORHEIGHT = 10;

    /**
     * bot initial displacements
     */
    static final int XDISP = 4;
    static final int YDISP = 2;
    private static final String DROPZONE = "DropZone";
    private static final String FRONTDROPZONE = "FrontDropZone";
    public static final int DROP_ZONE_SEQUENCE_LENGTH = 12;

    private boolean isLabelsVisible;

    private UpdateableEditorInterface uei;

    private ZonePopupMenu zoneMenu = new ZonePopupMenu(this);

    /**
     * size of map is fixed, you can't change it after construction.
     * 
     * @param rows
     * @param columns
     * @param isLabelsVisible
     *            true if labels should be shown by renderers
     */
    public MapPanelController(int rows, int columns, int entities, boolean rand, boolean labelsVisible) {
        isLabelsVisible = labelsVisible;
        if (rows < 1 || rows > 100) {
            throw new IllegalArgumentException("illegal value for row:" + rows);
        }
        if (columns < 1 || columns > 100) {
            throw new IllegalArgumentException("illegal value for columns:" + columns);
        }
        if (entities < 1 || entities > 20) {
            throw new IllegalArgumentException("illegal value for entities:" + entities);
        }

        this.numberOfEntities = entities;
        this.randomize = rand;

        cscontroller = new ColorSequenceController();
        zonecontrollers = new ZoneController[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                zonecontrollers[i][j] = new ZoneController(this, i, j, new nl.tudelft.bw4t.map.editor.model.ZoneModel());
            }
        }

        attachListenersToZoneMenu();
    }

    /**
     * this method attaches all the listeners to the Zone Menu.
     */
    public void attachListenersToZoneMenu() {
        this.getZoneMenu().getMenuItemZoneBlockade().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createZone(Type.BLOCKADE, false, false);
            }
        });

        this.getZoneMenu().getMenuItemZoneChargingZone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createZone(Type.CHARGINGZONE, false, false);
            }
        });

        this.getZoneMenu().getMenuItemZoneCorridor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createZone(Type.CORRIDOR, false, false);
            }
        });

        this.getZoneMenu().getMenuItemZoneDropZone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createZone(Type.ROOM, true, false);
            }
        });

        this.getZoneMenu().getMenuItemZoneRoom().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createZone(Type.ROOM, false, false);
            }
        });

        this.getZoneMenu().getMenuItemZoneStartZone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createZone(Type.CORRIDOR, false, true);
            }
        });
    }

    /**
     * The createZone method creates a Zone and updates the UpdateableEditorInterface.
     * 
     * @param t is the type of Zone that we would like to create.
     * @param isDropZone is true if the Zone that we are creating is a dropZone.
     * @param isStartZone is true if the Zone that we are creating is a startZone.
     */
    public void createZone(Type t, boolean isDropZone, boolean isStartZone) {
        if (selected != null) {
            selected.setType(t);
            selected.setDropZone(isDropZone);
            selected.setStartZone(isStartZone);
            selected.getUpdateableEditorInterface().update();
        }
        selected = null;
    }

    public int getRows() {
        return zonecontrollers.length;
    }

    public int getColumns() {
        return zonecontrollers[0].length;
    }

    public List<BlockColor> getSequence() {
        return sequence;
    }

    public void setSequence(List<BlockColor> colorSequence) {
        this.sequence = colorSequence;
    }

    /**
     * Check that given row and column are inside the actual map.
     * 
     * @param r
     *            is row
     * @param c
     *            is column
     * @throws IllegalArgumentException
     *             if r<0, c<0, r>=rows or c>=columns
     */
    public void checkCoord(int r, int c) throws IllegalArgumentException {
        if (r < 0) {
            throw new IllegalArgumentException("row is negative");
        }
        if (c < 0) {
            throw new IllegalArgumentException("column is negative");
        }
        if (r >= zonecontrollers.length) {
            throw new IllegalArgumentException("rownr is too high");
        }
        if (c >= zonecontrollers[0].length) {
            throw new IllegalArgumentException("columnnr is too high");
        }
    }

    /**
     * get the number of entities on the map.     * 
     * @return the number of entities in the map.
     */
    public int getNumberOfEntities() {
        return numberOfEntities;
    }


    /**
     * Set the maximum number of entities in the map.
     * @param numberOfEntities is the maximum number of entities.
     */
    public void setNumberOfEntities(int numberOfEntities) {
        this.numberOfEntities = numberOfEntities;
    }

    public UpdateableEditorInterface getUpdateableEditorInterface() {
        return uei;
    }

    public void setUpdateableEditorInterface(UpdateableEditorInterface ui) {
        uei = ui;
    }

    public ColorSequenceController getCSController() {
        return cscontroller;
    }

    public ZoneController getSelected() {
        return selected;
    }

    public void setSelected(ZoneController selected) {
        this.selected = selected;
    }

    public ZonePopupMenu getZoneMenu() {
        return zoneMenu;
    }

    /**
     * Save the real map to given file
     * 
     * @param file
     * @throws IOException
     * @throws JAXBException
     */
    public void save(File file) throws IOException, JAXBException {
        System.out.println("SAVE to " + file);

        String error = checkConsistency();
        if (error != null) {
            throw new IllegalStateException("save failed: " + error);
        }

        NewMap map = createMap();
        JAXBContext context = JAXBContext.newInstance(NewMap.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(map, new FileOutputStream(file));

    }

    /**
     * Add entities to map
     * 
     * @param map
     * @param centerx
     *            the position of first entity
     * @param centery
     *            the position of first entity
     */
    void addEntities(NewMap map, double centerx, double centery) {

        // set entities. start at 1.
        for (int n = 1; n <= numberOfEntities; n++) {
            int n6 = n / 6; // floor
            int m6 = n % 6;
            int m6sign = (m6 % 2) * 2 - 1; // 1 if m6=odd, -1 if even

            int extrax = 1;
            if (m6 <= 1)
                extrax = 0;
            double x = centerx + (XDISP * (2 * n6 + extrax)) * m6sign;

            int yoffset = 0;
            if (m6 == 2 || m6 == 3)
                yoffset = -1;
            if (m6 == 4 || m6 == 5)
                yoffset = 1;
            double y = centery + YDISP * yoffset;
            map.addEntity(new Entity("Bot" + n, new Point(x, y)));
        }

    }

    /**
     * Create the real map object using the settings
     * 
     * @return NewMap the new map that has been created.
     * @throws MapFormatException if no dropZone or no startZone is found.
     */
    public NewMap createMap() throws MapFormatException {
        NewMap map = new NewMap();

        // compute a number of key values
        double mapwidth = getColumns() * ROOMWIDTH;
        double mapheight = getRows() * ROOMHEIGHT;

        // set the general fields of the map
        map.setArea(new Point(mapwidth, mapheight));
        map.setSequence(sequence);
        if (randomize) {
            map.setRandomBlocks((int) (2.5 * zonecontrollers.length * zonecontrollers[0].length));
            map.setRandomSequence(2 * zonecontrollers.length * zonecontrollers[0].length / 3);
        }

        // addEntities(map, dropzonex, dropzoney - ROOMHEIGHT / 2 - CORRIDORHEIGHT / 2);

        // generate zones for each row:
        // write room zones with their doors. and the zone in frront
        // also generate the lefthall and righthall for each row.
        // connect room and corridor in front of it.
        // connect all corridor with each other and with left and right hall.
        boolean foundDropzone = false;
        boolean foundStartzone = false;
        Zone[][] output = new Zone[getRows()][getColumns()];
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                ZoneController room = getZoneController(row, col);
                if (room.isDropZone()) {
                    if (foundDropzone) {
                        throw new MapFormatException("Only one DropZone allowed per map!");
                    }
                    foundDropzone = true;
                }
                if (room.isStartZone()) {
                    foundStartzone = true;
                }
                output[row][col] = new Zone(room.getName(),
                        new Rectangle(calcX(col), calcY(row), ROOMWIDTH, ROOMHEIGHT), room.getType());
                //TODO DOORS
                if (output[row][col].getType() == Type.ROOM){
                	
                }
                map.addZone(output[row][col]);
                // TODO add Entity spawn points on Startzones
                output[row][col].setBlocks(room.getColors());
            }
        }
        // connect all the zones
        connect(output);
        if (!foundDropzone) {
            throw new MapFormatException("No DropZone found on the map!");
        }
        if (!foundStartzone) {
            throw new MapFormatException("No StartZone found on the map!");
        }

        setRenderOptions(map);
        return map;
    }
    
    /**
     * Create the entire random grid as a model for a randomized map.
     * @param rows The amount of rows in the table.
     * @param cols The amount of columns in the table.
     * @param roomCount The amount of rooms that need to be in the map.
     * @return A 2D array of nodes representing the randomized map.
     */
    public Node[][] createRandomGrid(int rows, int cols, int roomCount) {
    	assert rows > 0 && cols > 0 : "The amount of rows and colums must be"
    		+ " greater than 0.";
    	Node[][] grid = new Node[rows][cols];
    	initGrid(grid);
    	createRooms(grid);
    	int amountPossibleRooms = maxRoomsPossible(rows, cols);
    	assert amountPossibleRooms >= roomCount : "The amount of rooms wanted"
    		+ " cannot be higher than the amount of rooms possible.";
    	List<Node> allRooms = getRooms(grid);
    	List<Node> allCorridors = getCorridors(grid);
    	randomizeCorridors(allCorridors);
    	randomizeRooms(allRooms, amountPossibleRooms - roomCount);
    	randomizeDoorDirs(allRooms);
    	return grid;
    }
    /**
     * Calculates the max. amount of rooms in a map (use only for randomized maps).
     * @param r The amount of rows.
     * @param c The amount of columns.
     * @return The maximum amount of rooms in a map of this size.
     */
    public int maxRoomsPossible(int r, int c) {
    	return (c - 2)*(r/2);
    }
    
    /**
     * Uses a list of nodes classified as corridors and has a 15% chance
     * to reclassify a node as a charge zone.
     * @param corridors The list of corridors to be randomized.
     */
    private void randomizeCorridors(List<Node> corridors) {
    	Random r = new Random(System.currentTimeMillis());
    	for (Node n : corridors) {
    		if (r.nextDouble() < 0.15) {
    			n.setType(Zone.Type.CHARGINGZONE);
    		}
    	}
    }
    
    /**
     * Returns a list with all corridors, used in randomization.
     * @param grid The grid containing the corridors.
     * @return A list containing all the nodes classified as a corridor in the given grid.
     */
    private List<Node> getCorridors(Node[][] grid) {
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
    private void randomizeDoorDirs(List<Node> rooms) {
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
    private void randomizeRooms(List<Node> rooms, int changedRooms) {
    	List<Zone.Type> typeList = new ArrayList<Zone.Type>();
    	typeList.add(Zone.Type.CORRIDOR);
    	typeList.add(Zone.Type.CHARGINGZONE);
    	typeList.add(Zone.Type.BLOCKADE);
    	Random typeSelector = new Random(System.currentTimeMillis());
    	for (int i = 0; i < changedRooms; i++) {
    		Random r = new Random(System.currentTimeMillis());
    		Node n = rooms.remove(r.nextInt(rooms.size()));
    		n.setType(typeList.get(typeSelector.nextInt(3)));
    	}
    }
    
    /**
     * Returns a list of all the rooms in the grid.
     * @param grid The grid from which the rooms have to be extracted.
     * @return A list containing every tile in the grid classified as a room.
     */
    private List<Node> getRooms(Node[][] grid) {
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
    private void createRooms(Node[][] grid) {
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
    private void initGrid(Node[][] grid) {
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
    private void connectTiles(Node[][] grid) {
    	configureCorners(grid);
    	configureBorders(grid);
    	configureInnerNodes(grid);
    }
    
    /**
     * Set the tiles that would be out of bound to null.
     * The corner nodes are connected to their adjacent grid tiles later.
     * @param grid The grid containing the corner tiles to be configured.
     */
    private void configureCorners(Node[][] grid) {
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
    private void configureBorders(Node[][] grid) {
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
    private void configureInnerNodes(Node[][] grid) {
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
    
    /**
     * Check what type of zone the current zone is. Then call the correct connect method.
     * @param zones - matrix of the map
     */
    private void connect(Zone[][] zones){
    	for(int row = 0; row < zones.length; row++){
    		for( int col = 0; col < zones[0].length; col++){
    			if(zones[row][col].getType() == Type.CORRIDOR || zones[row][col].getType() == Type.CHARGINGZONE){
    				connectCorridor(zones, row, col);
    			} else if(zones[row][col].getType() == Type.ROOM){
    				connectRoom(zones, row, col);
    			} else {
    				//it is a blockade and thus it should not be connected.
    			}
    		}
    	}
    }
    
    /**
     * For a corridor all adjacent zones should be added.
     * @param zones - matrix of the map
     * @param row indicates where the current zone is
     * @param col indicates where the current zone is
     */
    private void connectCorridor(Zone[][] zones, int row, int col) {
    	connectWest(zones, row, col);
    	connectNorth(zones, row, col);
    	connectEast(zones, row, col);
    	connectSouth(zones, row, col);
    }
    
    /**
     * For a room, only adjacent zones where a door is positioned should be added.
     * @param zones - matrix of the map
     * @param row indicates where the current zone is
     * @param col indicates where the current zone is
     */
    private void connectRoom(Zone[][] zones, int row, int col){
    	if (zones[row][col].hasEast()) {
    		connectEast(zones, row, col);
    	}
    	if (zones[row][col].hasNorth()) {
    		connectNorth(zones, row, col);
    	}
    	if (zones[row][col].hasWest()) {
    		connectWest(zones, row, col);
    	}
    	if (zones[row][col].hasSouth()) {
    		connectSouth(zones, row, col);
    	}
    }
    
    /**
     * Connect the west neighbour
     * @param zones - matrix of the map
     * @param row indicates where the current zone is
     * @param col indicates where the current zone is
     */
    private void connectWest(Zone[][] zones, int row, int col){
    	try{
    		if ((zones[row][col - 1].getType() == Type.ROOM && zones[row][col - 1].hasEast()) || 
    				zones[row][col - 1].getType() == Type.CORRIDOR || 
    				zones[row][col - 1].getType() == Type.CHARGINGZONE){
    			zones[row][col].addNeighbour(zones[row][col - 1]);
    		}
    	} catch(IndexOutOfBoundsException e){
    		//Do nothing.
    	}
    }
    
    /**
     * Connect the north neighbour
     * @param zones - matrix of the map
     * @param row indicates where the current zone is
     * @param col indicates where the current zone is
     */
    private void connectNorth(Zone[][] zones, int row, int col){
    	try{
    		if ((zones[row - 1][col].getType() == Type.ROOM && zones[row - 1][col].hasSouth()) || 
    				zones[row - 1][col].getType() == Type.CORRIDOR || 
    				zones[row - 1][col].getType() == Type.CHARGINGZONE){
    			zones[row][col].addNeighbour(zones[row - 1][col]);
    		}
    	} catch(IndexOutOfBoundsException e){
    		//Do nothing.
    	}
    }
    
    /**
     * Connect the east neighbour
     * @param zones - matrix of the map
     * @param row indicates where the current zone is
     * @param col indicates where the current zone is
     */
    private void connectEast(Zone[][] zones, int row, int col){
    	try{
    		if ((zones[row][col + 1].getType() == Type.ROOM && zones[row][col + 1].hasWest()) || 
    				zones[row][col + 1].getType() == Type.CORRIDOR || 
    				zones[row][col + 1].getType() == Type.CHARGINGZONE){
    			zones[row][col].addNeighbour(zones[row][col + 1]);
    		}
    	} catch(IndexOutOfBoundsException e){
    		//Do nothing.
    	}
    }
    
    /**
     * Connect the south neighbour
     * @param zones - matrix of the map
     * @param row indicates where the current zone is
     * @param col indicates where the current zone is
     */
    private void connectSouth(Zone[][] zones, int row, int col){
    	try{
    		if ((zones[row + 1][col].getType() == Type.ROOM && zones[row + 1][col].hasNorth()) || 
    				zones[row + 1][col].getType() == Type.CORRIDOR || 
    				zones[row + 1][col].getType() == Type.CHARGINGZONE){
    			zones[row][col].addNeighbour(zones[row + 1][col]);
    		}
    	} catch(IndexOutOfBoundsException e){
    		//Do nothing.
    	}
    }
    
    public int determineDoorIndex(int row1, int col1, int row2, int col2) {
    	if (col1 == col2) {
    		if (row1 > row2) {
    			return 0;
    		}
    		else {
    			return 2;
    		}
    	}
    	else {
    		if (col1 > col2) {
    			return 3;
    		} 
    		else {
    			return 1;
    		}
    	}
    }

    /**
     * Get the zone from certain coordinates.
     * @param zones
     * @param row
     * @param col
     * @return the Zone if it is a valid zone, else return null.
     */
    private Zone getZone(Zone[][] zones, int row, int col) {
        if (isValidZone(row, col)) {
            return zones[row][col];
        }
        return null;
    }

    /**
     * Set all the render options of the map.
     * 
     * @param map
     */
    private void setRenderOptions(NewMap map) {
        // do we need the render options? Check against defaults.
        if (isLabelsVisible) {
            return;
        }
        RenderOptions opts = new RenderOptions();
        opts.setLabelVisible(isLabelsVisible);

        for (Zone zone : map.getZones()) {
            zone.setRenderOptions(opts);
        }

    }

    /**
     * get x coordinate of center of the room on the map.
     * 
     * @param column is the column cordinate of the Zone.
     * @return x coordinate of room on the map.
     */
    public double calcX(int column) {
        return column * ROOMWIDTH + ROOMWIDTH / 2;
    }

    /**
     * get y coordinate of center of room on the map.
     * 
     * @param row is the row cordinate of the Zone.
     * @return y coordinate of room on the map.
     */
    public double calcY(int row) {
        return row * ROOMHEIGHT + ROOMHEIGHT / 2;
    }

    /**
     * get a navpoint label for a corridor location.
     * 
     * @param row
     *            allowed is 0..{@link #rows}-1
     * @param col
     *            col=-1 means the LeftHall, col={@link #columns} means RightHall.
     * @return
     */
    private String corridorLabel(int row, int col) {
        if (col == -1) {
            return "LeftHall" + (char) (row + 65);
        }
        if (col == zonecontrollers[0].length) {
            return "RightHall" + (char) (row + 65);
        }
        return "Front" + zonecontrollers[row][col].toString();
    }

    /**
     * check consistency of the map. The sequence length must be 1 at least. returns string with error message, or null
     * if all ok
     * 
     * @return null, or string with error message.
     */
    public String checkConsistency() {
        if (numberOfEntities < 1) {
            return "There should be at least 1 entity";
        }
        if (sequence.size() <= 0 && !randomize) {
            return "Sequence must contain at least 1 block color";
        }

        // check if all blocks for sequence are there.
        // first accumulate all blocks from all rooms
        List<BlockColor> allblocks = new ArrayList<BlockColor>();
        for (int i = 0; i < zonecontrollers.length; i++) {
            for (int j = 0; j < zonecontrollers[0].length; j++) {
                allblocks.addAll(zonecontrollers[i][j].getColors());
            }
        }

        // first check if there are blocks while random is on
        if (randomize && (!allblocks.isEmpty() || !sequence.isEmpty())) {
            EnvironmentStore
                    .showDialog("There are blocks on the map\nbut the map is set to random.\nWe proceed anyway.");
        }

        // remove all colors from the sequence. That will throw exception if
        // impossible.
        try {
            allblocks.removeAll(sequence);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return null;

    }

    /**
     * Ask user where to save and then call {@link #save(File)}
     */
    public void saveAsFile() {
        try {
            // check before user puts effort in
            String state = checkConsistency();
            if (state != null) {
                throw new IllegalStateException("Map is not ready for save.\n" + state);
            }
            // TODO Auto-generated method stub
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                save(chooser.getSelectedFile());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            EnvironmentStore.showDialog(e, "Save failed: " + e.getMessage());
        }
    }

    public ZoneController getZoneController(int row, int col) {
        if (isValidZone(row, col)) {
            return zonecontrollers[row][col];
        }
        return null;
    }

    /**
     * Checks if there is a Zone at certain row,col cordinates.
     * @param row
     * @param col
     * @return
     */
    public boolean isValidZone(int row, int col) {
        return row >= 0 && col >= 0 && row < getRows() && col < getColumns();
    }

    /**
     * Show the ZoneMenu popup at given cordinates.
     * 
     * @param component
     * @param x
     * @param y
     */
    public void showPopup(Component component, int x, int y) {
        getZoneMenu().update();
        getZoneMenu().show(component, x, y);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.sequence = ((ColorSequenceEditor) e.getSource()).getSequence();

    }
    
    /**
     * Creates a random sequence
     * @param input	the list of available colors
     * @param sequencelength how long you want the sequence to be
     * @return the random sequence
     */
    public List<BlockColor> makeRandomSequence(ArrayList<BlockColor> input, int sequencelength) {
        Random random = new Random();
        for (int n = 0; n < sequencelength; n++) {
            sequence.add(input.get(random.nextInt(input.size())));
        }
        return sequence;
    }
}
