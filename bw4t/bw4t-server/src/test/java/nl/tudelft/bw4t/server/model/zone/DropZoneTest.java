package nl.tudelft.bw4t.server.model.zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.robots.AgentRecord;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;


public class DropZoneTest {
    
    private nl.tudelft.bw4t.map.Zone zone = Mockito.mock(nl.tudelft.bw4t.map.Zone.class);
    private Context<Object> context = Mockito.mock(Context.class);
    private ContinuousSpace<Object> space = Mockito.mock(ContinuousSpace.class);
    private Grid<Object> grid = Mockito.mock(Grid.class);
    private List<BlockColor> colors = new ArrayList<BlockColor>();
    private AbstractRobot robot = Mockito.mock(AbstractRobot.class);
    private DropZone dropzone;
    
    @Before
    public void setup() {
        when(zone.getBoundingbox()).thenReturn(new Rectangle(1.0,1.0,1.0,1.0));
         dropzone = new DropZone(zone,space,grid,context);
        
    }
    
    @Test
    public void setSequenceTest() {
        colors.add(BlockColor.BLUE);
        dropzone.setSequence(colors);         
    }
    
    @Test
    public void getSequenceTest() {
        assertEquals(new LinkedList<BlockColor>(), dropzone.getSequence());
        colors.add(BlockColor.BLUE);
        dropzone.setSequence(colors);
        assertEquals(colors,dropzone.getSequence());
    }

    
    @Test
    public void getSequenceIndexTest() {
        assertEquals(0, dropzone.getSequenceIndex());
    }
    
    @Test
    public void droppedTest() {
        when(robot.getBoundingBox()).thenReturn(new Rectangle2D.Double());
        assertFalse(dropzone.dropped(new Block(BlockColor.BLUE,space,grid,context), robot));
        when(robot.getBoundingBox()).thenReturn(new Rectangle2D.Double(0.5,0.5,1.0,1.0));
        assertTrue(dropzone.dropped(new Block(BlockColor.BLUE,space,grid,context), robot));
//        colors.add(BlockColor.BLUE);
        colors.add(BlockColor.DARK_GRAY);
        dropzone.setSequence(colors);
        when(robot.getAgentRecord()).thenReturn(new AgentRecord("SomethingToTest"));
        assertTrue(dropzone.dropped(new Block(BlockColor.BLUE,space,grid,context), robot));
        Mockito.verify(robot).getAgentRecord();
        colors.clear();
        colors.add(BlockColor.BLUE);
        colors.add(BlockColor.DARK_GRAY);
        assertTrue(dropzone.dropped(new Block(BlockColor.BLUE,space,grid,context), robot));
        colors.remove(1);
        assertTrue(dropzone.dropped(new Block(BlockColor.BLUE,space,grid,context), robot));
    }
    
    @Test
    public void sequenceCompleteTest() {
        assertTrue(dropzone.sequenceComplete());
        colors.add(BlockColor.DARK_GRAY);
        dropzone.setSequence(colors);
        assertFalse(dropzone.sequenceComplete());
    }
}
