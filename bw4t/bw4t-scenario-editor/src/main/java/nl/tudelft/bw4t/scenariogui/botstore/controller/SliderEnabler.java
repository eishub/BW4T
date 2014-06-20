package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Updates the sliders in the view when the action was performed.
 */
public class SliderEnabler implements ActionListener {

    private BotEditorPanel view;

    /**
     * Constructor
     * 
     * @param pview
     *            The BotEditorPanel that contains the button and the required sliders.
     */
    public SliderEnabler(BotEditorPanel pview) {
        this.view = pview;
    }
    
    protected BotEditorPanel getView() {
        return view;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        view.updateSliders();
    }
}
