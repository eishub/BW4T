package nl.tudelft.bw4t.scenariogui.botstore.controller;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;

/**
 * The BotStoreController class is in charge of all events that happen on the BotStoreGUI. It delegates all events
 * to classes implementing ActionListener, sending the view along as an argument.
 */
public class BotStoreController {

    /**
     * the view being controlled
     */
    private BotEditor view;

    /**
     * Create the BotStore controllers
     *
     * @param pview The parent view, used to call relevant functions by the event listeners
     */
    public BotStoreController(BotEditor pview) {
        this.view = pview;

        view.getBotEditorPanel().getResetButton().addActionListener(
                new ResetButton(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getSaveButton().addActionListener(
                new SaveButton(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getSpeedSlider().addMouseListener(
                new SpeedSlider(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getSizeSlider().addMouseListener(
                new SizeSlider(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getBatterySlider().addMouseListener(
                new BatterySlider(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getGripperCheckbox().addActionListener(
                new GripperBox(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getColorblindCheckbox().addActionListener(
                new ColorBox(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getsizeoverloadCheckbox().addActionListener(
                new WalkingBox(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getmovespeedCheckbox().addActionListener(
                new JumpBox(getMainView().getBotEditorPanel()));
        
        view.getBotEditorPanel().getCancelButton().addActionListener(
                new CancelButton(getMainView()));
        
        view.getBotEditorPanel().getBatteryEnabledCheckbox().addActionListener(
                new BatteryBox(getMainView().getBotEditorPanel()));
        
        view.getBotEditorPanel().getmovespeedCheckbox().addActionListener(
                new SpeedBox(getMainView().getBotEditorPanel()));
        
        view.getBotEditorPanel().getFileButton().addActionListener(
        		new GoalFileButton(getMainView().getBotEditorPanel()));
    }
    
    /**
     * Return the BotEditor-object passed in the constructor.
     * @return the object passed in the constructor.
     */
    public BotEditor getMainView() {
        return view;
    }
}

