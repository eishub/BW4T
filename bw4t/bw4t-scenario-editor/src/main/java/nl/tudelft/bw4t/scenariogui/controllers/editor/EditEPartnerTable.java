package nl.tudelft.bw4t.scenariogui.controllers.editor;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event when a cell in the bot table is edited.
 * <p>
 * @author      Nick Feddes
 * @version     0.1                
 * @since       27-05-2014        
 */
public class EditEPartnerTable implements TableModelListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;

    /**
     * Create a EditEPartnerTable event handler.
     *
     * @param newView The parent view.
     */
    public EditEPartnerTable(final MainPanel newView) {
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
        // TODO: once the EPartnerConfig has been made, uncomment (select it, then ctrl + 7) below stuff
        System.out.println("TODO: once the EPartnerConfig has been made, uncomment below stuff");
//        EPartnerConfig config = view.getEntityPanel().getEPartnerConfigs().get(event.getFirstRow());
//        String value = (String) view.getEntityPanel().getEPartnerTable().getValueAt(
//                event.getFirstRow(), event.getColumn());
//        switch (event.getColumn()) {
//        case 0:
//            config.setEPartnerName(value);
//            break;
//        case 1:
//            config.setEPartnerAmount(value);
//            break;
//         default:
//            break;
//        }
    }
    
}
