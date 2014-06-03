package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.table.DefaultTableModel;

/**
 * @author Katia Asmoredjo
 * @author Calvin Wong Loi Sing
 *
 * The table model for the entity panel.
 */
public class EntityTableModel extends DefaultTableModel {

    /** Randomly generated serial version. */
    private static final long serialVersionUID = -662539737379069835L;

    /**
     * The column class for the tables in the entity panel
     * @param column The column
     * @return The column class
     */
    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 1) {
            return Integer.class;
        }

        return String.class;
    }
}
