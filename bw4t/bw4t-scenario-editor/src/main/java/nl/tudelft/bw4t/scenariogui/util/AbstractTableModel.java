package nl.tudelft.bw4t.scenariogui.util;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

/**
 * This class creates a table which can hold entities.
 */
public abstract class AbstractTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();
    
    private ScenarioEditor parent = null;
    
    /** Prevents this class from being instantiated. */
    public AbstractTableModel() {}
    
    protected BW4TClientConfig getConfig() {
        if(parent == null || parent.getController() == null)
            return null;
        return parent.getController().getModel();
    }
    
    public void setEnvironmentStore(ScenarioEditor parent) {
        this.parent = parent;
    }
    
    /**
     * Update all attached listeners.
     */
    public void update() {
        final TableModelEvent tableModelEvent = new TableModelEvent(this);
        for (TableModelListener listener : listeners) {
            listener.tableChanged(tableModelEvent);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

}
