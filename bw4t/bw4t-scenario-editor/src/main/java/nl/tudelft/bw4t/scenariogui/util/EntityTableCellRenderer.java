package nl.tudelft.bw4t.scenariogui.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;

/**
 * This class is used for changing the text color in the entityPanel tables. 
 */
public class EntityTableCellRenderer extends DefaultTableCellRenderer {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3827222514194681729L;

    /** (non-Javadoc)
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent
     * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
            boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (table.getName().equals(EntityPanel.getBotTableName())) {
            setAppropriateColorBotTableName(value, column, component);
        } else if (table.getName().equals(EntityPanel.getePartnerTableName())) { 
            setAppropriateColorEPartner(value, column, component);
        }
        return component;
    }

    /**
     * Sets the appropriate color bot table name.
     * 
     * @param value
     *            the value inside of the field
     * @param column
     *            the column
     * @param component
     *            the to be rendered component
     */
    private void setAppropriateColorBotTableName(Object value, int column, Component component) {
        if (column == 2 && !AgentFileChecker.fileNameExists(value.toString())) {
            component.setForeground(Color.RED);
        } else {
            component.setForeground(Color.BLACK);
        }
    }

    /**
     * Sets the appropriate color e partner.
     * 
     * @param value
     *            the value inside of the field
     * @param column
     *            the column
     * @param component
     *            the to be rendered component
     */
    private void setAppropriateColorEPartner(Object value, int column, Component component) {
        if (column == 1 && !AgentFileChecker.fileNameExists(value.toString())) {
            component.setForeground(Color.RED);
        } else {
            component.setForeground(Color.BLACK);
        }
    }
}
