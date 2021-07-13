package nl.tudelft.bw4t.server.model.robots;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import repast.simphony.context.Context;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class AbstractRobotTest {
    private Context<Object> context = Mockito.mock(Context.class);

    private AbstractRobot bot;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        smap = spy(new BW4TServerMap(map, context));

        int cap = 2;
        String name = "Bot1";
        bot = new NavigatingRobot(name, smap, true, cap);
    }

    // can no do because no such field in navigatingrobot
    @Test
    public void getNameTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        assertEquals(bot.getName(), "Bot1");
    }
}
