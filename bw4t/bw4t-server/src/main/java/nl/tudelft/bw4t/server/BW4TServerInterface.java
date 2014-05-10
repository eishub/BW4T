package nl.tudelft.bw4t.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

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

}
