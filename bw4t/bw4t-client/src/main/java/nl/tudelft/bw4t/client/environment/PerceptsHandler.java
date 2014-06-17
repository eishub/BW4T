package nl.tudelft.bw4t.client.environment;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;
import eis.exceptions.EntityException;
import eis.exceptions.PerceiveException;
import eis.iilang.Percept;

/**
 * The Percepts Handler, which should retrieve the percepts from the environment.
 */
public class PerceptsHandler {
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
     * @param env .
     *            The remote environment
     * @return The list of received percepts.
     * @throws PerceiveException             
     *            The NoEnvironmentException is thrown if an attempt to perform
     *            an action or to retrieve percepts has failed.
     */
    public static List<Percept> getAllPerceptsFromEntity(String entity, RemoteEnvironment env) throws PerceiveException {
        tryGetEntityConfig(entity, env);
        final ClientController control = env.getEntityController(entity);
        if (env.isConnectedToGoal() && !guiEntity && humanType) {
            if(control == null) {
                return new LinkedList<>();
            }
            return control.getToBePerformedAction();
        } else if (guiEntity && humanType) {
            return tryGetAllPerceptsFromEntity(entity, env);
        } else if (runningGUI) {
            return tryGetGUIPercepts(entity, env);
        } else {
            return tryClientGetAllPerceptsFromEntity(entity, env);
        }
        
    }
    
    public static void tryGetEntityConfig(String entity, RemoteEnvironment remoteEnvironment) throws PerceiveException {
        try {
            getEntityConfig(entity, remoteEnvironment);
        } catch (EntityException e) {
            throw new PerceiveException("getAllPerceptsFromEntity failed for " + entity, e);
        }
    }
    
    public static List<Percept> tryGetAllPerceptsFromEntity(String entity, RemoteEnvironment remoteEnvironment) {
        try {
            return client.getAllPerceptsFromEntity(entity.replace("gui", ""));
        } catch (RemoteException e) {
            throw remoteEnvironment.environmentSuddenDeath(e);
        }
    }
    
    public static List<Percept> tryGetGUIPercepts(String entity, RemoteEnvironment env ) {
        try {
            List<Percept> percepts = client.getAllPerceptsFromEntity(entity);

            if (percepts.size() > 0) {
                env.getEntityController(entity).handlePercepts(percepts);
            }
            
            return percepts;
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        }
    }
    
    public static List<Percept> tryClientGetAllPerceptsFromEntity(String entity, RemoteEnvironment remoteEnvironment ) {
        try {
            return client.getAllPerceptsFromEntity(entity);
        } catch (RemoteException e) {
            throw remoteEnvironment.environmentSuddenDeath(e);
        }
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
        client = remoteEnvironment.getClient();
        runningGUI = "true".equals(InitParam.LAUNCHGUI.getValue());
        humanType = "human".equals(remoteEnvironment.getType(entity.replace("gui", "")));
        guiEntity = entity.contains("gui");
    }
}
