package nl.tudelft.bw4t.client.gui.menu;

import java.util.Collection;

/**
 * DOC What is this?
 *
 */
public interface EntityComboModelProvider {
	/**
	 * The list of entities in the environment.
	 * 
	 * @return the remote environment
	 */
	Collection<String> getEntities();
}
