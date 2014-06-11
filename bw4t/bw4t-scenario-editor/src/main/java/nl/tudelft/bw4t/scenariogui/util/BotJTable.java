package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class BotJTable extends JTable {
    
    /**
     * BotJTable extends JTable in order to use a custom cell renderer.
     */
    private static final long serialVersionUID = 6368045182363427342L;

    private EntityTableCellRenderer cellRenderer;
    

    public BotJTable() {
        cellRenderer = new EntityTableCellRenderer();
    }

    @Override 
    public TableCellRenderer getCellRenderer(int row, int col) {
        return cellRenderer;
    }
    
    
}
