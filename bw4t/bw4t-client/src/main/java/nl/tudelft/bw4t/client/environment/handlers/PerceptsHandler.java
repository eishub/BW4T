package nl.tudelft.bw4t.client.environment.handlers;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.environment.RemoteEnvironmentData;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.operations.ProcessingOperations;
import eis.exceptions.EntityException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Percept;

public class PerceptsHandler {
    /**
     * Get all percepts for a certain entity, is passed through the server
     * 
     * @param entity
     *            , the entity for which all percepts should be gotten
     * @return the list of received percepts, null if an exception occurred
     * @throws PerceiveException
     *             , NoEnvironmentException if the attempt to perform an action
     *             or to retrieve percepts has failed.
     */
    public static List<Percept> getAllPerceptsFromEntity(String entity, RemoteEnvironment env)
            throws PerceiveException {
        RemoteEnvironmentData data = env.getData();
        try {
            boolean launchGui = "true".equals(((Identifier) data
                    .getInitParameters().get("launchgui")).getValue());
            if (data.isConnectedToGoal() && !"gui".contains(entity)
                    && "human".equals(env.getType(entity))) {
                return (LinkedList<Percept>) data.getEntityToGUI().get(entity)
                        .getToBePerformedAction();
            } else if (entity.contains("gui")
                    && "human".equals(env.getType(entity.replace("gui", "")))) {
                return (LinkedList<Percept>) data.getClient()
                        .getAllPerceptsFromEntity(entity.replace("gui", ""));
            } else if (launchGui) {
                if (data.getEntityToGUI().get(entity) == null) {
                    return null;
                }
                List<Percept> percepts = (LinkedList<Percept>) data.getClient()
                        .getAllPerceptsFromEntity(entity);
                BW4TClientGUI tempEntity;
                if (percepts != null) {
                    tempEntity = data.getEntityToGUI().get(entity);
                    ProcessingOperations.processPercepts(percepts,
                            tempEntity.getBW4TClientInfo());
                }
                return percepts;
            } else {
                return (LinkedList<Percept>) data.getClient()
                        .getAllPerceptsFromEntity(entity);
            }
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        } catch (EntityException e) {
            throw new PerceiveException("getAllPerceptsFromEntity failed for "
                    + entity, e);
        }
    }
}
