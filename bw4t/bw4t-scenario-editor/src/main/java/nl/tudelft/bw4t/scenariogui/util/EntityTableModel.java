package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.agent.EntityType;

/**
 * The table model for the entity panel.
 */
public class EntityTableModel extends DefaultTableModel {

    private static final long serialVersionUID = -662539737379069835L;
    
    private EntityType type;
    
    public EntityTableModel(EntityType type) {
    	this.type = type;
    }

    /**
     * The column class for the tables in the entity panel
     * @param column The column
     * @return The column class
     */
    @Override
    public Class<?> getColumnClass(int column) {
    	if (type.equals(EntityType.EPARTNER)) {
    		if (column == 1) {
    			return Integer.class;
    		}
    	} else {
    		if (column == 2) {
    			return Integer.class;
    		}
    	}
        return String.class;
    }
}
