package nl.tudelft.bw4t.environmentstore.editor.model;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Test;
/**
 * Test suite to cover everything from EnvironmentMap
 * that hasn't already been covered.
 *
 */
public class EnvironmentMapTest {
    private EnvironmentMap em = new EnvironmentMap(10, 10);
    @Test(expected = IllegalArgumentException.class)
    public void tooFewRowsTest() {
        em = new EnvironmentMap(0, 10);
    }
    @Test(expected = IllegalArgumentException.class)
    public void tooManyRowsTest() {
        em = new EnvironmentMap(101, 10);
    }
    @Test(expected = IllegalArgumentException.class)
    public void tooFewColsTest() {
        em = new EnvironmentMap(10, 0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void tooManyColsTest() {
        em = new EnvironmentMap(10, 101);
    }
    @Test(expected = IllegalArgumentException.class)
    public void setZonesNullTest() {
        em.setZones(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void setZonesTooFewRowsTest() {
        em.setZones(new ZoneModel[1][10]);
    }
    @Test(expected = IllegalArgumentException.class)
    public void setZonesTooFewColsTest() {
        em.setZones(new ZoneModel[10][1]);
    }
    @Test
    public void setZonesNiceWeatherTest() {
        ZoneModel[][] zmarray = new ZoneModel[2][2];
        ZoneModel zm = new ZoneModel();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                zmarray[i][j] = zm;
            }
        }
        em.setZones(zmarray);
        assertTrue(em.getZone(0, 0) == zm);
    }
    @Test(expected = NullPointerException.class)
    public void setZoneNullTest() {
        em.setZone(0, 0, null);
    }
    @Test
    public void isValidZoneTest() {
        assertFalse(em.isValidZone(-1, 0));
        assertFalse(em.isValidZone(10, 0));
        assertFalse(em.isValidZone(0, -1));
        assertFalse(em.isValidZone(0, 10));
        assertTrue(em.isValidZone(0, 0));
    }
    @Test
    public void getSpawnCountTest() {
        ZoneModel zm1 = new ZoneModel(new Zone("Test", null, Type.CORRIDOR));
        zm1.setStartZone(true);
        em = new EnvironmentMap(1, 1);
        em.setZone(0, 0, zm1);
        assertTrue(em.getSpawnCount() == 4);
    }
}
