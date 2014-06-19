package nl.tudelft.bw4t.environmentstore.editor.model;

import static org.junit.Assert.*;
import nl.tudelft.bw4t.map.Zone;

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
}
