package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * The Map Model internal to the EnvironmentStore.
 */
public class EnvironmentMap {

    public static final int DROP_ZONE_SEQUENCE_LENGTH = 12;

    private ZoneModel[][] zones;
    /**
     * the target sequence.
     * */
    private List<BlockColor> sequence = new ArrayList<>();

    public EnvironmentMap(int rows, int cols) {
        if (rows < 1 || rows > 100) {
            throw new IllegalArgumentException("illegal value for row: 1 <= " + rows + " <= 100");
        }
        if (cols < 1 || cols > 100) {
            throw new IllegalArgumentException("illegal value for columns: 1 <= " + cols + " <= 100");
        }

        generateZones(rows, cols);
    }

    /**
     * make the map of zones the given size and initialize with default Zones.
     * 
     * @param rows
     *            the number of rows
     * @param cols
     *            the number of columns
     */
    private void generateZones(int rows, int cols) {
        zones = new ZoneModel[rows][cols];

        for (int row = 0; row < zones.length; row++) {
            for (int col = 0; col < zones[0].length; col++) {
                zones[row][col] = new ZoneModel();
                zones[row][col].generateNameFromPosition(row, col);
            }
        }
    }

    public int getRows() {
        return zones.length;
    }

    public int getColumns() {
        return zones[0].length;
    }

    /**
     * Get the zone at the given coordinates
     * 
     * @param row
     *            the row number
     * @param col
     *            the col number
     * @return the zone at the given position
     * @throws ArrayIndexOutOfBoundsException
     *             if the given coords are not within the map
     */
    public ZoneModel getZone(int row, int col) {
        return this.zones[row][col];
    }

    /**
     * Get the zone at the given coordinates
     * 
     * @param row
     *            the row number
     * @param col
     *            the col number
     * @param zone
     *            the zone at the given position
     * @throws ArrayIndexOutOfBoundsException
     *             if the given coords are not within the map
     */
    public void getZone(int row, int col, ZoneModel zone) {
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
        return row >= 0 && col >= 0 && row < zones.length && col < zones[0].length;
    }

    public void generateRandomBlocks(List<BlockColor> colors, int amount) {
        for (int row = 0; row < zones.length; row++) {
            for (int col = 0; col < zones[0].length; col++) {
                ZoneModel zm = getZone(row, col);
                zm.generateRandomBlocks(amount, colors);
            }
        }
    }

}
