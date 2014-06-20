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
            client = remoteEnvironment.getClient();
            runningGUI = "true".equals(InitParam.LAUNCHGUI.getValue());
            humanType = "human".equals(remoteEnvironment.getType(entity.replace("gui", "")));
            guiEntity = entity.contains("gui");
        } catch (EntityException e) {
            throw new PerceiveException("getAllPerceptsFromEntity failed for " + entity, e);
        }
    }
    
    public static List<Percept> tryGetAllPerceptsFromEntity(String entity, RemoteEnvironment env) {
        try {
            return env.getClient().getAllPerceptsFromEntity(entity.replace("gui", ""));
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        }
    }
    
    public static List<Percept> tryGetGUIPercepts(String entity, RemoteEnvironment env ) {
        if(env.getClient() == null) {
            return new LinkedList<>();
        }
        try {
            List<Percept> percepts = env.getClient().getAllPerceptsFromEntity(entity);

            ClientController control = env.getEntityController(entity);
            if (percepts.size() > 0 && control != null) {
                //control.handlePercepts(percepts); //TODO: What was this for? It causes percepts to be received twice (it's also called in run() in ClientMapController), and thus causes each message a bot sends to be shown twice (bug).
            }
            
            return percepts;
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        }
    }
    
    public static List<Percept> tryClientGetAllPerceptsFromEntity(String entity, RemoteEnvironment env ) {
        try {
            return env.getClient().getAllPerceptsFromEntity(entity);
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        }
    }
}
