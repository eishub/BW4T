package nl.tudelft.bw4t.network;

import java.rmi.RemoteException;

/** Interface BW4TServerHiddenActions */
public interface BW4TServerHiddenActions extends BW4TServerActions {
    /**
     * Shutdown the server if the given key matches the key given to the server
     * at startup.
     * 
     * @param key
     *            the key for this server
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    void stopServer(String key) throws RemoteException;
}
