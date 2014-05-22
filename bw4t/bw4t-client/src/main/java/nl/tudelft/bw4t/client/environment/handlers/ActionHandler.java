package nl.tudelft.bw4t.client.environment.handlers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.iilang.Action;
import eis.iilang.Percept;

public class ActionHandler {
    /**
     * Internal version that can fully throw exceptions
     * 
     * @param agent
     * @param action
     * @param entities
     * @return
     * @throws ActException
     * @throws AgentException
     */
    public static Map<String, Percept> performAction1(String agent,
            Action action, RemoteEnvironment remoteEnvironment,
            String... entities) throws ActException, AgentException {
        // FIXME this function is way too long.
        // 1. unregistered agents cannot act
        if (!remoteEnvironment.getAgents().contains(agent)) {
            throw new ActException(ActException.NOTREGISTERED);
        }
        // get the associated entities
        Set<String> associatedEntities;
        associatedEntities = (HashSet<String>) remoteEnvironment
                .getAssociatedEntities(agent);
        // 2. no associated entity/ies -> trivial reject
        if (associatedEntities == null || associatedEntities.isEmpty()) {
            throw new ActException(ActException.NOENTITIES);
        }
        // entities that should perform the action
        Set<String> targetEntities = null;
        if (entities.length == 0) {
            targetEntities = associatedEntities;
        } else {
            targetEntities = new HashSet<String>();
            for (String entity : entities) {
                // 3. provided wrong entity
                if (!associatedEntities.contains(entity)) {
                    throw new ActException(ActException.WRONGENTITY);
                }
                targetEntities.add(entity);
            }
        }
        // 4. action could be not supported by the environment
        if (!remoteEnvironment.isSupportedByEnvironment(action)) {
            throw new ActException(ActException.NOTSUPPORTEDBYENVIRONMENT);
        }
        // 5. action could be not supported by the type of the entities
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
        }
        // 6. action could be not supported by the entities themselves
        for (String entity : entities) {
            String type;
            try {
                type = remoteEnvironment.getType(entity);
            } catch (EntityException e) {
                throw new ActException("can't get entity type", e);
            }
            if (!remoteEnvironment.isSupportedByEntity(action, type)) {
                throw new ActException(ActException.NOTSUPPORTEDBYENTITY);
            }
        }
        Map<String, Percept> ret = new HashMap<String, Percept>();
        // 6. action could be not supported by the entities themselves
        for (String entity : targetEntities) {
            // TODO catch and rethrow exceptions //differentiate between
            // actexceptions and others
            // TODO how is ensured that this method is called? ambiguity?
            try {
                Percept p = remoteEnvironment.performEntityAction(entity,
                        action);
                if (p != null) {
                    // workaround for #2270
                    ret.put(entity, p);
                }
            } catch (Exception e) {
                throw new ActException(ActException.FAILURE,
                        "performAction failed:", e);
            }
        }
        return ret;
    }
}
