package nl.tudelft.bw4t.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>NewMapTest</code> contains tests for the class
 * <code>{@link NewMap}</code>.
 */
public class NewMapTest {
    /**
     * Run the NewMap() constructor test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testNewMap_1() throws Exception {

        NewMap result = new NewMap();

        // add additional test code here
        assertNotNull(result);
        assertEquals(
                "Map[onebotperzone=false, randomblocks=0,size=0,sequence=[],zones=[]]",
                result.toString());
        assertEquals(Boolean.FALSE, result.getOneBotPerCorridorZone());
        assertEquals(new Integer(0), result.getRandomSequence());
        assertEquals(new Integer(0), result.getRandomBlocks());
    }

    /**
     * Run the void addEntity(Entity) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testAddEntity_1() throws Exception {
        NewMap fixture = new NewMap();
        Entity e = new Entity();

        fixture.addEntity(e);

        // add additional test code here
        assertTrue(fixture.getEntities().contains(e));
    }

    /**
     * Run the void addZone(Zone) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testAddZone_1() throws Exception {
        NewMap fixture = new NewMap();
        Zone zone = new Zone();

        fixture.addZone(zone);

        // add additional test code here
    }

    /**
     * Run the NewMap create(InputStream) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test(expected = javax.xml.bind.UnmarshalException.class)
    public void testCreate_1() throws Exception {
        InputStream instream = new ByteArrayInputStream("".getBytes());

        NewMap result = NewMap.create(instream);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the NewMap create(InputStream) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testCreate_2() throws Exception {
        InputStream instream = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><newMap></newMap>".getBytes());

        NewMap result = NewMap.create(instream);

        // add additional test code here
        assertNotNull(result);
        assertEquals(
                "Map[onebotperzone=false, randomblocks=0,size=0,sequence=[],zones=[]]",
                result.toString());
        assertEquals(Boolean.FALSE, result.getOneBotPerCorridorZone());
        assertEquals(new Integer(0), result.getRandomSequence());
        assertEquals(new Integer(0), result.getRandomBlocks());
    }

    /**
     * Run the NewMap create(InputStream) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testCreate_3() throws Exception {
        InputStream instream = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><newMap><area><x>50.0</x><y>80.0</y></area></newMap>".getBytes());

        NewMap result = NewMap.create(instream);

        // add additional test code here
        assertNotNull(result);
        Point size = result.getArea();
        assertEquals(50, size.getX(), 0.1);
        assertEquals(80, size.getY(), 0.1);
    }

    /**
     * Run the Point getArea() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetArea_1() throws Exception {
        NewMap fixture = new NewMap();

        Point result = fixture.getArea();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0.0, result.getX(), 1.0);
        assertEquals(0.0, result.getY(), 1.0);
    }

    /**
     * Run the List<Entity> getEntities() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetEntities_1() throws Exception {
        NewMap fixture = new NewMap();

        assertEquals(0, fixture.getEntities().size());
        
        List<Entity> entities = new ArrayList<Entity>();
        
        fixture.setEntities(entities);
        
        assertEquals(entities, fixture.getEntities());
    }

    /**
     * Run the Boolean getOneBotPerCorridorZone() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetOneBotPerCorridorZone_1() throws Exception {
        NewMap fixture = new NewMap();
        fixture.setOneBotPerCorridorZone(new Boolean(true));

        Boolean result = fixture.getOneBotPerCorridorZone();

        // add additional test code here
        assertNotNull(result);
        assertTrue(result.booleanValue());
    }

    /**
     * Run the Integer getRandomBlocks() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetRandomBlocks_1() throws Exception {
        NewMap fixture = new NewMap();
        fixture.setRandomBlocks(new Integer(1));

        Integer result = fixture.getRandomBlocks();

        // add additional test code here
        assertNotNull(result);
        assertEquals(1, result.intValue());
    }

    /**
     * Run the Integer getRandomSequence() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetRandomSequence_1() throws Exception {
        NewMap fixture = new NewMap();
        
        assertEquals(0, fixture.getRandomSequence().intValue());
        
        fixture.setRandomSequence(new Integer(1));

        assertEquals(1, fixture.getRandomSequence().intValue());
    }

    /**
     * Run the List<BlockColor> getSequence() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetSequence_1() throws Exception {
        NewMap fixture = new NewMap();
        ArrayList<BlockColor> list = new ArrayList<BlockColor>();
        list.add(BlockColor.BLUE);
        list.add(BlockColor.GREEN);
        list.add(BlockColor.ORANGE);
        list.add(BlockColor.PINK);
        list.add(BlockColor.RED);
        list.add(BlockColor.WHITE);
        list.add(BlockColor.YELLOW);
        fixture.setSequence(list);

        List<BlockColor> result = fixture.getSequence();

        // add additional test code here
        assertNotNull(result);
        assertEquals(7, result.size());
    }

    /**
     * Run the Zone getZone(String) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test(expected= IllegalArgumentException.class)
    public void testGetZone_1() throws Exception {
        NewMap fixture = new NewMap();
        String name = "";

        fixture.getZone(name);
    }

    /**
     * Run the Zone getZone(String) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetZone_2() throws Exception {
        String name = "test";
        Zone z = mock(Zone.class);
        when(z.getName()).thenReturn(name);
        NewMap fixture = new NewMap();
        fixture.addZone(z);

        Zone result = fixture.getZone(name);

        // add additional test code here
        assertNotNull(result);
        assertEquals(result, z);
    }

    /**
     * Run the Zone getZone(String) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetZone_3() throws Exception {
        String name = "test";
        Zone z = mock(Zone.class);
        when(z.getName()).thenReturn(name);
        String name2 = "test2";
        Zone z2 = mock(Zone.class);
        when(z2.getName()).thenReturn(name2);
        NewMap fixture = new NewMap();
        fixture.addZone(z);
        fixture.addZone(z2);

        Zone result = fixture.getZone(name2);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the List<Zone> getZones() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetZones_empty() throws Exception {
        
        NewMap fixture = new NewMap();

        assertEquals(0, fixture.getZones().size());
        
        List<Zone> zones = new ArrayList<Zone>();
        
        fixture.setZones(zones);
        
        assertEquals(zones, fixture.getZones());

    }

    /**
     * Run the List<Zone> getZones(Type) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetZones_Rooms() throws Exception {
        Zone z = mock(Zone.class);
        when(z.getType()).thenReturn(Type.CORRIDOR);
        Zone z2 = mock(Zone.class);
        when(z2.getType()).thenReturn(Type.ROOM);
        NewMap fixture = new NewMap();
        fixture.addZone(z);
        fixture.addZone(z2);

        List<Zone> result = fixture.getZones(Type.ROOM);

        // add additional test code here
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Run the List<Zone> getZones(Type) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testGetZones_Corridors() throws Exception {
        Zone z = mock(Zone.class);
        when(z.getType()).thenReturn(Type.CORRIDOR);
        Zone z2 = mock(Zone.class);
        when(z2.getType()).thenReturn(Type.ROOM);
        NewMap fixture = new NewMap();
        fixture.addZone(z);
        fixture.addZone(z2);

        List<Zone> result = fixture.getZones(Type.CORRIDOR);

        // add additional test code here
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Run the String toString() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 10:59
     */
    @Test
    public void testToString_1() throws Exception {
        NewMap fixture = new NewMap();
        fixture.setRandomBlocks(new Integer(1));
        fixture.setOneBotPerCorridorZone(new Boolean(true));
        fixture.setArea(new Point());
        fixture.setRandomSequence(new Integer(1));
        ArrayList<BlockColor> list = new ArrayList<BlockColor>();
        list.add(BlockColor.BLUE);
        list.add(BlockColor.GREEN);
        list.add(BlockColor.ORANGE);
        list.add(BlockColor.PINK);
        list.add(BlockColor.RED);
        list.add(BlockColor.WHITE);
        list.add(BlockColor.YELLOW);
        fixture.setSequence(list);

        String result = fixture.toString();

        // add additional test code here
        assertEquals(
                "Map[onebotperzone=true, randomblocks=1,size=0,sequence=[BLUE, GREEN, ORANGE, PINK, RED, WHITE, YELLOW],zones=[]]",
                result);
    }

    /**
     * Perform pre-test initialization.
     * 
     * @throws Exception
     *             if the initialization fails for some reason
     * 
     * @generatedBy CodePro at 25.05.14 10:59
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
     * @generatedBy CodePro at 25.05.14 10:59
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
     * @generatedBy CodePro at 25.05.14 10:59
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(NewMapTest.class);
    }
}