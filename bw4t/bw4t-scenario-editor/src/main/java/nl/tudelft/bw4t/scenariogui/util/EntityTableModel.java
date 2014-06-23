package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.map.EntityType;

/**
 * The table model for the entity panel.
 */
public class EntityTableModel extends DefaultTableModel {

    private static final long serialVersionUID = -662539737379069835L;
    
    private EntityType type;
    
    /**
     * Constructor.
     * @param type The EntityTypes that should be in the table.
     */
    public EntityTableModel(EntityType type) {
        this.type = type;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (type.equals(EntityType.EPARTNER)) {
            if (column == 2) {
                return Integer.class;
            }
        } else {
            if (column == 3) {
                return Integer.class;
            }
        }
        return String.class;
    }
}
