package nl.tudelft.bw4t.client.environment;

import java.util.Collection;

import org.apache.log4j.Logger;

import eis.EnvironmentListener;

public class EntityNotifiers {
    private static final Logger LOGGER = Logger.getLogger(EntityNotifiers.class);

    /**
     * Notifies all listeners about an entity that is free.
     * 
     * @param entity
     *            is the free entity.
     * @param agents
     *            is the list of agents that were associated
     */
    public static void notifyFreeEntity(String entity, Collection<String> agents, RemoteEnvironmentData data) {
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleFreeEntity(entity, agents);
        }
    }

    /**
     * Notifies all listeners about an entity that has been newly created.
     * 
     * @param entity
     *            is the new entity.
     */
    public static void notifyNewEntity(String entity, RemoteEnvironmentData data) {
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleNewEntity(entity);
        }
    }

    /**
     * Notifies all listeners about an entity that has been deleted.
     * 
     * @param entity
     *            is the deleted entity.
     */
    public static void notifyDeletedEntity(String entity, Collection<String> agents, RemoteEnvironmentData data) {
        LOGGER.debug("Notifying all listeners about an entity that has been deleted.");
        if (data.getEntityToGUI().get(entity) != null) {
            data.getEntityToGUI().get(entity).getjFrame().dispose();
        }
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleDeletedEntity(entity, agents);
        }
    }

}
