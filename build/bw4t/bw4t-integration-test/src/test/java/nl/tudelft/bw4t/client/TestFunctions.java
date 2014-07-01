package nl.tudelft.bw4t.client;

import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Percept;

import java.util.Collection;
import java.util.Iterator;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

/**
 * Support class for the Integration test.
 */
public final class TestFunctions {
    
    /**
     * The client to query for percepts.
     */
    private static RemoteEnvironment client = null;
    /**
     * The currently stored percepts.
     */
    private static Collection<Percept> percepts;
    
    /**
     * Should never be instantiated.
     */
    private TestFunctions() { }
    
    /**
     * Check if a given percept is present. You must have retrieved percepts before this.
     * @param prologPercept A Prolog percept
     * @return True if the percept is present, otherwise false
     */
    public static boolean hasPercept(String prologPercept) {
        if (percepts == null) {
            return false;
        }
        String perceptName = prologPercept.substring(0, prologPercept.indexOf("("));
        Iterator<Percept> iterator = percepts.iterator();
        while (iterator.hasNext()) {
            Percept percept = iterator.next();
            if (percept.getName().equals(perceptName) && percept.toProlog().equals(prologPercept)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retrieves the percepts received by the given bot.
     * @param entityId Entity to fetch percepts from
     */
    public static void retrievePercepts(String entityId) {
        try {
            percepts = client.getAllPercepts(entityId).get(entityId);
        } catch (PerceiveException e) {
            e.printStackTrace();
        } catch (NoEnvironmentException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return The current collection of percepts
     */
    public static Collection<Percept> getPercepts() {
        return percepts;
    }
    
    /**
     * Sets the client to be used.
     * @param c Client
     */
    public static void setClient(RemoteEnvironment c) {
        client = c;
    }
}
