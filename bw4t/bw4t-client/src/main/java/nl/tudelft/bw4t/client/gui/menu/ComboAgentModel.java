package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

public class ComboAgentModel extends AbstractListModel implements ComboBoxModel {

    private final BW4TClientGUI gui;
    private String selection = "";

    public ComboAgentModel(BW4TClientGUI clientGUI) {
        this.gui = clientGUI;
    }

    @Override
    public Object getElementAt(int arg0) {
        return gui.environment.getAgents().get(arg0);
    }

    @Override
    public int getSize() {
        return gui.environment.getAgents().size();
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
