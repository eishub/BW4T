package nl.tudelft.bw4t.environmentstore.editor.randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the view of the randomize sequence frame.
 *
 */
public class RandomizeSequenceFrameTest {

    /**
     * This is the controller where the sequence gets generated, and blocks in
     * all rooms.
     */
    private MapPanelController map;

    /** This is the frame needed to instantiate the RandomizeController */
    private RandomizeSequenceFrame frame;
    
    /** This is the room we will generate blocks in. */
    private ZoneController room1;
    
    /** This is the list of block colors we have made available for this test. */
    private List<BlockColor> colors;
    
    private UpdateableEditorInterface uei = new UpdateableEditorInterface() {
        @Override
        public void update() {
            // For testing purposes only.
        }
    };
    
    /**
     * Setup method used to initialize the frame.
     */
    @Before
    public void setUp() {
        map = new MapPanelController(2, 1);
        map.setUpdateableEditorInterface(uei);
        
        setUpRoom();
        setUpColors();

        frame = new RandomizeSequenceFrame("Sequence", map);
    }
    
    /** Sets up the room we work in. */
    private void setUpRoom() {
        room1 = map.getZoneControllers()[0][0];
        room1.setType(Type.ROOM);
        room1.setUpdateableEditorInterface(uei);
    }

    /** Sets up the block colors we have made available for this test. */
    private void setUpColors() {
        colors = new ArrayList<BlockColor>();
        colors.add(BlockColor.RED);
        colors.add(BlockColor.GREEN);
        colors.add(BlockColor.BLUE);
    }

    /**
     * Test whether from the beginning all block colors are checked.
     */
    @Test
    public void allCheckboxesSelectedOnStartTest() {
        assertTrue(frame.isRed());
        assertTrue(frame.isGreen());
        assertTrue(frame.isYellow());
        assertTrue(frame.isBlue());
        assertTrue(frame.isOrange());
        assertTrue(frame.isWhite());
        assertTrue(frame.isPink());
    }
    
    /**
     * Test whether the number of blocks initially has been set to 8.
     */
    @Test 
    public void checkOfBlocksOnStartTest() {
        assertEquals((Integer) 8, frame.getNumberOfBlocks());
    }
    
    /**
     * Test whether we can set the spinner.
     */
    @Test
    public void setSpinnerNumberOfBlocksTest() {
        frame.setSpinnerModel(9);
        assertEquals((Integer) 9, frame.getNumberOfBlocks());
    }
    
    /**
     * Test whether the apply button has one listener.
     */
    @Test
    public void applyButtonListenerTest() {
        int number = frame.getApplyButton().getActionListeners().length;
        int expect = 1;
        assertEquals(number, expect);
    }
    
    /**
     * Test whether the cancel button has one listener.
     */
    @Test
    public void cancelButtonListenerTest() {
        int number = frame.getCancelButton().getActionListeners().length;
        int expect = 1;
        assertEquals(number, expect);
    }
    
    /**
     * Test whether the randomize button has one listener.
     */
    @Test
    public void randomizeButtonListenerTest() {
        int number = frame.getRandomizeButton().getActionListeners().length;
        int expect = 1;
        assertEquals(number, expect);
    }
    
    /**
     * Test whether we can set the textfield by pressing the button.
     */
    @Test
    public void setRandomizedSequenceWithButtonTest() {
        frame.getRandomizeButton().doClick();
        assertFalse(frame.getRandomizedSequence().getText().isEmpty());
    }
    
    @Test
    public void setRandomSequenceTesting() {
        assertTrue(map.getSequence().isEmpty());
        
        frame.getRandomizeButton().doClick();
        frame.getApplyButton().doClick();

        assertFalse(map.getSequence().isEmpty());
    }
    
    @Test
    public void disposeRandomizeFrame() {
        frame.setVisible(true);
        frame.getRandomizeButton().doClick();
        frame.getCancelButton().doClick();
        
        assertTrue(map.getSequence().isEmpty());
        assertFalse(frame.isVisible());
    }
    
}
