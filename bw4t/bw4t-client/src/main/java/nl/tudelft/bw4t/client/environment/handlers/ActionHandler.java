package nl.tudelft.bw4t.client.environment.handlers;

import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.iilang.Action;
import eis.iilang.Percept;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

/** The ActionHandler Class handles the performAction function of the RemoteEnvironment. */
public final class ActionHandler {
	
	/** Should never be instantiated. */
	private ActionHandler() { }
	
    /**
     * Handles the performAction function from the {@link RemoteEnvironment}. 
     * All actions which are requested are routed through this function.
     * 
     * @param agent
     *            - The agent requesting the action.
     * @param action
     *            - The {@link Action} being requested.
     * @param remoteEnvironment
     *            - The RemoteEnvironment which should be acted upon.
     * @param entities
     *            - The entities that should perform the action
     * @return A map of entity names -> {@link Percept} that have resulted by Execution of the action.
     * @throws ActException
     *             The act exception is thrown if the action cannot be performed.
     * @throws AgentException
     *             The agent exception is thrown if the agent is not registered
     *             or does not have any entities assigned.
     */
    public static Map<String, Percept> performActionDelegated(String agent, Action action,
            RemoteEnvironment remoteEnvironment, String... entities) throws ActException, AgentException {

        /** Check to see if the agent is actually registered. */
        if (!remoteEnvironment.getAgents().contains(agent)) {
            throw new ActException(ActException.NOTREGISTERED);
        }

        /** Get a list of associated entities and target entities. */
        Set<String> associatedEntities = remoteEnvironment.getAssociatedEntities(agent);
        if ((associatedEntities == null) || associatedEntities.isEmpty()) {
            throw new ActException(ActException.NOENTITIES);
        }
        Set<String> targetEntities = getTargetEntities(associatedEntities, entities);

        /** Check if the action is supported by the environment. */
        if (!remoteEnvironment.isSupportedByEnvironment(action)) {
            throw new ActException(ActException.NOTSUPPORTEDBYENVIRONMENT);
        }

        checkSupportedEntities(entities, remoteEnvironment, action);

        return getActionPercept(targetEntities, remoteEnvironment, action);
    }

    /**
     * Gets the targeted entities(The entities which should perform the {@link Action}).
     * 
     * @param associatedEntities
     *            - All the entities associated in the {@link RemoteEnvironment}.
     * @param entities
     *            - The entities which should perform the action.
     * @return The set of entities which should perform the action.
     * @throws ActException
     *             Thrown if the the entity is incorrect.
     */
    private static Set<String> getTargetEntities(Set<String> associatedEntities, String... entities)
            throws ActException {
        Set<String> targetEntities = null;
        if (entities.length == 0) {
            targetEntities = associatedEntities;
        } else {
            targetEntities = new HashSet<String>();
            for (String entity : entities) {
                if (!associatedEntities.contains(entity)) {
                    throw new ActException(ActException.WRONGENTITY);
                }
                targetEntities.add(entity);
            }
        }
        return targetEntities;
    }

    /**
     * Check if the entity types and the entities are supported.
     * 
     * @param entities
     *            - The entities which need to be checked.
     * @param remoteEnvironment
     *            - The {@link RemoteEnvironment} which is currently used.
     * @param action
     *            - The {@link Action} which is requested to be performed.
     * @throws ActException
     *             Thrown if the action is not support by the type of entity, or by the entity.
     */
    private static void checkSupportedEntities(String[] entities, RemoteEnvironment remoteEnvironment, Action action)
            throws ActException {
        for (String entity : entities) {
            String type;
            try {
                type = remoteEnvironment.getType(entity);
            } catch (EntityException e) {
                throw new ActException("can't get entity type", e);
            }
            if (!remoteEnvironment.isSupportedByType(action, type)) {
                throw new ActException(ActException.NOTSUPPORTEDBYTYPE);
            }
            if (!remoteEnvironment.isSupportedByEntity(action, type)) {
                throw new ActException(ActException.NOTSUPPORTEDBYENTITY);
            }
        }
    }

    /**
     * Perform the action on the server and get the resulting {@link Percept}{@code s}.
     * 
     * @param targetEntities
     *            - The entities which should perform the action.
     * @param remoteEnvironment
     *            - The {@link RemoteEnvironment} which is acted upon.
     * @param action
     *            - The {@link Action} to be performed.
     * @return The map of resulting percepts.
     * @throws ActException
     *             Thrown if the server could not perform the action.
     */
    private static Map<String, Percept> getActionPercept(Set<String> targetEntities,
            RemoteEnvironment remoteEnvironment, Action action) throws ActException {
        Map<String, Percept> actionPercepts = new HashMap<String, Percept>();
        for (String entity : targetEntities) {
            try {
                Percept percept = remoteEnvironment.performEntityAction(entity, action);
                if (percept != null) {
                    actionPercepts.put(entity, percept);
                }
            } catch (RemoteException e) {
                throw new ActException(ActException.FAILURE, "performAction failed:", e);
            }
        }
        return actionPercepts;
    }
}
