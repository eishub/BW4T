package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import eis.exceptions.EntityException;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

public class ComboAgentModel extends AbstractListModel implements ComboBoxModel {

    private final BW4TClientGUI gui;

    private static final String[] DEFAULT_OPTIONS = new String[] { "all" };
    private String selection = DEFAULT_OPTIONS[0];

    public ComboAgentModel(BW4TClientGUI clientGUI) {
        this.gui = clientGUI;
    }

    @Override
    public Object getElementAt(int listIndex) {
        int defaultOptionsSize = DEFAULT_OPTIONS.length;
        
        /** If the element is a default option, then return that option: */
        if (listIndex < defaultOptionsSize) {
            return DEFAULT_OPTIONS[listIndex];
        }
        
        /** Else return the agent in the dropdown menu list: */
        int agentIndex = listIndexToAgentIndex(listIndex);
        if (agentIndex >= 0 && showAgent(agentIndex)) {
            return gui.environment.getAgents().get(agentIndex);
        }
        
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
        if (name == null) {
            return false;
        }
        try {
            return !gui.environment.getType(name).equals("epartner");
        } catch (EntityException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Gets the agent index in the environment's agent list from the supplied
     * agent index in the list.
     * @param listIndex The index of the agent in the list.
     * @return The agent index, -1 if the agent was not in the list.
     */
    private int listIndexToAgentIndex(int listIndex) {
        listIndex -= DEFAULT_OPTIONS.length;
        
        /** A default option, which can't be translated to an agent id: */
        if (listIndex < 0) {
            return -1;
        }
        
        int currentListIndex = 0;
        for (int agentIndex = 0; agentIndex < gui.environment.getAgents().size(); agentIndex++) {
            if (showAgent(agentIndex) && currentListIndex++ >= listIndex) {
                return agentIndex;
            }
        }
        return -1;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (int i = 0; i < gui.environment.getAgents().size(); i++) {
            if (showAgent(i)) {
                size++;
            }
        }
        return size + DEFAULT_OPTIONS.length;
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
