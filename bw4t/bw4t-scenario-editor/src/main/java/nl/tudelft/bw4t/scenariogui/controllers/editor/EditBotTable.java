package nl.tudelft.bw4t.scenariogui.controllers.editor;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.config.BotConfig;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event when a cell in the bot table is edited.
 * <p>
 * @author      Nick Feddes
 * @version     0.1                
 * @since       27-05-2014        
 */
public class EditBotTable implements TableModelListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;

    /**
     * Create a EditBotTable event handler.
     *
     * @param newView The parent view.
     */
    public EditBotTable(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Gets called when a cell in the table was changed.
     * Updates the config object with the new data.
     * @param arg0 The event.
     */
    @Override
    public void tableChanged(TableModelEvent arg0) {
        if (arg0.getColumn() == -1)
            return;
        BotConfig config = view.getEntityPanel().getBotConfigs().get(arg0.getFirstRow());
        String value = (String) view.getEntityPanel().getBotTable().getValueAt(
                arg0.getFirstRow(), arg0.getColumn());
        switch (arg0.getColumn()) {
        case 0:
            config.setBotName(value);
            break;
        case 1:
            config.setBotController(value);
            break;
        case 2:
            config.setBotAmount(value);
            break;
         default:
            break;
        }
    }
    
}
