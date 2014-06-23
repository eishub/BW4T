package nl.tudelft.bw4t.server.repast;

import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.server.model.blocks.Block;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/** 
 * Tests various functions of the MapLoader. 
 * The class is rather complex, so not everything can be properly tested. 
 */
@RunWith(MockitoJUnitRunner.class)
public class MapLoaderTest {
    
    @Mock private ContinuousSpace space;
    @Mock private Context context;
    
    @Rule public ExpectedException exception = ExpectedException.none();

    /** Tests whether it correctly returns a random sequence of colors when called. */
    @Test
    public void makeRandomSequenceTest() throws NoSuchMethodException, SecurityException, 
            IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Method method = MapLoader.class.getDeclaredMethod("makeRandomSequence", int.class);
        method.setAccessible(true);
        List<BlockColor> result;

        result = (List<BlockColor>) method.invoke(null, -1);
        assertTrue(result.size() == 0);
        result = (List<BlockColor>) method.invoke(null, 0);
        assertTrue(result.size() == 0);
        result = (List<BlockColor>) method.invoke(null, 1);
        assertTrue(result.size() == 1 && Arrays.asList(BlockColor.values()).contains(result.get(0)));
        result = (List<BlockColor>) method.invoke(null, 5);
        assertTrue(result.size() == 5);
        for (int i = 0; i < result.size(); i++) {
            assertTrue(Arrays.asList(BlockColor.values()).contains(result.get(i)));
        }
    }

    /** Tests whether it correctly prevents vertical overlapping of the blocks. */
    @Test
    public void createBlocksForRoomVerticalOverlapTest() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = MapLoader.class.getDeclaredMethod("findFreePlace", 
                Rectangle2D.class, List.class);
        method.setAccessible(true);
        Rectangle2D room = new Rectangle(0, 0, 2, 10);
        List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();
        blockList.add(new Rectangle(0, 0, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(1, 0, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(0, 9, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(1, 9, Block.SIZE, Block.SIZE));

        // As there is a random element in placement, we test multiple times to be sure
        for (int i = 0; i < 100; i++) {
            Rectangle2D block = (Rectangle2D) method.invoke(null, room, blockList);
            assertTrue(block.getMinY() >= 1);
            assertTrue(block.getMaxY() <= 9);
        }
    }

    /** Tests whether it correctly prevents horizontal overlapping of the blocks. */
    @Test
    public void createBlocksForRoomHorizontalOverlapTest() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = MapLoader.class.getDeclaredMethod("findFreePlace", 
                Rectangle2D.class, List.class);
        method.setAccessible(true);
        Rectangle2D room = new Rectangle(0, 0, 10, 2);
        List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();
        blockList.add(new Rectangle(0, 0, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(0, 1, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(9, 0, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(9, 1, Block.SIZE, Block.SIZE));
        
        // As there is a random element in placement, we test multiple times to be sure
        for (int i = 0; i < 100; i++) {
            Rectangle2D block = (Rectangle2D) method.invoke(null, room, blockList);
            assertTrue(block.getMinX() >= 1);
            assertTrue(block.getMaxX() <= 9);
        }
    }

    /** Checks if the right exception is thrown when it tries to add a block room with not enough space. */
    @Test
    public void createBlocksForRoomNoRoomTest() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = MapLoader.class.getDeclaredMethod("findFreePlace", 
                Rectangle2D.class, List.class);
        method.setAccessible(true);
        Rectangle2D room = new Rectangle(0, 0, 0, 0);
        List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();

        // We check for InvocationTargetException because that is thrown before IllegalStateException
        exception.expect(InvocationTargetException.class);
        method.invoke(null, room, blockList);
    }
    
    /** Checks if the right exception is thrown when it tries to add a block to a full room. */
    @Test
    public void createBlocksForRoomFullRoomTest() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = MapLoader.class.getDeclaredMethod("findFreePlace", 
                Rectangle2D.class, List.class);
        method.setAccessible(true);
        Rectangle2D room = new Rectangle(0, 0, 2, 2);
        List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();
        blockList.add(new Rectangle(0, 0, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(0, 1, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(1, 0, Block.SIZE, Block.SIZE));
        blockList.add(new Rectangle(1, 1, Block.SIZE, Block.SIZE));
        
        // We check for InvocationTargetException because that is thrown before IllegalStateException
        exception.expect(InvocationTargetException.class);
        method.invoke(null, room, blockList);
    }
}
