package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;

/**
 * EpartnerController is in charge of all events that happen on the EpartnerGUI.
 * 
 * @author Wendy
 */

public class EpartnerController {

    /**
     * The view being controlled
     */
    private EpartnerFrame view;

    /**
     * Create the Epartner controller
     *
     * @param pview The parent view, used to call relevant functions by the event listeners
     */
    public EpartnerController(EpartnerFrame pview) {
        this.view = pview;
        
        view.getResetButton().addActionListener(
                new ResetButton(getMainView()));

        view.getCancelButton().addActionListener(
                new CancelButton(getMainView()));

        view.getApplyButton().addActionListener(
                new ApplyButton(getMainView()));

        view.getLeftAloneCheckbox().addActionListener(
                new LeftAloneCheckBox(getMainView()));

        view.getGPSCheckbox().addActionListener(
                new gpsCheckBox(getMainView()));
    }

    /**
     * @return view
     */
    public EpartnerFrame getMainView() {
        return view;
    }
}

/**
 * Handles actions of the ResetButton
 */
class ResetButton implements ActionListener {

    private EpartnerFrame view;
    
    public ResetButton(EpartnerFrame pview) {
        this.view = pview;
    }

    public void actionPerformed(ActionEvent ae) {
        view.resetAction();
    }
}

/**
 * Handles actions of the CancelButton
 */
class CancelButton implements ActionListener {
    private EpartnerFrame view;

    public CancelButton(EpartnerFrame pview) {
        this.view = pview;
    }

    public void actionPerformed(ActionEvent ae) {
        view.cancelAction();
    }
}

/**
 * Handles actions of the ApplyButton
 */
class ApplyButton implements ActionListener {
    private EpartnerFrame view;

    public ApplyButton(EpartnerFrame pview) {
        this.view = pview;
    }

    public void actionPerformed(ActionEvent ae) {
        view.applyAction();
    }
}

/**
 * Handles actions of the LeftAloneCheckBox
 */
class LeftAloneCheckBox implements ActionListener {
    private EpartnerFrame view;

    public LeftAloneCheckBox(EpartnerFrame view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {

    }
}

/**
 * Handles actions of the GPSCheckBox
 */
class gpsCheckBox implements ActionListener {
    private EpartnerFrame view;

    public gpsCheckBox(EpartnerFrame pview) {
        this.view = pview;
    }

    public void actionPerformed(ActionEvent ae) {

    }
}
