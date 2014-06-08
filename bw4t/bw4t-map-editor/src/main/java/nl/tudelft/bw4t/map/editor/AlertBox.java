package nl.tudelft.bw4t.map.editor;

import javax.swing.JOptionPane;

/**
 * Simple box to show warnings to user.
 */
public class AlertBox {

    public static void alert(String string) {
        JOptionPane.showMessageDialog(MapEditor.frame, "Warning:" + string,
                "Click OK to continue", JOptionPane.WARNING_MESSAGE);
    }

}
