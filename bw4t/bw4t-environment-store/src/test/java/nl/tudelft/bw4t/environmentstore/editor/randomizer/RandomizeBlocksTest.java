package nl.tudelft.bw4t.environmentstore.editor.randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.controller.RandomizeSequenceController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Before;
import org.junit.Test;

/**
 * This tests the map controller class.
 */
public class RandomizeBlocksTest {

    /**
     * This is the controller where the sequence gets generated, and blocks in
     * all rooms.
     */
    private MapPanelController map;

    /** This is the frame needed to instantiate the RandomizeController */
    private RandomizeSequenceFrame rframe;
    

    /** This is the controller where blocks get generated in specific rooms. */
    private RandomizeSequenceController random;
    

    /** This is the room we will generate blocks in. */
    private ZoneController room1;
    
    /** This is the room we will generate blocks in. */
    private ZoneController room2;

    /** This is the updateable interface the map controller will use. */
    private UpdateableEditorInterface uei = new UpdateableEditorInterface() {

        @Override
        public void update() {
            // For testing purposes only.

        }
    };

    /** This is the list of block colors we have made available for this test. */
    private List<BlockColor> colors;

    @Before
    public void setUp() {
        map = new MapPanelController(2, 1);

        setUpRoom();
        setUpColors();

        rframe = new RandomizeSequenceFrame("Pédé", map);
        

        random = new RandomizeSequenceController(rframe, map);
    }

    /** Sets up the room we work in. */
    private void setUpRoom() {
        room1 = map.getZoneControllers()[0][0];
        room1.setType(Type.ROOM);
        room1.setUpdateableEditorInterface(uei);
        
        room2 = map.getZoneControllers()[1][0];
        room2.setType(Type.ROOM);
        room2.setUpdateableEditorInterface(uei);
    }

    /** Sets up the block colors we have made available for this test. */
    private void setUpColors() {
        colors = new ArrayList<BlockColor>();
        colors.add(BlockColor.RED);
        colors.add(BlockColor.GREEN);
        colors.add(BlockColor.BLUE);
    }

    /**
     * Testing the randomizeSequence method: 0 blocks.
     * Testing the randomizeSequence method: 0 blocks. The resulting sequence
     * should be empty.
     */
    @Test
    public void randomizeSequenceTest1() {
        colors = new ArrayList<BlockColor>();
        colors.add(BlockColor.BLUE);
        colors.add(BlockColor.GREEN);
        colors.add(BlockColor.RED);
        map.setSequence(random.randomizeSequence(colors, 0));

        assertTrue(map.getSequence().isEmpty());
    }

    /**
     * Testing the randomizeSequence method: 5 blocks.
     * Testing the randomizeSequence method: 5 blocks. The resulting sequence
     * should contain 5 blocks. The resulting sequence should not contain any
     * other block colors than RGB.
     */
    @Test
    public void randomizeSequenceTest2() {
        colors = new ArrayList<BlockColor>();
        colors.add(BlockColor.BLUE);
        colors.add(BlockColor.GREEN);
        colors.add(BlockColor.RED);
        map.setSequence(random.randomizeSequence(colors, 5));

        assertEquals(map.getSequence().size(), 5);

        assertFalse(map.getSequence().contains(BlockColor.YELLOW));
        assertFalse(map.getSequence().contains(BlockColor.WHITE));
        assertFalse(map.getSequence().contains(BlockColor.PINK));
        assertFalse(map.getSequence().contains(BlockColor.ORANGE));
        assertFalse(map.getSequence().contains(BlockColor.DARK_GRAY));
    }

    /**
     * Testing the randomizeSequence method: 5 blocks but no available colors.
     * This throws a IllegalArgumentException because random.nextInt(0) throws it. 
     * This throws an exception because it is not possible to randomize
     * anything. This throws an IllegalArgumentException because
     * random.nextInt(0) throws it.
     */
    @Test(expected = IllegalArgumentException.class)
    public void randomizeSequenceTest3() {
        colors = new ArrayList<BlockColor>();

        random.randomizeSequence(colors, 5);
    }

    /**
     * Testing the randomizeColors method: 5 blocks. The resulting list should
     * contain 5 blocks. The resulting list should not contain any other block
     * colors than RGB.
     */
    @Test
    public void randomizeColorsTest2() {
        room1.randomizeColors(5, colors);

        assertTrue(room1.getColors().size() <= 5);

        Set<BlockColor> set = toSet(room1.getColors());
        assertTrue(set.size() <= 3);
        assertFalse(set.contains(BlockColor.YELLOW));
        assertFalse(set.contains(BlockColor.WHITE));
        assertFalse(set.contains(BlockColor.PINK));
        assertFalse(set.contains(BlockColor.ORANGE));
        assertFalse(set.contains(BlockColor.DARK_GRAY));
    }

    /** Converts a list to a set to avoid duplicates, used for testing purposes only. */
    private Set<BlockColor> toSet(List<BlockColor> colors) {
        Set<BlockColor> set = new HashSet<BlockColor>();
        set.addAll(colors);
        return set;
    }

    /**
     * Testing the randomizeColors method: 5 blocks but no available colors.
     * This throws an exception because it is not possible to randomize anything. 
     * This throws an IllegalArgumentException because random.nextInt(0) throws it.
     */
    @Test(expected = IllegalArgumentException.class)
    public void randomizeColorsTest3() {
        colors = new ArrayList<BlockColor>();

        room1.randomizeColors(5, colors);
    }

    /**
     * Testing the randomizeColorsInRoom method.
     * Testing on two rooms if they both get a number of blocks between 1 and 10. 
     */
    @Test
    public void randomizeColorsInRoom() {
        map.randomizeColorsInRooms(colors, 10);

        assertFalse(room1.getColors().isEmpty());
        assertFalse(room2.getColors().isEmpty());
        assertTrue(room1.getColors().size() <= 10);
        assertTrue(room2.getColors().size() <= 10);
    }
}