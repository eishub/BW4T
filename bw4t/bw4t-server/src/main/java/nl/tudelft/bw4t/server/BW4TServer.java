package nl.tudelft.bw4t.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
    private String message;

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

}
