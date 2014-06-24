package nl.tudelft.bw4t.client.environment;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.controller.ClientController;
import eis.exceptions.PerceiveException;
import eis.iilang.Percept;

/**
 * The Percepts Handler, which should retrieve the percepts from the environment.
 */
public class PerceptsHandler {
    /**
     * Get all percepts for a certain entity, is passed through the server.
     * 
     * @param entity
     *            The entity for which the percepts are requested.
     * @param env
     *            . The remote environment
     * @return The list of received percepts.
     * @throws PerceiveException
     *             The NoEnvironmentException is thrown if an attempt to perform an action or to retrieve percepts has
     *             failed.
     */
    public static List<Percept> getAllPerceptsFromEntity(String entity, RemoteEnvironment env) throws PerceiveException {
        final BW4TClient client = env.getClient();
        if (client == null) {
            return new LinkedList<>();
        }
        try {
            if (entity.endsWith("gui")) {
                entity = entity.substring(0, entity.length() - 4);
            }
            final List<Percept> percepts = client.getAllPerceptsFromEntity(entity);
            ClientController cc = env.getEntityController(entity);
            if (cc != null) {
                percepts.addAll(cc.getToBePerformedAction());
            }
            return percepts;
        } catch (RemoteException e) {
            throw env.environmentSuddenDeath(e);
        }
    }
}
