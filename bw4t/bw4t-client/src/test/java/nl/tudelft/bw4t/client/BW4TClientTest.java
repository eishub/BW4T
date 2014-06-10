package nl.tudelft.bw4t.client;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.network.BW4TClientActions;
import nl.tudelft.bw4t.network.BW4TServerActions;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BW4TClient.class, LocateRegistry.class, Naming.class })
public class BW4TClientTest {

	@Mock
	RemoteEnvironment remoteEnvironment;
	
	Registry registry;
	
	BW4TServerActions remote;

	BW4TClient client;

	@Before
	public void setUp() throws RemoteException, MalformedURLException, NotBoundException {
		client = new BW4TClient(remoteEnvironment);
		registry = new Registry() {
			@Override
			public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
			}

			@Override
			public String[] list() throws RemoteException, AccessException {
				return null;
			}

			@Override
			public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException {
				return null;
			}

			@Override
			public void rebind(String name, Remote obj) throws RemoteException, AccessException {		
			}

			@Override
			public void unbind(String name) throws RemoteException, NotBoundException, AccessException {		
			}};
		remote = new BW4TServerActions() {
			
			@Override
			public void unregisterClient(BW4TClientActions client) throws RemoteException {	
			}
			
			@Override
			public void unregisterAgent(String agent) throws AgentException, RemoteException {	
			}
			
			@Override
			public void requestStart() throws RemoteException, ManagementException {	
			}
			
			@Override
			public void requestReset(Map<String, Parameter> parameters) throws RemoteException, ManagementException {	
			}
			
			@Override
			public void requestPause() throws RemoteException, ManagementException {
			}
			
			@Override
			public void requestInit(Map<String, Parameter> parameters) throws RemoteException, ManagementException {
			}
			
			@Override
			public void registerClient(BW4TClientActions client, int agentCount, int humanCount) throws RemoteException {
			}
			
			@Override
			public void registerAgent(String agentId) throws RemoteException, AgentException {
			}
			
			@Override
			public String queryProperty(String property) throws RemoteException {
				return null;
			}
			
			@Override
			public String queryEntityProperty(String entity, String property) throws RemoteException {
				
				return null;
			}
			
			@Override
			public Percept performEntityAction(String entity, Action action) throws RemoteException {
				
				return null;
			}
			
			@Override
			public boolean isSupportedByEnvironment(Action arg0) throws RemoteException {
				
				return false;
			}
			
			@Override
			public String getType(String entity) throws RemoteException, EntityException {
				
				return null;
			}
			
			@Override
			public EnvironmentState getState() throws RemoteException {
				return null;
			}
			
			@Override
			public Collection<String> getFreeEntities() throws RemoteException {
				return null;
			}
			
			@Override
			public Collection<String> getEntities() throws RemoteException {
				return null;
			}
			
			@Override
			public Set<String> getAssociatedEntities(String agent) throws RemoteException, AgentException {
				return null;
			}
			
			@Override
			public Collection<String> getAssociatedAgents(String entity) throws RemoteException, EntityException {
				return null;
			}
			
			@Override
			public List<Percept> getAllPerceptsFromEntity(String entity) throws RemoteException {
				return null;
			}
			
			@Override
			public List<String> getAgents() throws RemoteException {
				return null;
			}
			
			@Override
			public void freePair(String agent, String entity) throws RemoteException, RelationException {
			}
			
			@Override
			public void freeEntity(String entity) throws RemoteException, RelationException, EntityException {
			}
			
			@Override
			public void freeAgent(String agent) throws RemoteException, RelationException {		
			}
			
			@Override
			public void associateEntity(String agentId, String entityId) throws RelationException, RemoteException {		
			}

            @Override
            public void registerClient(BW4TClientActions client, List<BotConfig> bots, List<EPartnerConfig> partners)
                    throws RemoteException {
                // TODO Auto-generated method stub
                
            }
		};
	}

	@Test
	public void constructorTest() throws RemoteException, MalformedURLException, NotBoundException {
		assertNotNull(client);
	}

	@Test
	public void connectionTest() throws RemoteException, MalformedURLException, NotBoundException{
		mockStaticPartial(LocateRegistry.class, "createRegistry");
		mockStatic(Naming.class);
		expect(LocateRegistry.createRegistry(EasyMock.eq(2000))).andReturn(registry).anyTimes();
		Naming.rebind((String) EasyMock.anyObject(), (Remote) EasyMock.anyObject());
		EasyMock.expectLastCall().anyTimes();
		expect(Naming.lookup((String) EasyMock.anyObject())).andReturn(remote);
		PowerMock.replayAll();
		client.connectServer();
		assertTrue(true);
		PowerMock.verifyAll();
	}

}
