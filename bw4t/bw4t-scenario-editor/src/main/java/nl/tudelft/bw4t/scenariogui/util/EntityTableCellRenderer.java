package nl.tudelft.bw4t.scenariogui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;

/**
 * @since 10-06-2014
 * This class is used for changing the text color in the entityPanel tables. 
 */

public class EntityTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 3827222514194681729L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (table.getName().equals(EntityPanel.getBotTableName())) {
            if (column == 2 && !fileNameExists(value.toString())) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
        } else if (table.getName().equals(EntityPanel.getePartnerTableName())) { 
            if (column == 1 && !fileNameExists(value.toString())) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
        }
        return c;
    }
    
    /**
     * Returns whether a file exists
     * @return Returns whether a file exists.
     */
    public boolean fileNameExists(String filename){
        File f = new File(filename);
        return f.exists();
    }
}
