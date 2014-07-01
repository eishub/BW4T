package nl.tudelft.bw4t.scenariogui.editor.controller;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event when a cell in the bot table is edited.
 * 
 * @version     0.1                
 * @since       27-05-2014        
 */
public class EditBotTable implements TableModelListener {

    private MainPanel view;
    
    private BW4TClientConfig model;

    /**
     * Create a EditBotTable event handler.
     *
     * @param newView The parent view.
     * @param model The model.
     */
    public EditBotTable(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }

    /**
     * Gets called when a cell in the table was changed.
     * Updates the config object with the new data.
     * @param event The event.
     */
    @Override
    public void tableChanged(TableModelEvent event) {
        if (event.getColumn() == -1) {
            return;
        }
        
        BotConfig config = model.getBots().get(event.getFirstRow());
        String value =  "" + view.getEntityPanel().getBotTable().getValueAt(
                event.getFirstRow(), event.getColumn());

        updateConfigFieldCorrespondingToTableColumn(event.getColumn(), config, value);
    }
    
    /**
     * Updates a field in the {@link BotConfig} object that corresponds to
     * the table column with the value specified.
     * @param tableColumn The index of the column in the table.
     * @param config The {@link BotConfig} object to update.
     * @param newValue The new value for the field in the config.
     */
    private void updateConfigFieldCorrespondingToTableColumn(int tableColumn, BotConfig config, String newValue) {
        switch (tableColumn) {
        case 0:
            config.setBotName(newValue);
            break;
        case 1:
            EntityType botController = EntityType.getType(newValue);
            config.setBotController(botController);
            break;
        case 2:
           config.setFileName(newValue);
            break;
        case 3:
            config.setBotAmount(Integer.parseInt(newValue));
            break;
         default:
            break;
        }
    }   
}
