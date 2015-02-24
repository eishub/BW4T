package nl.tudelft.bw4t.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * The class <code>EntityTest</code> contains tests for the class
 * <code>{@link Entity}</code>.
 */
public class EntityTest {
    /**
     * Run the Entity() constructor test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 16:04
     */
    @Test
    public void testEntity_1() throws Exception {

        Entity result = new Entity();

        // add additional test code here
        assertNotNull(result);
        assertEquals("-", result.getName());
    }

    /**
     * Run the Entity(String,Point) constructor test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 16:04
     */
    @Test
    public void testEntity_2() throws Exception {
        String n = "Blubb";
        Point pos = new Point(2, 3);

        Entity result = new Entity(n, pos);

        // add additional test code here
        assertNotNull(result);
        assertEquals(n, result.getName());
        assertEquals(pos, result.getPosition());
    }

    /**
     * Run the String getName() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 16:04
     */
    @Test
    public void testGetName_1() throws Exception {
        Entity fixture = new Entity();
        String name = "argh";

        String result = fixture.getName();

        assertEquals("-", result);

        fixture.setName(name);

        result = fixture.getName();

        assertEquals(name, result);
    }

    /**
     * Run the Point getPosition() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 16:04
     */
    @Test
    public void testGetPosition() throws Exception {
        Point pos = new Point();
        Entity fixture = new Entity("", pos);
        fixture.setType(Entity.EntityType.JAVA);

        Point result = fixture.getPosition();

        // add additional test code here
        assertNotNull(result);
        assertEquals(pos, result);

        pos = new Point(2, 3);

        fixture.setPosition(pos);

        result = fixture.getPosition();

        // add additional test code here
        assertNotNull(result);
        assertEquals(pos, result);
    }

    /**
     * Run the Entity.EntityType getType() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 16:04
     */
    @Test
    public void testGetType() throws Exception {
        Entity fixture = new Entity("", new Point());

        assertEquals(Entity.EntityType.NORMAL, fixture.getType());

        fixture.setType(Entity.EntityType.JAVA);

        assertEquals(Entity.EntityType.JAVA, fixture.getType());
    }

    /**
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     * @generatedBy CodePro at 25.05.14 16:04
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(EntityTest.class);
    }
}