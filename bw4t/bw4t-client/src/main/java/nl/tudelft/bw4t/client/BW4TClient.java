package nl.tudelft.bw4t.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import nl.tudelft.bw4t.server.BW4TServerInterface;

/**
 * The BW4TClient which connects to the BW4TServer.
 */
public final class BW4TClient {

    /**
     * Constructor is not used but is needed to secure non-creation of this
     * class.
     */
    private BW4TClient() {
    }

    /**
     * The main method running the client and setting up the connection to the
     * BW4T Server.
     * @param args
     *            Optional Arguments
     */
    public static void main(final String[] args) {
        try {
            BW4TServerInterface serverHello;
            serverHello = (BW4TServerInterface) Naming
                    .lookup("rmi://127.0.0.1:8989/BW4TServer");
            System.out.println(serverHello.say());
        } catch (MalformedURLException | RemoteException
                | NotBoundException e) {
            e.printStackTrace();
        }
    }

}
