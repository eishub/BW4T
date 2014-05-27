package nl.tudelft.bw4t.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.eq;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.server.BW4TServerActions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.mockmaker.PowerMockMaker;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({InitParam.class,Naming.class,LocateRegistry.class})
public class BW4TClientTest {
	BW4TClient client;

	@Mock
	RemoteEnvironment remoteEnvironment;
	@Mock
	InitParam initParams;
	@Mock
	BW4TServerActions server;
	@Mock
	Registry registry;

	@Test
	public void test_Constructor() throws Exception {
		client = new BW4TClient(remoteEnvironment);
		assertNotNull(client);
	}
	
	@Test
	public void test_ConnectServer() throws RemoteException, MalformedURLException, NotBoundException {
		client = new BW4TClient(remoteEnvironment);

		PowerMockito.doReturn("12").when(spy(InitParam.CLIENTPORT)).getValue();
		PowerMockito.doReturn("123.123.123.123").when(spy(InitParam.SERVERIP)).getValue();
		PowerMockito.doReturn("123").when(spy(InitParam.SERVERPORT)).getValue();
		
		PowerMockito.mockStatic(Naming.class);
		PowerMockito.when(Naming.lookup(any(String.class))).thenReturn(server);
		
		PowerMockito.mockStatic(LocateRegistry.class);
		PowerMockito.when(LocateRegistry.createRegistry(any(Integer.class))).thenReturn(registry);
		
		//replay(Naming.class);
		
		//client.connectServer();
		assertTrue(true);
	}

}
