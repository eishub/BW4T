/**
 * 
 */
package nl.tudelft.bw4t.environmentstore.editor;

import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.BeforeClass;
import org.junit.Test;


public class EntityTest {
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
        ZoneModel rmodel = new ZoneModel(new Zone());
        rmodel.setType(Type.ROOM);
        rmodel.setDoor(0, true);
        rmodel.generateNameFromPosition(1,2);
    }

    @Test
    public void NumberOfEntitiesTest() {
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getEntities().size() == 4);
    }
    
    @Test
    public void LocationOfEntity1Test() {
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getEntities().get(0).getPosition().equals(new Point(95-2.5,95-2.5)));
    }
    
    @Test
    public void LocationOfEntity2Test() {
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getEntities().get(1).getPosition().equals(new Point(95+2.5,95-2.5)));
    }
    
    @Test
    public void LocationOfEntity3Test() {
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getEntities().get(2).getPosition().equals(new Point(95-2.5,95+2.5)));
    }
    
    @Test
    public void LocationOfEntity4Test() {
        NewMap map = nl.tudelft.bw4t.environmentstore.editor.model.MapConverter.createMap(model);
        assertTrue(map.getEntities().get(3).getPosition().equals(new Point(95+2.5,95+2.5)));
    }

}
