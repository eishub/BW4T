package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * An interface for showConfirmDialog, used to make the mocking of responses
 * simpler.
 */
public interface OptionPrompt {
    /**
     *  @see javax.swing.JOptionPane#showConfirmDialog(java.awt.Component, Object, String, int, int);
     */
    int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType);
}




