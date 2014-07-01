package nl.tudelft.bw4t.map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EqualsTest {
    @Test
    public void testSameInstance() {
        NewMap map = new NewMap();
        assertTrue(map.equals(map));
    }
    @Test
    public void testSimple1() {
        NewMap map = new NewMap();
        NewMap map2 = new NewMap();
        assertTrue(map.equals(map2));
    }

    @Test
    public void testFile1() throws Exception {
        NewMap map = NewMap.create(EqualsTest.class.getResourceAsStream("/Rainbow"));
        NewMap map2 = NewMap.create(EqualsTest.class.getResourceAsStream("/Rainbow"));
        assertTrue(map.equals(map2));
        assertEquals(map.hashCode(), map2.hashCode());
    }
}
