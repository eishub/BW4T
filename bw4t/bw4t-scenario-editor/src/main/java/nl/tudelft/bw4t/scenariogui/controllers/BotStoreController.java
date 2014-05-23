package nl.tudelft.bw4t.scenariogui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

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

        view.getBotEditorPanel().getCancelButton().addActionListener(
                new CancelButton(getMainView().getBotEditorPanel()));

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
    }

    public BotEditor getMainView() {
        return view;
    }
}

/**
 * Handles actions of the resetbutton
 */
class ResetButton implements ActionListener {
    private BotEditorPanel view;

    public ResetButton(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        view.resetAction();
    }
}

/**
 * Handles actions of the cancelbutton
 */
class CancelButton implements ActionListener {
    private BotEditorPanel view;

    public CancelButton(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        view.cancelAction();
    }
}

/**
 * Handles actions of the applybutton
 */
class ApplyButton implements ActionListener {
    private BotEditorPanel view;

    public ApplyButton(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        view.applyAction();
    }
}

/**
 * Handles actions of the speedslider
 */
class SpeedSlider implements MouseListener {
    private BotEditorPanel view;

    public SpeedSlider(BotEditorPanel view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        view.calculateBatteryUse();

    }

}

/**
 * Handles actions of the sizeslider
 */
class SizeSlider implements MouseListener {
    private BotEditorPanel view;

    public SizeSlider(BotEditorPanel view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        view.calculateBatteryUse();

    }

}

/**
 * Handles actions of the batteryslider
 */
class BatterySlider implements MouseListener {
    private BotEditorPanel view;

    public BatterySlider(BotEditorPanel view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        view.calculateBatteryUse();

    }
}

/**
 * Handles actions of the gripperbox
 */
class GripperBox implements ActionListener {
    private BotEditorPanel view;

    public GripperBox(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {

    }
}

/**
 * Handles actions of the colorblindcheckbox
 */
class ColorBox implements ActionListener {
    private BotEditorPanel view;

    public ColorBox(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {

    }
}

/**
 * Handles actions of the walkingcheckbox
 */
class WalkingBox implements ActionListener {
    private BotEditorPanel view;

    public WalkingBox(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {

    }
}

/**
 * Handles actions of the jumpingcheckbox
 */
class JumpBox implements ActionListener {
    private BotEditorPanel view;

    public JumpBox(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {

    }
}