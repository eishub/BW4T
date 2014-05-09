package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The client class via which all the communication travels to the server.
 * @author Sander Liebens
 */

public class BW4TClient extends UnicastRemoteObject implements BW4TClientInterface {
	
	private static final long serialVersionUID = 4060908402879332038L;
	
	/**
	 * The parent that we serve.
	 * @ToDo: check whether we need this, or how we are going to work around this.
	 */
	//private BW4TRemoteEnvironment parent
	
	private String bindAddress;
	
	//@ToDo: make this work with BW4TServerInterface.
	//private BW4TServerInterface server;
	
	//@ToDO: Still using NewMap???
	// private NewMap map;
	
	protected BW4TClient(BW4TRemoteEnvironment parent) throws RemoteException, 
				MalformedURLException, NotBoundException {
		this.parent = parent;
		
	}
	
}
