package nl.tudelft.bw4t.environmentstore.editor.controller;

import java.awt.Component;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.editor.view.RoomMenu;
import nl.tudelft.bw4t.environmentstore.editor.view.ZoneMenu;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * This holds the map that the user designed. This is an abstract map contianing only number of rows and columns, do not
 * confuse with {@link NewMap}.
 */
public class MapPanelController implements ChangeListener {

    /** basic size of the map */
    private ZoneController[][] zonecontrollers;

    
    private boolean startzone;
	private boolean dropzone;
	
    private EnvironmentMap model;

    private ColorSequenceController cscontroller;

    private ZoneMenuController zmenucontroller;
    private ZoneController selected = null;

    private UpdateableEditorInterface uei;

    public MapPanelController(EnvironmentMap map) {
        cscontroller = new ColorSequenceController();
        zmenucontroller = new ZoneMenuController();

        this.setModel(map);
    }

    /**
     * size of map is fixed, you can't change it after construction.
     * 
     * @param rows
     * @param columns
     * @param isLabelsVisible
     *            true if labels should be shown by renderers
     */
    public MapPanelController(int rows, int columns) {
        this(new EnvironmentMap(rows, columns));
    }

    /**
     * The createZone method creates a Zone and updates the UpdateableEditorInterface.
     * 
     * @param t
     *            is the type of Zone that we would like to create.
     * @param isDropZone
     *            is true if the Zone that we are creating is a dropZone.
     * @param isStartZone
     *            is true if the Zone that we are creating is a startZone.
     */
    public void createZone(Type t, boolean isDropZone, boolean isStartZone) {
        if (selected != null) {
        	if (isStartZone && hasStartzone() && !selected.isStartZone()){
        		EnvironmentStore.showDialog("Only one start zone can be added to the map.");
        	} else if (isDropZone && hasDropzone() && !selected.isDropZone()) {
        		EnvironmentStore.showDialog("Only one drop zone can be added to the map.");
        	} else {
        		if (selected.isStartZone() && hasStartzone()) {
        			setStartzone(isStartZone);
        		}
        		if (selected.isDropZone() && hasDropzone()) {
        			setDropzone(isDropZone);
        		}
        		selected.setType(t);
                selected.setDropZone(isDropZone);
                if (isDropZone) {
                	setDropzone(isDropZone);
                }
                selected.setStartZone(isStartZone);
                if (isStartZone) {
                	setStartzone(isStartZone);
                }
                selected.getUpdateableEditorInterface().update();
        	}
        }
        selected = null;
    }

    public EnvironmentMap getModel() {
        return model;
    }

    public void setModel(EnvironmentMap model) {
        this.model = model;
        connectZoneControllers();
    }

    public int getRows() {
        return model.getRows();
    }

    public int getColumns() {
        return model.getColumns();
    }

    public List<BlockColor> getSequence() {
        return getModel().getSequence();
    }

    public void setSequence(List<BlockColor> colorSequence) {
        getModel().setSequence(colorSequence);
    }

    public ZoneController[][] getZoneControllers() {
        return zonecontrollers;
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

    public EnvironmentMap getEnvironmentMap() {
        return getModel();
    }

    /**
     * get the number of entities on the map. *
     * 
     * @return the number of entities in the map.
     */
    public int getNumberOfEntities() {
        return getModel().getSpawnCount();
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

    public ZoneMenu getZoneMenu() {
        if (selected.getType() == Type.ROOM) {
            RoomMenu menu = new RoomMenu(this);
            zmenucontroller.attachListenersToRoomMenu(menu, this);
            return menu;
        }
        else {
            ZoneMenu menu = new ZoneMenu(this);
            zmenucontroller.attachListenersToZoneMenu(menu, this);
            return menu;
        }
    }

    private void connectZoneControllers() {
        final ZoneController[][] original = getZoneControllers();
        zonecontrollers = new ZoneController[getRows()][getColumns()];

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                final ZoneModel zone = getModel().getZone(row, col);
                if (original != null && row < original.length && col < original[row].length) {
                    final ZoneController zoneController = original[row][col];
                    zoneController.setZoneModel(zone);
                    zonecontrollers[row][col] = zoneController;
                    zonecontrollers[row][col].update();
                }
                else {
                    zonecontrollers[row][col] = new ZoneController(this, zone);
                }
            }
        }
        final UpdateableEditorInterface uei = this.getUpdateableEditorInterface();
        if (uei != null) {
            uei.update();
        }
    }

    public ZoneController getZoneController(int row, int col) {
        if (getModel().isValidZone(row, col)) {
            return zonecontrollers[row][col];
        }
        return null;
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
        this.setSequence(((ColorSequenceEditor) e.getSource()).getSequence());

    }

    public void randomizeColorsInRooms(List<BlockColor> colors, int amount) {
        getModel().generateRandomBlocks(colors, amount);
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                zonecontrollers[row][col].update();
            }
        }
    }
    /**
   	 * @return the startzone
   	 */
   	public boolean hasStartzone() {
   		return startzone;
   	}

   	/**
   	 * @param startzone the startzone to set
   	 */
   	public void setStartzone(boolean startzone) {
   		this.startzone = startzone;
   	}

   	/**
   	 * @return the dropzone
   	 */
   	public boolean hasDropzone() {
   		return dropzone;
   	}

   	/**
   	 * @param dropzone the dropzone to set
   	 */
   	public void setDropzone(boolean dropzone) {
   		this.dropzone = dropzone;
   	}


}
