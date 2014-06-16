package nl.tudelft.bw4t.client.gui.menu;

import java.util.Collection;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import eis.exceptions.EntityException;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.network.BW4TServerHiddenActions;
import nl.tudelft.bw4t.network.BW4TServerActions;
import nl.tudelft.bw4t.server.BW4TServer;

public class ComboAgentModel extends AbstractListModel implements ComboBoxModel {

    private final BW4TClientGUI gui;
    //private final BW4TServer server;
    private String selection = "";

    public ComboAgentModel(BW4TClientGUI clientGUI/*, BW4TServer server*/) {
        this.gui = clientGUI;
        //this.server = server;
    }

    @Override
    public Object getElementAt(int agentIndex) {
        if (showAgent(agentIndex))
            return gui.environment.getAgents().get(agentIndex);
        return null;
    }
    
    /**
     * Returns whether this agent is not an e-partner and is not our
     * own agent, and thus can be shown in the list.
     * @param agentIndex The index of the agent in the environment.
     * @return Whether this agent should be shown.
     */
    private boolean showAgent(int agentIndex) {
        String name = gui.environment.getAgents().get(agentIndex);
        boolean ourBot = gui.getController().getMapController().getTheBot().getName().equals(name);
        if (ourBot || name == null)
            return false;
        try {
            if (gui.environment.getType(gui.environment.getAgents().get(agentIndex)).equals("epartner")) {
                return false;
            } else {
                return true;
            }
        } catch (EntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (int i = 0; i < gui.environment.getAgents().size(); i++)
            if (showAgent(i))
                size++;
        return size;
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
