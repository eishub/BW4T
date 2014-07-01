package nl.tudelft.bw4t.server.model.robots.handicap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot;
import nl.tudelft.bw4t.server.model.zone.DropZone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

@RunWith(MockitoJUnitRunner.class)
public class HandicapTest {

    /**
     * space Mock
     */
    @Mock
    private ContinuousSpace<Object> space;
    @Mock
    private Grid<Object> grid;
    /**
     * context Mock
     */
    @Mock
    private Context<Object> context;
    /**
     * point Mock
     */
    @Mock
    private NdPoint point;
    /**
     * block Mock
     */
    @Mock
    private Block block;

    /**
     * DropZone Mock
     */
    @Mock
    private DropZone dropzone;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        smap = spy(new BW4TServerMap(map, context));
        when(smap.getContinuousSpace()).thenReturn(space);
        when(smap.getGridSpace()).thenReturn(grid);
    }

    /**
     * - the ColorBlindHandicap was nicely added to the Handicaps List.
     */
    @Test
    public void testColorBlindHandicap() {
        IRobot r = new ColorBlindHandicap(new NavigatingRobot("", smap, true, 0));

        assertTrue(r.getHandicapsList().contains("ColorBlind"));
    }

    /**
     * - the GripperHandicap was nicely added to the Handicaps List; - the robot cannot pick up blocks. - the robot's
     * capacity is 0 no matter what.
     */
    @Test
    public void testGripperHandicap() {
        IRobot r = new GripperHandicap(new NavigatingRobot("", smap, true, 200));

        assertTrue(r.getHandicapsList().contains("Gripper"));

        assertFalse(r.canPickUp(block));
        assertTrue(r.getGripperCapacity() == 0);
    }

    /**
     * - Set the gripper capacity to 2. - Default is 1, robot can now hold 2 blocks.
     */
    @Test
    public void testSetGripperCapacity() {
        IRobot r = new Human(new NavigatingRobot("", smap, true, 200));
        r.setGripperCapacity(2);

        assertTrue(r.getGripperCapacity() == 2);
    }

    /**
     * - The Human handicap was added to the Handicaps List; - The Robot is not holding an e-Partner.
     */
    @Test
    public void testHumanHandicap() {
        IRobot r = new Human(new NavigatingRobot("", smap, true, 200));

        assertTrue(r.getSuperParent().getHandicapsList().contains("Human"));
        assertTrue(r.getEPartner() == null);
    }

    /**
     * - the SizeOverloadHandicap was nicely added to the Handicaps List; - the robot's size is 5.
     */
    @Test
    public void testSizeOverloadHandicap() {
        IRobot r = new SizeOverloadHandicap(new NavigatingRobot("", smap, true, 1), 5);

        assertTrue(r.getHandicapsList().contains("SizeOverload"));

        assertTrue(r.getSize() == 5);
    }

    /**
     * - the handicaps were nicely added to the Handicaps List; - the robot's speed mod is 0.8; - the robot cannot pick
     * up blocks; - the robot's capacity is 0 no matter what; - the robot's size is 2.
     */
    @Test
    public void testAllHandicaps() {
        IRobot r = new SizeOverloadHandicap(new ColorBlindHandicap(new GripperHandicap(new NavigatingRobot("", smap,
                true, 1))), 2);

        assertTrue(r.getHandicapsList().size() == 3);

        assertFalse(r.canPickUp(block));
        assertTrue(r.getGripperCapacity() == 0);
        assertTrue(r.getSize() == 2);
    }

    /**
     * Testing whether the robot is an instance of - IRobot - AbstractRobotDecorator
     */
    @Test
    public void testAllHandicapStructure() {
        IRobot r = new SizeOverloadHandicap(new ColorBlindHandicap(new GripperHandicap(new NavigatingRobot("", smap,
                true, 1))), 3);

        assertTrue(r instanceof IRobot);
        assertTrue(r instanceof AbstractRobotDecorator);

    }

    /**
     * Testing the AbstractRobotDecorator getName method.
     */
    @Test
    public void testDecoratorGetName() {
        IRobot r = new SizeOverloadHandicap(new NavigatingRobot("botname", smap, true, 1), 5);

        assertTrue(r.getName().equals("botname"));
    }

    /**
     * Testing the pickUp block function. By picking up a block and testing whether the robot is now holding it.
     */
    @Test
    public void testDecoratorPickUp() {
        IRobot r = new SizeOverloadHandicap(new NavigatingRobot("", smap, true, 1), 5);
        r.pickUp(block);

        ArrayList<Block> blockList = new ArrayList<Block>();
        blockList.add(block);

        assertTrue(blockList.equals(r.isHolding()));
    }

}
