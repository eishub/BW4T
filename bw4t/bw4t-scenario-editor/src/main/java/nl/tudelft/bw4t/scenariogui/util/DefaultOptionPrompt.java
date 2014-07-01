package nl.tudelft.bw4t.scenariogui.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * This is the DefaultOptionPrompt, used in production when a confirmation dialog
 * has to be shown to the user. 
 */
public class DefaultOptionPrompt implements OptionPrompt {

    /**
     * @param parentComponent The <code>java.awt.Component</code> that will be used to align the box.
     * @param message         The message to be shown to the user.
     * @param title           The title of the confirmation dialog
     * @param optionType      The option type
     * @param messageType     The message type
     * @return The chosen option
     */
    public int showConfirmDialog(Component parentComponent, Object message, String title,
                                 int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);
    }

    /**
     * Call JOptionPane to show the message dialog.
     *
     * @param parentComponent The <code>java.awt.Component</code> that will be used to align the box.
     * @param message   The message to be displayed.
     */
    public void showMessageDialog(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message);
    }
}
