package nl.tudelft.bw4t.client;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.environment.handlers.PerceptsHandler;
import nl.tudelft.bw4t.server.BW4TEnvironment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import repast.simphony.scenario.ScenarioLoadException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import eis.iilang.Percept;

public class MovementTest {

	private RemoteEnvironment client;
	private BW4TEnvironment server;
	private Collection<Percept> percepts;

	@Before
	public void setup() throws ManagementException, IOException,
			ScenarioLoadException, JAXBException, InterruptedException {
//		String[] serverArgs = new String[]{
//				"-scenaro", "BW4T.rs", 
//				"-map", "Random",
//				"-serverip", "localhost",
//				"-serverport", "8000",
//				"-gui", "true"};
//		nl.tudelft.bw4t.server.Launcher.main(serverArgs);
//		server = nl.tudelft.bw4t.server.Launcher.getEnvironment();
		
		String[] clientArgs = new String[] {
				"-agentcount", "2", 
				"-humancount", "0"};
		nl.tudelft.bw4t.client.startup.Launcher.launch(clientArgs);
		client = nl.tudelft.bw4t.client.startup.Launcher.getEnvironment();
	}

	@After
	public void shutdown() {
	}

	@Test
	public void movementTest() throws TranslationException, ActException,
			InterruptedException, PerceiveException {
		String bot1 = client.getAgents().get(0);
		String bot2 = client.getAgents().get(1);
		
		// We verify that we are indeed at the starting area, then move to RoomC1
		assertTrue(hasPercept("at(FrontDropZone)", bot1));
		Parameter[] param = Translator.getInstance().translate2Parameter("RoomC1");
		client.performAction(bot1, new Action("goTo", param));
		// Wait for the bot to move
		Thread.sleep(2000L);
		assertTrue(hasPercept("in(RoomC1)", bot1));
		
		// Next we test collision by having a second bot attempt to enter the same room
		assertTrue(hasPercept("at(FrontDropZone)", bot2));
		param = Translator.getInstance().translate2Parameter("RoomC1");
		client.performAction(bot2, new Action("goTo", param));
		// We wait for it to move
		Thread.sleep(2000L);
		assertTrue(hasPercept("state(collided)", bot2));
		assertTrue(hasPercept("at(FrontRoomC1)"));
	}
	
	/**
	 * @param prologPercept A Prolog percept
	 * @return True if the percept is present, otherwise false
	 */
	private boolean hasPercept(String prologPercept){
		String perceptName = prologPercept.substring(0, prologPercept.indexOf("("));
		Iterator<Percept> percepts = this.percepts.iterator();
		while (percepts.hasNext()) {
			Percept percept = percepts.next();
			if (percept.getName().equals(perceptName)) {
				return percept.toProlog().equals(prologPercept);
			}
		}
		return false;
	}
	
	/**
	 * Essentially the same as the single argument function, but this updates the percepts first.
	 * Note that a percept can only be retrieved once.
	 * @param prologPercept A prolog percept
	 * @param entityId The name of the entity to retrieve percepts for
	 * @return True if the percept is present, otherwise false
	 * @throws PerceiveException
	 */
	private boolean hasPercept(String prologPercept, String entityId) throws PerceiveException{
//		this.percepts=PerceptsHandler.getAllPerceptsFromEntity(entityId, client);
		this.percepts=client.getAllPercepts(entityId).get(entityId);
		return hasPercept(prologPercept);
	}
}
