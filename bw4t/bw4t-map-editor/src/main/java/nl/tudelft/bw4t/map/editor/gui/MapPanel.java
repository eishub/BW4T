package nl.tudelft.bw4t.map.editor.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.controller.UpdateableEditorInterface;

public class MapPanel extends JPanel implements UpdateableEditorInterface {
    private static final long serialVersionUID = -5921838296315289933L;

    private MapPanelController controller;

    private ZonePanel[][] zones;
    
    private ColorSequenceEditor dropSequence;

    public MapPanel(MapPanelController control) {
        assert control != null;
        this.controller = control;
        setupGrid();
    }

    private void setupGrid() {
        zones = new ZonePanel[controller.getRows()][controller.getColumns()];
        setLayout(new GridLayout(controller.getRows(), controller.getColumns()));

        for (int y = 0; y < controller.getColumns(); y++) {
            for (int x = 0; x < controller.getRows(); x++) {
                zones[x][y] = new ZonePanel(controller.getZoneController(x, y));
                this.add(zones[x][y]);
            }
        }
        
        dropSequence = new ColorSequenceEditor(MapPanelController.DROP_ZONE_SEQUENCE_LENGTH);
    }

    @Override
    public void update() {
        dropSequence.setSequence(controller.getSequence());
    }
}
