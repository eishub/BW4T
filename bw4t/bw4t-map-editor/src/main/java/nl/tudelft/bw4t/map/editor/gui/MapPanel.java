package nl.tudelft.bw4t.map.editor.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import nl.tudelft.bw4t.map.editor.controller.MapController;


public class MapPanel extends JPanel {
    private static final long serialVersionUID = -5921838296315289933L;
    
    private MapController controller;
    
    private ZonePanel[][] zones;
    
    public MapPanel(MapController control) {
        assert control != null;
        this.controller = control;
        setupGrid();
    }
    
    private void setupGrid() {
        zones = new ZonePanel[controller.getRows()][controller.getColumns()];
        setLayout(new GridLayout(controller.getRows(), controller.getColumns()));
        
    }
}
