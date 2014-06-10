package nl.tudelft.bw4t.client.environment;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import eis.EnvironmentListener;

/**
 * This class tests the EntityNotifiers class from the Environment package in the client.
 * It tests whether entities are correctly notified when free, deleted or new.
 */
public class EntityNotifiersTest {
    
    private EnvironmentListener mockedEnvListener = Mockito.mock(eis.EnvironmentListener.class);
    private RemoteEnvironment mockedRemEnv = Mockito.mock(RemoteEnvironment.class);
    private Collection<String> mockedCollection = Mockito.mock(Collection.class);
    
    @Test
    public void notifyFreeEntityTest() {
        ArrayList<EnvironmentListener> listenerList = new ArrayList<EnvironmentListener>();
        listenerList.add(mockedEnvListener);
        when(mockedRemEnv.getEnvironmentListeners()).thenReturn(listenerList);
        
        EntityNotifiers.notifyFreeEntity("Bot1", mockedCollection, mockedRemEnv);
        Mockito.verify(mockedEnvListener).handleFreeEntity(any(String.class), any(Collection.class));
    }
}
