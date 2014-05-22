package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * Created by on 21-5-2014.
 */
public class YesMockOptionPrompt implements OptionPrompt {

    /**
     *  Returns the JOptionPane.YES_OPTION
     *  @see javax.swing.JOptionPane#showConfirmDialog(java.awt.Component, Object, String, int, int);
     */
    @Override
    public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
        return JOptionPane.YES_OPTION;
    }
}