package nl.tudelft.bw4t.environmentstore.editor.controller;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * This contains the room information: the blocks that the room contains.
 */
public class ZoneController extends MouseAdapter implements ChangeListener {
    
    private ZoneModel model;
    
    private MapPanelController mapController;
    
    private UpdateableEditorInterface uei;

    /** Create a new ZoneController from the MapPanelController, rows, columns, and Zone model.
     * @param mc MapPanelController
     * @param m Zone model
     */
    public ZoneController(MapPanelController mc, ZoneModel m) {
        mapController = mc;
        model = m;
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

    /**
     * Sets the value identical to the given room. The coordinates of the
     * otherRoom are ignored, assuming that the original coordinates of our room
     * are correct.
     * 
     * @param otherRoom the room to be set
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
    
    /**
     * opens the popup to modify a zone 
     * @param e contains the x and y location of the mouse where the popup needs to open
     */
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
        this.model.setColors(((ColorSequenceEditor) arg0.getSource()).getSequence());
    }

    public boolean hasDoor(int dir) {
        return this.model.hasDoor(dir);
    }
    
    public boolean canPlaceDoor(int dir) {
        return this.model.canPlaceDoor(dir);
    }
    
    public void setDoor(int dir, boolean value) {
        this.model.setDoor(dir, value);
    }
    
    public ZoneModel getZoneModel() {
        return model;
    }
    
    public void setZoneModel(ZoneModel model) {
        this.model = model;
    }
    
    /**
     * Randomizes the BlockColors in the rooms
     * @param amount the max amount of blocks per room
     * @param validcolors list of colors to randomize
     */
    public void randomizeColors(int amount, List<BlockColor> validcolors) {
        model.generateRandomBlocks(amount, validcolors);
        update();
    }
    
    /** update the map and preview */
    public void update() {
        uei.update();
    }

}
