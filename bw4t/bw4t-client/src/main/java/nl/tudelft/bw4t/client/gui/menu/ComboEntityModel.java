package nl.tudelft.bw4t.client.gui.menu;

import eis.exceptions.EntityException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

public class ComboEntityModel extends AbstractListModel implements ComboBoxModel {
    private static final long serialVersionUID = 344469588673323347L;

    private final BW4TClientGUI gui;

    private List<String> entities;

    private static final String[] DEFAULT_OPTIONS = new String[] { "all" };
    private String selection = DEFAULT_OPTIONS[0];

    public ComboEntityModel(BW4TClientGUI clientGUI) {
        this.gui = clientGUI;
    }

    @Override
    public Object getElementAt(int listIndex) {
        int defaultOptionsSize = DEFAULT_OPTIONS.length;

        /** If the element is a default option, then return that option: */
        if (listIndex < defaultOptionsSize) {
            return DEFAULT_OPTIONS[listIndex];
        }
        
        filterEntityList();

        /** Else return the agent in the dropdown menu list: */
        int entityIndex = listIndexToAgentIndex(listIndex);
        if (entityIndex >= 0) {
            return getEntities().get(entityIndex);
        }

        return null;
    }

    /**
     * Gets the agent index in the environment's agent list from the supplied agent index in the list.
     * 
     * @param listIndex
     *            The index of the agent in the list.
     * @return The agent index, -1 if the agent was not in the list.
     */
    private int listIndexToAgentIndex(int listIndex) {
        listIndex -= DEFAULT_OPTIONS.length;

        /** A default option, which can't be translated to an agent id: */
        if (listIndex < 0 || listIndex >= getEntities().size()) {
            return -1;
        }
        return listIndex;
    }

    @Override
    public int getSize() {
        filterEntityList();
        return getEntities().size() + DEFAULT_OPTIONS.length;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public void setSelectedItem(Object arg0) {
        selection = (String) arg0;
    }

    /**
     * load the list of entities from the server and filter it.
     */
    private void filterEntityList() {
        final RemoteEnvironment env = gui.getController().getEnvironment();
        entities = new ArrayList<>();

        for (String entity : env.getEntities()) {
            try {
                if (!"epartner".equalsIgnoreCase(env.getType(entity))) {
                    entities.add(entity);
                }
            } catch (EntityException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getEntities() {
        return entities;
    }

}
