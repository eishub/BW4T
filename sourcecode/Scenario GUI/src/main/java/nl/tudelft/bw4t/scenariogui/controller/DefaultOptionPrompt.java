package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

public class DefaultOptionPrompt implements OptionPrompt  {

    /**
     *  @see javax.swing.JOptionPane#showConfirmDialog(java.awt.Component, Object, String, int, int);
     */
    public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);
    }
}
