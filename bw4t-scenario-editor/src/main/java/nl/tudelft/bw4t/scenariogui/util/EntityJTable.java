package nl.tudelft.bw4t.scenariogui.util;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;

public class EntityJTable extends JTable {
    
    private static final long serialVersionUID = 6368045182363427342L;

    private EntityTableCellRenderer cellRenderer;
    
    /**
     * Constructor.
     */
    public EntityJTable() {
        cellRenderer = new EntityTableCellRenderer();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override 
    public TableCellRenderer getCellRenderer(int row, int col) {
        return cellRenderer;
    }
  
    /**
     * Returns the tooltip at a specific point in a mouse event.
     * 
     * @param e The mouse event.
     * @return Returns the warning showed when a agent file doesn't exist.
     */
    public String getToolTipText(MouseEvent e) {
        
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        
        return getToolTipText(rowIndex, colIndex);        
    }
    
    /**
     * Returns the tooltip text of a specific cell.
     * 
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The text of the tooltip.
     */
    public String getToolTipText(int row, int col) {
        String warning = null;
        
        String value =  getValueAt(row, col).toString();
        if (row >= 0 && col == getAgentFileColumn() && !AgentFileChecker.fileNameExists(value)) {
            warning = "Agent file does not exist.";
        }
        return warning;
    }
    
    public int getAgentFileColumn() {
        String name = "";
        try {
            name = this.getName();
    
            if (name.equals(EntityPanel.getBotTableName())) {
                return 2;
            } else if (name.equals(EntityPanel.getePartnerTableName())) {
                return 1;
            }
            IndexOutOfBoundsException e = new IndexOutOfBoundsException("Error: No agent file column found.");
            ScenarioEditor.showDialog(e, "Check whether columns are updated");
        } catch (NullPointerException e) {
            ScenarioEditor.showDialog(e, "Error: Table has no name. Cannot find agent file column");
        } 
        return -1;        
    }        
    
}
