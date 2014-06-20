package nl.tudelft.bw4t.client.environment;

import eis.EnvironmentListener;

import java.util.Collection;

import nl.tudelft.bw4t.client.controller.ClientController;

import org.apache.log4j.Logger;

/** Utility class for notifying listeners about freed, new or deleted entities. */
public final class EntityNotifiers {
    /** The log4j Logger which displays logs on console. */
    private static final Logger LOGGER = Logger.getLogger(EntityNotifiers.class);

    /** Should never be instantiated. */
    private EntityNotifiers() { 
        
    }
    
    /**
     * Notifies all listeners about an entity that is free.
     * 
     * @param entity
     *            - The free entity.
     * @param agents
     *            - The list of agents that were associated.
     * @param remoteEnvironment
     *            - Used to fetch the list of listeners to notify.
     */
    public static void notifyFreeEntity(String entity, Collection<String> agents,
            RemoteEnvironment remoteEnvironment) {
        for (EnvironmentListener listener : remoteEnvironment.getEnvironmentListeners()) {
            listener.handleFreeEntity(entity, agents);
        }
    }

    /**
     * Notifies all listeners about an entity that has been newly created.
     * 
     * @param entity
     *            - The new entity.
     * @param remoteEnvironment
     *            - Used to fetch the list of listeners to notify.
     */
    public static void notifyNewEntity(String entity, RemoteEnvironment remoteEnvironment) {
        for (EnvironmentListener listener : remoteEnvironment.getEnvironmentListeners()) {
            listener.handleNewEntity(entity);
        }
    }

    /**
     * Notifies all listeners about an entity that has been deleted.
     * 
     * @param entity
     *            - The deleted entity.
     * @param agents
     *            - The list of agents that were associated.
     * @param remoteEnvironment
     *            - Used to fetch the list of listeners to notify.
     */
    public static void notifyDeletedEntity(String entity, Collection<String> agents,
            RemoteEnvironment remoteEnvironment) {
        LOGGER.debug("Notifying all listeners about an entity that has been deleted.");
        final ClientController control = remoteEnvironment.getEntityController(entity);
        if (control != null) {
            control.stop();
            remoteEnvironment.putEntityController(entity, null);
        }
        for (EnvironmentListener listener : remoteEnvironment.getEnvironmentListeners()) {
            listener.handleDeletedEntity(entity, agents);
        }
    }

}
