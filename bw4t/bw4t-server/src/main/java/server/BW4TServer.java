package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;


/**
 * The server object that handles all communication with the client.
 *  @author Sille Kamoen
 *
 */
public class BW4TServer extends UnicastRemoteObject implements BW4TServerInterface {

	/**
	 * Comments about object variables.
	 */
	private static final long serialVersionUID = 6109258464610186214L;
	private final HashMap<BW4TClient, Integer> clientWaitingForAgent;
	private final HashMap<BW4TClient, Integer> clientWaitingForHuman;

	/**
	 * Name of the server
	 */
	private final String servername;
	
	private BW4TEnvironment env;

	/**
	 * Create a new instance of the server
	 * 
	 * @param serverIp
	 *            the ip address that the server should listen to
	 * @param serverPort
	 *            the port that the server should listen to
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws MalformedURLException
	 */
	public BW4TServer(String serverIp, String serverPort)
			throws RemoteException, MalformedURLException {
		super();
		clientWaitingForAgent = new HashMap<BW4TClient, Integer>();
		clientWaitingForHuman = new HashMap<BW4TClient, Integer>();
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(Integer
					.parseInt(serverPort));
		} catch (RemoteException e) {
			System.out.println("registry is already running. Reconnecting.");
			registry = LocateRegistry.getRegistry(Integer.parseInt(serverPort));
		}
		servername = "rmi://" + serverIp + ":" + serverPort + "/BW4TServer";
		Naming.rebind(servername, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerClient(BW4TClient client, int agentCount,
			int humanCount) throws RemoteException {
		System.out.println("registerClient " + client);
		//clientWaitingForAgent.put(client, new Integer(agentCount));
		//clientWaitingForHuman.put(client, new Integer(humanCount));

		/**
		 * #2234. First, tell client which map to use. Because of the many
		 * things that may relate to maps, this must come before the client gets
		 * to do anything else.
		 */
		client.useMap(BW4TEnvironment.getInstance().getMap());

		// #2013 late-listening pattern... partial implement.
		((BW4TClientActions) client).handleStateChange(getState());
		// System.out.println("late=listening: " + getFreeEntities());
		for (String entity : getFreeEntities()) {
			try {
				notifyFreeEntity(client, entity);
			} catch (EntityException e) {
				reportClientProblem(client, e);
			}
		}

	}

	@Override
	public void unregisterClient(BW4TClient client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Percept performEntityAction(String entity, Action action) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateEntity(String agentId, String entityId) throws RelationException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerAgent(String agentId) throws RemoteException, AgentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterAgent(String agent) throws AgentException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LinkedList<Percept> getAllPerceptsFromEntity(String entity) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<String> getAgents() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<String> getAssociatedEntities(String agent) throws RemoteException, AgentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getEntities() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void freeEntity(String entity) throws RemoteException, RelationException, EntityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freeAgent(String agent) throws RemoteException, RelationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freePair(String agent, String entity) throws RemoteException, RelationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<String> getAssociatedAgents(String entity) throws RemoteException, EntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getFreeEntities() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType(String entity) throws RemoteException, EntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnvironmentState getState() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryProperty(String property) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryEntityProperty(String entity, String property) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSupportedByEnvironment(Action arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void requestStart() throws RemoteException, ManagementException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestPause() throws RemoteException, ManagementException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestInit(Map<String, Parameter> parameters) throws RemoteException, ManagementException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestReset(Map<String, Parameter> parameters) throws RemoteException, ManagementException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterClient(BW4TClient client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
