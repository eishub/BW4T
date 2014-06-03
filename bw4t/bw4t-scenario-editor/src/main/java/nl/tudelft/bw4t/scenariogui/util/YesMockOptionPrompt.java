package nl.tudelft.bw4t.scenariogui.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * The OptionPrompt that always returns yes, used when mocking the
 * objects during tests, to prevent the system from hanging due
 * to having to press a button on the prompt.
 * <p>
 * @author      Calvin Wong Loi Sing
 * @version     0.1                
 * @since       12-05-2014        
 */
public class YesMockOptionPrompt implements OptionPrompt {

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
        return JOptionPane.YES_OPTION;
    }

    /**
     * This class will not show any message dialog.
     *
     * @param parentComponent The <code>java.awt.Component</code> that will be used to align the box.
     * @param message The message to be displayed.
     */
    public void showMessageDialog(Component parentComponent, Object message) {
        return;
    }
}
