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
	
	/**
	 * run the client. This is provided as separate function from the
	 * constructor, because the parent (BW4TEnvironment) needs to do some setup
	 * with the given constructor, before it is really ready to handle callbacks
	 * from the server.
	 * 
	 * 
	 * @param parameters
	 *            the set of initialization parameters as Map<String,Parameter>.
	 *            Should contain values for the following keys:
	 *            <ul>
	 *            <li>clientip
	 *            <li>clientport
	 *            <li>serverip
	 *            <li>serverport
	 *            <li>agentcount
	 *            <li>humancount
	 *            <li>goal
	 *            </ul>
	 *            Note, these are not all init parameters available to
	 *            BW4TRemoteEnvironment, these are just the ones needed to
	 *            launch this client class.
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void connectServer(java.util.Map<String, Parameter> initParameters)
			throws RemoteException, MalformedURLException, NotBoundException {

		// Get all necessary parameters from initialization arguments
		Parameter clientIp = initParameters.get(InitParam.CLIENTIP.nameLower());
		String clientIpString = ((Identifier) clientIp).getValue();

		Parameter clientPort = initParameters.get(InitParam.CLIENTPORT
				.nameLower());
		String clientPortString = ((Identifier) clientPort).getValue();

		Parameter serverIp = initParameters.get(InitParam.SERVERIP.nameLower());
		String serverIpString = ((Identifier) serverIp).getValue();

		Parameter serverPort = initParameters.get(InitParam.SERVERPORT
				.nameLower());
		String serverPortString = ((Identifier) serverPort).getValue();

		// Launch the client and bind it
		bindAddress = "rmi://" + clientIpString + ":" + clientPortString
				+ "/BW4TClient";
		try {
			LocateRegistry.createRegistry(Integer.parseInt(clientPortString));
		} catch (Exception e) {
			System.out.println("Registry already created");
		}
		Naming.rebind(bindAddress, this);
		System.out.println("BW4TClient bound");

		// Register the client to the server
		String address = "//" + serverIpString + ":" + serverPortString
				+ "/BW4TServer";
		try {
			server = (BW4TServerInterface) Naming.lookup(address);
		} catch (Exception e) {
			throw new NoEnvironmentException("Failed to connect " + address, e);
		}

	}
	
}
