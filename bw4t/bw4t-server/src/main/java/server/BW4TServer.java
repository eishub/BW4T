package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


/**
 * The server object that handles all communication with the client.
 *  @author Sille Kamoen
 *
 */
public class BW4TServer extends UnicastRemoteObject {

	/**
	 * Comments about object variables.
	 */
	private static final long serialVersionUID = 6109258464610186214L;
	//private final HashMap<BW4TClientActions, Integer> clientWaitingForAgent;
	//private final HashMap<BW4TClientActions, Integer> clientWaitingForHuman;

	/**
	 * Name of the server
	 */
	private final String servername;
	
	/**
	 * Create a new instance of the server
	 * 
	 * @param serverIp
	 *            the ip address that the server should listen to
	 * @param serverPort
	 *            the port that the server should listen to
	 * 
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws MalformedURLException
	 */
	public BW4TServer(String serverIp, String serverPort)
			throws RemoteException, MalformedURLException {
		super();
		//clientWaitingForAgent = new HashMap<BW4TClientActions, Integer>();
		//clientWaitingForHuman = new HashMap<BW4TClientActions, Integer>();
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

}
