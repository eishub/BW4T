package nl.tudelft.bw4t.map.editor.controller;


import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.map.editor.model.Zone;

/**
 * This contains the room information: the blocks that the room contains.
 */
public class ZoneController {

    private int row, column; // should match the position in the map table
    
    private Zone model;
    
    private MapPanelController mapcontroller;

    public ZoneController(MapPanelController mc, int r, int c, Zone m) {
    	mapcontroller = mc;
    	
        if (r > 24 || r < 0) {
            // prepare to compare rows to 'A'..'Y'. we need r=24 for the
            // dropzone row and
            throw new IllegalArgumentException("Row should be in range 0..24");
        }
        if (c < 0) {
            throw new IllegalArgumentException(
                    "Column should be bigger or equal to 0");
        }

        row = r;
        column = c;
        
        model = m;
    }
    
    public MapPanelController getMapController() {
    	return mapcontroller;
    }
    
    public Type getType() {
    	return model.getType();
    }
    
    public String getName() {
    	return model.getName();
    }

    public List<BlockColor> getColors() {
        return model.getColors();
    }

    public void setColors(List<BlockColor> cs) {
    	model.setColors(cs);
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
        return "Zone" + (char) (row + 65) + (column + 1);
    }

    /**
     * Sets the value identical to the given room. The coordinates of the
     * otherRoom are ignored, assuming that the original coordinates of our room
     * are correct.
     * 
     * @param otherRoom
     */
    public void setValue(ZoneController otherRoom) {
        setColors(otherRoom.getColors());
    }

}
