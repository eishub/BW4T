package nl.tudelft.bw4t.environmentstore.editor.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.environmentstore.editor.model.Node.DoorDirection;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Test;

public class ZoneModelTest {
    
    private ZoneModel zone;
    
    @Test
    public void testGenerateName() {
        zone = new ZoneModel();
        zone.generateNameFromPosition(0, 0);
        assertEquals(Zone.CORRIDOR_NAME + "A1", zone.getName());
    }

    @Test
    public void testGenerateName2() {
        zone = new ZoneModel();
        zone.generateNameFromPosition(5, 25);
        assertEquals(Zone.CORRIDOR_NAME + "Z6", zone.getName());
    }

    @Test
    public void testGenerateName3() {
        zone = new ZoneModel();
        zone.generateNameFromPosition(10, 26);
        assertEquals(Zone.CORRIDOR_NAME + "AA11", zone.getName());
    }

    @Test
    public void testGenerateName4() {
        zone = new ZoneModel();
        zone.generateNameFromPosition(28, 28);
        assertEquals(Zone.CORRIDOR_NAME + "AC29", zone.getName());
    }

    @Test
    public void testGenerateName5() {
        zone = new ZoneModel();
        zone.generateNameFromPosition(100, 702);
        assertEquals(Zone.CORRIDOR_NAME + "AAA101", zone.getName());
    }
    
    @Test
    public void constructorTest() {
        Zone z = new Zone();
        ZoneModel zm = new ZoneModel(z);
        assertEquals(zm.getType(), Type.ROOM);
    }
    
    @Test
    public void constructorTestNode() {
        Node n = new Node(Type.ROOM);
        n.setDir(DoorDirection.NORTH);
        ZoneModel zm = new ZoneModel(n);
        assertEquals(zm.getType(), Type.ROOM);
        assertTrue(zm.hasDoor(0));
        n.setDir(DoorDirection.EAST);
        zm = new ZoneModel(n);
        assertTrue(zm.hasDoor(1));
        n.setDir(DoorDirection.SOUTH);
        zm = new ZoneModel(n);
        assertTrue(zm.hasDoor(2));
        n.setDir(DoorDirection.WEST);
        zm = new ZoneModel(n);
        assertTrue(zm.hasDoor(3));
        n.setType(Type.CORRIDOR);
        zm = new ZoneModel(n);
        assertFalse(zm.hasDoor(0));
        assertFalse(zm.hasDoor(1));
        assertFalse(zm.hasDoor(2));
        assertFalse(zm.hasDoor(3));
    }
    
    @Test(expected = NullPointerException.class)
    public void setColorNullTest() {
        ZoneModel zm = new ZoneModel();
        zm.setColors(null);
    }
    
    @Test
    public void setColorTest() {
        List<BlockColor> l = new ArrayList<BlockColor>();
        ZoneModel zm = new ZoneModel();
        zm.setColors(l);
        assertEquals(zm.getColors(), l);
    }
    
    @Test
    public void BlockColorsRoomTest() {
        List<BlockColor> l = new ArrayList<BlockColor>();
        l.add(BlockColor.BLUE);
        l.add(BlockColor.BLUE);
        ZoneModel zm = new ZoneModel();
        zm.setType(Type.ROOM);
        zm.setColors(l);
        assertEquals(zm.getColors(), l);
    }
    
    @Test
    public void BlockColorsCorridorTest() {
        List<BlockColor> l = new ArrayList<BlockColor>();
        l.add(BlockColor.BLUE);
        l.add(BlockColor.BLUE);
        ZoneModel zm = new ZoneModel();
        zm.setType(Type.CORRIDOR);
        zm.setColors(l);
        assertNotEquals(zm.getColors(), l);
    }
    
    @Test
    public void spawnCountCorridorTest() {
        ZoneModel zm = new ZoneModel();
        zm.setType(Type.CORRIDOR);
        assertEquals(zm.getSpawnCount(), 0);
    }
    
    @Test
    public void spawnCountStartZoneTest() {
        ZoneModel zm = new ZoneModel();
        zm.setType(Type.CORRIDOR);
        zm.setStartZone(true);
        assertEquals(zm.getSpawnCount(), 4);
    }
    
    @Test
    public void getZoneTest() {
        Zone z = new Zone();
        ZoneModel zm = new ZoneModel(z);
        assertEquals(zm.getZone(), z);
    }
    
    @Test
    public void calcColTest() {
        Rectangle r = new Rectangle(100.0, 100.0, 100.0, 100.0);
        Zone z = new Zone();
        z.setBoundingbox(r);
        assertTrue(ZoneModel.calcColumn(z) == 9);
    }
    
    @Test
    public void calcRowTest() {
        Rectangle r = new Rectangle(100.0, 100.0, 100.0, 100.0);
        Zone z = new Zone();
        z.setBoundingbox(r);
        assertTrue(ZoneModel.calcRow(z) == 9);
    }
}
