package nl.tudelft.bw4t.environmentstore.editor.model;

import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.environmentstore.editor.model.Node.DoorDirection;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Before;
import org.junit.Test;
/**
 * Test the node class for coverage.
 */
public class NodeTest {
    /**
     * The test subject node.
     */
    private Node n;
    /**
     * Set up the test.
     */
    @Before
    public void setUp() {
        n = new Node(Type.ROOM);
    }
    /**
     * Test if all the directions are free when all the neighbours
     * of the node are corridors.
     */
    @Test
    public void allFreeTest() {
        n.setNorth(new Node(Type.CORRIDOR));
        n.setEast(new Node(Type.CORRIDOR));
        n.setSouth(new Node(Type.CORRIDOR));
        n.setWest(new Node(Type.CORRIDOR));
        assertTrue(n.getFreeDirs().size() == 4);
    }
    
    /**
     * Test if all the directions are blocked if they are blockades.
     */
    @Test
    public void allBlockedTest() {
        n.setNorth(new Node(Type.BLOCKADE));
        n.setEast(new Node(Type.BLOCKADE));
        n.setSouth(new Node(Type.BLOCKADE));
        n.setWest(new Node(Type.BLOCKADE));
        assertTrue(n.getFreeDirs().size() == 0);
    }
    /**
     * Test if all the directions are blocked if they are null-valued.
     */
    @Test
    public void allNullTest() {
        assertTrue(n.getFreeDirs().size() == 0);
    }
    /**
     * General test for getters and setters.
     */
    @Test
    public void getterSetterTest() {
        Node n1 = new Node(Type.BLOCKADE);
        n.setNorth(n1);
        assertTrue(n.getNorth() == n1);
        n.setEast(n1);
        assertTrue(n.getEast() == n1);
        n.setSouth(n1);
        assertTrue(n.getSouth() == n1);
        n.setWest(n1);
        assertTrue(n.getWest() == n1);
        n.setType(Type.ROOM);
        assertTrue(n.getType() == Type.ROOM);
        n.setDir(DoorDirection.NORTH);
        assertTrue(n.getDir() == DoorDirection.NORTH);
    }
}
