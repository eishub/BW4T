package nl.tudelft.bw4t.server;

import java.rmi.RemoteException;

public interface BW4TServerHiddenActions extends BW4TServerActions {
    /**
     * Shutdown the server if the given key matches the key given to the server
     * at startup.
     * 
     * @param key
     *            the key for this server
     */
    public void stopServer(String key) throws RemoteException;
}
