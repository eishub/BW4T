package nl.tudelft.bw4t.server.model.doors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.zone.BlocksRoom;
import nl.tudelft.bw4t.server.model.zone.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

/**
 * The class <code>DoorTest</code> contains tests for the class <code>{@link Door}</code>.
 */
@RunWith(MockitoJUnitRunner.class)
public class DoorTest {

    @Mock
    private ContinuousSpace<Object> space;
    @Mock
    private Grid<Object> grid;
    @Mock
    private Context<Object> context;
    @Mock
    private BlocksRoom room;
    @Mock
    private AbstractRobot robot;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        smap = spy(new BW4TServerMap(map, context));
        when(smap.getContinuousSpace()).thenReturn(space);
        when(smap.getGridSpace()).thenReturn(grid);
    }

    /**
     * Run the Door(ContinuousSpace<Object>,Context<Object>) constructor test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testDoor_1() {
        when(context.isEmpty()).thenReturn(true);
        Door result = new Door(smap);

        assertNotNull(result);
        assertTrue(result.isOpen());
        assertEquals(new NdPoint(0, 0), result.getLocation());
        assertEquals(0L, result.getId());
    }

    /**
     * Run the Color getColor() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testGetColor_1() {
        Door fixture = new Door(smap);
        fixture.connectTo(null);

        Color result = fixture.getColor();

        assertEquals(result, nl.tudelft.bw4t.map.Door.COLOR_OPEN);
    }

    /**
     * Run the Color getColor() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testGetColor_2() {
        when(room.getOccupier()).thenReturn(robot);

        Door fixture = new Door(smap);
        fixture.connectTo(room);

        Color result = fixture.getColor();

        assertEquals(result, nl.tudelft.bw4t.map.Door.COLOR_CLOSED);
    }

    /**
     * Run the boolean isOpen() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testIsOpen_1() {
        Door fixture = new Door(smap);
        fixture.connectTo((Room) null);

        boolean result = fixture.isOpen();

        // add additional test code here
        assertTrue(result);
    }

    /**
     * Run the boolean isOpen() method test.
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testIsOpen_2() {
        when(room.getOccupier()).thenReturn(robot);

        Door fixture = new Door(smap);
        fixture.connectTo(room);

        boolean result = fixture.isOpen();

        assertFalse(result);
    }

    /**
     * Run the boolean isOpen() method test.
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testIsOpen_3() {
        Door fixture = new Door(smap);
        fixture.connectTo(room);

        boolean result = fixture.isOpen();

        assertTrue(result);
    }

    /**
     * Run the void setPos(double,double,Orientation) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testSetPos_1() {
        Door fixture = new Door(smap);
        double x = 1.0;
        double y = 1.0;
        nl.tudelft.bw4t.map.Door.Orientation ori = nl.tudelft.bw4t.map.Door.Orientation.HORIZONTAL;

        fixture.setPos(x, y, ori);

        Rectangle2D rect = fixture.getBoundingBox();
        assertEquals(rect.getWidth(), nl.tudelft.bw4t.map.Door.DOOR_WIDTH, 0.01);
        assertEquals(rect.getHeight(), nl.tudelft.bw4t.map.Door.DOOR_THICKNESS, 0.01);
        verify(space).moveTo(fixture, x, y);
    }

    /**
     * Run the void setPos(double,double,Orientation) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Test
    public void testSetPos_2() {
        Door fixture = new Door(smap);
        double x = 1.0;
        double y = 1.0;
        nl.tudelft.bw4t.map.Door.Orientation ori = nl.tudelft.bw4t.map.Door.Orientation.VERTICAL;

        fixture.setPos(x, y, ori);

        Rectangle2D rect = fixture.getBoundingBox();
        assertEquals(rect.getWidth(), nl.tudelft.bw4t.map.Door.DOOR_THICKNESS, 0.01);
        assertEquals(rect.getHeight(), nl.tudelft.bw4t.map.Door.DOOR_WIDTH, 0.01);
        verify(space).moveTo(fixture, x, y);
    }

    /**
     * Perform pre-test initialization.
     * 
     * @throws Exception
     *             if the initialization fails for some reason
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @Before
    public void setUp() throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     * 
     * @throws Exception
     *             if the clean-up fails for some reason
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    @After
    public void tearDown() throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     * @generatedBy CodePro at 24.05.14 15:09
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(DoorTest.class);
    }
}
