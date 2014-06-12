package nl.tudelft.bw4t.map.editor.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

/**
 * This holds the map that the user designed. This is an abstract map contianing only number of rows and columns, do not
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
                zonecontrollers[i][j] = new ZoneController(this, i, j, new nl.tudelft.bw4t.map.editor.model.Zone());
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
                map.addZone(output[row][col]);
                // TODO add the doors to the map
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
