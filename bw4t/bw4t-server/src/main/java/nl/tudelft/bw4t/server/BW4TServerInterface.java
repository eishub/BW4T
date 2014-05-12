package nl.tudelft.bw4t.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import eis.exceptions.EntityException;
import eis.iilang.EnvironmentState;
import nl.tudelft.bw4t.client.BW4TClient;

/**
 * Interface of the actions performed by BW4TServer.
 */
public interface BW4TServerInterface extends Remote {

    /**
     * Communicate a message.
     *
     * @return a String containing a message
     * @throws RemoteException
     *             if the remote was not responding correctly.
     */
    String say() throws RemoteException;
    
	/**
	 * Called when a client wants to register itself to this server
	 * 
	 * @param client
	 *            the client that wants to register
	 * @param agentCount
	 *            the amount of agent entities that the client is waiting for
	 * @param humanCount
	 *            the amount of human GUI entities that the client is waiting
	 *            for
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void registerClient(BW4TClient client, int agentCount, int humanCount)
			throws RemoteException;
	
	/**
	 * Get the state of the environment
	 * 
	 * @return the state of the environment
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 */
	EnvironmentState getState() throws RemoteException;
	
	/**
	 * Get all free entities on the server
	 * 
	 * @return all free entities
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 */
	Collection<String> getFreeEntities() throws RemoteException;

}
