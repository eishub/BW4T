package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.controller.UpdateableEditorInterface;

/**
 * The mapPanel class contains the map that is being edited in a grid.
 *
 */
public class MapPanel extends JPanel implements UpdateableEditorInterface {
	
    private static final long serialVersionUID = -5921838296315289933L;

    private MapPanelController mapController;

    private ZonePanel[][] zones;
    
    private JPanel mapGrid = new JPanel();
    
    private ColorSequenceEditor dropSequence;

    /**
     * Constructor sets the controller and calls the setupGrid method.
     * @param control is the MapPanelController that is being used.
     */
    public MapPanel(MapPanelController control) {
        assert control != null;
        this.mapController = control;
        
        this.mapController.setUpdateableEditorInterface(this);
        
        setupGrid();
    }

    /**
     * Setup the grid that we are going to use, based on information from the controller.
     */
    private void setupGrid() {
        this.setLayout(new BorderLayout());
        zones = new ZonePanel[mapController.getRows()][mapController.getColumns()];
        mapGrid.setLayout(new GridLayout(mapController.getRows(), mapController.getColumns()));

        for (int row = 0; row < mapController.getRows(); row++) {
            for (int col = 0; col < mapController.getColumns(); col++) {
                zones[row][col] = new ZonePanel(mapController.getZoneController(row, col));
                mapGrid.add(zones[row][col]);
            }
        }
        this.add(new JScrollPane(mapGrid), BorderLayout.CENTER);
        
        dropSequence = new ColorSequenceEditor(MapPanelController.DROP_ZONE_SEQUENCE_LENGTH);
        dropSequence.addChangeListener(mapController);
        mapController.getCSController().addColorSequenceEditor(dropSequence);
        this.add(dropSequence, BorderLayout.SOUTH);
        update();
    }

    @Override
    public void update() {
        dropSequence.setSequence(mapController.getSequence());
    }
}
