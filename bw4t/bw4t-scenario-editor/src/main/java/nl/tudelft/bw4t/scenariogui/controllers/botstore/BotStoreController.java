package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;

/**
 * The BotStoreController class is in charge of all events that happen on the BotStoreGUI. It delegates all events
 * to classes implementing ActionListener, sending the view along as an argument.
 *
 * @author Arun
 */
public class BotStoreController {

    /**
     * the view being controlled
     */
    private BotEditor view;

    /**
     * Create the BotStore controllers
     *
     * @param view The parent view, used to call relevant functions by the event listeners
     */
    public BotStoreController(BotEditor view) {
        this.view = view;

        view.getBotEditorPanel().getResetButton().addActionListener(
                new ResetButton(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getApplyButton().addActionListener(
                new ApplyButton(getMainView().getBotEditorPanel()));

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
    }

    public BotEditor getMainView() {
        return view;
    }
}

