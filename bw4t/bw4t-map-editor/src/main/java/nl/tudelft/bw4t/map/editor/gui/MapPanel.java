package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.controller.UpdateableEditorInterface;

public class MapPanel extends JPanel implements UpdateableEditorInterface {
    private static final long serialVersionUID = -5921838296315289933L;

    private MapPanelController controller;

    private ZonePanel[][] zones;
    
    private JPanel mapGrid = new JPanel();
    
    private ColorSequenceEditor dropSequence;

    public MapPanel(MapPanelController control) {
        assert control != null;
        this.controller = control;
        
        this.controller.setUpdateableEditorInterface(this);
        
        setupGrid();
    }

    private void setupGrid() {
        this.setLayout(new BorderLayout());
        zones = new ZonePanel[controller.getRows()][controller.getColumns()];
        mapGrid.setLayout(new GridLayout(controller.getRows(), controller.getColumns()));

        for (int x = 0; x < controller.getRows(); x++) {
            for (int y = 0; y < controller.getColumns(); y++) {
                zones[x][y] = new ZonePanel(controller.getZoneController(x, y));
                mapGrid.add(zones[x][y]);
            }
        }
        this.add(new JScrollPane(mapGrid), BorderLayout.CENTER);
        
        dropSequence = new ColorSequenceEditor(MapPanelController.DROP_ZONE_SEQUENCE_LENGTH);
        controller.getCSController().addColorSequenceEditor(dropSequence);
        this.add(dropSequence, BorderLayout.SOUTH);
        update();
    }

    @Override
    public void update() {
        dropSequence.setSequence(controller.getSequence());
    }
}
