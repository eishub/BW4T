package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

/** Used to store the current selection */
public class ComboAgentModel extends AbstractListModel implements ComboBoxModel {

	/** Stores the {@link BW4TClientGUI} to perform operations on. */
    private final BW4TClientGUI gui;
    /** Stores currently selected item. */
    private String selection = "";

    /**
     * @param clientGUI
     *            - The {@link BW4TClientGUI} to perform operations on.
     */
    public ComboAgentModel(BW4TClientGUI clientGUI) {
        this.gui = clientGUI;
    }

    @Override
    public Object getElementAt(int arg0) {
        return gui.getController().getEnvironment().getAgents().get(arg0);
    }

    @Override
    public int getSize() {
        return gui.getController().getEnvironment().getAgents().size();
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public void setSelectedItem(Object arg0) {
        selection = (String) arg0;
    }

}
