package nl.tudelft.bw4t.client.environment.handlers;

import eis.exceptions.EntityException;
import eis.exceptions.PerceiveException;
import eis.iilang.Percept;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;

/**
 * The Percepts Handler, which should retrieve the percepts from the environment.
 */
public class PerceptsHandler {
    
    /** The client entity. */
    private static BW4TClientGUI clientEntity;
    
    /** The client. */
    private static BW4TClient client;
    
    /**  Is the client running a GUI right now. */
    private static boolean runningGUI;
    
    /**  Is the entity human, regardless if it's running a GUI?. */
    private static boolean humanType;
    
    /**  Is the entity being run on a GUI?. */
    private static boolean guiEntity;
    
    
    /**
     * Get all percepts for a certain entity, is passed through the server.
     *
     * @param entity            
     *            The entity for which the percepts are requested.
     * @param remoteEnvironment .
     *            The remote environment
     * @return The list of received percepts.
     * @throws PerceiveException             
     *            The NoEnvironmentException is thrown if an attempt to perform
     *            an action or to retrieve percepts has failed.
     */
    public static List<Percept> getAllPerceptsFromEntity(String entity, RemoteEnvironment remoteEnvironment)
            throws PerceiveException {
        try {
            getEntityConfig(entity, remoteEnvironment);
            if (remoteEnvironment.isConnectedToGoal() && !guiEntity && humanType) {
                return clientEntity.getController().getToBePerformedAction();
            } else if (guiEntity && humanType) {
                return client.getAllPerceptsFromEntity(entity.replace("gui", ""));
            } else if (runningGUI) {
                return getGUIPercepts(entity);
            }
            return client.getAllPerceptsFromEntity(entity);
        } catch (RemoteException e) {
            throw remoteEnvironment.environmentSuddenDeath(e);
        } catch (EntityException e) {
            throw new PerceiveException("getAllPerceptsFromEntity failed for " + entity, e);
        }
    }


    /**
     * Gets the percepts if the entity in question is running a GUI.
     *
     * @param entity the entity in question
     * @return the percepts of the given entity
     * @throws RemoteException the remote exception
     */
    private static List<Percept> getGUIPercepts(String entity) throws RemoteException {
        if (clientEntity == null) {
            return new LinkedList<Percept>();
        }
        List<Percept> percepts = client.getAllPerceptsFromEntity(entity);
        //FIXME update the Controllers with the new percepts
        return percepts;
    }


    /**
     * Gets the entity configuration.
     *
     * @param entity 
     *      The entity in question.
     * @param remoteEnvironment 
     *      The remote environment from which the percepts should be retrieved.
     * @throws EntityException is thrown if there is a problem retrieve the status of the entity. 
     */
    private static void getEntityConfig(String entity, RemoteEnvironment remoteEnvironment) throws EntityException {
        clientEntity = remoteEnvironment.getEntityToGUI().get(entity);
        client = remoteEnvironment.getClient();
        runningGUI = "true".equals(InitParam.LAUNCHGUI.getValue());
        humanType = "human".equals(remoteEnvironment.getType(entity.replace("gui", "")));
        guiEntity = entity.contains("gui");
    }
}
