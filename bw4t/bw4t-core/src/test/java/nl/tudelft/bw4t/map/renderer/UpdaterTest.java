package nl.tudelft.bw4t.map.renderer;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpdaterTest {

    @Mock
    private AbstractMapController mc;
    
    @Mock
    private MapRenderSettings sett;

    @Test
    public void failToStart() {
        when(mc.isRunning()).thenReturn(true);
        when(mc.getRenderers()).thenReturn(new HashSet<MapRendererInterface>());
        Updater upd = new Updater(mc);
        
        upd.run();
        
        verify(mc).isRunning();
        verifyNoMoreInteractions(mc);
    }
    
    @Test
    public void startAndRun() {
        when(mc.isRunning()).thenReturn(false, true, true, false);
        when(mc.isStarting()).thenReturn(true, false, false, false);
        when(mc.getRenderSettings()).thenReturn(sett);
        when(sett.getUpdateDelay()).thenReturn(0);
        Updater upd = new Updater(mc);
        
        upd.run();
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            fail("Got interrupted!");
        }
        
        verify(mc, times(4)).isRunning();
        verify(mc, times(3)).isStarting();
        verify(mc).setForceRunning(true);
        verify(mc, times(2)).run();
        verify(mc, times(2)).getRenderSettings();
        verifyNoMoreInteractions(mc);
        
        verify(sett, times(2)).getUpdateDelay();
        verifyNoMoreInteractions(sett);
    }
}
