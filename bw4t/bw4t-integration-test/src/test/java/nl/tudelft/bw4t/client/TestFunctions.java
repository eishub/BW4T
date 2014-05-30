package nl.tudelft.bw4t.client;

import java.util.Collection;
import java.util.Iterator;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Percept;

public class TestFunctions {
	
	private static RemoteEnvironment client=null;
	
	private static Collection<Percept> percepts;
	
	/**
	 * Check if a given percept is present. You must have retrieved percepts before this.
	 * @param prologPercept A Prolog percept
	 * @return True if the percept is present, otherwise false
	 */
	public static boolean hasPercept(String prologPercept){
		if (percepts==null){
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
	 * @param entityId
	 */
	public static void retrievePercepts(String entityId){
		try {
			percepts=client.getAllPercepts(entityId).get(entityId);
		} catch (PerceiveException e) {
			e.printStackTrace();
		} catch (NoEnvironmentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return The current collection of percepts
	 */
	public static Collection<Percept> getPercepts(){
		return percepts;
	}
	
	/**
	 * Sets the client to be used.
	 * @param c
	 */
	public static void setClient(RemoteEnvironment c){
		client=c;
	}
}
