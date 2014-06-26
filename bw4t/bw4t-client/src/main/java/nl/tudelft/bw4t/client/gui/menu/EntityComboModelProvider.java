package nl.tudelft.bw4t.client.gui.menu;

import java.util.Collection;

public interface EntityComboModelProvider {
    /**
     * The list of entities in the environment.
     * @return the remote environment
     */
    Collection<String> getEntities();
}
