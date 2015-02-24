package nl.tudelft.bw4t.server.model.robots;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

@RunWith(MockitoJUnitRunner.class)
public class AbstractRobotTest {

    private ContinuousSpace<Object> space = Mockito.mock(ContinuousSpace.class);
    private Grid<Object> grid = Mockito.mock(Grid.class);

    private Context<Object> context = Mockito.mock(Context.class);
    private IRobot mockedIRobot = Mockito.mock(IRobot.class);

    private AbstractRobot bot;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        smap = spy(new BW4TServerMap(map, context));
        when(smap.getContinuousSpace()).thenReturn(space);
        when(smap.getGridSpace()).thenReturn(grid);

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
