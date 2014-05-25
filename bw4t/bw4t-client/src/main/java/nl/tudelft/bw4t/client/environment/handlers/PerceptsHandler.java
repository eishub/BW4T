package nl.tudelft.bw4t.client.environment.handlers;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.BW4TClient;
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
     * Get all percepts for a certain entity, is passed through the server.
     * 
     * @param entity
     *            The entity for which the percepts are requested.
     * @return The list of received percepts.
     * @throws PerceiveException
     *             The NoEnvironmentException is thrown if an attempt to perform
     *             an action or to retrieve percepts has failed.
     */
    public static List<Percept> getAllPerceptsFromEntity(String entity, RemoteEnvironment env) throws PerceiveException {
        try {
            RemoteEnvironmentData remoteEnvironmentData = env.getData();
            BW4TClientGUI clientEntity = remoteEnvironmentData.getEntityToGUI().get(entity);
            BW4TClient client = remoteEnvironmentData.getClient();

            /** Is the client running a GUI right now */
            boolean runningGUI = "true"
                    .equals(((Identifier) remoteEnvironmentData.getInitParameters().get("launchgui")).getValue());
            /** Is the entity human, regardless if it's running a GUI? */
            boolean humanType = "human".equals(env.getType(entity.replace("gui", "")));
            /** Is the entity being run on a GUI? */
            boolean guiEntity = entity.contains("gui");

            if (remoteEnvironmentData.isConnectedToGoal() && !guiEntity && humanType) {
                return clientEntity.getToBePerformedAction();
            } else if (guiEntity && humanType) {
                return client.getAllPerceptsFromEntity(entity.replace("gui", ""));
            } else if (runningGUI) {
                if (clientEntity == null) {
                    return new LinkedList<Percept>();
                }
                /** Get the percepts and process them in the GUI */
                List<Percept> percepts = client.getAllPerceptsFromEntity(entity);
                if (percepts != null) {
                    ProcessingOperations.processPercepts(percepts, clientEntity.getBW4TClientInfo());
                }
                return percepts;
            }
            return client.getAllPerceptsFromEntity(entity);
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        } catch (EntityException e) {
            throw new PerceiveException("getAllPerceptsFromEntity failed for " + entity, e);
        }
    }
}
