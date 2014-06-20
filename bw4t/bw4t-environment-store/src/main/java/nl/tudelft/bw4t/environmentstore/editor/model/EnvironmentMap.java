package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * The Map Model internal to the EnvironmentStore.
 */
public class EnvironmentMap {

    /** the max sequence length */
    public static final int DROP_ZONE_SEQUENCE_LENGTH = 12;

    /** contains zones on the same x and y location as the map editor */
    private ZoneModel[][] zones;
    
    /** true if map has a startzone */
    private boolean startzone;
    
    /** true if map has a dropzone */
    private boolean dropzone;
    
    /**
     * the target sequence.
     * */
    private List<BlockColor> sequence = new ArrayList<>();

    /**
     * Constructs the environmentMap and adds default zones to every zones[][] cell
     * Also connects all these zones with their respective neighbor
     * @param rows the amount of rows the maps has
     * @param cols the amount of columns the map has
     */
    public EnvironmentMap(int rows, int cols) {
        if (rows < 1 || rows > 100) {
            throw new IllegalArgumentException("illegal value for row: 1 <= "
                    + rows + " <= 100");
        }
        if (cols < 1 || cols > 100) {
            throw new IllegalArgumentException(
                    "illegal value for columns: 1 <= " + cols + " <= 100");
        }

        generateZones(rows, cols);
        initZoneNeighbours();
    }

    /**
     * make the map of zones the given size and initialise with default Zones.
     * 
     * @param rows
     *            the number of rows
     * @param cols
     *            the number of columns
     */
    private void generateZones(int rows, int cols) {
        zones = new ZoneModel[rows][cols];

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                zones[row][col] = new ZoneModel();
                zones[row][col].generateNameFromPosition(row, col);
            }
        }
    }
    
    /**
     * Attach the corresponding neighbours to each zone. 
     */
    private void initZoneNeighbours() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                if (col == getColumns() - 1) {
                    zones[row][col].setNeighbour(ZoneModel.EAST, null);
                } else {
                    zones[row][col].setNeighbour(ZoneModel.EAST, zones[row][col + 1]);
                }
                if (row == 0) {
                    zones[row][col].setNeighbour(ZoneModel.NORTH, null);
                } else {
                    zones[row][col].setNeighbour(ZoneModel.NORTH, zones[row - 1][col]);
                }
                if (row == getRows() - 1) {
                    zones[row][col].setNeighbour(ZoneModel.SOUTH, null);
                } else {
                    zones[row][col].setNeighbour(ZoneModel.SOUTH, zones[row + 1][col]);
                }
                if (col == 0) {
                    zones[row][col].setNeighbour(ZoneModel.WEST, null);
                } else {
                    zones[row][col].setNeighbour(ZoneModel.WEST, zones[row][col - 1]);
                }
            }
        }
    }

    public int getRows() {
        return zones.length;
    }

    public int getColumns() {
        return zones[0].length;
    }

    public void setZones(ZoneModel[][] model) {
        if (model == null || model.length < 2 || model[0].length < 2) {
            throw new IllegalArgumentException(
                    "The given grid is not suitable!");
        }
        this.zones = model;
        initZoneNeighbours();
    }

    /**
     * Get the zone at the given coordinates
     * 
     * @param row
     *            the row number
     * @param col
     *            the col number
     * @return the zone at the given position
     */
    public ZoneModel getZone(int row, int col) {
        return this.zones[row][col];
    }

    /**
     * Set the zone at the given coordinates
     * 
     * @param row
     *            the row number
     * @param col
     *            the col number
     * @param zone
     *            the zone at the given position
     * @throws NullPointerException
     *               a zone can't be null
     */
    public void setZone(int row, int col, ZoneModel zone) {
        if (zone == null) {
            throw new NullPointerException("A zone can not be null");
        }
        this.zones[row][col] = zone;
    }

    public List<BlockColor> getSequence() {
        return sequence;
    }

    public void setSequence(List<BlockColor> sequence) {
        this.sequence = sequence;
    }

    /**
     * get the number of spawn points on the map. *
     * 
     * @return the number of spawns in the map.
     */
    public int getSpawnCount() {
        int spawns = 0;
        for (int row = 0; row < zones.length; row++) {
            for (int col = 0; col < zones[0].length; col++) {
                spawns += getZone(row, col).getSpawnCount();
            }
        }
        return spawns;
    }

    /**
     * @param row
     *            the row number
     * @param col
     *            the column number
     * @return true iff the given coordinates are inside the map
     */
    public boolean isValidZone(int row, int col) {
        return row >= 0 && col >= 0 && row < zones.length
                && col < zones[0].length;
    }

    /**
     * Randomizes the blocks in all rooms
     * @param colors valid list of colors to randomize from
     * @param amount max amount of blocks per room
     */
    public void generateRandomBlocks(List<BlockColor> colors, int amount) {
        for (int row = 0; row < zones.length; row++) {
            for (int col = 0; col < zones[0].length; col++) {
                ZoneModel zm = getZone(row, col);
                zm.generateRandomBlocks(amount, colors);
            }
        }
    }

    public boolean hasStartzone() {
        return startzone;
    }

    public void setStartzone(boolean startzone) {
        this.startzone = startzone;
    }

    public boolean hasDropzone() {
        return dropzone;
    }

    public void setDropzone(boolean dropzone) {
        this.dropzone = dropzone;
    }

}
