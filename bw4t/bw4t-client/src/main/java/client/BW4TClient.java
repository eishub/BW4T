package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.jws.soap.InitParam;

import org.omg.Dynamic.Parameter;


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
	private BW4TServerInterface server;
	
	//@ToDO: Still using NewMap???
	private NewMap map;
	
	/**
	 * Create a client object which calls upon a connection
	 *  with a server.
	 * @param initParameters The initial parameters needed to set up the connection.
	 * @ToDo: See what is needed to make this function work, without RemoteEnvironment
	 */
	private BW4TClient(java.util.Map<String, Parameter> initParameters) {
		connectServer(initParameters);
	}
	
	protected BW4TClient(BW4TRemoteEnvironment parent) throws RemoteException, 
				MalformedURLException, NotBoundException {
		this.parent = parent;
	}
	
}
