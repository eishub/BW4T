package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;

/**
 * This class implements the action listener for the Apply button in the
 * Randomize Sequence Frame.
 */
public class ApplyRandomSequence implements ActionListener {

    /** The frame where the button connected to the action listener is in. */
    private RandomizeSequenceFrame view;

    /** The controller for the frame. */
    private RandomizeSequenceController controller;

    /**
     * @param rf
     *            the frame the button is in
     * @param rc
     *            the controller of the frame
     */
    public ApplyRandomSequence(RandomizeSequenceFrame rf,
            RandomizeSequenceController rc) {
        this.view = rf;
        this.controller = rc;
    }

    /**
     * When the button is clicked the generated sequence is sent to the map
     * panel controller, and the view is closed.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        MapPanelController mapController = controller.getMapController();
        mapController.setSequence(controller.getRandomizeFromSettings()
                .getResult());
        UpdateableEditorInterface uei = mapController
                .getUpdateableEditorInterface();
        uei.update();
        view.dispose();
    }
}
