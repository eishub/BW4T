package nl.tudelft.bw4t.scenariogui.editor.controller;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event when a cell in the bot table is edited.
 * <p>
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
        if (event.getColumn() == -1) 
            return;
        model.getAmountBot();
        BotConfig config = model.getBots().get(event.getFirstRow());
        String value =  "" + view.getEntityPanel().getBotTable().getValueAt(
                event.getFirstRow(), event.getColumn());
        switch (event.getColumn()) {
        case 0:
            config.setBotName(value);
            break;
        case 1:
            EntityType botController = EntityType.getType(value);

            config.setBotController(botController);
            break;
        case 2:
            config.setBotAmount(Integer.parseInt(value));
            break;
         default:
            break;
        }
    }
    
}
