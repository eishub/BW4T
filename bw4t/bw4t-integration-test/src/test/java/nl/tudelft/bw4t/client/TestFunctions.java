package nl.tudelft.bw4t.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Percept;

public class TestFunctions {
	
	private static RemoteEnvironment client=null;
	
	private static Collection<Percept> percepts;
	
	/**
	 * @param prologPercept A Prolog percept
	 * @return True if the percept is present, otherwise false
	 */
	public static boolean hasPercept(String prologPercept){
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
	
	public static void retrievePercepts(String entityId){
		try {
			percepts=client.getAllPercepts(entityId).get(entityId);
		} catch (PerceiveException e) {
			e.printStackTrace();
		} catch (NoEnvironmentException e) {
			e.printStackTrace();
		}
	}
	
	public static Collection<Percept> getPercepts(){
		return percepts;
	}
	
	public static void setClient(RemoteEnvironment c){
		client=c;
	}
}
