package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

public class NoMockOptionPrompt implements OptionPrompt {

    /**
     *  Returns the JOptionPane.NO_OPTION
     *  @see javax.swing.JOptionPane#showConfirmDialog(java.awt.Component, Object, String, int, int);
     */
    @Override
    public int showConfirmDialog(Component parentComponent, Object message, String title,
                                 int optionType, int messageType) {
        return JOptionPane.NO_OPTION;
    }
}
