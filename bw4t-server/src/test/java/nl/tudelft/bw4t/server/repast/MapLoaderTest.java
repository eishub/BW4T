package nl.tudelft.bw4t.server.repast;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * Tests various functions of the MapLoader. The class is rather complex, so not
 * everything can be properly tested.
 */
@RunWith(MockitoJUnitRunner.class)
public class MapLoaderTest {

	@Mock
	private ContinuousSpace<Object> space;
	@Mock
	private Context<Object> context;

	/**
	 * Tests whether it correctly returns a random sequence of colors when
	 * called.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void makeRandomSequenceTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = MapLoader.class.getDeclaredMethod("makeRandomSequence", BW4TServerMap.class, int.class);
		method.setAccessible(true);
		List<BlockColor> result;

		result = (List<BlockColor>) method.invoke(null, null, 0);
		assertTrue(result.size() == 0);
		result = (List<BlockColor>) method.invoke(null, null, 1);
		assertTrue(result.size() == 1 && Arrays.asList(BlockColor.values()).contains(result.get(0)));
		result = (List<BlockColor>) method.invoke(null, null, 5);
		assertTrue(result.size() == 5);
		for (int i = 0; i < result.size(); i++) {
			assertTrue(Arrays.asList(BlockColor.values()).contains(result.get(i)));
		}
	}

}