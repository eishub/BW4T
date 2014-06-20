package nl.tudelft.bw4t.server.model.robots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

@RunWith(MockitoJUnitRunner.class)
public class AbstractRobotTest {
    
    private ContinuousSpace<Object> mockedSpace = Mockito.mock(ContinuousSpace.class);
    private Grid<Object> grid = Mockito.mock(Grid.class);

    private Context<Object> mockedContext = Mockito.mock(Context.class);
    private IRobot mockedIRobot = Mockito.mock(IRobot.class);

    private AbstractRobot bot;
    
    @Before
    public void createNavigatingRobot() {
        int cap = 2;
        String name = "Bot1";
        bot = new NavigatingRobot(name, mockedSpace, grid, mockedContext, true, cap);
    }
    
    // can no do because no such field in navigatingrobot
    @Test
    public void getNameTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        assertEquals(bot.getName(), "Bot1");
    }
/*    
    @Test
    public void setTopMostHandicapTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        bot.setTopMostHandicap(mockedIRobot);
        Field topMostHandicap = AbstractRobot.class.getField("topMostHandicap");
        topMostHandicap.setAccessible(true);
        assertEquals(topMostHandicap.get(bot), mockedIRobot);
    }
*/
}
