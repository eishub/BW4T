package nl.tudelft.bw4t.map.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Zone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractMapControllerTest {
    
    private AbstractMapController mc;
    @Mock
    private MapRendererInterface mri;
    @Mock
    private NewMap themap;
    
    @Before
    public void setup() {
        mc = mock(AbstractMapController.class, Mockito.CALLS_REAL_METHODS);
        mc.setRenderSettings(new MapRenderSettings());
    }
    
    @After
    public void tearDown() {
        //make sure we stop the thread(if started)
        mc.setRunning(false);
        mc = null;
    }
    
    @Test
    public void getMapTest() {
        assertNull(mc.getMap());
    }
    
    @Test
    public void setMapTest() {
        assertNull(mc.getMap());
        
        setMap();
        assertEquals(themap, mc.getMap());
    }
    
    public void setupMapZones() {
        List<Zone> zones = new ArrayList<>();
        Zone z = mock(Zone.class);
        when(z.getType()).thenReturn(Zone.Type.CORRIDOR);
        when(z.getName()).thenReturn("C");
        zones.add(z);
        z = mock(Zone.class);
        when(z.getType()).thenReturn(Zone.Type.ROOM);
        when(z.getName()).thenReturn("A");
        zones.add(z);
        z = mock(Zone.class);
        when(z.getType()).thenReturn(Zone.Type.ROOM);
        when(z.getName()).thenReturn("B");
        zones.add(z);
        z = mock(Zone.class);
        when(z.getType()).thenReturn(Zone.Type.ROOM);
        when(z.getName()).thenReturn(Zone.DROP_ZONE_NAME);
        zones.add(z);
        when(themap.getZones()).thenReturn(zones);
    }
    
    public void setMap() {
        Point p = new Point();
        when(themap.getArea()).thenReturn(p);
        mc.setMap(themap);
    }
    
    @Test
    public void getRoomsTest() {
        setupMapZones();

        setMap();
        
        Set<Zone> res = mc.getRooms();
        
        assertEquals(3, res.size());
        for (Zone z1 : res) {
            assertEquals(Zone.Type.ROOM, z1.getType());
        }
    }
    
    @Test
    public void getZonesTest() {
        setupMapZones();

        setMap();
        
        Set<Zone> res = mc.getZones();
        
        assertEquals(4, res.size());
    }
    
    @Test
    public void getDropZoneTest() {
        setupMapZones();

        setMap();
        
        Zone res = mc.getDropZone();
        
        assertNotNull(res);
        assertEquals(Zone.DROP_ZONE_NAME, res.getName());
    }
    
    @Test(expected=MapFormatException.class)
    public void getDropZoneFailTest() {
        setMap();
        
        Zone res = mc.getDropZone();
    }
    
    @Test
    public void setRunningTest() throws Exception{
        assertFalse(mc.isRunning());
        
        mc.setRunning(true);
        //let the thread set the running to true
        Thread.sleep(10);
        
        assertTrue(mc.isRunning());
    }
    
    @Test
    public void rendererTest() throws Exception {
        setMap();
        TestMapController mc = spy(new TestMapController(themap));
        assertTrue(mc.getRenderSettings() instanceof MapRenderSettings);
        
        Thread.sleep(5);
        mc.setRunning(false);
        Thread.sleep(5);
        
        assertEquals(0, mc.getRenderers().size());
        
        
        mc.addRenderer(mri);
        assertEquals(1, mc.getRenderers().size());
        
        mc.run();
        verify(mc).updateRenderer(mri);
        
        
        mc.removeRenderer(mri);
        assertEquals(0, mc.getRenderers().size());
    }
}
