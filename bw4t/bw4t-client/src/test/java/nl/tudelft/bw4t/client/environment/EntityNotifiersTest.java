package nl.tudelft.bw4t.client.environment;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import eis.EnvironmentListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import nl.tudelft.bw4t.client.controller.ClientController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This class tests the EntityNotifiers class from the Environment package in the client.
 * It tests whether entities are correctly notified when free, deleted or new.
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityNotifiersTest {
    
    private EnvironmentListener mockedEnvListener = Mockito.mock(eis.EnvironmentListener.class);
    private EnvironmentListener mockedEnvListener2 = Mockito.mock(eis.EnvironmentListener.class);
    private EnvironmentListener mockedEnvListener3 = Mockito.mock(eis.EnvironmentListener.class);
    private RemoteEnvironment mockedRemEnv = Mockito.mock(RemoteEnvironment.class);
    private Collection<String> mockedCollection = Mockito.mock(Collection.class);
    private ClientController mockedGUI = Mockito.mock(ClientController.class);
    private Map mockedMap = Mockito.mock(Map.class);
    
    @Before
    public void createRemotes() {
        ArrayList<EnvironmentListener> listenerList = new ArrayList<EnvironmentListener>();
        listenerList.add(mockedEnvListener);
        listenerList.add(mockedEnvListener2);
        listenerList.add(mockedEnvListener3);
        when(mockedRemEnv.getEnvironmentListeners()).thenReturn(listenerList);
    }
    
    @Test
    public void notifyFreeEntityTest() {
        EntityNotifiers.notifyFreeEntity("Bot1", mockedCollection, mockedRemEnv);
        
        Mockito.verify(mockedEnvListener).handleFreeEntity(any(String.class), any(Collection.class));
        Mockito.verify(mockedEnvListener2).handleFreeEntity(any(String.class), any(Collection.class));
        Mockito.verify(mockedEnvListener3).handleFreeEntity(any(String.class), any(Collection.class));
    }
    
    @Test
    public void notifyNewEntityTest() {        
        EntityNotifiers.notifyNewEntity("Bot1", mockedRemEnv);
        
        Mockito.verify(mockedEnvListener).handleNewEntity(any(String.class));
        Mockito.verify(mockedEnvListener2).handleNewEntity(any(String.class));
        Mockito.verify(mockedEnvListener3).handleNewEntity(any(String.class));
    }
    
    @Test
    public void notifyDeletedEntityTest() {

        when(mockedRemEnv.getEntityController("Bot1")).thenReturn(mockedGUI);
        EntityNotifiers.notifyDeletedEntity("Bot1", mockedCollection, mockedRemEnv);
        
        //if(mockedRemEnv.getEntityToGUI().get("Bot1") != null) {
            Mockito.verify(mockedGUI).stop();
        //}
        
        Mockito.verify(mockedEnvListener).handleDeletedEntity(any(String.class), any(Collection.class));
        Mockito.verify(mockedEnvListener2).handleDeletedEntity(any(String.class), any(Collection.class));
        Mockito.verify(mockedEnvListener3).handleDeletedEntity(any(String.class), any(Collection.class));
    }
}
