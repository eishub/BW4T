package nl.tudelft.bw4t.scenariogui.controllers.editor;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.BotConfig;
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
     * @param event The event.
     */
    @Override
    public void tableChanged(TableModelEvent event) {
        if (event.getColumn() == -1)
            return;
        BotConfig config = view.getEntityPanel().getBotConfigs().get(event.getFirstRow());
        String value = (String) view.getEntityPanel().getBotTable().getValueAt(
                event.getFirstRow(), event.getColumn());
        switch (event.getColumn()) {
            case 0:
                config.setBotName(value);
                break;
            case 1:
                config.setBotController(value);
                break;
            case 2:
                config.setBotAmount(Integer.parseInt(value));
                break;
            default:
                break;
        }
    }

}
