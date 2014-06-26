package nl.tudelft.bw4t.client.gui.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ComboEntityModel extends AbstractListModel<String> implements ComboBoxModel<String> {
    private static final long serialVersionUID = 344469588673323347L;

    private final EntityComboModelProvider gui;

    private List<String> entities = new ArrayList<>();

    private static final String[] DEFAULT_OPTIONS = new String[] { "all" };
    private String selection = DEFAULT_OPTIONS[0];

    public ComboEntityModel(EntityComboModelProvider clientGUI) {
        this.gui = clientGUI;
    }

    @Override
    public String getElementAt(int listIndex) {
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
        Collection<String> ents = gui.getEntities();
        if(ents != null){
            this.entities.clear();
            this.entities.addAll(ents);
        }
    }

    private List<String> getEntities() {
        return entities;
    }

}
