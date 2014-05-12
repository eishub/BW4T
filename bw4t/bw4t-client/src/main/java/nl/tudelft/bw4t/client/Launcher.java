package nl.tudelft.bw4t.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Launcher {

	public static void main(final String[] args) throws RemoteException, MalformedURLException, NotBoundException {
    	BW4TClient client = new BW4TClient("127.0.0.1", "8989", "127.0.0.1", "8989");
        client.connectServer();
    }
	
}
