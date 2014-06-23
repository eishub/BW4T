/**
 * 
 */
package nl.tudelft.bw4t.environmentstore.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.BeforeClass;
import org.junit.Test;


public class NeighbourTest {
    static EnvironmentMap model;
    
    @BeforeClass
    public static void init() {
        model = new EnvironmentMap(10, 10);
        ZoneModel startzone = new ZoneModel();
        startzone.setStartZone(true);
        model.setZone(9,9, startzone);
        ZoneModel dropzone = new ZoneModel();
        dropzone.setDropZone(true);
        model.setZone(8,9, dropzone);
    }
    
    @Test
    public void testCorridorsNeigbour() {
        ZoneModel zmodel = new ZoneModel();
        zmodel.generateNameFromPosition(1, 1);
        model.setZone(1, 1, zmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getZone(Zone.CORRIDOR_NAME + "B1").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "B2")));
    }
    
    @Test
    public void testRoomsNeighbourNorth1(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(0, true);
        rmodel.generateNameFromPosition(1,2);
        model.setZone(1, 2, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getZone(Zone.ROOM_NAME + "C2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "C1")));
    }
    
    @Test
    public void testRoomsNeighbourNorth2(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(0, true);
        rmodel.generateNameFromPosition(1,2);
        model.setZone(1, 2, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertFalse(map.getZone(Zone.ROOM_NAME + "C2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "C3")));
    }
    
    @Test
    public void testRoomsNeighbourSouth1(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(2, true);
        rmodel.generateNameFromPosition(1,3);
        model.setZone(1, 3, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getZone(Zone.ROOM_NAME + "D2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "D3")));
    }
    
    @Test
    public void testRoomsNeighbourSouth(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(2, true);
        rmodel.generateNameFromPosition(1,3);
        model.setZone(1, 3, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertFalse(map.getZone(Zone.ROOM_NAME + "D2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "D1")));
    }
    
    @Test
    public void testRoomsNeighbourEast1(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(1, true);
        rmodel.generateNameFromPosition(1,4);
        model.setZone(1, 4, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getZone(Zone.ROOM_NAME + "E2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "F2")));
    }
    
    @Test
    public void testRoomsNeighbourEast2(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(1, true);
        rmodel.generateNameFromPosition(1,4);
        model.setZone(1, 4, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertFalse(map.getZone(Zone.ROOM_NAME + "E2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "D2")));
    }
    
    @Test
    public void testRoomsNeighbourWest1(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(3, true);
        rmodel.generateNameFromPosition(1,7);
        model.setZone(1, 7, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getZone(Zone.ROOM_NAME + "H2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "G2")));
    }
    
    @Test
    public void testRoomsNeighbourWest2(){
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(3, true);
        rmodel.generateNameFromPosition(1,7);
        model.setZone(1, 7, rmodel);
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertFalse(map.getZone(Zone.ROOM_NAME + "H2").getNeighbours().
                contains(map.getZone(Zone.CORRIDOR_NAME + "I2")));
    }

}
