package nl.tudelft.bw4t.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;

import eis.exceptions.EntityException;
import eis.iilang.EnvironmentState;
import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.BW4TClientActions;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;

/**
 * The BW4TServer, to which the client can connect to.
 */
public class BW4TServer extends UnicastRemoteObject implements
        BW4TServerInterface {

    /**
     * The serial, generated.
     */
    private static final long serialVersionUID = -7313583479222142380L;
    /**
     * The message which is going to be sent towards the client.
     */
    private final String message;

	/**
	 * Hashmaps to track which clients are connected
	 * TODO: explain difference
	 */
    private HashMap<BW4TClient, Integer> clientWaitingForAgent;
	/**
	 * Hashmaps to track which clients are connected (2)
	 */
	private HashMap<BW4TClient, Integer> clientWaitingForHuman;

    /**
     * The constructor, which creates the server object.
     * @param msg The message that should be said.
     * @throws RemoteException If anything goes wrong with the set up.
     */
    protected BW4TServer(final String msg) throws RemoteException {
        super();
        message = msg;
    }

    /**
     * Method which says the message towards the client.
     * @return message The message being said.
     */
    @Override
	public final String say() {

        return message;
    }

    /**
     * The main method launching the BW4TServer.
     * @param args Optional Arguments, not used.
     * @throws RemoteException If something goes wrong during initialization.
     */
    public static void main(final String[] args) throws RemoteException {
        String servername;
        String serverIp = "127.0.0.1";
        String serverPort = "8989";
        @SuppressWarnings("unused")
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(Integer
                    .parseInt(serverPort));
        } catch (RemoteException e) {
            System.out.println("Registry is already running. Reconnecting.");
            registry = LocateRegistry.getRegistry(Integer.parseInt(serverPort));
        }
        servername = "rmi://" + serverIp + ":" + serverPort + "/BW4TServer";
        try {
            Naming.rebind(servername, new BW4TServer(
                    "Hello Client! Welcome to the BW4TServer."));
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
        System.out
                .println("BW4TServer is ready to accept incoming connections.");
    }

	@Override
	public void registerClient(BW4TClient client, int agentCount, int humanCount) throws RemoteException {
		System.out.println("registerClient " + client);
		clientWaitingForAgent.put(client, new Integer(agentCount));
		clientWaitingForHuman.put(client, new Integer(humanCount));

		/**
		 * #2234. First, tell client which map to use. Because of the many
		 * things that may relate to maps, this must come before the client gets
		 * to do anything else.
		 */
		client.useMap(BW4TEnvironment.getInstance().getMap());

		// #2013 late-listening pattern... partial implement.
		client.handleStateChange(getState());
		// System.out.println("late=listening: " + getFreeEntities());
		for (String entity : getFreeEntities()) {
			try {
				notifyFreeEntity(client, entity);
			} catch (EntityException e) {
				reportClientProblem(client, e);
			}
		}
		
	}
	
	/**
	 * Notify given client of a new free entity, if the new entity is of
	 * interest for that client. Note, we use this both when an entity is new
	 * and when an entity became free after use.
	 * 
	 * @param client
	 * @param entity
	 * @throws EntityException
	 */
	private void notifyFreeEntity(BW4TClient client, String entity)
			throws EntityException {
		String type = BW4TEnvironment.getInstance().getType(entity);

		if (clientWaitingForAgent.get(client).intValue() > 0
				&& (type.equals("unknown") || type.equals("bot"))) {
			try {
				if (type.equals("unknown")) {
					BW4TEnvironment.getInstance().setType(entity, "bot");
				}
				client.handleNewEntity(entity);
				// Client is now waiting for one less entity
				clientWaitingForAgent.put(client, new Integer(
						clientWaitingForAgent.get(client).intValue() - 1));
				return;
			} catch (RemoteException e) {
				reportClientProblem(client, e);
			}
		} else if (clientWaitingForHuman.get(client).intValue() > 0
				&& (type.equals("unknown") || type.equals("human"))) {
			try {
				if (type.equals("unknown")) {
					BW4TEnvironment.getInstance().setType(entity, "human");
				}
				client.handleNewEntity(entity);
				// Client is now waiting for one less entity
				clientWaitingForHuman.put(client, new Integer(
						clientWaitingForHuman.get(client).intValue() - 1));
				return;
			} catch (RemoteException e) {
				reportClientProblem(client, e);
			}
		}
	}
	
	/**
	 * This is called when we should call back to one of our clients but it
	 * gives us an exception.
	 * 
	 * @param client
	 * @param e
	 */
	private void reportClientProblem(BW4TClient client, Exception e) {
		System.out.println("issue with client " + client + ":" + e);
		e.printStackTrace();
	}

	/**
	 * {@inheritDoc}
	 * instance GetState() comes from EIS package.
	 */
	@Override
	public EnvironmentState getState() throws RemoteException {
		return BW4TEnvironment.getInstance().getState();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getFreeEntities() throws RemoteException {
		return BW4TEnvironment.getInstance().getFreeEntities();
	}

}
