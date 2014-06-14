package nl.tudelft.bw4t.map.editor.controller;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.map.editor.gui.ColorSequenceEditor;
import nl.tudelft.bw4t.map.editor.model.ZoneModel;

/**
 * This contains the room information: the blocks that the room contains.
 */
public class ZoneController extends MouseAdapter implements ChangeListener {

    /** should match the position in the map table */
    private int row, column;
    
    private ZoneModel model;
    
    private MapPanelController mapController;
    
    private UpdateableEditorInterface uei;

    /** Create a new ZoneController from the MapPanelController, rows, columns, and Zone model.
     * @param mc MapPanelController
     * @param r rows
     * @param c columns
     * @param m Zone model
     */
    public ZoneController(MapPanelController mc, int r, int c, ZoneModel m) {
    	mapController = mc;
    	
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
        
        m.setName("" + (char)('A' + c) + (r + 1));
    }
    
    public MapPanelController getMapController() {
    	return mapController;
    }
    
    public Type getType() {
    	return model.getType();
    }
    
    public void setType(Type t) {
    	model.setType(t);
    }
    
    public boolean isDropZone() {
    	return model.isDropZone();
    }
    
    public void setDropZone(boolean isDZ) {
    	model.setDropZone(isDZ);
    }
    
    public boolean isStartZone() {
    	return model.isStartZone();
    }
    
    public void setStartZone(boolean isSZ) {
    	model.setStartZone(isSZ);
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

	public UpdateableEditorInterface getUpdateableEditorInterface() {
		return uei;
	}

	public void setUpdateableEditorInterface(UpdateableEditorInterface ui) {
		uei = ui;
	}
	
	private void openPopup(MouseEvent e) {
	    if (e.isPopupTrigger()) {
            getMapController().setSelected(this);
            
            getMapController().showPopup(e.getComponent(), e.getX(), e.getY());
        }
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	    openPopup(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	    openPopup(e);
	}

    @Override
    public void stateChanged(ChangeEvent arg0) {
        this.model.setColors(((ColorSequenceEditor)arg0.getSource()).getSequence());
    }

    public boolean hasDoor(int dir) {
        return this.model.hasDoor(dir);
    }
    
	public ZoneModel getZoneModel() {
		return model;
	}
	
	public void setZoneModel(ZoneModel model) {
		this.model = model;
	}
	
    
    public void makeRandomColorRoom(int amount, ArrayList<BlockColor> validcolors) {
    	Random random = new Random();
		ArrayList<BlockColor> colors = new ArrayList<BlockColor>();
    	for(int i = 0; i <  amount; i++) {
    		colors.add(validcolors.get(random.nextInt(validcolors.size())));
    	}
    	model.setColors(colors);
    	uei.update();
    }

}
