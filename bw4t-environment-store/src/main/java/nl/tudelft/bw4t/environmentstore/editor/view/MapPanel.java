package nl.tudelft.bw4t.environmentstore.editor.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nl.tudelft.bw4t.environmentstore.editor.controller.ColorSequenceEditor;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;

/** The mapPanel class contains the map that is being edited in a grid. */
public class MapPanel extends JPanel implements UpdateableEditorInterface {
    
    /** Random generated serial version UID. */
    private static final long serialVersionUID = -5921838296315289933L;

    /** The controller for this view class. */
    private MapPanelController mapController;

    /** The panels that make up the grid. */
    private ZonePanel[][] zones;
    
    /** The grid we use. */
    private JPanel mapGrid = new JPanel();
    
    /** The editor for the sequence bots will need to pick up. */
    private ColorSequenceEditor dropSequence;
    
    /** The layout for the grid. */
    private GridLayout layout;

    /**
     * Constructor sets the controller and calls the setupGrid method.
     * @param control is the MapPanelController that is being used.
     */
    public MapPanel(MapPanelController control) {
        assert control != null;
        this.mapController = control;
        
        this.mapController.setUpdateableEditorInterface(this);
        
        setupLayout();
    }

    /** Setup the grid layout that we are going to use, based on information from the controller. */
    private void setupLayout() {
        this.setLayout(new BorderLayout());
        setupGrid();
        this.add(new JScrollPane(mapGrid), BorderLayout.CENTER);
        
        dropSequence = new ColorSequenceEditor(EnvironmentMap.DROP_ZONE_SEQUENCE_LENGTH);
        dropSequence.addChangeListener(mapController);
        mapController.getCSController().addColorSequenceEditor(dropSequence);
        this.add(dropSequence, BorderLayout.SOUTH);
        update();
    }
    
    /** Setup the grid that we are going to use, based on information from the controller. */
    public void setupGrid() {
        zones = new ZonePanel[mapController.getRows()][mapController.getColumns()];
        mapGrid.removeAll();
        layout = new GridLayout(mapController.getRows(), mapController.getColumns());
        mapGrid.setLayout(layout);

        for (int row = 0; row < mapController.getRows(); row++) {
            for (int col = 0; col < mapController.getColumns(); col++) {
                zones[row][col] = new ZonePanel(mapController.getZoneController(row, col));
                mapGrid.add(zones[row][col]);
            }
        }
    }

    @Override
    public void update() {
        dropSequence.setSequence(mapController.getSequence());
        
        if(layout.getRows() != mapController.getRows() || layout.getColumns() != mapController.getColumns()) {
            this.setupGrid();
        }
    }
}
